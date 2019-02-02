<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:vds='http://www.ujf-grenoble.fr/l3miage/medical'
                xmlns:act='http://www.ujf-grenoble.fr/l3miage/actes'
                version="1.0">
    <xsl:output method="xml" indent="yes"/>
    
    <xsl:param name="destinedName" select="'Pien'" />
    <xsl:variable name="actes" select="document('actes.xml', /)/act:ngap"/>
    
    <xsl:template match="/">
        <xsl:element name="patient">                
            <xsl:apply-templates select="//vds:patients/vds:patient/vds:nom" />                         
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="vds:nom">
        <xsl:if test=".=$destinedName">
            <xsl:element name="nom">
                <xsl:value-of select="."/>
            </xsl:element>
            <xsl:element name="prénom">
                <xsl:value-of select="../vds:prénom"/>
            </xsl:element>
            <xsl:element name="sexe">
                <xsl:value-of select="../vds:sexe"/>
            </xsl:element>
            <xsl:element name="naissance">
                <xsl:value-of select="../vds:naissance"/>
            </xsl:element>
            <xsl:element name="numéroSS">
                <xsl:value-of select="../vds:numéro"/>
            </xsl:element>
            <xsl:element name="adresse">
                <xsl:apply-templates select="../vds:adresse"/>
            </xsl:element>
            <xsl:apply-templates select="../vds:visite"/>          
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="vds:adresse">
        <xsl:if test="vds:étage">
            <xsl:element name="étage">
                <xsl:value-of select="vds:étage"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="vds:numéro">
            <xsl:element name="numéro">
                <xsl:value-of select="vds:numéro"/>
            </xsl:element>
        </xsl:if>
        <xsl:element name="rue">
            <xsl:value-of select="vds:rue"/>
        </xsl:element>
        <xsl:element name="ville">
            <xsl:value-of select="vds:ville"/>
        </xsl:element>
        <xsl:element name="codePostal">
            <xsl:value-of select="vds:codePostal"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="vds:visite">
        <xsl:element name="visite" >
            <xsl:param name="ids" select="@intervenant"/>
            <xsl:attribute name="date">
                <xsl:value-of select="@date" />
            </xsl:attribute>
            <xsl:element name="intervenant" >
                <xsl:apply-templates select="//vds:infirmiers/vds:infirmier[@id=$ids]"/>
            </xsl:element>
                <xsl:apply-templates select="vds:acte"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="vds:infirmier">
        <xsl:element name="nom">
            <xsl:value-of select="vds:nom"/>
        </xsl:element>
        <xsl:element name="prénom">
            <xsl:value-of select="vds:prénom"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="vds:acte">
        <xsl:param name="idActe" select="@id"/>
        <xsl:element name="acte"><xsl:value-of select="$actes//act:actes/act:acte[@id=$idActe]"/></xsl:element>
    </xsl:template>
    

</xsl:stylesheet>
