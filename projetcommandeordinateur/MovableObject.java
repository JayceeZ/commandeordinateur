/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetcommandeordinateur;

/**
 *
 * @author Alex
 */
public class MovableObject {
    
    double m;
    double X;
    double Y;
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

        // pour le reset de position
        this.X = xi;
        this.Y = yi;

        this.G = 0;
        this.m = m;
        this.x = X;
        this.y = Y;
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
        x = X;
        y = Y;
        vx = 0;
        vy = 0;
    }
}
