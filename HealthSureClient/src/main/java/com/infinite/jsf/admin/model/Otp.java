package com.infinite.jsf.admin.model;

import java.sql.Timestamp;

public class Otp {
	private int otpId;
	private String email;
	private String otpCode;
	private OtpStatus status;
	private Reason reason;
	private Timestamp createdAt;
	private Timestamp expiresAt;
	public int getOtpId() {
		return otpId;
	}
	public void setOtpId(int otpId) {
		this.otpId = otpId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtpCode() {
		return otpCode;
	}
	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
	public OtpStatus getStatus() {
		return status;
	}
	public void setStatus(OtpStatus status) {
		this.status = status;
	}
	public Reason getReason() {
		return reason;
	}
	public void setReason(Reason reason) {
		this.reason = reason;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Otp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Otp(int otpId, String email, String otpCode, OtpStatus status, Reason reason, Timestamp createdAt,
			Timestamp expiresAt) {
		super();
		this.otpId = otpId;
		this.email = email;
		this.otpCode = otpCode;
		this.status = status;
		this.reason = reason;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
	@Override
	public String toString() {
		return "Otp [otpId=" + otpId + ", email=" + email + ", otpCode=" + otpCode + ", status=" + status + ", reason="
				+ reason + ", createdAt=" + createdAt + ", expiresAt=" + expiresAt + "]";
	}
	
}