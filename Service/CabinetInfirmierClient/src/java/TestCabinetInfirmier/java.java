/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestCabinetInfirmier;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loic
 */
public class java {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            System.out.println(getNomCabinet());
            System.out.println(getListePatientsStr(001));
            // get the html content and record it into a file (a cache)
            FileUtil.stringToFile(getInfirmiere("001"), "D:/programmation/LW/projet/html/Cabinet1.html");
            // view this file in a browser
            BrowserUtil.launch("D:/programmation/LW/projet/html/Cabinet1.html");
        } catch (IOException ex) {
            Logger.getLogger(java.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getListePatientsStr(int id) {
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service service = new fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service();
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier port = service.getCabinetInfirmierPort();
        return port.getListePatientsStr(id);
    }

    private static String getNomCabinet() {
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service service = new fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service();
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier port = service.getCabinetInfirmierPort();
        return port.getNomCabinet();
    }

    private static int getStrLength(String mot) {
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service service = new fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service();
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier port = service.getCabinetInfirmierPort();
        return port.getStrLength(mot);
    }

    private static String hello(String name) {
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service service = new fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service();
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier port = service.getCabinetInfirmierPort();
        return port.hello(name);
    }

   
    

    private static String getHTML() {
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service service = new fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service();
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier port = service.getCabinetInfirmierPort();
        return port.getHTML();
    }

    private static String getInfirmiere(String infirmierId) {
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service service = new fr.ujf_grenoble.l3miage.medical.CabinetInfirmier_Service();
        fr.ujf_grenoble.l3miage.medical.CabinetInfirmier port = service.getCabinetInfirmierPort();
        return port.getInfirmiere(infirmierId);
    }
    
}
