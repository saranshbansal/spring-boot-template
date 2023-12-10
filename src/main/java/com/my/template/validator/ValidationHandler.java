package com.my.template.validator;

import com.my.template.exception.GenericRuntimeException;
import com.my.template.exception.ValidationException;
import com.my.template.util.MessageSource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Apply particular validation rules from suitable validator.
 *
 * Created by Yevhen Khvostov on 11/02/2021.
 */
@Component
@AllArgsConstructor
public class ValidationHandler {

	private final List<Validator> validators;

	/**
	 * Executes a validator for target object
	 *
	 * @param object target object
	 * @throws ValidationException if any validation errors exist
	 */
	@SuppressWarnings("unchecked")
	public void validate(Object object) {
		Map<String, String> errors = validators.stream()
				.filter(v -> v.canValidate(object))
				.findFirst()
				.orElseThrow(() -> new GenericRuntimeException(MessageSource.ERROR_VALIDATION_TYPE_INVALID.getText(object.getClass())))
				.validate(object);

		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}
}
