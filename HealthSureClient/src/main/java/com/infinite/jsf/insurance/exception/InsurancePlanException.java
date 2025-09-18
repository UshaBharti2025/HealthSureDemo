package com.infinite.jsf.insurance.exception;

public class InsurancePlanException extends Exception {

	InsurancePlanException(String message) {
		super(message);
	}

	public InsurancePlanException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
