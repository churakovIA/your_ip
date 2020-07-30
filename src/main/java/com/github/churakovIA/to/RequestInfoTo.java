package com.github.churakovIA.to;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestInfoTo {

  private Integer id;
  private String date;
  private String protocol;
  private String method;
  private String fullURL;
  private String locale;
  private String ip;
  private Map<String, String> headers;
  private String body;

}
