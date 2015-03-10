package projetcommandeordinateur.view;

import projetcommandeordinateur.MovableObject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Field extends JPanel {
    private MovableObject movableObject;
    private static final double TIME = 0.04;
    private static final double PUSH_POWER = 1;

    public Field() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        movableObject = new MovableObject(1, 400, 0, TIME);

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
        return "Propulsion: Px:"+movableObject.getPx()+" Py:"+movableObject.getPy();
    }
}
