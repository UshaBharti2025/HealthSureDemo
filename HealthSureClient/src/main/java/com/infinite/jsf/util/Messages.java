package com.infinite.jsf.util;

public class Messages {

	// Common Errors
	public static final String USER_INPUT_MISSING = "Required input is missing.";
	public static final String INTERNAL_ERROR = "Internal error occurred. Please try again.";
	public static final String INVALID_OTP_FORMAT = "Invalid OTP format.";
	public static final String INVALID_INPUT = "Invalid input.";
	public static final String USER_NOT_EXIST = "User not found with the provided username or email.";

	// Email & Username Validation
	public static final String EMAIL_REQUIRED = "Email is required.";
	public static final String EMAIL_INVALID = "Invalid email format or unapproved domain.";
	public static final String EMAIL_ALREADY_REGISTERED = "This email is already registered.";
	public static final String EMAIL_NOT_FOUND = "Email not found.";

	public static final String USERNAME_REQUIRED = "Username is required.";
	public static final String USERNAME_INVALID = "Username must be 4-20 characters long, start with a letter, and contain only letters, numbers, or underscores.";
	public static final String USERNAME_ALREADY_TAKEN = "This username is already taken.";
	public static final String USERNAME_NOT_FOUND = "Username not found.";

	// Name Validation
	public static final String FIRST_NAME_REQUIRED = "First name is required.";
	public static final String FIRST_NAME_INVALID = "First name must be one or two words, only letters, and no numbers or special characters.";

	public static final String LAST_NAME_REQUIRED = "Last name is required.";
	public static final String LAST_NAME_INVALID = "Last name must contain at least 2 letters and no numbers or special characters.";

	// Password Validation
	public static final String PASSWORD_REQUIRED = "Password is required.";
	public static final String PASSWORD_INVALID = "Password must be at least 12 characters long and include letters, numbers, and at least one special character.";
	public static final String PASSWORD_REUSED = "New password cannot be one of your last 3 passwords.";
	public static final String LOGIN_PASSWORD_INCORRECT = "Password is incorrect.";

	// OTP Related
	public static final String OTP_REQUIRED = "OTP required";
	public static final String INVALID_OTP_INPUT = "Invalid OTP format. Please enter a 6-digit code.";
	public static final String OTP_EXPIRED = "Your OTP has expired. Please request a new one.";
	public static final String OTP_INVALID = "The OTP you entered is incorrect.";
	public static final String OTP_LIMIT_EXCEEDED = "You’ve exceeded the OTP limit. Try again in %d minutes.";
	public static final String OTP_REQUEST_COOLDOWN = "Please wait before requesting another OTP.";
	public static final String FAILED_TO_SEND_EMAIL = "Failed to send OTP email. Please try again.";
	public static final String INTERNAL_OTP_SAVE_ERROR = "Internal error saving OTP. Please try again.";
	public static final String OTP_VERIFIED_SUCCESS = "OTP verified successfully. You can now reset your password.";
	public static final String OTP_INVALID_OR_EXPIRED = "OTP is invalid or has expired.";
	public static final String OTP_VERIFICATION_ERROR = "Error verifying OTP.";
	public static final String OTP_VERIFICATION_INVALID = "Invalid OTP input.";
	public static final String OTP_VERIFICATION_EMAIL_NOT_FOUND = "Email not found for OTP verification.";

	// Signup
	public static final String REGISTRATION_SUCCESSFUL = "Registration successful. Please login.";
	public static final String SIGNUP_INPUT_MISSING = "User or OTP input is missing.";
	public static final String USER_SIGNUP_FAILED = "User sign-up failed.";

	// Login
	public static final String LOGIN_USERNAME_REQUIRED = "Username or email is required.";
	public static final String LOGIN_PASSWORD_REQUIRED = "Password is required.";
	public static final String LOGIN_INTERNAL_ERROR = "Internal error activating user.";
	public static final String LOGIN_SUCCESS = "Login successful.";

	// Forgot Password
	public static final String FORGOT_REQUIRED_FIELDS_MISSING = "Username or Email Required";
	public static final String PASSWORD_RESET_SUCESS= "Password reset successful.";
	public static final String PASSWORD_MISMATCH = "New password and confirm password do not match.";
	public static final String FORGOT_USERNAME_REQUIRED = "Password reset not possible without username or email.";

	public static final String NEW_PASSWORD_REQUIRED = "New Password is required.";
	public static final String CONFIRM_PASSWORD_REQUIRED = "Confirm Password is required.";

	private Messages() {} // Prevent instantiation
}
