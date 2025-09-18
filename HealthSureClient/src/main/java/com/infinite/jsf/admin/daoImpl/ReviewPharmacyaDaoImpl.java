/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains DAO implementation classes for performing database operations
 * related to administrative features such as pharmacy review and approval.
 */
package com.infinite.jsf.admin.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;

import com.infinite.jsf.admin.dao.ReviewPharmacyaDao;
import com.infinite.jsf.admin.exception.ReviewPharmacyException;
import com.infinite.jsf.pharmacy.model.Pharmacy;
import com.infinite.jsf.util.MailSend;
import com.infinite.jsf.util.SessionHelper;

/**
 * Implementation of ReviewPharmacyaDao interface for pharmacy review
 * operations. This class handles fetching pharmacy details, searching by ID,
 * and updating pharmacy status. Hibernate is used for ORM and Log4j for
 * logging.
 */
public class ReviewPharmacyaDaoImpl implements ReviewPharmacyaDao {

	static SessionFactory factory;
	static Session session;
	private static final Logger logger = Logger.getLogger(ReviewPharmacyaDaoImpl.class);

	static {
		factory = SessionHelper.getConnection();
	}

	/**
	 * Fetches all pharmacy records for review.
	 *
	 * @return List of Pharmacy objects
	 * @throws ReviewPharmacyException
	 */
	@Override
	public List<Pharmacy> reviewPharmacyDetails() throws ReviewPharmacyException {
		List<Pharmacy> pharmacies = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();

			Query query = session.createQuery("from Pharmacy");
			pharmacies = query.list();

			trans.commit();

			if (logger.isInfoEnabled()) {
				logger.info("Fetched " + pharmacies.size() + " pharmacies for review.");
			}
		} catch (JDBCConnectionException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Database connection error while fetching all Pharamcy", e);
			throw new ReviewPharmacyException("Database connection error", e);

		} catch (SQLGrammarException e) {
			if (trans != null)
				trans.rollback();
			logger.error("SQL syntax error while fetching all Pharmacy", e);
			throw new ReviewPharmacyException("SQL syntax error", e);

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while fetching all plans", e);
			throw new ReviewPharmacyException("Hibernate error", e);
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();

			logger.error("Error fetching pharmacy details", e);

		} finally {
			if (session != null)
				session.close();
		}

		return pharmacies;
	}

	/**
	 * Searches for a pharmacy by its ID.
	 *
	 * @param pharmacyId the ID of the pharmacy
	 * @return Pharmacy object if found
	 */
	@Override
	public Pharmacy searchPharmacyById(String pharmacyId) {
		Pharmacy pharmacy = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction()

			pharmacy = (Pharmacy) session.get(Pharmacy.class, pharmacyId);

			trans.commit();

			if (logger.isDebugEnabled()) {
				logger.debug("Pharmacy found with ID: " + pharmacyId);
			}
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();

			logger.error("Error searching pharmacy by ID: " + pharmacyId, e);

		} finally {
			if (session != null)
				session.close();
		}

		return pharmacy;
	}

	/**
	 * Updates the status of a pharmacy and sends a notification email.
	 *
	 * @param pharmacy the Pharmacy object to update
	 * @param status   the new status to set (e.g., ACCEPTED or REJECTED)
	 * @return "updated" if successful
	 * @throws ReviewPharmacyException
	 */
	@Override
	public String updatePharmacyStatus(Pharmacy pharmacy, String status) throws ReviewPharmacyException {
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();

			String pharmacyId = pharmacy.getPharmacyId();
			pharmacy.setStatus(status);
			session.update(pharmacy);

			String email = pharmacy.getEmail();
			MailSend.sendInfo(email, "PHARMACY STATUS : ACCETPED",
					"\n Congratulations !! \n Your pharmacy  has been ACCPTED \n\n ADMIN : \n RAVIKANT TURI");

			trans.commit();

			if (logger.isInfoEnabled()) {
				logger.info("Pharmacy status updated to " + status + " for ID: " + pharmacyId);
			}

			return "updated";

		} catch (SQLGrammarException e) {
			if (trans != null)
				trans.rollback();
			logger.error("SQL syntax error", e);
			throw new ReviewPharmacyException("SQL error in query  while fetching all Pharamcy", e);
		}

		catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while fetching all Pharamcy", e);
			throw new ReviewPharmacyException("Hibernate error", e);
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();

			logger.error("Error updating pharmacy status for ID: " + pharmacy.getPharmacyId(), e);

		} finally {
			if (session != null)
				session.close();
		}

		return "error";
	}
}
