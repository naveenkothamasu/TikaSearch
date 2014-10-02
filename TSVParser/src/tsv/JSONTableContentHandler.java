package tsv;

import java.io.FileInputStream;
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

public class JSONTableContentHandler extends BodyContentHandler{
	
	String json=null;
	private ArrayList<String> headers = new ArrayList<String>();
	
	private static int currentElemPos = 0;
	
	private int headerPos = 0;
	public JSONTableContentHandler(ContentHandler delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void parseToJSON(String xhtml) throws IOException,SAXException,TikaException{
		
		//BodyContentHandler handler = new BodyContentHandler();
        //InputStream stream = JSONTableContentHandler.class.getResourceAsStream("/mnt/572/TikaSearch/TSVParser/test1.xhtml");
        InputStream stream =new FileInputStream("/mnt/572/TikaSearch/TSVParser/test1.xhtml");
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
	}
	
	@Override
	public void startDocument() {
		System.out.println("{\"job details\": [{");
	}
	public void endDocument() throws SAXException {
		System.out.println("}]}");
	}
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		if(headerPos == 21){
			System.out.println("\""+ headers.get(currentElemPos)+"\":");
			++currentElemPos;
		}
		if(headerPos==20)
		{
			headerPos++;
		}
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
		if(headerPos  < 20){
			headers.add(sb.toString());
			headerPos++;
		}else{
			if(currentElemPos == 20){
				System.out.println("\""+sb.toString()+"\"");
			}else{
				System.out.println("\""+sb.toString()+"\",");
			}
		}
		
	}
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		//System.out.println(",");
	}
}
