package org.apache.tika.parser.tsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

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

public class TSVParser extends AbstractParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Set<MediaType> SUPPORTED_TYPES = Collections
			.singleton(MediaType.text("tab-separated-values"));
	public static final String TSV_MIME_TYPE = "text/tab-separated-values";

	public Set<MediaType> getSupportedTypes(ParseContext context) {
		return SUPPORTED_TYPES;
	}

	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata, ParseContext context) throws IOException,
			SAXException, TikaException {
		
		metadata.set(Metadata.CONTENT_TYPE, TSV_MIME_TYPE);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(stream, "ISO-8859-1"));
			String line=null;
			if((line = br.readLine()) != null){
				XHTMLContentHandler xhtml = new XHTMLContentHandler(new WriteOutContentHandler(), metadata);
				writeBody(xhtml, handler, metadata, line);
				//System.out.println(xhtml.toString());

				String test=xhtml.toString();
				InputStream stream1 = new ByteArrayInputStream(test.getBytes("ISO-8859-1"));
				 SAXParserFactory factory=SAXParserFactory.newInstance();
			     SAXParser parser;
			     Metadata metadata1=new Metadata();
					JSONTableContentHandler jsonHandler = new JSONTableContentHandler("test");
					
					try {
						parser = factory.newSAXParser();
						//parser.parse(stream, handler, metadata);
						parser.parse(stream1, jsonHandler);
					} 
					
				//parser.parse(stream, jsonHandler, metadata1);
				//metadata.se
				//XHTMLContentHandler XHTMLhandler = new XHTMLContentHandler(jsonHandler, metadata);
				//Parser.parse(xhtml, handler, metadata);
				//json.parseToJSON(fileIndex+"_"+fileName);
					catch(Exception e)
					{
						e.printStackTrace();
					}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeBody(XHTMLContentHandler xhtml, ContentHandler handler, Metadata metadata,
			String line) throws SAXException {
		
		xhtml.startElement("html");
		xhtml.startElement("body");
		xhtml.startElement("table");
		
		xhtml.startElement("tr");
		
		String[] job=line.split("\t");
		for(int j=0;j<job.length;j++)
		{
			xhtml.startElement("td");
			xhtml.characters(job[j]);
					
			xhtml.endElement("td");
		}
		
		xhtml.endElement("tr");
		xhtml.endElement("table");
		xhtml.endElement("body");
		xhtml.endElement("html");
	}	
}
