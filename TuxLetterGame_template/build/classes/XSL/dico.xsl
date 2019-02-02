<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tux="http://myGame/tux"
                version="1.0">
    <xsl:output method="html"/>

   
    <xsl:template match="/">
        <html>
            <head>
                <title>dico.xsl</title>
            </head>
            <body>
                <h1> Dictionnaire </h1>
              <ul>
                
                <xsl:apply-templates select="//tux:mot">
                    <xsl:sort select="@niveau" order="ascending"/>
                    <xsl:sort select="." order="ascending"/>
                </xsl:apply-templates>
              </ul>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="tux:mot">
        <li>
            <xsl:value-of select="."/>
        </li>
    </xsl:template>
    
</xsl:stylesheet>
