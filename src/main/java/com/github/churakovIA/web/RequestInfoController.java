package com.github.churakovIA.web;

import com.github.churakovIA.config.TemplateEngineUtil;
import com.github.churakovIA.persist.dao.RequestInfoDao;
import com.github.churakovIA.persist.dao.RequestInfoDaoImpl;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet("/rq/view")
public class RequestInfoController extends HttpServlet {

  private final Logger log = LoggerFactory.getLogger(RequestInfoController.class);
  private final RequestInfoDao dao = RequestInfoDaoImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    log.debug("call view requests");

    int items;
    String parameter = req.getParameter("items");
    try {
      items = Integer.parseInt(parameter);
    } catch (NumberFormatException e) {
      items = 10;
    }

    String ip = req.getParameter("ip");
    if (ip == null) {
      ip = "";
    }

    TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
    WebContext context = new WebContext(req, resp, req.getServletContext());
    context.setVariable("items", items);
    context.setVariable("ip", ip);
    context.setVariable("requests", dao.getLast(items));
    engine.process("requests.html", context, resp.getWriter());

  }
}
