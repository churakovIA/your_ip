package com.github.churakovIA.model;

import java.util.Map;
import lombok.Data;

@Data
public class RequestInfo {

  private String protocol;
  private String method;
  private String fullURL;
  private String locale;
  private String ip;
  private Map<String, String> headers;
  private String body;
}
