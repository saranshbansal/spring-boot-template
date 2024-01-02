package com.my.template.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.my.template.TemplateEndpoints.AI_CONTROLLER_PATH;
import static com.my.template.TemplateEndpoints.AI_PROMPT;

/**
 * Controller for AI endpoints
 *
 * Created by Saransh Bansal on 02/01/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = AI_CONTROLLER_PATH)
public class AIController {

	private final ChatClient chatClient;

	/**
	 * Generate response from open ai
	 *
	 * @param message prompt
	 * @return generated response
	 */
	@GetMapping(AI_PROMPT)
	public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return Map.of("generation", chatClient.generate(message));
	}
}
