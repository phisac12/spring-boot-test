package br.com.zaix.unit_tests.mapper.mocks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.zaix.data.dto.BookDTO;
import java.util.Date;
import br.com.zaix.models.Book;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setLaunchDate(calendar.getTime());
        book.setId(number.longValue());
        book.setPrice(100.0 + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 1);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("Author Test" + number);
        bookDTO.setTitle("Title Test" + number);
        bookDTO.setLaunchDate(calendar.getTime());
        bookDTO.setId(number.longValue());
        bookDTO.setPrice(100.0 + number);
        return bookDTO;
    }


}
