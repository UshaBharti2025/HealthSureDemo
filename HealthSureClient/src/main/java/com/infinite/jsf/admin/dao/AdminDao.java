package com.infinite.jsf.admin.dao;

import java.util.List;


import com.infinite.jsf.admin.model.Otp;
import com.infinite.jsf.admin.model.User;

public interface AdminDao {
	boolean signUp(User user, String otpInput);
	boolean saveEmailOtp(Otp otp);
	public User login(String usernameOrEmail, String password);
	User findByUsername(String username);
	User findByEmail(String email);
	boolean forgotPassword(User user, String otpInput, String newPasswordHash);
	List<String> getPasswordHistory(int userId, int limit);
}
