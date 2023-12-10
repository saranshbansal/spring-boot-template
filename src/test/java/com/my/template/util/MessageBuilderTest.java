package com.my.template.util;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Unit test for {@link MessageBuilder}
 *
 * Created by Saransh Bansal on 29/06/2018.
 */
public class MessageBuilderTest {

	@Test
	public void testBuildMessage() {
		String expected = "Test message arg1=one arg2=2";
		String template = "{} message arg1={} arg2={}";
		String result = MessageBuilder.buildMessage(template, "Test", "one", 2);
		assertEquals(expected, result);

		result = MessageBuilder.buildMessage(template, "Test", "one", 2, true);
		assertEquals(expected, result);

		result = MessageBuilder.buildMessage(template, "Test");
		assertNotEquals(expected, result);
	}

	@Test
	public void testBuildMessageWithNamedParameters() {
		String expected = "Test message arg1=John arg2=25";
		String template = "{head} message arg1={name} arg2={age}";
		Map<String, Object> params = new HashMap<>(2);
		params.put("head", "Test");
		params.put("name", "John");
		params.put("age", 25);
		String result = MessageBuilder.buildMessageWithNamedParams(template, params);
		assertEquals(expected, result);

		params.remove("age");
		expected = "Test message arg1=John arg2={age}";
		result = MessageBuilder.buildMessageWithNamedParams(template, params);
		assertEquals(expected, result);
	}
}