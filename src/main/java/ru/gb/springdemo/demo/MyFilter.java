package ru.gb.springdemo.demo;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MyFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (request instanceof HttpServletRequest httpRequest) {
//      HttpServletRequest httpRequest = (HttpServletRequest) request;

      String requestURI = httpRequest.getRequestURI();
      log.info("Income request: {}", requestURI);

      if (requestURI.startsWith("/admin")) {
        ((HttpServletResponse) response).sendError(403);
      } else {
        chain.doFilter(request, response);
      }
    } else {
      chain.doFilter(request, response);
    }
  }

}
