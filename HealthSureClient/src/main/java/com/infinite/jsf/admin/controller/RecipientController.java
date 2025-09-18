/**
 * Controller class for managing Recipient entities in the administrative interface.
 * 
 * This class provides functionality for:
 * - Searching recipients by various criteria (HID, name, mobile number)
 * - Updating recipient details with validation
 * - Sorting and pagination of recipient lists
 * - Navigation between different views
 * - Comprehensive logging for all operations
 */


package com.infinite.jsf.admin.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.validation.ValidationException;

import com.infinite.jsf.admin.dao.RecipientDao;
import com.infinite.jsf.admin.daoImpl.RecipientDaoImpl;
import com.infinite.jsf.recipient.model.Recipient;

public class RecipientController implements Serializable {

	private RecipientDao recipientDao;
	private Recipient recipient;

	// Search fields
	private String searchHid;
	private String searchFirstName;
	private String searchMobile;
//	    private String searchCreatedAt;

	// ----dropdown search fields----
	private String searchType;
	private String searchValue;

	private List<Recipient> recipientList;

	// ----- Fields for sorting -----
	private String sortColumn = "";
	private boolean sortAscending = true;

	// Pagination and search helpers
	private List<Recipient> resultList;
//	    private List<Recipient> paginatedSearchList;
	private int totalPages = 0;

	// ----- Fields for Pagination -----

	private int currentPage = 0;
	private int pageSize = 5;

	// ----- Fields for search field contains mode -----
	private String nameSearchMode = ""; // default mode

	// -----------HYPERLINK 1---------
	public String prepareUpdate() {
		// Read parameter sent from <f:param>
		String hId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hid");

		System.out.println("prepareUpdate() called for hId: " + hId);

		// Fetch from DAO using hId
		this.recipient = recipientDao.searchByHid(hId);
//		this.recipientLoaded = true; // Mark as loaded manually

		return "UpdateRecipientRedirected"; // Navigate to update page
	}

	// ========================Hyperlink 3===========================

	private boolean recipientLoaded = false;

	public Recipient getRecipient() {
		if (!recipientLoaded) {
			loadRecipientForUpdate();
			recipientLoaded = true;
		}
		return this.recipient;
	}

	public void loadRecipientForUpdate() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();

			if (context == null) {
				System.out.println("⚠️ FacesContext is null — skipping.");
				return;
			}

			Map<String, String> params = context.getExternalContext().getRequestParameterMap();

			if (params == null) {
				System.out.println("Parameter map is null.");
				return;
			}

			String hidParam = params.get("hid");

