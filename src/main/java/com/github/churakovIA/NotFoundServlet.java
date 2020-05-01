package com.github.churakovIA;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/404")
public class NotFoundServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");
    PrintWriter out = resp.getWriter();
    out.println("404 (Not found): " + req.getAttribute("javax.servlet.error.request_uri"));
    out.println("see docs api: https://github.com/churakovIA/your_ip/blob/master/README.md");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doGet(req, resp);
  }
}
