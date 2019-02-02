<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:vds='http://www.ujf-grenoble.fr/l3miage/medical'
                xmlns:act='http://www.ujf-grenoble.fr/l3miage/actes'
                version="1.0">
    <xsl:output method="html"/>

    <xsl:param name="destinedId" select="001"/>
    <xsl:param name="visitesDuJour" select="//patients/patient/visite[@intervenant=$destinedId]"/>
    <xsl:variable name="actes" select="document('D:/programmation/LW/Service/CabinetInfirmierServer/src/java/fr/ujf_grenoble/l3miage/medical/resources/actes.xml', /)/act:ngap"/>
    <xsl:template match="/">
        <html>
            <head>
                <link rel="stylesheet" href="css/style.css"/>
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"/>
                <script type="text/javascript" src="js/facture.js"></script> 
                <script type="text/javascript">     
                <![CDATA[               
                    function openFacture(prénom, nom, actes) {
                    var width  = 500;
                    var height = 300;
                    if(window.innerWidth) {
                    var left = (window.innerWidth-width)/2;
                    var top = (window.innerHeight-height)/2;
                    }
                    else {
                    var left = (document.body.clientWidth-width)/2;
                    var top = (document.body.clientHeight-height)/2;
                    }
                    var factureWindow = window.open('','facture','menubar=yes, scrollbars=yes, top='+top+', left='+left+', width='+width+', height='+height+'');
                    var factureText = afficherFacture(prénom, nom, actes);
                    factureWindow.document.write(factureText);
                    } 
                ]]>                            
                </script>               
                <title>Emploi du temps</title>
            </head>
            <body>
                <h1>
                    Bonjour <xsl:value-of select="//infirmiers/infirmier[@id=$destinedId]/prénom"/>
                </h1>
                <h1>
                    Aujourd'hui, vous avez <xsl:value-of select="count($visitesDuJour)"/> patients
                </h1>
                <table>
                    <tr>
                        <th>
                            nom
                        </th>
                        <th>
                            adresse
                        </th>
                        <th>
                            intervention
                        </th>
                        <th>
                            Facture
                        </th>
                    </tr>
                    <xsl:apply-templates select="$visitesDuJour/.."/>
                </table>
            </body>
        </html>
    </xsl:template>

    <!-- on souhaite lister pour chaque patient
    à visiter (et dans l'ordre de visite), son nom, son adresse correctement 
    mise en forme et la liste des soins à effectuer. -->

    <xsl:template match="patient">
        <tr>
            <td>
                <xsl:value-of select="nom"/>  
            </td>
            <td>
                étage:<xsl:value-of select="adresse/étage"/>
                <br/>
                <xsl:value-of select="adresse/numéro"/>  
                <xsl:value-of select="adresse/rue"/> 
                <br/>
                <xsl:value-of select="adresse/codePostal"/> 
                <xsl:value-of select="adresse/ville"/> 
            </td>
            <td> 
                <ul>    
                    <xsl:apply-templates select="visite/acte"/>
                </ul>
            </td>
            <td>
                <xsl:element name="button">
                    <xsl:attribute name="class">btn btn-outline-success btn-block</xsl:attribute>
                    <xsl:variable name="Actes" select="visite//acte/@id"/>
                    <xsl:attribute name="onclick">openFacture('<xsl:value-of select="prénom"/>','<xsl:value-of select="nom"/>','<xsl:value-of select="$Actes"/>')</xsl:attribute> 
                    facture 
                </xsl:element>
            </td>
        </tr>
    
    </xsl:template>
    
    
    <xsl:template match="acte">
        <xsl:param name="valueId" select="@id"/>
        <li>
            <xsl:value-of select="$actes//act:actes/act:acte[@id=$valueId]"/>
        </li>
    </xsl:template>
    
    
</xsl:stylesheet>