			if (hidParam != null && !hidParam.trim().isEmpty()) {
//				this.recipient = recipientDao.getRecipientByhId(hidParam);
				this.recipient = recipientDao.searchByHid(hidParam);


				
				if (this.recipient != null) {
					this.searchHid = recipient.gethId();
					this.searchFirstName = recipient.getFirstName();
					this.searchMobile = recipient.getMobile();
					System.out.println("Recipient loaded via hid param: " + hidParam);
				} else {
					System.out.println("No recipient found for hid: " + hidParam);
				}
			} else {
				System.out.println("No hid param provided.");
			}

		} catch (Exception e) {
			System.out.println("Exception in loadRecipientForUpdate: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// --- SHOW PAGE NAVIGATION METHOD ---
	public String goToShowPage() {
		this.recipientList = null; // force a fresh fetch
		this.sortColumn = "";
		// optional: reset sort
		this.currentPage = 0; // optional: reset pagination
		return "ShowRecipient1"; // target JSF page
	}

	// ----- Getters & Setters -----

	public RecipientDao getRecipientDao() {
		return recipientDao;
	}

	public void setRecipientDao(RecipientDao recipientDao) {
		this.recipientDao = recipientDao;
	}



	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	// ===============HYPERLINK 2==================
	private Recipient selectedRecipient; // TEMPORARY holder

	// Getter and Setter for selectedRecipient
	public Recipient getSelectedRecipient() {
		return selectedRecipient;
	}

	public void setSelectedRecipient(Recipient selectedRecipient) {
		this.selectedRecipient = selectedRecipient;
	}

	public String getSearchHid() {
		return searchHid;
	}

	public void setSearchHid(String searchHid) {
		this.searchHid = searchHid;
	}

	public String getSearchFirstName() {
		return searchFirstName;
	}

	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}

	public String getSearchMobile() {
		return searchMobile;
	}

	public void setSearchMobile(String searchMobile) {
		this.searchMobile = searchMobile;
	}

	public List<Recipient> getRecipientList() {
		return recipientList;
	}

	// -----For searchType and searchValue for search ----
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setRecipientList(List<Recipient> recipientList) {
		this.recipientList = recipientList;
	}

	// -----Method For searchMode ----
	public String getNameSearchMode() {
		return nameSearchMode;
	}

	public void setNameSearchMode(String nameSearchMode) {
		this.nameSearchMode = nameSearchMode;
	}

	// ----For Sort----
	public String getSortColumn() {
		return sortColumn;
	}

	public boolean isSortAscending() {
		return sortAscending;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	/** List of page indexes (0‑based) that the JSP will iterate over. */
	public List<Integer> getPageIndexes() {
		int pages = getTotalPages();
		List<Integer> list = new ArrayList<Integer>(pages);
		for (int i = 0; i < pages; i++) {
			list.add(i); // ✅ 0-based indexes
		}
		return list;
	}

	/** Action method called by each page number link. */
	public String goToPage(int pageIndex) {
		this.currentPage = pageIndex; // ✅ pageIndex should be 0-based
		return null; // stay on same view
	}

	// --- (OPTIONAL) helper for the CSS class -----------
	public String styleClassForPage(int pageIndex) {
		return (currentPage == pageIndex) ? "active-page" : "page-link";
	}

	// TO Show all sorted and paginated items
	public List<Recipient> getShowRecipient() {
		isSearchContext = false;
		if (recipientDao == null) {
			System.out.println("recipientDao not injected!");
			return Collections.emptyList();
		}

		if (recipientList == null) {
			recipientList = recipientDao.showAllRecipients();
//	            resetPagination();  // go to page 0
		}

//	        sortResults(); // Always sort based on current sortColumn + sortAscending
		performSorting();
		return getPaginatedList();
	}

	public String getPageLabel(int pageIndex) {
		return String.valueOf(pageIndex + 1);
	}

	// -----------SORTING----------------

	private boolean isSearchContext = false;

	// Add these helper methods at the class level
	private int compareStrings(String s1, String s2) {
		if (s1 == null && s2 == null)
			return 0;
		if (s1 == null)
			return -1;
		if (s2 == null)
			return 1;
		return s1.compareToIgnoreCase(s2);
	}

	private int compareDates(Date d1, Date d2) {
		if (d1 == null && d2 == null)
			return 0;
		if (d1 == null)
			return -1;
		if (d2 == null)
			return 1;
		return d1.compareTo(d2);
	}

	// sorting methods separated by arrows icon
	public String sortByAsc(String column) {
		this.sortColumn = column;
		this.sortAscending = true;
		performSorting();
		resetPagination();
		return null;

	}

	public String sortByDesc(String column) {
		this.sortColumn = column;
		this.sortAscending = false;
		performSorting();
		resetPagination(); // Optional
		return null;
	}

	private void performSorting() {
		if (searchResultList != null && !searchResultList.isEmpty()) {
			sortSearchResults();
		} else if (recipientList != null && !recipientList.isEmpty()) {
			sortResults(); // Handle regular list sorting
		}
	}

	public String sortBy(String column) {
		if (column.equals(this.sortColumn)) {
			this.sortAscending = !this.sortAscending;
		} else {
			this.sortColumn = column;
			this.sortAscending = true;
		}
//	        2
		performSorting();
//	        sortResults();
		resetPagination();
		currentPage = 0;
		return null;
	}

	// ===============sorting method for search results===================
	private void sortSearchResults() {
		if (searchResultList == null || searchResultList.isEmpty()) {
			return;
		}

		Collections.sort(searchResultList, new Comparator<Recipient>() {
			public int compare(Recipient r1, Recipient r2) {
				// Reuse the same comparison logic from sortResults()
				if (r1 == null && r2 == null)
					return 0;
				if (r1 == null)
					return -1;
				if (r2 == null)
					return 1;

				int result = 0;
				try {
					switch (sortColumn) {
					case "hId":
						result = compareStrings(r1.gethId(), r2.gethId());
						break;
					case "firstName":
						result = compareStrings(r1.getFirstName(), r2.getFirstName());
						break;
					case "lastName":
						result = compareStrings(r1.getLastName(), r2.getLastName());
						break;
					case "mobile":
						result = compareStrings(r1.getMobile(), r2.getMobile());
						break;
					case "email":
						result = compareStrings(r1.getEmail(), r2.getEmail());
						break;
					case "address":
						result = compareStrings(r1.getAddress(), r2.getAddress());
						break;
					case "createdAt":
						result = compareDates(r1.getCreatedAt(), r2.getCreatedAt());
						break;
					case "lastModifiedAt":
						result = compareDates(r1.getLastModifiedAt(), r2.getLastModifiedAt());
						break;
					default:
						result = 0;
					}
				} catch (NullPointerException e) {
					result = 0;
				}
				return sortAscending ? result : -result;
			}
		});
	}

	
	
	
	
	// =====================sorting method for show page=========================
	private void sortResults() {
		if (recipientList == null || recipientList.isEmpty()) {
			return;
		}

		Collections.sort(recipientList, new Comparator<Recipient>() {
			public int compare(Recipient r1, Recipient r2) {
				// Handle null objects
				if (r1 == null && r2 == null)
					return 0;
				if (r1 == null)
					return -1;
				if (r2 == null)
					return 1;

				int result = 0;
				try {
					switch (sortColumn) {
					case "hId":
						result = compareStrings(r1.gethId(), r2.gethId());
						break;
					case "firstName":
						result = compareStrings(r1.getFirstName(), r2.getFirstName());
						break;
					case "lastName":
						result = compareStrings(r1.getLastName(), r2.getLastName());
						break;
					case "mobile":
						result = compareStrings(r1.getMobile(), r2.getMobile());
						break;
					case "email":
						result = compareStrings(r1.getEmail(), r2.getEmail());
						break;
					case "address":
						result = compareStrings(r1.getAddress(), r2.getAddress());
						break;
					case "createdAt":
						result = compareDates(r1.getCreatedAt(), r2.getCreatedAt());
						break;
					case "lastModifiedAt":
						result = compareDates(r1.getLastModifiedAt(), r2.getLastModifiedAt());
						break;
					default:
						result = 0;
					}

				} catch (NullPointerException e) {
					result = 0; // Fallback if any field is null
				}
				return sortAscending ? result : -result;
			}

			private int compareStrings(String s1, String s2) {
				if (s1 == null && s2 == null)
					return 0;
				if (s1 == null)
					return -1;
				if (s2 == null)
					return 1;
				return s1.compareToIgnoreCase(s2);
			}

			private int compareDates(Date d1, Date d2) {
				if (d1 == null && d2 == null)
					return 0;
				if (d1 == null)
					return -1;
				if (d2 == null)
					return 1;
				return d1.compareTo(d2);
			}
		});
	}

	// Add these new methods
	public boolean isColumnSorted(String column) {
		return column.equals(this.sortColumn);
	}

	public boolean isAscending() {
		return this.sortAscending;
	}

	public boolean isDescending() {
		return this.sortColumn != null && !this.sortAscending;
	}

	// -----------Pagination Methods---------------

//	    current page =1;
//	    reset pagination();

	public List<Recipient> getPaginatedList() {
		if (recipientList == null || recipientList.isEmpty()) {
			return Collections.emptyList();
		}

		// Ensure currentPage is within valid bounds for 0-based system
		int totalPages = getTotalPages();
		if (currentPage >= totalPages) {
			currentPage = Math.max(0, totalPages - 1); // Go to last page if out of bounds
		}
		if (currentPage < 0) {
			currentPage = 0; // Ensure not negative
		}

		int start = currentPage * pageSize;
		int end = Math.min(start + pageSize, recipientList.size());

		return recipientList.subList(start, end);
	}

	public int getTotalPages() {
		if (recipientList == null || recipientList.isEmpty()) {
			return 1; // At least 1 page even if empty
		}
		return (int) Math.ceil((double) recipientList.size() / pageSize);
	}

	// Navigation methods for 0-based system
	public boolean isNextButtonDisabled() {
		return recipientList == null || (currentPage >= getTotalPages() - 1);
	}

	public boolean isPreviousButtonDisabled() {
		return currentPage <= 0;
	}

	public String nextPage() {
		if (!isNextButtonDisabled()) {
			currentPage++;
		}
		return null;
	}

	public String previousPage() {
		if (!isPreviousButtonDisabled()) {
			currentPage--;
		}
		return null;
	}

	// For UI display - convert 0-based to 1-based for user display
	public String getPageNumberDisplay() {
		int total = getTotalPages();
		return "Page " + (currentPage + 1) + " of " + total;
	}

	public List<Integer> getPageNumbers() {
		int totalPages = getTotalPages();
		List<Integer> numbers = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++) {
			numbers.add(i); // 1-based for UI
		}
		return numbers;
	}

	public void calculateTotalPages() {
		if (resultList != null && !resultList.isEmpty()) {
			totalPages = (int) Math.ceil((double) resultList.size() / pageSize);
		} else {
			totalPages = 1;
		}
	}

	public List<Recipient> getPaginatedSearchResults() {
		if (resultList == null || resultList.isEmpty()) {
			return Collections.emptyList();
		}

		// Ensure currentPage is within valid bounds for 0-based system
		int searchTotalPages = getSearchTotalPages();
		if (currentPage >= searchTotalPages) {
			currentPage = Math.max(0, searchTotalPages - 1);
		}
		if (currentPage < 0) {
			currentPage = 0;
		}

		int start = currentPage * pageSize;
		int end = Math.min(start + pageSize, resultList.size());

		return resultList.subList(start, end);
	}

	public int getSearchTotalPages() {
		if (resultList == null || resultList.isEmpty()) {
			return 1;
		}
		return (int) Math.ceil((double) resultList.size() / pageSize);
	}

	public String goToSearchPage() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String pageParam = params.get("page");

		try {
			currentPage = Integer.parseInt(pageParam);
		} catch (NumberFormatException e) {
			currentPage = 0; // Default to first page if conversion fails
		}
		return null;
	}

	public List<Integer> getSearchPageIndexes() {
		int pages = getSearchTotalPages(); // ✅ Use method, not variable
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < pages; i++) { // ✅ Use pages, not searchTotalPages
			list.add(i); // Returns List of Integers
		}
		return list;
	}

	public void calculateSearchTotalPages() {
		if (resultList != null && !resultList.isEmpty()) {
			searchTotalPages = (int) Math.ceil((double) resultList.size() / pageSize);
		} else {
			searchTotalPages = 1;
		}
	}

	// ===========methods for disabling the paginations buttons=============
	public boolean isFirstPage() {
		return currentPage == 0;
	}

	public boolean isLastPage() {
		return currentPage >= getSearchTotalPages() - 1;
	}

	public boolean isOnlyOnePage() {
		return searchPageCount <= 1;
	}

	private boolean firstSearchPageDisabled;
	private boolean lastSearchPageDisabled;

	public boolean lastSearchPageDisabled() {
		return isLastSearchPageDisabled();
	}

	// Add setters if needed
	public void setFirstSearchPageDisabled(boolean firstSearchPageDisabled) {
		this.firstSearchPageDisabled = firstSearchPageDisabled;
	}

	public void setLastSearchPageDisabled(boolean lastSearchPageDisabled) {
		this.lastSearchPageDisabled = lastSearchPageDisabled;
	}

	public boolean shouldDisablePrevious() {
		return firstSearchPageDisabled || isFirstPage() || isOnlyOnePage();
	}

	public boolean shouldDisableNext() {
		return lastSearchPageDisabled || isLastPage() || isOnlyOnePage();
	}

	public boolean isPreviousDisabled() {
		return isFirstSearchPageDisabled() || isFirstPage() || isOnlyOnePage();
	}

	public boolean isNextDisabled() {
		return currentPage >= getSearchTotalPages() - 1 || resultList == null || resultList.isEmpty();
	}

