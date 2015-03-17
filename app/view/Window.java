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

    // Pas de puissance des réacteurs
    private static final double PUSH_POWER = 1;
    private Timer timerField;
    private Timer timerInfos;

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

    public void keyTyped(KeyEvent keyEvent){
        switch(keyEvent.getKeyChar()){
            case 's' :
                field.changePy(-PUSH_POWER);
                break;
            case 'z' :
                field.changePy(PUSH_POWER);
                break;
            case 'd' :
                field.changePx(-PUSH_POWER);
                break;
            case 'q' :
                field.changePx(PUSH_POWER);
                break;
            case ' ':
                field.restart();
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        //Do nothing
    }

    public void keyReleased(KeyEvent e) {
        //Do nothing
    }
    
    public void actualize(){
    }

    public void start() {
        timerField = new Timer((int) (Field.Te*1000), field);
        timerField.start();
        timerInfos = new Timer(1, this);
        timerInfos.start();
    }

    public void stop() {
        timerField.stop();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        infoPanel.display(field.getGameStatusMessage());
        infoPanel.revalidate();
    }
}
