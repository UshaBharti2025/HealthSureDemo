/*
 * -----------------------------------------------------------------------------
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 * -----------------------------------------------------------------------------
 * 
 * @Author  : 
 * @Purpose : This class implements the AdminDao interface and provides DAO-level 
 *            operations for user management, OTP handling, login/logout, password 
 *            history tracking, and related user verification functionalities. 
 *            It communicates with Hibernate for ORM and interacts with the 
 *            remote EJB bean for signup operations.
 * 
 * -----------------------------------------------------------------------------
 */
package com.infinite.jsf.admin.daoImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;


import com.infinite.ejb.admin.RemoteHelper;
import com.infinite.ejb.admin.UserBeanRemote;
import com.infinite.jsf.admin.dao.AdminDao;
import com.infinite.jsf.admin.model.Otp;
import com.infinite.jsf.admin.model.OtpStatus;
import com.infinite.jsf.admin.model.PasswordHistory;
import com.infinite.jsf.admin.model.Reason;
import com.infinite.jsf.admin.model.User;


import com.infinite.jsf.util.SessionHelper;

public class AdminDaoImpl implements AdminDao {

	/** 
	 * Logger instance for logging important info, warnings, and errors 
	 * during Database operations.
	 */
	private static final Logger logger = Logger.getLogger(AdminDaoImpl.class);

	// Remote EJB reference to interact with the backend UserBean
	static UserBeanRemote remote;

	// Static block to initialize remote EJB reference during class loading though JNDI
	static {
		try {
			remote = RemoteHelper.lookUpRemoteStatelessUserNew();
		} catch (NamingException e) {
			logger.error("NamingException in static block: ", e);
		}
	}

