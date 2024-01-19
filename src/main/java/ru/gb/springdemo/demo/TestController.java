package ru.gb.springdemo.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {

  @GetMapping("/resource")
  public String testResource(@RequestParam String resourceName) {
    System.out.println(resourceName);
    return "ok";
  }

}
