/*
 * -----------------------------------------------------------------------------
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 * -----------------------------------------------------------------------------
 * 
 * @Author  : Sourav Kumar Das
 * @Purpose : This class contains service-level business logic for managing 
 *            user registration, login/logout, OTP verification, password reset, 
 *            and user verification workflows. It interacts with AdminDaoImpl 
 *            for persistence and enforces business rules like resend limits, 
 *            OTP deactivation, and password history checks.
 * 
 * -----------------------------------------------------------------------------
 */

package com.infinite.jsf.admin.service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.infinite.jsf.admin.daoImpl.AdminDaoImpl;
import com.infinite.jsf.admin.model.Otp;
import com.infinite.jsf.admin.model.OtpStatus;
import com.infinite.jsf.admin.model.Reason;
import com.infinite.jsf.admin.model.User;
import com.infinite.jsf.util.EncryptPassword;
import com.infinite.jsf.util.MailSend;
import com.infinite.jsf.util.MailSendIsh;
import com.infinite.jsf.util.Messages;

public class AdminService {

	/** 
	 * Logger instance for logging important info, warnings, and errors 
	 * during service operations.
	 */
	private static final Logger logger = Logger.getLogger(AdminService.class);

	/**
	 * DAO instance used for accessing and manipulating admin-related 
	 * database records.
	 */
	private AdminDaoImpl dao = new AdminDaoImpl();

	/**
	 * Checks whether the user is allowed to resend OTP based on two constraints:
	 * 1. At least 30 seconds must have passed since the last active OTP was generated.
	 * 2. The user must not have requested OTP more than 5 times in the last 30 minutes.
	 *
	 * @param email 
	 * @param reason 
	 * @return boolean 
	 */
	public boolean canResendOtp(String email, Reason reason) {
		Timestamp now = new Timestamp(System.currentTimeMillis());

		Otp lastOtp = dao.getLatestActiveOtp(email, reason);
		if (lastOtp != null) {
			long secondsElapsed = (now.getTime() - lastOtp.getCreatedAt().getTime()) / 1000;
			if (secondsElapsed < 30) {
				if(logger.isInfoEnabled()) {
					logger.info("Resend blocked: last OTP sent " + secondsElapsed + "s ago to " + email); 
				}
				return false;
			}
		}

		Timestamp halfHourAgo = new Timestamp(now.getTime() - (30 * 60 * 1000));
		int resendCount = dao.countOtpRequestsSince(email, reason, halfHourAgo);
		if(logger.isInfoEnabled()) {
			logger.info("OTP resend count in last 30 min for " + email + ": " + resendCount); 
		}
		return resendCount <= 5;
	}


	/**
	 * Sends a verification OTP to the provided email address during user signup.
	 * This method performs the following checks and actions:
	 * 
	 * 1. Validates that the given email and username are not already registered.
	 * 2. Ensures no more than 5 OTPs have been sent to the user for signup in the last 30 minutes.
	 * 3. Ensures at least 30 seconds have passed since the last active OTP was sent.
	 * 4. Generates and sends a 6-digit OTP via email.
	 * 5. Stores the OTP in the database with expiration and active status.
	 *
	 * @param email 
	 * @param username 
	 * @return Map<String, String> 
	 */
	public Map<String, String> sendEmailOtp(String email, String username) {
		Map<String, String> errors = new HashMap<>();
		if(logger.isInfoEnabled()) {
			logger.info("Checking if user/email already exists before sending OTP.");
		}
		// Check if user or email already exists
		if (dao.existsByEmail(email)) {
			errors.put("email", Messages.EMAIL_ALREADY_REGISTERED);
		}
		if (dao.existsByUsername(username)) {
			errors.put("username", Messages.USERNAME_ALREADY_TAKEN);
		}
		if (!errors.isEmpty()) {
			return errors; // return errors if any found
		}

		// ✅ Step 1: Rate-limit logic - check if already sent 5 in 30 min
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp thirtyMinutesAgo = new Timestamp(now.getTime() - (30 * 60 * 1000));

		List<Otp> recentOtps = dao.getAllOtpByEmailAndReasonSince(email, Reason.SIGNUP, thirtyMinutesAgo);

		if (recentOtps.size() >= 5) {
			// OTPs exist — calculate remaining block time
			Otp oldest = recentOtps.get(recentOtps.size() -1); // assume list is sorted by createdAt ascending
			long millisElapsed = now.getTime() - oldest.getCreatedAt().getTime();
			long secondsRemaining = (30 * 60) - (millisElapsed / 1000);
			long minutes = secondsRemaining / 60;
			long seconds = secondsRemaining % 60;

			logger.warn("OTP resend blocked for email: " + email);

			errors.put("otp", String.format(Messages.OTP_LIMIT_EXCEEDED, minutes));
			errors.put("remainingMinutes", String.valueOf(minutes));
			errors.put("remainingSeconds", String.valueOf(seconds));
			return errors;
		}

		// ✅ Step 2: Check cooldown of 30 seconds since last OTP
		if (!canResendOtp(email, Reason.SIGNUP)) {
			errors.put("otp", Messages.OTP_REQUEST_COOLDOWN);
			return errors;
		}

		if(logger.isInfoEnabled()) {
			logger.info("Generating and sending OTP to: " + email);
		}
		String otpCode = String.format("%06d", new Random().nextInt(999999));


		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 5);
		Timestamp expiry = new Timestamp(cal.getTimeInMillis());

