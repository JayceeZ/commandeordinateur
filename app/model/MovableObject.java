/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.model;

import app.view.Field;

import java.awt.*;

/**
 * Représente l'objet contrôlable
 *
 * @author Alex
 */
public class MovableObject {
    // Taille de l'objet
    private static final int SIZE = 20;
    private static final double DEADLYSPEED = 20;

    // Positions initiales
    private int initX;
    private int initY;

    // Propriétés physiques de l'objet
    private double m;

    // Position de l'objet
    private double x;
    private double y;

    // Vitesse de l'objet
    private double vx;
    private double vy;
    // Accélération de l'objet
    private double ax;
    private double ay;

    // Niveau de propulsion des réacteurs
    private double px;
    private double py;

    // Commande automatique
    private boolean commandOn;
    private boolean landed;
    private StaticObject landedStaticObject;
    private AutoPilot commands;

    // variables de mémoire d'erreur de pilotage
    // pour calculer la distance parcourue réelle
    private double lastx;
    private double lasty;
    // le point qui devait être atteint
    private double lastThx;
    private double lastThy;

    public MovableObject(double m, int xi, int yi) {

        // on définie la position initiale
        this.initX = xi;
        this.initY = yi;

        this.m = m;
        this.x = initX;
        this.y = initY;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.px = 0;
        this.py = 0;
        this.commandOn = false;
    }

    public void changePx(double npx) {
        this.px += npx;
    }

    public void changePy(double npy) {
        if(landed) {
            landed = false;
        }
        this.py += npy;
    }

    public void setPx(double npx) {
        this.px = npx;
    }

    public void setPy(double npy) {
        this.py = npy;
    }

    public void setVx(double nvx) {
        this.vx = nvx;
    }

    public void setVy(double nvy) {
        this.vy = nvy;
    }

    public void actualizeSpeed() {
        if (!landed) {
            vx += (px / m) * Field.Te;
            vy += ((py / m) - Field.getG()) * Field.Te;
        } else {
            this.stop();
        }
    }

    public void actualizePosition() {
        x += vx * Field.Te;
        y += vy * Field.Te;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    /**
     * Eteint les propulseurs
     */
    public void stop() {
        px = 0;
        py = 0;
        vx = 0;
        vy = 0;
        if(commandOn)
            commands.reset();
    }

    /**
     * Réinitialise la position
     */
    public void respawn() {
        stop();
        x = initX;
        y = initY;
    }

    /**
     * Donne la vitesse horizontale de l'objet
     *
     * @return
     */
    public double getVx() {
        return vx;
    }

    /**
     * Donne la vitesse verticale de l'objet
     *
     * @return
     */
    public double getVy() {
        return vy;
    }

    /**
     * Donne la propulsion horizontale de l'objet
     *
     * @return
     */
    public double getPx() {
        return px;
    }

    /**
     * Donne la propulsion verticale de l'objet
     *
     * @return
     */
    public double getPy() {
        return py;
    }

    public void setCommandOn(boolean c) {
        this.commandOn = c;
    }

    public boolean isCommandOn() {
        return commandOn;
    }

    public void dessine(Graphics g) {
        g.drawRect(this.getX() - SIZE / 2, this.getY() - SIZE / 2, SIZE, SIZE);

        // propulsion gauche/droite
        if(Math.abs(px) > 0.01)
            g.drawLine(this.getX(), this.getY(), (int) (this.getX() - px), this.getY());
        // propulsion haut/bas
        if(Math.abs(py) > 0.01)
            g.drawLine(this.getX(), this.getY(), this.getX(), (int) (this.getY() - py));

        // points autopilotage
        if(commandOn)
            commands.drawPoints(g);
        // bulle d'info
        if(landed)
            g.drawString("Posé", this.getX()-SIZE/2, (this.getY()-SIZE/2)-1);
    }

    public void testCollision(Field field) {
        double x = this.x - SIZE / 2;
        double y = this.y - SIZE / 2;

        if (field.testCollisionBord(x, y)
                || field.testCollisionBord(x + SIZE, y)
                || field.testCollisionBord(x, y + SIZE)
                || field.testCollisionBord(x + SIZE, y + SIZE)) {
            this.respawn();
        }
    }

    public void testCollision(StaticObject staticObject) {
        double x = this.x - SIZE / 2;
        double y = this.y - SIZE / 2;

        if (staticObject.appartient(x, y)
                || staticObject.appartient(x + SIZE, y)
                || staticObject.appartient(x, y + SIZE)
                || staticObject.appartient(x + SIZE, y + SIZE)) {
            System.out.println("Collision à (" + x + "," + y + ") avec " + staticObject + "\n");
            staticObject.colorify(Color.RED);

            if (staticObject.appartient(x, y + SIZE)
                    && staticObject.appartient(x + SIZE, y + SIZE)) { // si c'est la partie inférieure de l'objet qui entre en collision
                if (this.vy > DEADLYSPEED) { // si la vitesse verticale est meurtrière
                    this.respawn();
                } else { // sinon on est posé
                    this.land(staticObject);
                }
            } else {
                this.respawn();
            }
        }
    }

    private void land(StaticObject staticObject) {
        this.y = staticObject.getTopY() - SIZE / 2;
        this.landed = true;
        this.landedStaticObject = staticObject;
    }

    public boolean isLanded() {
        return landed;
    }

    public StaticObject getLandedStaticObject() {
        return landedStaticObject;
    }

    public void setCommands(AutoPilot commands) {
        this.commands = commands;
    }

    public AutoPilot getCommands() {
        return commands;
    }

    public String distancesToObjective() {
        double[] values = new double[2];
        if (commandOn) {
            Command command = commands.getCommandState();
            values[0] = Math.abs(command.getXf() - x);
            values[1] = Math.abs(command.getYf() - y);
        }
        return "("+String.format("%1$.2f", values[0])+", "+String.format("%1$.2f", values[1])+")";
    }

    public void actualize() {
        if(commandOn) {
            Command command = commands.getCommandState();
            if (Math.abs(command.getXf() - x) < 6.5
                    && Math.abs(command.getYf() - y) < 6.5) {
                System.out.println("Objectif de la commande " + command + " atteint");
                if (commands != null) {
                    System.out.println("Etat au changement: (x="+this.getX()+",y="+this.getY()+",vx="+this.getVx()+",vy="+this.getVy()+")");
                    lastx = x;
                    lasty = y;

                    // on peut aussi lui demander d'être stationnaire sur le dernier point
                    //if(commands.hasNext())
                        commands.switchNext();
                }
            }

            // application de la commande en boucle fermée
            double px = command.getKx() * ((command.getXf()+lastx)/2 - x);
            double py = command.getKy() * ((command.getYf()+lasty)/2 - y);

            setPx(px);
            setPy(py);
        }

        actualizeSpeed();
        actualizePosition();
    }

    public String getObjective() {
        String objective = "pas d'objectif";
        if (commandOn) {
            Command command = commands.getCommandState();
            objective = "("+command.getXf()+","+command.getYf()+")";
        }
        return objective;
    }
}
