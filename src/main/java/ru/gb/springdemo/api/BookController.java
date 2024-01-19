package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Book>> getAllBooks() {
        return new ResponseEntity<>(bookRepository.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookRepository.getBookById(id);
        if (book !=null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.getBookById(id);
        if (book ==null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else{
            bookRepository.deleteBookById(id);
            return new ResponseEntity<>(book, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody String title) {
        bookRepository.createBook(title);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
