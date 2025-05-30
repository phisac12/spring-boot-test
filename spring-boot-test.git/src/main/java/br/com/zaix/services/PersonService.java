package br.com.zaix.services;

import br.com.zaix.controllers.PersonController;
import br.com.zaix.data.dto.PersonDTO;
import br.com.zaix.exception.RequiredObjectIsNullException;
import br.com.zaix.exception.ResourceNotFoundException;
import static br.com.zaix.mapper.ObjectMapper.parseListObject;
import static br.com.zaix.mapper.ObjectMapper.parseObject;
import br.com.zaix.models.Person;
import br.com.zaix.repository.PersonRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    PersonRepository personRepository;


    public List<PersonDTO> getAllPersons() {
        logger.info("Found all persons");
        var persons = parseListObject(personRepository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDTO getPersonById(Long id) {
        logger.info("Find Person by id: " + id);

        var entity = personRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }


    public PersonDTO createPerson(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        var entity = parseObject(person, Person.class);
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO updatePerson(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        Person entity = personRepository
        .findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void deletePerson(Long id) {
        Person entity = personRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));

        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).getAllPersons()).withRel("getAllPersons").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).createPerson(dto)).withRel("createPerson").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).updatePerson(dto)).withRel("updatePerson").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).deletePerson(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
