package com.visiblethread.demo.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigurationTest {

	@InjectMocks
	private SwaggerConfiguration underTest;

	@Mock
	private RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;

	@Test
	void testAvailApi() throws Exception {
		// given
		// when
		Docket actual = underTest.userApi();
		// then
		assertNotNull(actual);
	}

	@Test
	void testUiConfig() throws Exception {
		// given
		// when
		UiConfiguration actual = underTest.uiConfig();
		// then
		assertNotNull(actual);
	}

}
