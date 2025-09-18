/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains DAO interfaces for defining database access operations
 * related to administrative modules such as pharmacy review and approval.
 */

package com.infinite.jsf.admin.dao;

import java.util.List;

import com.infinite.jsf.admin.exception.ReviewPharmacyException;
import com.infinite.jsf.pharmacy.model.Pharmacy;

/**
 * Interface for data access operations related to pharmacy review and approval.
 *
 * <p>
 * This DAO provides methods to retrieve pharmacy details for review, search
 * pharmacies by ID, and update their approval status.
 * </p>
 *
 * @author Infinite Computer Solution
 * @version 1.0
 */
public interface ReviewPharmacyaDao {

	/**
	 * Retrieves a list of all pharmacies pending review.
	 *
	 * @return List of Pharmacy objects to be reviewed
	 * @throws ReviewPharmacyException 
	 */
	List<Pharmacy> reviewPharmacyDetails() throws ReviewPharmacyException;

	/**
	 * Searches for a pharmacy by its unique ID.
	 *
	 * @param pharmacyId the ID of the pharmacy to search
	 * @return Pharmacy object if found, otherwise null
	 */
	Pharmacy searchPharmacyById(String pharmacyId);

	/**
	 * Updates the status of a pharmacy (e.g., ACCEPTED or REJECTED).
	 *
	 * @param pharmacy the Pharmacy object to update
	 * @param status   the new status to set
	 * @return a message indicating the result of the update
	 * @throws ReviewPharmacyException 
	 */
	String updatePharmacyStatus(Pharmacy pharmacy, String status) throws ReviewPharmacyException;
}
