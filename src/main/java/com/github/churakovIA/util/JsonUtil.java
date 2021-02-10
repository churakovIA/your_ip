package com.github.churakovIA.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
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

  public static <T> String writeValue(T obj) {
    try {
      return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
    }
  }

  public static <T> T readValue(InputStream is, Class<T> clazz) {
    try {
      return MAPPER.readValue(is, clazz);
    } catch (IOException e) {
      throw new IllegalStateException("Invalid read JSON", e);
    }
  }
}
