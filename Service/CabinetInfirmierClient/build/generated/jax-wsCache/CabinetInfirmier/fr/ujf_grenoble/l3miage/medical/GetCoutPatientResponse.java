
package fr.ujf_grenoble.l3miage.medical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getCoutPatientResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getCoutPatientResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCoutPatientResponse", propOrder = {
    "_return"
})
public class GetCoutPatientResponse {

    @XmlElement(name = "return")
    protected double _return;

    /**
     * Obtient la valeur de la propriété return.
     * 
     */
    public double getReturn() {
        return _return;
    }

    /**
     * Définit la valeur de la propriété return.
     * 
     */
    public void setReturn(double value) {
        this._return = value;
    }

}
