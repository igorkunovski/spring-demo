package ru.gb.springdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
public class Issue {

  public static long sequence = 1L;
  private final long id;
  private final long bookId;
  private final long readerId;

  private final LocalDateTime issueDate;
  private LocalDateTime returnedDate;

  public Issue(long bookId, long readerId) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issueDate = LocalDateTime.now().withNano(0);
    this.returnedDate = null;
  }

}
