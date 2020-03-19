package com.github.churakovIA.model;

import java.util.Map;
import lombok.Data;

@Data
public class RequestInfo {

  private String ip;
  private Map<String, String> headers;
}
