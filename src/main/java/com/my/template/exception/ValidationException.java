package com.my.template.exception;

import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

import static com.my.template.exception.ErrorCode.BAD_REQUEST;

/**
 * Validation exception
 *
 * Created by Sergey Dukhnich on 07/06/2018.
 */
@Getter
public class ValidationException extends GenericRuntimeException {

	private static final String ERROR_MESSAGE = "Validation error";
	private final Map<String, ? extends Serializable> errors;

	/**
	 * Exception with validation errors
	 *
	 * @param errors map of validation errors
	 */
	public ValidationException(Map<String, ? extends Serializable> errors) {
		super(BAD_REQUEST, ERROR_MESSAGE);
		this.errors = errors;
	}

	/**
	 * Exception with error message, validation errors and arguments which are used to substitute placeholders
	 *
	 * @param message string message
	 * @param errors map of validation errors
	 * @param args one or several arguments for placeholders substitution
	 */
	public ValidationException(String message, Map<String, ? extends Serializable> errors, Object... args) {
		super(message, args);
		this.errors = errors;
	}

	/**
	 * Exception with wrapped exception and validation errors
	 *
	 * @param cause underlying exception to be wrapped
	 * @param errors map of validation errors
	 */
	public ValidationException(Throwable cause, Map<String, ? extends Serializable> errors) {
		super(cause);
		this.errors = errors;
	}

	/**
	 * Exception with error code and validation errors
	 *
	 * @param errorCode one of {@link ErrorCode}
	 * @param errors map of validation errors
	 */
	public ValidationException(ErrorCode errorCode, Map<String, ? extends Serializable> errors) {
		super(errorCode);
		this.errors = errors;
	}

	/**
	 * Exception with error code, message, validation errors and arguments which are used to substitute placeholders
	 *
	 * @param errorCode one of {@link ErrorCode}
	 * @param message string message
	 * @param errors map of validation errors
	 * @param args one or several arguments for placeholders substitution
	 */
	public ValidationException(ErrorCode errorCode, String message, Map<String, ? extends Serializable> errors, Object... args) {
		super(errorCode, message, args);
		this.errors = errors;
	}
}
