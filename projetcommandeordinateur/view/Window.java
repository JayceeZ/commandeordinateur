package projetcommandeordinateur.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Window extends JFrame implements KeyListener{
    Field field;

    public Window() {
        super();
        this.setLayout(new BorderLayout());
        this.setSize(800,600);
        this.setResizable(false);

        field = new Field();
        this.add(field, BorderLayout.CENTER);
        
        addKeyListener(this);

        this.setVisible(true);
    }
    
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        field.keyTyped(c);
    }

    public void keyPressed(KeyEvent e) {
    //Invoked when a key has been pressed.
    }

    public void keyReleased(KeyEvent e) {
    //Invoked when a key has been released.
    }
    
    public void actualize(){
        field.actualizeObjects();
        revalidate();
        field.repaint();
    }
}
