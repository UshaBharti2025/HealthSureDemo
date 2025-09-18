
/**
 *  Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains the implementation classes for DAO (Data Access Object)
 * related to the insurance module 
 */
package com.infinite.jsf.insurance.daoImpl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;

import com.infinite.jsf.insurance.dao.InsurancePlanDao;
import com.infinite.jsf.insurance.exception.InsurancePlanException;
import com.infinite.jsf.insurance.model.InsurancePlan;
import com.infinite.jsf.util.SessionHelper;

import jakarta.validation.ConstraintViolationException;

/**
 *
 * This class provides the implementation of the InsurancePlanDao interface. It
 * handles CRUD operations for InsurancePlan entities using Hibernate ORM.
 * Operations include adding, updating, retrieving by ID, and listing all plans.
 *
 */
public class InsurancePlanDaoImpl implements InsurancePlanDao {

	private static final SessionFactory factory = SessionHelper.getConnection();
	private static final Logger logger = Logger.getLogger(InsurancePlanDaoImpl.class);

	/**
	 * Adds a new InsurancePlan to the database.
	 *
	 * @param insurancePlan the InsurancePlan object to be added
	 * @return the generated plan ID if successful, otherwise null
	 * @throws InsurancePlanExcetping
	 * @throws HibernateException
	 * @throws InsurancePlanExcetpiong
	 */
	@Override
	public String addInsurancePlan(InsurancePlan insurancePlan) throws InsurancePlanException {
		String planId = generateNextPlanId();
		insurancePlan.setPlanId(planId);
		insurancePlan.setActiveOn(new Date());
		Session session = null;
		Transaction trans = null;
		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			session.save(insurancePlan);
			trans.commit();
			logger.info("Plan is save with this planId :" + planId);
			return planId;
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error: " + e.getMessage(), e);
			throw new InsurancePlanException("Hibernate error: plan not saved", e);
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while saving plan: " + e.getMessage(), e);
			throw new InsurancePlanException("Unexpected error: plan not saved", e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Generates the next plan ID in the format PLA###.
	 *
	 * @return the next generated plan ID
	 */
	public String generateNextPlanId() throws InsurancePlanException {
		Session session = null;
		String lastId = null;

		try {
			session = factory.openSession();
			lastId = (String) session.createQuery("SELECT p.planId FROM InsurancePlan p ORDER BY p.planId DESC")
					.setMaxResults(1).uniqueResult();
			logger.debug("Last plan ID fetched: " + lastId);

		} catch (HibernateException e) {
			logger.error("Hibernate error while generating plan ID", e);
			throw new InsurancePlanException("Hibernate error", e);

		} catch (Exception e) {
			logger.error("Unexpected error while generating plan ID", e);
			throw new InsurancePlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}

		int nextNum = 1;
		try {
			if (lastId != null && lastId.toUpperCase().startsWith("PLA") && lastId.length() == 6) {
				String numPart = lastId.substring(3); // e.g., "001"
				if (numPart.matches("\\d{3}")) {
					nextNum = Integer.parseInt(numPart) + 1;
				}
			}
		} catch (NumberFormatException e) {
			logger.error("Error parsing numeric part of plan ID", e);
			throw new InsurancePlanException("Invalid numeric format in last plan ID", e);
		}

		String nextId = String.format("PLA%03d", nextNum);
		logger.debug("Next generated plan ID: " + nextId);
		return nextId;
	}

	/**
	 * Finds an InsurancePlan by its ID.
	 *
	 * @param planId the ID of the insurance plan
	 * @return the InsurancePlan object if found, otherwise null
	 */
	@Override
	public InsurancePlan findInsuranceById(String planId) throws InsurancePlanException {
		Session session = null;
		Transaction trans = null;
		InsurancePlan plan = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			plan = (InsurancePlan) session.get(InsurancePlan.class, planId);
			trans.commit();
			logger.info("Insurance plan fetched for ID: " + planId);

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while fetching plan by ID", e);
			throw new InsurancePlanException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while fetching plan by ID", e);
			throw new InsurancePlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}

		return plan;
	}

	/**
	 * Retrieves all insurance plans from the database.
	 *
	 * @return a list of InsurancePlan objects
	 */
	@Override
	public List<InsurancePlan> showAllPlan() throws InsurancePlanException {
		List<InsurancePlan> planList = null;
		Session session = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			planList = session.createQuery("FROM InsurancePlan").list();
			trans.commit();
			logger.info("Fetched all insurance plans. Total: " + (planList != null ? planList.size() : 0));

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while fetching all plans", e);
			throw new InsurancePlanException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while fetching all plans", e);
			throw new InsurancePlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}

		return planList;
	}

	/**
	 * Updates an existing InsurancePlan in the database.
	 *
	 * @param insurancePlan the InsurancePlan object with updated data
	 * @return "success" if update is successful, otherwise "failure"
	 */
	@Override
	public String updateInsurancePlan(InsurancePlan insurancePlan) throws InsurancePlanException {
		Session session = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			session.update(insurancePlan);
			trans.commit();
			logger.info("Insurance plan updated successfully for ID: " + insurancePlan.getPlanId());
			return "success";

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while updating plan", e);
			throw new InsurancePlanException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while updating plan", e);
			throw new InsurancePlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}
	}

}
