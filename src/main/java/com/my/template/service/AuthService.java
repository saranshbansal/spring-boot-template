package com.my.template.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.template.domain.dto.PrincipalUserDto;
import com.my.template.exception.GenericRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

import static com.my.template.domain.IAMUserPrincipalParams.*;
import static com.my.template.exception.ErrorCode.BAD_REQUEST;
import static com.my.template.util.MessageSource.ERROR_INVALID_TYK_TOKEN_PROVIDED;
import static java.util.Base64.getUrlDecoder;

/**
 * Service class used to work with user details
 *
 * Created by Saransh Bansal on 15/11/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private static final Base64.Decoder decoder = getUrlDecoder();

	private final ObjectMapper objectMapper;

	/**
	 * Retrieves user details from JWT token
	 *
	 * @param token JWT token
	 * @return user details
	 */
	public PrincipalUserDto getUserDetails(String token) {
		try {
			String[] chunks = token.split("\\.");
			String payload = new String(decoder.decode(chunks[1]));
			Map<String, Object> map = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
			});

			return PrincipalUserDto.builder()
						   .isActive((Boolean) map.get(ACTIVE_KEY.getValue()))
						   .domain(map.get(DOMAIN_KEY.getValue()).toString())
						   .userName(map.get(USERNAME_KEY.getValue()).toString())
						   .firstName(map.get(FIRSTNAME_KEY.getValue()).toString())
						   .lastName(map.get(LASTNAME_KEY.getValue()).toString())
						   .email(map.get(EMAIL_KEY.getValue()).toString())
						   .memberOf(map.get(MEMBEROF_KEY.getValue()).toString())
						   .build();
		} catch (Exception e) {
			throw new GenericRuntimeException(BAD_REQUEST, ERROR_INVALID_TYK_TOKEN_PROVIDED.getText(token));
		}
	}
}
