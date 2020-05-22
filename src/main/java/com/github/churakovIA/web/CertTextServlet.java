package com.github.churakovIA.web;

import java.io.IOException;
import javax.security.cert.X509Certificate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cert")
public class CertTextServlet extends AbstractServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");
    StringBuilder sb = new StringBuilder();
    X509Certificate[] certs = (X509Certificate[]) req
        .getAttribute("javax.servlet.request.X509Certificate");
    if (certs != null) {
      for (X509Certificate cert : certs) {
        sb.append(cert).append('\n');
      }
    }
    if (sb.length() > 0) {
      resp.getWriter().println(sb.toString());
    } else {
      resp.getWriter().println("No X.509 client certificate found in request");
    }
  }
}
