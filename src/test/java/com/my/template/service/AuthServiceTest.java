package com.my.template.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.template.domain.dto.PrincipalUserDto;
import com.my.template.exception.GenericRuntimeException;
import com.my.template.MockInitializer;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.testng.annotations.Test;

import static com.my.template.TestData.TYK_TOKEN;
import static org.testng.Assert.*;

/**
 * Unit test for {@link AuthService}
 *
 * Created by Saransh Bansal on 30/06/2023.
 */
public class AuthServiceTest extends MockInitializer {

	@InjectMocks
	private AuthService authService;

	@Spy
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test(expectedExceptions = GenericRuntimeException.class,
			expectedExceptionsMessageRegExp = "Invalid TYK token provided: invalid_token")
	public void getUserDetailsFromToken_throwsError_whenInvalidTokenProvided() {
		// when
		authService.getUserDetails("invalid_token");
	}

	@Test
	public void getUserDetailsFromToken_returnsUserDetails() {
		// given
		String username = "ccvUser03";
		// when
		PrincipalUserDto userDetails = authService.getUserDetails(TYK_TOKEN);
		// then
		assertNotNull(userDetails);
		assertTrue(userDetails.getIsActive());
		assertEquals(username, userDetails.getUserName());
		assertEquals(username, userDetails.getFirstName());
		assertEquals(username, userDetails.getLastName());
	}
}