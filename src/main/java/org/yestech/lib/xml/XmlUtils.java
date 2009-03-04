/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class for XML
 */
public class XmlUtils {
    final private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    public static void dump(XMLStreamReader reader, Logger callbackLogger) {
        if (logger.isDebugEnabled()) {
            callbackLogger.debug("\nXML Dump Results: \n" + parse(reader) + "\n");
        }
    }

    /**
     * Serializes any Object to XML Note: Annotation detection will be used when
     * called.
     *
     * @param object Object to serialize
     * @return XML serialization of the supplied object
     */
    public static String toXml(Object object) {
        String result = "";
        if (object != null) {
            XStream stream = new XStream();
            stream.autodetectAnnotations(true);
            result = stream.toXML(object);
        }
        return result;
    }

    /**
     * Serializes a list to xml with a given name Note: Annotation detection
     * will be used when called.
     *
     * @param list     list to serialize
     * @param listName Name of the list
     * @return The serialized list
     */
    public static String toXml(List<?> list, String listName) {

        String result = "";
        if (list != null) {
            XStream stream = new XStream();
            // stream.registerConverter(new XStreamDateConverter(locale));
            stream.autodetectAnnotations(true);
            stream.alias(listName, List.class);
            result = stream.toXML(list);
        }
        return result;
    }

    /**
     * Serializes any Object to JSon
     *
     * @param object Object to serialize
     * @return JSon serialization of the supplied object
     */
    public static String toJSon(Object object) {
        String result = "";
        if (object != null) {
            XStream stream = new XStream(new JettisonMappedXmlDriver());
            result = stream.toXML(object);
        }
        return result;
    }

    /**
     * @param object
     * @param omit
     */
    public static String toPartialXml(Object object, Map<String, Class<?>> omit) {
        String result = "";
        if (object != null && omit != null) {
            XStream stream = new XStream();
            Iterator<Entry<String, Class<?>>> aliasIt = omit.entrySet()
                    .iterator();
            while (aliasIt.hasNext()) {
                Map.Entry<String, Class<?>> alias = aliasIt.next();
                stream.omitField(alias.getValue(), alias.getKey());
            }
            result = stream.toXML(object);
        }
        return result;
    }

    /**
     * Serializes any Object to XML. Using an Alias mapping that will replace
     * the any instance of Class with the key String in the XML. Format of the
     * alias mapping:
     * <ul>
     * <li>Key - String what you want to be printed
     * <li>Value - Class to be remapped
     * </ul>
     *
     * @param object  Object to serialize
     * @param aliases Mapping Alias to use
     * @return XML serialization of the supplied object
     */
    public static String toXml(Object object, Map<String, Class<?>> aliases) {
        String result = "";
        if (object != null && aliases != null) {
            XStream stream = new XStream();
            Iterator<Map.Entry<String, Class<?>>> aliasIt = aliases.entrySet()
                    .iterator();
            while (aliasIt.hasNext()) {
                Map.Entry<String, Class<?>> alias = aliasIt.next();
                stream.alias(alias.getKey(), alias.getValue());
            }
            result = stream.toXML(object);
        }
        return result;
    }

    public static String toPartialXml(Object object,
                                      Map<String, Class<?>> aliases, Map<String, Class<?>> omit) {
        String result = "";
        XStream stream = new XStream();
        if (object != null && aliases != null) {
            Iterator<Map.Entry<String, Class<?>>> aliasIt = aliases.entrySet()
                    .iterator();
            while (aliasIt.hasNext()) {
                Map.Entry<String, Class<?>> alias = aliasIt.next();
                stream.alias(alias.getKey(), alias.getValue());
            }
        }
        if (object != null && omit != null) {
            Iterator<Map.Entry<String, Class<?>>> aliasIt = omit.entrySet()
                    .iterator();
            while (aliasIt.hasNext()) {
                Map.Entry<String, Class<?>> alias = aliasIt.next();
                stream.omitField(alias.getValue(), alias.getKey());
            }
        }
        if (object != null) {
            result = stream.toXML(object);
        }
        return result;
    }

