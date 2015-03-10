/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetcommandeordinateur;

import projetcommandeordinateur.view.Window;

/**
 *
 * @author Alex
 */
public class ProjetCommandeOrdinateur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window();
        while(true){
            Thread.sleep(100);
            window.actualize();
        }
    }
    
}
