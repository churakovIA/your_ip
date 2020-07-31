package com.github.churakovIA.web;

import com.github.churakovIA.config.TemplateEngineUtil;
import com.github.churakovIA.persist.dao.RequestInfoDao;
import com.github.churakovIA.persist.dao.RequestInfoDaoImpl;
import com.github.churakovIA.to.RequestInfoTo;
import com.github.churakovIA.util.exeption.ApplicationException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet("/rq/view/*")
public class RequestInfoByIdController extends HttpServlet {

  private final Logger log = LoggerFactory.getLogger(RequestInfoByIdController.class);
  private final RequestInfoDao dao = RequestInfoDaoImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String pathInfo = req.getPathInfo();
    String[] pathParts = pathInfo.split("/");

    if (pathParts.length > 2) {
      resp.sendRedirect(req.getContextPath() + "/404");
      return;
    }

    int id;
    String parameterId = pathParts[1];

    log.debug("call view request by id={}", parameterId);

    try {
      id = Integer.parseInt(parameterId);
    } catch (NumberFormatException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Не верный формат id", e);
    }

    RequestInfoTo requestInfoTo = dao.get(id);
    if (requestInfoTo == null) {
      resp.sendRedirect(req.getContextPath() + "/404");
    } else {
      TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
      WebContext context = new WebContext(req, resp, req.getServletContext());
      context.setVariable("req", requestInfoTo);
      engine.process("request.html", context, resp.getWriter());

    }
  }
}
