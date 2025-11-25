package com.app.payments.presentation.advice;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
	public NotFoundException(String msg) { super(msg); }
}
