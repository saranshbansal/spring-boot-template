package com.my.template.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration
 * It is enabled by default. To disable it use below properties:
 * <code>
 * springdoc.swagger-ui.enabled=false
 * springdoc.api-docs.enabled=false
 * </code>
 * NOTE: replace "Template" name in configuration below with necessary project name
 *
 * Created by Saransh Bansal on 09/02/2023.
 */
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", matchIfMissing = true)
@Configuration
public class SwaggerConfig {

	/**
	 * Swagger API details
	 *
	 * @return OpenAPI object
	 */
	@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI().info(new Info()
										  .title("Learn Spring Boot - Template")
										  .description("Spring REST APIs for learning Spring Boot"));
	}
}