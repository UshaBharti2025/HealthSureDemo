/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains controller classes that manage user interactions for insurance-related features,
 * including plan creation, coverage options, validations, and data persistence.
 * These controllers act as a bridge between the JSF view layer and backend services and DAOs.
 */

package com.infinite.jsf.insurance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.infinite.jsf.insurance.dao.InsuranceCompanyDao;
import com.infinite.jsf.insurance.dao.InsuranceCoverageOptionDao;
import com.infinite.jsf.insurance.dao.InsurancePlanDao;
import com.infinite.jsf.insurance.dao.MemberPlanRuleDao;
import com.infinite.jsf.insurance.daoImpl.InsuranceCompanyDaoImpl;
import com.infinite.jsf.insurance.daoImpl.InsuranceCoverageOptionDaoImpl;
import com.infinite.jsf.insurance.daoImpl.InsurancePlanDaoImpl;
import com.infinite.jsf.insurance.daoImpl.MemberPlanRuleDaoImpl;
import com.infinite.jsf.insurance.exception.InsuranceCoverageOptionException;
import com.infinite.jsf.insurance.exception.InsurancePlanException;
import com.infinite.jsf.insurance.exception.MemberPlanException;
import com.infinite.jsf.insurance.model.CoveragePlanStatus;
import com.infinite.jsf.insurance.model.CoverageType;
import com.infinite.jsf.insurance.model.CreateInsuranceMessageConstants;
import com.infinite.jsf.insurance.model.Gender;
import com.infinite.jsf.insurance.model.InsuranceCompany;
import com.infinite.jsf.insurance.model.InsuranceCoverageOption;
import com.infinite.jsf.insurance.model.InsurancePlan;
import com.infinite.jsf.insurance.model.MemberPlanRule;
import com.infinite.jsf.insurance.model.PlanType;
import com.infinite.jsf.insurance.model.Relation;

/**
 * This controller class is responsible for handling user interactions related
 * to the creation of insurance entities such as plans, coverage options, and
 * associated rules. It acts as a bridge between the JSF view layer and the
 * backend services managing form submissions, validations, and data
 * persistence.
 *
 * Typical responsibilities include: - Initializing form data - Handling user
 * input and triggering business logic - Managing navigation outcomes -
 * Coordinating with DAO classes to persist insurance-related data
 */
public class CreateInsuranceController {
	private InsuranceCompany insuranceCompany;
	private InsurancePlan insurancePlan;
	private InsuranceCoverageOption coverageOption;
	private InsuranceCoverageOption coverageOption1;
	private InsuranceCoverageOption coverageOption2;
	private InsuranceCoverageOption coverageOption3;
	private MemberPlanRule memberPlanRule;
	private InsuranceCoverageOptionDao insuranceCoverageOptionDao = new InsuranceCoverageOptionDaoImpl();
	private InsurancePlanDao insurancplanDao = new InsurancePlanDaoImpl();
	private MemberPlanRuleDao memberPlanRuleDao = new MemberPlanRuleDaoImpl();
	private List<InsuranceCoverageOption> planwithCovrageDetailsList;
	private int yearsToAdd;
	private List<MemberPlanRule> members;
	private List<InsurancePlan> planList;
	private Map<String, Boolean> relationMap = new HashMap<>();
	List<String> selectedRelations = new ArrayList<String>();
	private boolean isSilver = true;
	private boolean isGold = false;
	private boolean isPlatinum = false;
	private String individualMemberGender;
	CreateInsuranceMessageConstants validationMessages = new CreateInsuranceMessageConstants();
	private InsuranceCompanyDao companyDao = new InsuranceCompanyDaoImpl();
	private static final Logger logger = Logger.getLogger(InsuranceCompanyDaoImpl.class);

//paginatin and sorting

	private int pageSize = 5;
	private int currentPage = 0;
	private String sortField = "planId";
	private boolean sortAscending = true;

	public List<InsurancePlan> getPaginatedPlans() {
		showAllPlan();
		applySorting(); // Ensure sorting is applied before pagination
		int start = currentPage * pageSize;
		int end = Math.min(start + pageSize, planList.size());
		return planList.subList(start, end);
	}

	public void sortBy(String field) {
		if (field.equals(sortField)) {
			sortAscending = !sortAscending;
		} else {
			sortField = field;
			sortAscending = true;
		}
		applySorting();
	}

	public void sortByAsc(String field) {
		sortField = field;
		sortAscending = true;
		applySorting();
	}

	public void sortByDesc(String field) {
		sortField = field;
		sortAscending = false;
		applySorting();
	}

	private void applySorting() {
		if (planList == null || planList.isEmpty())
			return;

		Comparator<InsurancePlan> comparator = getComparator(sortField);
		if (comparator == null)
			return;

		if (!sortAscending) {
			comparator = comparator.reversed();
		}
		planList.sort(comparator);
	}

	private Comparator<InsurancePlan> getComparator(String field) {
		switch (field) {
		case "planName":
			return Comparator.comparing(InsurancePlan::getPlanName, Comparator.nullsLast(String::compareTo));
		case "planType":
			return Comparator.comparing(InsurancePlan::getPlanType, Comparator.nullsLast(Enum::compareTo));
		case "waitingPeriod":
			return Comparator.comparing(InsurancePlan::getWaitingPeriod, Comparator.nullsLast(Integer::compareTo));
		case "expireDate":
			return Comparator.comparing(InsurancePlan::getExpireDate, Comparator.nullsLast(Date::compareTo));
		case "activeOn":
			return Comparator.comparing(InsurancePlan::getActiveOn, Comparator.nullsLast(Date::compareTo));
		case "maximumMemberAllowed":
			return Comparator.comparing(InsurancePlan::getMaximumMemberAllowed,
					Comparator.nullsLast(Integer::compareTo));
		case "availableCoverAmounts":
			return Comparator.comparing(InsurancePlan::getAvailableCoverAmounts,
					Comparator.nullsLast(Double::compareTo));
		case "periodicDiseases":
			return Comparator.comparing(InsurancePlan::getPeriodicDiseases, Comparator.nullsLast(String::compareTo));
		case "description":
			return Comparator.comparing(InsurancePlan::getDescription, Comparator.nullsLast(String::compareTo));
		default: // planId
			return Comparator.comparing(InsurancePlan::getPlanId, Comparator.nullsLast(String::compareTo));
		}
	}

