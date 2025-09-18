package com.infinite.jsf.insurance.exception;

public class MemberPlanException extends Exception{
	
	public MemberPlanException(String message) {
		super(message);
	}
	public MemberPlanException(String message ,Throwable throwable) {
		super(message,throwable);
	}

}
