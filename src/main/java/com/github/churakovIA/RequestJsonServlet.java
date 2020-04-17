package com.github.churakovIA;

import com.github.churakovIA.mappers.RequestInfoMapper;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rq/json")
public class RequestJsonServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    writeResponse(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    writeResponse(req, resp);
  }

  private void writeResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    RequestInfoMapper.getInstance(req).toJSON(resp.getWriter());
  }
}
