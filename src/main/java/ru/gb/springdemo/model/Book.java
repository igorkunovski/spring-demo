package ru.gb.springdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {

  private static Long sequence = 1L;
  private final Long id;
  private final String title;

  public Book(String title) {
    this.id = sequence++;
    this.title = title;
  }

}
