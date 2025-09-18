package com.infinite.jsf.admin.model;

import java.sql.Timestamp;

public class PasswordHistory {
	private int id;
    private String passwordHash;
    private Timestamp changedAt;
    private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Timestamp getChangedAt() {
		return changedAt;
	}
	public void setChangedAt(Timestamp changedAt) {
		this.changedAt = changedAt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public PasswordHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PasswordHistory(int id, String passwordHash, Timestamp changedAt, User user) {
		super();
		this.id = id;
		this.passwordHash = passwordHash;
		this.changedAt = changedAt;
		this.user = user;
	}
	@Override
	public String toString() {
		return "PasswordHistory [id=" + id + ", passwordHash=" + passwordHash + ", changedAt="
				+ changedAt + ", user=" + user + "]";
	}
       
}
