package com.my.template;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Application endpoints holder.
 *
 * Created by Saransh Bansal on 10/08/2022.
 */
@NoArgsConstructor(access = PRIVATE)
public final class TemplateEndpoints {
	public static final String BASE_PATH = "${controller.path:}";
	public static final String VERSION = "/v1";
	public static final String AUTH_CONTROLLER_PATH = BASE_PATH + VERSION + "/auth";

	public static final String PARSE_TOKEN = "/util/parse-token";
}
