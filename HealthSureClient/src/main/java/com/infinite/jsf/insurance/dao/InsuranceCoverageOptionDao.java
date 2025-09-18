/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains DAO interfaces for accessing and managing insurance-related data,
 * such as coverage options, plans.
 */
package com.infinite.jsf.insurance.dao;

import java.util.List;

import com.infinite.jsf.insurance.exception.InsuranceCoverageOptionException;
import com.infinite.jsf.insurance.model.InsuranceCoverageOption;

/**
 * Interface for defining CRUD operations related to InsuranceCoverageOption
 * entities. Used to interact with the database layer for insurance coverage
 * data.
 */
public interface InsuranceCoverageOptionDao {

	/**
	 * Adds a new coverage plan to the database.
	 *
	 * @param coverageOption the coverage option to be added
	 * @return status message
	 * @throws InsuranceCoverageOptionException
	 */
	String addCoveragePlan(InsuranceCoverageOption coverageOption) throws InsuranceCoverageOptionException;

	/**
	 * Retrieves all insurance coverage options.
	 *
	 * @return list of all coverage options
	 * @throws InsuranceCoverageOptionException 
	 */
	List<InsuranceCoverageOption> findAllInsuranceCoverageOptions() throws InsuranceCoverageOptionException;

	/**
	 * Retrieves coverage options by plan ID.
	 *
	 * @param planId the plan ID to filter coverage options
	 * @return list of coverage options for the given plan ID
	 * @throws InsuranceCoverageOptionException
	 */
	List<InsuranceCoverageOption> findAllInsuranceCoverageOptionsByPlanId(String planId)
			throws InsuranceCoverageOptionException;

	/**
	 * Finds a specific coverage option by its ID.
	 *
	 * @param coverageId the ID of the coverage option
	 * @return the coverage option object
	 */
	InsuranceCoverageOption findInsuranceCoverageById(String coverageId);

	/**
	 * Updates an existing coverage option.
	 *
	 * @param coverageOption the coverage option with updated data
	 * @return status message
	 * @throws InsuranceCoverageOptionException 
	 */
	String updateInsuranceCoverageOption(InsuranceCoverageOption coverageOption) throws InsuranceCoverageOptionException;
}
