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
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class for XML
 */
public class XmlUtils {
    final private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    final private static XStream cachedStream;

    final private static XStream cachedJsonStream;

    static {
        cachedStream = new XStream() {
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {

                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                    }

                };
            }

        };

        cachedJsonStream = new XStream(new JettisonMappedXmlDriver()) {
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {

                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                    }

                };
            }
        };
    }

    public static void dump(XMLStreamReader reader, Logger callbackLogger) {
        if (logger.isDebugEnabled()) {
            callbackLogger.debug("\nXML Dump Results: \n" + parse(reader) + "\n");
        }
    }

    /**
     * Serializes any Object to XML
     *
     * @param object Object to serialize
     * @return XML serialization of the supplied object
     */
    public static String toXmlJaxb(Object object) {
        String result = "";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Serializes any Object to XML
     *
     * @param object Object to serialize
     * @return XML serialization of the supplied object
     */
    public static String toXml(Object object) {
        return toXml(object, false);
    }

    /**
     * Serializes any Object to XML Note: Annotation detection will be used when
     * called.
     *
     * @param object     Object to serialize
     * @param annotation whether to use annotations
     * @return XML serialization of the supplied object
     */
    public static String toXml(Object object, boolean annotation) {
        String result = "";
        if (object != null) {
            if (annotation) {
                XStream stream = new XStream() {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                result = stream.toXML(object);
            } else {
                cachedStream.setMode(XStream.NO_REFERENCES);
                result = cachedStream.toXML(object);
            }
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
        return toXml(list, listName, false);
    }

    /**
     * Serializes a list to xml with a given name Note: Annotation detection
     * will be used when called.
     *
     * @param list       list to serialize
     * @param annotation whether to use annotations
     * @param listName   Name of the list
     * @return The serialized list
     */
    public static String toXml(List<?> list, String listName, boolean annotation) {

        String result = "";
        if (list != null) {
            if (annotation) {
                XStream stream = new XStream() {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                stream.alias(listName, List.class);
                result = stream.toXML(list);
            } else {
                cachedStream.setMode(XStream.NO_REFERENCES);
                cachedStream.alias(listName, List.class);
                result = cachedStream.toXML(list);
            }
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
        return toJSon(object, false);
    }

    /**
     * Serializes any Object to JSon
     *
     * @param object     Object to serialize
     * @param annotation whether to use annotations
     * @return JSon serialization of the supplied object
     */
    public static String toJSon(Object object, boolean annotation) {
        String result = "";
        if (object != null) {
            if (annotation) {
                XStream stream = new XStream(new JettisonMappedXmlDriver()) {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                result = stream.toXML(object);
            } else {
                cachedJsonStream.setMode(XStream.NO_REFERENCES);
                result = cachedJsonStream.toXML(object);
            }
        }
        return result;
    }

    /**
     * @param object
     * @param omit
     */
    public static String toPartialXml(Object object, Map<String, Class<?>> omit) {
        return (toPartialXml(object, omit, false));
    }

    /**
     * @param object
     * @param annotation whether to use annotations
     * @param omit
     */
    public static String toPartialXml(Object object, Map<String, Class<?>> omit, boolean annotation) {
        String result = "";
        if (object != null && omit != null) {
            XStream stream = new XStream() {
                protected MapperWrapper wrapMapper(MapperWrapper next) {
                    return new MapperWrapper(next) {

                        public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                            return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                        }

                    };
                }
            };
            stream.setMode(XStream.NO_REFERENCES);
            stream.autodetectAnnotations(annotation);
            for (Entry<String, Class<?>> stringClassEntry : omit.entrySet()) {
                Entry<String, Class<?>> alias = stringClassEntry;
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
        return toXml(object, aliases, false);
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
     * @param object     Object to serialize
     * @param aliases    Mapping Alias to use
     * @param annotation whether to use annotations
     * @return XML serialization of the supplied object
     */
    public static String toXml(Object object, Map<String, Class<?>> aliases, boolean annotation) {
        String result = "";
        if (object != null && aliases != null) {
            if (annotation) {
                XStream stream = new XStream() {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                for (Entry<String, Class<?>> stringClassEntry : aliases.entrySet()) {
                    Entry<String, Class<?>> alias = stringClassEntry;
                    stream.alias(alias.getKey(), alias.getValue());
                }
                result = stream.toXML(object);
            } else {
                cachedStream.setMode(XStream.NO_REFERENCES);
                for (Entry<String, Class<?>> stringClassEntry : aliases.entrySet()) {
                    Entry<String, Class<?>> alias = stringClassEntry;
                    cachedStream.alias(alias.getKey(), alias.getValue());
                }
                result = cachedStream.toXML(object);
            }
        }
        return result;
    }

    public static String toPartialXml(Object object,
                                      Map<String, Class<?>> aliases, Map<String, Class<?>> omit) {
        return toPartialXml(object, aliases, omit, false);
    }

    public static String toPartialXml(Object object,
                                      Map<String, Class<?>> aliases, Map<String, Class<?>> omit, boolean annotation) {
        String result = "";
        XStream stream = new XStream() {
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {

                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                    }

                };
            }
        };
        stream.autodetectAnnotations(annotation);
        stream.setMode(XStream.NO_REFERENCES);
        if (object != null && aliases != null) {
            for (Entry<String, Class<?>> stringClassEntry : aliases.entrySet()) {
                Entry<String, Class<?>> alias = stringClassEntry;
                stream.alias(alias.getKey(), alias.getValue());
            }
        }
        if (object != null && omit != null) {
            for (Entry<String, Class<?>> stringClassEntry : omit.entrySet()) {
                Entry<String, Class<?>> alias = stringClassEntry;
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
        return (T) fromJSon(json, false);
    }

    /**
     * Deserializes any XML to Object
     *
     * @param json       Object to deserialize
     * @param annotation whether to use annotations
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJSon(String json, boolean annotation) {
        T result = (T) "";
        if (StringUtils.isNotBlank(json)) {
            if (annotation) {
                XStream stream = new XStream(new JettisonMappedXmlDriver()) {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                result = (T) stream.fromXML(json);
            } else {
                cachedJsonStream.setMode(XStream.NO_REFERENCES);
                result = (T) cachedJsonStream.fromXML(json);
            }
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
        return (T) fromXml(xml, false);
    }

    /**
     * Deserializes any XML to Object
     *
     * @param xml        Object to deserialize
     * @param annotation whether to use annotations
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, boolean annotation) {
        T result = (T) "";
        if (StringUtils.isNotBlank(xml)) {
            if (annotation) {
                XStream stream = new XStream() {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                result = (T) stream.fromXML(xml);
            } else {
                cachedStream.setMode(XStream.NO_REFERENCES);
                result = (T) cachedStream.fromXML(xml);
            }
        }
        return result;
    }

    /**
     * Deserializes any XML to Object
     *
     * @param result Object of deserialization
     * @param xml    Object to deserialize
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXmlJaxb(Object result, String xml) {
        return (T) fromXmlJaxb(result.getClass(), xml);
    }

    /**
     * Deserializes any XML to Object
     *
     * @param resultClass Class of deserialization
     * @param xml         Object to deserialize
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXmlJaxb(Class resultClass, String xml) {
        T result = null;
        if (StringUtils.isNotBlank(xml)) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(resultClass);
                Unmarshaller marshaller = jaxbContext.createUnmarshaller();
                result = (T) marshaller.unmarshal(new StringReader(xml));
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
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
        return (T) fromXml(xml, aliases, false);
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
     * @param xml        Object to deserialize
     * @param annotation whether to use annotations
     * @param aliases    Mapping Alias to use
     * @return Object from xml
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Map<String, Class<?>> aliases, boolean annotation) {
        T result = (T) "";
        if (StringUtils.isNotBlank(xml) && aliases != null) {
            if (annotation) {
                XStream stream = new XStream() {
                    protected MapperWrapper wrapMapper(MapperWrapper next) {
                        return new MapperWrapper(next) {

                            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                                return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                            }

                        };
                    }
                };
                stream.autodetectAnnotations(annotation);
                stream.setMode(XStream.NO_REFERENCES);
                for (Entry<String, Class<?>> stringClassEntry : aliases.entrySet()) {
                    Entry<String, Class<?>> alias = stringClassEntry;
                    stream.alias(alias.getKey(), alias.getValue());
                }
                result = (T) stream.fromXML(xml);
            } else {
                cachedStream.setMode(XStream.NO_REFERENCES);
                for (Entry<String, Class<?>> stringClassEntry : aliases.entrySet()) {
                    Entry<String, Class<?>> alias = stringClassEntry;
                    cachedStream.alias(alias.getKey(), alias.getValue());
                }
                result = (T) cachedStream.fromXML(xml);
            }
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
            result.append(prefix).append(":");
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
