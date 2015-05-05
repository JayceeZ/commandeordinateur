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

        G = 0;
        init(scenario);

        this.setPreferredSize(dimension);
        this.setVisible(true);
    }

    private void initERP() {
        this.gameStatus = "Mode: Estimation Recursives de paramètres";

        movableObject = new MovableObject(MASS, 20, 200);
        movableObject.setVx(20);
        observer = new Observer(200, 200, 100, 0, 0.5);
    }

    private void initGAME() {
        this.gameStatus = "Mode: Jeu";

        movableObject = new MovableObject(MASS, 80, 20);

        if(autopilot) {
            // commande par retour d'état
            AutoPilot commands = AutoPilot.loadFromFile("Kre.txt");
            System.out.println(commands);
            movableObject.setCommands(commands);
        }
        movableObject.setCommandOn(autopilot);

        staticObjects = buildLevel();
    }

    private static StaticObject[] buildLevel() {
        StaticObject[] staticObjects = new StaticObject[7];

        staticObjects[0] = new StaticObject(0, 0, 40, 200);
        staticObjects[1] = new StaticObject(70, 70, 200, 200);
        staticObjects[2] = new StaticObject(240, 100, 400, 140);
        staticObjects[3] = new StaticObject(500, 40, 580, 400);
        staticObjects[4] = new StaticObject(270, 220, 470, 300);
        staticObjects[5] = new StaticObject(50, 380, 300, 420);
        staticObjects[6] = new StaticObject(350, 420, 450, 470);

        staticObjects[6].setDestination(true);

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
        // mise à jour de l'objet avec les paramètres
        movableObject.actualize();

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

    public boolean testCollisionBord(double x, double y) {
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
                String gravity = "Gravité: "+String.valueOf(getG());
                String propulsion = "Propulsion:\nPx:" + String.format("%1$.2f", movableObject.getPx()) + "\nPy:" + String.format("%1$.2f", movableObject.getPy());
                String vitesse = "Vitesse:\nVx:" + String.format("%1$.2f", movableObject.getVx()) + "\nVy:" + String.format("%1$.2f", movableObject.getVy());
                String position = "Position:\nx:" + movableObject.getX() + " y:" + movableObject.getY();
                String autopilote = "";
                if(autopilot) {
                    autopilote = "Autopilote:\n   Objectif: "+movableObject.getObjective()+"\n   Reste: "+movableObject.distancesToObjective();
                }
                return gravity+"\n\n"+propulsion + "\n\n" + vitesse + "\n\n" + position +"\n\n"+autopilote;
            case ERP:
                String theta = "Angle d'observation: " + String.format("%1$.2f",thetaDegrees);
                String positionCalc = "Position estimée: " + observer.getEstimation();
                String launchedERP = "Appuyez sur L";
                if(observer.isLaunchedObject()) {
                    MovableObject launchedObject = observer.getLaunchedObject();
                    launchedERP = "Objet lancé:\n   Objectif: "+launchedObject.getObjective()+"\n   Reste: "+launchedObject.distancesToObjective();
                }
                return theta + "\n\n" + positionCalc+"\n\n" + launchedERP;
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

    public void enableGravity() {
        if(G == 0) {
            System.out.printf("Gravité activée");
            G = -9.81;
        } else {
            System.out.printf("Gravité désactivée");
            G = 0;
        }
        restart();
    }

    public void launchERP() {
        if(scenario == Scenario.ERP) {
            observer.launchObject();
        }
    }
}
