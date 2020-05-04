package com.github.churakovIA.web;

import com.github.churakovIA.mappers.RequestInfoMapper;
import com.github.churakovIA.util.Util;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YourIPServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String remoteAddr = Util.getRealClientIP(req);

    RequestInfoMapper mapper = RequestInfoMapper.getInstance(req);

    String format = req.getParameter("format");
    if(format == null){
      format = "";
    }

    PrintWriter out = resp.getWriter();

    switch (format){
      case "json":
        resp.setContentType("application/json");
        mapper.toJSON(out);
        break;
      case "xml":
        resp.setContentType("text/xml");
        mapper.toXML(out);
        break;
      default:
        resp.setContentType("text/plain");
        out.println(remoteAddr);
        out.println("GitHub: " + getServletContext().getInitParameter("projectUrl"));
    }
  }
}
