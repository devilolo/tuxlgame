/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import xml.BrowserUtil;

/**
 *
 * @author loic
 */
public class AfficherProfil {

    private String chemin;

    public AfficherProfil(String chemin) {
        if(chemin==null){
            this.chemin="src/Profil/null.html";
        }else{
             this.chemin = chemin;
        }
       
    }

    public void openProfil() {
        BrowserUtil.launch(chemin);
    }

}
