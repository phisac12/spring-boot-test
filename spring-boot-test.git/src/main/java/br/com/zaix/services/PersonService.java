package br.com.zaix.services;

import br.com.zaix.exception.ResourceNotFoundException;
import br.com.zaix.models.Person;
import br.com.zaix.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    PersonRepository personRepository;


    public List<Person> getAllPersons() {
        logger.info("Found all persons");
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        logger.info("Find Person by id: " + id);

        return personRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));
    }

    public Person createPerson(Person person) {
        logger.info("Created an person");
        return personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Update an person =>");
        
        Person entity = personRepository
        .findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        personRepository.save(entity);

        return entity;
    }

    public void deletePerson(Long id) {
        Person entity = personRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));

        personRepository.delete(entity);
    }

}
