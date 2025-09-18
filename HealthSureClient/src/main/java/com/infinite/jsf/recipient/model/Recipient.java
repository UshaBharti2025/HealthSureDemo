package com.infinite.jsf.recipient.model;

import java.io.Serializable;
import java.util.Date;
 

 
public class Recipient implements Serializable {
	    private String hId;
	    private String firstName;
	    private String lastName;
	    private String mobile;
	    private String userName;
	    private Gender gender;
	    private Date dob;
	    private String address;
	    private Date createdAt;
	    private Date lastModifiedAt;  // New field
	    private String password;
	    private String email;
	    private Status status;
	    private int loginAttempts;
	    private Date lockedUntil;
	    private Date lastLogin;
	    private Date passwordUpdatedAt;
	    
	    
	    
		public String gethId() {
			return hId;
		}
		public void sethId(String hId) {
			this.hId = hId;
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
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Gender getGender() {
			return gender;
		}
		public void setGender(Gender gender) {
			this.gender = gender;
		}
		public Date getDob() {
			return dob;
		}
		public void setDob(Date dob) {
			this.dob = dob;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		
		public Date getLastModifiedAt() {
			return lastModifiedAt;
		}
		public void setLastModifiedAt(Date lastModifiedAt) {
			this.lastModifiedAt = lastModifiedAt;
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
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		public int getLoginAttempts() {
			return loginAttempts;
		}
		public void setLoginAttempts(int loginAttempts) {
			this.loginAttempts = loginAttempts;
		}
		public Date getLockedUntil() {
			return lockedUntil;
		}
		public void setLockedUntil(Date lockedUntil) {
			this.lockedUntil = lockedUntil;
		}
		public Date getLastLogin() {
			return lastLogin;
		}
		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}
		public Date getPasswordUpdatedAt() {
			return passwordUpdatedAt;
		}
		public void setPasswordUpdatedAt(Date passwordUpdatedAt) {
			this.passwordUpdatedAt = passwordUpdatedAt;
		}

		@Override
		public String toString() {
			return "Recipient [hId=" + hId + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile="
					+ mobile + ", userName=" + userName + ", gender=" + gender + ", dob=" + dob + ", address=" + address
					+ ", createdAt=" + createdAt + ", lastModifiedAt=" + lastModifiedAt + ", password=" + password
					+ ", email=" + email + ", status=" + status + ", loginAttempts=" + loginAttempts + ", lockedUntil="
					+ lockedUntil + ", lastLogin=" + lastLogin + ", passwordUpdatedAt=" + passwordUpdatedAt + "]";
		}
		
}



