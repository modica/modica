/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.serializer.xml;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.StructuredFieldHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import static org.modica.common.ByteUtils.bytesToHex;

/**
 * A StructuredFieldHandler that transforms AFP SF parsing to XML written to an output stream.
 */
public class XmlSerializingStructuredFieldHandler implements StructuredFieldHandler {

    public static final String URI = "http://afpparser.org";

    public static final String PREFIX = "afp";

    public static final String SF_ELEM_NAME = "sf";

    public static final String SF_PARAMETER = "parameter";

    public static final String SF_PARAMETER_KEY = "key";

    public static final String ROOT_NAME = "modca";

    private final TransformerHandler handler;

    public XmlSerializingStructuredFieldHandler(OutputStream output) {
        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        tf.setAttribute("indent-number", 2);
        try {
            handler  = tf.newTransformerHandler();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        Transformer transformer = handler.getTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        try {
            handler.setResult(new StreamResult(new OutputStreamWriter(output, "utf-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startAfp(){
        try {
            handler.startDocument();
            handler.startPrefixMapping(PREFIX, URI);
            handler.startElement(URI, ROOT_NAME, qNameFor(ROOT_NAME), null);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void handleBegin(StructuredField structuredField) {
        startElement(SF_ELEM_NAME, structuredField);
    }

    @Override
    public void handleEnd(StructuredField structuredField) {
        endElement(SF_ELEM_NAME);
    }

    @Override
    public void handle(StructuredField structuredField) {
        startElement(SF_ELEM_NAME, structuredField);
        endElement(SF_ELEM_NAME);
    }

    @Override
    public void endAfp(){
        try {
            handler.endElement(URI, ROOT_NAME, qNameFor(ROOT_NAME));
            handler.endPrefixMapping(PREFIX);
            handler.endDocument();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void startElement(String name, StructuredField structuredField) {
        Attributes atts = getAttributes(structuredField);
        try {
            handler.startElement(URI, name, qNameFor(name), atts);
            writeParameters(structuredField);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeParameters(StructuredField structuredField) throws SAXException {
        for (ParameterAsString parameterAsString : structuredField.getParameters()) {
            String key = parameterAsString.getKey();
            String valueStr = parameterAsString.getValue();
            char[] value = valueStr != null ? valueStr.toCharArray() : new char[0];
            AttributesImpl pparamAtts = new AttributesImpl();
            addAttribute(pparamAtts, SF_PARAMETER_KEY, key);
            handler.startElement(URI, SF_PARAMETER, qNameFor(SF_PARAMETER), pparamAtts);
            handler.characters(value, 0, value.length);
            handler.endElement(URI, SF_PARAMETER, qNameFor(SF_PARAMETER));
        }
    }

    private void endElement(String name) {
        try {
            handler.endElement(URI, name, qNameFor(name));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static String qNameFor(String name) {
        return PREFIX + ":" + name;
    }

    private static Attributes getAttributes(StructuredField sf) {
        AttributesImpl atts = new AttributesImpl();
        addAttribute(atts, "name", sf.getType().getName());
        addAttribute(atts, "type-code", bytesToHex(sf.getType().getTypeCode().getValue()));
        addAttribute(atts, "category-code", bytesToHex(sf.getType().getCategoryCode().getValue()));
        addAttribute(atts, "offset", String.valueOf(sf.getOffset()));
        addAttribute(atts, "length", String.valueOf(sf.getLength()));
        addAttribute(atts, "ext-length", String.valueOf(sf.getExtLength()));
        addAttribute(atts, "ext-data", String.valueOf(sf.hasExtData()));
        addAttribute(atts, "segmented-data", String.valueOf(sf.hasSegmentedData()));
        addAttribute(atts, "data-padding", String.valueOf(sf.hasDataPadding()));
        return atts;
    }

    private static void addAttribute(AttributesImpl atts, String name, String value) {
        atts.addAttribute("", name, name, "CDATA", value);
    }

}