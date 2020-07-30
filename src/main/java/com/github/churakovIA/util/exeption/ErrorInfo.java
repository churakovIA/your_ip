package com.github.churakovIA.util.exeption;

import java.util.Arrays;

public class ErrorInfo {

  private final String uri;
  private final Integer statusCode;
  private final String servletName;
  private final String[] details;

  public ErrorInfo(CharSequence uri, Integer statusCode, String servletName, String... details) {
    this.uri = uri.toString();
    this.statusCode = statusCode;
    this.servletName = servletName;
    this.details = details;
  }

  public String getUri() {
    return uri;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public String getServletName() {
    return servletName;
  }

  public String[] getDetails() {
    return details;
  }

  @Override
  public String toString() {
    return "ErrorInfo{" +
        "uri='" + uri + '\'' +
        ", statusCode=" + statusCode +
        ", servletName='" + servletName + '\'' +
        ", details=" + Arrays.toString(details) +
        '}';
  }
}