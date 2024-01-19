package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;
import ru.gb.springdemo.model.*;
import ru.gb.springdemo.repository.*;
import ru.gb.springdemo.service.IssueService;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssueController {


  private final IssueService issueService;
  private final IssueRepository issueRepository;
  private final ReaderRepository readerRepository;
  private final BookRepository bookRepository;

  @Autowired
  public IssueController(IssueService service, IssueRepository issueRepository, ReaderRepository readerRepository,
                         BookRepository bookRepository) {
    this.issueService = service;
    this.issueRepository = issueRepository;
    this.readerRepository = readerRepository;
    this.bookRepository = bookRepository;
  }

  /**
   *
   * @return возврат списка только открытых выдач
   */
  @GetMapping("/opened")
  public ResponseEntity<ArrayList<Issue>> getOpenedIssues() {
    return new ResponseEntity<>(issueRepository.getOpenedIssues(), HttpStatus.OK);
  }

  /**
   *
   * @return возврат списка всех выдач (и открытых и закрытых)
   */

  @GetMapping("/all")
  public ResponseEntity<ArrayList<Issue>> getAllIssues() {
    return new ResponseEntity<>(issueRepository.getAllIssues(), HttpStatus.OK);
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
      Reader reader = readerRepository.getReaderById(request.getReaderId());
      Book book = bookRepository.getBookById(request.getBookId());
      reader.addBook(book);
      issueRepository.addNewOpenedIssue(issue);

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

    Issue issue = issueRepository.getIssueById(id);
    Book book = bookRepository.getBookById(issue.getBookId());

    // книга удаляется из списка взятых книг читателя
    readerRepository.getReaderById(issue.getReaderId()).removeBook(book);

    //добавляется время возврата книги, выдача удаляется из списка активных и добавляется в список закрытых
    issueRepository.closeIssue(issue);

    return ResponseEntity.status(HttpStatus.OK).body(issueRepository.getIssueById(id));
  }


  /**
   *
   * @param id - идентификатор выдачи
   * @return - тело выдачи и статус запроса
   */

  @GetMapping("/{id}")
  public ResponseEntity<Issue> getIssue(@PathVariable long id) {
    Issue issue = issueRepository.getIssueById(id);
    if (issue !=null) {
      return new ResponseEntity<>(issue, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

}
