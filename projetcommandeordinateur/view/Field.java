package projetcommandeordinateur.view;

import projetcommandeordinateur.MovableObject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Field extends JPanel {
    private MovableObject movableObject;

    public Field() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        movableObject = new MovableObject(0, 400, 550, 0);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO: Draw the field

        // Draw the movableobject
        g.drawRect(movableObject.getX(), movableObject.getY(), 20, 20);
    }
}
