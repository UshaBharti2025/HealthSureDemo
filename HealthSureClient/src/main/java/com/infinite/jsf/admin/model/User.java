package com.infinite.jsf.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

public class User implements Serializable {
	private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    
    private Set<Otp> otp;
    private Set<PasswordHistory> passwordHistory;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Set<Otp> getOtp() {
		return otp;
	}
	public void setOtp(Set<Otp> otp) {
		this.otp = otp;
	}
	public Set<PasswordHistory> getPasswordHistory() {
		return passwordHistory;
	}
	public void setPasswordHistory(Set<PasswordHistory> passwordHistory) {
		this.passwordHistory = passwordHistory;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int userId, String firstName, String lastName, String username, String password, String email,
			 Timestamp createdAt, Timestamp updatedAt, Set<Otp> otp,
			Set<PasswordHistory> passwordHistory) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.otp = otp;
		this.passwordHistory = passwordHistory;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", password=" + password + ", email=" + email + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", otp=" + otp + ", passwordHistory=" + passwordHistory + "]";
	}
	
}