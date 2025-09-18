package com.infinite.ejb.admin;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ejb.Remote;
import javax.ejb.Stateless;


/**
 * 
 * -----------------------------------------------------------------------------
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 * -----------------------------------------------------------------------------
 * 
 * @Author  : Sourav Kumar Das
 * @Purpose : This class implements the UserBeanRemote interface and handles 
 *            user-related operations.
 * Session Bean implementation class UserBean
 */
@Stateless
@Remote(UserBeanRemote.class)
public class UserBean implements UserBeanRemote {

	Connection connection;
	PreparedStatement pstm;

	/**
	 * Default constructor. 
	 */
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Registers a new user and stores password in history table.
	 *
	 * @param user 
	 * @return String
	 */
	@Override
	public String SignUp(User user) {
		String sql = "INSERT INTO Admin_User (first_name, last_name, user_name, password, email) " +
				"VALUES (?, ?, ?, ?, ?)";

		String passwordHistorySql = "INSERT INTO Admin_Password_History (user_id, password_hash) " +
				"VALUES (?, ?)";

		try (Connection connection = ConnectionHelper.getConnection();
				PreparedStatement pstm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {


			pstm.setString(1, user.getFirstName());
			pstm.setString(2, user.getLastName());
			pstm.setString(3, user.getUsername());
			pstm.setString(4, user.getPassword());
			pstm.setString(5, user.getEmail());
			

			int rows = pstm.executeUpdate();

			if (rows > 0) {
				// Step 2: Get generated user ID
				try (java.sql.ResultSet generatedKeys = pstm.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int userId = generatedKeys.getInt(1);

						// Step 3: Insert into password_history
						try (PreparedStatement historyPstm = connection.prepareStatement(passwordHistorySql)) {
							historyPstm.setInt(1, userId);
							historyPstm.setString(2, user.getPassword()); // hashed password assumed
							historyPstm.executeUpdate();
						}

						return "User successfully registered.";
					} else {
						return "User registration failed: could not retrieve user ID.";
					}
				}
			} else {
				return "User registration failed.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Exception during registration: " + e.getMessage();
		}
	}
}
