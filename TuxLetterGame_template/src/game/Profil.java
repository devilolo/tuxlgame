/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import xml.XMLUtil;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author lemairlo
 */
@XmlRootElement(name = "profil")

@XmlAccessorType(XmlAccessType.FIELD)
public class Profil {
    
    @XmlAttribute(name="xmlns")
    String xmlns ="http://myGame/tux";
    @XmlAttribute(name="xmlns:xsi")
    private String xsi = "http://www.w3.org/2001/XMLSchema-instance";
    @XmlAttribute(name="xsi:schemaLocation")
    private String schemaLocation ="http://myGame/tux ./profil.xsd ";
    
    @XmlElement(name = "nom")
    private String nom;
    @XmlElement(name = "avatar")
    private String avatar;
    @XmlElement(name = "anniversaire")
    private String dateNaissance;
    @XmlElementWrapper
    @XmlElement(name = "parties")
    private ArrayList<Partie> parties;
    @XmlTransient
    public Document _doc;

    public Profil() {
    }

    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = profileDateToXmlDate(dateNaissance);
        this.avatar = "avatar/"+nom + ".png";
        parties = new ArrayList<>();
    }

    // Cree un DOM à partir d'un fichier XML
    public Profil(String nomFichier) {
        _doc = fromXML(nomFichier);
        parties = new ArrayList<>();
        // parsing à compléter
        //on transforme les dates en format prfil
        

        for (int i = 0; i < _doc.getElementsByTagName("partie").getLength(); i++) {
            Element elem;
            elem = (Element) _doc.getElementsByTagName("partie").item(i);
            elem.setAttribute("date", xmlDateToProfileDate(elem.getAttribute("date")));
        }
    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajouterPartie(Partie p) {

        parties.add(p);
        //on rajoute egalement la partie dans le dom appel a getpartie de class partie

    }

    public int getDernierNiveau() {
        Partie part = parties.get(parties.size() - 1);
        return part.getNiveau();
    }

    @Override
    public String toString() {
        return null;

    }

    public void sauvegarder(String filename) {
        //sauvegarder les parties ici
        Partie part = parties.get(parties.size() - 1);
        part.getPartie(_doc);

        //on retransform les dates
        for (int i = 0; i < _doc.getElementsByTagName("partie").getLength(); i++) {
            Element elem;
            elem = (Element) _doc.getElementsByTagName("partie").item(i);
            elem.setAttribute("date", profileDateToXmlDate(elem.getAttribute("date")));
        }
        toXML(filename);
    }

    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }

    public Boolean charge(String name) {
        File f = new File(name);
        return f.exists();

    }

    public Element chercherPartie(String date) {
        Element tmp;
        for (int i = 0; i < _doc.getElementsByTagName("partie").getLength(); i++) {
            tmp = (Element) _doc.getElementsByTagName("partie").item(i);
            if (tmp.getAttribute("date").equals(date)) {
                System.out.println("oioi");
                return tmp;
            }
        }
        return null;
    }

}
