package br.com.zaix.services;

import br.com.zaix.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(this.getClass().getName());


    public List<Person> getAllPersons() {
        final List<Person> persons = new ArrayList<Person>();
        for(int i = 0; i< 10; i++) {
            persons.add(createMockPerson(i));
        }
        logger.info("Found all persons");
        return persons;
    }

    public Person findById(String id) {
        logger.info("Find Person by id: " + id);

        return createMockPerson(0);
    }

    public Person createPerson(Person person) {
        logger.info("Created an person");
        return person;
    }

    public Person updatePerson(Person person) {
        logger.info("Update an person =>");
        return person;
    }

    public void deletePerson(String id) {
        logger.info("Delete an person id " + id);
    }

    

    public Person createMockPerson(int i) {
        return new Person(
                counter.incrementAndGet(),
                "Isaac => " + i,
                "Souza => " + i,
                "Rua teste => " + i,
                "M => " + i
        );
    }

}
