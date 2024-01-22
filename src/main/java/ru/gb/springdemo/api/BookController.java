package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.service.BookService;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        try {

            Book book = bookService.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {

        try {

            Book book = bookService.getBookById(id);
            bookService.deleteBook(id);
            return new ResponseEntity<>(book, HttpStatus.NO_CONTENT);

        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody String title) {
        try {

            bookService.createBook(title);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
