/**
 * The `DoctorType` enum defines the types of doctors available in the system.
 * 
 * It categorizes doctors into two types:
 * 
 * - `STANDARD`: Represents a regular, full-time doctor who is consistently available and part healthsure's provider's staff.
 * - `ADHOC`: Represents a doctor who works on an as-needed, temporary basis, often filling in for others or working on specific tasks or projects.
 * 
 * This enum is used to differentiate between different types of doctors in the system, helping to manage and track their availability and role within the healthsure's provider organization.
 */
package com.infinite.jsf.provider.model;

public enum DoctorType {
	STANDARD,ADHOC
}
