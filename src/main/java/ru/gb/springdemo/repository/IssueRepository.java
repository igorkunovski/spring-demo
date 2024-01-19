package ru.gb.springdemo.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Repository
public class IssueRepository {

  private final List<Issue> closedIssues;
  private final List<Issue> openedIssues;

  public IssueRepository() {
    this.closedIssues = new ArrayList<>();
    this.openedIssues = new ArrayList<>();
  }

  private ArrayList<Issue> getClosedIssues(){
    return (ArrayList<Issue>) closedIssues;
  }

  public ArrayList<Issue> getOpenedIssues(){
    return (ArrayList<Issue>) openedIssues;
  }

  public ArrayList<Issue> getAllIssues(){
    ArrayList<Issue> result = new ArrayList<>();
    result.addAll(getOpenedIssues());
    result.addAll(getClosedIssues());
    return result;
  }

  public void addNewOpenedIssue(Issue issue) {
    openedIssues.add(issue);
  }

  public void closeIssue(Issue issue){
    openedIssues.stream()
            .filter(i -> Objects.equals(i.getId(), issue.getId()))
            .findFirst()
            .ifPresent(searched ->
                    {
                      searched.setReturnedDate(LocalDateTime.now());
                      closedIssues.add(searched);
                      openedIssues.remove(searched);
                    }
            );
  }

  public Issue getIssueById(Long id) {
    return getAllIssues().stream().filter(issue -> Objects.equals(issue.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public Stream<Issue> getUserIssues(long id){
    return getAllIssues().stream()
            .filter(issue -> Objects.equals(issue.getReaderId(), id))
            .sorted(Comparator.comparingLong(Issue::getId));
  }

}
