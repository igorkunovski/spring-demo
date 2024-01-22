package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {

  private final List<Reader> readers;

  public ReaderRepository() {
    this.readers = new ArrayList<>();
  }

  @PostConstruct
  public void generateReaders() {

    for (int i = 1; i < 15; i++) {
      readers.add(new Reader("Reader #" + i));
    }
  }

  public ArrayList<Reader> getAllReaders(){
    return (ArrayList<Reader>) readers;
  }

  public Reader getReaderById(long id) {
    return readers.stream().filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public void deleteReaderById(Long id) {
    readers.removeIf(reader -> Objects.equals(reader.getId(), id));
  }

  public void createReader(String name){
    readers.add(new Reader(name));
    System.out.println("Reader added successfully");
  }

  public List<Reader> findReadersByName(String name){
    return getAllReaders().stream()
            .filter(reader -> reader.getName().equals(name))
            .toList();
  }
}
