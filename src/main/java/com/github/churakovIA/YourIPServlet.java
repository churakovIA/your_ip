package com.github.churakovIA;

import com.github.churakovIA.mappers.RequestInfoMapper;
import com.github.churakovIA.model.RequestInfo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YourIPServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String remoteAddr = Util.getRealClientIP(req);

    RequestInfo requestInfo = new RequestInfo();
    requestInfo.setIp(remoteAddr);
    requestInfo.setHeaders(Util.getRequestHeaders(req));
    RequestInfoMapper mapper = new RequestInfoMapper(requestInfo);

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
        mapper.toXML(out);
        break;
      default:
        resp.setContentType("text/plain");
        out.println(remoteAddr);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    resp.setContentType("text/xml;charset=UTF-8");

    RequestInfo requestInfo = new RequestInfo();
    requestInfo.setIp(Util.getRealClientIP(req));
    requestInfo.setHeaders(Util.getRequestHeaders(req));
    requestInfo.setBody(Util.inputStreamToString(req.getInputStream()));
    RequestInfoMapper mapper = new RequestInfoMapper(requestInfo);
    mapper.toXML(resp.getWriter());
  }
}
