/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetcommandeordinateur;

import projetcommandeordinateur.view.Field;

import java.awt.*;

/**
 *
 * @author Alex
 */
public class MovableObject {

    // Taille de l'objet
    private final int size;
    /**
     * Positions initiales
     */
    double initX;
    double initY;

    double m;
    double x;
    double y;
    double vx;
    double vy;
    double ax;
    double ay;
    double px;
    double py;
    double Te;
    final double G;
    
    public MovableObject(double m, double xi, double yi, double te){

        // on définie la position initiale
        this.initX = xi;
        this.initY = yi;

        this.size = 20;

        this.G = -9.81;
        this.m = m;
        this.x = initX;
        this.y = initY;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.px = 0;
        this.py = 0;
        this.Te = te;
    }
    
    public void changePx(double npx){
        this.px += npx;
    }
    
    public void changePy(double npy){
        this.py += npy;
    }
    
    public void actualizeSpeed(){
        vx +=  ( px / m ) * Te;
        vy += ( py / m - G ) * Te; 
    }
    
    public void actualizePosition(){
        x += vx * Te;
        y += vy * Te;
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
        actualizeSpeed();
    }

    /**
     * Réinitialise la position
     */
    public void respawn() {
        stop();
        x = initX;
        y = initY;
        vx = 0;
        vy = 0;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getPx() {
        return px;
    }

    public double getPy() {
        return py;
    }

    public void dessine(Graphics g) {
        g.drawRect(this.getX(), this.getY(), size, size);

        // propulsion gauche/droite
        g.drawLine(this.getX()+size/2, this.getY()+size/2, (int) (this.getX()+size/2-px), this.getY()+size/2);
        // propulsion haut/bas
        g.drawLine(this.getX()+size/2, this.getY()+size/2, this.getX()+size/2, (int) (this.getY()+size/2-py));
    }

    public void testCollision(Field field) {
        int x = (int) this.x;
        int y = (int) this.y;
        if(field.testCollisionBord(x,y) || field.testCollisionBord(x+size, y) || field.testCollisionBord(x,y+size) || field.testCollisionBord(x+size, y+size)) {
            this.respawn();
        }
    }
}
