package com.my.template.web.dto;

import com.my.template.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * DTO for error message response
 * Being transformed to JSON to show error details for consumers
 *
 * Created by Sergey Dukhnich on 13/10/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
	private ErrorCode errorCode;
	private String description;

	/**
	 * Error response with an errorCode and a description
	 *
	 * @param errorCode code of an error
	 * @param description description of an error
	 */
	public ErrorResponse(ErrorCode errorCode, String description) {
		this.errorCode = errorCode;
		this.description = isBlank(description) ? errorCode.getMessage() : description;
	}
}
