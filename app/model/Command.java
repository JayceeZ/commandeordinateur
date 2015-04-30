package app.model;

/**
 * Created by alex on 17/3/2558.
 */
public class Command {
    private int xf;
    private int yf;
    private double kx;
    private double ky;

    public Command(int x, int y, double k1, double k2) {
        this.xf = x;
        this.yf = y;
        this.kx = k1;
        this.ky = k2;
    }

    public Command() {
        this.xf = 0;
        this.yf = 0;
        this.kx = 0;
        this.ky = 0;
    }

    public int getXf() {
        return xf;
    }

    public int getYf() {
        return yf;
    }

    public double getKx() {
        return kx;
    }

    public double getKy() {
        return ky;
    }

    public String toString() {
        return "aller à ("+xf+","+yf+")";
    }
}
