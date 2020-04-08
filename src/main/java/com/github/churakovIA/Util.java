package com.github.churakovIA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Util {

  public static String getRealClientIP(HttpServletRequest req) {
    String ip = "";
    if (req != null) {
      ip = req.getHeader("X-FORWARDED-FOR");
      if (ip == null || "".equalsIgnoreCase(ip)) {
        ip = req.getRemoteAddr();
      }
    }
    return ip;
  }

  public static Map<String, String> getRequestHeaders(HttpServletRequest request) {

    Map<String, String> result = new HashMap<>();

    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      result.put(key, value);
    }
    return result;
  }

  public static String getFullURL(HttpServletRequest request) {
    StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
    String queryString = request.getQueryString();

    if (queryString == null) {
      return requestURL.toString();
    } else {
      return requestURL.append('?').append(queryString).toString();
    }
  }

  public static String inputStreamToString(InputStream in) throws IOException {
    return inputStreamToString(in, "UTF-8");
  }

  static String inputStreamToString(InputStream in, String charset) throws IOException {
    if (in == null || in.available() == 0) {
      return null;
    } else {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      inputToOutputStream(in, out);
      return out.toString(charset);
    }
  }

  private static void inputToOutputStream(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read = in.read(buffer);
    while (read != -1) {
      out.write(buffer, 0, read);
      read = in.read(buffer);
    }
  }

}
