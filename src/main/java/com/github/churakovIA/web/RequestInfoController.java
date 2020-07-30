package com.github.churakovIA.web;

import com.github.churakovIA.persist.dao.RequestInfoDao;
import com.github.churakovIA.persist.dao.RequestInfoDaoImpl;
import com.github.churakovIA.to.RequestInfoTo;
import com.github.churakovIA.util.JsonUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/rq/view")
public class RequestInfoController extends HttpServlet {

  private final Logger log = LoggerFactory.getLogger(RequestInfoController.class);
  private final RequestInfoDao dao = RequestInfoDaoImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    log.debug("call view requests");

    PrintWriter writer = resp.getWriter();
    List<RequestInfoTo> last = dao.getLast(10);
    for (RequestInfoTo to : last) {
      writer.println(JsonUtil.writeValue(to));
      writer.println("\n-------------------------------------------------\n");
    }
  }
}
