package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book getBookById(Long id) {
        if (bookRepository.getBookById(id) == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\"");
        }
        return bookRepository.getBookById(id);
    }

    public void deleteBook(Long id) {
        if (getBookById(id)==null){
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\"");
        }else {
            bookRepository.deleteBookById(id);
        }
    }

    public ArrayList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public void createBook(String title) {
        if (bookRepository.findBookByTitle(title).size() < 1) {
            bookRepository.createBook(title);
        } else {
            throw new NoSuchElementException("Книга с таким название уже введена \"" + title + "\"");
        }
    }
}
