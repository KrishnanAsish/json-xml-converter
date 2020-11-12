package com.converter.implementations;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.converter.constants.ConverterConstants;
import com.converter.exceptions.ValidationException;
import com.converter.interfaces.XMLJSONConverterI;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CswConverter implements XMLJSONConverterI {

	public void convertJSONtoXML(File inputJson, File outputXml) {
		Map jsonDataMap=null;
		Document document = null;
		try {
			jsonDataMap = getInputJsonValues(inputJson);
			document = createDocumentObjectForXml();
			Element root = document.createElement(ConverterConstants.XML_OBJECT_TAG);
			document.appendChild(root);
			createXmlEntriesFromMap(document,root,jsonDataMap);
			generateXmlFileFromDocumentObject(outputXml, document);
		} catch (ValidationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done creating XML File");
	}

	//Generates the final XML file from using the document object
	private void generateXmlFileFromDocumentObject(File outputXml, Document document)
			throws TransformerFactoryConfigurationError, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(outputXml);
		Transformer transformer=null;
		transformer = transformerFactory.newTransformer();
		transformer.transform(domSource, streamResult);
	}

	//Creates the document object for generating XML elements
	private Document createDocumentObjectForXml() throws ParserConfigurationException {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		documentBuilder = documentFactory.newDocumentBuilder();
		document = documentBuilder.newDocument();
		return document;
	}

	private void createXmlEntriesFromMap(Document document, Element root, Map map) {
		for(Object key:map.keySet()) {
			Object value = map.get(key);
			jsonValueToXmlMapper(document, root, key, value);
		}
	}

	//Generate an XML element under the root element, as passed in the argument
	//nameAttributeValue represents name=<nameAttributeValue>
	//nodeValue represents <node name="">nodeValue</node>
	private void jsonValueToXmlMapper(Document document, Element root, Object nameAttributeValue, Object nodeValue) {
		if(nodeValue==null)
			generateNewElement(document, root, ConverterConstants.XML_NULL_TAG, nameAttributeValue, null);
		else if(nodeValue instanceof Boolean)
			generateNewElement(document, root, ConverterConstants.XML_BOOLEAN_TAG, nameAttributeValue, nodeValue.toString());
		else if(NumberUtils.isCreatable(nodeValue.toString()))
			generateNewElement(document, root, ConverterConstants.XML_NUMBER_TAG, nameAttributeValue, nodeValue.toString());
		else if(nodeValue instanceof LinkedHashMap) {
			//Create an object tag, with name = nameAttributeValue and nodeValue is null
			Element curEle = generateNewElement(document, root, ConverterConstants.XML_OBJECT_TAG, nameAttributeValue, null);
			//Recursive call, to build XML elements from a map, but to be linked under curEle and not under root
			createXmlEntriesFromMap(document,curEle,(LinkedHashMap)nodeValue);
		}
		else if(nodeValue instanceof ArrayList) { 
			Element curEle = generateNewElement(document, root, ConverterConstants.XML_ARRAY_TAG, nameAttributeValue,null); 
			for(Object obj:(ArrayList)nodeValue) {
				//For array values, no need to generate name attribute
				jsonValueToXmlMapper(document,curEle,null,obj);
			} 
		}
		else {
			//Implies we are left with only the String type
			generateNewElement(document, root, ConverterConstants.XML_STRING_TAG, nameAttributeValue, nodeValue.toString());
		}
	}

	//Create a single XML element under root with tag name as tagName and attribute name with val as nameAttribValue
	private Element generateNewElement(Document document, Element root, String tagName, Object nameAttribValue,
			Object textNodeValue) {
		Element objEle = document.createElement(tagName);
		root.appendChild(objEle);
		if(nameAttribValue!=null) {
			Attr attr = document.createAttribute(ConverterConstants.XML_NAME_ATTRIBUTE);
			attr.setValue(nameAttribValue.toString());
			objEle.setAttributeNode(attr);
		}
		if(textNodeValue!=null) objEle.appendChild(document.createTextNode(textNodeValue.toString()));
		return objEle;
	}

	private Map getInputJsonValues(File inputJson) throws ValidationException {
		Map jsonDataMap = null;
		FileInputStream fi=null;
		BufferedInputStream bi=null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			fi = new FileInputStream(inputJson);
			bi = new BufferedInputStream(fi);
			jsonDataMap = mapper.readValue(bi, Map.class); //ObjectMapper reading, using dependent jar
			//Json parser closes the stream reader, at the end of parse cycle
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ValidationException(ConverterConstants.filePathDoesNotExist+inputJson.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidationException(ConverterConstants.exceptionOccurred+e.getMessage());
		}
		return jsonDataMap;
	}

}
