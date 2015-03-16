package app.view;

import app.model.MovableObject;

import javax.swing.*;
import java.awt.*;

/**
 * Représente le terrain de jeu
 */
public class Field extends JPanel {
    private MovableObject movableObject;
    private static final double MASS = 1;

    // Période d'échantillonage en secondes
    public static final double Te = 0.04;

    // Pas de puissance des réacteurs
    private static final double PUSH_POWER = 0.1;

    // Propriètés physiques du monde
    private static final double G = -9.81;

    public Field() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        movableObject = new MovableObject(MASS, 400, 0);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO: Draw the field

        // Dessine le vaisseau
        movableObject.dessine(g);
    }

    public void actualizeObjects(){
        movableObject.actualizeSpeed();
        movableObject.actualizePosition();
        movableObject.testCollision(this);
    }

    public boolean testCollisionBord(int x, int y) {
        if(x < 0 || y < 0)
            return true;
        if(x > this.getWidth() || y > this.getHeight())
            return true;
        return false;
    }

    public void keyTyped(char c){
        switch(c){
            case 'z' :
                movableObject.changePy(-PUSH_POWER);
                break;
            case 's' :
                movableObject.changePy(PUSH_POWER);
                break;
            case 'q' :
                movableObject.changePx(-PUSH_POWER);
                break;
            case 'd' :
                movableObject.changePx(PUSH_POWER);
                break;
            case ' ':
                movableObject.respawn();
                break;
        }
    }

    public String getGameStatusMessage() {
        String propulsion = "Propulsion: Px:"+movableObject.getPx()+" Py:"+movableObject.getPy();
        String vitesse = "Vitesse: Vx:"+movableObject.getVx()+" Vy:"+movableObject.getVy();
        String position = "Position: x:"+movableObject.getX()+" y:"+movableObject.getY();

        return propulsion+" "+vitesse+" "+position;
    }

    public static double getG() {
        return G;
    }
}
