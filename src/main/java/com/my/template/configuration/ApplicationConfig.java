package com.my.template.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines generic application configuration
 *
 * Created by Sergey Dukhnich on 29/07/2019.
 */
@Configuration
public class ApplicationConfig {

	/**
	 * Model mapper bean is needed to transform entities to DTO and vice versa
	 *
	 * @return ModelMapper instance
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
