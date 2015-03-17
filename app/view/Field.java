package app.view;

import app.model.Command;
import app.model.MovableObject;
import app.model.StaticObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Représente le terrain de jeu
 */
public class Field extends JPanel implements ActionListener {
    private final StaticObject[] staticObjects;
    private MovableObject movableObject;
    private static final double MASS = 1;

    // Période d'échantillonage en secondes
    public static final double Te = 0.04;

    // Propriètés physiques du monde
    private static final double G = -9.81;

    public Field() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        movableObject = new MovableObject(MASS, 400, 0);
        movableObject.setCommand(new Command(600,200,0.0514048,0.1265232));
        movableObject.setCommandOn(true);

        staticObjects = new StaticObject[8];

        staticObjects[0] = new StaticObject(0, 0, 40, 200);
        staticObjects[1] = new StaticObject(100, 100, 200, 200);
        staticObjects[2] = new StaticObject(280, 100, 400, 140);
        staticObjects[3] = new StaticObject(0, 0, 40, 200);
        staticObjects[4] = new StaticObject(0, 0, 40, 200);
        staticObjects[5] = new StaticObject(0, 0, 40, 200);
        staticObjects[6] = new StaticObject(0, 0, 40, 200);
        staticObjects[7] = new StaticObject(0, 0, 40, 200);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO: Draw the field
        for (StaticObject staticObject : staticObjects) {
            staticObject.dessine(g);
        }


        // Dessine le vaisseau
        movableObject.dessine(g);
    }

    public void changePx(double npx) {
        movableObject.changePx(npx);
    }

    public void changePy(double npy) {
        movableObject.changePy(npy);
    }

    public void actualizeObjects(){
        if(movableObject.isCommandOn()){
            Command command = movableObject.getCommand();
            movableObject.setPx(command.getKx()*(command.getXf()-movableObject.getX()));
            movableObject.setPy(command.getKy() * (command.getYf() - movableObject.getY()));

        }
        movableObject.actualizeSpeed();
        movableObject.actualizePosition();
        movableObject.testCollision(this);

        for (StaticObject staticObject : staticObjects) {
            movableObject.testCollision(staticObject);
        }
    }

    public boolean testCollisionBord(int x, int y) {
        if(x < 0 || y < 0)
            return true;
        if(x > this.getWidth() || y > this.getHeight())
            return true;
        return false;
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.actualizeObjects();
        this.repaint();
    }

    public void restart() {
        movableObject.respawn();
    }
}
