package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.*;
import ru.gb.springdemo.repository.*;

import java.util.ArrayList;
import java.util.stream.Stream;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public ReaderController(ReaderRepository readerRepository, IssueRepository issueRepository) {
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }


    @GetMapping("/all")
    public ResponseEntity<ArrayList<Reader>> getAllReaders() {
        return new ResponseEntity<>(readerRepository.getAllReaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReader(@PathVariable Long id) {
        Reader reader= readerRepository.getReaderById(id);
        if (reader !=null) {
            return new ResponseEntity<>(readerRepository.getReaderById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<Stream<Issue>> getReaderIssueList(@PathVariable Long id) {
        Reader reader= readerRepository.getReaderById(id);
        if (reader !=null) {
            return new ResponseEntity<>(issueRepository.getUserIssues(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteBook(@PathVariable Long id) {
        Reader reader = readerRepository.getReaderById(id);
        if (reader ==null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else{
            readerRepository.deleteReaderById(id);
            return new ResponseEntity<>(reader, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Reader> createReader(@RequestBody String name) {
        readerRepository.createReader(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
