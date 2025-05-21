package br.com.zaix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaix.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    

}
