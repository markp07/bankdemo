package xyz.markpost.bankdemo.config;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Filter for the REST API.
 */
@Component
public class RestFilter implements Filter {

  private static final String ALLOWED_ORIGIN = "*";
  private static final String ALLOWED_METHODS = "POST, GET, DELETE, PATCH";
  private static final String MAX_AGE = "36000";
  private static final String ALLOWED_HEADERS = "origin, content-type, accept";

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
    response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
    response.setHeader("Access-Control-Max-Age", MAX_AGE);
    response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
    chain.doFilter(req, res);
  }

}