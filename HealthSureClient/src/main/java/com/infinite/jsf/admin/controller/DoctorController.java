
/**
 * Doctor Controller class that handles the business logic and interactions for healthsure.
 * for searching and managing Doctor and Provider information in the application.
 * 
 * This class provides:
 * 
 * - **Search Functionality**: 
 *   - Enables search operations for both doctors and providers based on the input criteria.
 *   - Handles the logic for performing database queries based on user selections.
 * 
 * - **Validation Logic**:
 *   - Ensures that search inputs are correctly validated before performing the search operation.
 * 
 * - **Navigation**:
 *   - Redirects to the results page when valid search results are found, or stays on the same page if no results are found.
 *   - Returns a user-friendly message ("No Results Found") if no entries match the search criteria.
 *   - Utilizes managed beans to interface with the underlying database and services.
 * 
 * This class interacts with the `Doctor` and `Provider` entities to fetch relevant data and display it to the user.
 * It uses Hibernate ORM for database operations to fetch search results based on the criteria set in the view.
 * 
 * Example Operations:
 * - Search for doctors based on doctor ID.
 * - Search for providers based on provider ID.
 * - Display relevant results with pagination or full result sets.
 * 
 * This class ensures that no invalid search is performed, avoiding unnecessary database queries.
 * 
 */
