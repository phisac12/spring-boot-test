package br.com.zaix.controllers;

import br.com.zaix.data.dto.PersonDTO;
import br.com.zaix.routes.Routes;
import br.com.zaix.services.PersonService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(Routes.BASE_API)
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @GetMapping(value = "/person", produces = { 
        MediaType.APPLICATION_JSON_VALUE,
         MediaType.APPLICATION_XML_VALUE, 
         MediaType.APPLICATION_YAML_VALUE 
        })
    public List<PersonDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/person/{id}", produces = { 
        MediaType.APPLICATION_JSON_VALUE,
         MediaType.APPLICATION_XML_VALUE, 
         MediaType.APPLICATION_YAML_VALUE 
        })
    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = personService.getPersonById(id);
        return person;
    }

    @PostMapping(
        value = "person", 
        produces = { 
            MediaType.APPLICATION_JSON_VALUE, 
            MediaType.APPLICATION_XML_VALUE, 
            MediaType.APPLICATION_YAML_VALUE 
        }
    )
    public PersonDTO createPerson(@RequestBody PersonDTO person) {
        return personService.createPerson(person);
    }

    @PutMapping(
        value = "person", 
        produces = { 
            MediaType.APPLICATION_JSON_VALUE, 
            MediaType.APPLICATION_XML_VALUE, 
            MediaType.APPLICATION_YAML_VALUE 
        },
        consumes = { 
            MediaType.APPLICATION_JSON_VALUE, 
            MediaType.APPLICATION_XML_VALUE, 
            MediaType.APPLICATION_YAML_VALUE 
        }
    )
    public PersonDTO updatePerson(@RequestBody PersonDTO person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping(value = "person/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
