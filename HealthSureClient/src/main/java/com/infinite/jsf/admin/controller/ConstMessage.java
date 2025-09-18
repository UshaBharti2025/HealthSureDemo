/**
 * Copyright © 2025 Infinite Computer Solution. All rights reserved.
 */

/**
 * This package contains controller and utility classes for managing administrative tasks
 * such as pharmacy review, approval workflows, and related operations in the JSF application.
 */

package com.infinite.jsf.admin.controller;

/**
 * Enum class containing constant messages used during pharmacy approval
 * processes, including validation, approval status, and user notifications.
 */

public enum ConstMessage {

	INVALID_AADHAR("Invalid Aadhar !! Must start with 'AADHAR' followed by 6 digits."),
	INVALID_LICENSE("Invalid License No! Must start with 'LIC' followed by 5 digits."),
	INVALID_GST("Invalid GST No! Must start with 'GSTIN', followed by 4 digits and end with 2 uppercase letters."),
	PHARMACY_UPDATE_ERROR ("Error occurred while updating the Pharmacy Status"),

	APPROVED_SUCCESSFULLY("Approved successfully"), REJECTED_SUBJECT("PHARMACY STATUS : REJECTED"),
	REJECTED_HTML_TEMPLATE("<h2 style='color:red;'>Pharmacy Review Status: Rejected</h2>" + "<p>Dear Pharmacy,</p>"
			+ "<p>We regret to inform you that your pharmacy application has been <strong>rejected</strong> based on the following validation:</p>"
			+ "<p style='color:#333;'>%s</p>" + "<p><b>Reviewed By:</b> Ravikant Turi</p>"
			+ "<hr><small>This is an automated message. Please do not reply.</small>");

	private final String message;

	ConstMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
