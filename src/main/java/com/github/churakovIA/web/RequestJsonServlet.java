package com.github.churakovIA.web;

import com.github.churakovIA.mappers.RequestInfoMapper;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rq/json")
public class RequestJsonServlet extends AbstractRequestServlet {

  @Override
  protected void processRequest(RequestInfoMapper mapper, HttpServletRequest req,
      HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    mapper.toJSON(resp.getWriter());
  }
}
