/*
 * -----------------------------------------------------------------------------
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 * -----------------------------------------------------------------------------
 * 
 * @Author  : Infinite Computer Solution
 * @Purpose : This class handles UI-layer interactions and business coordination 
 *            for user-related workflows including registration, OTP verification, 
 *            login, logout, and password reset. It communicates with the 
 *            AdminService layer to invoke validations. It also manages view state, error messages, 
 *            and navigation flows for the JSF frontend.
 * 
 * -----------------------------------------------------------------------------
 */
package com.infinite.jsf.admin.controller;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.infinite.jsf.admin.model.Otp;
import com.infinite.jsf.admin.model.User;

import com.infinite.jsf.admin.service.AdminService;
import com.infinite.jsf.util.Messages;

public class AdminController {

	//Logger Instance for Controller
	private static final Logger logger = Logger.getLogger(AdminController.class);

	private User user;
	private User loginUser;
	private String otp;
	private String newPassword;
	private String confirmPassword;
	private String usernameOrEmail;
	private String tempPassword;
	private AdminService service = new AdminService();
	private boolean otpSent = false;

	private boolean otpResendBlocked = false;

	private Long lastForgotOtpTimestamp = 0L;
	private Integer forgotResendAttempts = 0;
	private Long forgotOtpBlockedUntil = 0L;
	
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public boolean isOtpResendBlocked() {
		return otpResendBlocked;
	}
	public void setOtpResendBlocked(boolean otpResendBlocked) {
		this.otpResendBlocked = otpResendBlocked;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isOtpSent() {
		return otpSent;
	}

	public void setOtpSent(boolean otpSent) {
		this.otpSent = otpSent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Long getLastOtpTimestamp() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (user != null && user.getEmail() != null) {
			return (Long) session.getAttribute(user.getEmail() + "lastOtpTimestamp");
		}
		return 0L;
	}

	public void setLastOtpTimestamp(Long lastOtpTimestamp) {

	}
	public Integer getResendAttempts() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (user != null && user.getEmail() != null) {
			return (Integer) session.getAttribute(user.getEmail() + "resendAttempts");
		}
		return 0;
	}
	public void setResendAttempts(Integer resendAttempts) {

	}