//	    ===========================================

	// ===== SEARCH PAGINATION FIELDS =====
	private int searchCurrentPageIndex = 0;
	private final int searchResultsPerPage = 5;
	private int searchPageCount = 1;
	private List<Recipient> searchResultList = new ArrayList<>();
//	    private boolean searchPerformed = false;

	// ===== PROPERTY ACCESSORS =====
	public int getSearchCurrentPageIndex() {
		return searchCurrentPageIndex;
	}

	public void setSearchCurrentPageIndex(int searchCurrentPageIndex) {
		this.searchCurrentPageIndex = searchCurrentPageIndex;
	}

	public int getSearchResultsPerPage() {
		return searchResultsPerPage;
	}

	public int getSearchPageCount() {
		return searchPageCount;
	}

	public List<Recipient> getSearchResultList() {
		return searchResultList;
	}

	// ===== SEARCH PAGINATION METHODS =====
	public List<Recipient> getCurrentSearchResultsPage() {
		if (searchResultList == null || searchResultList.isEmpty()) {
			sortResults();
			return Collections.emptyList();
		}

		searchCurrentPageIndex = Math.min(searchCurrentPageIndex, computeSearchPageCount() - 1);
		searchCurrentPageIndex = Math.max(searchCurrentPageIndex, 0);

		int start = searchCurrentPageIndex * searchResultsPerPage;
		int end = Math.min(start + searchResultsPerPage, searchResultList.size());

		return searchResultList.subList(start, end);
	}

	private void resetPagination() {
		searchCurrentPageIndex = 0; // Reset to first page
		searchPageCount = computeSearchPageCount(); // Recalculate page count
	}

	public String navigateToNextSearchPage() {
		if (searchCurrentPageIndex < computeSearchPageCount() - 1) {
			searchCurrentPageIndex++;
		}
		return null;
	}

	public String navigateToPreviousSearchPage() {
		if (searchCurrentPageIndex > 0) {
			searchCurrentPageIndex--;
		}
		return null;
	}

	private void updatePageDisabledFlags() {
		firstSearchPageDisabled = (searchCurrentPageIndex == 0);
		lastSearchPageDisabled = (searchCurrentPageIndex >= searchPageCount - 1);
	}

	public String jumpToSearchPage() {
		isSearchContext = true;
		FacesContext context = FacesContext.getCurrentInstance();
		String page = context.getExternalContext().getRequestParameterMap().get("page");

		try {
			int requestedPage = Integer.parseInt(page) - 1;
			searchCurrentPageIndex = Math.max(0, Math.min(requestedPage, computeSearchPageCount() - 1));
		} catch (NumberFormatException e) {
			searchCurrentPageIndex = 0;
		}
		return null;
	}

	public boolean isFirstSearchPageDisabled() {
		return searchCurrentPageIndex == 0 || searchResultList == null || searchResultList.isEmpty();
	}

	public boolean isLastSearchPageDisabled() {
		return searchCurrentPageIndex >= computeSearchPageCount() - 1 || searchResultList == null
				|| searchResultList.isEmpty();
	}

	public List<Integer> getSearchResultsPageNumbers() {
		int total = computeSearchPageCount();
		List<Integer> numbers = new ArrayList<>();
		for (int i = 1; i <= total; i++) {
			numbers.add(i);
		}
		return numbers;
	}

	private int computeSearchPageCount() {
		if (searchResultList == null || searchResultList.isEmpty()) {
			return 1;
		}
		return (int) Math.ceil((double) searchResultList.size() / searchResultsPerPage);
	}

	public void resetSearchResultsPagination() {
		searchCurrentPageIndex = 0;
		searchPageCount = computeSearchPageCount();
	}

	
	
