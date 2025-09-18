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
import org.hibernate.Query;
import org.hibernate.QueryTimeoutException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;

import com.infinite.jsf.insurance.dao.MemberPlanRuleDao;
import com.infinite.jsf.insurance.exception.MemberPlanException;
import com.infinite.jsf.insurance.model.MemberPlanRule;
import com.infinite.jsf.util.SessionHelper;

/**
 * Implementation of MemberPlanRuleDao interface. Provides CRUD operations and
 * search functionalities for MemberPlanRule entities.
 */
public class MemberPlanRuleDaoImpl implements MemberPlanRuleDao {

	private static final SessionFactory factory = SessionHelper.getConnection();
	private static final Logger logger = Logger.getLogger(MemberPlanRuleDaoImpl.class);

	/**
	 * Adds a new MemberPlanRule to the database.
	 *
	 * @param member the MemberPlanRule object to be added
	 * @return "success" if the operation is successful
	 */
	@Override
	public String addMember(MemberPlanRule member) throws MemberPlanException {
		Transaction trans = null;
		Session session = null;
		String memberId = generateNextMemberId();
		member.setMeberId(memberId);
		logger.info("Generated Member ID: " + memberId);
		logger.debug("Member details to be saved: " + member);

		try {
			session = factory.openSession();
			trans = session.beginTransaction();
			session.save(member);
			trans.commit();
			logger.info("Member saved successfully with ID: " + memberId);
			return "success";

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while saving member", e);
			throw new MemberPlanException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while saving member", e);
			throw new MemberPlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Generates the next member ID in the format MEM###.
	 *
	 * @return the next generated member ID
	 */

	public String generateNextMemberId() throws MemberPlanException {
		Session session = null;
		String lastId = null;

		try {
			session = factory.openSession();
			lastId = (String) session.createQuery("SELECT m.meberId FROM MemberPlanRule m ORDER BY m.meberId DESC")
					.setMaxResults(1).uniqueResult();
			logger.debug("Last member ID fetched: " + lastId);

		} catch (HibernateException e) {
			logger.error("Hibernate error while fetching last member ID", e);
			throw new MemberPlanException("Hibernate error", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching last member ID", e);
			throw new MemberPlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close();
		}

		int nextNum = 1;
		try {
			if (lastId != null && lastId.toUpperCase().startsWith("MEM") && lastId.length() == 6) {
				String numPart = lastId.substring(3); // e.g., "001"
				if (numPart.matches("\\d{3}")) {
					nextNum = Integer.parseInt(numPart) + 1;
				}
			}
		} catch (NumberFormatException e) {
			logger.error("Error parsing numeric part of member ID", e);
			throw new MemberPlanException("Invalid numeric format in last member ID", e);
		}

		String nextId = String.format("MEM%03d", nextNum);
		logger.debug("Next generated member ID: " + nextId);
		return nextId;
	}

	/**
	 * Searches and retrieves all MemberPlanRule records associated with a specific
	 * plan ID.
	 *
	 * @param planId the insurance plan ID to filter members
	 * @return list of MemberPlanRule objects
	 */
	@Override
	public List<MemberPlanRule> searchMemberByPlanId(String planId) throws MemberPlanException {
		List<MemberPlanRule> memberList = null;
		Session session = null;
		Transaction trans = null;

		try {
			session = factory.openSession();
			trans = session.beginTransaction();

			String hql = "FROM MemberPlanRule m WHERE m.insurancePlan.planId = :planId";
			Query query = session.createQuery(hql);
			query.setParameter("planId", planId);
			memberList = query.list();

			trans.commit();
			logger.info("Members retrieved for plan ID: " + planId);

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			logger.error("Hibernate error while retrieving members", e);
			throw new MemberPlanException("Hibernate error", e);

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			logger.error("Unexpected error while retrieving members", e);
			throw new MemberPlanException("Unexpected error", e);

		} finally {
			if (session != null)
				session.close(); // use close instead of clear
		}

		return memberList;
	}

}
