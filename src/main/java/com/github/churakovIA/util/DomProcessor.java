package com.github.churakovIA.util;

import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomProcessor {

  private static final DocumentBuilderFactory DOCUMENT_FACTORY = DocumentBuilderFactory
      .newInstance();
  private static final DocumentBuilder DOCUMENT_BUILDER;

  static {
    DOCUMENT_FACTORY.setNamespaceAware(true);
    try {
      DOCUMENT_BUILDER = DOCUMENT_FACTORY.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException(e);
    }
  }

  private final Document doc;

  public DomProcessor() {
    this.doc = DOCUMENT_BUILDER.newDocument();
  }

  public Document getDoc() {
    return doc;
  }

  public Element addElement(String tagName) {
    return addElement(null, tagName);
  }

  public Element addElement(Element parent, String tagName) {
    Element node = doc.createElement(tagName);
    if (parent == null) {
      doc.appendChild(node);
    } else {
      parent.appendChild(node);
    }
    return node;
  }

  public Element addElementWithText(Element parent, String tagName, String text) {
    Element node = doc.createElement(tagName);
    if (text != null) {
      node.appendChild(doc.createTextNode(text));
    }
    parent.appendChild(node);
    return node;
  }

  public void writeDoc(Writer writer) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(writer);
    transformer.transform(source, result);
  }
}
