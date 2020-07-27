package com.github.churakovIA.web;

import com.github.churakovIA.mappers.RequestInfoMapper;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/rq")
public class RequestTextServlet extends AbstractServlet {
  private final static Logger log = LoggerFactory.getLogger(RequestTextServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    log.debug("Request InputStream: {}",
        req.getInputStream() == null ? "null" : req.getInputStream().available());
    resp.setContentType("text/plain");
    RequestInfoMapper.getInstance(req).toString(resp.getWriter());
  }
}
