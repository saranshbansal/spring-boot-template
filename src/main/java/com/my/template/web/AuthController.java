package com.my.template.web;

import com.my.template.domain.dto.PrincipalUserDto;
import com.my.template.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.my.template.TemplateEndpoints.AUTH_CONTROLLER_PATH;
import static com.my.template.TemplateEndpoints.PARSE_TOKEN;

/**
 * Controller that is used to get authenticated user data from IAM token
 *
 * Created by Saransh Bansal on 26/06/2023
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH_CONTROLLER_PATH)
public class AuthController {

	private final AuthService authService;

	/**
	 * Gets user details from ping federate token
	 *
	 * @param token received from ping federate service
	 * @return authenticated user details
	 */
	@GetMapping(PARSE_TOKEN)
	@Operation(summary = "Gets user from given JWT token for debugging purpose")
	public PrincipalUserDto getUserDetailsFromToken(@RequestHeader(name = "Iam-Claimsetjwt") String token) {
		return authService.getUserDetails(token);
	}
}
