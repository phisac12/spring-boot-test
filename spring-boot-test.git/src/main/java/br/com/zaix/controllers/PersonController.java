package br.com.zaix.controllers;

import br.com.zaix.models.Person;
import br.com.zaix.services.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class PersonController {

    @Autowired
    private PersonService personService;


    
    @GetMapping(value = "/persons", produces = "application/json")
    public List<Person> findById() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/person/{id}", produces = "application/json")
    public Person findById(@PathVariable("id") Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping(
        value = "/person/create", 
        produces = "application/json"
    )
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping(
        value = "/person/update", 
        produces = "application/json",
        consumes = "application/json"
    )
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping(value = "/person/delete/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id, Person person) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
