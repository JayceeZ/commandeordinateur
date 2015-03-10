package projetcommandeordinateur.view;

import projetcommandeordinateur.MovableObject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Field extends JPanel {
    private MovableObject movableObject;
    private static final double TIME = 1;
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

        g.drawRect(movableObject.getX(), movableObject.getY(), 20, 20);
    }
    
    public void actualizeObjects(){
        movableObject.actualizeSpeed();
        movableObject.actualizePosition();
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
        }
    }
}