	public void nextPage() {
		if (isNext()) {
			currentPage++;
		}
	}

	public void previousPage() {
		if (isPrevious()) {
			currentPage--;
		}
	}

	public boolean isNext() {
		return (currentPage + 1) * pageSize < planList.size();
	}

	public boolean isPrevious() {
		return currentPage > 0;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) planList.size() / pageSize);
	}

	public int getPageSize() {
		return pageSize;
	}

	// ==========================
	/**
	 * Retrieves and returns a list of all available insurance plans to be displayed
	 * on the dashboard. This method delegates the data fetching to the
	 * InsurancePlan DAO layer.
	 *
	 * @return List of InsurancePlan objects representing all available plans.
	 */

	public List<InsurancePlan> showAllPlan() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			planList = insurancplanDao.showAllPlan();
		} catch (InsurancePlanException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"An error occurred while fetching the data."));
		}
		System.out.println(planList);
		return planList;
	}

	public String navigateToAddInsurance() {
		resetAll();
		System.err.println("navigate : insuranceAddInsuranceCoveragePlan");
		return "insuranceAddInsuranceCoveragePlan?faces:redirect=true";

//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ExternalContext externalContext = facesContext.getExternalContext();
//
//		try {
//			externalContext.redirect("insuranceAddInsuranceCoveragePlan.jsp");
//		} catch (IOException e) {
//			logger.error("not redirecting : insuranceAddInsuranceCoveragePlan ");
//		} 

	}

	/**
	 * Adds a new insurance plan along with predefined coverage options: Silver,
	 * Gold, and Platinum. This method is responsible for creating the base
	 * insurance plan and associating it with standard coverage tiers, each with its
	 * own premium and coverage amount.
	 *
	 * @return Navigation outcome or status string indicating success or failure.
	 */

	public String addSilverOnlyMendatory() {

		FacesContext context = FacesContext.getCurrentInstance();
		logger.info("Prepare insurance plan");
		// Prepare insurance plan
		logger.info("adding the company to insurancePlan");

		// add company (insurance must belong to any company)
		insurancePlan.setInsuranceCompany(insuranceCompany);

		// insurance expire date calculated dynamically using activeOnDate and duration
		if (insurancePlan.getActiveOn() != null) {
			logger.info("adding the expired date using activeOn Date ");

			insurancePlan.setExpireDate(calculateExpiryDate(insurancePlan.getActiveOn(), yearsToAdd));
		}

		// insuranceplan created date will be today
		logger.info("add todays date to a insurace createdOn date  ");
		insurancePlan.setCreatedOn(new Date());

		// Link coverage options to the plan
		logger.info("adding silver option to the insurance plan");
		coverageOption1.setInsurancePlan(insurancePlan);
		logger.info("adding gold option to the insurance plan");
		coverageOption2.setInsurancePlan(insurancePlan);
		logger.info("adding platinum option to the insurance plan");
		coverageOption3.setInsurancePlan(insurancePlan);

		// Step 1: Validate Silver (mandatory)
		logger.info("checking the condition for silve is mandatory");
		boolean silverValid = isSilver && validateInsurancePlanWithFacesMessage(insurancePlan)
				&& validateInsuranceMeberRelationsWithFacesMessage(insurancePlan)
				&& validateInsuranceCoverageOptionWithFacesMessage1(coverageOption1);

		if (!silverValid) {
			logger.info("silver plan  condition Failed");
			return null;
		}

		// Step 2: If Gold or Platinum is selected, validate them
		System.out.println("============= for gold validation =====================");
		logger.info("checking the condition for gold ");
		logger.info("is gold : " + isGold);
		logger.info("goldObject Validations : " + validateInsuranceCoverageOptionWithFacesMessage2(coverageOption2));
		logger.info("amount validation for gold " + validatePremiumAndCoverrageAmountOfAllCoverageOptions(
				coverageOption1, coverageOption2, coverageOption3));

		if (isGold && (!validateInsuranceCoverageOptionWithFacesMessage2(coverageOption2)
				|| !validatePremiumAndCoverrageAmountOfAllCoverageOptions(coverageOption1, coverageOption2,
						coverageOption3))) {

			logger.info("Gold condition Failed");
			return null; // Gold selected but invalid
		}

		System.out.println("==================for platimum==================");
		logger.info("checking the platinum conditions");
		logger.info("is platinum : " + isPlatinum);
		logger.info("goldObject Validations for platinum"
				+ validateInsuranceCoverageOptionWithFacesMessage3(coverageOption3));
		logger.info("amount validation " + validatePremiumAndCoverrageAmountOfAllCoverageOptions(coverageOption1,
				coverageOption2, coverageOption3));
		if (isPlatinum && (!validateInsuranceCoverageOptionWithFacesMessage3(coverageOption3)
				|| !validatePremiumAndCoverrageAmountOfAllCoverageOptions(coverageOption1, coverageOption2,
						coverageOption3))) {
			logger.info("Platinum condition Failed");

			return null; // Platinum selected but invalid
		}

		// Step 3: Save insurance plan and Silver coverage
		try {
			insurancplanDao.addInsurancePlan(insurancePlan);
		} catch (InsurancePlanException e) {
			logger.info("An error occurred while adding the insurancePlan.");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while adding the insurancePlan."));

		}
		try {
			insuranceCoverageOptionDao.addCoveragePlan(coverageOption1);
		} catch (InsuranceCoverageOptionException e) {
			logger.info("An error occurred while adding the SILVER plan.");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while adding the SILVER plan."));

		}

		// Step 4: Add members
		if (insurancePlan.getPlanType() == PlanType.INDIVIDUAL) {
			logger.info("plan is INDIVIDUAL type ");
			MemberPlanRule member = new MemberPlanRule();
			member.setInsurancePlan(insurancePlan);
			member.setRelation(Relation.INDIVIDUAL);
			member.setGender(Gender.valueOf(individualMemberGender));
			try {
				memberPlanRuleDao.addMember(member);

			} catch (MemberPlanException e) {
				logger.info(" An error occurred while adding the insurance member in INDIVIDUAL type.");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
						"An error occurred while adding the insurance member in INDIVIDUAL type."));

			}
			logger.info("individual member added to the plan");
		} else {
			logger.info("plan is FAMILY type ");
			for (String relation : selectedRelations) {
				MemberPlanRule member = new MemberPlanRule();
				member.setInsurancePlan(insurancePlan);
				member.setRelation(Relation.valueOf(relation));
				member.setGender(relation.equals("SON1") || relation.equals("SON2") || relation.equals("FATHER")
						|| relation.equals("HUSBAND") ? Gender.MALE : Gender.FEMALE);
				try {
					memberPlanRuleDao.addMember(member);

				} catch (MemberPlanException e) {
					logger.info("An error occurred while adding the insurance member in FAMILY type");
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
							"An error occurred while adding the insurance member in FAMILY type"));

				}
				logger.info("family member added to the plan");
			}
		}

		// Step 5: Add Gold and Platinum if selected and valid
		if (isGold) {
			logger.info("Gold is selected");
			try {
				insuranceCoverageOptionDao.addCoveragePlan(coverageOption2);
				logger.info("Gold is added");
			} catch (InsuranceCoverageOptionException e) {
				logger.info("An error occurred while adding the GOLD coveragePlan.");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
						"An error occurred while adding the GOLD coveragePlan."));

			}
		}

		if (isPlatinum) {
			logger.info("Platinum is selected");

			try {
				insuranceCoverageOptionDao.addCoveragePlan(coverageOption3);
				logger.info("Platinum is added");

			} catch (InsuranceCoverageOptionException e) {
				logger.info("An error occurred while adding the PLATINUM coveragePlan.");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
						"An error occurred while adding the PLATINUM coveragePlan."));

			}
		}

		return "insuranceAdminDashBoard";
	}

	/**
	 * Retrieves detailed information of a specific insurance plan based on the
	 * provided plan ID. This method is typically used to display full plan details
	 * on the dashBoard or detail view.
	 *
	 * @param planId The unique identifier of the insurance plan to be retrieved.
	 * @return A status string or navigation outcome indicating the result of the
	 *         operation.
	 */
	public String findAllPlanDetailsByPlanId(String planId) {
		System.err.println("we are searching with id " + planId);
		logger.info(" findAllPlanDetailsByPlanId method is called ");
		resetAll();
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			insurancePlan = insurancplanDao.findInsuranceById(planId);
			logger.info("insurancePlan is found with the id : " + planId);
		} catch (InsurancePlanException e) {
			logger.info("An error occurred while searching the insurancePlan with id : " + planId);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while searching the insurancePlan id : " + planId));

		}

		try {
			members = memberPlanRuleDao.searchMemberByPlanId(planId);
			logger.info("insurance member is found with the plan " + planId);
		} catch (MemberPlanException e) {
			logger.info("An error occurred while searching the memeber of insurancePlan id : " + planId);

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while searching the memeber of insurancePlan id : " + planId));

		}

		try {
			planwithCovrageDetailsList = insuranceCoverageOptionDao.findAllInsuranceCoverageOptionsByPlanId(planId);
			logger.info("coverageoptions are found with the insuranceplan id " + planId);
		} catch (InsuranceCoverageOptionException e) {
			logger.info("An error occurred while fetching the coverageOptions of insurancePlan id : " + planId);

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while fetching the coverageOptions of insurancePlan id : " + planId));

		}

		for (int i = 0; i < planwithCovrageDetailsList.size(); i++) {

			if (planwithCovrageDetailsList.get(i).getCoverageType().equals(CoverageType.SILVER)) {
				logger.info("Silver is found");
				coverageOption1 = planwithCovrageDetailsList.get(i);

			} else if (planwithCovrageDetailsList.get(i).getCoverageType().equals(CoverageType.GOLD)) {
				logger.info("Gold is found");
				coverageOption2 = planwithCovrageDetailsList.get(i);

			} else if (planwithCovrageDetailsList.get(i).getCoverageType().equals(CoverageType.PLATINUM)) {
				logger.info("Plantinum is found");
				coverageOption3 = planwithCovrageDetailsList.get(i);
			}

		}

		for (MemberPlanRule member : members) {
			String key = member.getRelation().toString();

			relationMap.put(key, true);

		}
		logger.info("All search is done and redirect to search page : insuranceCoverageDetails");
		return "insuranceCoverageDetails?faces:redirect=true";
	}

	/**
	 * Updates the details of an existing insurance plan identified by the given
	 * plan ID. This method typically fetches the plan, applies modifications, and
	 * persists the changes.
	 *
	 * @param planId The unique identifier of the insurance plan to be updated.
	 * @return A status string or navigation outcome indicating the result of the
	 *         update operation.
	 */
	public String updateInsurancePlan(String planId) {
		FacesContext context = FacesContext.getCurrentInstance();
		System.err.println("We are calling update method");
		resetAll();
		try {
			insurancePlan = insurancplanDao.findInsuranceById(planId);
		} catch (InsurancePlanException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while fetching the insurancePlan : " + planId));

		}
		try {
			members = memberPlanRuleDao.searchMemberByPlanId(planId);
		} catch (MemberPlanException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while fetching the memeber of insurancePlan  : " + planId));

		}
		try {
			planwithCovrageDetailsList = insuranceCoverageOptionDao.findAllInsuranceCoverageOptionsByPlanId(planId);
		} catch (InsuranceCoverageOptionException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while fetching the coverageOptions of insurancePlan  : " + planId));

		}

		for (int i = 0; i < planwithCovrageDetailsList.size(); i++) {

			if (planwithCovrageDetailsList.get(i).getCoverageType().equals(CoverageType.SILVER)) {

				coverageOption1 = planwithCovrageDetailsList.get(i);

			} else if (planwithCovrageDetailsList.get(i).getCoverageType().equals(CoverageType.GOLD)) {

				coverageOption2 = planwithCovrageDetailsList.get(i);

			} else if (planwithCovrageDetailsList.get(i).getCoverageType().equals(CoverageType.PLATINUM)) {

				coverageOption3 = planwithCovrageDetailsList.get(i);
			}

		}

		for (MemberPlanRule member : members) {
			String key = member.getRelation().toString();
			relationMap.put(key, true);

		}
		return "insuranceUpdate?faces:redirect=true";
	}

	/**
	 * Helper method to perform the actual update logic for an insurance plan. This
	 * method receives the modified InsurancePlan object and applies the necessary
	 * updates to the database or persistence layer.
	 *
	 * @param plan The InsurancePlan object containing updated details.
	 * @return A status string or navigation outcome indicating the result of the
	 *         update operation.
	 */
	public String updateInsurancePlanHelper(InsurancePlan plan) {

		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;
		// Description
		if (plan.getDescription() == null || plan.getDescription().trim().isEmpty()) {
			context.addMessage("companyForm:description",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.DESCRIPTION_REQUIRED, null));
			isValid = false;
		} else if (plan.getDescription().trim().length() <= 5) {
			context.addMessage("companyForm:description",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.DESCRIPTION_TOO_SHORT, null));
			isValid = false;
		}
		// Plan Name
		if (plan.getPlanName() == null || plan.getPlanName().trim().isEmpty()) {
			context.addMessage("companyForm:planName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PLAN_NAME_REQUIRED, null));
			isValid = false;
		} else if (plan.getPlanName().trim().length() < 4) {
			context.addMessage("companyForm:planName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PLAN_NAME_TOO_SHORT, null));
			isValid = false;
		}
		if (isValid) {
			try {
				insurancplanDao.updateInsurancePlan(plan);
			} catch (InsurancePlanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "insuranceAdminDashBoard?faces:redirect=true";
		}
		return null;

	}

	public InsurancePlan getInsurancePlan() {
		return insurancePlan;
	}

	public void setInsurancePlan(InsurancePlan insurancePlan) {
		this.insurancePlan = insurancePlan;
	}

	public InsuranceCoverageOption getCoverageOption() {
		return coverageOption;
	}

	public void setCoverageOption(InsuranceCoverageOption coverageOption) {
		this.coverageOption = coverageOption;
	}

	public InsuranceCoverageOption getCoverageOption1() {
		return coverageOption1;
	}

	public void setCoverageOption1(InsuranceCoverageOption coverageOption1) {
		this.coverageOption1 = coverageOption1;
	}

	public InsuranceCoverageOption getCoverageOption2() {
		return coverageOption2;
	}

	public void setCoverageOption2(InsuranceCoverageOption coverageOption2) {
		this.coverageOption2 = coverageOption2;
	}

	public InsuranceCoverageOption getCoverageOption3() {
		return coverageOption3;
	}

	public void setCoverageOption3(InsuranceCoverageOption coverageOption3) {
		this.coverageOption3 = coverageOption3;
	}

	public InsuranceCoverageOptionDao getInsuranceCoverageOptionDao() {
		return insuranceCoverageOptionDao;
	}

	public void setInsuranceCoverageOptionDao(InsuranceCoverageOptionDao insuranceCoverageOptionDao) {
		this.insuranceCoverageOptionDao = insuranceCoverageOptionDao;
	}

	public InsurancePlanDao getInsurancplanDao() {
		return insurancplanDao;
	}

	public CreateInsuranceMessageConstants getvalidationMessages() {
		return validationMessages;
	}

	public void setInsurancplanDao(InsurancePlanDao insurancplanDao) {
		this.insurancplanDao = insurancplanDao;
	}

	public MemberPlanRule getMemberPlanRule() {
		return memberPlanRule;
	}

	public void setMemberPlanRule(MemberPlanRule memberPlanRule) {
		this.memberPlanRule = memberPlanRule;
	}

	public MemberPlanRuleDao getMemberPlanRuleDao() {
		return memberPlanRuleDao;
	}

	public void setMemberPlanRuleDao(MemberPlanRuleDao memberPlanRuleDao) {
		this.memberPlanRuleDao = memberPlanRuleDao;
	}

	public List<InsuranceCoverageOption> getPlanwithCovrageDetailsList() {
		return planwithCovrageDetailsList;
	}

	public void setPlanwithCovrageDetailsList(List<InsuranceCoverageOption> planwithCovrageDetailsList) {
		this.planwithCovrageDetailsList = planwithCovrageDetailsList;
	}

	public int getYearsToAdd() {
		return yearsToAdd;
	}

	public void setYearsToAdd(int yearsToAdd) {
		this.yearsToAdd = yearsToAdd;
	}

	public void setSilver(boolean isSilver) {
		this.isSilver = isSilver;
	}

	public List<MemberPlanRule> getMembers() {
		return members;
	}

	public void setMembers(List<MemberPlanRule> members) {
		this.members = members;
	}

	public List<InsurancePlan> getPlanList() {
		return planList;
	}

	public void setPlanList(List<InsurancePlan> planList) {
		this.planList = planList;
	}

	public InsuranceCompany getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public List<String> getSelectedRelations() {
		return selectedRelations;
	}

	public void setSelectedRelations(List<String> selectedRelations) {
		this.selectedRelations = selectedRelations;
	}

	public Map<String, Boolean> getRelationMap() {
		return relationMap;
	}

	public void setRelationMap(Map<String, Boolean> relationMap) {
		this.relationMap = relationMap;
	}

	public CreateInsuranceMessageConstants getValidationMessages() {
		return validationMessages;
	}

	public void setValidationMessages(CreateInsuranceMessageConstants validationMessages) {
		this.validationMessages = validationMessages;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getIndividualMemberGender() {
		return individualMemberGender;
	}

	public void setIndividualMemberGender(String individualMemberGender) {
		this.individualMemberGender = individualMemberGender;
	}

	public boolean isSilver() {
		return isSilver;
	}

	public boolean isGold() {
		return isGold;
	}

	public void setGold(boolean isGold) {
		this.isGold = isGold;
	}

	public boolean isPlatinum() {
		return isPlatinum;
	}

	public void setPlatinum(boolean isPlatinum) {
		this.isPlatinum = isPlatinum;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isSortAscending() {
		return sortAscending;
	}

	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		relationMap.put("SON1", true);
		relationMap.put("SON2", false);
		relationMap.put("DAUGHTER1", true);
		relationMap.put("DAUGHTER2", false);
		relationMap.put("FATHER", false);
		relationMap.put("MOTHER", false);
		relationMap.put("HUSBAND", true);
		relationMap.put("WIFE", true);
		relationMap.put("SELF", false);
		relationMap.put("INDIVIDUAL", false);
		// dynamically update the COVERAGEPLAN STATUS : ACTIVE OR INACTIVE
		try {
			planwithCovrageDetailsList = insuranceCoverageOptionDao.findAllInsuranceCoverageOptions();
		} catch (InsuranceCoverageOptionException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
					"An error occurred while fetching the coverageOptions of insurancePlan "));

		}
		for (InsuranceCoverageOption options : planwithCovrageDetailsList) {
			if (isActiveBeforeOrEqualToday(options.getInsurancePlan().getActiveOn()))
				options.setStatus(CoveragePlanStatus.ACTIVE);
			try {
				insuranceCoverageOptionDao.updateInsuranceCoverageOption(options);
			} catch (InsuranceCoverageOptionException e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error :",
						"An error occurred while fetching the coverageOptions of insurancePlan  "));

			}
		}

		// load InsuranceCompany
		insuranceCompany = companyDao.findCompanyById("COM001");
		insurancePlan.setInsuranceCompany(insuranceCompany);
	}

	// Coverage plan status dynamically update so 2 date compare
	/**
	 * Checks whether the given date is before or equal to today's date. This method
	 * is typically used to determine if a coverage plan is currently active based
	 * on its activation date.
	 *
	 * @param activeOn the date when the coverage plan becomes active
	 * @return true if the activation date is before or equal to today, false
	 *         otherwise
	 */
	public static boolean isActiveBeforeOrEqualToday(Date activeOn) {
		// method logic here

		try {
			// Format to yyyy-MM-dd
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// Strip time from both dates
			Date today = sdf.parse(sdf.format(new Date()));
			Date activeDate = sdf.parse(sdf.format(activeOn));

			// Compare

			return activeDate.equals(today) || activeDate.before(today);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Dynamically EXPIRYDATE get calculated from activeDate and
	// durations(yearToAdd)
	public static Date calculateExpiryDate(Date activeDate, int yearsToAdd) {
		if (activeDate == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(activeDate);
		calendar.add(Calendar.YEAR, yearsToAdd);
		return calendar.getTime();
	}

//	VALIDATION :: INSURANCEPLAN
	// validation of plan
	@SuppressWarnings("unused")
	public boolean validateInsurancePlanWithFacesMessage(InsurancePlan plan) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		// 1.Plan Name
		if (plan.getPlanName() == null || plan.getPlanName().trim().isEmpty()) {
			context.addMessage("companyForm:planName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PLAN_NAME_REQUIRED, null));
			isValid = false;
		} else if (plan.getPlanName().trim().length() < 4) {
			context.addMessage("companyForm:planName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PLAN_NAME_TOO_SHORT, null));
			isValid = false;
		} else if (!plan.getPlanName().matches("^[A-Za-z\\s]+$")) {

			context.addMessage("companyForm:planName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PLAN_NAME_INVALID, null));
			isValid = false;
		}

		// 2.Insurance Company
		if (plan.getInsuranceCompany() == null || plan.getInsuranceCompany().getCompanyId() == null
				|| plan.getInsuranceCompany().getCompanyId().trim().isEmpty()) {
			context.addMessage("companyForm:companyId",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COMPANY_REQUIRED, null));
			isValid = false;
		}

		// 3.Description
		if (plan.getDescription() == null || plan.getDescription().trim().isEmpty()) {
			context.addMessage("companyForm:description",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.DESCRIPTION_REQUIRED, null));
			isValid = false;
		} else if (plan.getDescription().trim().length() <= 5) {
			context.addMessage("companyForm:description",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.DESCRIPTION_TOO_SHORT, null));
			isValid = false;
		} else if (!plan.getDescription().matches("^[A-Za-z\\s]+$")) {
			context.addMessage("companyForm:description",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.DESCRIPTION_INVALID, null));
			isValid = false;
		}
		// 4.AvailableCoverAmounts
		Double coverAmount = plan.getAvailableCoverAmounts();
		if (coverAmount == null) {
			context.addMessage("companyForm:cover",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVER_AMOUNT_REQUIRED, null));
			isValid = false;
		} else if (coverAmount < 0) {
			context.addMessage("companyForm:cover",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVER_AMOUNT_NEGATIVE, null));
			isValid = false;
		}

		else if (coverAmount == 0) {
			context.addMessage("companyForm:cover",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVER_AMOUNT_REQUIRED, null));
			logger.info("Cover amount must be greater than zero.");
			isValid = false;
		} else if (coverAmount < 500 && coverAmount > 50000000) {
			context.addMessage("companyForm:cover",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVERAGE_AMOUNT_RANGE, null));
			logger.info("Cover amount must be in the range coverAmount<500 && coverAmount>50000000");
			isValid = false;
		}

		// 5.Min Age
		Integer minAge = plan.getMinEntryAge();
		if (minAge == null) {
			context.addMessage("companyForm:minAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MIN_AGE_REQUIRED, null));
			isValid = false;
		} else if (minAge < 0) {
			context.addMessage("companyForm:minAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MIN_AGE_INVALID, null));
			isValid = false;
		} else if (minAge == 0) {
			context.addMessage("companyForm:minAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MIN_AGE_REQUIRED, null));
			isValid = false;
		}

		// 6.Max Age
		Integer maxAge = plan.getMaxEntryAge();
		if (maxAge == null) {
			context.addMessage("companyForm:maxAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_AGE_REQUIRED, null));
			isValid = false;
		} else if (maxAge < 0) {
			context.addMessage("companyForm:maxAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_AGE_INVALID, null));
			isValid = false;
		} else if (maxAge == 0) {
			context.addMessage("companyForm:maxAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_AGE_REQUIRED, null));
			isValid = false;
		} else if (minAge != null && maxAge < minAge) {
			context.addMessage("companyForm:maxAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_AGE_LESS_THAN_MIN, null));
			isValid = false;
		} else if (maxAge > 70) {
			context.addMessage("companyForm:maxAge",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_AGE_TOO_HIGH, null));
			isValid = false;
		}

		// 7. miximum memberAllowed
		if (plan.getMaximumMemberAllowed() == 0) {
			context.addMessage("companyForm:maximumMemberAllowed",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_MEMBER_REQUIRED, null));
			logger.info(validationMessages.MAX_MEMBER_REQUIRED);
			isValid = false;
		}
		// 7.minimum memberAllowed
		if (plan.getMinimumMeberAllowed() == 0) {
			context.addMessage("companyForm:minimumMemberAllowed",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MIN_MEMBER_REQUIRED, null));
			logger.info(validationMessages.MIN_MEMBER_REQUIRED);
			isValid = false;
		}

		// 7.Minimum and Maximum Member Validation for family
		if (plan.getPlanType() != null && plan.getPlanType() == PlanType.valueOf("FAMILY")) {
			if (plan.getMaximumMemberAllowed() > 8) {
				context.addMessage("companyForm:maximumMemberAllowed", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						validationMessages.MAX_MEMBER_FAMILY_EXCEEDED, null));
				logger.info(validationMessages.MAX_MEMBER_FAMILY_EXCEEDED);
				isValid = false;
			}
			if (plan.getMinimumMeberAllowed() < 2) {
				context.addMessage("companyForm:minimumMemberAllowed", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						validationMessages.MIN_MEMBER_FAMILY_REQUIRED, null));
				logger.info(validationMessages.MIN_MEMBER_FAMILY_REQUIRED);
				isValid = false;
			}
			if (plan.getMinimumMeberAllowed() > plan.getMaximumMemberAllowed()) {
				context.addMessage("companyForm:minimumMemberAllowed",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MIN_GREATER_THAN_MAX, null));
				logger.info(validationMessages.MIN_GREATER_THAN_MAX);
				isValid = false;
			}
		}
		// 7.Minimum and Maximum Member Validation for INDIVIDUAL

		if (plan.getPlanType() != null && plan.getPlanType().equals(PlanType.INDIVIDUAL)) {
			if (plan.getMaximumMemberAllowed() == 0) {
				context.addMessage("companyForm:maximumMemberAllowed",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MAX_MEMBER_REQUIRED, null));
				logger.info(validationMessages.MAX_MEMBER_REQUIRED);
				isValid = false;
			}
			if (plan.getMinimumMeberAllowed() == 0) {
				context.addMessage("companyForm:minimumMeberAllowed",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.MIN_MEMBER_REQUIRED, null));
				logger.info(validationMessages.MAX_MEMBER_REQUIRED);
				isValid = false;
			}
			if (plan.getMaximumMemberAllowed() != 1) {
				context.addMessage("companyForm:maximumMemberAllowed", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						validationMessages.INDIVIDUAL_MEMBER_ALLOWED, null));
				logger.info(validationMessages.INDIVIDUAL_MEMBER_ALLOWED);
				isValid = false;
			}
			if (plan.getMinimumMeberAllowed() != 1) {
				context.addMessage("companyForm:minimumMemberAllowed", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						validationMessages.INDIVIDUAL_MEMBER_ALLOWED, null));
				logger.info(validationMessages.INDIVIDUAL_MEMBER_ALLOWED);
				isValid = false;
			}
		}
		// 8. Waiting Period

		int waitingPeriod = plan.getWaitingPeriod();
		if (waitingPeriod == 0) {
			context.addMessage("companyForm:waitingPeriod",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.WAITING_PERIOD_REQUIRED, null));
			logger.info("Waiting period is required.");
			isValid = false;
		}

		if (waitingPeriod < 0 || waitingPeriod > 3) {
			context.addMessage("companyForm:waitingPeriod", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					validationMessages.WAITING_PERIOD_OUT_OF_RANGE, null));
			logger.info("Waiting period must be between 0 and 3 months.");
			isValid = false;
		}

		// 9.Periodic Diseases
		if (plan.getPeriodicDiseases() == null || plan.getPeriodicDiseases().trim().isEmpty()) {
			context.addMessage("companyForm:periodicDiseases",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PERIODIC_DISEASES_REQUIRED, null));
			isValid = false;
		}

		// 10.Plan Type
		if (plan.getPlanType() == null || plan.getPlanType().toString().isEmpty()) {
			context.addMessage("companyForm:planType",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PLAN_TYPE_REQUIRED, null));
			isValid = false;
		}

		// 11.Dates
		Date activeOn = plan.getActiveOn();
		Date createdOn = plan.getCreatedOn();
		Date expireDate = plan.getExpireDate();

		if (activeOn == null) {
			context.addMessage("companyForm:activeOn",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.ACTIVE_ON_REQUIRED, null));
			isValid = false;
		} else {
			if (createdOn != null && activeOn.before(createdOn)) {
				context.addMessage("companyForm:activeOn", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						validationMessages.ACTIVE_ON_BEFORE_CREATED, null));
				isValid = false;
			}
			if (expireDate != null && activeOn.after(expireDate)) {
				context.addMessage("companyForm:activeOn",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.ACTIVE_ON_AFTER_EXPIRE, null));
				isValid = false;
			}
		}

		// 12.Duration(Expire date)

		if (yearsToAdd < 0) {
			context.addMessage("companyForm:yearsToAdd",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.DURATION_NEGATIVE, null));
			logger.info("Duration is negative.");
			isValid = false;
		} else if (yearsToAdd == 0) {
			context.addMessage("companyForm:yearsToAdd",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "durantion is required", null));
			logger.info("Duration is zero.");
			isValid = false;
		}

		return isValid;
	}

	// VALIDATION :: INSURANCECOVERAGE : SILVER , GOLD , PLATINUM

	public boolean validateInsuranceCoverageOptionWithFacesMessage1(InsuranceCoverageOption silverOption) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		// Validate plan
		if (silverOption.getInsurancePlan() == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.LINKED_PLAN_REQUIRED, null));
			isValid = false;
		}

		// Validate premiumAmount
		if (silverOption.getPremiumAmount() < 500 || silverOption.getPremiumAmount() > 100000) {
			context.addMessage("companyForm:PremiumAmount",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PREMIUM_AMOUNT_RANGE, null));
			isValid = false;
		}

		// Validate coverageAmount
		if (silverOption.getCoverageAmount() < 100000 || silverOption.getCoverageAmount() > 50000000) {
			context.addMessage("companyForm:CoverageAmount",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVERAGE_AMOUNT_RANGE, null));
			isValid = false;
		}

		// Premium should not exceed coverage
		if (silverOption.getPremiumAmount() > silverOption.getCoverageAmount()) {
			context.addMessage("companyForm:CoverageAmount", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					validationMessages.COVERAGE_GREATER_THAN_PREMIUM, null));
			isValid = false;
		}
		if (silverOption != null && silverOption.getInsurancePlan() != null && silverOption.getCoverageType() != null
				&& silverOption.getInsurancePlan().getAvailableCoverAmounts() != null) {

			if (silverOption.getCoverageType().equals(CoverageType.SILVER)) {

				if (silverOption.getCoverageAmount() != silverOption.getInsurancePlan().getAvailableCoverAmounts()) {
					context.addMessage("companyForm:CoverageAmount", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							validationMessages.COVERAGE_AMOUNT_MISMATCH, null));

					logger.info("Coverage amount does not match available cover amount.");
					isValid = false;
				}
			}
		}

		return isValid;
	}

	public boolean validateInsuranceCoverageOptionWithFacesMessage2(InsuranceCoverageOption goldOption) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		// Validate plan
		if (goldOption.getInsurancePlan() == null || goldOption.getInsurancePlan() == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.LINKED_PLAN_REQUIRED, null));
			isValid = false;
		}

		// Validate premiumAmount
		if (goldOption.getPremiumAmount() < 500 || goldOption.getPremiumAmount() > 100000) {
			context.addMessage("companyForm:PremiumAmount2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PREMIUM_AMOUNT_RANGE, null));
			isValid = false;
		}

		// Validate coverageAmount
		if (goldOption.getCoverageAmount() < 100000 || goldOption.getCoverageAmount() > 50000000) {
			context.addMessage("companyForm:CoverageAmount2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVERAGE_AMOUNT_RANGE, null));
			isValid = false;
		}
		// goldOption.premiumAmount > goldOption.coverageAmount
		if (goldOption.getPremiumAmount() > goldOption.getCoverageAmount()) {
			context.addMessage("companyForm:CoverageAmount2", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					validationMessages.COVERAGE_GREATER_THAN_PREMIUM, null));
			isValid = false;
		}

		return isValid;
	}

	public boolean validateInsuranceCoverageOptionWithFacesMessage3(InsuranceCoverageOption platinumOption) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		// Validate plan
		if (platinumOption.getInsurancePlan() == null || platinumOption.getInsurancePlan() == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.LINKED_PLAN_REQUIRED, null));
			isValid = false;
		}

		// Validate premiumAmount
		if (platinumOption.getPremiumAmount() < 500 || platinumOption.getPremiumAmount() > 100000) {
			context.addMessage("companyForm:PremiumAmount3",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.PREMIUM_AMOUNT_RANGE, null));
			isValid = false;
		}

		// Validate coverageAmount
		if (platinumOption.getCoverageAmount() < 100000 || platinumOption.getCoverageAmount() > 50000000) {
			context.addMessage("companyForm:CoverageAmount3",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, validationMessages.COVERAGE_AMOUNT_RANGE, null));
			isValid = false;
		}
		// platinumOption.premiumAmount > platinumOption.coverageAmount
		if (platinumOption.getPremiumAmount() > platinumOption.getCoverageAmount()) {
			context.addMessage("companyForm:CoverageAmount3", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					validationMessages.COVERAGE_GREATER_THAN_PREMIUM, null));
			isValid = false;
		}

		return isValid;
	}

	public boolean validateInsuranceMeberRelationsWithFacesMessage(InsurancePlan insurancePlan) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;
		logger.info("we inside the member validation");
		if (insurancePlan == null) {

			context.addMessage("companyForm:memberValidation",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insuranceplan is empty", null));
			return false;

		}

		// Ensure SON1 is true if SON2 is true and SON1 is false
		if (Boolean.TRUE.equals(relationMap.get("SON2")) && !Boolean.TRUE.equals(relationMap.get("SON1"))) {
			relationMap.put("SON1", true);
			relationMap.put("SON2", false); // Optional: make SON2 false if only one should be selected
		}

		// Ensure DAUGHTER1 is true if DAUGHTER2 is true and DAUGHTER1 is false
		if (Boolean.TRUE.equals(relationMap.get("DAUGHTER2")) && !Boolean.TRUE.equals(relationMap.get("DAUGHTER1"))) {
			relationMap.put("DAUGHTER1", true);
			relationMap.put("DAUGHTER2", false); // Optional
		}

		selectedRelations = relationMap.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		System.out.println(selectedRelations);

		if (insurancePlan.getPlanType() != null && insurancePlan.getPlanType() == PlanType.valueOf("FAMILY")) {
			logger.info("insurancePlanType : " + insurancePlan.getPlanType());
			if (selectedRelations.size() != insurancePlan.getMaximumMemberAllowed()) {
				context.addMessage("companyForm:memberValidation", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Must choise maximum member that you mention", null));
				isValid = false;

			}
			if (selectedRelations.size() == insurancePlan.getMaximumMemberAllowed()) {
				List<String> requiredRelations = Arrays.asList("FATHER", "MOTHER", "HUSBAND", "WIFE");

				boolean hasRequiredRelation = selectedRelations.stream().anyMatch(requiredRelations::contains);

				if (!hasRequiredRelation) {
					context.addMessage("companyForm:memberValidation", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"For FAMILY plan, atleast one member must be Father, Mother, Husband, or Wife.", null));

					isValid = false;
				}
			}
		}

		if (insurancePlan.getPlanType().equals(PlanType.INDIVIDUAL)) {
			logger.info("we are inside individual Type");
			logger.info("and gender is: " + individualMemberGender);
			relationMap.put("SON1", false);
			relationMap.put("SON2", false);
			relationMap.put("DAUGHTER1", false);
			relationMap.put("DAUGHTER2", false);
			relationMap.put("FATHER", false);
			relationMap.put("MOTHER", false);
			relationMap.put("HUSBAND", false);
			relationMap.put("WIFE", false);
			relationMap.put("SELF", false);
			relationMap.put("INDIVIDUAL", true);
			selectedRelations = relationMap.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey)
					.collect(Collectors.toList());
			System.out.println(selectedRelations);
			logger.info("and gender is validation if null : " + individualMemberGender);

			if (individualMemberGender == null || individualMemberGender.isEmpty()) {
				logger.info("and through errror if  gender is null: " + individualMemberGender);

				context.addMessage("companyForm:individualMemberGender",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "MUST CHOSSE GENDER", null));
				logger.info("genderValidation fails ");
				isValid = false;
			}
		}
		if (insurancePlan.getPlanType() == PlanType.valueOf("INDIVIDUAL")) {
			if (insurancePlan.getMaximumMemberAllowed() != 1) {
				context.addMessage("companyForm:maximumMemberAllowed", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Only One member should Allowed in individual", null));
				logger.info("only one member allowed in individual in individual Type");
				isValid = false;
			}
			if (insurancePlan.getMinimumMeberAllowed() != 1) {
				context.addMessage("companyForm:minimumMeberAllowed", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Only One member should Allowed in individual", null));
				logger.info("only one member allowed in individual in individual Type");
				isValid = false;
			}
		}
		return isValid;
	}

	public boolean validatePremiumAndCoverrageAmountOfAllCoverageOptions(InsuranceCoverageOption silverOption,
			InsuranceCoverageOption goldOption, InsuranceCoverageOption platinumOption) {
		boolean isValid = true;
		FacesContext context = FacesContext.getCurrentInstance();

		logger.info("silver coverage amount is :" + silverOption.getCoverageAmount());
		logger.info("golve coverage amount is " + goldOption.getCoverageAmount());
		logger.info("platinium coverage amount is " + platinumOption.getCoverageAmount());
		logger.info("silver PremiumAmount amount is :" + silverOption.getPremiumAmount());
		logger.info("golve PremiumAmount amount is " + goldOption.getPremiumAmount());
		logger.info("platinium PremiumAmount amount is " + platinumOption.getPremiumAmount());

		System.out.println("=====check for silve and  gold ==========");

		if (isSilver && silverOption != null && isGold && goldOption != null) {
			if (silverOption.getPremiumAmount() > goldOption.getPremiumAmount()) {
				context.addMessage("companyForm:PremiumAmount2", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"GoldOption PremiumAmount  must be greater than SilverOption premiumAmount", null));

				isValid = false;
				logger.info("we are return isValid [siler and gold] in premiumAmount: " + isValid);

			}
			if (silverOption.getCoverageAmount() > goldOption.getCoverageAmount()) {

				context.addMessage("companyForm:CoverageAmount2", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"GoldOption CoverageAmount  must be greater than SilverOption CoverageAmount", null));
				isValid = false;
				logger.info("we are return isValid [siler and gold] in coverageAmount: " + isValid);

			}

		}

		if (isGold && goldOption != null && isPlatinum && platinumOption != null) {

			if (goldOption.getPremiumAmount() > platinumOption.getPremiumAmount()) {

				context.addMessage("companyForm:PremiumAmount3", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"platinumOption PremiumAmount  must be greater than goldOption premiumAmount", null));
				isValid = false;
				logger.info("we are return isValid [platinumOption and gold] in premiumAmount: " + isValid);

			}
			if (goldOption.getCoverageAmount() > platinumOption.getCoverageAmount()) {

				context.addMessage("companyForm:CoverageAmount3", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"platinumOption CoverageAmount  must be greater than goldOption CoverageAmount", null));
				isValid = false;
				logger.info("we are return isValid [platinumOption and gold] in coverageAmount: " + isValid);

			}

		}

		if (!isGold && isSilver && silverOption != null && isPlatinum && platinumOption != null) {

			if (silverOption.getPremiumAmount() > platinumOption.getPremiumAmount()) {
				context.addMessage("companyForm:PremiumAmount3", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"platinumOption PremiumAmount  must be greater than SilverOption premiumAmount", null));
				isValid = false;
				logger.info("we are return isValid [platinumOption and silverOption] in coverageAmount: " + isValid);

			}
			if (silverOption.getCoverageAmount() > platinumOption.getCoverageAmount()) {
				context.addMessage("companyForm:CoverageAmount3", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"platinumOption CoverageAmount  must be greater than SilverOption CoverageAmount", null));
				isValid = false;
				logger.info("we are return isValid [platinumOption and silverOption] in coverageAmount: " + isValid);

			}

		}

		return isValid;

	}

	public void resetAll() {

		insurancePlan = new InsurancePlan();
		coverageOption1 = new InsuranceCoverageOption();
		coverageOption2 = new InsuranceCoverageOption();
		coverageOption3 = new InsuranceCoverageOption();
		insurancePlan.setInsuranceCompany(insuranceCompany);
		logger.debug("resetAll is Done ");
	}

}
