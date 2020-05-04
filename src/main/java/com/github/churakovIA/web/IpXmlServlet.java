package com.github.churakovIA.web;

import com.github.churakovIA.util.Util;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/xml")
public class IpXmlServlet extends AbstractServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/xml");
    resp.getWriter().write(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ip>" + Util.getRealClientIP(req) + "</ip>");
  }
}
