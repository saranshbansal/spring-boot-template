package com.my.template.configuration;

import com.my.template.exception.GenericRuntimeException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static com.my.template.util.MessageBuilder.buildMessage;
import static com.my.template.util.MessageSource.ERROR_WEB_SECURITY_FILTER;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Web security configuration to allow method level authorisation with annotations
 *
 *
 * Created by Saransh Bansal on 11/12/2023.
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	private static final String[] AUTH_WHITELIST = {
			"/swagger-ui.html/**",
			"/swagger-ui/**",
			"/api-docs**",
			"/csrf",
			"/*/webjars**",
			"/*.js",
			"/*.css",
			"/*.png",
			"/*.woff",
			"/*.woff2",
			// actuator endpoint
			"/actuator/**",
			"h2-console/**"
	};

	/**
	 * Explicit request matcher bean to mitigate vulnerability:
	 * https://github.com/jzheaux/cve-2023-34035-mitigations
	 *
	 * @param introspector handler mapping config class
	 * @return instance of {@link MvcRequestMatcher.Builder}
	 */
	@Bean
	public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	/**
	 * Configures access to application with reduced requirements to security
	 * to allow local testing and h2 console.
	 *
	 * @param mvc request matcher configs
	 * @param httpSecurity security object
	 * @return instance of {@link SecurityFilterChain}
	 */
	@Bean
	public SecurityFilterChain filterChain(MvcRequestMatcher.Builder mvc, HttpSecurity httpSecurity) {
		try {
			httpSecurity
					.csrf(AbstractHttpConfigurer::disable) // disables csrf
					.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // disables xframe deny warnings
					.sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // set session as stateless
					.httpBasic(AbstractHttpConfigurer::disable) // disables pop-up
					.authorizeHttpRequests(auth -> auth
							.requestMatchers(getMvcRequestMatchers(mvc)).permitAll()
							.requestMatchers(toH2Console()).permitAll()
							.anyRequest().authenticated()
					)
					.formLogin(withDefaults());

			return httpSecurity.build();
		} catch (Exception ex) {
			throw new GenericRuntimeException(buildMessage(ERROR_WEB_SECURITY_FILTER.getText(ex.getMessage())), ex);
		}
	}

	/**
	 * Custom authentication
	 *
	 * @return instance of {@link UserDetailsService}
	 */
	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		UserDetails user = User
				.withUsername("user")
				.password(passwordEncoder().encode("user"))
				.authorities("read").build();
		userDetailsService.createUser(user);
		return userDetailsService;
	}

	/**
	 * Custom password encoder bean
	 *
	 * @return instance of {@link BCryptPasswordEncoder}
	 */
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Construct a list of request matchers for whitelisted request patterns
	 *
	 * @param mvc explicit mvc request matcher helper
	 * @return list of {@link MvcRequestMatcher} per whitelisted request pattern
	 */
	private MvcRequestMatcher[] getMvcRequestMatchers(MvcRequestMatcher.Builder mvc) {
		MvcRequestMatcher[] requestMatchers = new MvcRequestMatcher[AUTH_WHITELIST.length];
		for (int i = 0; i < AUTH_WHITELIST.length; i++) {
			requestMatchers[i] = mvc.pattern(AUTH_WHITELIST[i]);
		}
		return requestMatchers;
	}
}
