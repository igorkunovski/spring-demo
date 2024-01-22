package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springdemo.model.*;
import ru.gb.springdemo.service.*;

import java.util.List;

@Controller
@RequestMapping("/ui")
public class UiController {

    BookService bookService;
    ReaderService readerService;
    IssueService issueService;

    @Autowired
    public UiController(BookService bookService, ReaderService readerService, IssueService issueService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.issueService = issueService;
    }

    @GetMapping("/booklist")
    public String booklist(Model model){

        List<Book> books = bookService.getAllBooks();

        model.addAttribute("books", books);
        return "booklist";
    }

    @GetMapping("/readerlist")
    public String readerlist(Model model){

        List<Reader> readers = readerService.getAllReaders();

        model.addAttribute("readers", readers);
        return "readerlist";
    }

    @GetMapping("/issuetable")
    public String issuetable(Model model){

        List<Issue> issues = issueService.getAllIssues();

        model.addAttribute("issues", issues);
        return "issuetable";
    }

    @GetMapping("/reader/{id}")
    public String readerbooks(@PathVariable Long id, Model model){
        List<Book> readerBooks = readerService.getReaderById(id).getBookList();
        String readerName = readerService.getReaderById(id).getName();

        model.addAttribute("readerName", readerName);
        model.addAttribute("books", readerBooks);
        return "readerbooklist";
    }
}
