/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author loic
 */
public class JeuDevineLeMotOrdre extends Jeu {

    int nbLettresRestantes;
    Chronometre chrono;
    private int temps = 50;

    public JeuDevineLeMotOrdre() {
        super();
        chrono = new Chronometre(temps);

    }

    @Override
    protected void démarrePartie(Partie partie) {
        //initiliser le chrono
        chrono.start();
        this.nbLettresRestantes = letters.size();

    }

    @Override
    protected void appliqueRegles(Partie partie) {
        //tant que le chrono n'est pas a zero et on a pas trouvé toutes les lettres on continue 
        if (chrono.remainsTime() <= 0 || nbLettresRestantes == 0) {
            //c'est la fin            
            super.finished = true;
            /* } else if (tuxTrouveLettre()) {
            nbLettresRestantes--;
        }*/}else{
            //ici la regle si la lettre a été trouvé elle disparait si une mauvaise lettre est touché on recommence a zero
            for (int i=(letters.size() - nbLettresRestantes) ; i < letters.size() ; i++) {
               if(collision(letters.get(i))){
                   if(i==letters.size() - nbLettresRestantes){
                       env.removeObject(letters.get(i));
                       nbLettresRestantes--;
                   }else{
                       for (int j=0 ; j < LettreNumero() ; j++) {
                           env.addObject(letters.get(j));
                       }
                       nbLettresRestantes=letters.size();
                   }
               }
            }
        }
    }

    @Override
    protected void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTemps(getTemps() - chrono.remainsTime());
        partie.setTrouve(nbLettresRestantes);
    }

    private Boolean tuxTrouveLettre() {
        return collision(letters.get(letters.size() - nbLettresRestantes));
    }

    //renvoie le nombre de lettre restante
    private int getNbLettresRestantes() {
        return nbLettresRestantes;

    }
    
    //renvoie le numero de la lettre actuellement recherché
    private int LettreNumero(){
       return letters.size() - nbLettresRestantes;
    }

    private int getTemps() {
        return temps;
    }

}
