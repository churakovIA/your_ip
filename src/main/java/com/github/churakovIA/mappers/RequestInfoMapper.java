package com.github.churakovIA.mappers;

import com.github.churakovIA.model.RequestInfo;
import java.io.Writer;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RequestInfoMapper {

  private RequestInfo requestInfo;

  public RequestInfoMapper(RequestInfo requestInfo) {
    this.requestInfo = requestInfo;
  }

  public void toXML(Writer writer) {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;
    try {
      dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();
      //root
      Element rootElement = doc.createElement("RequestInfo");
      doc.appendChild(rootElement);
      //ip
      Element nodeIP = doc.createElement("IP");
      nodeIP.appendChild(doc.createTextNode(requestInfo.getIp()));
      rootElement.appendChild(nodeIP);
      //headers title
      Element nodeHeaders = doc.createElement("Headers");
      rootElement.appendChild(nodeHeaders);
      //headers elements
      for (Entry<String, String> entry : requestInfo.getHeaders().entrySet()) {
        Element element = doc.createElement(entry.getKey());
        element.appendChild(doc.createTextNode(entry.getValue()));
        nodeHeaders.appendChild(element);
      }

      //write to string
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(writer);
      transformer.transform(source, result);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
