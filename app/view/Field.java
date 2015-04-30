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
    private double thetaDegrees;
    private String gameStatus;
    private boolean autopilot;

    public Field(Scenario scenario, Dimension dimension) {
        this.scenario = scenario;
        this.gameStatus = "Field loaded, waiting for simulation to start.";
        this.autopilot = false;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        init(scenario);

        this.setPreferredSize(dimension);
        this.setVisible(true);
    }

    private void initERP() {
        this.gameStatus = "Mode: Estimation Recursives de paramètres";

        G = 0;
        movableObject = new MovableObject(MASS, 20, 200);
        movableObject.setVx(20);
        observer = new Observer(200, 200, 100, 0, 0.5);
    }

    private void initGAME() {
        this.gameStatus = "Mode: Jeu";

        movableObject = new MovableObject(MASS, 80, 20);

        // commande par retour d'état
        movableObject.setCommand(new Command(360, 90, 0.5255541, 1.0252537));
        movableObject.setCommandOn(autopilot);

        staticObjects = buildLevel();

        G = -9.81;
    }

    private static StaticObject[] buildLevel() {
        StaticObject[] staticObjects = new StaticObject[3];

        staticObjects[0] = new StaticObject(0, 0, 40, 200);
        staticObjects[1] = new StaticObject(70, 70, 200, 200);
        staticObjects[2] = new StaticObject(240, 100, 400, 140);

        staticObjects[2].setDestination(true);

        return staticObjects;
    }

    private void init(Scenario scenario) {
        switch (scenario) {
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

        if (staticObjects != null)
            for (StaticObject staticObject : staticObjects) {
                staticObject.dessine(g);
            }

        // Dessine le vaisseau
        movableObject.dessine(g);

        if (observer != null) {
            observer.dessine(g);

            g.drawString("(" + observer.getX() + "," + observer.getY() + ")", observer.getX(), observer.getY());
            g.drawString("(" + movableObject.getX() + "," + movableObject.getY() + ")", movableObject.getX(), movableObject.getY());

            g.drawLine(movableObject.getX(), movableObject.getY(), observer.getX(), observer.getY());
            g.drawLine(observer.getX(), observer.getY(), observer.getX(), observer.getY() - 100);
        }
    }

    public void changePx(double npx) {
        movableObject.changePx(npx);
    }

    public void changePy(double npy) {
        movableObject.changePy(npy);
    }

    public void actualizeObjects() {
        if (movableObject.isCommandOn()) {
            Command command = movableObject.getCommand();
            movableObject.setPx(command.getKx() * (command.getXf() - movableObject.getX()));
            movableObject.setPy(command.getKy() * (command.getYf() - movableObject.getY()));
        }

        // mise à jour de l'objet avec les paramètres
        movableObject.actualizeSpeed();
        movableObject.actualizePosition();

        // tests de collision (avec le bord)
        movableObject.testCollision(this);
        // tests de collision (avec les solides)
        if (staticObjects != null)
            for (StaticObject staticObject : staticObjects) {
                movableObject.testCollision(staticObject);
            }

        if (observer != null) {
            observer.actualizePosition();
            thetaDegrees = 180 * observer.getThetaObs(movableObject.getX(), movableObject.getY()) / Math.PI;
        }

        if (movableObject.isLanded() && movableObject.getLandedStaticObject().isDestination()) {
            gameStatus = "Vous êtes arrivé à destination !";
        } else if (autopilot) {
            gameStatus = "Autopilotage de l'appareil activé (Commande par retour d'état)";
        } else {
            gameStatus = "Courage, vous pouvez y arriver ! (ESPACE pour recommencer)";
        }
    }

    public boolean testCollisionBord(int x, int y) {
        if (x < 0 || y < 0)
            return true;
        if (x > this.getWidth() || y > this.getHeight())
            return true;
        return false;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public String getScenarioData() {
        switch (scenario) {
            case GAME:
                String propulsion = "Propulsion:\nPx:" + String.format("%1$.2f", movableObject.getPx()) + "\nPy:" + String.format("%1$.2f", movableObject.getPy());
                String vitesse = "Vitesse:\nVx:" + String.format("%1$.2f", movableObject.getVx()) + "\nVy:" + String.format("%1$.2f", movableObject.getVy());
                String position = "Position:\nx:" + movableObject.getX() + " y:" + movableObject.getY();
                return propulsion + "\n\n" + vitesse + "\n\n" + position;
            case ERP:
                String theta = "Angle d'observation: " + thetaDegrees;
                String positionCalc = "Position estimée: " + observer.getEstimation();
                return theta + "\n" + positionCalc;
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

    public void setGameStatus(String text) {
        this.gameStatus = text;
    }

    public void restart() {
        init(scenario);
    }

    public void changeAutoPilot() {
        if(scenario.equals(Scenario.GAME)) {
            autopilot = !autopilot;
        }
        this.restart();
    }
}
