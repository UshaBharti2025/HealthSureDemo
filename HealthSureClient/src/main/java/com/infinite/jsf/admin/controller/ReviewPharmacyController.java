/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains controller classes for managing administrative operations in the JSF-based application,
 * including pharmacy review, approval workflows, navigation, and integration with pharmacy modules.
 */

package com.infinite.jsf.admin.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.infinite.jsf.admin.dao.ReviewPharmacyaDao;
import com.infinite.jsf.admin.daoImpl.ReviewPharmacyaDaoImpl;
import com.infinite.jsf.admin.exception.ReviewPharmacyException;
import com.infinite.jsf.pharmacy.model.DispensedEquipments;
import com.infinite.jsf.pharmacy.model.DispensedMedicines;
import com.infinite.jsf.pharmacy.model.Equipment;
import com.infinite.jsf.pharmacy.model.Medicines;
import com.infinite.jsf.pharmacy.model.Passwords;
import com.infinite.jsf.pharmacy.model.Pharmacists;
import com.infinite.jsf.pharmacy.model.Pharmacy;
import com.infinite.jsf.util.MailSend;

/**
 * Controller class for handling pharmacy review and approval operations.
 * 
 * This class manages the review process of pharmacies including validation,
 * approval, rejection, and navigation to related views such as medicines,
 * equipment, pharmacists, and dispensed items. It also supports pagination and
 * sorting of pharmacy records.
 * 
 * Logging is handled using Log4j, with conditional debug logging enabled to
 * trace internal operations when debugging is turned on.
 *
 */
public class ReviewPharmacyController {

	private Pharmacy pharmacy;
	private ReviewPharmacyaDao reviewPharmacyaDao = new ReviewPharmacyaDaoImpl();
	private Medicines medicines;
	private Equipment equipment;
	private Pharmacists pharmacists;
	private DispensedMedicines dispensedMedicines;
	private DispensedEquipments dispensedEquipments;
	private Passwords passwords;
	private Pharmacy selectedPharmacy;
//	private List<Pharmacy> pharmaciesList;
	private String showValidatinMessage;

	private static final Logger logger = Logger.getLogger(ReviewPharmacyController.class);

	public ReviewPharmacyController() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching all pharmacies for review.");
		}
		try {
			allPharmacies = reviewPharmacyaDao.reviewPharmacyDetails();
		} catch (ReviewPharmacyException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error : ",
					"Error accured while fetchig the pharamcy Data"));

		}
		sortAndPaginate();
	}

	/**
	 * Retrieves a list of all pharmacies that are pending review. This method calls
	 * the DAO layer to fetch pharmacy details that require administrative approval
	 * or verification.
	 *
	 * @return List of Pharmacy objects pending review.
	 */

