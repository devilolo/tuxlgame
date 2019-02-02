
package fr.ujf_grenoble.l3miage.medical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getStrLength complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getStrLength"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getStrLength", propOrder = {
    "mot"
})
public class GetStrLength {

    protected String mot;

    /**
     * Obtient la valeur de la propriété mot.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMot() {
        return mot;
    }

    /**
     * Définit la valeur de la propriété mot.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMot(String value) {
        this.mot = value;
    }

}
