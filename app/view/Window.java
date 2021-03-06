package app.view;

import app.model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Window extends JFrame implements KeyListener, ActionListener {
    private final InfoPanel gameStatusPanel;
    private final InfoPanel dataPanel;
    private final Field field;

    // Pas de puissance des réacteurs
    private static final double PUSH_POWER = 2;
    private Timer timerField;
    private Timer timerInfos;

    private boolean stop;

    public Window() {
        super();

        this.stop = false;

        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.setResizable(false);

        field = new Field(Scenario.GAME, new Dimension(600, 580));
        gameStatusPanel = new InfoPanel(new Dimension(800, 20));
        dataPanel = new InfoPanel(new Dimension(200, 580));

        this.add(field, BorderLayout.CENTER);
        this.add(gameStatusPanel, BorderLayout.NORTH);
        this.add(dataPanel, BorderLayout.EAST);

        this.addKeyListener(this);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        //this.pack();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        switch (keyEvent.getKeyChar()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case 's':
                field.changePy(-PUSH_POWER);
                break;
            case 'z':
                field.changePy(PUSH_POWER);
                break;
            case 'd':
                field.changePx(-PUSH_POWER);
                break;
            case 'q':
                field.changePx(PUSH_POWER);
                break;
            case 'r':
                field.changeAutoPilot();
                break;
            case 'l':
                field.launchERP();
                break;
            case 'g':
                field.enableGravity();
                break;
            case ' ':
                field.restart();
                break;
            case 'p':
                if (stop)
                    start();
                else
                    stop();
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        //Do nothing
    }

    public void keyReleased(KeyEvent e) {
        //Do nothing
    }

    public void start() {
        if (timerField == null)
            timerField = new Timer((int) (Field.Te * 1000), field);
        timerField.start();
        if (timerInfos == null)
            timerInfos = new Timer((int) (Field.Te * 1000), this);
        timerInfos.start();
        stop = false;
    }

    public void stop() {
        timerField.stop();
        timerInfos.stop();
        stop = true;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameStatusPanel.display(field.getGameStatus());
        dataPanel.display(field.getScenarioData());
    }
}
