package com.github.churakovIA.web;

import com.github.churakovIA.model.to.DummyTo;
import com.github.churakovIA.persist.dao.DummyDao;
import com.github.churakovIA.persist.dao.DummyDaoImpl;
import com.github.churakovIA.util.JsonUtil;
import com.github.churakovIA.util.exeption.ApplicationException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/dummy/*")
public class DummyServlet extends HttpServlet {

  private final Logger log = LoggerFactory.getLogger(DummyServlet.class);
  private final DummyDao dao = DummyDaoImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Optional<String> partPath = getPartPath(req);
    if (partPath.isPresent()) {
      DummyTo dummy = dao.get(getId(req));
      if (dummy == null) {
        resp.sendRedirect(req.getContextPath() + "/404");
      } else {
        resp.setContentType(dummy.getContentType());
        resp.getWriter().print(dummy.getDummy());
      }
    } else {
      resp.setContentType("application/json");
      JsonUtil.writeValue(resp.getWriter(), dao.getAll());
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Optional<String> partPath = getPartPath(req);

    if (partPath.isPresent()) {
      DummyTo dummy = dao.get(getId(req));
      resp.setContentType(dummy.getContentType());
      resp.getWriter().print(dummy.getDummy());
    } else {
      throw new IllegalArgumentException("id absent");
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int id = dao.save(JsonUtil.readValue(req.getInputStream(), DummyTo.class));
    resp.setContentType("application/json");
    resp.getWriter().write("{\"id\":\"" + id + "\"}");
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    boolean delete = dao.delete(getId(req));
    resp.setContentType("application/json");
    if (delete) {
      resp.getWriter().write("{\"status\":\"ok\"}");
    } else {
      resp.getWriter().write("{\"status\":\"error\"}");
    }
  }

  private Optional<String> getPartPath(HttpServletRequest req) {
    String pathInfo = req.getPathInfo();
    if (pathInfo == null) {
      return Optional.empty();
    }
    String[] pathParts = pathInfo.split("/");

    if (pathParts.length > 2) {
      throw new IllegalArgumentException("Не верный формат запроса");
    }
    return pathParts.length < 1 ? Optional.empty() : Optional.ofNullable(pathParts[1]);
  }

  private int getId(HttpServletRequest req) {
    String parameterId = getPartPath(req)
        .orElseThrow(() -> new IllegalArgumentException("Не верный формат id"));
    log.debug("call view request by id={}", parameterId);
    try {
      return Integer.parseInt(parameterId);
    } catch (NumberFormatException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Не верный формат id", e);
    }
  }
}
