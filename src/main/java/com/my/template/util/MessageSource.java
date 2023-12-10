package com.my.template.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Application messages holder.
 *
 * Created by Yevhen Khvostov on 11/02/2021.
 */
@Getter
@AllArgsConstructor
public enum MessageSource {
	ERROR_INVALID_TYK_TOKEN_PROVIDED("Invalid TYK token provided: {}"),
	ERROR_USER_NOT_AUTHENTICATED("Could not retrieve user details from context"),
	ERROR_VALIDATION_TYPE_INVALID("Validation for type {} is not supported"),
	ERROR_WEB_SECURITY_FILTER("Web security configuration error: {}");

	private final String text;

	/**
	 * Get enum value
	 *
	 * @return Enum value
	 */
	@Override
	public String toString() {
		return text;
	}

	/**
	 * Get evaluated value of enum based on given params
	 *
	 * @param params message parameters
	 * @return message
	 */
	public String getText(Object... params) {
		return MessageBuilder.buildMessage(this.text, params);
	}
}
