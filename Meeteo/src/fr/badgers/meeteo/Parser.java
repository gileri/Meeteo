package fr.badgers.meeteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class Parser {
	static public Parser parser;
	
	public Parser(){
		
	}
	
	public static ArrayList getData(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList entries = null;
		try{
			parser = factory.newSAXParser();
			
		}
		catch(ParserConfigurationException e){
			e.printStackTrace();
		}
		catch(SAXException e){
			e.printStackTrace();
		}
		
		URL url = null;
		try {
			url = new URL("http://api.yr.no/weatherapi/locationforecast/1.8/?lat=60.10;lon=9.58;msl=70");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		DefaultHandler handler = new ParserXMLHandler();
		try {
			// On parse le fichier XML
			InputStream input = url.openStream();
			if(input==null)
				Log.e("erreur android","null");
			else{
				parser.parse(input, handler);
				// On récupère directement la liste des feeds
				entries = ((ParserXMLHandler) handler).getData();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return entries;
	}
}
