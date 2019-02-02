/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import xml.FileUtil;
import xml.XMLUtil;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    // Une énumération pour définir les choix de l'utilisateur
    enum MENU_VAL {

        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    Env env;
    Room mainRoom;
    Profil profil;
    Tux tux;
    Letter letter;
    ArrayList<Letter> letters;
    Boolean finished;
    Dico dico;
    private String Chemin;

    private final Room menuRoom;
    EnvText textNomJoueur;
    EnvText textMenuQuestion;
    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    EnvText textMenuJeu4;
    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;

    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);
        //nouveau dictionnaire
        dico = new Dico("src/XML/dico.xml");
        //lecture du dico
        dico.lireDictionnaire();

        //a changer
        // Textes affichés à l'écran
        textMenuQuestion = new EnvText(env, "Voulez vous ?", 200, 300);
        textMenuJeu1 = new EnvText(env, "1. Commencer une nouvelle partie ?", 250, 280);
        textMenuJeu2 = new EnvText(env, "2. Charger une partie existante ?", 250, 260);
        textMenuJeu3 = new EnvText(env, "3. Sortir de ce jeu ?", 250, 240);
        textMenuJeu4 = new EnvText(env, "4. Quitter le jeu ?", 250, 220);
        textNomJoueur = new EnvText(env, "Choisissez un nom de joueur : ", 200, 300);
        textMenuPrincipal1 = new EnvText(env, "1. Charger un profil de joueur existant ?", 250, 280);
        textMenuPrincipal2 = new EnvText(env, "2. Créer un nouveau joueur ?", 250, 260);
        textMenuPrincipal3 = new EnvText(env, "3. Sortir du jeu ?", 250, 240);
    }

    //Place les lettres dans letters
    private void initDico(String mot) {
        letters = new ArrayList<>();
        for (int i = 0; i < mot.length(); i++) {
            letter = new Letter(mot.charAt(i), (int) (Math.random() * mainRoom.getWidth()), (int) (Math.random() * mainRoom.getDepth()), env);
            letters.add(letter);
        }
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);

        //ici on vas afficher le profil du joueur sur une page internet pas fonctionnel a cause de http://MyGame/tux
        AfficherProfil aff = new AfficherProfil(Chemin);
        aff.openProfil();
        env.exit();
    }

    /**
     * Teste si une code clavier correspond bien à une lettre
     *
     * @return true si le code est une lettre
     */
    private Boolean isLetter(int codeKey) {
        return codeKey == Keyboard.KEY_A || codeKey == Keyboard.KEY_B || codeKey == Keyboard.KEY_C || codeKey == Keyboard.KEY_D || codeKey == Keyboard.KEY_E
                || codeKey == Keyboard.KEY_F || codeKey == Keyboard.KEY_G || codeKey == Keyboard.KEY_H || codeKey == Keyboard.KEY_I || codeKey == Keyboard.KEY_J
                || codeKey == Keyboard.KEY_K || codeKey == Keyboard.KEY_L || codeKey == Keyboard.KEY_M || codeKey == Keyboard.KEY_N || codeKey == Keyboard.KEY_O
                || codeKey == Keyboard.KEY_P || codeKey == Keyboard.KEY_Q || codeKey == Keyboard.KEY_R || codeKey == Keyboard.KEY_S || codeKey == Keyboard.KEY_T
                || codeKey == Keyboard.KEY_U || codeKey == Keyboard.KEY_V || codeKey == Keyboard.KEY_W || codeKey == Keyboard.KEY_X || codeKey == Keyboard.KEY_Y
                || codeKey == Keyboard.KEY_Z;
    }

    /**
     * Récupère une lettre à partir d'un code clavier
     *
     * @return une lettre au format String
     */
    private String getLetter(int letterKeyCode) {
        Boolean shift = false;
        if (this.env.getKeyDown(Keyboard.KEY_LSHIFT) || this.env.getKeyDown(Keyboard.KEY_RSHIFT)) {
            shift = true;
        }
        env.advanceOneFrame();
        String letterStr = "";
        if ((letterKeyCode == Keyboard.KEY_SUBTRACT || letterKeyCode == Keyboard.KEY_MINUS)) {
            letterStr = "-";
        } else {
            letterStr = Keyboard.getKeyName(letterKeyCode);
        }
        if (shift) {
            return letterStr.toUpperCase();
        }
        return letterStr.toLowerCase();
    }

    /**
     * Permet de saisir le nom d'un joueur et de l'affiche à l'écran durant la
     * saisie
     *
     * @return le nom du joueur au format String
     */
    private String getNomJoueur() {
        textNomJoueur.modify("Choisissez un nom de joueur : ");
        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;
            while (!isLetter(touche) && touche != Keyboard.KEY_MINUS && touche != Keyboard.KEY_SUBTRACT && touche != Keyboard.KEY_RETURN) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if ((touche == Keyboard.KEY_SUBTRACT || touche == Keyboard.KEY_MINUS) && nomJoueur.length() > 0) {
                    nomJoueur += "-";
                } else {
                    nomJoueur += getLetter(touche);
                }
                textNomJoueur.modify("Choisissez un nom de joueur : " + nomJoueur);
            }
        }
        textNomJoueur.erase();
        return nomJoueur;
    }

    /**
     * Menu principal
     *
     * @return le choix du joueur
     */
    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu principal
        textMenuQuestion.display();
        textMenuPrincipal1.display();
        textMenuPrincipal2.display();
        textMenuPrincipal3.display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // efface le menu
        textMenuQuestion.erase();
        textMenuPrincipal1.erase();
        textMenuPrincipal2.erase();
        textMenuPrincipal3.erase();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                Chemin = "src/Profil/" + nomJoueur + ".xml";
                //creation dun profil temporaire vide pour le test
                profil = new Profil(nomJoueur, "00/00/0000");
                if (profil.charge(Chemin)) {
                    profil = new Profil(Chemin);
                    // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                    choix = menuJeu();
                } else {
                    // sinon continue (et boucle dans ce menu)
                    choix = MENU_VAL.MENU_CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();

                //String Avatar = getAvatar();
                Chemin = "src/Profil/" + nomJoueur + ".xml";

                 {
                    try {
                        File f = new File(Chemin);
                        if (!f.exists()) {
                            String DateN = getDateN();
                            FileUtil.stringToFile("", Chemin);
                            Profil profil = new Profil(nomJoueur, DateN);
                            Document doc = XMLUtil.DocumentFactory.fromObject(profil);
                            //ajoutons le stylesheet auto avec l'insertion d'une node avant l'element root cela nous evitera de 
                            //faire une transformation xsl manuelle et evite les erreurs url car pas de serveur
                            Node pi = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"profil.xsl\"");
                            doc.insertBefore(pi, doc.getDocumentElement());
                            XMLUtil.DocumentTransform.writeDoc(doc, Chemin);
                            

                            profil = new Profil(Chemin);
                        } else {
                            choix = MENU_VAL.MENU_CONTINUE;
                            break;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                // crée un profil avec le nom d'un nouveau joueur

                // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                // le choix est de sortir du jeu (quitter l'application)
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    /**
     * Menu de jeu
     *
     * @return le choix du joueur une fois la partie terminée
     */
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie = null;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);

            // affiche le menu de jeu
            textMenuQuestion.display();
            textMenuJeu1.display();
            textMenuJeu2.display();
            textMenuJeu3.display();
            textMenuJeu4.display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // efface le menu
            textMenuQuestion.erase();
            textMenuJeu1.erase();
            textMenuJeu2.erase();
            textMenuJeu3.erase();
            textMenuJeu4.erase();

            // restaure la room du jeu
            env.setRoom(mainRoom);
            //on recharge le _doc en rechargent le profil
            profil = new Profil(Chemin);
            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    //menu pour choix lettre
                    env.setRoom(menuRoom);
                    textMenuQuestion.modify("Choisir un niveau entre 1 et 5:");
                    textMenuQuestion.display();
                    //demande niveaux
                    int niveau = this.touche();
                    // crée un nouvelle partie
                    String mot = selectLevel(niveau);
                    //recuperation de la date
                    Date actuelle = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dat = dateFormat.format(actuelle);
                    //creation d'une nouvelle partie
                    partie = new Partie(dat, mot, niveau);
                    //on lance la partie
                    joue(partie);
                    //sauvegarde 
                    profil.ajouterPartie(partie);
                    profil.sauvegarder(Chemin);
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    env.setRoom(menuRoom);
                    // demander de fournir une date
                    String date;
                    date = getDateN();
                    env.setRoom(mainRoom);
                    // tenter de trouver une partie à cette date
                    Element part;
                    part = profil.chercherPartie(date);
                    // .......
                    // Si partie trouvée, recupère le mot de la partie existante, sinon ???
                    if (part == null) {

                    } else {
                        partie = new Partie(part);
                        System.out.println(partie.getNiveau());
                        initDico(part.getElementsByTagName("mot").item(0).getTextContent());

                        // joue
                        joue(partie);
                        // enregistre la partie dans le profil --> enregistre le profil
                        profil.ajouterPartie(partie);
                        profil.sauvegarder(Chemin);
                    }

                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
            //on remet la bonne question
            textMenuQuestion.modify("Que voulez vous ?");
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private int touche() {
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4 || touche == Keyboard.KEY_5)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }
        //enum de touche rend 2 pour 1 etc donc -1 par chance a changer si on change
        return touche - 1;
    }

    /**
     * ici selection aleatoire d'un chiffre en fonction de la difficulté choisie
     *
     * @param touche
     * @return
     */
    private String selectLevel(int touche) {
        String mot = dico.getMotDepuisListeNiveaux(touche);
        textMenuQuestion.erase();
        initDico(mot);
        textMenuQuestion.modify("mot à trouver : " + mot);
        textMenuQuestion.display();
        env.advanceOneFrame();
        Chronometre chrono;
        //on met un chrono d'affichage du mot a trouver de 2 seconde a changer si on veux plus
        chrono = new Chronometre(2);
        chrono.start();
        while (chrono.remainsTime() > 0) {
        }
        chrono.stop();
        textMenuQuestion.erase();
        return mot;
    }

    public void joue(Partie partie) {

        env.setRoom(mainRoom);
        // Instancie un Tux
        tux = new Tux(env, mainRoom);

        env.addObject(tux);
        for (Letter lettre : letters) {
            env.addObject(lettre);
        }

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);

        // Boucle de jeu
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace();
            tux.changeView(letters);

            // Ici, on applique les regles
            appliqueRegles(partie);

            //ici on fait tourner les lettres sur elles meme
            for (Letter lettre : letters) {
                lettre.setRotateY(lettre.getRotateY() + 5);
            }

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
        //on remet la camera par default
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);
        //on sauvegarde la partie dans le profil

    }

    protected abstract void démarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

    protected double distance(Letter letter) {
        return Math.sqrt(Math.pow(letter.getX() - tux.getX(), 2) + Math.pow(letter.getZ() - tux.getZ(), 2));

    }

    //collision en fonction de la taille des boite ici fixé
    protected Boolean collision(Letter letter) {
        return distance(letter) <= 8;
    }

    /**
     * demande une date de Naissance
     *
     * @return
     */
    private String getDateN() {
        textNomJoueur.modify("Date de la partie format jj/mm/aaaa : ");
        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;
            while (!isNumber(touche) && touche != Keyboard.KEY_RETURN && touche != Keyboard.KEY_SLASH) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if (touche == Keyboard.KEY_SLASH && nomJoueur.length() > 0) {
                    nomJoueur += "/";
                } else {
                    nomJoueur += getNumber(touche);
                }
                textNomJoueur.modify("Date de la partie format jj/mm/aaaa : \n" + nomJoueur);
            }
        }
        textNomJoueur.erase();
        return nomJoueur;
    }

    private Boolean isNumber(int codeKey) {
        return codeKey == Keyboard.KEY_0 || codeKey == Keyboard.KEY_1 || codeKey == Keyboard.KEY_2 || codeKey == Keyboard.KEY_3
                || codeKey == Keyboard.KEY_4 || codeKey == Keyboard.KEY_5 || codeKey == Keyboard.KEY_6 || codeKey == Keyboard.KEY_7
                || codeKey == Keyboard.KEY_8 || codeKey == Keyboard.KEY_9;
    }

    /**
     * Récupère une lettre à partir d'un code clavier
     *
     * @return une lettre au format String
     */
    private String getNumber(int letterKeyCode) {
        env.advanceOneFrame();
        String letterStr = "";
        if ((letterKeyCode == Keyboard.KEY_0 || letterKeyCode == Keyboard.KEY_NUMPAD0)) {
            letterStr = "0";
        } else {
            letterStr = (letterKeyCode - 1) + "";
        }
        return letterStr;
    }

    /**
     * demande un avatar pour l'instant inutile mais peut a tout moment etre
     * implanté
     *
     * @return
     */
    private String getAvatar() {
        textNomJoueur.modify("Donner le lien vers votre image : ");
        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;

            while (!isLetter(touche) && touche != Keyboard.KEY_MINUS && touche != Keyboard.KEY_SUBTRACT && touche != Keyboard.KEY_RETURN && touche != Keyboard.KEY_SLASH && touche != Keyboard.KEY_PERIOD) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if ((touche == Keyboard.KEY_SUBTRACT || touche == Keyboard.KEY_MINUS) && nomJoueur.length() > 0) {
                    nomJoueur += "-";
                } else if (touche == Keyboard.KEY_DECIMAL && nomJoueur.length() > 0) {
                    nomJoueur += "/";
                } else if (touche == Keyboard.KEY_PERIOD && nomJoueur.length() > 0) {
                    nomJoueur += ".";
                } else {
                    nomJoueur += getLetter(touche);
                }
                textNomJoueur.modify("Donner le lien vers votre image : \n" + nomJoueur);
            }
        }
        textNomJoueur.erase();
        return nomJoueur;
    }

}
