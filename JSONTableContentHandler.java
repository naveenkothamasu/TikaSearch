package org.apache.tika.parser.tsv;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class JSONTableContentHandler extends BodyContentHandler{
	
	String json=null;
	private ArrayList<String> headers = new ArrayList<String>();
	private BufferedWriter fileWriter;
	private int currentElemPos = 0;
	
	private int headerPos = -1;
	

	public JSONTableContentHandler (String fileName) {
		try {
			//fileWriter = new FileWriter("test.json");
			fileWriter = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(fileName), "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void startDocument() {
		try {
			fileWriter.write("{\"job details\": [{");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endDocument() throws SAXException {
		try {
			fileWriter.write("}]}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		//System.out.println(qName);
		if(qName.equals("th")){
			++headerPos;
		}else if(qName.equals("td")){
			try {
				++currentElemPos;
				fileWriter.write("\""+ headers.get(currentElemPos)+"\":");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	/*	if(headerPos==20)
		{
			headerPos++;
		}*/
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		StringBuilder sb = new StringBuilder();
		for(int i=start;i<start+length;i++)
		{
			sb.append(ch[i]);
			//System.out.print(ch[i]);
		}
		//System.out.println(sb.toString());
		if(headerPos  < 20 && currentElemPos == 0){
			headers.add(sb.toString());
		}else{
			try {
				if(currentElemPos-1 == headerPos){
					fileWriter.write("\""+sb.toString()+"\"");
				}else{
						fileWriter.write("\""+sb.toString()+"\",");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		//System.out.println(",");
	}
}