	/**
	 * Handles user registration after validating the OTP.
	 *
	 * @param userInput
	 * @param otpInput
	 * @return boolean
	 */
	@Override
	public boolean signUp(User userInput, String otpInput) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for signUp.");
			}
			logger.info("It is the Singup Method in Daoimpl:");
			if (logger.isInfoEnabled()) {
				logger.info("Validating OTP for email: " + userInput.getEmail());
			}

			Otp latestOtp = getLatestActiveOtp(userInput.getEmail(), Reason.SIGNUP);
			if (latestOtp == null || !latestOtp.getOtpCode().equals(otpInput) || latestOtp.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
				
				logger.warn("Invalid or expired OTP for email: " + userInput.getEmail());
				
				return false;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Saving User to Database.");
			}
			logger.info("Calling EJB for USer Data to be saved:");
			com.infinite.ejb.admin.User a = new com.infinite.ejb.admin.User();
			a.setUserId(userInput.getUserId());
			a.setCreatedAt(userInput.getCreatedAt());
			a.setFirstName(userInput.getFirstName());
			a.setLastName(userInput.getLastName());
			a.setUsername(userInput.getUsername());
			a.setEmail(userInput.getEmail());
			a.setPassword(userInput.getPassword());
			a.setUpdatedAt(userInput.getUpdatedAt());

			String result  = remote.SignUp(a);

			if (logger.isInfoEnabled()) {
				logger.info("User Passed to EJB for Saving: " + userInput.getUsername());
			}			

			transaction = session.beginTransaction();

			deactivateAllOtps(userInput.getEmail(), Reason.SIGNUP);

			transaction.commit();
			if (logger.isDebugEnabled()) {
				logger.debug("OTP deactivated and password history saved.");
			}


			return true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Exception during signUp: ", e);
			return false;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for signUp.");
				}
			}
		}
	}

	/**
	 * Persists a newly generated OTP into the database.
	 *
	 * @param otp
	 * @return boolean
	 */
	@Override
	public boolean saveEmailOtp(Otp otp) {
		Transaction tx = null;
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for saveEmailOtp.");
			}

			tx = session.beginTransaction();
			session.save(otp);
			tx.commit();
			if (logger.isInfoEnabled()) {
				logger.info("OTP saved for email: " + otp.getEmail());
			}
			return true;

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			logger.error("Exception during saveEmailOtp: ", e);
			return false;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for saveEmailOtp.");
				}
			}
		}
	}

	/**
	 * Authenticates the user using username/email and password.
	 *
	 * @param usernameOrEmail
	 * @param password
	 * @return User
	 */
	@Override
	public User login(String usernameOrEmail, String password) {
		Transaction tx = null;
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for login.");
			}
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.or(
							Restrictions.eq("username", usernameOrEmail),
							Restrictions.eq("email", usernameOrEmail)))
					.add(Restrictions.eq("password", password));

			User user = (User) criteria.uniqueResult();

			if (user == null) {
				logger.warn("Invalid login credentials for: " + usernameOrEmail);
				return null;
			}
			tx.commit();

			if (logger.isInfoEnabled()) {
				logger.info("Login successful for user: " + user.getUsername());
			}
			return user;

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			logger.error("Exception during login", e);
			return null;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed after login.");
				}
			}
		}
	}

	/**
	 * Finds and returns a user by their email address.
	 *
	 * @param email
	 * @return User
	 */
	@Override
	public User findByEmail(String email) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for findByEmail: " + email);
			}
			if (logger.isInfoEnabled()) {
				logger.info("Attempting to find user by email: " + email);
			}

			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.eq("email", email));
			User user = (User) criteria.uniqueResult();

			if (logger.isInfoEnabled()) {
				logger.info("User found by email: " + (user != null));
			}

			return user;
		} catch (Exception e) {
			logger.error("Exception in findByEmail: " + email, e);
			return null;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for findByEmail.");
				}
			}
		}
	}

	/**
	 * Finds and returns a user by their username.
	 *
	 * @param username
	 * @return User
	 */
	@Override
	public User findByUsername(String username) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for findByUsername: " + username);
			}
			if (logger.isInfoEnabled()) {
				logger.info("Attempting to find user by username: " + username);
			}

			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.eq("username", username));
			User user = (User) criteria.uniqueResult();

			if (logger.isInfoEnabled()) {
				logger.info("User found by username: " + (user != null));
			}

			return user;
		} catch (Exception e) {
			logger.error("Exception during findByUsername for username: " + username, e);
			return null;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for findByUsername.");
				}
			}
		}
	}

	/**
	 * Resets the password for the user after verifying the OTP.
	 *
	 * @param usernameOrEmail
	 * @param otpInput
	 * @param newPasswordHash
	 * @return boolean
	 */
	@Override
	public boolean forgotPassword(User user, String otpInput, String newPasswordHash) {
		Transaction transaction = null;
		Session session = null;

		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for forgotPassword.");
			}
			
			transaction = session.beginTransaction();

			user.setPassword(newPasswordHash);
			user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			session.update(user);

			if (logger.isInfoEnabled()) {
				logger.info("Password updated for user: " + user.getUsername());
			}

			// Save pwd to pwdhistory table
			PasswordHistory history = new PasswordHistory();
			history.setUser(user);
			history.setPasswordHash(newPasswordHash);
			session.save(history);

			if (logger.isInfoEnabled()) {
				logger.info("Password history record inserted for user: " + user.getUsername());
			}

			deactivateAllOtps(user.getEmail(), Reason.FORGOT_PASSWORD);
			transaction.commit();

			if (logger.isInfoEnabled()) {
				logger.info("Password successfully reset for user: " + user.getUsername());
			}

			return true;

		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Exception during forgotPassword: ", e);
			return false;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for forgotPassword.");
				}
			}
		}
	}


	/**
	 * Fetches the most recent password hashes for a given user.
	 *
	 * @param userId
	 * @param limit
	 * @return List<String>
	 */
	@Override
	public List<String> getPasswordHistory(int userId, int limit) {
		Session session = null;

		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for getPasswordHistory using Criteria.");
			}

			Criteria criteria = session.createCriteria(PasswordHistory.class, "ph")
					.createAlias("ph.user", "user")  // join with User
					.add(Restrictions.eq("user.userId", userId))
					.addOrder(Order.desc("changedAt"))
					.setMaxResults(limit);

			@SuppressWarnings("unchecked")
			List<PasswordHistory> historyList = criteria.list();

			List<String> passwordHashes = new ArrayList<>();
			for (PasswordHistory ph : historyList) {
				passwordHashes.add(ph.getPasswordHash());
			}

			if (logger.isInfoEnabled()) {
				logger.info("Retrieved last " + passwordHashes.size() + " password(s) using Criteria for user ID: " + userId);
			}
			return passwordHashes;

		} catch (Exception e) {
			logger.error("Exception during getPasswordHistory (Criteria): ", e);
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for getPasswordHistory (Criteria).");
				}
			}
		}
	}

	/**
	 * Retrieves the latest active OTP for the specified email and reason.
	 *
	 * @param email
	 * @param reason
	 * @return Otp
	 */
	public Otp getLatestActiveOtp(String email, Reason reason) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			Criteria criteria = session.createCriteria(Otp.class);
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("status", OtpStatus.ACTIVE));
			criteria.add(Restrictions.eq("reason", reason));
			criteria.addOrder(Order.desc("createdAt"));
			criteria.setMaxResults(1);

			Otp otp = (Otp) criteria.uniqueResult();

			if (logger.isDebugEnabled()) {
				logger.debug("Fetched latest active OTP for email: " + email + " -> " + otp);
			}

			return otp;
		} catch (Exception e) {
			logger.error("Exception in getLatestActiveOtp: ", e);
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Marks all active OTPs as expired for a given email and reason.
	 *
	 * @param email
	 * @param reason
	 */
	public void deactivateAllOtps(String email, Reason reason) {
		Transaction tx = null;
		Session session = null;

		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for deactivateAllOtps.");
			}

			tx = session.beginTransaction();

			String hql = "UPDATE Otp o SET o.status = :expiredStatus " +
					"WHERE o.email = :email AND o.reason = :reason AND o.status = :activeStatus";

			int updatedCount = session.createQuery(hql)
					.setParameter("expiredStatus", OtpStatus.EXPIRED)
					.setParameter("activeStatus", OtpStatus.ACTIVE)
					.setParameter("email", email)
					.setParameter("reason", reason)
					.executeUpdate();

			tx.commit();

			if (logger.isInfoEnabled()) {
				logger.info("Deactivated " + updatedCount + " OTP(s) for email: " + email + ", reason: " + reason);
			}

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			logger.error("Failed to deactivate OTPs", e);
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for deactivateAllOtps.");
				}
			}
		}
	}

	/**
	 * Checks if a user exists with the provided email address.
	 *
	 * @param email
	 * @return boolean
	 */
	public boolean existsByEmail(String email) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for existsByEmail.");
			}

			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.eq("email", email));

			boolean exists = !criteria.list().isEmpty();

			if (logger.isInfoEnabled()) {
				logger.info("Email existence check for " + email + ": " + exists);
			}

			return exists;

		} catch (Exception e) {
			logger.error("Error checking email existence", e);
			return false;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for existsByEmail.");
				}
			}
		}
	}


	/**
	 * Checks if a user exists with the provided username.
	 *
	 * @param username
	 * @return boolean
	 */
	public boolean existsByUsername(String username) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for existsByUsername.");
			}

			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.eq("username", username));

			boolean exists = !criteria.list().isEmpty();

			if (logger.isInfoEnabled()) {
				logger.info("Username existence check for " + username + ": " + exists);
			}

			return exists;

		} catch (Exception e) {
			logger.error("Error checking username existence", e);
			return false;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for existsByUsername.");
				}
			}
		}
	}
	/**
	 * Counts the number of OTPs generated for a user since a specific timestamp.
	 *
	 * @param email
	 * @param reason
	 * @param since
	 * @return int
	 */
	public int countOtpRequestsSince(String email, Reason reason, Timestamp since) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for countOtpRequestsSince.");
			}

			Criteria criteria = session.createCriteria(Otp.class)
					.add(Restrictions.eq("email", email))
					.add(Restrictions.eq("reason", reason))
					.add(Restrictions.ge("createdAt", since));
			int count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

			if (logger.isInfoEnabled()) {
				logger.info("OTP request count for " + email + " since " + since + ": " + count);
			}

			return count;
		} catch (Exception e) {
			logger.error("Error in countOtpRequestsSince", e);
			return 0;
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for countOtpRequestsSince.");
				}
			}
		}
	}
	
	/**
	 * Retrieves all OTPs for a given email and reason created after a specified time.
	 *
	 * @param email
	 * @param reason
	 * @param since
	 * @return List<Otp>
	 */
	public List<Otp> getAllOtpByEmailAndReasonSince(String email, Reason reason, Timestamp since) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			if (logger.isDebugEnabled()) {
				logger.debug("Session opened for getAllOtpByEmailAndReasonSince.");
			}

			Criteria criteria = session.createCriteria(Otp.class)
					.add(Restrictions.eq("email", email))
					.add(Restrictions.eq("reason", reason))
					.add(Restrictions.ge("createdAt", since))
					.addOrder(Order.asc("createdAt"));

			List<Otp> results = criteria.list();
			if (logger.isInfoEnabled()) {
				logger.info("Fetched " + results.size() + " OTP(s) for " + email + " and reason " + reason);
			}

			return results;
		} catch (Exception e) {
			logger.error("Error fetching OTPs by email/reason/time", e);
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Session closed for getAllOtpByEmailAndReasonSince.");
				}
			}
		}
	}


	public User findUserByUsernameOrEmail(String input) {
		Session session = null;
		try {
			session = SessionHelper.getConnection().openSession();
			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.or(
							Restrictions.eq("username", input),
							Restrictions.eq("email", input)
							));
			return (User) criteria.uniqueResult();
		} catch (Exception e) {
	
			logger.error("Error in findUserByUsernameOrEmail", e);
			return null;
		} finally {
			if (session != null) session.close();
		}
	}


}
