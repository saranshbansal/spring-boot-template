package com.my.template.validator;

import java.util.Map;

/**
 * Validator interface
 * NOTE: this template shows common logic used across agile apps.
 *
 * @param <T> type of a validated object
 *
 * Created by Yevhen Khvostov on 11/02/2021.
 */
public interface Validator<T> {
	/**
	 * Validates target object
	 *
	 * @param object target object
	 * @return map of errors (empty if valid)
	 */
	Map<String, String> validate(T object);

	/**
	 * Checks whether validator is suitable for passed object.
	 *
	 * @param object target object
	 * @return true if object can be validated by validator, false otherwise
	 */
	boolean canValidate(Object object);
}
