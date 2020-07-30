package com.github.churakovIA.web;

import com.github.churakovIA.mappers.RequestInfoMapper;
import com.github.churakovIA.persist.dao.RequestInfoDao;
import com.github.churakovIA.persist.dao.RequestInfoDaoImpl;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractRequestServlet extends AbstractServlet {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final RequestInfoDao dao = RequestInfoDaoImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    log.debug("receive request with method {}", req.getMethod());
    RequestInfoMapper mapper = RequestInfoMapper.getInstance(req);
    dao.save(mapper.getRequestInfo());
    processRequest(mapper, req, resp);
  }

  protected abstract void processRequest(RequestInfoMapper mapper, HttpServletRequest req,
      HttpServletResponse resp) throws IOException;
}
