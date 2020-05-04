package com.github.churakovIA.web;

import com.github.churakovIA.mappers.RequestInfoMapper;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rq/xml")
public class RequestXmlServlet extends AbstractServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/xml");
    RequestInfoMapper.getInstance(req).toXML(resp.getWriter());
  }
}
