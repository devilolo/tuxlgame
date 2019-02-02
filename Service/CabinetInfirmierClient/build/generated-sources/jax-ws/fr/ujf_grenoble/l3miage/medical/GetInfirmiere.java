
package fr.ujf_grenoble.l3miage.medical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getInfirmiere complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getInfirmiere"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="infirmierId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInfirmiere", propOrder = {
    "infirmierId"
})
public class GetInfirmiere {

    protected String infirmierId;

    /**
     * Obtient la valeur de la propriété infirmierId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfirmierId() {
        return infirmierId;
    }

    /**
     * Définit la valeur de la propriété infirmierId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfirmierId(String value) {
        this.infirmierId = value;
    }

}
