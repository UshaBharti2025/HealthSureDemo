/** 
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */
/**
 * Package: com.infinite.jsf.insurance.model
 * 
 * This package contains model classes for the insurance module of the Infinite JSF application.
 * These classes represent the core business entities and are used for data transfer between
 * different layers of the application.
 */
package com.infinite.jsf.insurance.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * This class represents an insurance plan offered by an insurance company. It
 * includes details such as plan type, age limits, coverage, and associated
 * members.
 */
public class InsurancePlan implements Serializable {
	private static final long serialVersionUID = 1L;
	private String planId;
	private InsuranceCompany insuranceCompany;

	private String planName;
	private PlanType planType;

	private int minEntryAge;
	private int maxEntryAge;

	private String description;
	private Double availableCoverAmounts;
	private int waitingPeriod;

	private Date createdOn = new Date();

	private Date expireDate;

	private Date activeOn;

	private String periodicDiseases; // YES or NO

	private int maximumMemberAllowed;

	private int minimumMeberAllowed;

	private Set<MemberPlanRule> members;

	// Helper for Hibernate to store enum as string
	public String getPlanTypeString() {
		return planType != null ? planType.name() : null;
	}

	public void setPlanTypeString(String planTypeString) {
		this.planType = (planTypeString != null) ? PlanType.valueOf(planTypeString) : null;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public InsuranceCompany getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public PlanType getPlanType() {
		return planType;
	}

	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}

	public int getMinEntryAge() {
		return minEntryAge;
	}

	public void setMinEntryAge(int minEntryAge) {
		this.minEntryAge = minEntryAge;
	}

	public int getMaxEntryAge() {
		return maxEntryAge;
	}

	public void setMaxEntryAge(int maxEntryAge) {
		this.maxEntryAge = maxEntryAge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAvailableCoverAmounts() {
		return availableCoverAmounts;
	}

	public void setAvailableCoverAmounts(Double availableCoverAmounts) {
		this.availableCoverAmounts = availableCoverAmounts;
	}

	public int getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(int waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = new Date();
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getActiveOn() {
		return activeOn;
	}

	public void setActiveOn(Date activeOn) {
		this.activeOn = activeOn;
	}

	public String getPeriodicDiseases() {
		return periodicDiseases;
	}

	public void setPeriodicDiseases(String periodicDiseases) {
		this.periodicDiseases = periodicDiseases;
	}

	public int getMinimumMeberAllowed() {
		return minimumMeberAllowed;
	}

	public void setMinimumMeberAllowed(int minimumMeberAllowed) {
		this.minimumMeberAllowed = minimumMeberAllowed;
	}

	public InsurancePlan() {
		super();
	}

	public Set<MemberPlanRule> getMembers() {
		return members;
	}

	public void setMembers(Set<MemberPlanRule> members) {
		this.members = members;
	}

	public int getMaximumMemberAllowed() {
		return maximumMemberAllowed;
	}

	public void setMaximumMemberAllowed(int maximumMemberAllowed) {
		this.maximumMemberAllowed = maximumMemberAllowed;
	}

	@Override
	public String toString() {
		return "InsurancePlan [planId=" + planId + ", insuranceCompany=" + insuranceCompany + ", planName=" + planName
				+ ", planType=" + planType + ", minEntryAge=" + minEntryAge + ", maxEntryAge=" + maxEntryAge
				+ ", description=" + description + ", availableCoverAmounts=" + availableCoverAmounts
				+ ", waitingPeriod=" + waitingPeriod + ", createdOn=" + createdOn + ", expireDate=" + expireDate
				+ ", activeOn=" + activeOn + ", periodicDiseases=" + periodicDiseases + ", members=" + members
				+ ", maximumMemberAllowed=" + maximumMemberAllowed + ", minimumMeberAllowed=" + minimumMeberAllowed
				+ "]";
	}

	public InsurancePlan(String planId, InsuranceCompany insuranceCompany, String planName, PlanType planType,
			int minEntryAge, int maxEntryAge, String description, Double availableCoverAmounts, int waitingPeriod,
			Date createdOn, Date expireDate, Date activeOn, String periodicDiseases, Set<MemberPlanRule> members,
			int maximumMemberAllowed, int minimumMeberAllowed) {
		super();
		this.planId = planId;
		this.insuranceCompany = insuranceCompany;
		this.planName = planName;
		this.planType = planType;
		this.minEntryAge = minEntryAge;
		this.maxEntryAge = maxEntryAge;
		this.description = description;
		this.availableCoverAmounts = availableCoverAmounts;
		this.waitingPeriod = waitingPeriod;
		this.createdOn = createdOn;
		this.expireDate = expireDate;
		this.activeOn = activeOn;
		this.periodicDiseases = periodicDiseases;
		this.members = members;
		this.maximumMemberAllowed = maximumMemberAllowed;
		this.minimumMeberAllowed = minimumMeberAllowed;
	}

}
