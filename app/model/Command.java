package app.model;

/**
 * Created by alex on 17/3/2558.
 */
public class Command {
    private double vxf;
    private double vyf;
    private double xf;
    private double yf;
    private double ka;
    private double kb;

    public Command(double x, double y, double vx, double vy, double k1, double k2) {
        this.xf = x;
        this.yf = y;
        this.vxf = vx;
        this.vyf = vy;
        this.ka = k1;
        this.kb = k2;
    }

    public Command(double x, double y, double k1, double k2) {
        this.xf = x;
        this.yf = y;
        this.vxf = 0;
        this.vyf = 0;

        this.ka = k1;
        this.kb = k2;
    }

    public Command() {
        this.xf = 0;
        this.yf = 0;
        this.ka = 1;
        this.kb = 1;
    }

    public double getXf() {
        return xf;
    }

    public double getYf() {
        return yf;
    }

    public void setXf(double x) {
        xf = x;
    }

    public void setYf(double y) {
        yf = y;
    }

    public double getKa() {
        return ka;
    }

    public double getKb() {
        return kb;
    }

    public String toString() {
        return "aller à ("+xf+","+yf+")";
    }

    public double getVxf() {
        return vxf;
    }

    public void setVxf(double vxf) {
        this.vxf = vxf;
    }

    public double getVyf() {
        return vyf;
    }

    public void setVyf(double vyf) {
        this.vyf = vyf;
    }
}
