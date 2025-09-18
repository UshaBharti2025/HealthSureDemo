/** 
 * © 2025 Infinite Insurance Solutions. All rights reserved.
 */
package com.infinite.jsf.insurance.dao;

import java.util.List;
import com.infinite.jsf.insurance.model.InsuranceCompany;

/**
 * InsuranceCompanyDao is a Data Access Object (DAO) interface that defines
 * methods for performing CRUD operations on InsuranceCompany entities. This
 * interface provides methods to add, find, update, and delete
 *  
 */
public interface InsuranceCompanyDao {

	/**
	 * Adds a new insurance company.
	 * 
	 * @param company the InsuranceCompany object to be added
	 * @return a status message
	 */
	String addCompany(InsuranceCompany company);

	/**
	 * Finds an insurance company by its ID.
	 * 
	 * @param companyId the ID of the company
	 * @return the InsuranceCompany object if found, otherwise null
	 */
	InsuranceCompany findCompanyById(String companyId);

	/**
	 * Retrieves all insurance companies.
	 * 
	 * @return a list of InsuranceCompany objects
	 */
	List<InsuranceCompany> findAllCompany();

	/**
	 * Deletes an insurance company by its ID.
	 * 
	 * @param company the InsuranceCompany object to be deleted
	 * @return a status message
	 */
	String deleteCompanyById(InsuranceCompany company);

	/**
	 * Updates an existing insurance company.
	 * 
	 * @param company the InsuranceCompany object with updated data
	 * @return a status message
	 */
	String updateCompany(InsuranceCompany company);
}