		Otp otp = new Otp();
		otp.setEmail(email);
		otp.setOtpCode(otpCode);
		otp.setCreatedAt(now);
		otp.setExpiresAt(expiry);
		otp.setReason(Reason.SIGNUP);
		otp.setStatus(OtpStatus.ACTIVE);

		String message = "Your OTP for email verification is: " + otpCode;

		try {
			MailSendIsh.sendInfo(email, "Email Verification OTP", message);
		} catch (Exception e) {
			logger.error("Failed to send email: ", e);
			errors.put("email", Messages.FAILED_TO_SEND_EMAIL);
			return errors;
		}

		boolean saved = dao.saveEmailOtp(otp); 
		if (!saved) {
			errors.put("email", Messages.INTERNAL_OTP_SAVE_ERROR);
		}

		return errors;
	}

	/**
	 * Validates the user input fields including first name, last name, username,
	 * email, and password. 
	 *
	 * @param user
	 * @return Map 
	 */
	public Map<String, String> validateUserInputFields(User user) {
		Map<String, String> errors = new HashMap<>();
		if(logger.isInfoEnabled()) {
			logger.info("Starting validation for user input fields.");
		}
		if (user == null) {
			logger.warn("User object is null.");
			errors.put("general", Messages.USER_INPUT_MISSING);
			return errors;
		}

		// List of allowed base domains (you can expand this as needed)
		List<String> allowedBaseDomains = Arrays.asList(
				"gmail.com", "yahoo.com", "hotmail.com", "outlook.com",
				"icloud.com", "aol.com", "protonmail.com", "zoho.com",
				"mail.com", "gmx.com", "yandex.ru", "mail.ru",
				"edu.in", "edu", "rediffmail.com"
				);

		// Allow optional subdomains before the base domains
		List<String> mapped = allowedBaseDomains.stream()
				.map((String domain) -> "([A-Za-z0-9-]+\\.)*" + Pattern.quote(domain))
				.collect(Collectors.toList());

		String domainPattern = String.join("|", mapped);

		// Final email regex with allowed subdomains and domains
		String emailPattern = "^[A-Za-z0-9._%+-]+@(?:" + domainPattern + ")$";


		if (user.getFirstName() == null || user.getFirstName().trim().isEmpty())
			errors.put("firstName", Messages.FIRST_NAME_REQUIRED);
		else if (!user.getFirstName().matches("^[A-Za-z]{3,}( [A-Za-z]+)?$"))
			errors.put("firstName", Messages.FIRST_NAME_INVALID);

		if (user.getLastName() == null || user.getLastName().trim().isEmpty())
			errors.put("lastName", Messages.LAST_NAME_REQUIRED);
		else if (!user.getLastName().matches("^[A-Za-z]{2,}$"))
			errors.put("lastName", Messages.LAST_NAME_INVALID);

		if (user.getUsername() == null || user.getUsername().trim().isEmpty())
			errors.put("username", Messages.USERNAME_REQUIRED);
		else if (!user.getUsername().matches("^[A-Za-z][A-Za-z0-9_]{3,19}$"))
			errors.put("username", Messages.USERNAME_INVALID);

		if (user.getEmail() == null || user.getEmail().trim().isEmpty()) 
			errors.put("email", Messages.EMAIL_REQUIRED);
		else if (!user.getEmail().matches(emailPattern)) 
			errors.put("email", Messages.EMAIL_INVALID + " Allowed domains: " + String.join(", ", allowedBaseDomains));


		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) 
			errors.put("password", Messages.PASSWORD_REQUIRED);
		else if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,18}$")) 
			errors.put("password", Messages.PASSWORD_INVALID);

		if(logger.isInfoEnabled()) {
			logger.info("Completed validation for user: " + user.getUsername());
		}
		return errors;
	}


	/**
	 * Sends a password reset OTP to the user's registered email.
	 * 
	 * 1. Verifies whether the input is an email or username and checks its existence.
	 * 2. Resolves the user's email from the username if necessary.
	 * 3. Enforces a rate limit of 5 OTPs in 30 minutes.
	 * 4. Applies a cooldown period of 30 seconds between OTP requests.
	 * 5. Generates a 6-digit OTP valid for 5 minutes.
	 * 6. Sends the OTP to the user's email.
	 * 7. Saves the OTP to the database.
	 * 
	 * @param emailOrUsername 
	 * @return Map<String, String>
	 */
	public Map<String, String> sendForgotPasswordOtp(String emailOrUsername) {
		Map<String, String> errors = new HashMap<>();

		// Check if empty
		if (emailOrUsername == null || emailOrUsername.trim().isEmpty()) {
			errors.put("usernameOrEmail", Messages.FORGOT_REQUIRED_FIELDS_MISSING);
			return errors;
		}

		boolean isEmail = emailOrUsername.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

		// Step 0: Validate email/username existence
		if (isEmail) {
			if (!dao.existsByEmail(emailOrUsername)) {
				errors.put("usernameOrEmail", Messages.EMAIL_NOT_FOUND);
				return errors;
			}
		} else {
			if (!dao.existsByUsername(emailOrUsername)) {
				errors.put("usernameOrEmail", Messages.USERNAME_NOT_FOUND);
				return errors;
			}
		}

		// Step 1: Resolve email if given username
		String email = isEmail ? emailOrUsername : Optional.ofNullable(dao.findByUsername(emailOrUsername))
				.map(User::getEmail)
				.orElse(null);

		if (email == null) {
			errors.put("usernameOrEmail", Messages.EMAIL_NOT_FOUND);
			return errors;
		}

		// ✅ Step 2: Rate-limit - max 5 OTPs in 30 minutes
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp thirtyMinutesAgo = new Timestamp(now.getTime() - (30 * 60 * 1000));
		List<Otp> recentOtps = dao.getAllOtpByEmailAndReasonSince(email, Reason.FORGOT_PASSWORD, thirtyMinutesAgo);

		if (recentOtps.size() >= 5) {
			Otp oldest = recentOtps.get(recentOtps.size() -1); // assume sorted by createdAt ascending
			long millisElapsed = now.getTime() - oldest.getCreatedAt().getTime();
			long secondsRemaining = (30 * 60) - (millisElapsed / 1000);
			long minutes = secondsRemaining / 60;
			long seconds = secondsRemaining % 60;

			logger.warn("Forgot Password OTP resend blocked for email: " + email);
			errors.put("otp", String.format(Messages.OTP_LIMIT_EXCEEDED, minutes));
			errors.put("remainingMinutes", String.valueOf(minutes));
			errors.put("remainingSeconds", String.valueOf(seconds));
			return errors;
		}

		// ✅ Step 3: Cooldown - 30 seconds since last OTP
		if (!canResendOtp(email, Reason.FORGOT_PASSWORD)) {
			errors.put("otp", Messages.OTP_REQUEST_COOLDOWN);
			return errors;
		}

		// ✅ Step 4: Generate OTP
		if(logger.isInfoEnabled()) {
			logger.info("Generating Forgot Password OTP for: " + email);
		}
		String otpCode = String.format("%06d", new Random().nextInt(999999));

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 5);
		Timestamp expiry = new Timestamp(cal.getTimeInMillis());

		Otp otp = new Otp();
		otp.setEmail(email);
		otp.setOtpCode(otpCode);
		otp.setCreatedAt(now);
		otp.setExpiresAt(expiry);
		otp.setReason(Reason.FORGOT_PASSWORD);
		otp.setStatus(OtpStatus.ACTIVE);

		String message = "Your OTP for password reset is: " + otpCode;

		// ✅ Step 5: Send Email
		try {
			MailSendIsh.sendInfo(email, "Forgot Password OTP", message);
		} catch (Exception e) {
			logger.error("Failed to send email: ", e);
			errors.put("usernameOrEmail", Messages.FAILED_TO_SEND_EMAIL);
			return errors;
		}

		// ✅ Step 6: Save OTP
		if (!dao.saveEmailOtp(otp)) {
			errors.put("usernameOrEmail", Messages.INTERNAL_OTP_SAVE_ERROR);
		}

		return errors; // empty = success
	}


	/**
	 * Registers a new user using the provided user details and OTP.
	 * 
	 * 1. Encrypts the user's password before storing.
	 * 2. Sets the user status to INACTIVE until OTP verification is complete.
	 * 3. Attempts user registration through the DAO using the OTP.
	 * 
	 * @param user 
	 * @param otp 
	 * @return boolean
	 */
	public Map<String, String> registerUser(User user, String otp) {
		if(logger.isInfoEnabled()) {
			logger.info("Registering user: " + (user != null ? user.getEmail() : "null"));
		}
		
		Map<String, String> result = new HashMap<>();

		if (otp == null || otp.trim().isEmpty()) {
			logger.warn("OTP input is empty for user: " + user.getEmail());
			result.put("error", Messages.OTP_REQUIRED);
			return result;
		}
		
		if (!otp.matches("\\d{6}")) {
			logger.warn("Invalid OTP format for user: " + user.getEmail());
			result.put("error", Messages.INVALID_OTP_INPUT);
			return result;
		}
		
		user.setPassword(EncryptPassword.getCode(user.getPassword()));
		
		boolean success = dao.signUp(user, otp);
		
		if (success) {
			logger.info("User sign-up succeeded in DAO layer.");
			result.put("success", Messages.REGISTRATION_SUCCESSFUL);
		} else {
			logger.error("User sign-up failed in DAO layer.");
			result.put("error", Messages.OTP_INVALID_OR_EXPIRED);
		}

		return result;
	}

	/**
	 * Authenticates a user based on the provided username/email and password.
	 * 
	 * 
	 * @param usernameOrEmail 
	 * @param plainPassword 
	 * @return Map<String, String>
	 */
	public Map<String, String> login(String usernameOrEmail, String plainPassword) {
		Map<String, String> result = new HashMap<>();

		try {
			boolean hasError = false;

			// Check for blank username/email
			if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
				result.put("username", Messages.LOGIN_USERNAME_REQUIRED);
				hasError = true;
			}

			// Check for blank password
			if (plainPassword == null || plainPassword.trim().isEmpty()) {
				result.put("password", Messages.LOGIN_PASSWORD_REQUIRED);
				hasError = true;
			}

			// If either field is missing, return early
			if (hasError) {
				return result;
			}

			User user = usernameOrEmail.contains("@")
					? findByEmail(usernameOrEmail)
							: findByUsername(usernameOrEmail);

			if (user == null) {
				result.put("username", Messages.USER_NOT_EXIST);
				return result;
			}

			String encryptedPassword = EncryptPassword.getCode(plainPassword);
			if (!user.getPassword().equals(encryptedPassword)) {
				result.put("password", Messages.LOGIN_PASSWORD_INCORRECT);
				return result;
			}

			// ✅ Successful login
			result.put("success", Messages.LOGIN_SUCCESS);
			result.put("userId", String.valueOf(user.getUserId()));
			if(logger.isInfoEnabled()) {
				logger.info("Login Successful for User: " +user.getFirstName());
			}
		}catch (Exception e) {
			logger.error("Internal error during login: " + e.getMessage());
			result.put("success", "Login successful.");
		}
		return result;
	}

	/**
	 * Retrieves a user based on the provided username by delegating to the DAO layer.
	 *
	 * @param username 
	 * @return User
	 */
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}

	/**
	 * Retrieves a user based on the provided email by delegating to the DAO layer.
	 *
	 * @param email 
	 * @return User
	 */
	public User findByEmail(String email) {
		return dao.findByEmail(email);
	}

	/**
	 * Handles the forgot password process by validating input fields, checking for reused passwords,
	 * and updating the user's password if the OTP is valid and all conditions are met.
	 *
	 * @param usernameOrEmail 
	 * @param otpInput 
	 * @param newPassword 
	 * @return Map<String, String>
	 */
	public Map<String, String> forgotPassword(String usernameOrEmail, String otpInput, String newPassword, String confirmPassword) {
		Map<String, String> map = new HashMap<>();

		if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
			map.put("GLOBAL", Messages.FORGOT_USERNAME_REQUIRED);
			return map;
		}

		// Required field validation for each field separately
		if (newPassword == null || newPassword.trim().isEmpty()) {
			map.put("newPassword", Messages.NEW_PASSWORD_REQUIRED);
		}

		if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
			map.put("confirmPassword", Messages.CONFIRM_PASSWORD_REQUIRED);
		}


		if (!map.isEmpty()) {
			return map;
		}

		// ✅ Password match check
		if (!newPassword.equals(confirmPassword)) {
			map.put("GLOBAL", Messages.PASSWORD_MISMATCH);
			return map;
		}

		// ✅ Password strength validation
		if (!newPassword.trim().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,18}$")) {
			map.put("GLOBAL", Messages.PASSWORD_INVALID);
			return map;
		}

		// ✅ User existence check
		User user = dao.findUserByUsernameOrEmail(usernameOrEmail.trim());
		if (user == null) {
			map.put("failure", Messages.USER_NOT_EXIST);
			return map;
		}

		List<String> last3 = dao.getPasswordHistory(user.getUserId(), 3);
		String newPasswordHash = EncryptPassword.getCode(newPassword.trim());

		if (last3.contains(newPasswordHash)) {
			map.put("GLOBAL", Messages.PASSWORD_REUSED);
			return map;
		}

		boolean resetDone = dao.forgotPassword(user, otpInput.trim(), newPasswordHash);

		if (resetDone) {
			map.put("success", Messages.PASSWORD_RESET_SUCESS); // Or use a constant if you have one
		} else {
			map.put("failure", Messages.INTERNAL_ERROR); // Fallback message
		}

		return map;
	}

	/**
	 * Retrieves the email address associated with the given username.
	 *
	 * @param username 
	 * @return String
	 */
	public String getEmailByUsername(String username) {
		if (username == null || username.trim().isEmpty()) return null;
		User user = dao.findByUsername(username.trim());
		return user != null ? user.getEmail() : null;
	}

	/**
	 * Retrieves the most recent passwords from the user's password history.
	 *
	 * @param userId 
	 * @param limit 
	 * @return List<String>
	 */
	public List<String> getPasswordHistory(int userId,int limit) {
		return dao.getPasswordHistory(userId, 3);
	}

	/**
	 * Verifies the OTP entered by the user during the Forgot Password process.
	 * If a username is provided instead of an email, it resolves the email internally.
	 *
	 * @param usernameOrEmail 
	 * @param otpCode 
	 * @return boolean
	 */
	public Map<String, String> verifyForgotPasswordOtp(String usernameOrEmail, String otpCode) {

		Map<String, String> errors = new HashMap<>();

		if(logger.isInfoEnabled()) {
			logger.info("Verifying OTP for forgot password for: " + usernameOrEmail);
		}

		// Step 0: Validate inputs
		if ( otpCode == null || !otpCode.matches("^\\d{6}$")) {
			logger.warn("Invalid input for OTP verification.");
			errors.put("otp", Messages.INVALID_OTP_INPUT);
			return errors;
		}

		// Step 1:Resolve email from username if needed
		String email = usernameOrEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
				? usernameOrEmail
						: getEmailByUsername(usernameOrEmail);


		try {
			// Step 2: Fetch latest active OTP
			Otp otp = dao.getLatestActiveOtp(email, Reason.FORGOT_PASSWORD);


			// Step 3: Check if OTP is expired
			if (otp.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
				logger.warn("OTP expired for: " + email);
				errors.put("otp", Messages.OTP_EXPIRED);
				return errors;
			}

			// Step 4: Check if OTP matches
			if (!otp.getOtpCode().equals(otpCode)) {
				logger.warn("Invalid OTP entered for: " + email);
				errors.put("otp", Messages.OTP_INVALID);
				return errors;
			}
			if(logger.isInfoEnabled()) {
				logger.info("OTP verified successfully for: " + email);
			}
		} catch (Exception e) {
			logger.error("Error verifying OTP: ", e);
			errors.put("otp", Messages.INTERNAL_ERROR);
		}
		return errors; // empty = success
	}
}
