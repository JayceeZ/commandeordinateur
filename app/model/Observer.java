package app.model;

import app.view.Field;

import java.awt.*;

/**
 * Created by alex on 24/03/15.
 */
public class Observer {

    private static final int SIZE_OBJECT = 20;

    private double x0;
    private double y0;

    private double xp;
    private double yp;

    private double w;

    private double theta0;

    private double t;

    private double R;
    private int y;

    public Observer(double x0, double y0,double R, double theta, double w){
        this.x0 = x0;
        this.y0 = y0;
        this.R = R;
        this.theta0 = theta;
        this.w = w;
        this.t = 0;
    }

    public void actualizePosition(){
        this.xp = R*Math.cos(w * t + theta0) + x0;
        this.yp = R*Math.sin(w * t + theta0) + y0;
        this.t += Field.Te;
    }

    public double getThetaObs(double xm, double ym){
        return Math.atan2(ym-yp,xm-xp)+Math.PI/2;
    }

    public void dessine(Graphics g){
        g.drawOval((int) xp-SIZE_OBJECT/2, (int) yp-SIZE_OBJECT/2,SIZE_OBJECT,SIZE_OBJECT);
    }

    public int getY() {
        return (int) yp;
    }

    public int getX() {
        return (int) xp;
    }
}
