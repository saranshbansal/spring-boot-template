package com.my.template.exception;

import lombok.Getter;

import static com.my.template.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.my.template.util.MessageBuilder.buildMessage;

/**
 * Main class for all runtime exceptions which are processed by CommonExceptionHandler
 *
 * Created by Sergey Dukhnich on 13/10/2017.
 */
@Getter
public class GenericRuntimeException extends RuntimeException {

	private static final ErrorCode DEFAULT_ERROR_CODE = INTERNAL_SERVER_ERROR;

	private final ErrorCode errorCode;

	/**
	 * Runtime exception with simple message
	 *
	 * @param message simple string message
	 */
	public GenericRuntimeException(String message) {
		super(message);
		this.errorCode = DEFAULT_ERROR_CODE;
	}

	/**
	 * Runtime exception with simple string message which wraps some other exception
	 *
	 * @param message simple string message
	 * @param cause cause to be wrapped
	 */
	public GenericRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = DEFAULT_ERROR_CODE;
	}

	/**
	 * Runtime exception wrapping some other exception
	 *
	 * @param cause cause to be wrapped
	 */
	public GenericRuntimeException(Throwable cause) {
		super(cause);
		this.errorCode = DEFAULT_ERROR_CODE;
	}

	/**
	 * Runtime exception with string message and arguments which are used to substitute placeholders
	 *
	 * @param message string messages
	 * @param args one or several arguments for placeholders substitution
	 */
	public GenericRuntimeException(String message, Object... args) {
		super(buildMessage(message, args));
		this.errorCode = DEFAULT_ERROR_CODE;
	}

	/**
	 * Exception with error code
	 *
	 * @param errorCode error code type
	 */
	public GenericRuntimeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Exception with error code and message
	 *
	 * @param errorCode error code type
	 * @param message message template
	 * @param args values for message's placeholders
	 */
	public GenericRuntimeException(ErrorCode errorCode, String message, Object... args) {
		super(buildMessage(message, args));
		this.errorCode = errorCode;
	}
}
