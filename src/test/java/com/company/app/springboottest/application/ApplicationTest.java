package com.company.app.springboottest.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

class ApplicationTest extends ApplicationSpringBootTestContext {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void context_must_be_test() {
		Assertions.assertNotNull(applicationContext);
	}
}