//	    ============================================

	// ----------------Search method--------------------

	private String searchMode = "exact"; // default value
	private String searchOption = "exact"; // Default to exact search

	// Getter and setter for searchOption
	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String search() {
		// Reset search-specific pagination and results
		resetSearchResultsPagination();
		searchResultList = new ArrayList<>();
		searchPerformed = true;

		isSearchContext = true;
		System.out.println("=== STARTING SEARCH ===");
		System.out.println("Search type: " + searchType);
		System.out.println("Search value: " + searchValue);
		System.out.println("Name search mode: " + nameSearchMode);

		FacesContext context = FacesContext.getCurrentInstance();

		if (searchType == null || searchType.trim().isEmpty()) {
			context.addMessage("FormId:searchType",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a search type.", null));
			searchPerformed = false;
			return null;
		}

		// If "Show All" is selected, bypass other validations
		if ("showAll".equals(searchType)) {
			searchResultList = recipientDao.showAllRecipients();
			if (searchResultList.isEmpty()) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "No recipients found in the system.", null));
			}

			// Update pagination
			searchPageCount = computeSearchPageCount();
			return null;
		}

		if (searchValue == null || searchValue.trim().isEmpty()) {
			context.addMessage("FormId:searchValue",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a value for search.", null));
			searchPerformed = false;
			return null;
		}


		switch (searchType) {
		case "hid":
			searchValue = searchValue.trim().toUpperCase();
			searchHid = searchValue;

			if (!searchHid.matches("HID\\d{3}")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Invalid Health ID. HID must be in format HID followed by 3 digits (e.g. HID001)", null));
				searchPerformed = false;
				return null;
			}

			recipient = recipientDao.searchByHid(searchHid);
			searchResultList = (recipient != null) ? Arrays.asList(recipient) : new ArrayList<>();
			break;

			
			
		case "firstName":
			searchValue = searchValue.trim(); // Trim the input
			if (searchValue.isEmpty()) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "First name cannot be empty.", null));
				searchPerformed = false;
				return null;
			}

			// Enhanced validation: only letters, no digits or special characters
			if (!searchValue.matches("^[a-zA-Z\\s]+$")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"First name should contain only alphabets and spaces. No digits or special characters allowed.",
						null));
				searchPerformed = false;
				return null;
			}

			searchFirstName = searchValue;

			// Check which search option is selected
			if ("exact".equals(searchOption)) {
				searchResultList = recipientDao.searchByFirstNameExact(searchFirstName);
				if (searchResultList.isEmpty()) {
					exactSearchFailed = true;
					context.addMessage(null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "No recipient found with the exact name: "
											+ searchFirstName + ". Please try using search mode for partial matching.",
									null));
					searchPerformed = false;
					return null;
				}
			} else if ("useMode".equals(searchOption)) {
				// Use the selected partial search mode
				if (nameSearchMode == null) {
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "select a search mode.", null));
					return null;
				} else {
					switch (nameSearchMode) {
					case "startsWith":
						searchResultList = recipientDao.searchByFirstNameStartsWith(searchFirstName);
						break;
					case "contains":
						searchResultList = recipientDao.searchByFirstNameContains(searchFirstName);
						break;
					default:
						searchResultList = new ArrayList<>();
						break;
					}
				}
			}
				break;
						
			
			
		case "mobile":
			searchValue = searchValue.trim(); // Trim the input
			if (searchValue.isEmpty()) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mobile number cannot be empty.", null));
				searchPerformed = false;
				return null;
			}

			// Enhanced validation: only digits, no letters or special characters
			if (!searchValue.matches("^[0-9]+$")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Mobile number must contain only digits. No letters or special characters allowed.", null));
				searchPerformed = false;
				return null;
			}

			// Original mobile validation
			if (!searchValue.matches("^[6-9][0-9]{9}$")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Mobile number must be exactly 10 digits and cannot start with 0.", null));
				searchPerformed = false;
				return null;
			}

			searchMobile = searchValue;
			searchResultList = recipientDao.searchByMobile(searchMobile);
			break;

		default:
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown search type.", null));
			recipientList = new ArrayList<>();
			searchPerformed = false;
			return null;
		}

		if (searchResultList.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "No recipients found with the given value.", null));
		}

		// Update pagination
		searchPageCount = computeSearchPageCount();

		return null;


	}

	private int searchTotalPages = 1; // Total pages available



	public String selectRecipientForUpdate(Recipient selectedRecipient) {
		this.recipient = selectedRecipient;
		return "updateRecipientRedirected.jsp"; // actual page name without .xhtml in nav-rule
	}



	public String updateFormVisibility() {
		// This method just updates the component tree without performing a search
		this.searchValue = null;
		return null;
	}



	public List<Recipient> getResultList() {
		if (recipientList != null && !recipientList.isEmpty()) {
			return recipientList;
		} else if (recipient != null) {
			return Arrays.asList(recipient);
		}
		return Collections.emptyList();
	}

	// Add these properties
	private boolean exactSearchFailed = false;

	// Add getters and setters
	public boolean isExactSearchFailed() {
		return exactSearchFailed;
	}

	public void setExactSearchFailed(boolean exactSearchFailed) {
		this.exactSearchFailed = exactSearchFailed;
	}

	// ===========================================================================================================
