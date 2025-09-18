/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */
/**
 * This package contains the implementation classes for DAO (Data Access Object)
 * related to the insurance module 
 */
package com.infinite.jsf.insurance.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.QueryTimeoutException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;

import com.infinite.jsf.insurance.dao.InsuranceCoverageOptionDao;
import com.infinite.jsf.insurance.exception.InsuranceCoverageOptionException;
import com.infinite.jsf.insurance.model.CoveragePlanStatus;
import com.infinite.jsf.insurance.model.InsuranceCoverageOption;
import com.infinite.jsf.util.SessionHelper;

/**
 * This class provides the implementation of the InsuranceCoverageOptionDao
 * interface. It handles CRUD operations for InsuranceCoverageOption entities
 * using Hibernate ORM. Provides methods to perform database operations on
 * InsuranceCoverageOption entities.
 */
public class InsuranceCoverageOptionDaoImpl implements InsuranceCoverageOptionDao {

	private static final SessionFactory factory = SessionHelper.getConnection();
	private static final Logger logger = Logger.getLogger(InsuranceCoverageOptionDaoImpl.class);

	/**
	 * Adds a new InsuranceCoverageOption to the database.
	 *
	 * @param coverageOption the InsuranceCoverageOption object to be added
	 * @return "success" if the operation is successful
	 */
	@Override
	public String addCoveragePlan(InsuranceCoverageOption coverageOption) throws InsuranceCoverageOptionException {
		Session session = null;
		Transaction trans = null;
		String coverageId = generateNextInsuranceCoverageOptionId();
		coverageOption.setCoverageId(coverageId);
		coverageOption.setStatus(CoveragePlanStatus.INACTIVE);
		logger.info("Generated Coverage ID: " + coverageId);

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			session.save(coverageOption);
			trans.commit();
			logger.info("Coverage option saved successfully with ID: " + coverageId);
			return "success";

		} catch (ConstraintViolationException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Constraint violation while saving coverage option", e);
			throw new InsuranceCoverageOptionException("Constraint violation", e);

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while saving coverage option", e);
			throw new InsuranceCoverageOptionException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while saving coverage option", e);
			throw new InsuranceCoverageOptionException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Generates the next coverage ID in the format COV###.
	 *
	 * @return the next generated coverage ID
	 */

	public String generateNextInsuranceCoverageOptionId() throws InsuranceCoverageOptionException {
		Session session = null;
		String lastId = null;

		try {
			session = factory.openSession();
			lastId = (String) session
					.createQuery("SELECT c.coverageId FROM InsuranceCoverageOption c ORDER BY c.coverageId DESC")
					.setMaxResults(1).uniqueResult();
			logger.debug("Last coverage ID fetched: " + lastId);

		} catch (HibernateException e) {
			logger.error("Hibernate error while fetching last coverage ID", e);
			throw new InsuranceCoverageOptionException("Hibernate error", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching last coverage ID", e);
			throw new InsuranceCoverageOptionException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}

		int nextNum = 1;
		try {
			if (lastId != null && lastId.toUpperCase().startsWith("COV") && lastId.length() == 6) {
				String numPart = lastId.substring(3); // e.g., "001"
				if (numPart.matches("\\d{3}")) {
					nextNum = Integer.parseInt(numPart) + 1;
				}
			}
		} catch (NumberFormatException e) {
			logger.error("Error parsing numeric part of coverage ID", e);
			throw new InsuranceCoverageOptionException("Invalid numeric format in last coverage ID", e);
		}

		String nextId = String.format("COV%03d", nextNum);
		logger.debug("Next generated coverage ID: " + nextId);
		return nextId;
	}

	/**
	 * Retrieves all InsuranceCoverageOption records from the database.
	 *
	 * @return list of InsuranceCoverageOption objects
	 */

	/**
	 * Retrieves all InsuranceCoverageOption records by plan ID.
	 *
	 * @param planId the plan ID to filter by
	 * @return list of InsuranceCoverageOption objects
	 * @throws InsuranceCoverageOptionException
	 */
	@Override
	public List<InsuranceCoverageOption> findAllInsuranceCoverageOptionsByPlanId(String planId)
			throws InsuranceCoverageOptionException {
		List<InsuranceCoverageOption> coverageOptionsList = null;
		Session session = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			String hql = "FROM InsuranceCoverageOption c WHERE c.insurancePlan.planId = :planId";
			Query query = session.createQuery(hql);
			query.setParameter("planId", planId);
			coverageOptionsList = query.list();
			trans.commit();
			logger.info("Fetched coverage options for plan ID: " + planId);
		} catch (HibernateException e) {
			logger.error("Hibernate error while fetching last coverage ID", e);
			throw new InsuranceCoverageOptionException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected connection-related error", e);
		} finally {
			if (session != null)
				session.close();
		}

		return coverageOptionsList;
	}

	/**
	 * Finds an InsuranceCoverageOption by its ID. (Currently not implemented)
	 *
	 * @param coverageId the coverage ID
	 * @return InsuranceCoverageOption object if found, otherwise null
	 */
	@Override
	public InsuranceCoverageOption findInsuranceCoverageById(String coverageId) {
		logger.warn("findInsuranceCoverageById method is not yet implemented.");
		return null;
	}

	/**
	 * Updates an existing InsuranceCoverageOption in the database.
	 *
	 * @param coverageOption the InsuranceCoverageOption object with updated data
	 * @return "updated" if successful
	 * @throws InsuranceCoverageOptionException
	 */
	@Override
	public String updateInsuranceCoverageOption(InsuranceCoverageOption coverageOption)
			throws InsuranceCoverageOptionException {
		Session session = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			session.update(coverageOption);
			trans.commit();
			logger.info("Coverage option updated successfully for ID: " + coverageOption.getCoverageId());
			return "updated";
		} catch (IllegalArgumentException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Update failed: The provided InsuranceCoverageOption object is null or invalid.", e);
			throw new InsuranceCoverageOptionException("Invalid input", e);
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Update failed: A general Hibernate error occurred. Check database connectivity and mappings.",
					e);
			throw new InsuranceCoverageOptionException("Hibernate error", e);
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Update failed: An unexpected error occurred while updating the coverage option.", e);
			throw new InsuranceCoverageOptionException("Unexpected error", e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<InsuranceCoverageOption> findAllInsuranceCoverageOptions() throws InsuranceCoverageOptionException {
		List<InsuranceCoverageOption> options = null;
		Session session = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			options = session.createQuery("from InsuranceCoverageOption").list();
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error occurred while fetching coverage options", e);
			throw new InsuranceCoverageOptionException("Hibernate error", e);
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error occurred while fetching coverage options", e);
			throw new InsuranceCoverageOptionException("Unexpected error", e);
		} finally {
			if (session != null)
				session.close();
		}

		return options;
	}

}