    /**
     * Deserializes any XML to Object
     *
     * @param json Object to deserialize
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJSon(String json) {
        T result = (T) "";
        if (StringUtils.isNotBlank(json)) {
            XStream stream = new XStream(new JettisonMappedXmlDriver());
            result = (T) stream.fromXML(json);
        }
        return result;
    }

    /**
     * Deserializes any XML to Object
     *
     * @param xml Object to deserialize
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml) {
        T result = (T) "";
        if (StringUtils.isNotBlank(xml)) {
            XStream stream = new XStream();
            result = (T) stream.fromXML(xml);
        }
        return result;
    }

    /**
     * Deserializes any XML to Object. Using an Alias mapping that will replace
     * the any instance of Class with the key String in the XML. Format of the
     * alias mapping:
     * <ul>
     * <li>Key - String what you want to be printed
     * <li>Value - Class to be remapped
     * </ul>
     *
     * @param xml     Object to deserialize
     * @param aliases Mapping Alias to use
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Map<String, Class<?>> aliases) {
        T result = (T) "";
        if (StringUtils.isNotBlank(xml) && aliases != null) {
            XStream stream = new XStream();
            Iterator<Map.Entry<String, Class<?>>> aliasIt = aliases.entrySet()
                    .iterator();
            while (aliasIt.hasNext()) {
                Map.Entry<String, Class<?>> alias = aliasIt.next();
                stream.alias(alias.getKey(), alias.getValue());
            }
            result = (T) stream.fromXML(xml);
        }
        return result;
    }

    public static void dump(XMLStreamReader reader) {
        dump(reader, logger);
    }

    public static String parse(XMLStreamReader reader) {
        StringBuffer result = new StringBuffer();
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    switch (reader.getEventType()) {

                        case XMLStreamConstants.START_ELEMENT:
                            result.append("<");
                            printName(reader, result);
                            printNamespaces(reader, result);
                            printAttributes(reader, result);
                            result.append(">");
                            break;

                        case XMLStreamConstants.END_ELEMENT:
                            result.append("</");
                            printName(reader, result);
                            result.append(">");
                            break;

                        case XMLStreamConstants.SPACE:

                        case XMLStreamConstants.CHARACTERS:
                            int start = reader.getTextStart();
                            int length = reader.getTextLength();
                            result.append(new String(reader.getTextCharacters(),
                                    start, length));
                            break;

                        case XMLStreamConstants.PROCESSING_INSTRUCTION:
                            result.append("<?");
                            if (reader.hasText())
                                result.append(reader.getText());
                            result.append("?>");
                            break;

                        case XMLStreamConstants.CDATA:
                            result.append("<![CDATA[");
                            start = reader.getTextStart();
                            length = reader.getTextLength();
                            result.append(new String(reader.getTextCharacters(),
                                    start, length));
                            result.append("]]>");
                            break;

                        case XMLStreamConstants.COMMENT:
                            result.append("<!--");
                            if (reader.hasText())
                                result.append(reader.getText());
                            result.append("-->");
                            break;

                        case XMLStreamConstants.ENTITY_REFERENCE:
                            result.append(reader.getLocalName()).append("=");
                            if (reader.hasText())
                                result.append("[").append(reader.getText())
                                        .append("]");
                            break;

                        case XMLStreamConstants.START_DOCUMENT:
                            result.append("<?xml");
                            result.append(" version='")
                                    .append(reader.getVersion()).append("'");
                            result.append(" encoding='").append(
                                    reader.getCharacterEncodingScheme()).append(
                                    "'");
                            if (reader.isStandalone())
                                result.append(" standalone='yes'");
                            else
                                result.append(" standalone='no'");
                            result.append("?>");
                            break;
                    }
                    reader.next();
                } // end while
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                }
            }
        }
        return result.toString();
    }

    private static void printName(XMLStreamReader xmlr, StringBuffer result) {
        if (xmlr.hasName()) {
            String prefix = xmlr.getPrefix();
            String uri = xmlr.getNamespaceURI();
            String localName = xmlr.getLocalName();
            printName(prefix, uri, localName, result);
        }
    }

    private static void printName(String prefix, String uri, String localName,
                                  StringBuffer result) {
        if (uri != null && !("".equals(uri)))
            result.append("['").append(uri).append("']:");
        if (prefix != null)
            result.append(prefix + ":");
        if (localName != null)
            result.append(localName);
    }

    private static void printAttributes(XMLStreamReader xmlr,
                                        StringBuffer result) {
        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
            printAttribute(xmlr, i, result);
        }
    }

    private static void printAttribute(XMLStreamReader xmlr, int index,
                                       StringBuffer result) {
        String prefix = xmlr.getAttributePrefix(index);
        String namespace = xmlr.getAttributeNamespace(index);
        String localName = xmlr.getAttributeLocalName(index);
        String value = xmlr.getAttributeValue(index);
        result.append(" ");
        printName(prefix, namespace, localName, result);
        result.append("='").append(value).append("'");
    }

    private static void printNamespaces(XMLStreamReader xmlr,
                                        StringBuffer result) {
        for (int i = 0; i < xmlr.getNamespaceCount(); i++) {
            printNamespace(xmlr, i, result);
        }
    }

    private static void printNamespace(XMLStreamReader xmlr, int index,
                                       StringBuffer result) {
        String prefix = xmlr.getNamespacePrefix(index);
        String uri = xmlr.getNamespaceURI(index);
        result.append(" ");
        if (prefix == null)
            result.append("xmlns='").append(uri).append("'");
        else
            result.append("xmlns:").append(prefix).append("='").append(uri)
                    .append("'");
    }

}
