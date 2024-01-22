package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;
import ru.gb.springdemo.model.*;
import ru.gb.springdemo.service.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssueController {

  private final IssueService issueService;
  private final ReaderService readerService;
  private final BookService bookService;

  @Autowired
  public IssueController(IssueService service, ReaderService readerService, BookService bookService) {
    this.issueService = service;
    this.readerService = readerService;
    this.bookService = bookService;
  }

  /**
   *
   * @return возврат списка только открытых выдач
   */
  @GetMapping("/opened")
  public ResponseEntity<List<Issue>> getOpenedIssues() {
    return new ResponseEntity<>(issueService.getOpenedIssues(), HttpStatus.OK);
  }

  /**
   *
   * @return возврат списка всех выдач (и открытых и закрытых)
   */

  @GetMapping("/all")
  public ResponseEntity<List<Issue>> getAllIssues() {
    return new ResponseEntity<>(issueService.getAllIssues(), HttpStatus.OK);
  }

  /**
   *
   * @param request - тело запроса на добавление
   * @return - статус запроса и тело запроса
   */
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;

    try {
      issue = issueService.issue(request);

      // добавление книги в список взятых книг читателя
      Reader reader = readerService.getReaderById(request.getReaderId());
      Book book = bookService.getBookById(request.getBookId());
      readerService.addBook(reader, book);
      issueService.addNewOpenedIssue(issue);

    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
    catch (NotAcceptableStatusException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.status(HttpStatus.OK).body(issue);
  }

  /**
   * Метод добавляет текущее время в поле returnedDate выдачи и
   * удаляет книгу из списка взятых книг читателем.
   *
   * @param id - id выдачи для возврата.
   * @return - статус запроса и тело запроса
   */
  @PutMapping("/{id}")
  public ResponseEntity<Issue> returnBook(@PathVariable long id) {

    Issue issue = issueService.getIssueById(id);
    Book book = bookService.getBookById(issue.getBookId());

    // книга удаляется из списка взятых книг читателя
    readerService.getReaderById(issue.getReaderId()).removeBook(book);

    //добавляется время возврата книги, выдача удаляется из списка активных и добавляется в список закрытых
    issueService.closeIssue(issue);

    return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueById(id));
  }

  /**
   *
   * @param id - идентификатор выдачи
   * @return - тело выдачи и статус запроса
   */

  @GetMapping("/{id}")
  public ResponseEntity<Issue> getIssue(@PathVariable long id) {
    Issue issue = issueService.getIssueById(id);
    if (issue !=null) {
      return new ResponseEntity<>(issue, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

}
