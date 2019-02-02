<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <link rel="stylesheet" href="css/style.css"/>
                <title>fiche_patient.xsl</title>
            </head>
            <body>
                <h1>
                    Bonjour <xsl:value-of select="//nom"/> <xsl:value-of select="//prénom"/>
                </h1>
                <p>
                    Née en <xsl:value-of select="//naissance"/>
                    <br/>
                    De sexe <xsl:value-of select="//sexe"/>
                    <br/>
                    Numéro de securité sociale <xsl:value-of select="//numéroSS"/>
                    <br/>
                </p>
                <table>
                    <tr>
                    <th>Date</th>
                    <th>Intervenant</th>
                    <th>Actes</th>
                    </tr>
                    <xsl:apply-templates select="//visite"/>
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="visite">
        <tr>
            <td>
                visite du <xsl:value-of select="@date"/>
            </td>
            <td>
                <xsl:apply-templates select="intervenant"/>
            </td>
            <td>
                <ul>
                    <xsl:apply-templates select="acte"/>
                </ul>
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template match="intervenant">
        <xsl:value-of select="prénom"/>
        <br/>
        <xsl:value-of select="nom"/>      
    </xsl:template>
    
    
    <xsl:template match="acte">
        <li>
            <xsl:value-of select="."/> 
        </li>    
    </xsl:template>
</xsl:stylesheet>
