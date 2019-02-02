/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author lemairlo
 */
public class Partie {

    private String date;
    private String mot;
    private int niveau;
    private int trouvé;
    private int temps;

    public Partie(String date, String mot, int niveau) {
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
    }

    public Partie(Element partieElt) {
        //on va recuperer dans l'element tout ce qui es stocké et les mettre dans les elements correspondants
        this.date = partieElt.getAttribute("date");
        Element elemmot;
        elemmot = (Element) partieElt.getElementsByTagName("mot").item(0);
        this.mot = elemmot.getTextContent();
        this.niveau = Integer.parseInt(elemmot.getAttribute("niveau"));
        /* this.temps = Integer.parseInt(partieElt.getElementsByTagName("temps").item(0).getTextContent());
        String trouve = partieElt.getAttribute("trouvé");
        this.trouvé = Integer.parseInt(trouve.replaceAll("%", ""));*/
    }

    public Element getPartie(Document doc) {
        //a revoir pas compris je crois il faut creer le bloc partie avec nos info
        Element parties = (Element) doc.getElementsByTagName("parties").item(0);
        parties.appendChild(doc.createElement("partie"));
        parties = (Element) parties.getLastChild();
        parties.setAttribute("date", this.date);
        parties.setAttribute("trouvé", this.trouvé + "%");
        parties.appendChild(doc.createElement("temps"));
        parties.getElementsByTagName("temps").item(0).setTextContent(this.temps + "");
        parties.appendChild(doc.createElement("mot"));
        parties.getElementsByTagName("mot").item(0).setTextContent(this.mot);
        Element mot = (Element) parties.getElementsByTagName("mot").item(0);
        mot.setAttribute("niveau", this.niveau + "");
        return parties;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setTrouve(int nbrLettreRestantes) {
        //nblettre / taillemot *100
        this.trouvé = (int) (((double) (mot.length() - nbrLettreRestantes) / (double) mot.length()) * 100);
        //ya un truc qui vas pas dans le sujet return (nbrLettreRestantes/mot.length())*100;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public String toString() {
        return null;

    }

}
