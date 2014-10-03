package org.apache.tika.parser.tsv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;


import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class JSONTableContentHandler extends BodyContentHandler{
	
	String json=null;
	private ArrayList<String> headers = new ArrayList<String>();
	private OutputStreamWriter fileWriter;
	private int currentElemPos = -1;
	
	private int headerPos = -1;
	

	public JSONTableContentHandler (String fileName) {
		try {
			//fileWriter = new FileWriter(new OutputStreamWriter(new FileOutputStream(fileName+".json"), "UTF-8"));
			fileWriter = new OutputStreamWriter(new FileOutputStream(fileName+".json"), "ISO-8859-1");  

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*public void parseToJSON(String fileName) throws IOException,SAXException,TikaException{
		
		fileWriter = new FileWriter(fileName+".json");
		//BodyContentHandler handler = new BodyContentHandler();
        //InputStream stream = JSONTableContentHandler.class.getResourceAsStream("/mnt/572/TikaSearch/TSVParser/test1.xhtml");
        InputStream stream =new FileInputStream(fileName+".xhtml");
        //AutoDetectParser parser=new AutoDetectParser();
        //Metadata metadata=new Metadata();
        //XHTMLContentHandler handler=new XHTMLContentHandler(new BodyContentHandler(), metadata);
        //ContentHandler handler = new BodyContentHandler(new ToXMLContentHandler());
        SAXParserFactory factory=SAXParserFactory.newInstance();
        SAXParser parser;
		try {
			parser = factory.newSAXParser();
			//parser.parse(stream, handler, metadata);
			parser.parse(stream, this);
		} 
        
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}*/
	
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
		if(headerPos  < 20 && currentElemPos == -1){
			headers.add(sb.toString());
		}else{
			try {
				if(currentElemPos == 20){
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
