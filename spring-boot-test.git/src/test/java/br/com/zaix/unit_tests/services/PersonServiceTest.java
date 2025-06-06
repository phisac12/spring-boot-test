package br.com.zaix.unit_tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.zaix.data.dto.PersonDTO;
import br.com.zaix.exception.RequiredObjectIsNullException;
import br.com.zaix.models.Person;
import br.com.zaix.repository.PersonRepository;
import br.com.zaix.services.PersonService;
import br.com.zaix.unit_tests.mapper.mocks.MockPerson;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {

        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPersonById() {
        Person person = input.mockEntity(1);
        person.setId(1L);   
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.getPersonById(1L);   
             

        assertNotNull(person);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllPersons") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("GET")));

         assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createPerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updatePerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("delete") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("DELETE")));


        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void createPerson() {
        Person person = input.mockEntity(1);
        Person persisted = person;

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);

        var result = service.createPerson(dto);    
             

        assertNotNull(person);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllPersons") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("GET")));

         assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createPerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updatePerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("delete") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("DELETE")));


        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {

            service.createPerson(null);

        });

        String expectedMessage = "It's not allowed to persist a null object!";
        String receivedMessage = exception.getMessage();
        assertTrue(receivedMessage.contains(expectedMessage));
    }

    @Test
    void updatePerson() {
        Person person = input.mockEntity(1);
        Person persisted = person;

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = service.updatePerson(dto);    
             

        assertNotNull(person);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllPersons") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("GET")));

         assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createPerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updatePerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("delete") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("DELETE")));


        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {

            service.updatePerson(null);

        });

        String expectedMessage = "It's not allowed to persist a null object!";
        String receivedMessage = exception.getMessage();
        assertTrue(receivedMessage.contains(expectedMessage));
    }

    @Test
    void deletePerson() {
        Person person = input.mockEntity(1);
        person.setId(1L);   
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.deletePerson(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

     @Test
    void getAllPersons() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<PersonDTO> people = service.getAllPersons();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.getFirst();

        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());

        assertNotNull(personOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("GET")));

        assertNotNull(personOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllPersons") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("GET")));

         assertNotNull(personOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createPerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("POST")));

        assertNotNull(personOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updatePerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("PUT")));

        assertNotNull(personOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("delete") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("DELETE")));

        var personFour = people.get(4);

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        assertNotNull(personOne);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getLinks());

        assertNotNull(personFour.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("GET")));

        assertNotNull(personFour.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllPersons") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("GET")));

         assertNotNull(personFour.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createPerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("POST")));

        assertNotNull(personFour.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updatePerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("PUT")));

        assertNotNull(personFour.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("delete") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("DELETE")));


        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Female", personFour.getGender());


        var personSeven = people.get(7);

        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());

        assertNotNull(personOne);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getLinks());

        assertNotNull(personSeven.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("GET")));

        assertNotNull(personSeven.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllPersons") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("GET")));

         assertNotNull(personSeven.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createPerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person") 
        && link.getType().equals("POST")));

        assertNotNull(personSeven.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updatePerson") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("PUT")));

        assertNotNull(personSeven.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("delete") 
        && link.getHref().endsWith("api/spring-boot-test/v1/person/1") 
        && link.getType().equals("DELETE")));


        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());

    }
}