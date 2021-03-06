package com.github.churakovIA.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/wait/*")
public class WaitingServlet extends AbstractServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();
    long millis = pathInfo == null ? 1000 : Long.parseLong(pathInfo.split("/")[1]);

    PrintWriter out = resp.getWriter();
    resp.setContentType("text/plain");
    out.println("wait=" + millis+"ms");
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      e.printStackTrace(out);
    }
  }
}
