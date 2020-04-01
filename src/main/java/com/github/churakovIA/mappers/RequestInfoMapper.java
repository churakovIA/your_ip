package com.github.churakovIA.mappers;

import com.github.churakovIA.Util;
import com.github.churakovIA.model.RequestInfo;
import com.github.churakovIA.util.DomProcessor;
import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.w3c.dom.Element;

public class RequestInfoMapper {

  private RequestInfo requestInfo;

  private RequestInfoMapper(RequestInfo requestInfo) {
    this.requestInfo = requestInfo;
  }

  public static RequestInfoMapper getInstance(HttpServletRequest req) throws IOException{
    RequestInfo requestInfo = new RequestInfo();
    requestInfo.setProtocol(req.getProtocol());
    requestInfo.setMethod(req.getMethod());
    requestInfo.setFullURL(Util.getFullURL(req));
    requestInfo.setLocale(req.getLocale().toLanguageTag());
    requestInfo.setIp(Util.getRealClientIP(req));
    requestInfo.setHeaders(Util.getRequestHeaders(req));
    requestInfo.setBody(Util.inputStreamToString(req.getInputStream()));
    return new RequestInfoMapper(requestInfo);
  }

  public void toXML(Writer writer) throws Exception {
    DomProcessor processor = new DomProcessor();
    Element root = processor.addElement("RequestInfo");
    processor.addElementWithText(root, "Protocol", requestInfo.getProtocol());
    processor.addElementWithText(root, "Method", requestInfo.getMethod());
    processor.addElementWithText(root, "FullURL", requestInfo.getFullURL());
    processor.addElementWithText(root, "Locale", requestInfo.getLocale());
    processor.addElementWithText(root, "IP", requestInfo.getIp());
    Element nodeHeaders = processor.addElement(root, "Headers");
    for (Entry<String, String> entry : requestInfo.getHeaders().entrySet()) {
      processor.addElementWithText(nodeHeaders, "Header", entry.getValue())
          .setAttribute("name", entry.getKey());
    }
    String body = requestInfo.getBody();
    if (body != null && body.length() != 0) {
      Element nodeBody = processor.addElement(root, "Body");
      nodeBody.appendChild(processor.getDoc().createCDATASection(body));
    }
    processor.writeDoc(writer);
  }
}
