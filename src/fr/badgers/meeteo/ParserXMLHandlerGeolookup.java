package fr.badgers.meeteo;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserXMLHandlerGeolookup extends DefaultHandler {

	private final String ERROR = "error";
	private final String LINK = "l";
	private final String CITY = "city";
	private final String COUNTRY= "country";
	private boolean inLocation = false;
	
	private ArrayList<Location> entries;

	private Location currentLocation;

	private boolean error;

	// Buffer for data in XML tag
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXMLHandlerGeolookup() {
		super();
	}

	// * Cette méthode est appelée par le parser une et une seule
	// * fois au démarrage de l'analyse de votre flux xml.
	// * Elle est appelée avant toutes les autres méthodes de l'interface,
	// * à l'exception unique, évidemment, de la méthode setDocumentLocator.
	// * Cet événement devrait vous permettre d'initialiser tout ce qui doit
	// * l'être avant ledébut du parcours du document.

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		entries = new ArrayList<Location>();
	}

	/*
	 * Fonction étant déclenchée lorsque le parser trouve un tag XML C'est cette
	 * méthode que nous allons utiliser pour instancier un nouveau feed
	 */

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		// Nous réinitialisons le buffer a chaque fois qu'il rencontre un item
		buffer = new StringBuffer();

		if (localName.equalsIgnoreCase(ERROR)) {
			error = true;
		}
		
		if(localName.equalsIgnoreCase("location")) {
			currentLocation = new Location();
			inLocation = true;
		}
		
		if (localName.equalsIgnoreCase("nearby_weather_stations"))
			inLocation = false;
	}

	// * Fonction étant déclenchée lorsque le parser à parsé
	// * l'intérieur de la balise XML La méthode characters
	// * a donc fait son ouvrage et tous les caractère inclus
	// * dans la balise en cours sont copiés dans le buffer
	// * On peut donc tranquillement les récupérer pour compléter
	// * notre objet currentFeed

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		// if (localName.equalsIgnoreCase(TITLE)){
		// if(inItem){
		// // Les caractères sont dans l'objet buffer
		// this.currentFeed.setTitle(buffer.toString());
		// buffer = null;
		// }
		// }
		if (localName.equalsIgnoreCase(CITY) && inLocation)
		{
			currentLocation.setName(buffer.toString());
		}
		
		if (localName.equalsIgnoreCase(COUNTRY) && inLocation)
		{
			currentLocation.setCountry(buffer.toString());
		}
		
		if (localName.equalsIgnoreCase(LINK) && inLocation)
		{
			currentLocation.setLink(buffer.toString());
		}
		
		if (localName.equalsIgnoreCase("location")) {
			inLocation = false;
			entries.add(currentLocation);
		}
			
	}

	// * Tout ce qui est dans l'arborescence mais n'est pas partie
	// * intégrante d'un tag, déclenche la levée de cet événement.
	// * En général, cet événement est donc levé tout simplement
	// * par la présence de texte entre la balise d'ouverture et
	// * la balise de fermeture

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String lecture = new String(ch, start, length);
		if (buffer != null)
			buffer.append(lecture);
	}

	// cette méthode nous permettra de récupérer les données
	public ArrayList<Location> getData() {
		if (error)
			return null;
		return entries;
	}
}
