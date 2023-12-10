package com.my.template.web;

import com.my.template.domain.dto.PrincipalUserDto;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testng.annotations.Test;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Integration test for {@link AuthController}
 *
 * Created by Saransh Bansal on 03/07/2023.
 */
public class AuthControllerIT extends AbstractControllerIT {

	private static final String PARSE_TOKEN_URL = "/api/template/v1/auth/util/parse-token";

	@Test
	public void getUserDetailsFromToken_returnsUserDetails() throws Exception {
		// given
		Boolean active = true;
		String username = "ccvUser03";
		String firstName = "ccvUser03";
		String lastName = "ccvUser03";
		// when
		MvcResult mvcResult = getRequest(PARSE_TOKEN_URL, SC_OK);
		// then
		PrincipalUserDto userDetailsDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				PrincipalUserDto.class);

		assertNotNull(userDetailsDto);
		assertEquals(active, userDetailsDto.getIsActive());
		assertEquals(username, userDetailsDto.getUserName());
		assertEquals(firstName, userDetailsDto.getFirstName());
		assertEquals(lastName, userDetailsDto.getLastName());
	}

	@Test
	public void getUserDetailsFromToken_returnsErrorResponse_whenInvalidToken() throws Exception {
		// when
		mockMvc.perform(get(PARSE_TOKEN_URL)
						.contentType(APPLICATION_JSON)
						.header("Iam-Claimsetjwt", "invalid_token")
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(SC_BAD_REQUEST))
				.andReturn();
	}
}