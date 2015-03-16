package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Window extends JFrame implements KeyListener, ActionListener {
    private final InfoPanel infoPanel;
    private final Field field;
    private Timer timer;

    public Window() {
        super();
        this.setLayout(new BorderLayout());
        this.setSize(800,600);
        this.setResizable(false);

        field = new Field();
        infoPanel = new InfoPanel();
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(field, BorderLayout.CENTER);
        
        addKeyListener(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        field.keyTyped(c);
    }

    public void keyPressed(KeyEvent e) {
        //Do nothing
    }

    public void keyReleased(KeyEvent e) {
        //Do nothing
    }
    
    public void actualize(){
        field.actualizeObjects();
        field.repaint();
        infoPanel.display(field.getGameStatusMessage());
    }

    public void start() {
        timer = new Timer((int) (Field.Te*1000), this);
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        actualize();
    }
}
