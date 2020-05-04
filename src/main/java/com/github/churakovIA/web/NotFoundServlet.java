package com.github.churakovIA.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/404")
public class NotFoundServlet extends AbstractServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");
    PrintWriter out = resp.getWriter();
    out.println("404 (Not found): " + req.getAttribute("javax.servlet.error.request_uri"));
    out.println("see docs api: " + getServletContext().getInitParameter("projectUrl"));
  }
}
