/**
* ErrorMessage.java
*
* Singleton class to hold all application-wide message constants. Ensures only
* one instance is created and accessed in a thread-safe manner.
*
* Copyright © 2025 Infinite Computer Solution. All rights reserved.
*/
package com.infinite.jsf.util;

public class ErrorMessage {

    // Validation Messages
    public static final String PROVIDER_NAME_EMPTY = "Provider Name Can Not Be Empty. Please Enter A Value";
    public static final String DOCTOR_NAME_EMPTY = "Doctor Name Can Not Be Empty. Please Enter A Value";
    public static final String INVALID_EMAIL = "Invalid Email Id.";
    public static final String INVALID_EMAIL_FORMAT = "Email should contain one '@' and have suffix 'gmail.com' or 'google.com'";
	public static final String INVALID_EMAIL_PREFIX = "Enter Complete Email Id.";

    
	
    public static final String INVALID_TELEPHONE = "Invalid Telephone number. It should contain exactly 10 digits.";
    public static final String INVALID_ZIPCODE = "Invalid ZIPCODE.";
    public static final String SUCCESSFUL_VALIDATION = "Validation Successful.";
    public static final String PROVIDER_APPROVED = "Provider Approved.";
    public static final String PROVIDER_REJECTED = "Provider Rejected.";

    // Provider Status Messages
    public static final String PROVIDER_NOT_FOUND = "Provider Not Found.";
    public static final String INVALID_SEARCH_CRITERIA = "Invalid Search Criteria.";

    
    
    // Search Validation Messages
    public static final String INVALID_DOCTOR_ID = " Invalid Doctor ID. Must Begin With 'D' followed by 3 digits.";
    public static final String INVALID_DOCTOR_NAME = "Invalid Doctor Name. Only Alphabets Are Allowed..";
    public static final String INVALID_DOCTOR_NAME_LENGTH = "Invalid Doctor Name. Minimum 5 characters needed";
    public static final String INVALID_SPECIALIZATION = "No Specialization Found.";
    public static final String INVALID_LICENSE_NO = "Invalid License Number. Must Begin With 'L' followed by 3 digits.";
    public static final String INVALID_PROVIDER_ID = "Invalid Provider ID. Must Begin With 'P' followed by 3 digits.";
    public static final String PROVIDER_NAME_LENGTH = "Provider Name should be Minimum 5 Letters.";
    public static final String PROVIDER_NAME_NUMBER = "Invalid Provider Name";
    
    // Update Validation Messages
    public static final String ERROR_UPDATE_DOCTOR = "Invalid Doctor Name";
    public static final String DOCTOR_NOT_FOUND_BY_ID = "Invalid Doctor ID. No Doctor Found With This ID";
    public static final String ERROR_UPDATE_PROVIDER = "Invalid Provider Name";
    public static final String INVALID_PROVIDER_NAME = "Invalid Provider Name. Only Alphabets Allowed.";
    public static final String EMPTY_PROVIDERID = "Please Enter A Provider ID";
    public static final String EMPTY_PROVIDERNAME = "Please Enter A Provider Name";
    public static final String EMPTY_DOCTORNAME = "Please Enter A Doctor Name";
    public static final String EMPTY_DOCTORID = "Please Enter A Doctor ID";
    public static final String EMPTY_EMAILID = "Please Enter An Email ID";
    public static final String EMPTY_ADDRESS = "Please Enter An Address";
    public static final String EMPTY_CITY= "Please Enter A City Name";
    public static final String EMPTY_TELEPHONE = "Please Enter A Telephone Number";
	public static final String EMPTY_PHONE = "Please Enter A Phone Number";
    public static final String EMPTY_LICENSE_NO = "Please Enter A License Number";
	public static final String INVALID_DOCTOR_QUALIFICATION = "Not a Valid Medical Qualification Degree. Please Enter A Valid Qualification.";
	public static final String INVALID_DOCTOR_SPECIALIZATION = "Not a Valid Medical Specialization. Please Enter A Valid Specialization.";
	public static final String INVALID_ADDRESS = "Enter Correct Address. Minimum 3 characters required. Must contain atleast one alphabet.";
	public static final String INVALID_CITY = "Enter Correct City. Minimum 3 characters required.Must contain atleast one alphabet.";
	public static final String INVALID_NAME = "Name cannot contain '@', numeric characters, or special characters. Please Use Only Alphabets.";
	public static final String INVALID_SPACES = "Maximun One space Allowed.";



   
}