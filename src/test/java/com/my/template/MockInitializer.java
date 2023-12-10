package com.my.template;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Abstract class to initialize all fields annotated with Mockito annotations
 *
 * Created by Saransh Bansal on 09/02/2021.
 */
public abstract class MockInitializer {
	private AutoCloseable mockContext;

	@BeforeMethod
	public void setUp() {
		mockContext = openMocks(this);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		mockContext.close();
	}
}
