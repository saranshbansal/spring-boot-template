package com.my.template.web;

import com.my.template.exception.GenericException;
import com.my.template.exception.GenericRuntimeException;
import com.my.template.exception.ValidationException;
import com.my.template.web.dto.ErrorResponse;
import com.my.template.web.dto.ValidationErrorResponse;
import com.my.template.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.my.template.exception.ErrorCode.BAD_REQUEST;
import static com.my.template.exception.ErrorCode.FORBIDDEN;

/**
 * Generic exception handler for controllers
 * Handles defined exceptions, transforming them into user friendly JSON error response
 *
 * Created by Sergey Dukhnich on 31/07/2017.
 */
@Slf4j
@ControllerAdvice
class CommonExceptionHandler {

	/**
	 * Intercepts AccessDeniedException exception and sends corresponding error
	 *
	 * @param ex target exception
	 * @return ResponseEntity with string error message and status code
	 */
	@ExceptionHandler(AccessDeniedException.class)
	ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(FORBIDDEN, ex.getMessage()), HttpStatus.FORBIDDEN);
	}

	/**
	 * Intercept ValidationException and send validation errors as a json
	 *
	 * @param ex target exception
	 * @return ResponseEntity with json of validation errors and and status code
	 */
	@ExceptionHandler(ValidationException.class)
	ResponseEntity<ErrorResponse> handleValidationError(ValidationException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(new ValidationErrorResponse<>(BAD_REQUEST, ex.getMessage(), ex.getErrors()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Intercept IllegalArgumentException and send validation errors as a json
	 *
	 * @param ex target exception
	 * @return ResponseEntity with json of validation errors and and status code
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<ErrorResponse> handleIllegalArgumentError(IllegalArgumentException ex) {
		log.error(ex.getMessage());
		return handleException(BAD_REQUEST, ex.getMessage());
	}

	/**
	 * Generic exception handler for GenericException exception
	 *
	 * @param ex GenericException instance
	 * @return ResponseEntity instance
	 */
	@ExceptionHandler(GenericException.class)
	ResponseEntity<ErrorResponse> handleGenericAppException(GenericException ex) {
		log.error(ex.getMessage());
		return handleException(ex.getErrorCode(), ex.getMessage());
	}

	/**
	 * Generic runtime exception handler for GenericRuntimeException exception
	 *
	 * @param ex GenericRuntimeException instance
	 * @return ResponseEntity instance
	 */
	@ExceptionHandler(GenericRuntimeException.class)
	ResponseEntity<ErrorResponse> handleGenericRuntimeAppException(GenericRuntimeException ex) {
		return handleException(ex.getErrorCode(), ex.getMessage());
	}

	/**
	 * Generic exception handler
	 *
	 * @param code error code
	 * @param message error message
	 * @return response object
	 */
	private ResponseEntity<ErrorResponse> handleException(ErrorCode code, String message) {
		log.error(message);
		ErrorResponse errorResponse = new ErrorResponse(code, message);
		switch (code) {
			case NOT_FOUND:
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			case BAD_REQUEST:
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
			case UNAUTHORIZED:
				return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
			case FORBIDDEN:
				return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
