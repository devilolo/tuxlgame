/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author lemairlo
 */
public class Dico extends DefaultHandler {

    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;
    private StringBuffer buffer;
    private int niveau;
    private Boolean isWord;

    public Dico(String cheminFichierDico) {
        super();
        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList<String>();
        listeNiveau2 = new ArrayList<String>();
        listeNiveau3 = new ArrayList<String>();
        listeNiveau4 = new ArrayList<String>();
        listeNiveau5 = new ArrayList<String>();
        buffer = new StringBuffer();
        isWord = false;
    }

    public String getMotDepuisListeNiveaux(int niveau) {
        switch (vérifieNiveau(niveau)) {
            case 5:
                return getMotDepuisListe(listeNiveau5);
            case 4:
                return getMotDepuisListe(listeNiveau4);
            case 3:
                return getMotDepuisListe(listeNiveau3);
            case 2:
                return getMotDepuisListe(listeNiveau2);
            case 1:
                return getMotDepuisListe(listeNiveau1);
            default:
                return null;
        }

    }

    public void ajouteMotADico(int niveau, String mot) {
        switch (vérifieNiveau(niveau)) {
            case 5:
                listeNiveau5.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 1:
                listeNiveau1.add(mot);
                break;
            default:
                break;
        }
    }

    public String getCheminFichierDico() {
        return cheminFichierDico;

    }

    private int vérifieNiveau(int niveau) {
        return (niveau % 5 + 1);
    }

    private String getMotDepuisListe(ArrayList<String> list) {
        if (list.isEmpty()) {
            return "null";
        } else {
            return list.get((int) (Math.random() * list.size()));
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("dictionnaire")) {
            //si le nom de balise donné = dictionnaire on a rien a faire
            isWord = false;
        } else if (qName.equals("mot")) {
            //si le nom de balise donné = mot
            isWord = true;
            try {
                //on cherche a recupérer le niveau
                niveau = Integer.parseInt(attributes.getValue("niveau"));
                //besoin d'enlever tout ce qui pourrait etre devant tabulation et autre
                buffer.delete(0, buffer.length());
            } catch (Exception e) {
                //erreur, le contenu de id n'est pas un entier 
                throw new SAXException(e);
            }
            //inPersonne = true;
        } else {
            isWord = false;
            throw new SAXException("Balise " + qName + " inconnue.");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("dictionnaire")) {
            //si le nom de balise donné = dictionnaire
            isWord = false;
        } else if (qName.equals("mot")) {
            //si le nom de balise donné = mot
            String s = buffer.toString();
            ajouteMotADico(niveau, s);
            isWord = false;
        } else {
            isWord = false;
            //erreur, on peut lever une exception 
            throw new SAXException("Balise " + qName + " inconnue.");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String lecture = new String(ch, start, length);
        if (buffer != null) {
            buffer.append(lecture);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Début du parsing");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Fin du parsing");
        System.out.println("Resultats du parsing");
    }

    public void lireDictionnaire() {
        try {
            // création d'une fabrique de parseurs SAX 
            SAXParserFactory fabrique = SAXParserFactory.newInstance();

            // création d'un parseur SAX 
            SAXParser parseur = fabrique.newSAXParser();

            // lecture d'un fichier XML avec un DefaultHandler 
            File fichier = new File(cheminFichierDico);
            /* Dico gestionnaire;
            gestionnaire = new Dico(cheminFichierDico);*/
            parseur.parse(fichier, this);

        } catch (ParserConfigurationException pce) {
            System.out.println("Erreur de configuration du parseur");
            System.out.println("Lors de l'appel à newSAXParser()");
        } catch (SAXException se) {
            System.out.println("Erreur de parsing");
            System.out.println("Lors de l'appel à parse()");
        } catch (IOException ioe) {
            System.out.println("Erreur d'entrée/sortie");
            System.out.println("Lors de l'appel à parse()");
        }
    }
}
