package com.my.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class
 * Use it as a reference to Main class creating run configuration in IDEA
 * DON'T FORGET TO rename the class to correspond project name
 *
 * Created by Sergey Dukhnich on 29/07/2019.
 */
@SpringBootApplication
public class TemplateSpringBootTomcatApplication {

	/**
	 * Application entry point
	 *
	 * @param args external parameters if any to be passed to application
	 */
	public static void main(String[] args) {
		SpringApplication.run(TemplateSpringBootTomcatApplication.class, args);
	}
}
