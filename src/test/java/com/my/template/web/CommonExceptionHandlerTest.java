package com.my.template.web;

import com.my.template.exception.GenericException;
import com.my.template.exception.ValidationException;
import com.my.template.web.dto.ErrorResponse;
import com.my.template.web.dto.ValidationErrorResponse;
import com.my.template.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.my.template.exception.ErrorCode.*;
import static org.testng.Assert.*;

/**
 * Exception handler unit tests
 *
 * Created by Sergey Dukhnich on 16/10/2017.
 */
public class CommonExceptionHandlerTest {

	private final static String CUSTOM_UNAUTHORIZED_MESSAGE = "Current user doesn't have permissions to proceed";
	private final CommonExceptionHandler commonExceptionHandler = new CommonExceptionHandler();

	@Test(dataProvider = "exceptionDataProvider")
	public void shouldReturnCorrectErrorResponse(GenericException ex, HttpStatus expectedStatus, ErrorResponse expectedResponse) {
		//when
		ResponseEntity<ErrorResponse> response = commonExceptionHandler.handleGenericAppException(ex);
		//then
		assertEquals(expectedStatus, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(expectedResponse.getErrorCode(), response.getBody().getErrorCode());
		assertEquals(expectedResponse.getDescription(), response.getBody().getDescription());
	}

	@Test
	public void shouldReturnValidationError() {
		//given
		Map<String, String> errors = new HashMap<>(2);
		errors.put("error1", "error1 text");
		errors.put("error2", "error2 text");
		ValidationException ex = new ValidationException(errors);
		//when
		ResponseEntity<ErrorResponse> responseEntity = commonExceptionHandler.handleValidationError(ex);
		//then
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getBody().getErrorCode(), BAD_REQUEST);
		assertTrue(responseEntity.getBody() instanceof ValidationErrorResponse);
		ValidationErrorResponse<?> validationResponse = (ValidationErrorResponse<?>) responseEntity.getBody();
		assertEquals(validationResponse.getData().size(), 2);
	}

	@DataProvider
	private Object[][] exceptionDataProvider() {
		return new Object[][]{
				{new GenericException(BAD_REQUEST), HttpStatus.BAD_REQUEST,
						new ErrorResponse(BAD_REQUEST, ErrorCode.BAD_REQUEST.getMessage())},
				{new GenericException(NOT_FOUND), HttpStatus.NOT_FOUND,
						new ErrorResponse(NOT_FOUND, ErrorCode.NOT_FOUND.getMessage())},
				{new GenericException(UNAUTHORIZED), HttpStatus.UNAUTHORIZED,
						new ErrorResponse(UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getMessage())},
				{new GenericException(FORBIDDEN), HttpStatus.FORBIDDEN,
						new ErrorResponse(FORBIDDEN, ErrorCode.FORBIDDEN.getMessage())},
				{new GenericException(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR,
						new ErrorResponse(INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMessage())},
				{new GenericException(CUSTOM_UNAUTHORIZED_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR,
						new ErrorResponse(INTERNAL_SERVER_ERROR, CUSTOM_UNAUTHORIZED_MESSAGE)}
		};
	}
}