package com.infinite.jsf.admin.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import com.infinite.jsf.admin.dao.DoctorDao;
import com.infinite.jsf.admin.daoImpl.DoctorDaoImpl;
import com.infinite.jsf.provider.model.Doctors;
import com.infinite.jsf.provider.model.LoginStatus;
import com.infinite.jsf.provider.model.Provider;
import com.infinite.jsf.util.ErrorMessage;
import com.infinite.jsf.util.MailSend;
import com.infinite.jsf.util.MailSendIsh;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DoctorController {

	private DoctorDao doctorDao;
	private Doctors doctor;
	private Provider provider;
	private List<Doctors> doctorsList; // To hold the list of doctors from search
	private boolean updateSuccess; // Property to control success message
	private String selectedRole;
	private String errorMessage;
	private static final Logger logger = Logger.getLogger(DoctorController.class.getName());

	// search and inquiry
	private String searchType;
	private String searchCriteria;
	private String searchInput;
	private List<Doctors> searchResults;
	private List<Provider> searchResultsP; // search result for providers

	private List<SelectItem> searchTypeOptions;
	private List<SelectItem> searchCriteriaOptions;

	// Sort field and direction
	private String sortField;
	private boolean ascending = true;
	private String searchOption;
	private String selectedSearchField;

	private boolean isValidated = false;

	private List<Provider> providerList;
	private Provider selectedProvider;
	private String providerStatus;
	private List<Provider> paginatedProviders;

	// Pagination Fields
	private int page = 0; // Current page index
	private final int pageSize = 10; // Number of providers per page
	private int totalPages; // Total number of pages for pagination
	private static final int PAGE_SIZE = 10;
	// New property to track if the details are updated
	private boolean detailsUpdated = false;

	private Map<String, Boolean> validatedProviders = new HashMap<>(); // Map to track validated providers by their
	// providerId
	//for sorting button
	private String currentSort;

	public String getCurrentSort() {
		return currentSort;
	}

	public void setCurrentSort(String currentSort) {
		this.currentSort = currentSort;
	}
	public List<Provider> getSearchResultsP() {
		return searchResultsP;
	}

	public void setSearchResultsP(List<Provider> searchResultsP) {
		this.searchResultsP = searchResultsP;
	}

	public List<SelectItem> getSearchTypeOptions() {
		return searchTypeOptions;
	}

	public void setSearchTypeOptions(List<SelectItem> searchTypeOptions) {
		this.searchTypeOptions = searchTypeOptions;
	}

	public void setSearchCriteriaOptions(List<SelectItem> searchCriteriaOptions) {
		this.searchCriteriaOptions = searchCriteriaOptions;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

	public List<Doctors> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Doctors> searchResults) {
		this.searchResults = searchResults;
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

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getSelectedSearchField() {
		return selectedSearchField;
	}

	public void setSelectedSearchField(String selectedSearchField) {
		this.selectedSearchField = selectedSearchField;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public boolean isUpdateSuccess() {
		return updateSuccess;
	}

	public void setUpdateSuccess(boolean updateSuccess) {
		this.updateSuccess = updateSuccess;
	}

	public List<Doctors> getDoctorsList() {
		return doctorsList;
	}

	public void setDoctorsList(List<Doctors> doctorsList) {
		this.doctorsList = doctorsList;
	}

	public Provider getSelectedProvider() {
		return selectedProvider;
	}

	public void setSelectedProvider(Provider selectedProvider) {
		this.selectedProvider = selectedProvider;
	}

	public String getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(String providerStatus) {
		this.providerStatus = providerStatus;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	// Getter and Setter for validatedProviders
	public Map<String, Boolean> getValidatedProviders() {
		return validatedProviders;
	}

	public void setValidatedProviders(Map<String, Boolean> validatedProviders) {
		this.validatedProviders = validatedProviders;
	}

	// Getter and setter for isValidated
	public boolean getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	// Getter for total number of pages
	public int getTotalPages() {
		return totalPages;
	}

	// Getter for current page index (1-based)
	public int getCurrentPage() {
		return page + 1;
	}

	public List<Provider> getProviderList() {
		return providerList;
	}

	public void setProviderList(List<Provider> providerList) {
		this.providerList = providerList;
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

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	// Paginated providers methods

	public List<Provider> getPaginatedProviders() {
		return paginatedProviders;
	}

	public void setPaginatedProviders(List<Provider> paginatedProviders) {
		this.paginatedProviders = paginatedProviders;
	}

	public DoctorController() {
		doctorDao = new DoctorDaoImpl(); // Initialize the DAO
		doctor = new Doctors(); // Initialize doctor object
		provider = new Provider();
		loadProviders(); // Load initial set of providers

		if (doctor == null) {
			doctor = new Doctors(); // Initialize doctor if it's null
		}
		searchTypeOptions = new ArrayList<>();
		searchTypeOptions.add(new SelectItem("doctor", "Doctor"));
		searchTypeOptions.add(new SelectItem("provider", "Provider"));

		searchCriteriaOptions = new ArrayList<>();
		searchCriteriaOptions.add(new SelectItem("doctorId", "Doctor ID"));
		searchCriteriaOptions.add(new SelectItem("doctorName", "Doctor Name"));
		searchCriteriaOptions.add(new SelectItem("specialization", "Specialization"));
		searchCriteriaOptions.add(new SelectItem("licenseNo", "License Number"));

		resetSearchFields(); // Reset fields when the controller is initialized

	}

	/**
	 * Sets the search type and updates the search criteria accordingly.
	 * 
	 * This method updates the `searchType` field and resets the `searchCriteria`
	 * based on the selected search type. If the search type is "provider", the
	 * `searchCriteria` is set to "providerId". If the search type is anything else,
	 * it defaults to "doctorId". This helps in determining which field will be used
	 * for searching.
	 * 
	 * @param searchType The type of search being performed, either "provider" or
	 *                   "doctor". Based on the value, the search criteria will be
	 *                   set accordingly.
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;

		// Reset searchCriteria when searchType changes
		if ("provider".equals(searchType)) {
			this.searchCriteria = "providerId"; // Default to Provider ID when "provider" is selected
		} else {
			this.searchCriteria = "doctorId"; // Default to Doctor ID when "doctor" is selected
		}
	}

	/**
	 * Retrieves the available search criteria options based on the current search
	 * type.
	 * 
	 * This method returns a list of selectable search criteria options (as
	 * `SelectItem` objects) based on the current `searchType`. If the search type
	 * is "doctor", the options will include Doctor ID, Doctor Name, Specialization,
	 * and License Number. If the search type is "provider", the options will
	 * include Provider ID and Provider Name.
	 * 
	 * @return A list of `SelectItem` objects representing the search criteria
	 *         options for the current search type.
	 */
	public List<SelectItem> getSearchCriteriaOptions() {
		List<SelectItem> options = new ArrayList<>();

		if ("doctor".equals(searchType)) {
			options.add(new SelectItem("doctorId", "Doctor ID"));
			options.add(new SelectItem("doctorName", "Doctor Name"));
			options.add(new SelectItem("specialization", "Specialization"));
			options.add(new SelectItem("licenseNo", "License No"));
		} else if ("provider".equals(searchType)) {
			options.add(new SelectItem("providerId", "Provider ID"));
			options.add(new SelectItem("providerName", "Provider Name"));
		}

		return options;
	}

	// Dynamic input label based on search criteria
	/**
	 * Returns the appropriate input label based on the current search criteria.
	 * 
	 * This method dynamically generates a label to prompt the user for input based
	 * on the current `searchCriteria` value. For example, if the search criteria is
	 * "doctorId", the label returned will be "Enter Doctor ID:", and similarly for
	 * other criteria like "doctorName", "licenseNo", etc. If the search criteria
	 * doesn't match any known values, an empty string is returned.
	 * 
	 * @return A string representing the label for the input field based on the
	 *         current search criteria.
	 */
	public String getInputLabel() {
		switch (searchCriteria) {
		case "doctorId":
			return "Enter Doctor ID:";
		case "doctorName":
			return "Enter Doctor Name:";
		case "licenseNo":
			return "Enter License Number:";
		case "specialization":
			return "Enter Specialization:";
		case "providerId":
			return "Enter Provider ID:";
		default:
			return "";
		}
	}

	/**
	 * Fetches and returns the list of all doctors, and sorts the list.
	 * 
	 * This method retrieves the list of all doctors from the `doctorDao` using the
	 * `showDoctorDao` method. Once the list is retrieved, it is sorted using the
	 * `sortList` method before being returned. The sorting logic is assumed to be
	 * defined in the `sortList` method (which is not shown here).
	 * 
	 * @return A sorted list of `Doctors` retrieved from the data source.
	 */
	public List<Doctors> showDoctor() {
		doctorsList = doctorDao.showDoctorDao();
		sortList();
		return doctorsList;
	}

	/**
	 * Sort the list of doctors by the selected field and order.
	 */
	/**
	 * Sorts the list of doctors based on the selected field and the current sort
	 * order.
	 * 
	 * 
	 * This method uses Java reflection to dynamically access the specified field
	 * (based on `sortField`) of the `Doctors` class and compares its values to sort
	 * the `doctorsList`. The sorting order is determined by the `ascending` flag—if
	 * true, the list is sorted in ascending order; otherwise, it's sorted in
	 * descending order. If the field is not found or an error occurs, the method
	 * returns without modifying the list.
	 */

	/**
	 * Sort the list of doctors by the selected field and order.
	 */
	/**
	 * Sets the sorting field and toggles the sorting order.
	 * 
	 * This method is used to specify which field to sort by. If the selected field
	 * is already the current sort field, the method will toggle the sort direction
	 * between ascending and descending. If the selected field is different, it will
	 * set the new field to sort by and default the sort order to ascending.
	 * 
	 * @param field The name of the field to sort by (e.g., "doctorName",
	 *              "specialization").
	 */
	public void sortBy(String field) {
		if (field.equals(sortField)) {
			ascending = !ascending; // Toggle sort direction
		} else {
			sortField = field;
			ascending = true;
		}
	}

	// Back Buttons
	/**
	 * Redirects the user to the admin dashboard.
	 * 
	 * This method is used to navigate the user back to the admin dashboard page. It
	 * can optionally include any cleanup or data processing before performing the
	 * redirection. By default, it redirects to the "Admin_Dashboard_Providers"
	 * view, which is assumed to be mapped to the `dashboard.xhtml` page.
	 * 
	 * @return A string representing the view name for the redirection. In this
	 *         case, "Admin_Dashboard_Providers".
	 */
	public String backtoadmindashboard() {
		resetSearchFields();
		resetFields();
		return "/admin/AuthAdmin/AdminDashBoard.jsp";
	}

	public String backtoupdatemenu() {
		resetFields();
		return "Provider_Update_Details_Menu";
	}

	public String backtosearchmenu() {
		return "Provider_Search_And_Inquiry";
	}

	public String backtoupdateprovidersearch() {
		resetFields();
		return "Provider_Update_Search_Provider";
	}

	public String backtoupdatedoctorsearch() {
		resetFields();
		return "Provider_Update_Search_Doctor";
	}

	// Methods For Provider Update Details -
	// Reset method for clearing form fields and error messages
	public void resetFields() {

		if (doctor == null) {
			doctor = new Doctors(); // Initialize doctor if it's null
		}
		if (provider == null) {
			provider = new Provider(); // Initialize provider if it's null
		}
		doctor.setDoctorId(null); // Reset the doctor ID input field
		provider.setProviderId(null);
		updateSuccess = false;
		errorMessage = null; // Clear any previous error message
		// You can add other fields here if necessary
	}

	/**
	 * Displays the details of a doctor by fetching the doctor information using the
	 * doctor ID.
	 * 
	 * This method is used to retrieve a doctor's details based on the provided
	 * doctor ID. If the doctor exists in the system, the user is navigated to the
	 * "Provider_Update_Details_Doctor" page to update the details. If the doctor is
	 * not found, an error message is shown, and the user remains on the current
	 * page.
	 * 
	 * @return A string representing the navigation outcome. Returns
	 *         "Provider_Update_Details_Doctor" if successful, or null if doctor is
	 *         not found.
	 */
	public String showDoctorDetails() {

		System.err.println(doctor);

		// Initialize doctor if it's null (for safety)
		if (doctor == null) {
			doctor = new Doctors(); // Ensure the doctor object is not null
		}

		// Trim the provider ID to remove leading/trailing spaces
		String doctorId = doctor.getDoctorId() != null ? doctor.getDoctorId().trim() : "";

		logger.info("Doctor ID reached showDoctorDetails() " + doctorId);
		System.out.println("Doctor ID reached showDoctorDetails() " + doctorId); // Debugging line
		errorMessage = null;

		// Step 1: Check if the provider ID is empty (after trimming)

		System.err.println("doctorId == null: " + (doctorId == null));
		System.err.println("doctorId.trim().isEmpty(): " + doctorId.trim().isEmpty());

		if (doctorId == null || doctorId.trim().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter an ID.", ""));
			return null; // Return to the same page to show the error
		}
		if (doctor == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter an ID.", ""));
			System.err.println(doctor);

			return null; // Return to the same page to show the error
		}

		// Step 1: Validate the doctorId format using a regex
		if (!isValidDoctorId(doctorId)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Invalid doctor ID. Please enter a valid ID starting with 'D' followed by 3 digits.", ""));
			return null; // Return to the same page to show the error
		}
		try {
			System.out.println("Doctor Id  : " + doctorId);
			logger.info("Doctor Id  : " + doctorId);

			this.doctor = doctorDao.getDoctorById(doctorId);
			if (this.doctor != null) {
				return "Provider_Update_Details_Doctor"; // Navigate to update page (use your JSF navigation rule here)

			} else {

				errorMessage = ErrorMessage.DOCTOR_NOT_FOUND_BY_ID;
				// Initialize doctor object to prevent null access in the JSF page
				doctor = new Doctors(); // Initialize doctor object (or reset the doctorId field)
				return null;
			}
		} catch (Exception e) {
			logger.error("Error occurred while fetching doctor details: " + e.getMessage());
			return "errorPage"; // Redirect to an error page in case of an exception
		}

	}

	/**
	 * Displays the details of a provider by fetching the provider information using
	 * the provider ID.
	 * 
	 * This method is used to retrieve a provider's details based on the provided
	 * provider ID. If the provider exists, the user is navigated to the
	 * "Provider_Update_Details_Provider" page to update the provider details. If
	 * the provider is not found, an error message is displayed.
	 * 
	 * @return A string representing the navigation outcome. Returns
	 *         "Provider_Update_Details_Provider" if successful, or null if provider
	 *         is not found.
	 */
	public String showProviderDetails() {

		// Initialize provider if it's null (for safety)
		if (provider == null) {
			provider = new Provider(); // Ensure the provider object is not null
		}

		// Trim the provider ID to remove leading/trailing spaces
		String providerId = provider.getProviderId() != null ? provider.getProviderId().trim() : "";

		System.out.println("Provider ID reached showProviderDetails() " + providerId); // Debugging line
		logger.info("Provider ID reached showProviderDetails() " + providerId);

		errorMessage = null;

		// Step 1: Check if the provider ID is empty (after trimming)
		if (providerId == null || providerId.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter an ID.", ""));
			return null; // Return to the same page to show the error
		}

		// Step 2: Validate the providerId format using a regex
		if (!isValidProviderId(providerId)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Invalid provider ID. Please enter a valid ID starting with 'P' followed by 3 digits.", ""));
			return null; // Return to the same page to show the error
		}

		try {
			System.out.println("Provider Id  : " + providerId);
			logger.info("Provider Id  : " + providerId);

			this.provider = doctorDao.getProviderById(providerId); // Use the trimmed providerId

			if (this.provider != null) {
				return "Provider_Update_Details_Provider"; // Navigate to update page (use your JSF navigation rule
															// here)
			} else {
				errorMessage = ErrorMessage.PROVIDER_NOT_FOUND;
				provider = new Provider(); // Initialize provider object
				return null;
			}
		} catch (Exception e) {
			logger.error("Error occurred while fetching provider details: " + e.getMessage());
			return "errorPage"; // Redirect to an error page in case of an exception
		}
	}

	/**
	 * Validates the provider ID format (should start with 'P' followed by 3
	 * digits).
	 * 
	 * @param providerId The provider ID to validate
	 * @return true if valid, false otherwise
	 */
	private boolean isValidProviderId(String providerId) {
		// Regular expression for a provider ID that starts with 'P' followed by exactly
		// 3 digits
		return providerId != null && providerId.matches("^P\\d{3}$");
	}

	/**
	 * Validates the doctor ID format (should start with 'D' followed by 3 digits).
	 * 
	 * @param doctorId The doctor ID to validate
	 * @return true if valid, false otherwise
	 */
	private boolean isValidDoctorId(String doctorId) {
		// Regular expression for a doctor ID that starts with 'D' followed by exactly 3
		// digits
		return doctorId != null && doctorId.matches("^D\\d{3}$");
	}

	/**
	 * Updates the details of a doctor after validating the input fields.
	 * 
	 * This method validates the input fields for the doctor's details (e.g., name,
	 * email, license number, phone number). If the validation passes, the doctor's
	 * information is updated. If the update is successful, a success message is
	 * displayed, and the user is navigated to the "Provider_Update_Details_Doctors"
	 * page. In case of an error, an error message is shown.
	 * 
	 * @return A string representing the navigation outcome. Returns
	 *         "Provider_Update_Details_Doctors" if the update is successful, or
	 *         "errorPage" if the update fails.
	 */
	public String updateDoctorDetails() {
		System.out.println("Doctor Object: " + doctor); // Print doctor object
		System.out.println("Doctor ID: " + doctor.getDoctorId()); // Print doctor ID
		logger.info("Doctor Object: " + doctor);
		logger.info("Doctor ID: " + doctor.getDoctorId());
		errorMessage = null;
		updateSuccess = false;
		// Validate the doctor details before attempting update
		if (!validateFields()) {
			return null; // Stay on the current page and show error message if validation fails
		}
		try {
			doctorDao.updateDoctor(doctor); // Update the doctor
			updateSuccess = true;
			// After successful update, set detailsUpdated to true
			detailsUpdated = true;

			// Option 1: Show a message and navigate to a success page
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated successfully...", ""));

			// Return the page to navigate to (could be a success page)
			return "Provider_Update_Details_Doctors"; // Page you want to navigate to after successful update

		} catch (Exception e) {
			logger.error("Error occurred while updating doctor: " + e.getMessage());
			detailsUpdated = false; // Reset if update failed

			updateSuccess = false;
			errorMessage = ErrorMessage.ERROR_UPDATE_DOCTOR + e.getMessage();
			return "errorPage"; // Redirect to error page if update fails
		}
	}

	/**
	 * Validates the input fields for the doctor's details.
	 * 
	 * This method checks the validity of various fields in the doctor's details,
	 * including the doctor's name, email, license number, and phone number. If any
	 * field is invalid, an error message is set, and the method returns false.
	 * 
	 * @return A boolean value indicating whether the input fields are valid.
	 *         Returns true if all fields are valid, and false otherwise.
	 */
	private boolean validateFields() {
		boolean isValid = true;
		errorMessage = null;
		// Name validation (at least 5 characters)
		if (doctor.getDoctorName().length() < 5) {
			errorMessage = ErrorMessage.INVALID_DOCTOR_NAME;
			isValid = false;
		}

		// Doctor Name validation (no numbers allowed)
		if (doctor.getDoctorName().matches(".*\\d.*")) { // Regex to check if the name contains any digit
			errorMessage = ErrorMessage.INVALID_DOCTOR_NAME_LENGTH;
			isValid = false;
		}

		// Validation for no special characters, numbers, or '@' symbols
		String doctorName = doctor.getDoctorName();

		// Allow 'Dr.' as a valid prefix (case insensitive), but check the rest of the
		// name for invalid characters
		if (doctorName.matches(".*[@0-9!\"#$%&'()*+,-./:;<=>?\\[\\]^_`{|}~].*")
				&& !doctorName.toLowerCase().startsWith("dr.")) {
			errorMessage = ErrorMessage.INVALID_NAME;
			isValid = false;
		}

		// Check if the doctor name is null or empty
		if (doctorName == null || doctorName.trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_DOCTORNAME;
			isValid = false;
		}
		// Ensure there is at most one space between words
		else if (doctorName.matches(".*\\s{2,}.*")) { // Matches if there are consecutive spaces
		    errorMessage = ErrorMessage.INVALID_SPACES;
		    isValid = false;
		}
// License number validation (starts with "L" followed by 3 digits)
		if (!doctor.getProviders().getProviderId().matches("^P\\d{3}$")) {
			errorMessage = ErrorMessage.INVALID_PROVIDER_ID;
			isValid = false;
		}
		if (doctor.getProviders().getProviderId() == null || doctor.getProviders().getProviderId().trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_PROVIDERID;
			isValid = false;
		}
		// Email validation (contains exactly one '@' and is a valid Gmail address)
		String email = doctor.getEmail();

		// Check if email is null or empty
		if (email == null || email.trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_EMAILID;
			isValid = false;
		} else {
			// Check if there is exactly one '@' symbol
			if (email.chars().filter(ch -> ch == '@').count() != 1) {
				errorMessage = ErrorMessage.INVALID_EMAIL_FORMAT;
				isValid = false;
			}
			// Check if email ends with '@gmail.com' or '@googlemail.com'
			else if (!(email.endsWith("@gmail.com") || email.endsWith("@googlemail.com"))) {
				errorMessage = ErrorMessage.INVALID_EMAIL_FORMAT;
				isValid = false;
			}
		}

		// Doctor Qualification validation (should be a valid medical qualification)
		String[] validQualifications = { "MBBS", "BDS", "BAMS", "BHMS", "BUMS", "DM", "BPT", "MPT", "BSc Nursing", "MD",
				"MS", "DNB", "MCh", "DLO", "FRCS", "FRCSEd", "MBChB" };
		boolean isQualificationValid = false;
		// Check if the qualification is in the valid list
		for (String validQualification : validQualifications) {
			if (doctor.getQualification().equalsIgnoreCase(validQualification)) {
				isQualificationValid = true;
				break;
			}
		}
		if (!isQualificationValid) {
			errorMessage = ErrorMessage.INVALID_DOCTOR_QUALIFICATION;
			isValid = false;
		}
		// Doctor Specialization validation (should be a valid medical Specialization)
		String[] validSpecializations = { "General Medicine", "Pediatrics", "General Surgery", "Orthopedics",
				"Gynecology and Obstetrics", "Dermatology", "Psychiatry", "Anesthesiology", "Ophthalmology",
				"Otolaryngology (ENT)", "Radiology", "Pathology", "Cardiology", "Neurology", "Neurosurgery", "Urology",
				"Oncology", "Gastroenterology", "Endocrinology", "Pulmonology", "Nephrology", "Rheumatology",
				"Infectious Diseases", "Emergency Medicine", "Plastic Surgery", "Forensic Medicine", "Vascular Surgery",
				"Reproductive Medicine", "Hematology", "Immunology", "Sports Medicine", "Geriatrics", "Palliative Care",
				"Bariatric Surgery", "Dental Surgery", "Nuclear Medicine", "Pediatric Surgery", "Thoracic Surgery",
				"Traumatology", "Pain Medicine", "Clinical Pharmacology", "Obstetric Anesthesia", "Adolescent Medicine",
				"Palliative Medicine", "Sleep Medicine", "Clinical Genetics", "Fertility Medicine",
				"Transfusion Medicine", "Medical Toxicology", "Aesthetic Medicine", "Naturopathy", "Homeopathy",
				"Ayurveda" };
		boolean isSpecializationValid = false;

		// Check if the Specialization is in the valid list
		for (String validSpecialization : validSpecializations) {
			if (doctor.getSpecialization().equalsIgnoreCase(validSpecialization)) {
				isSpecializationValid = true;

				break;
			}
		}
		if (!isSpecializationValid) {
			errorMessage = ErrorMessage.INVALID_DOCTOR_SPECIALIZATION;
			isValid = false;
		}

		// License number validation (starts with "L" followed by 3 digits)
		if (!doctor.getLicenseNo().matches("^L\\d{3}$")) {
			errorMessage = ErrorMessage.INVALID_LICENSE_NO;
			isValid = false;
		}
		if (doctor.getLicenseNo() == null || doctor.getLicenseNo().trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_LICENSE_NO;
			isValid = false;

		}
		// Phone number validation (exactly 10 digits)
		if (!doctor.getPhoneNumber().matches("^\\d{10}$")) {
			errorMessage = ErrorMessage.INVALID_TELEPHONE;
			isValid = false;
		}
		if (doctor.getPhoneNumber() == null || doctor.getPhoneNumber().trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_PHONE;
			isValid = false;
		}

		// Address validation (at least 5 characters, contains both numbers and
		// alphabets)
		String address = doctor.getAddress();

		// Check if address is null or empty
		if (address == null || address.trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_ADDRESS;
			isValid = false;
		}
		// Check if address is too short
		else if (address.length() < 3) {
			errorMessage = ErrorMessage.INVALID_ADDRESS;
			isValid = false;
		}
		// Ensure the address is not entirely numeric
				else if (address.matches("^[0-9]+$")) {
				    errorMessage = ErrorMessage.INVALID_ADDRESS;
				    isValid = false;
				}
		// Ensure there is at most one space between words
				else if (address.matches(".*\\s{2,}.*")) { // Matches if there are consecutive spaces
				    errorMessage = ErrorMessage.INVALID_SPACES;
				    isValid = false;
				}
		return isValid;
	}

	/**
	 * Updates the details of a provider after validating the input fields.
	 * 
	 * This method validates the input fields for the provider's details (e.g.,
	 * provider name, email, telephone). If the validation passes, the provider's
	 * information is updated. If the update is successful, a success message is
	 * displayed, and the user is navigated to the
	 * "Provider_Update_Details_Provider" page. In case of an error, an error
	 * message is shown.
	 * 
	 * @return A string representing the navigation outcome. Returns
	 *         "Provider_Update_Details_Provider" if the update is successful, or
	 *         "errorPage" if the update fails.
	 */
	public String updateProviderDetails() {
		System.out.println("Provider Object: " + provider); // Print provider object
		System.out.println("Provider ID: " + provider.getProviderId()); // Print provider ID
		logger.info("Provider Object: " + provider);
		logger.info("Provider ID: " + provider.getProviderId());
		errorMessage = null;
		updateSuccess = false;

		// Validate the provider details before attempting update
		if (!validateProviderFields()) {
			return null; // Stay on the current page and show error message if validation fails
		}

		try {
			doctorDao.updateProvider(provider); // Update the provider
			updateSuccess = true;

			// Show a message and navigate to a success page
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Provider updated successfully...", ""));

			// Return the page to navigate to (could be a success page)
			return "Provider_Update_Details_Provider"; // Page you want to navigate to after successful update

		} catch (Exception e) {
			logger.error("Error occurred while updating provider: " + e.getMessage());

			updateSuccess = false;
			errorMessage = ErrorMessage.ERROR_UPDATE_PROVIDER + e.getMessage();
			return "errorPage"; // Redirect to error page if update fails
		}
	}

	// Validation for provider fields
	/**
	 * Validates the input fields for the provider's details.
	 * 
	 * This method checks the validity of various fields in the provider's details,
	 * including the provider's name, email, and telephone number. If any field is
	 * invalid, an error message is set, and the method returns false.
	 * 
	 * @return A boolean value indicating whether the input fields are valid.
	 *         Returns true if all fields are valid, and false otherwise.
	 */

	private boolean validateProviderFields() {
		boolean isValid = true;
		errorMessage = null;

		// Provider Name validation (at least 5 characters)
		if (provider.getProviderName().length() < 5) {
			errorMessage = ErrorMessage.INVALID_PROVIDER_NAME;
			isValid = false;
		}

		// Provider Name validation (no numbers allowed)
		if (provider.getProviderName().matches(".*\\d.*")) { // Regex to check if the name contains any digit
			errorMessage = ErrorMessage.PROVIDER_NAME_NUMBER;
			isValid = false;
		}
		// Validation for no @, numeric or special characters
		String providerName = provider.getProviderName();
		if (providerName.matches(".*[@0-9!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*")) {
			errorMessage = ErrorMessage.INVALID_NAME;
			isValid = false;
		}
		else if (providerName.matches(".*\\s{2,}.*")) { // Matches if there are consecutive spaces
		    errorMessage = ErrorMessage.INVALID_SPACES;
		    isValid = false;
		}
		// Empty Provider Name
		if (providerName == null || providerName.trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_PROVIDERNAME;
			isValid = false;
		}

		// Email validation (contains exactly one '@' and is a valid Gmail address)
		String email = provider.getEmail();

		// Check if email is null or empty
		if (email == null || email.trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_EMAILID;
			isValid = false;
		} else {
			// Check if there is exactly one '@' symbol
			if (email.chars().filter(ch -> ch == '@').count() != 1) {
				errorMessage = ErrorMessage.INVALID_EMAIL_FORMAT;
				isValid = false;
			}
			// Check if email ends with '@gmail.com' or '@googlemail.com'
			else if (!(email.endsWith("@gmail.com") || email.endsWith("@googlemail.com"))) {
				errorMessage = ErrorMessage.INVALID_EMAIL_FORMAT;
				isValid = false;
			}
		}

		// Telephone number validation (exactly 10 digits)
		if (!provider.getTelephone().matches("^\\d{10}$")) {
			errorMessage = ErrorMessage.INVALID_TELEPHONE;
			isValid = false;
		}
		// Empty Telephone
		if (provider.getTelephone() == null || provider.getTelephone().trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_TELEPHONE;
			isValid = false;
		}
		// Address validation (at least 5 characters, contains both numbers and
		// alphabets)
		String address = provider.getAddress();

		// Check if address is null or empty
		if (address == null || address.trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_ADDRESS;
			isValid = false;
		}
		// Check if address is too short
		else if (address.length() < 3) {
			errorMessage = ErrorMessage.INVALID_ADDRESS;
			isValid = false;
		}
		
		// Ensure the address is not entirely numeric
		else if (address.matches("^[0-9]+$")) {
		    errorMessage = ErrorMessage.INVALID_ADDRESS;
		    isValid = false;
		}
		else if (address.matches(".*\\s{2,}.*")) { // Matches if there are consecutive spaces
		    errorMessage = ErrorMessage.INVALID_SPACES;
		    isValid = false;
		}
		// City Validation (at least 3 characters)
		if (provider.getCity().length() < 3) {
			errorMessage = ErrorMessage.INVALID_CITY;
			isValid = false;
		}
		// Empty City
		if (provider.getCity() == null || provider.getCity().trim().isEmpty()) {
			errorMessage = ErrorMessage.EMPTY_CITY;
			isValid = false;
		}
//		// Ensure the City is not entirely numeric
		else if (provider.getCity().matches("^[0-9]+$")) {
		    errorMessage = ErrorMessage.INVALID_CITY;
		    isValid = false;
		}
		else if (provider.getCity().matches(".*\\s{2,}.*")) { // Matches if there are consecutive spaces
		    errorMessage = ErrorMessage.INVALID_SPACES;
		    isValid = false;
		}

		return isValid;
	}

	// Methods For Provider Review And Approval

	private void sortList() {

		System.out.println("sorList method is called: " + ascending + " sortedfiled : " + sortField);
		// Check if providerList is null or empty before attempting to sort
	    if (providerList == null || providerList.isEmpty() || sortField == null) {
	        System.out.println("providerList is null or empty, skipping sort.");
	        return;
	    }
		providerList.forEach(System.out::println);
		if (sortField == null || providerList == null)
			return;

		Collections.sort(providerList, (e1, e2) -> {
			try {
				Field f = Provider.class.getDeclaredField(sortField);
				f.setAccessible(true);
				Comparable v1 = (Comparable) f.get(e1);
				Comparable v2 = (Comparable) f.get(e2);
				return ascending ? v1.compareTo(v2) : v2.compareTo(v1);
			} catch (Exception ex) {
				return 0;
			}
		});
		providerList.forEach(System.out::println);
		

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
//		doctorsList.forEach(System.out::println);
		if (!field.equals(sortField) || !ascending) {
			// If this is a new field or the current order is not ascending, update sort
			sortField = field;
			ascending = true;
			//for disabling
			currentSort = "asc";
			System.out.println("this is ascending");

		}
		// reset to page 1
		page = 0;
		
		updatePaginatedProviders();
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
		System.out.println("this is desc");

		if (!field.equals(sortField) || ascending) {
			// If this is a new field or the current order is not ascending, update sort
			sortField = field;
			ascending = false;
			currentSort = "desc";
			System.out.println("this is desc");
		}
		page = 0;
		updatePaginatedProviders();
		

	}

	// Method to validate provider data
	/**
	 * Validates the details of a provider and updates the provider's status
	 * accordingly.
	 * 
	 * This method checks the validity of the provider's name, email, telephone
	 * number, and zipcode. If all validations pass, the provider is approved, and
	 * an approval email is sent. If any validation fails, the provider is rejected,
	 * and a rejection email is sent along with the specific error messages. The
	 * provider's status is updated in the database to reflect the approval or
	 * rejection.
	 * 
	 * @param provider The provider whose details are to be validated.
	 */
	public void validateProvider(Provider provider) {
		boolean isValid = true;
		StringBuilder errorMessages = new StringBuilder();

		// Validate Provider Name
		if (provider.getProviderName() == null || provider.getProviderName().trim().isEmpty()) {
			isValid = false;

			errorMessages.append(ErrorMessage.PROVIDER_NAME_EMPTY).append("\n");
		}

		// Validate Email
		if (provider.getEmail() == null || !provider.getEmail().contains("@")
				|| !provider.getEmail().endsWith("gmail.com")) {
			isValid = false;
			errorMessages.append(ErrorMessage.INVALID_EMAIL).append("\n");
		}

		// Validate Telephone Number (10 digits)
		if (provider.getTelephone() == null || !provider.getTelephone().matches("\\d{10}")) {
			isValid = false;
			errorMessages.append(ErrorMessage.INVALID_TELEPHONE).append("\n");
		}

		// Validate Zipcode (6 digits)
		if (provider.getZipcode() == null || !provider.getZipcode().matches("\\d{6}")) {
			isValid = false;
			errorMessages.append(ErrorMessage.INVALID_ZIPCODE).append("\n");

		}
		
		

		// If any validation fails, show error messages
		if (isValid) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Successful Validation. You can approve the provider.", ""));
			// provider.setStatus(LoginStatus.PENDING); // Optionally change status to
			// PENDING after successful validation

			// Update the status to "APPROVED"
			provider.setStatus(LoginStatus.APPROVED);

			// Change validation flag to true
			this.isValidated = true;
			// Update the provider's status in the database
			String updateResult = doctorDao.updateProviderStatus(provider, "APPROVED");

			// Update the map to mark this provider as validated
			validatedProviders.put(provider.getProviderId(), true);
			// method call send email
			MailSendIsh.sendInfo(provider.getEmail(), "PROVIDER REQUEST APPROVED",
					"Congratulations! Your document verification is completed. You have been approved as a provider. Cheers!");
			// method call to update status
			// Update provider status in the database
			// String updateResult = doctorDao.updateProviderStatus(provider, "PENDING");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, updateResult, ""));

			// Optionally, refresh the provider list to show the updated status in the UI
			providerList = null; // Invalidate the cached list

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessages.toString(), ""));
			MailSendIsh.sendInfo(provider.getEmail(), "PROVIDER REQUEST REJECTED",
					"Dear applicant, I hope this email finds you well. After careful review, we regret to inform you that your credentials did not meet the required validation standards, and as a result, your provider request has been rejected.\r\n"
							+ "\r\n"
							+ "Thank you for your interest in partnering with us, and we wish you the best in your future endeavors.\r\n"
							+ "\r\n" + "Regards,\r\n" + "Admin - Healthsure");
			// Update the status to "APPROVED"
			provider.setStatus(LoginStatus.REJECTED);
			validatedProviders.put(provider.getProviderId(), false);
			this.isValidated = false;
			// Update the provider's status in the database
			String updateResult = doctorDao.updateProviderStatus(provider, "REJECTED");

		}
	}

	// Fetching all providers to display in the UI
	/**
	 * Fetches the list of all providers for display in the UI.
	 * 
	 * This method retrieves the list of providers from the database. If the list is
	 * not already cached, it loads the list by calling the
	 * `reviewProviderDetails()` method from the DAO.
	 * 
	 * @return A list of providers.
	 */
	public List<Provider> getProviders() {
		
		 if (providerList == null) {
		        providerList = doctorDao.reviewProviderDetails();
		        // Handle the case if the list is still null after fetching
		        if (providerList == null) {
		            providerList = new ArrayList<>();  // Initialize with an empty list to prevent NullPointerException
		            System.out.println("providerList was null after fetching from DB");
		        }
		        sortList(); // Ensure sorting occurs only when providerList is properly initialized
		    }
		    return providerList;
	}

	// Searching provider by providerId
	/**
	 * Searches for a provider by their provider ID.
	 * 
	 * This method looks for a provider in the database using the provided provider
	 * ID. If the provider is found, a success message is returned. If the provider
	 * is not found, an error message is returned.
	 * 
	 * @param providerId The provider ID to search for.
	 * @return A message indicating whether the provider was found or not.
	 */
	public String searchProviderById(String providerId) {
		selectedProvider = doctorDao.searchProviderById(providerId);
		if (selectedProvider == null) {
			return "Provider not found!";
		}
		return "Provider found!";
	}

	// Updating provider status
	/**
	 * Updates the status of a selected provider.
	 * 
	 * This method updates the status of a provider, as selected by the user. If the
	 * provider and the status are valid, the update is performed in the database,
	 * and a success message is shown. If either the provider or the status is
	 * missing, an appropriate message is displayed.
	 * 
	 * @return A message indicating whether the status was successfully updated or
	 *         if a provider or status was not selected.
	 */
	public String updateProviderStatus() {
		if (selectedProvider != null && providerStatus != null) {
			String result = doctorDao.updateProviderStatus(selectedProvider, providerStatus);
			FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(result));
			return "Status updated successfully!";
		}
		return "Please select a provider and a status.";
	}

	// Loading providers and setting up pagination
	/**
	 * Loads the list of all providers and sets up pagination.
	 * 
	 * This method loads all the providers from the database and calculates the
	 * total number of pages based on the page size. It then updates the paginated
	 * list to display providers for the current page.
	 */
	private void loadProviders() {
		providerList = doctorDao.reviewProviderDetails(); // Load all providers
		totalPages = (int) Math.ceil((double) providerList.size() / pageSize);
		updatePaginatedProviders(); // Update the paginated list
	}

	// Update the paginated list based on current page
	/**
	 * Updates the paginated list based on the current page and page size.
	 * 
	 * This method updates the list of providers that should be displayed on the
	 * current page. It calculates the start and end indices based on the current
	 * page and the page size, and then creates a sublist containing only the
	 * providers for that page.
	 */
	public void updatePaginatedProviders() {
		sortList();
		int start = page * pageSize;
		int end = Math.min(start + pageSize, providerList.size());
		paginatedProviders = providerList.subList(start, end);

	}

	// Pagination logic to go to the next page
	/**
	 * Navigates to the next page of providers.
	 * 
	 * This method increments the page number and updates the paginated list of
	 * providers. It checks whether there are more pages to display, and if so, it
	 * loads the providers for the next page.
	 * 
	 * @return null, as no navigation is required, just an update of the view.
	 */
	public String nextPage() {
		if (page < totalPages - 1) {
			page++; // Increment the page number
			loadProvidersForCurrentPage(); // Load providers for the new page
		}
		return null; // No navigation needed, just update the view
	}

	/**
	 * Loads the providers for the current page and updates the paginated list.
	 * 
	 * This method retrieves the list of all providers from the database and
	 * calculates which providers should be displayed on the current page based on
	 * the page size and the current page number.
	 */
	private void loadProvidersForCurrentPage() {
		try {
			List<Provider> allProviders = doctorDao.reviewProviderDetails(); // Get all providers
			totalPages = (int) Math.ceil(allProviders.size() / (double) PAGE_SIZE);

			int startIndex = page * PAGE_SIZE;
			int endIndex = Math.min(startIndex + PAGE_SIZE, allProviders.size());
			paginatedProviders = allProviders.subList(startIndex, endIndex);
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error occurred while loading providers for current page", e);

		}
	}

	// Pagination logic to go to the previous page
	/**
	 * Navigates to the previous page of providers.
	 * 
	 * This method decrements the page number and updates the paginated list of
	 * providers. It checks whether there are previous pages to display, and if so,
	 * it loads the providers for the previous page.
	 * 
	 * @return null, as no navigation is required, just an update of the view.
	 */
	public String previousPage() {
		if (page > 0) {
			page--; // Decrement the page number
			loadProvidersForCurrentPage(); // Load providers for the new page
		}
		return null; // No navigation needed, just update the view
	}

	// Provider Search and Inquiry
	// Method to reset search fields
	public void resetSearchFields() {
		this.searchType = null; // Reset search type
		this.searchCriteria = null; // Reset search criteria
		this.searchInput = null; // Clear search input
		this.searchResults = Collections.emptyList(); // Clear search results
		this.searchResultsP = Collections.emptyList(); // Clear provider results
	}

	// Method to handle the search logic for Doctors
	/**
	 * Handles the search logic based on the selected search type (Doctor or
	 * Provider).
	 * 
	 * This method checks the search type and calls the appropriate search function
	 * (searchDoctors or searchProviders) depending on whether the user is searching
	 * for a doctor or a provider. If search criteria or input is missing, it resets
	 * the search results. If valid results are found, it redirects to the results
	 * page. If no results are found, an error message is displayed, and the user
	 * remains on the same page.
	 * 
	 * @return A string representing the navigation outcome (redirects to results
	 *         page or stays on the same page).
	 */
	public String search() {

		// Step 1: Validate if searchType is null or empty
		if (searchType == null || searchType.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Select A Search Type.", null));
			return null; // Stay on the same page, don't proceed with search
		}

		// Step 2: Validate if searchCriteria is null or empty
		if (searchCriteria == null || searchCriteria.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Select A Criteria.", null));
			return null; // Stay on the same page, don't proceed with search
		}

		// Step 3: Validate if searchInput is null or empty
		if (searchInput == null || searchInput.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter A Value", null));
			return null; // Stay on the same page, don't proceed with search
		}

		// Step 4: If validation failed, don't proceed further (search)
		if (FacesContext.getCurrentInstance().isValidationFailed()) {
			return null; // Stay on the same page if validation failed
		}

		// Step 5: Clear previous search results
		searchResults = Collections.emptyList();
		searchResultsP = Collections.emptyList();

		// Step 6: Perform search based on the searchType
		if ("doctor".equals(searchType)) {
			searchDoctors(); // Method to search doctors
			System.out.println("Doctor Search Results: " + searchResults); //debugging line
		} else if ("provider".equals(searchType)) {
			searchProviders(); // Method to search providers
			System.out.println("Provider Search Results: " + searchResultsP); //debugging line 
		}

		// Step 7: If results are found, proceed to the results page
		if ((searchResults != null && !searchResults.isEmpty())
				|| (searchResultsP != null && !searchResultsP.isEmpty())) {
			return "Provider_Search_And_Inquiry_Results?faces-redirect=true"; // Proceed to results page
		}

		// Step 8: If validation passed but no results found, show the "No results
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No results found for this entry."));

		return null; // Stay on the same page
	}

	// Method to reset the fields when navigating back to the search page
	public String goToSearchPage() {
		resetSearchFields(); // Reset fields before navigating
		return "Provider_Search_And_Inquiry?faces-redirect=true"; // Navigate back to search page
	}

	// Search method for doctors based on dynamic search criteria
	/**
	 * Searches for doctors based on the dynamic search criteria (e.g., Doctor ID,
	 * Name, Specialization, License Number).
	 * 
	 * This method first validates the input based on the selected search criteria.
	 * For each search criterion, appropriate validations are performed, such as
	 * checking the format of doctor ID, ensuring the doctor's name does not contain
	 * digits, and verifying the specialization against a predefined list. If the
	 * validation fails, an error message is displayed, and the search results are
	 * cleared. After successful validation, the search is performed using the
	 * `doctorDao.searchDoctorDao()` method.
	 * 
	 * @return void This method does not return anything but updates the
	 *         `searchResults` list with the search results.
	 */
	public void searchDoctors() {

		if (searchCriteria != null && !searchCriteria.isEmpty() && searchInput != null && !searchInput.isEmpty()) {

			// Validate the input based on the selected search criteria
			boolean isValid = true;
			String errorMessage = "";

			// Normalize the input by trimming and converting to lowercase
			String normalizedInput = searchInput.trim().toLowerCase();

			switch (searchCriteria) {
			case "doctorId":

				// Doctor ID must start with 'D' and have 3 numbers after it
				if (!normalizedInput.matches("d\\d{3}")) {
					isValid = false;
					errorMessage = ErrorMessage.INVALID_DOCTOR_ID;
				}
				break;

			case "doctorName":
				// Doctor Name must be at least 5 characters long and should not contain numbers
				if (normalizedInput.length() < 5) {
					isValid = false;
					errorMessage = ErrorMessage.INVALID_DOCTOR_NAME_LENGTH;

				} else if (!normalizedInput.matches("[a-zA-Z]+")) { // Only alphabets, no digits or special characters
					isValid = false;
					errorMessage = ErrorMessage.INVALID_DOCTOR_NAME; // You can customize the error message accordingly
				}
				break;
			case "specialization":
				// Normalize the input: remove spaces and convert to lowercase
				String normalizedSpecialization = searchInput.replaceAll("\\s+", "").toLowerCase();

				// List of valid medical categories (You can add more as needed)
				Set<String> validSpecializations = new HashSet<>();
				validSpecializations.add("cardiology");
				validSpecializations.add("neurology");
				validSpecializations.add("dermatology");
				validSpecializations.add("orthopedics");
				validSpecializations.add("pediatrics");
				validSpecializations.add("psychiatry");
				validSpecializations.add("gynecology");
				validSpecializations.add("generalmedicine");
				validSpecializations.add("radiology");
				validSpecializations.add("oncology");
				validSpecializations.add("ent");
				validSpecializations.add("urology");
				validSpecializations.add("gastroenterology");
				validSpecializations.add("endocrinology");
				validSpecializations.add("pulmonology");
				
				// Check if the input matches any valid specialization by first 5 characters
			    boolean matchFound = false;
			    for (String validSpecialization : validSpecializations) {
			        // Compare first 5 characters of the input with each valid specialization
			        if (validSpecialization.toLowerCase().startsWith(normalizedSpecialization.substring(0, Math.min(normalizedSpecialization.length(), 5)))) {
			            matchFound = true;
			            break;
			        }
			    }

			    // If no match found, set error message
			    if (!matchFound) {
			        isValid = false;
			        errorMessage = ErrorMessage.INVALID_SPECIALIZATION;
			    }
			    break;
//				// Check if the input matches one of the valid specializations
//				if (!validSpecializations.contains(normalizedSpecialization)) {
//					isValid = false;
//					errorMessage = ErrorMessage.INVALID_SPECIALIZATION;
//
//				}
//				break;

			case "licenseNo":
				// License number must start with 'L' and have 3 numbers after it
				if (!searchInput.matches("L\\d{3}")) {
					isValid = false;
					errorMessage = ErrorMessage.INVALID_LICENSE_NO;

				}
				break;

//			case "providerId":
//				// Provider ID must start with 'P' and have 3 numbers after it
//				if (!searchInput.matches("P\\d{3}")) {
//					isValid = false;
//					errorMessage = ErrorMessage.INVALID_PROVIDER_ID;
//
//				}
//				break;

			default:
				isValid = false;
				errorMessage = ErrorMessage.INVALID_SEARCH_CRITERIA;

				break;
			}

			// If validation fails, set the error message and return early
			if (!isValid) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage));
				searchResults = Collections.emptyList(); // Reset search results
				return; // Exit the method early to prevent search from happening
			}

			switch (searchCriteria) {
			case "doctorId":
				searchResults = doctorDao.searchDoctorDao(normalizedInput, null, null, null, null);
				this.doctor = null;
				break;
			case "doctorName":
				searchResults = doctorDao.searchDoctorDao(null, normalizedInput, null, null, null);
				break;
			case "specialization":
				searchResults = doctorDao.searchDoctorDao(null, null, null, normalizedInput, null);
				break;
			case "licenseNo":
				searchResults = doctorDao.searchDoctorDao(null, null, null, null, normalizedInput);
				break;
			default:
				searchResults = Collections.emptyList(); // In case the search criteria is invalid
			}
		} else {
			searchResults = Collections.emptyList(); // Empty results if criteria or input is empty
		}

		// Optional: Sorting after search
		sortList();
	}

	// Search method for providers based on dynamic search criteria
	/**
	 * Searches for providers based on the dynamic search criteria (e.g., Provider
	 * ID, Name, City).
	 * 
	 * This method validates the input based on the selected search criteria. For
	 * each search criterion, input validation is performed, such as checking if the
	 * provider ID follows the correct format, ensuring the provider's name is
	 * sufficiently long and does not contain digits, and so on. If any validation
	 * fails, an error message is displayed, and the search results are cleared.
	 * After validation, the search is performed using the
	 * `doctorDao.searchProviderDao()` method.
	 * 
	 * @return void This method does not return anything but updates the
	 *         `searchResultsP` list with the search results.
	 */
	private void searchProviders() {
		if (searchCriteria != null && !searchCriteria.isEmpty() && searchInput != null && !searchInput.isEmpty()) {

			// Validate the input based on the selected search criteria
			boolean isValid = true;
			String errorMessage = "";
			// Normalize the input by trimming, converting to lowercase, and removing spaces
			String normalizedInput = searchInput.trim().toLowerCase().replaceAll("\\s+", "");
			switch (searchCriteria) {
			case "providerId":
				// Provider ID must start with 'P' and have 3 numbers after it
				if (!normalizedInput.matches("p\\d{3}")) {
					isValid = false;
					errorMessage = ErrorMessage.INVALID_PROVIDER_ID;
				}
				break;

			case "providerName":
				// Provider Name must be at least 5 characters long and should not contain
				// numbers
				if (normalizedInput.length() < 5) {
					isValid = false;
					errorMessage = ErrorMessage.PROVIDER_NAME_LENGTH;

				} else if (!normalizedInput.matches("[a-zA-Z]+")) { // Only alphabets, no digits or special characters
					isValid = false;
					errorMessage = ErrorMessage.INVALID_PROVIDER_NAME; // You can customize the error message
																		// accordingly
				}
				
				break;

			default:
				isValid = false;

				errorMessage = ErrorMessage.INVALID_SEARCH_CRITERIA;
				break;
			}

			// If validation fails, set the error message and return early
			if (!isValid) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage));
				searchResultsP = Collections.emptyList(); // Reset search results
				return; // Exit the method early to prevent search from happening
			}

			switch (searchCriteria) {
			case "providerId":
				searchResultsP = doctorDao.searchProviderDao(normalizedInput, null, null);
				break;
			case "providerName":
				searchResultsP = doctorDao.searchProviderDao(null, normalizedInput, null);
				break;
			case "city":
				searchResultsP = doctorDao.searchProviderDao(null, null, normalizedInput);
				break;

			default:
				searchResultsP = Collections.emptyList(); // Invalid search criteria for provider
			}
		} else {
			searchResultsP = Collections.emptyList(); // Empty results if criteria or input is empty
		}
	}

	// Perform the sorting of the doctor list
	private void sortList1() {
		if (sortField == null || searchResults == null) {
			return;
		}

		System.out.println("Sorting method called: " + ascending + " Sorted Field: " + sortField);

		// Sorting by the field dynamically
		Collections.sort(searchResults, (e1, e2) -> {
			try {
				// If the sorting field is 'providerId', handle it specially as it's in the
				// 'Provider' class
				if ("providerId".equals(sortField)) {
					// Access the 'providers' field in the Doctors class
					Field providersField = Doctors.class.getDeclaredField("providers");
					providersField.setAccessible(true);

					// Get the Provider objects from the Doctor instances
					Object provider1 = providersField.get(e1);
					Object provider2 = providersField.get(e2);

					// Access the 'providerId' field in the Provider class
					Field providerIdField = provider1.getClass().getDeclaredField("providerId");
					providerIdField.setAccessible(true);

					// Get the values of 'providerId' for both providers
					Comparable v1 = (Comparable) providerIdField.get(provider1);
					Comparable v2 = (Comparable) providerIdField.get(provider2);

					// Null-safe comparison logic
					if (v1 == null && v2 == null)
						return 0;
					if (v1 == null)
						return ascending ? -1 : 1;
					if (v2 == null)
						return ascending ? 1 : -1;

					return ascending ? v1.compareTo(v2) : v2.compareTo(v1); // Compare based on direction
				} else {
					// Default case for sorting by other fields (not 'providerId')
					Field f = Doctors.class.getDeclaredField(sortField); // Get the field by name
					f.setAccessible(true); // Make it accessible if it's private
					Comparable v1 = (Comparable) f.get(e1); // Get the value for e1
					Comparable v2 = (Comparable) f.get(e2); // Get the value for e2
					return ascending ? v1.compareTo(v2) : v2.compareTo(v1); // Compare based on direction
				}
			} catch (Exception ex) {
				ex.printStackTrace(); // Handle any potential exceptions
				return 0;
			}
		});

		// You can log the sorted list to verify
		searchResults.forEach(System.out::println);
	}

	// Sort by Ascending order (e.g., doctorId)
	public void sortByAsc1(String field) {
		if (!field.equals(sortField) || !ascending) {
			// If this is a new field or current order is not ascending, set ascending
			sortField = field;
			ascending = true;
			currentSort = "asc";
			System.out.println("Sorting Ascending by " + field);
		}
		sortList1(); // Apply the sorting
	}

	// Sort by Descending order (e.g., doctorId)
	public void sortByDesc1(String field) {
		if (!field.equals(sortField) || ascending) {
			// If this is a new field or current order is not descending, set descending
			sortField = field;
			ascending = false;
			currentSort = "desc";
			System.out.println("Sorting Descending by " + field);
		}
		sortList1(); // Apply the sorting
	}

	// Perform the sorting of the provider list
	private void sortList2() {
		if (sortField == null || searchResultsP == null) {
			return;
		}

		System.out.println("Sorting method called for provider in search and inquiry function: " + ascending
				+ " Sorted Field: " + sortField);

		// Sorting by the field dynamically
		Collections.sort(searchResultsP, (e1, e2) -> {
			try {
				Field f = Provider.class.getDeclaredField(sortField); // Get the field by name
				f.setAccessible(true); // Make it accessible if it's private
				Comparable v1 = (Comparable) f.get(e1); // Get the value for e1
				Comparable v2 = (Comparable) f.get(e2); // Get the value for e2
				return ascending ? v1.compareTo(v2) : v2.compareTo(v1); // Compare based on direction
			} catch (Exception ex) {
				ex.printStackTrace(); // Handle any potential exceptions
				return 0;
			}
		});

		// You can log the sorted list to verify
		searchResultsP.forEach(System.out::println);
	}

	// Sort by Ascending order (e.g., doctorId)
	public void sortByAsc2(String field) {
		if (!field.equals(sortField) || !ascending) {
			// If this is a new field or current order is not ascending, set ascending
			sortField = field;
			ascending = true;
			currentSort = "asc";
			System.out.println("Sorting Ascending by " + field);
		}
		sortList2(); // Apply the sorting
	}

	// Sort by Descending order (e.g., doctorId)
	public void sortByDesc2(String field) {
		if (!field.equals(sortField) || ascending) {
			// If this is a new field or current order is not descending, set descending
			sortField = field;
			ascending = false;
			currentSort = "desc";
			System.out.println("Sorting Descending by " + field);
		}
		sortList2(); // Apply the sorting
	}

	public boolean isDetailsUpdated() {
		return detailsUpdated;
	}

	public void setDetailsUpdated(boolean detailsUpdated) {
		this.detailsUpdated = detailsUpdated;
	}

}

//}
