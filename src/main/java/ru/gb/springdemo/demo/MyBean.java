package ru.gb.springdemo.demo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyBean {

//  @Value("${application.max-allowed-books:1}")
//  private int myProperty;

  public MyBean(MyService myService) {
    log.info("constructor");
  }

  @Autowired
  public void setMyService(MyService myService) {
    log.info("setter-injection");
    // this.myService = myService
  }

  @PostConstruct
  public void postConstruct() {
    log.info("postConstruct");
  }

  @EventListener(DatabaseConnectionSetupEvent.class)
  public void onDatabaseConnectionSetup() {
    log.info("onDatabaseConnectionSetup");

  }

  @PreDestroy
  public void preDestroy() {
    log.info("preDestroy");
  }

}
