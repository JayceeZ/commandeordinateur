package app.view;

import app.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Représente le terrain de jeu
 */
public class Field extends JPanel implements ActionListener {
    private StaticObject[] staticObjects;
    private MovableObject movableObject;
    private Observer observer;
    private static final double MASS = 1;
    private Scenario scenario;

    // Période d'échantillonage en secondes
    public static final double Te = 0.04;

    // Propriètés physiques du monde
    private static double G;

    public Field(Scenario scenario) {
        this.scenario = scenario;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        init(scenario);
        this.setVisible(true);
    }

    private void initERP(){
        G = 0;
        movableObject = new MovableObject(MASS, 0, 200);
        movableObject.setVx(20);
        observer = new Observer(200,200,100,0,3);

    }

    private void initGAME(){

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

        G = -9.81;
    }

    private void init(Scenario scenario) {
        switch(scenario){
            case GAME:
                initGAME();
                break;
            case ERP:
                initERP();
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO: Draw the field
        if(staticObjects != null)
            for (StaticObject staticObject : staticObjects) {
                staticObject.dessine(g);
            }

        // Dessine le vaisseau
        movableObject.dessine(g);
        if(observer != null){
            observer.dessine(g);
            g.drawLine(movableObject.getX(), movableObject.getY(), observer.getX(), observer.getY());
            g.drawLine(observer.getX(),observer.getY(),observer.getX(),observer.getY()-100);
        }
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
        if(staticObjects != null)
            for (StaticObject staticObject : staticObjects) {
                movableObject.testCollision(staticObject);
            }

        if(observer != null){
            observer.actualizePosition();
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
        switch(scenario){
            case GAME:
                String propulsion = "Propulsion: Px:"+movableObject.getPx()+" Py:"+movableObject.getPy();
                String vitesse = "Vitesse: Vx:"+movableObject.getVx()+" Vy:"+movableObject.getVy();
                String position = "Position: x:"+movableObject.getX()+" y:"+movableObject.getY();

                return propulsion+" "+vitesse+" "+position;
            case ERP:
                String theta = "Angle d'observation : "+180*observer.getThetaObs(movableObject.getX(),movableObject.getY())/Math.PI;
                return theta;

        }
        return "";

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
        init(scenario);
    }
}
