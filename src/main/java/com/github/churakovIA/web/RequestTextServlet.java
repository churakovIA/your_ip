package com.github.churakovIA.web;

import com.github.churakovIA.mappers.RequestInfoMapper;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rq")
public class RequestTextServlet extends AbstractRequestServlet {

  @Override
  protected void processRequest(RequestInfoMapper mapper, HttpServletRequest req,
      HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");
    mapper.toString(resp.getWriter());
  }
}
