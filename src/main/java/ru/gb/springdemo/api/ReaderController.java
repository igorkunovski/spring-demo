package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.*;
import ru.gb.springdemo.service.IssueService;
import ru.gb.springdemo.service.ReaderService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private final ReaderService readerService;
    private final IssueService issueService;

    public ReaderController(ReaderService readerService, IssueService issueService) {
        this.readerService = readerService;
        this.issueService = issueService;
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Reader>> getAllReaders() {
        return new ResponseEntity<>(readerService.getAllReaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReader(@PathVariable Long id) {
        Reader reader= readerService.getReaderById(id);
        if (reader !=null) {
            return new ResponseEntity<>(readerService.getReaderById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getReaderIssueList(@PathVariable Long id) {
        Reader reader= readerService.getReaderById(id);
        if (reader !=null) {
            return new ResponseEntity<>(issueService.getUserIssues(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteBook(@PathVariable Long id) {
        Reader reader = readerService.getReaderById(id);
        if (reader ==null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else{
            readerService.deleteReaderById(id);
            return new ResponseEntity<>(reader, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Reader> createReader(@RequestBody String name) {
        readerService.createReader(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
