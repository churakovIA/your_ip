package com.github.churakovIA.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

  private String encoding;

  @Override
  public void init(FilterConfig filterConfig) {
    encoding = filterConfig.getInitParameter("encoding");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    response.setCharacterEncoding(
        request.getCharacterEncoding() == null ? encoding : request.getCharacterEncoding());
    chain.doFilter(request, response);
  }
}
