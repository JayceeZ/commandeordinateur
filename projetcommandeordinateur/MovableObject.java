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
    double vx;
    double vy;
    double ax;
    double ay;
    double px;
    double py;
    double Te;
    final double G;
    
    public MovableObject(double m, double xi, double yi, double te){
        this.G = 9.81;
        this.m = m;
        this.X = xi;
        this.Y = yi;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.px = 0;
        this.py = 0;
        this.Te = te;
    }
    
    public void changePx(double npx){
        this.px = npx;
    }
    
    public void changePy(double npy){
        this.py = npy;
    }
    
    public void actualizeSpeed(int te){
        vx +=  ( px / m ) * Te;
        vy += ( py / m - G ) * Te; 
    }
    
    public void actualizePosition(int te){
        X += ( px / ( 2 * m ) ) * Te * Te;
        Y += ( py / ( 2 * m ) - G ) * Te * Te;
    }

    public int getX() {
        return (int) X;
    }

    public int getY() {
        return (int) Y;
    }
}
