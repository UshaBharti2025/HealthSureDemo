package com.infinite.jsf.insurance.exception;

public class InsuranceCoverageOptionException extends Exception {

	InsuranceCoverageOptionException(String message) {
		super(message);
	}

	public InsuranceCoverageOptionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
