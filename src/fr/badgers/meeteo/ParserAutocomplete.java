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

public class ParserAutocomplete {
	static public ParserAutocomplete parser;
	
	private static URL url = null;
	//private DefaultHandler handler;
	
	public ParserAutocomplete(String urlString){
	}
	
	public static ArrayList getData(String urlString) throws IOException{
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
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		DefaultHandler handler = new ParserXMLHandlerAutocomplete();
		try {
			// On parse le fichier XML
			InputStream input = url.openStream();
			if(input==null)
				Log.e("erreur android","null");
			else{
				parser.parse(input, handler);
				// On récupère directement la liste des feeds
				entries = ((ParserXMLHandlerAutocomplete) handler).getData();
			}
		} catch (SAXException e) {
			Log.e("meeteo", "FUUUU1");
			e.printStackTrace();
		}
		return entries;
	}
}
