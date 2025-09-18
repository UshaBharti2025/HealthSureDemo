/**
 * The `LoginStatus` enum represents the possible states of a provider's account registration process.
 * 
 * It provides three distinct statuses:
 * 
 * - `PENDING`: The provider's account is in the process of being reviewed or awaiting approval.
 * - `APPROVED`: The provider's account has been approved and is active.
 * - `REJECTED`: The provider's account has been rejected and is not active.
 * 
 * This enum is used to track and manage the login/registration status of providers within healthsure system.
 */
package com.infinite.jsf.provider.model;

public enum LoginStatus {
	 APPROVED,PENDING,REJECTED;
}