	public Long getOtpBlockedUntil() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (user != null && user.getEmail() != null) {
			return (Long) session.getAttribute(user.getEmail() + "otpBlockedUntil");
		}
		return 0L;
	}
	public void setOtpBlockedUntil(Long otpBlockedUntil) {

	}

	// Forgot Password specific OTP tracking
	public Long getLastForgotOtpTimestamp() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
	            .getExternalContext().getSession(false);
	    if (usernameOrEmail != null) {
	        Long timestamp = (Long) session.getAttribute(usernameOrEmail + "forgotLastOtpTimestamp");
	        return timestamp != null ? timestamp : 0L;
	    }
	    return 0L;
	}

	public void setLastForgotOtpTimestamp(Long lastForgotOtpTimestamp) {
		this.lastForgotOtpTimestamp = lastForgotOtpTimestamp;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (usernameOrEmail != null) {
			session.setAttribute(usernameOrEmail + "forgotLastOtpTimestamp", lastForgotOtpTimestamp);
		}
	}

	public Integer getForgotResendAttempts() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
	            .getExternalContext().getSession(false);
	    if (usernameOrEmail != null) {
	        Integer attempts = (Integer) session.getAttribute(usernameOrEmail + "forgotResendAttempts");
	        return attempts != null ? attempts : 0;
	    }
	    return 0;
	}

	public void setForgotResendAttempts(Integer forgotResendAttempts) {
		 this.forgotResendAttempts = forgotResendAttempts;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (usernameOrEmail != null) {
            session.setAttribute(usernameOrEmail + "forgotResendAttempts", forgotResendAttempts);
        }
	}

	public Long getForgotOtpBlockedUntil() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
	            .getExternalContext().getSession(false);
	    if (usernameOrEmail != null) {
	        Long blocked = (Long) session.getAttribute(usernameOrEmail + "forgotOtpBlockedUntil");
	        return blocked != null ? blocked : 0L;
	    }
	    return 0L;
	}

	public void setForgotOtpBlockedUntil(Long forgotOtpBlockedUntil) {
		this.forgotOtpBlockedUntil = forgotOtpBlockedUntil;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		 if (usernameOrEmail != null) {
	            session.setAttribute(usernameOrEmail + "forgotOtpBlockedUntil", forgotOtpBlockedUntil);
	        }
	}

	/**
	 * Sends an OTP to the user's email after validating input during signup.
	 *
	 * @return String outcome of the operation
	 */
	public String sendOtpToEmail() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		// Check if blocked - MODIFIED to check user-specific block
		Long blockedUntil = (Long) session.getAttribute(user.getEmail() + "otpBlockedUntil");
		if (blockedUntil != null && blockedUntil > System.currentTimeMillis()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"You've exceeded OTP limits. Try again later.", null));
			return null;
		}

		// Validate user input
		tempPassword = user.getPassword();
		Map<String, String> validationErrors = service.validateUserInputFields(user);
		if (!validationErrors.isEmpty()) {
			for (Map.Entry<String, String> entry : validationErrors.entrySet()) {
				FacesContext.getCurrentInstance().addMessage(entry.getKey(),
						new FacesMessage(FacesMessage.SEVERITY_WARN, entry.getValue(), null));
			}
			return null;
		}

		// Store user in session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tempUser", user);

		// Send OTP
		Map<String, String> result = service.sendEmailOtp(user.getEmail(), user.getUsername());

		if (result.containsKey("otp")) {
			// Block for 30 minutes if limit exceeded
			long blockTime = System.currentTimeMillis() + (30 * 60 * 1000);
			session.setAttribute("otpBlockedUntil", blockTime);
			session.setAttribute("resendAttempts", 0);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, result.get("otp"), null));
			return null;
		}

		if (result.isEmpty()) {
			// Successful OTP send - DON'T count this as attempt
			session.setAttribute(user.getEmail() + "lastOtpTimestamp", System.currentTimeMillis());
			// Initialize attempts to 0 for first send
			session.setAttribute(user.getEmail() + "resendAttempts", 0); 

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP has been sent to your email.", null));
			otpSent = true;
		} else {
			// Other errors
			for (Map.Entry<String, String> entry : result.entrySet()) {
				FacesContext.getCurrentInstance().addMessage(entry.getKey(),
						new FacesMessage(FacesMessage.SEVERITY_ERROR, entry.getValue(), null));
			}
		}

		return null;
	}


	/**
	 *  Handles the resend OTP operation during user signup.
	 *
	 * @return String
	 */
	public String resendOtp() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		// Get user from session
		User sessionUser = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("tempUser");

		if (sessionUser == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Session expired. Please sign up again.", null));
			return "Signup.jsp?faces-redirect=true";
		}

		// Increment and check resend attempts
		Integer attempts = (Integer) session.getAttribute(sessionUser.getEmail() + "resendAttempts");
		attempts = attempts == null ? 1 : attempts + 1;

		Long blockedUntil = (Long) session.getAttribute(sessionUser.getEmail() + "otpBlockedUntil");
		if (blockedUntil != null && blockedUntil > System.currentTimeMillis()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"You've exceeded OTP limits. Try again later.", null));
			return null;
		}

		// Block for 30 minutes if max attempts reached
		if (attempts >= 5) {
			long blockTime = System.currentTimeMillis() + (30 * 60 * 1000);
			session.setAttribute(sessionUser.getEmail() + "otpBlockedUntil", blockTime);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"You've exceeded OTP limits. Try again in 30 minutes.", null));
			return null;
		}

		session.setAttribute(sessionUser.getEmail() + "resendAttempts", attempts);


		// Resend OTP
		Map<String, String> result = service.sendEmailOtp(sessionUser.getEmail(), 
				sessionUser.getUsername());

		if (result.isEmpty()) {
			// Update last OTP timestamp on successful resend
			session.setAttribute(sessionUser.getEmail() + "lastOtpTimestamp", System.currentTimeMillis());

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, 
							"OTP resent successfully.", null));
		} else {
			// Handle errors
			for (Map.Entry<String, String> entry : result.entrySet()) {
				FacesContext.getCurrentInstance().addMessage(entry.getKey(),
						new FacesMessage(FacesMessage.SEVERITY_WARN, entry.getValue(), null));
			}
		}

		return null;
	}

	/**
	 * Verifies OTP and registers the user during signup.
	 *
	 * @return String navigation outcome based on registration success
	 */
	public String verifyOtpAndSignUp() {
		user.setPassword(tempPassword);

		FacesContext context = FacesContext.getCurrentInstance();
		User sessionUser = (User) context.getExternalContext().getSessionMap().get("tempUser");

		if (sessionUser == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Session expired. Please sign up again.", null));
			return "Signup.jsp?faces-redirect=true";
		}

		this.user = sessionUser;
		logger.info("Attempting to register user: " + user.getEmail());

		Map<String, String> registered = service.registerUser(user, otp);

		if (registered.containsKey("success")) {

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			session.removeAttribute(user.getEmail() + "resendAttempts");
			session.removeAttribute(user.getEmail() + "otpBlockedUntil");
			session.removeAttribute(user.getEmail() + "lastOtpTimestamp");

			logger.info("User registered successfully: " + user.getEmail());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, registered.get("success"), null));

			context.getExternalContext().getSessionMap().remove("tempUser");

			// Reset fields
			user = new User(); 
			otp = null; 
			otpSent = false;
			return "Profile.jsp?faces-redirect=true"; 
		} else {
			String errorMessage = registered.get("error");
			logger.warn("User registration failed for: " + user.getEmail() + " - " + errorMessage);
			FacesContext.getCurrentInstance().addMessage("otp",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
		
		}

		return null;
	}

	/**
	 * Handles user login using username/email and password.
	 * Validates credentials and redirects to dashboard on success.
	 *
	 * @return String 
	 */
	public String login() {
		logger.info("Login attempt for username/email: " + loginUser.getUsername());

		Map<String, String> result = service.login(loginUser.getUsername(), loginUser.getPassword());

		if (result.containsKey("success")) {

			User loggedInUser = loginUser.getUsername().contains("@")
					? service.findByEmail(loginUser.getUsername())
							: service.findByUsername(loginUser.getUsername());

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", loggedInUser);

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("loginSuccess", "Welcome " + loggedInUser.getFirstName());

			logger.info("Login successful for user: " + loggedInUser.getUsername());

			loginUser = new User();
			otp = null;
			otpSent = false;

			return "AdminDashBoard.jsp?faces-redirect=true";
		}
		else {

			for (Map.Entry<String, String> entry : result.entrySet()) {
				String key = entry.getKey();
				String msg = entry.getValue();

				if ("username".equals(key) || "email".equals(key)) {
					// Attach all errors including generic ones to the username/email input
					FacesContext.getCurrentInstance().addMessage("username", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
				} else if ("password".equals(key)) {
					FacesContext.getCurrentInstance().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
				}
			}
		}
		logger.warn("Login failed for input: " + loginUser.getUsername());
		return null;
	}

	/**
	 * Handles user logout by clearing session and user data.
	 *
	 * @return String navigation outcome based on logout result
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/home/Home.jsf?faces-redirect=true";
	}

	/**
	 * Sends OTP to user for forgot password flow after validating input.
	 *
	 * @return String navigation outcome or null if OTP sending fails
	 */
	public String sendOtpForForgotPassword() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		// Check if blocked first
		Long blockedUntil = (Long) session.getAttribute(usernameOrEmail + "forgotOtpBlockedUntil");
		if (blockedUntil != null && blockedUntil > System.currentTimeMillis()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"You've exceeded OTP limits. Try again later.", null));
			return null;
		}

		if(logger.isInfoEnabled()) {
			logger.info("Attempting password reset for " + usernameOrEmail);
		}
		Map<String, String> Forgot_Otp = service.sendForgotPasswordOtp(usernameOrEmail);

		// OTP block logic (e.g., too many resends)
		if (Forgot_Otp.containsKey("otp")) {
			// Block for 30 minutes
			long blockTime = System.currentTimeMillis() + (30 * 60 * 1000);
			session.setAttribute(usernameOrEmail + "forgotOtpBlockedUntil", blockTime);
			session.setAttribute(usernameOrEmail + "forgotResendAttempts", 0);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, Forgot_Otp.get("otp"), null));
			return null;
		}

		// Handle resend attempts
		boolean isResend = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().containsKey("action");

		if (isResend) {
			Integer attempts = (Integer) session.getAttribute(usernameOrEmail + "forgotResendAttempts");
			attempts = attempts == null ? 1 : attempts + 1;
			session.setAttribute(usernameOrEmail + "forgotResendAttempts", attempts);

			if (attempts >= 5) {
				long blockTime = System.currentTimeMillis() + (30 * 60 * 1000);
				session.setAttribute(usernameOrEmail + "forgotOtpBlockedUntil", blockTime);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"You've exceeded OTP limits. Try again in 30 minutes.", null));
				return null;
			}
		} else {
			// Initial send - reset counters
			session.setAttribute(usernameOrEmail + "forgotResendAttempts", 0);
			session.removeAttribute(usernameOrEmail + "forgotOtpBlockedUntil");
		}

		if (Forgot_Otp.isEmpty()) {
			// Update last OTP timestamp
			long currentTime = System.currentTimeMillis();
			session.setAttribute(usernameOrEmail + "forgotLastOtpTimestamp", currentTime);

			this.setLastForgotOtpTimestamp(currentTime);
			otpSent = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP has been sent to your email.", null));
			if(logger.isInfoEnabled()) {
				logger.info("OTP successfully sent to: " + usernameOrEmail);
			}
		} else {
			logger.warn("Validation error(s) during Forgot Password OTP: " + Forgot_Otp);
			for (Map.Entry<String, String> entry : Forgot_Otp.entrySet()) {
				FacesContext.getCurrentInstance().addMessage(entry.getKey(),
						new FacesMessage(FacesMessage.SEVERITY_ERROR, entry.getValue(), null));
			}
		}

		return null;
	}

	/**
	 * Verifies the OTP entered by the user during forgot password flow.
	 *
	 * @return String navigation outcome to reset page if OTP is valid, else null
	 */
	public String verifyOtpForForgotPassword() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		Map<String, String> errors = service.verifyForgotPasswordOtp(usernameOrEmail,otp);

		if (errors.isEmpty()) {

			// Clear OTP counters on successful verification
			if (usernameOrEmail != null) {
				session.removeAttribute(usernameOrEmail + "forgotLastOtpTimestamp");
				session.removeAttribute(usernameOrEmail + "forgotResendAttempts");
				session.removeAttribute(usernameOrEmail + "forgotOtpBlockedUntil");
			}

			// Store verified email in session for password reset
			session.setAttribute("resetEmail", usernameOrEmail);
			// ✅ OTP verified
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, Messages.OTP_VERIFIED_SUCCESS, null));

			// Redirect to reset password page
			return "ResetPassword.jsp?faces-redirect=true";
		} else {
			// ❌ Show all error messages from the service
			for (String msg : errors.values()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
			}
			return null;
		}
	}

	/**
	 * Handles password reset after successful OTP verification during forgot password flow.
	 *
	 * @return String navigation outcome to login page on success, else null
	 */
	public String resetPasswordAfterOtp() {
		if(logger.isInfoEnabled()) {
			logger.info("Attempting password reset for: " + usernameOrEmail);
		}
		Map<String, String> result = service.forgotPassword(usernameOrEmail, otp, newPassword, confirmPassword);

		if (result.containsKey("success")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, result.get("success"), null));

			// Clear fields
			usernameOrEmail = null;
			otp = null;
			newPassword = null;
			confirmPassword = null;
			user = new User();
			otpSent = false;
			
			return null;
//			return "Login.jsp?faces-redirect=true";
		}

		for (Map.Entry<String, String> entry : result.entrySet()) {
			String clientId = entry.getKey();
			String messageText = entry.getValue();

			if (clientId == null || "GLOBAL".equalsIgnoreCase(clientId)) {
				// Global message
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, null)
						);
			} else {
				// Field-specific message
				FacesContext.getCurrentInstance().addMessage(
						clientId,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, null)
						);
			}
		}
		return null;
	}
	public String Signupback() {
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("otp");
	     
	    
		return"Profile.jsp?faces-redirect=true";
	}
}