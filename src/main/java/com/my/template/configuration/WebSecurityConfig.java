package com.my.template.configuration;

import com.my.template.exception.GenericRuntimeException;
import com.my.template.util.MessageBuilder;
import com.my.template.util.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.my.template.util.MessageBuilder.buildMessage;
import static com.my.template.util.MessageSource.ERROR_WEB_SECURITY_FILTER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Web security configuration to allow method level authorisation with annotations
 *
 * Created by Eugene Dushenin on 18/03/2020.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	private static final String[] unsecurePaths = {
			"/*/swagger-ui.html",
			"/*/swagger-ui/**",
			"/*/api-docs**",
			"/csrf",
			"/*/webjars**",
			"/*/*.js",
			"/*/*.css",
			"/*/*.png",
			"/*/*.woff",
			"/*/*.woff2",
			// actuator endpoint
			"/*/actuator/**"
	};

	/**
	 * Configures access to application with reduced requirements to security
	 * to allow local testing and h2 console.
	 *
	 * @param http security object
	 * @return instance of {@link SecurityFilterChain}
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		try {
			http
					.authorizeRequests()
					.antMatchers(unsecurePaths).permitAll()
					.and()
					.sessionManagement().sessionCreationPolicy(STATELESS)
					.and().formLogin().disable()
					.csrf().disable().headers().frameOptions().sameOrigin().and() // disables xframe deny warnings
					.cors().and() // uses cors settings - only disabled if WebConfigLocal running
					.httpBasic().disable(); // disables pop-up
			return http.build();
		} catch (Exception ex) {
			throw new GenericRuntimeException(buildMessage(ERROR_WEB_SECURITY_FILTER.getText(ex.getMessage())), ex);
		}
	}
}