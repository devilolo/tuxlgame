//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.5-2 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2018.11.30 à 10:32:39 PM CET 
//


package fr.ujf_grenoble.l3miage.medical;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Cabinet complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Cabinet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="adresse" type="{http://www.ujf-grenoble.fr/l3miage/medical}Adresse"/>
 *         &lt;element name="infirmiers" type="{http://www.ujf-grenoble.fr/l3miage/medical}Infirmiers" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="patients" type="{http://www.ujf-grenoble.fr/l3miage/medical}Patients"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cabinet", propOrder = {
    "nom",
    "adresse",
    "infirmiers",
    "patients"
})
@XmlRootElement(name = "cabinet")
public class Cabinet {

    @XmlElement(required = true)
    protected String nom;
    @XmlElement(required = true)
    protected Adresse adresse;
    protected List<Infirmiers> infirmiers;
    @XmlElement(required = true)
    protected Patients patients;

    /**
     * Obtient la valeur de la propriété nom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit la valeur de la propriété nom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * Obtient la valeur de la propriété adresse.
     * 
     * @return
     *     possible object is
     *     {@link Adresse }
     *     
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * Définit la valeur de la propriété adresse.
     * 
     * @param value
     *     allowed object is
     *     {@link Adresse }
     *     
     */
    public void setAdresse(Adresse value) {
        this.adresse = value;
    }

    /**
     * Gets the value of the infirmiers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infirmiers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfirmiers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Infirmiers }
     * 
     * 
     */
    public List<Infirmiers> getInfirmiers() {
        if (infirmiers == null) {
            infirmiers = new ArrayList<Infirmiers>();
        }
        return this.infirmiers;
    }

    /**
     * Obtient la valeur de la propriété patients.
     * 
     * @return
     *     possible object is
     *     {@link Patients }
     *     
     */
    public Patients getPatients() {
        return patients;
    }

    /**
     * Définit la valeur de la propriété patients.
     * 
     * @param value
     *     allowed object is
     *     {@link Patients }
     *     
     */
    public void setPatients(Patients value) {
        this.patients = value;
    }

}
