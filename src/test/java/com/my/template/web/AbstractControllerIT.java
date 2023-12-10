package com.my.template.web;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.my.template.TemplateSpringBootTomcatApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Map;

import static com.my.template.TestData.IAM_CLAIMSETJWT;
import static com.my.template.TestData.TYK_TOKEN;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Abstract IT configuration. Should be extended in child integration test classes
 *
 * Created by Saransh Bansal on 05/06/2023.
 */
@ActiveProfiles({"test", "mock"})
@SpringBootTest
@ContextConfiguration(classes = TemplateSpringBootTomcatApplication.class)
@AutoConfigureMockMvc
@TestExecutionListeners(MockitoTestExecutionListener.class)
public abstract class AbstractControllerIT extends AbstractTestNGSpringContextTests {

	ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Autowired
	protected MockMvc mockMvc;

	/**
	 * Sends GET request to controller expecting success response with status provided
	 *
	 * @param uri resource path
	 * @return result object
	 * @throws Exception if failure
	 */
	protected final MvcResult getRequest(String uri, int expectedStatus) throws Exception {
		return mockMvc
					   .perform(get(uri)
										.contentType(APPLICATION_JSON)
										.header(IAM_CLAIMSETJWT, TYK_TOKEN)
					   )
					   .andDo(MockMvcResultHandlers.print())
					   .andExpect(status().is(expectedStatus))
					   .andReturn();
	}

	/**
	 * Sends POST request to controller expecting success response with status provided
	 *
	 * @param uri resource path
	 * @param body request body
	 * @return result object
	 * @throws Exception if failure
	 */
	final MvcResult postRequest(String uri, String body, int expectedStatus) throws Exception {
		return mockMvc
					   .perform(post(uri)
										.header(ACCEPT, APPLICATION_JSON)
										.header(IAM_CLAIMSETJWT, TYK_TOKEN)
										.contentType(APPLICATION_JSON)
										.content(body)
					   )
					   .andDo(MockMvcResultHandlers.print())
					   .andExpect(status().is(expectedStatus))
					   .andReturn();
	}

	/**
	 * De-serializes MVC result body as a list of objects of an expected type
	 *
	 * @param mvcResult response
	 * @param tClass expected list item type
	 * @param <T> expected list item type
	 * @return de-serialized list of items
	 * @throws Exception if failure
	 */
	<T> List<T> getList(MvcResult mvcResult, Class<T> tClass) throws Exception {
		JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, tClass);
		return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
	}

	/**
	 * De-serializes MVC result body as a list of objects of an expected type
	 *
	 * @param mvcResult response
	 * @param keyClass key class object
	 * @param valueClass value class object
	 * @param <K> key type
	 * @param <V> value type
	 * @return de-serialized map
	 * @throws Exception if failure
	 */
	<K, V> Map<K, V> getMap(MvcResult mvcResult, Class<K> keyClass, Class<V> valueClass) throws Exception {
		JavaType type = objectMapper.getTypeFactory().constructParametricType(Map.class, keyClass, valueClass);
		return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
	}
}
