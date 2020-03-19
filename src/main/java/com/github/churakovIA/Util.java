package com.github.churakovIA;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

class Util {

  static String getRealClientIP(HttpServletRequest req) {
    String ip = "";
    if (req != null) {
      ip = req.getHeader("X-FORWARDED-FOR");
      if (ip == null || "".equalsIgnoreCase(ip)) {
        ip = req.getRemoteAddr();
      }
    }
    return ip;
  }

  static Map<String, String> getRequestHeaders(HttpServletRequest request) {

    Map<String, String> result = new HashMap<>();

    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      result.put(key, value);
    }
    return result;
  }
}
