package br.com.zaix.unit_tests.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.zaix.data.dto.BookDTO;
import br.com.zaix.exception.RequiredObjectIsNullException;
import br.com.zaix.models.Book;
import br.com.zaix.repository.BookRepository;
import br.com.zaix.services.BookService;
import br.com.zaix.unit_tests.mapper.mocks.MockBook;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    Calendar calendar = Calendar.getInstance();

    @BeforeEach
    void setUp() {

        input = new MockBook();
        MockitoAnnotations.openMocks(this);

        calendar.set(2025, Calendar.JUNE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Test
    void getBookById() {
        Book book = input.mockEntity(1);
        book.setId(1L);   
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        var result = service.getBookById(1L);    
        result.setLaunchDate(calendar.getTime());    

        assertNotNull(book);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllBooks") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("GET")));

         assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updateBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("deleteBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(calendar.getTime(), result.getLaunchDate());
        assertEquals(100.0 + 1, result.getPrice());
    }

    @Test
    void createBook() {
        Book book = input.mockEntity(1);
        Book persisted = book;

        BookDTO dto = input.mockDTO(1);

        when(repository.save(book)).thenReturn(persisted);

        var result = service.createBook(dto);
        result.setLaunchDate(calendar.getTime());
             

        assertNotNull(book);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllBooks") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("GET")));

         assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updateBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("deleteBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(calendar.getTime(), result.getLaunchDate());
        assertEquals(100.0 + 1, result.getPrice());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {

            service.createBook(null);

        });

        String expectedMessage = "It's not allowed to persist a null object!";
        String receivedMessage = exception.getMessage();
        assertTrue(receivedMessage.contains(expectedMessage));
    }

    @Test
    void updateBook() {
        Book book = input.mockEntity(1);
        Book persisted = book;

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.updateBook(dto);
        result.setLaunchDate(calendar.getTime());
             

        assertNotNull(book);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllBooks") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("GET")));

         assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updateBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("deleteBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(calendar.getTime(), result.getLaunchDate());
        assertEquals(100.0 + 1, result.getPrice());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {

            service.updateBook(null);

        });

        String expectedMessage = "It's not allowed to persist a null object!";
        String receivedMessage = exception.getMessage();
        assertTrue(receivedMessage.contains(expectedMessage));
    }
    
    @Test
    void deleteBook() {
        Book book = input.mockEntity(1);
        book.setId(1L);   
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        service.deleteBook(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void getAllBooks() {
        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BookDTO> books = service.getAllBooks();

        // Calendar Set fixed Date

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.getFirst();
        bookOne.setLaunchDate(calendar.getTime());

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        assertNotNull(bookOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("GET")));

        assertNotNull(bookOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllBooks") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("GET")));

         assertNotNull(bookOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("POST")));

        assertNotNull(bookOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updateBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("PUT")));

        assertNotNull(bookOne.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("deleteBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("DELETE")));

        assertEquals("Author Test0", bookOne.getAuthor());
        assertEquals("Title Test0", bookOne.getTitle());
        assertEquals(calendar.getTime(), bookOne.getLaunchDate());
        assertEquals(100.0 + 0, bookOne.getPrice());

        //# < - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - >

        var bookTwo = books.get(2);
        bookTwo.setLaunchDate(calendar.getTime());
        assertNotNull(bookTwo);
        assertNotNull(bookTwo.getId());
        assertNotNull(bookTwo.getLinks());

        assertNotNull(bookTwo.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("GET")));

        assertNotNull(bookTwo.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllBooks") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("GET")));

         assertNotNull(bookTwo.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("POST")));

        assertNotNull(bookTwo.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updateBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("PUT")));

        assertNotNull(bookTwo.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("deleteBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("DELETE")));

        assertEquals("Author Test2", bookTwo.getAuthor());
        assertEquals("Title Test2", bookTwo.getTitle());
        assertEquals(calendar.getTime(), bookTwo.getLaunchDate());
        assertEquals(100.0 + 2, bookTwo.getPrice());


        var bookFive = books.get(5);
        bookFive.setLaunchDate(calendar.getTime());
        assertNotNull(bookTwo);
        assertNotNull(bookFive.getId());
        assertNotNull(bookFive.getLinks());

        assertNotNull(bookFive.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("self") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("GET")));

        assertNotNull(bookFive.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("getAllBooks") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("GET")));

         assertNotNull(bookFive.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("createBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book") 
        && link.getType().equals("POST")));

        assertNotNull(bookFive.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("updateBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("PUT")));

        assertNotNull(bookFive.getLinks().stream().anyMatch(link -> link.getRel()
        .value().equals("deleteBook") 
        && link.getHref().endsWith("api/spring-boot-test/v1/book/1") 
        && link.getType().equals("DELETE")));

        assertEquals("Author Test5", bookFive.getAuthor());
        assertEquals("Title Test5", bookFive.getTitle());
        assertEquals(calendar.getTime(), bookFive.getLaunchDate());
        assertEquals(100.0 + 5, bookFive.getPrice());

    }
}