package com.my.template.exception;

import lombok.Getter;

import static com.my.template.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.my.template.util.MessageBuilder.buildMessage;

/**
 * Main class for checked exceptions which are thrown during processing fails
 * and are processed by CommonExceptionHandler
 *
 * Created by Sergey Dukhnich on 06/06/2018
 */
@Getter
public class GenericException extends Exception {

	private static final ErrorCode DEFAULT_ERROR_CODE = INTERNAL_SERVER_ERROR;

	private final ErrorCode errorCode;

	/**
	 * Exception with string message and arguments which are used to substitute placeholders
	 *
	 * @param message string message
	 * @param args one or several arguments for placeholders substitution
	 */
	public GenericException(String message, Object... args) {
		super(buildMessage(message, args));
		this.errorCode = DEFAULT_ERROR_CODE;
	}

	/**
	 * Exception wrapping some other exception
	 *
	 * @param cause cause of exception
	 */
	public GenericException(Throwable cause) {
		super(cause);
		this.errorCode = DEFAULT_ERROR_CODE;
	}

	/**
	 * Exception with error code
	 *
	 * @param errorCode error code type
	 */
	public GenericException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Exception with error code and message
	 *
	 * @param errorCode error code type
	 * @param message message template
	 * @param args values for message's placeholders
	 */
	public GenericException(ErrorCode errorCode, String message, Object... args) {
		super(buildMessage(message, args));
		this.errorCode = errorCode;
	}
}
