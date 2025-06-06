package br.com.zaix.integration_tests.swagger;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;

import br.com.zaix.config.TestConfigs;
import br.com.zaix.integration_tests.tests_containers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void shouldDisplaySwaggerUiPage() {
		var content = given()
		.basePath("swagger-ui/index.html")
			.port(TestConfigs.SERVER_PORT)
		.when()
			.get()
		.then()
			.statusCode(200)
		.extract()
			.body()
				.asString();

		assertTrue(content.contains("Swagger UI"));
		

	}

}
