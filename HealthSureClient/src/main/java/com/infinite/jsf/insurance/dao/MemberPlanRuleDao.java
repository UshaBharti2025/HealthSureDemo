/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains DAO interfaces for accessing and managing insurance-related data,
 * such as members, plan rules, coverage options, and subscriptions.
 */
package com.infinite.jsf.insurance.dao;

import java.util.List;

import com.infinite.jsf.insurance.exception.MemberPlanException;
import com.infinite.jsf.insurance.model.MemberPlanRule;

/**
 * Interface for defining CRUD operations related to MemberPlanRule entities in
 * the insurance system.
 */
public interface MemberPlanRuleDao {

	/**
	 * Adds a new member to the database.
	 *
	 * @param member the Member object to be added
	 * @return status message
	 * @throws MemberPlanException 
	 */
	String addMember(MemberPlanRule member) throws MemberPlanException;


	/**
	 * Retrieves all members associated with a specific plan ID.
	 *
	 * @param planId the plan ID to filter members
	 * @return list of MemberPlanRule objects
	 * @throws MemberPlanException 
	 */
	List<MemberPlanRule> searchMemberByPlanId(String planId) throws MemberPlanException;

}