//	public List<Pharmacy> showPharmacyAllForReview() {
//		if (logger.isDebugEnabled()) {
//			logger.debug("Fetching all pharmacies for review.");
//		}
//		pharmaciesList = reviewPharmacyaDao.reviewPharmacyDetails();
//		return pharmaciesList;
//	}

	/*
	 * PAGINATION AND SORTING
	 */
	private List<Pharmacy> allPharmacies;
	private List<Pharmacy> paginatedPharmacies;
	private int page = 0;
	private int pageSize = 3;
	private String sortField = "pharmacyId";
	private boolean ascending = true;

	public void nextPage() {
		if ((page + 1) * pageSize < allPharmacies.size()) {
			page++;
			sortAndPaginate();
		}
	}

	public void previousPage() {
		if (page > 0) {
			page--;
			sortAndPaginate();
		}
	}

	private void sortAndPaginate() {
		Comparator<Pharmacy> comparator = Comparator.comparing(p -> {
			switch (sortField) {
			case "pharmacyName":
				return p.getPharmacyName();
			case "contactNo":
				return p.getContactNo();
			case "aadhar":
				return p.getAadhar();
			case "licenseNo":
				return p.getLicenseNo();
			case "gstNo":
				return p.getGstNo();
			case "status":
				return p.getStatus();
			default:
				return p.getPharmacyId();
			}
		});

		if (!ascending) {
			comparator = comparator.reversed();
		}

		allPharmacies.sort(comparator);

		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, allPharmacies.size());

		paginatedPharmacies = allPharmacies.subList(fromIndex, toIndex);
	}

	/**
	 * Sorts the list of entities (e.g., pharmacies, insurance plans, etc.) in
	 * ascending order based on the specified field. This method is typically used
	 * to organize data in UI tables for better readability and user experience.
	 *
	 * @param field The name of the field by which the list should be sorted (e.g.,
	 *              "name", "createdDate").
	 */

	public void sortByAsc(String field) {
		if (!field.equals(sortField) || !ascending) {
			// If this is a new field or the current order is not ascending, update sort
			sortField = field;
			ascending = true;
			if (logger.isDebugEnabled()) {
				logger.debug("Sorting pharmacies by field: " + sortField + ", ascending: true");
			}
			page = 0;
			sortAndPaginate();
		}
		// If already sorting ascending on this field, you may skip or re-apply
	}

	/**
	 * Sorts the list of entities (e.g., pharmacies, insurance plans, etc.) in
	 * decending order based on the specified field. This method is typically used
	 * to organize data in UI tables for better readability and user experience.
	 *
	 * @param field The name of the field by which the list should be sorted (e.g.,
	 *              "name", "createdDate").
	 */

	public void sortByDesc(String field) {
		if (!field.equals(sortField) || ascending) {
			// If this is a new field or the current order is ascending, update sort
			sortField = field;
			ascending = false;
			if (logger.isDebugEnabled()) {
				logger.debug("Sorting pharmacies by field: " + sortField + ", ascending: false");
			}
			page = 0;
			sortAndPaginate();
		}
		// If already sorting descending on this field, you may skip or re-apply
	}

	// how many page numbers to show per row
	private int pageBlockSize = pageSize;
	// which block of pages user is viewing (starts at 0)
	private int currentBlock = 0;

	public List<Integer> getPageNumbers() {

		List<Integer> pages = new ArrayList<>();

		int totalPages = getTotalPages();

		int startPage = currentBlock * pageBlockSize + 1;

		int endPage = Math.min(startPage + pageBlockSize - 1, totalPages);

		for (int i = startPage; i <= endPage; i++) {
			pages.add(i);
		}

		return pages;
	}

	// Jump to a page within the current block
	public void goToPage(int pageNumber) {
		if (pageNumber >= 1 && pageNumber <= getTotalPages()) {
			this.page = pageNumber - 1;
			sortAndPaginate();
		}
	}

	// Move to the next block of pages (row)
	public void nextBlock() {
		int totalPages = getTotalPages();
		int totalBlocks = (int) Math.ceil((double) totalPages / pageBlockSize);
		if (currentBlock + 1 < totalBlocks) {
			currentBlock++;
			this.page = currentBlock * pageBlockSize;
			sortAndPaginate();
		}
	}

	// Move to the previous block of pages (row)
	public void previousBlock() {
		if (currentBlock > 0) {
			currentBlock--;
			this.page = currentBlock * pageBlockSize;
			

			sortAndPaginate();
		}
	}

	/**
	 * Validates the given pharmacy's details such as GST number, license number,
	 * and Aadhaar number. If the pharmacy passes all validation checks, its status
	 * is updated to 'APPROVED'; otherwise, it remains unchanged or is marked as
	 * 'REJECTED'. Also triggers email notifications to the pharmacy regarding the
	 * validation outcome.
	 *
	 * @param pharmacy The Pharmacy object to be validated.
	 * @return A navigation string indicating the next page or action. Update
	 *         pharmacy status based on validation
	 */
	public String validatePharmacy(Pharmacy pharmacy) {
		FacesContext context = FacesContext.getCurrentInstance();

		if ("ACCEPTED".equals(pharmacy.getStatus())) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					pharmacy.getPharmacyId() + " " + ConstMessage.APPROVED_SUCCESSFULLY.getMessage(), null));
			return null;
		}

		if (validatePharmacyDetails(pharmacy)) {

			try {
				reviewPharmacyaDao.updatePharmacyStatus(pharmacy, "ACCEPTED");
			} catch (ReviewPharmacyException e) {

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
						ConstMessage.PHARMACY_UPDATE_ERROR.getMessage()));
				return null;

			}

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					pharmacy.getPharmacyId() + " " + ConstMessage.APPROVED_SUCCESSFULLY.getMessage(), null));

			showValidatinMessage = pharmacy.getPharmacyId() + " " + ConstMessage.APPROVED_SUCCESSFULLY.getMessage();

		} else {

			try {
				reviewPharmacyaDao.updatePharmacyStatus(pharmacy, "REJECTED");
			} catch (ReviewPharmacyException e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:",
						ConstMessage.PHARMACY_UPDATE_ERROR.getMessage()));
				return null;
			}

			String htmlContent = String.format(ConstMessage.REJECTED_HTML_TEMPLATE.getMessage(), showValidatinMessage);

			MailSend.sendInfo(pharmacy.getEmail(), ConstMessage.REJECTED_SUBJECT.getMessage(), htmlContent);

		}

		return null;
	}

	/**
	 * Performs validation checks on the provided Pharmacy object. Validates
	 * critical fields such as GST number, license number, and Aadhaar number to
	 * ensure they meet required formats and are not null. This method is used to
	 * determine whether the pharmacy can be approved.
	 *
	 * @param pharmacy The Pharmacy object containing details to be validated.
	 * @return true if all required details are valid; false otherwise.
	 */

	public boolean validatePharmacyDetails(Pharmacy pharmacy) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		String aadhar = pharmacy.getAadhar();
		String licenseNo = pharmacy.getLicenseNo();
		String gstNo = pharmacy.getGstNo();

		if (aadhar == null || !aadhar.matches("AADHAR\\d{6}")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Aadhar",
					ConstMessage.INVALID_AADHAR.getMessage()));
			showValidatinMessage = ConstMessage.INVALID_AADHAR.getMessage() + " And Your Aadhar is " + aadhar;
			isValid = false;
		}

		if (licenseNo == null || !licenseNo.matches("LIC\\d{5}")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid License No",
					ConstMessage.INVALID_LICENSE.getMessage()));
			showValidatinMessage += "\n\n" + ConstMessage.INVALID_LICENSE.getMessage() + " And Your LICENSE NO : "
					+ licenseNo;
			isValid = false;
		}

		if (gstNo == null || !gstNo.matches("GSTIN\\d{4}[A-Z]{2}")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid GST No",
					ConstMessage.INVALID_GST.getMessage()));
			showValidatinMessage += "\n\n" + ConstMessage.INVALID_GST.getMessage() + " AND YOUR GST No: " + gstNo;
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Calculates the total number of pages required to display all pharmacies based
	 * on the current page size.
	 *
	 * @return the total number of pages as an integer
	 *
	 *         This method uses Math.ceil to ensure that any remaining items that
	 *         don't fill a complete page still count as an additional page. It
	 *         casts the result to int because page numbers are whole numbers.
	 */

	public int getTotalPages() {
		return (int) Math.ceil((double) allPharmacies.size() / pageSize);
	}

