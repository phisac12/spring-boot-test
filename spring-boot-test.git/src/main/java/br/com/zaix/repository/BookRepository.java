package br.com.zaix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaix.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> { }
