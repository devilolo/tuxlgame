<?xml version="1.0" encoding="UTF-8"?>	
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tux="http://myGame/tux"
                version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Resultats</title>
                <link rel="stylesheet" href="style.css"/>
            </head>
            <body>
                <h1>
                    Resultat de <xsl:value-of select="/tux:profil/tux:nom"/>
                </h1>  
                <h2>
                    <xsl:element name="img">
                        <xsl:attribute name="src"><xsl:value-of select="tux:profil/tux:avatar"/></xsl:attribute>
                    </xsl:element>
                </h2>
                <table>
                    <th>Date de la partie</th><th>niveau</th><th>mot</th><th>temps</th><th>trouvé</th>
                    <xsl:apply-templates select="tux:profil/tux:parties/tux:partie"/>
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="tux:partie">        
        <tr>
            <td>
             <p/> Les parties <xsl:value-of select="@date"/> <p/> 
            </td>
            <td>
             <p/> <xsl:value-of select="tux:mot/@niveau"/> <p/> 
            </td>
            <td>
             <p/> <xsl:value-of select="tux:mot"/> <p/> 
            </td>
            <td>
             <p/> <xsl:value-of select="tux:temps"/> <p/> 
            </td>
            <td>
             <p/> <xsl:value-of select="@trouvé"/> <p/> 
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>


<!--A présent, nous voulons créer une page web permettant de montrer
proprement les résultats issus du profil d'un joueur. Nous pourrions 
imaginer créer ainsi, à l'aide de services web, une plateforme permettant
d'afficher, de partager et de contrôler des profils de ce jeu.

La page profil est la page permettant de voir toutes 
les parties d'un joueur, son avatar et son identité 
ainsi que ses scores.

-->