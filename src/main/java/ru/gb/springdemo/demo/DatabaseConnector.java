package ru.gb.springdemo.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class DatabaseConnector {

  private final ApplicationEventPublisher eventPublisher;

  @Autowired
  public DatabaseConnector(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public String getData() {
    // connect to db and select
    return "data";
  }

  // init-method
  @SneakyThrows
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    log.info("подключаемся к бд...");
    Thread.sleep(Duration.ofSeconds(1));
    log.info("подключение к БД успешно");

    eventPublisher.publishEvent(new DatabaseConnectionSetupEvent(this));
  }

}
