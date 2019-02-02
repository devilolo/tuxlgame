
import game.Dico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author loic
 */
public class dicoTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Dico dico = new Dico("../dicoxml/dico.xml");
        dico.ajouteMotADico(1, "bonjour");
        dico.ajouteMotADico(1, "ton");
        dico.ajouteMotADico(1, "vfdv");
        dico.ajouteMotADico(1, "gh");
        for (int i = 0; i < 10; i++) {
            System.out.println(dico.getMotDepuisListeNiveaux(1));
            System.out.println(dico.getMotDepuisListeNiveaux(i));
        }
    }

}
