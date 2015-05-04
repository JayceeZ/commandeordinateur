package app.model;

import app.Solver;
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

    private Solver solver;
    private double theta;

    private MovableObject launchedObject;

    private double[] solution;
    private int count;
    private Command commandeDePose;

    public Observer(double x0, double y0, double R, double theta, double w) {
        this.x0 = x0;
        this.y0 = y0;
        this.R = R;
        this.theta0 = theta;
        this.w = w;
        this.t = 0;
        this.solver = new Solver();
        this.count = 0;
        solution = new double[4];

        this.launchedObject = null;
    }

    public void actualizePosition() {
        count++;
        this.xp = R * Math.cos(w * t + theta0) + x0;
        this.yp = R * Math.sin(w * t + theta0) + y0;
        this.t += Field.Te;

        if(launchedObject != null)
            System.out.println(launchedObject.getCommands());

        if (count == 5) {
            count = 0;
            solution = solver.getPosition(xp, yp, theta, t);
            if(launchedObject != null) {
                // mettre à jour l'origine de launchedObject
                commandeDePose.setXi(launchedObject.getX());
                commandeDePose.setYi(launchedObject.getY());

                // mettre à jour la destination de launchedObject
                commandeDePose.setXf(solution[0]);
                commandeDePose.setYf(solution[1]);

                // activation commande
                launchedObject.setCommandOn(true);
            }
        }

        if(launchedObject != null)
            launchedObject.actualize();
    }

    public double getThetaObs(double xm, double ym) {
        theta = Math.atan2(ym - yp, xm - xp);
        return theta + Math.PI / 2;
    }

    public void dessine(Graphics g) {
        g.drawOval((int) xp - SIZE_OBJECT / 2, (int) yp - SIZE_OBJECT / 2, SIZE_OBJECT, SIZE_OBJECT);

        if(launchedObject != null)
            launchedObject.dessine(g);
    }

    public int getY() {
        return (int) yp;
    }

    public int getX() {
        return (int) xp;
    }

    public String getEstimation() {
        String estimation = "x0:" + solution[0] + " y0:" + solution[1] + " vx: " + solution[2] + " vy:" + solution[3];
        System.out.println(estimation);
        return estimation;
    }

    public void launchObject() {
        launchedObject = new MovableObject(1, getX(), getY());

        // attribution de la commande
        AutoPilot autopilote = new AutoPilot();
        commandeDePose = new Command(solution[0], solution[1], 0.5255541, 1.0252537);
        commandeDePose.setXi(getX());
        commandeDePose.setYi(getY());
        autopilote.addCommand(commandeDePose);
        launchedObject.setCommands(autopilote);
    }

    public boolean isLaunchedObject() {
        return launchedObject != null;
    }

    public MovableObject getLaunchedObject() {
        return launchedObject;
    }
}
