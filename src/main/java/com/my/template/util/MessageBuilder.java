package com.my.template.util;

import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Helper to create message using static part and list of parameters
 *
 * Created by Sergey Dukhnich on 13/10/2017.
 */
@NoArgsConstructor(access = PRIVATE)
public final class MessageBuilder {

	/**
	 * Creates message using static part with placeholder {} and list of parameters
	 *
	 * @param message static string with placeholders
	 * @param params values for placeholders
	 * @return built message
	 */
	public static String buildMessage(String message, Object... params) {
		StringBuilder sb = new StringBuilder(message);
		int index;
		if (isNotEmpty(params)) {
			for (Object param : params) {
				index = sb.indexOf("{}");
				if (index >= 0) {
					sb.replace(index, index + 2, ofNullable(param).map(Objects::toString).orElse(EMPTY));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Create message using static part with placeholders {} and map of parameters
	 * If parameter's name can be found in template its placeholder will be changed by value
	 *
	 * @param message static string with placeholders
	 * @param params map of parameters
	 * @return string with replaced placeholders if params are found
	 */
	public static String buildMessageWithNamedParams(String message, Map<String, Object> params) {
		if (params == null || isBlank(message)) {
			return message;
		}

		StringBuilder sb = new StringBuilder(message);
		int index;
		for (Map.Entry<String, Object> e : params.entrySet()) {
			index = sb.indexOf("{" + e.getKey() + "}");
			if (index >= 0 && e.getValue() != null) {
				sb.replace(index, index + e.getKey().length() + 2, e.getValue().toString());
			}
		}
		return sb.toString();
	}
}
