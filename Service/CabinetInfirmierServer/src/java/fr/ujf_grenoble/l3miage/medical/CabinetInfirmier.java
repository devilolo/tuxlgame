/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ujf_grenoble.l3miage.medical;

import fr.ujf_grenoble.l3miage.medical.XMLUtil.DocumentFactory;
import fr.ujf_grenoble.l3miage.medical.XMLUtil.DocumentTransform;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author loic
 */
@WebService(serviceName = "CabinetInfirmier")
public class CabinetInfirmier {

    Map<String, Double> actes = new HashMap<String, Double>();
    
    public CabinetInfirmier() {
        createCodeCoutMap();
    }
      
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStrLength")
    public int getStrLength(@WebParam(name = "mot") String mot) {
        //TODO write your implementation code here:
        return mot.length();
    }

    /**
     * Cette méthode : - récupère le document XML contenant l'organisation
     * complète du cabinet infirmier en tant que ressource du Web Service -
     * réalise un unmarshalling à partir de cette ressource et du JAXB binding
     * produit à partir du XML Schema - renvoie une référence sur l'instance de
     * Cabinet, racine du système d'information
     *
     * @return racine du document XML (instance de la classe Cabinet)
     */
    private Cabinet fromResource() {
        // déclare un cabinet
        Cabinet cabinet = null;
        try {
            // récupération  de la ressource à partir du fichier XML
            URL cabinetXMLURL = CabinetInfirmier.class.getResource("resources/cabinet.xml");

            // création du contexte
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(Cabinet.class.getPackage().getName());

            // création d'un unmarshaller 
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();

            // unmarshalling... (utilisation d'un transtypage)
            cabinet = (Cabinet) unmarshaller.unmarshal(cabinetXMLURL); //NOI18N

        } catch (JAXBException ex) {
            Logger.getLogger(CabinetInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
        // retourne une instance de cabinet, ou null si le chargement du fichier a 
        return cabinet;
    }

    /**
     * Cette méthode du WS permet de récupérer une resource distante sous la
     * forme d'un flux source (StreamSource) pouvant être utilisée dans les
     * méthodes de XMLUtil (trandformation XSLT ...).
     *
     * @param resourceFile
     * @return
     */
    private StreamSource fromResource(String resourceFile) {
        // crée un flux d'entrée qui récupère la resource distante comme un flux
        InputStream resourceStream = this.getClass().getResourceAsStream(resourceFile);
        // Retourne ce flux d'entrée sous la forme d'un flux source
        return new StreamSource(resourceStream);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getNomCabinet")
    public String getNomCabinet() {
            Cabinet c = fromResource();
        return c.getNom();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getListePatientsStr")
    public String getListePatientsStr(@WebParam(name = "id") int id) {
        //TODO write your implementation code here:       
        try {
            Cabinet cab = new Cabinet();
            cab = fromResource();
            String listpatient = "Patients de l'infimiere [" + id + "]: ";
            Document doc = XMLUtil.DocumentFactory.fromObject(cab);
            NodeList nlistNom = XMLUtil.XPathEvaluateExpression.getNodeList("//patient/visite[@intervenant=" + id + "]/../nom", doc);
            NodeList nlistPrenom = XMLUtil.XPathEvaluateExpression.getNodeList("//patient/visite[@intervenant=" + id + "]/../prénom", doc);
            for (int i = 0; i < nlistNom.getLength(); i++) {
                listpatient += "[" + nlistNom.item(i).getTextContent();
                listpatient += "," + nlistPrenom.item(i).getTextContent() + "] ";
            }
            return listpatient;
        } catch (JAXBException | ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(CabinetInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CabinetInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHTML")
    public String getHTML() {
        try {
            // On récupère une instance de cabinet depuis la resource.
            Cabinet c = fromResource();

            // Traitements préliminaires éventuels ... en Java
            /*
            * ici sont réalisés les traitements souhaités pour récupérer les informations utiles
            * ex: classement des patients par ordre alphabétique
            * ex: classement de la liste des patients d'une infirmière selon la distance
             */
            // On fabrique un document DOM à partir d'un objet, ici un cabinet
            Document doc = DocumentFactory.fromObject(c);

            // On récupère ici la feuille de transformation de l'infirmière
            StreamSource xslSource = fromResource("resources/infirmiere.xsl"); // !!!!!!!!! ATTENTION, changer le nom si nécessaire
            // On fabrique une processeur de document (Transformer)
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);
            // On donne un nom au paramètre ; ce nom est celui utilisé dans la feuille de transformation
            String id = "idInfirmier";
            String infirmierId = "001";
            // On indique au processeur de document d'appliquer un paramètre auquel on donne sa valeur.
            transformer.setParameter(id, infirmierId);
            transformer.setURIResolver(new classURIR().put("actes", "D:/programmation/LW/Service/CabinetInfirmierServer/src/java/fr/ujf_grenoble/l3miage/medical/resources/actes.xml"));
            // On applique la transformation
            return DocumentTransform.fromTransformation(transformer, doc);
        } catch (TransformerException | JAXBException | ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(CabinetInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Cette opération du Web Service permet de récupérer la page HTML de
     * l'infirmier d'identifiant Id. L'instance de cabinet subit un marshalling
     * pour etre transformée en Document DOM. Avant ou après cette étape (qui
     * peut être remplacée), des traitements (ex: recherche opérationnelle)
     * peuvent être effectués. Le Document subit ensuite une transformation
     * selon une feuille de transformation xsl présente dans les resources.
     * Cette feuile permet une transformation du document DOM en page HTML.
     *
     * @param infirmierId : numéro (Id) de l'infirmier qui pourra être utilisé.
     * @return String : contenu du fichier HTML.
     */
    @WebMethod(operationName = "getInfirmiere")
    public String getInfirmiere(@WebParam(name = "infirmierId") String infirmierId) {
        try {
            // On récupère une instance de cabinet depuis la resource.
            Cabinet c = fromResource();

            // Traitements préliminaires éventuels ... en Java
            /*
            * ici sont réalisés les traitements souhaités pour récupérer les informations utiles
            * ex: classement des patients par ordre alphabétique
            * ex: classement de la liste des patients d'une infirmière selon la distance
             */
            // On fabrique un document DOM à partir d'un objet, ici un cabinet
            Document doc = DocumentFactory.fromObject(c);
            

            // On récupère ici la feuille de transformation de l'infirmière
            StreamSource xslSource = fromResource("resources/infirmiere.xsl"); // !!!!!!!!! ATTENTION, changer le nom si nécessaire
            // On fabrique une processeur de document (Transformer)
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);
            // On donne un nom au paramètre ; ce nom est celui utilisé dans la feuille de transformation
            String id = "idInfirmier";
            // On indique au processeur de document d'appliquer un paramètre auquel on donne sa valeur.
            transformer.setParameter(id, infirmierId);
            transformer.setURIResolver(new classURIR().put("actes", "D:/programmation/LW/Service/CabinetInfirmierServer/src/java/fr/ujf_grenoble/l3miage/medical/resources/actes.xml"));
            
            // On applique la transformation
            return DocumentTransform.fromTransformation(transformer, doc);
        } catch (TransformerException | JAXBException | ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(CabinetInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getCoutPatient")
    public double getCoutPatient(@WebParam(name = "id") int id) {
        //TODO write your implementation code here:
        return actes.get(""+id);
    }
    
    
    
    private void createCodeCoutMap(){
        Double AMIVAL = 3.15;
        Double AISVAL = 2.65;
        Double DIVAL = 10.0;
        try {
            Document Acte = XMLUtil.DocumentFactory.fromFile("D:/programmation/LW/Service/CabinetInfirmierServer/src/java/fr/ujf_grenoble/l3miage/medical/resources/actes.xml");
            NodeList actess = Acte.getElementsByTagName("acte");
            for(int i=0 ; i < actess.getLength() ; i++){
                Element attri= (Element)actess.item(i);
                String id = attri.getAttribute("id");
                String cle = attri.getAttribute("clé");
                Double cout = 0.0;
                if(cle.equals("AMI")){
                    cout = AMIVAL;
                }else if(cle.equals("DI")){
                    cout = DIVAL;
                }else if(cle.equals("AIS")){
                    cout = AISVAL;
                }               
                cout *= Double.parseDouble(attri.getAttribute("coef"));
                actes.put(id,cout);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(CabinetInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
