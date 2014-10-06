package org.apache.tika.parser.tsv;

import java.util.TimeZone;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;

import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;

import java.util.Set;

import org.apache.tika.sax.WriteOutContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import java.util.Calendar;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import java.util.Locale;
import java.util.TimeZone;


import org.apache.tika.parser.tsv.JSONTableContentHandler;

public class TSVParser extends AbstractParser {

	private static final long serialVersionUID = 1L;

	private static final Set<MediaType> SUPPORTED_TYPES = Collections
			.singleton(MediaType.text("tab-separated-values"));
	public static final String TSV_MIME_TYPE = "text/tab-separated-values";

	public Set<MediaType> getSupportedTypes(ParseContext context) {
		return SUPPORTED_TYPES;
	}

	private String fileName = null;
	private int fileIndex = 0;

	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata, ParseContext context, int fileIndex, String fileName)
			throws IOException, SAXException, TikaException {

		this.fileName = fileName;
		this.fileIndex = fileIndex;
		this.parse(stream, handler, metadata, context);
	}

	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata, ParseContext context) throws IOException,
			SAXException, TikaException {

		metadata.set(Metadata.CONTENT_TYPE, TSV_MIME_TYPE);
		try {

			String line = null;
			File fileDirs = new File("tempinput.txt");

			BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileDirs), "UTF-8"));
			this.fileName = br.readLine();
			String[] val=this.fileName.split("/");
			this.fileName=val[val.length-1];
			System.out.println("fileName " + fileName);
			br.close();
			br = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			
			
			
			this.fileIndex = 0;
			//this.fileName = Long.toString(file);
			while ((line = br.readLine()) != null) {
				if(line.trim().equals(""))
					continue;
				line = line.replaceAll("&", "");
				XHTMLContentHandler xhtml = new XHTMLContentHandler(new WriteOutContentHandler(),
						metadata);

				writeBody(xhtml, handler, metadata, line);
				this.fileIndex += 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeBody(XHTMLContentHandler xhtml, ContentHandler handler,
			Metadata metadata, String line) throws SAXException, IOException,
			TikaException {
		xhtml.startDocument();
		
		
		// xhtml.startElement("html");
		// xhtml.startElement("body");
		xhtml.characters("<body>");
		
		xhtml.characters("<table>");
		xhtml.characters("<tr>");
		String col = null;
		File fileDirs = new File("/mnt/572/TikaSearch/col.txt");
		//BufferedReader br = new BufferedReader(new FileReader("/mnt/572/TikaSearch/col.txt"));
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileDirs), "UTF-8"));
		
		//BufferedReader br = new BufferedReader(new FileReader("/mnt/572/TikaSearch/col.txt"));
		while ((col = br.readLine()) != null) {
			xhtml.characters("<th>" + col + "</th>");
			
		}
		br.close();
		
		xhtml.characters("</tr>");
		
		xhtml.characters("<tr>");

		String[] job = line.split("\t");
		for (int j = 0; j < job.length; j++) {
			xhtml.characters("<td>");
			
			if (job[j].trim().equals("")) {
				
				xhtml.characters("--N/A--");// FIXME:
			} else {
				xhtml.characters(job[j]);
				
			}

			xhtml.characters("</td>");
			
		}

		xhtml.characters("</tr>");
		xhtml.characters("</table>");
		xhtml.characters("</body>");
		
		xhtml.endDocument();
		
	

		
		String test="<?xml version='1.0' encoding='UTF-8' ?>"+xhtml.toString();
		
		
		//InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
		InputStream stream = new ByteArrayInputStream(test.getBytes(Charset.forName("UTF-8")));
		//HtmlParser parser=new HtmlParser();
		SAXParserFactory factory=SAXParserFactory.newInstance();
	    	SAXParser parser;
	    	//Metadata metadata1=new Metadata();
		JSONTableContentHandler jsonHandler = new JSONTableContentHandler(this.fileIndex+"_"+this.fileName+".json");
			
			try {
				parser = factory.newSAXParser();
				parser.parse(stream, new XHTMLContentHandler(jsonHandler, metadata));
				//parser.parse(stream, jsonHandler,metadata1);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		//parser.parse(stream, jsonHandler, metadata1);
		//metadata.se
		//XHTMLContentHandler XHTMLhandler = new XHTMLContentHandler(jsonHandler, metadata);
		//Parser.parse(xhtml, handler, metadata);
		//json.parseToJSON(fileIndex+"_"+fileName);
	}
}

