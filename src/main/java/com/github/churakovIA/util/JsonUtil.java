package com.github.churakovIA.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Writer;

public class JsonUtil {

  private static final ObjectMapper MAPPER;

  static {
    MAPPER = new ObjectMapper();
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static <T> void writeValue(Writer out, T obj) {
    try {
      MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, obj);
    } catch (IOException e) {
      throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
    }
  }
}
