package com.my.template.web.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.my.template.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

/**
 * DTO for error message response
 * Being converted to JSON with validation errors under data section
 *
 * @param <T> type of error message, Serializable
 *
 * Created by Sergey Dukhnich on 13/10/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorResponse<T extends Serializable> extends ErrorResponse {
	private static final long serialVersionUID = -3801053302957466398L;

	@JsonTypeInfo(use = CLASS, property = "@class")
	private Map<String, T> data;

	/**
	 * Validation error response
	 *
	 * @param errorCode code of an error
	 * @param description description of an error
	 * @param data Map of validation errors
	 */
	public ValidationErrorResponse(ErrorCode errorCode, String description, Map<String, T> data) {
		super(errorCode, description);
		this.data = data;
	}
}