//	    ===============================================================================

	private String capitalizeName(String name) {
		if (name == null || name.isEmpty()) {
			return name;
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	// --------Update method WITH VALIDATIONS---------

	private boolean validateRecipient(Recipient recipient, FacesContext context) {
		boolean isValid = true;
		
		// -------- Health ID --------
		if (recipient.gethId() == null || recipient.gethId().trim().isEmpty()) {
			context.addMessage("hId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health ID is required.", null));
			isValid = false;
		} else if (!recipient.gethId().trim().matches("^HID\\d{3}$")) {
			context.addMessage("hId",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health ID must be in format HID001.", null));
			isValid = false;
		}

		// -------- First Name --------
		if (recipient.getFirstName() == null || recipient.getFirstName().trim().isEmpty()) {
			context.addMessage("firstName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "First Name is required.", null));
			isValid = false;
		} else {
			// Clean up the first name
			String cleanedFirstName = recipient.getFirstName().trim();
			recipient.setFirstName(capitalizeName(cleanedFirstName));

			// Now validate the cleaned name
			if (!cleanedFirstName.matches("^[a-zA-Z]+$")) {
				context.addMessage("firstName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"First Name must contain only letters (no digits or special characters).", null));
				isValid = false;
			} else if (cleanedFirstName.length() < 2) {
				context.addMessage("firstName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"First Name must be at least 2 characters long.", null));
				isValid = false;
			}
		}

		// -------- Last Name --------
		if (recipient.getLastName() == null || recipient.getLastName().trim().isEmpty()) {
			context.addMessage("lastName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Last Name is required.", null));
			isValid = false;
		} else {
			// Clean up the last name
			String cleanedLastName = recipient.getLastName().trim();
			recipient.setLastName(capitalizeName(cleanedLastName));

			// Now validate the cleaned name
			if (!cleanedLastName.matches("^[A-Za-z]+$")) {
				context.addMessage("lastName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Last Name must contain only letters (no digits or special characters).", null));
				isValid = false;
			} else if (cleanedLastName.length() < 2) {
				context.addMessage("lastName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Last Name must be at least 2 characters.", null));
				isValid = false;
			}
		}

		// -------- Mobile --------
		if (recipient.getMobile() == null || recipient.getMobile().trim().isEmpty()) {
			context.addMessage("mobile",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mobile number is required.", null));
			isValid = false;
		} else if (!recipient.getMobile().trim().matches("^[1-9]\\d{9}$")) {
			context.addMessage("mobile", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Mobile number must be 10 digits, cannot start with 0, and cannot contain letters or special characters.",
					null));
			isValid = false;
		}


		// -------- Address --------
		if (recipient.getAddress() == null || recipient.getAddress().trim().isEmpty()) {
			context.addMessage("address", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Address is required.", null));
			isValid = false;
		} else {
			String trimmedAddress = recipient.getAddress().trim();

			if (!trimmedAddress.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9\\s,.-]{10,150}$")) {
				context.addMessage("address", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Address must be 10–150 characters long, contain both letters and numbers, and may include spaces, commas, hyphens, and periods.",
						null));
				isValid = false;
			}
		}

		// -------- EMAIL --------
		String email = recipient.getEmail();
		if (email == null || email.trim().isEmpty()) {
			context.addMessage("email",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email field is required.", null));
			isValid = false;
		} else if (!email.trim().matches("^[A-Za-z][A-Za-z0-9]{0,11}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			context.addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Invalid email format. Username must start with a letter, can contain only letters/digits (max 12 chars), and must follow standard domain format.",
					null));
			isValid = false;
		}

		return isValid;
	}

	// ----------UPDATE METHOD------------
	public String updateRecipient() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Flash flash = externalContext.getFlash();
		flash.setKeepMessages(true); // This ensures messages survive redirect
		

		if (!validateRecipient(recipient, context)) {
			return null;
		}
		
		
//		if(!submittedHid.equals(searchHid)) {
//			throw new ValidationException("HID mismatch. Please check if the details of the recipient are not duplicated")
//		}

		try {
			boolean updated = recipientDao.updateRecipient(recipient);
			this.editMode = false;

			if (updated) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Recipient details updated successfully", null));

				// To Clear the input fields
				recipient = null;
				searchHid = null;

				
				
				
				
				return "Recipient_Update_MemberDetails?faces-redirect=true";
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update failed. Please try again.", null));
			}
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"System error during update: " + e.getMessage(), null));
		}

		return null;
	}

	
	
	
	
	
	public String navigateToUpdate() {
		return "UpdateRecipient.jsp?faces-redirect=true";
	}

	public String navigateToView() {
		return "ViewRecipient.jsp?faces-redirect=true";
	}

	public String goToUpdatePage() {
		if (recipient == null || recipient.gethId() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Please search and select a recipient first."));
			return "Recipient_Search_MemberDetails?faces-redirect=true";
		}
		return "UpdateRecipientRedirected?faces-redirect=true";
	}

