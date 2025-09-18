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
 * MemberPlanRule.java
 * 
 * This class represents a MemberPlanRule enrolled template or rule in an
 * insurance plan. It includes property of MemberPlanRule such as name, age,
 * gender, and relation to the policyholder.
 * 
 */

public class MemberPlanRule {

	private String meberId;
	private Gender gender;
	private Relation relation;
	private InsurancePlan insurancePlan;

	public MemberPlanRule(String meberId, String name, int age, Gender gender, Relation relation,
			InsurancePlan insurancePlan) {
		super();
		this.meberId = meberId;

		this.gender = gender;
		this.relation = relation;
		this.insurancePlan = insurancePlan;
	}

	public String getMeberId() {
		return meberId;
	}

	public void setMeberId(String meberId) {
		this.meberId = meberId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public InsurancePlan getInsurancePlan() {
		return insurancePlan;
	}

	public void setInsurancePlan(InsurancePlan insurancePlan) {
		this.insurancePlan = insurancePlan;
	}

	public MemberPlanRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MemberPlanRule [meberId=" + meberId + ", gender=" + gender + ", relation=" + relation
				+ ", insurancePlan=" + insurancePlan + "]";
	}

}
