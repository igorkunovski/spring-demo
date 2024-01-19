package ru.gb.springdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Reader {

  private List<Book> bookList;
  public static long sequence = 1L;

  private final long id;
  private final String name;

  public Reader(String name) {
    this(sequence++, name);
    this.bookList = new ArrayList<>();
  }

  public void addBook(Book book){
    bookList.add(book);
  }

  public void removeBook(Book book){
    bookList.remove(book);
  }

}
