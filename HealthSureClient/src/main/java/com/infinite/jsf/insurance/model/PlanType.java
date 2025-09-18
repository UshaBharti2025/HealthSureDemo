// Copyright © 2025 Infinite Computer Solutions. All rights reserved.

/**
 * Package: com.infinite.jsf.insurance.model
 *
 * This package contains model classes for the insurance module of the Infinite JSF application.
 * These classes represent the core business entities and are used for data transfer between
 * different layers of the application.
 */
package com.infinite.jsf.insurance.model;

/**
 * Enum representing the types of insurance plans. Each type maps to a database
 * value.
 *
 * Example types: - FAMILY: Coverage for family members - SENIOR: Plans tailored
 * for senior citizens - CRITICAL_ILLNESS: Coverage for major illnesses -
 * EPIDEMIC_PROTECT: Coverage during epidemic outbreaks - SUPER_ELITE: Premium
 * high-value plans - INDIVIDUAL: Another form of single-person coverage
 */
public enum PlanType {
	FAMILY, SENIOR, CRITICAL_ILLNESS, EPIDEMIC_PROTECT, SUPER_ELITE, INDIVIDUAL
}
