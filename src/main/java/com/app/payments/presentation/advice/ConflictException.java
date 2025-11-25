package com.app.payments.presentation.advice;

@SuppressWarnings("serial")
public class ConflictException extends RuntimeException{
	public ConflictException(String msg) { super(msg); }
}
