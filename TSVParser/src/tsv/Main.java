package tsv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

	public static void main(String[] args){
		

		try {
			InputStream stream = new FileInputStream("/mnt/572/TikaSearch/test.tsv");
			tsvParser parser = new tsvParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, new XHTMLContentHandler(new WriteOutContentHandler(), metadata), metadata, new ParseContext());
			
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
		}
		
		
	}
}
