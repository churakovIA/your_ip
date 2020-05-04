package com.github.churakovIA.web;

import com.github.churakovIA.util.Util;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/json")
public class IpJsonServlet extends AbstractServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.getWriter().write("{\"ip\":\"" + Util.getRealClientIP(req) + "\"}");
  }
}
