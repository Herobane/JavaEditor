package com.herobane.javaeditor;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Data {
	
	public int width;
	public int height;
	
	public String fontName;
	public int fontStyle;
	public int fontSize;
	
	public Data() {
		
		final DocumentBuilderFactory factory;
		final DocumentBuilder builder;
		final Document document;
		Element root;
		NodeList rootNodes;
		Element XMLwindow;
		Element XMLfont;
		Element XMLwidth = null;
		Element XMLheight = null;
		Element XMLfontName;
		Element XMLfontStyle;
		Element XMLfontSize;
		
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File("preferences.xml"));
			root = document.getDocumentElement();
			rootNodes = root.getChildNodes();
			
			XMLwindow = (Element) rootNodes.item(1);
			XMLfont = (Element) rootNodes.item(3);
			
			XMLwidth = (Element) XMLwindow.getElementsByTagName("width").item(0);
			XMLheight = (Element) XMLwindow.getElementsByTagName("height").item(0);
			
			XMLfontName = (Element) XMLfont.getElementsByTagName("name").item(0);
			XMLfontStyle = (Element) XMLfont.getElementsByTagName("style").item(0);
			XMLfontSize = (Element) XMLfont.getElementsByTagName("size").item(0);
			
			width = Integer.parseInt(XMLwidth.getTextContent());
			height = Integer.parseInt(XMLheight.getTextContent());
			
			fontName = XMLfontName.getTextContent();
			fontStyle = Integer.parseInt(XMLfontStyle.getTextContent());
			fontSize = Integer.parseInt(XMLfontSize.getTextContent());
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new Data();
	}

}
