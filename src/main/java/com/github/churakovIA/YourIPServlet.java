package com.github.churakovIA;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YourIPServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String remoteAddr = Util.getRealClientIP(req);

    String format = req.getParameter("format");
    if(format == null){
      format = "";
    }

    PrintWriter out = resp.getWriter();

    switch (format){
      case "json":
        resp.setContentType("application/json");
        out.println("{\"ip\":\""+remoteAddr+"\"}");
        break;
      case "xml":
        resp.setContentType("text/xml");
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<ip>"+remoteAddr+"</ip>");
        break;
      default:
        resp.setContentType("text/plain");
        out.println(remoteAddr);
    }
  }
}
