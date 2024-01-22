package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public Reader request(Long id) {
        if (readerRepository.getReaderById(id) == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
        }
        return readerRepository.getReaderById(id);
    }

    public void deleteReader(Long id) {
        if (request(id)==null){
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
        }else {
            readerRepository.deleteReaderById(id);
        }
    }

    public ArrayList<Reader> getAllReaders() {
        return readerRepository.getAllReaders();
    }

    public void createReader(String name) {
        if (readerRepository.findReadersByName(name).size() < 1) {
            readerRepository.createReader(name);
        } else {
            throw new NoSuchElementException("Такой читатель уже введен \"" + name + "\"");
        }
    }

    public Reader getReaderById(Long id) {
        return readerRepository.getReaderById(id);
    }

    public void addBook(Reader reader, Book book) {
        readerRepository.getReaderById(reader.getId()).addBook(book);
    }

    public void deleteReaderById(Long id) {
        readerRepository.deleteReaderById(id);
    }
}
