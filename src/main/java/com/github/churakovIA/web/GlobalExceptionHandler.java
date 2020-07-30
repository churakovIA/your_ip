package com.github.churakovIA.web;

import com.github.churakovIA.util.JsonUtil;
import com.github.churakovIA.util.ValidationUtil;
import com.github.churakovIA.util.exeption.ErrorInfo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/GlobalExceptionHandler")
public class GlobalExceptionHandler extends AbstractServlet {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
    Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
    String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
    if (servletName == null) {
      servletName = "Unknown";
    }
    String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
    if (requestUri == null) {
      requestUri = "Unknown";
    }

    PrintWriter out = resp.getWriter();
    resp.setContentType("application/json");
    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    Throwable rootCause = ValidationUtil.getRootCause(throwable);
    ErrorInfo errorInfo = new ErrorInfo(requestUri, statusCode, servletName, rootCause.toString());
    JsonUtil.writeValue(out, errorInfo);

    log.error("servlet error: {}", errorInfo);

  }
}
