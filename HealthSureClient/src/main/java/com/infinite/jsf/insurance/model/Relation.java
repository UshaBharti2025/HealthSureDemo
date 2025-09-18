/**
 *  Copyright © 2025 Infinite Computer Solutions. All rights reserved.
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
 * Enum representing the relationship of an insurance member to the primary
 * insured person.
 *
 * Used to categorize dependents and individuals in insurance plans. Examples
 * include SON1, SON2, FATHER, MOTHER, HUSBAND, WIFE, SELF, DAUGHTER1,
 * DAUGHTER2, INDIVIDUAL.
 */
public enum Relation {
	SON2, SON1, FATHER, MOTHER, HUSBAND, WIFE, SELF, DAUGHTER1, DAUGHTER2, INDIVIDUAL
}
