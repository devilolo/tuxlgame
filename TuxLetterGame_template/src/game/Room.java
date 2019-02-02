/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import xml.XMLUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author lemairlo
 */
public class Room {

    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;

    public Room() {
        //initialisation du dom plateau.xml
        Document doc = fromXML("src/XML/plateau.xml");
        
        //lecture dans le dom
        this.depth = Integer.parseInt(doc.getElementsByTagName("depth").item(0).getTextContent());
        this.height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
        this.width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
        textureBottom = doc.getElementsByTagName("textureBottom").item(0).getTextContent();
        textureNorth = doc.getElementsByTagName("textureNorth").item(0).getTextContent();
        textureEast = doc.getElementsByTagName("textureEast").item(0).getTextContent();
        textureWest = doc.getElementsByTagName("textureWest").item(0).getTextContent();
    }

    //renvoie un docment dom avec le lien vers le fichier
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }

}
