package com.github.churakovIA;

import com.github.churakovIA.mappers.RequestInfoMapper;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

public class YourIPServlet extends HttpServlet {

  @SneakyThrows
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

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
    }
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    resp.setContentType("text/xml;charset=UTF-8");
    RequestInfoMapper.getInstance(req).toXML(resp.getWriter());
  }
}
