/*
 * Copyright (c) 2025 Infinite Software Solutions Pvt. Ltd.
 * All rights reserved.
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

/**
 * Represents an insurance company entity in the system. This class holds basic
 * information about an insurance provider and is used throughout the insurance
 * module for referencing company details.
 * 
 * Implements Serializable to support session persistence and data transfer.
 */
public class InsuranceCompany implements Serializable {

	private String companyId;
	private String name;
	private String logoUrl;
	private String headOffice;
	private String contactEmail;
	private String contactPhone;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Override
	public String toString() {
		return "InsuranceCompany [companyId=" + companyId + ", name=" + name + ", logoUrl=" + logoUrl + ", headOffice="
				+ headOffice + ", contactEmail=" + contactEmail + ", contactPhone=" + contactPhone + "]";
	}

	public InsuranceCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InsuranceCompany(String companyId, String name, String logoUrl, String headOffice, String contactEmail,
			String contactPhone) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.logoUrl = logoUrl;
		this.headOffice = headOffice;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
	}

}
