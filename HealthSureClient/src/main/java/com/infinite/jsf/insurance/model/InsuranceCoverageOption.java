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

/**
 * InsuranceCoverageOption.java
 * 
 * This class represents a coverage option associated with an insurance plan. It
 * includes details such as premium amount, coverage amount, and status.
 * 
 */

public class InsuranceCoverageOption {

	private String coverageId;

	private InsurancePlan insurancePlan; // FK (planId)

	private double premiumAmount;

	private double coverageAmount;

	private CoveragePlanStatus status;

	private CoverageType coverageType;

	public String getCoverageId() {
		return coverageId;
	}

	public void setCoverageId(String coverageId) {
		this.coverageId = coverageId;
	}

	public InsurancePlan getInsurancePlan() {
		return insurancePlan;
	}

	public void setInsurancePlan(InsurancePlan insurancePlan) {
		this.insurancePlan = insurancePlan;
	}

	public double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public double getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(double coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	@Override
	public String toString() {
		return "InsuranceCoverageOption [coverageId=" + coverageId + ", insurancePlan=" + insurancePlan
				+ ", premiumAmount=" + premiumAmount + ", coverageAmount=" + coverageAmount + ", status=" + status
				+ ", coverageType=" + coverageType + "]";
	}

	public CoveragePlanStatus getStatus() {
		return status;
	}

	public void setStatus(CoveragePlanStatus status) {
		this.status = status;
	}

	public CoverageType getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(CoverageType coverageType) {
		this.coverageType = coverageType;
	}

	public InsuranceCoverageOption() {
		super();
		// TODO Auto-generated constructor stub
	}

}
