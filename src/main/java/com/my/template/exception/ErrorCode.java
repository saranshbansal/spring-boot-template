package com.my.template.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Represents error codes which can be thrown by application
 *
 * Created by Sergey Dukhnich on 13/10/2017.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_FOUND("Not found"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	BAD_REQUEST("Bad request"),
	FORBIDDEN("Access denied"),
	UNAUTHORIZED("Unauthorized");

	private final String message;

	/**
	 * Converts HttStatus error codes to ErrorCode element
	 *
	 * @param status HttpStatus object
	 * @return ErrorCode object
	 */
	public static ErrorCode resolveErrorCode(HttpStatus status) {
		switch (status) {
			case NOT_FOUND:
				return NOT_FOUND;
			case BAD_REQUEST:
				return BAD_REQUEST;
			case FORBIDDEN:
				return FORBIDDEN;
			case UNAUTHORIZED:
				return UNAUTHORIZED;
			default:
				return INTERNAL_SERVER_ERROR;
		}
	}
}
