package br.com.zaix.services;

import static br.com.zaix.mapper.ObjectMapper.parseListObject;
import static br.com.zaix.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zaix.controllers.BookController;
import br.com.zaix.data.dto.BookDTO;
import br.com.zaix.exception.RequiredObjectIsNullException;
import br.com.zaix.exception.ResourceNotFoundException;
import br.com.zaix.models.Book;
import br.com.zaix.repository.BookRepository;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    BookRepository bookRepository;

    public List<BookDTO> getAllBooks() {
        logger.info("Found all books");
        var books = parseListObject(bookRepository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;

    }

    public BookDTO getBookById(Long id) {
        logger.info("Find An Book by id: " + id);

        var entity = bookRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));
        var dto = parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO createBook(BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        var entity = parseObject(book, Book.class);
        var dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

     public BookDTO updateBook(BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        Book entity = bookRepository
        .findById(book.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setLaunchDate(book.getLaunchDate());
        var dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void deleteBook(Long id) {
        Book entity = bookRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));

        bookRepository.delete(entity);
    }

    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).getAllBooks()).withRel("getAllBooks").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).createBook(dto)).withRel("createBook").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).updateBook(dto)).withRel("updateBook").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).deleteBook(dto.getId())).withRel("deleteBook").withType("DELETE"));
    }
}
