/**
 * Copyright © 2025 Infinite Computer Solutions. All rights reserved.
 *
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
 * CreateInsuranceMessageConstants.java
 *
 * Singleton-style class to hold all application-wide message constants used in
 * the insurance creation module. This helps in maintaining consistency and
 * centralizing message management across the application.
 *
 */
public class CreateInsuranceMessageConstants {

	public final String COVERAGE_AMOUNT_MISMATCH = "Coverage amount does not match available cover amount in SILVER";
	// maximum member
	public final String MAX_MEMBER_NEGATIVE = "Maximum member cannot be negative.";
	public final String MAX_MEMBER_ZERO = "Maximum member cannot be zero.";
	// minimum member
	public final String MIN_MEMBER_NEGATIVE = "Minimum member cannot be negative.";
	public final String MIN_MEMBER_ZERO = "Minimum member cannot be zero.";
	// Individual member
	public final String INDIVIDUAL_MEMBER_ALLOWED = "Only one member is allowed in individual";
	public final String PLAN_NAME_REQUIRED = "Plan name is required.";
	public final String PLAN_NAME_TOO_SHORT = "Plan name must be at least 4 characters.";
	public final String PLAN_NAME_INVALID = "Plan name must contain only alphabetic characters (no digits or special characters).";
	public final String COMPANY_REQUIRED = "Insurance company is required.";
	// description
	public final String DESCRIPTION_REQUIRED = "Description is required.";
	public final String DESCRIPTION_TOO_SHORT = "Description must be more than 5 characters.";
	public final String DESCRIPTION_INVALID = "Only alphabetic characters are allowed (no digits or special characters).";
	// coverAmount
	public final String COVER_AMOUNT_CANNOT_ZERO = "Cover amount can not be zero";
	public final String COVER_AMOUNT_REQUIRED = "Cover amount is required.";
	public final String COVER_AMOUNT_NEGATIVE = "Cover amount must be greater than zero.";
	// min age
	public final String MIN_AGE_REQUIRED = "Minimum age is required.";
	public final String MIN_AGE_INVALID = "Minimum age must be greater than 0.";
	// max age
	public final String MAX_AGE_REQUIRED = "Maximum age is required.";
	public final String MAX_AGE_INVALID = "Maximum age must be greater than 0.";
	public final String MAX_AGE_LESS_THAN_MIN = "Maximum age must be greater than or equal to minimum age.";
	public final String MAX_AGE_TOO_HIGH = "Maximum age must not be greater than 70.";

	public final String WAITING_PERIOD_INVALID = "Invalid waiting period.";
	public final String WAITING_PERIOD_OUT_OF_RANGE = "Waiting period must be between 0 and 3 months.";
	public final String PERIODIC_DISEASES_REQUIRED = "Periodic diseases field is required.";
	public final String PLAN_TYPE_REQUIRED = "Plan Type field is required.";
	public final String ACTIVE_ON_REQUIRED = "Active On date is required.";
	public final String ACTIVE_ON_BEFORE_CREATED = "Active On date cannot be before Created On date.";
	public final String ACTIVE_ON_AFTER_EXPIRE = "Active On date cannot be after Expire date.";
	public final String DURATION_REQUIRED = "You must choose a duration.";
	public final String LINKED_PLAN_REQUIRED = "Linked Insurance Plan is required.";
	public final String PREMIUM_AMOUNT_RANGE = "Premium amount must be between ₹500 and ₹100,000.";
	public final String COVERAGE_AMOUNT_RANGE = "Coverage amount must be between ₹1,00,000 (1L) and ₹5,00,00,000 (5Cr).";
	public final String COVERAGE_GREATER_THAN_PREMIUM = "Coverage amount must be greater than premiumAmount.";
	// member
	public final String MAX_MEMBER_FAMILY_EXCEEDED = "Maximum 8 members are allowed in FAMILY plan.";
	public final String MIN_MEMBER_FAMILY_REQUIRED = "At least 2 members must be selected in FAMILY plan.";
	public final String MIN_GREATER_THAN_MAX = "Minimum members cannot be greater than maximum entry age.";
//	public final String MAX_MEMBER_REQUIRED = "Maximum member must be greater than zero.";
	public final String MAX_MEMBER_REQUIRED = "Maximum member is required.";
	public final String MIN_MEMBER_REQUIRED = "Minimum member is required.";

	// Add more constants as needed

	public final String WAITING_PERIOD_REQUIRED = "Waiting period is required.";
	public final String WAITING_PERIOD_DIGITS_ONLY = "Waiting period must contain only digits.";

	// durantion
	public final String DURATION_NEGATIVE = "Duration cannot be negative.";
	public final String DURATION_ZERO = "Duration cannot be zero.";

}
