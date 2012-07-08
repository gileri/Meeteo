package fr.badgers.meeteo;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("unused")
public class ParserXMLHandlerForecast extends DefaultHandler {

	private final String CURR = "current_observation";
	private final String TEMP_C = "temp_c";
	private final String URLIMAGE = "icon_url";
	private final String WEATHER = "weather";

	private ArrayList<Condition> entries;

	private Condition currentWeather;

	// Boolean to know if we're in an item
	private boolean inCurr;
	private boolean inTemp;

	// Buffer for data in XML tag
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXMLHandlerForecast() {
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
		entries = new ArrayList<Condition>();
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

		// Ci dessous, localName contient le nom du tag rencontré

		// Nous avons rencontré un tag ITEM, il faut donc instancier un nouveau
		// feed
		if (localName.equalsIgnoreCase(CURR)) {
			inCurr = true;
			currentWeather = new Condition();
		}
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

		if (localName.equalsIgnoreCase(TEMP_C)) {
			currentWeather.setTemperature(Float.valueOf(buffer.toString()));
			inTemp = false;
		}
		
		if (localName.equalsIgnoreCase(URLIMAGE))
		{
			currentWeather.setImageUrlString(buffer.toString());
		}
		
		if (localName.equalsIgnoreCase(WEATHER))
		{
			currentWeather.setDescription(buffer.toString());
		}

		if (localName.equalsIgnoreCase(CURR)) {
			inCurr = false;
			this.entries.add(currentWeather);
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
	public ArrayList<Condition> getData() {
		return entries;
	}
}
