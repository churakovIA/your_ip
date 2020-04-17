package com.github.churakovIA;

import com.github.churakovIA.mappers.RequestInfoMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet("/rq/xml")
public class RequestXmlServlet extends HttpServlet {

  @SneakyThrows
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
    writeResponse(req, resp);
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    writeResponse(req, resp);
  }

  @SneakyThrows
  private void writeResponse(HttpServletRequest req, HttpServletResponse resp) {
    resp.setContentType("text/xml");
    RequestInfoMapper.getInstance(req).toXML(resp.getWriter());
  }
}
