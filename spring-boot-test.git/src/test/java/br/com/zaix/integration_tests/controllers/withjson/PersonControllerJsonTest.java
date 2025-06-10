package br.com.zaix.integration_tests.controllers.withjson;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import static io.restassured.RestAssured.given;

import br.com.zaix.config.TestConfigs;
import br.com.zaix.data.dto.PersonDTO;
import br.com.zaix.integration_tests.tests_containers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO personDTO;

    @BeforeAll
    static void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDTO = new PersonDTO();
    }

    
    @Order(1)
    @Test
    void createPersonTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
        .setBasePath("/api/spring-boot-test/v1/person")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

        var content = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(personDTO)
		.when()
			.post()
		.then()
			.statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
		.extract()
			.body()
				.asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Torvald", createdPerson.getLastName());
        assertEquals( "Hellsink - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

     @Order(2)
    @Test
    void updatePersonTest() throws JsonProcessingException {
        personDTO.setLastName("Benedict Torvald");

        var content = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(personDTO)
		.when()
			.put()
		.then()
			.statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
		.extract()
			.body()
				.asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvald", createdPerson.getLastName());
        assertEquals( "Hellsink - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_HOST)
        .setBasePath("/api/spring-boot-test/v1/person")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

        var content = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("id", personDTO.getId())
		.when()
			.get("{id}")
		.then()
			.statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
		.extract()
			.body()
				.asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvald", createdPerson.getLastName());
        assertEquals( "Hellsink - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(4)
    void disablePersonTest() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_HOST)
        .setBasePath("/api/spring-boot-test/v1/person")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

        var content = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("id", personDTO.getId())
		.when()
			.patch("{id}")
		.then()
			.statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
		.extract()
			.body()
				.asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvald", createdPerson.getLastName());
        assertEquals( "Hellsink - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    void deletePersonTest() {
        given(specification)
            .pathParam("id", personDTO.getId())
		.when()
			.delete("{id}")
		.then()
			.statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
        .accept(MediaType.APPLICATION_JSON_VALUE)
		.when()
		.get()
		.then()
		.statusCode(200)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
		.extract()
			.body()
				.asString();


        
        List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});

        PersonDTO personOne = people.get(1);
        personDTO = personOne;

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Cleber", personOne.getFirstName());
        assertEquals("de Souza", personOne.getLastName());
        assertEquals( "Rua teste 120", personOne.getAddress());
        assertEquals("M", personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personThree = people.get(2);
        personDTO = personThree;

        assertNotNull(personThree.getId());
        assertTrue(personThree.getId() > 0);

        assertEquals("Vashew editado", personThree.getFirstName());
        assertEquals("Anderson", personThree.getLastName());
        assertEquals( "Rua luis gomes", personThree.getAddress());
        assertEquals("M", personThree.getGender());
        assertTrue(personThree.getEnabled());
    }

    private void mockPerson() {
        personDTO.setFirstName("Linus");
        personDTO.setLastName("Torvald");
        personDTO.setAddress("Hellsink - Finland");
        personDTO.setGender("Male");
        personDTO.setEnabled(true);
    }
}