//	GETTER AND STTER

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public ReviewPharmacyaDao getReviewPharmacyaDao() {
		return reviewPharmacyaDao;
	}

	public void setReviewPharmacyaDao(ReviewPharmacyaDao reviewPharmacyaDao) {
		this.reviewPharmacyaDao = reviewPharmacyaDao;
	}

	public Medicines getMedicines() {
		return medicines;
	}

	public void setMedicines(Medicines medicines) {
		this.medicines = medicines;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Pharmacists getPharmacists() {
		return pharmacists;
	}

	public void setPharmacists(Pharmacists pharmacists) {
		this.pharmacists = pharmacists;
	}

	public DispensedMedicines getDispensedMedicines() {
		return dispensedMedicines;
	}

	public void setDispensedMedicines(DispensedMedicines dispensedMedicines) {
		this.dispensedMedicines = dispensedMedicines;
	}

	public DispensedEquipments getDispensedEquipments() {
		return dispensedEquipments;
	}

	public void setDispensedEquipments(DispensedEquipments dispensedEquipments) {
		this.dispensedEquipments = dispensedEquipments;
	}

	public Passwords getPasswords() {
		return passwords;
	}

	public void setPasswords(Passwords passwords) {
		this.passwords = passwords;
	}

	public String getShowValidatinMessage() {
		return showValidatinMessage;
	}

	public void setShowValidatinMessage(String showValidatinMessage) {
		this.showValidatinMessage = showValidatinMessage;
	}

	public List<Pharmacy> getAllPharmacies() {
		return allPharmacies;
	}

	public void setAllPharmacies(List<Pharmacy> allPharmacies) {
		this.allPharmacies = allPharmacies;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public void setPaginatedPharmacies(List<Pharmacy> paginatedPharmacies) {
		this.paginatedPharmacies = paginatedPharmacies;
	}

	public void setSelectedPharmacy(Pharmacy selectedPharmacy) {
		this.selectedPharmacy = selectedPharmacy;
	}

	public Pharmacy getSelectedPharmacy() {
		return selectedPharmacy;
	}

	public List<Pharmacy> getPaginatedPharmacies() {
		return paginatedPharmacies;
	}

	public int getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(int currentBlock) {
		this.currentBlock = currentBlock;
	}

	public int getPageBlockSize() {
		return pageBlockSize;
	}

	public void setPageBlockSize(int pageBlockSize) {
		this.pageBlockSize = pageBlockSize;
	}

	public static Logger getLogger() {
		return logger;
	}
//	public List<Pharmacy> getPharmaciesList() {
//	return pharmaciesList;
//}
//
//public void setPharmaciesList(List<Pharmacy> pharmaciesList) {
//	this.pharmaciesList = pharmaciesList;
//}
}
