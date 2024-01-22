package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {

  private final List<Book> books;
  public BookRepository() {
    this.books = new ArrayList<>();
  }

  @PostConstruct
  public void generateBooks() {
    for (int i = 100; i < 121; i++){
      books.add(new Book("Book #" + i));
    }
  }

  public void createBook(String title){
    books.add(new Book(title));
    System.out.println("Book added successfully");
  }

  public ArrayList<Book> getAllBooks(){
    return (ArrayList<Book>) books;
  }

  public Book getBookById(Long id) {
    return books.stream().filter(book -> Objects.equals(book.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public void deleteBookById(Long id) {
    books.removeIf(book -> Objects.equals(book.getId(), id));
  }

  public List<Book> findBookByTitle(String title){
    return getAllBooks().stream()
            .filter(book -> book.getTitle().equals(title))
            .toList();

  }

}
