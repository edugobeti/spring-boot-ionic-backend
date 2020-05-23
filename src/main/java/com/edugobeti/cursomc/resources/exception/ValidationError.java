package com.edugobeti.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
	}
	
	List<FieldMessage> errors = new ArrayList<>();

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addErrors(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}