//	    4
	public void resetUpdate() {
	    try {
	        if (recipient != null && recipient.gethId() != null) {
	            // Get fresh copy
//	            Recipient freshCopy = recipientDao.getRecipientByhId(recipient.gethId());
	            Recipient freshCopy = recipientDao.searchByHid(recipient.gethId());
	    		this.editMode = false;

	            
	            
	            // Overwrite local object
	            this.searchHid = freshCopy.gethId();
	            recipient.sethId(freshCopy.gethId());
	            recipient.setFirstName(freshCopy.getFirstName());
	            recipient.setLastName(freshCopy.getLastName());
	            recipient.setMobile(freshCopy.getMobile());
	            recipient.setEmail(freshCopy.getEmail());
	            recipient.setAddress(freshCopy.getAddress());
	            
	            

	            FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Changes discarded successfully", null));
	        }
	    } catch (Exception e) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error discarding changes", null));
	    }
	    
	    // No redirect - stay on same page
	}

	
	
	
    private boolean editMode = false;
    
    public String enableEditMode() {
        this.editMode = true;
        return null;
       
    }

    
    public String disableEditMode() {
    	this.editMode = true;
    	return null;
    }
    
    
    public void cancelEdit() {
        this.editMode = false; // go back to read-only
    }

    public boolean isEditMode() {
        return editMode;
    }

	public void fetchRecipientBySearchHid() {
		FacesContext context = FacesContext.getCurrentInstance();

		// Clear previous messages for searchHid
		Iterator<FacesMessage> messages = context.getMessages("searchHid");
		while (messages.hasNext()) {
			messages.next();
			messages.remove();
		}

		// Validate HID
		if (searchHid == null || searchHid.trim().isEmpty()) {
			context.addMessage("searchHid",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health ID is required.", null));
			return;
		}

		searchHid = searchHid.trim().toUpperCase();
		if (!searchHid.matches("^HID\\d{3}$")) {
			context.addMessage("searchHid",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health ID must be in format HID001.", null));
			return;
		}

		// If validation passed, proceed with search
		try {
			this.recipient = recipientDao.searchByHid(searchHid);
			this.editMode= false;

			if (this.recipient != null) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Recipient loaded successfully", ""));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "No recipient found with HID: " + searchHid, ""));
			}
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error searching for recipient: " + e.getMessage(), ""));
		}

		this.searchPerformed = true;

	}

	
	
	
	
	public String goToSearch() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "Recipient_Search_MemberDetails?faces-redirect=true";
	}

	public String goToDashboard() {
		return "AdminDashBoard?faces-redirect=true";
	}

	private boolean searchPerformed = false; // initially false

	public boolean isSearchPerformed() {
		return searchPerformed;
	}

	public void setSearchPerformed(boolean searchPerformed) {
		this.searchPerformed = searchPerformed;
	}

	
	
	// -------reset page--------
	public String resetSearch() {
		isSearchContext = true;
		System.out.println("=== RESETTING SEARCH ===");
		System.out.println("Current values before reset - Type: " + searchType + ", Value: " + searchValue + ", Mode: "
				+ nameSearchMode);

//		this.searchType = null;
		this.searchValue = null;
		this.nameSearchMode = null;
		this.recipient = null;
		this.recipientList = null;
		this.resultList = null;
		this.currentPage = 0;
		this.sortColumn = null;
		this.sortAscending = true;
		this.searchPerformed = false;

		this.searchResultList = new ArrayList<>();
		this.searchCurrentPageIndex = 0;

		// to Force a full refresh of the view
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tableForm"); // Update the entire
																									// table form

		System.out.println(
				"Values after reset - Type: " + searchType + ", Value: " + searchValue + ", Mode: " + nameSearchMode);
		return null;
	}

	// Add this field to track sorting for lastModifiedAt
	private boolean lastModifiedSortAdded = false;

	// Add this method to format the last modified timestamp or show "Not updated
	// yet"
	public String getFormattedLastModified(Date lastModified) {
		if (lastModified == null) {
			return "Not updated yet";
		}
		// Format the date as you prefer
		return new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(lastModified);
	}

}