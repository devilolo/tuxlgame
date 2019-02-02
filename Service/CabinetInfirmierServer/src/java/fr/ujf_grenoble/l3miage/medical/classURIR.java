/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ujf_grenoble.l3miage.medical;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author loic
 */
public final class classURIR implements URIResolver {
    Map<String, String> documents = new HashMap<String, String>();

    public classURIR put(final String href, final String base) {
        documents.put(href, base);
        return this;
    }
    
    

    @Override
    public Source resolve(String string, String string1) throws TransformerException {
        final String s = documents.get(string);
        if (s != null) {
            return new StreamSource(new StringReader(string1));
        }
        return null;
    }
    
}
