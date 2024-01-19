package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;
import ru.gb.springdemo.model.*;
import ru.gb.springdemo.repository.*;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssueService {

  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;

  @Value("${application.max-allowed-books:1}")
  private int maxAllowedBook;

  public Issue issue(IssueRequest request) {
    if (bookRepository.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }

    // проверить, что у читателя лимит не превышает в 3 книги)

    if (readerRepository.getReaderById(request.getReaderId()).getBookList().size() >= maxAllowedBook) {
      throw new NotAcceptableStatusException("Превышен допустимы лимит книг");
    }

    return new Issue(request.getBookId(), request.getReaderId());
  }

}
