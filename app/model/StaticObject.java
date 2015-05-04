package app.model;

import java.awt.*;

/**
 * Created by isoard
 */
public class StaticObject {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Graphics graphic;
    private boolean destination;

    public StaticObject(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.destination = false;
    }

    /**
     * Test si le point (x, y) appartient à l'objet statique
     *
     * @param x
     * @param y
     * @return
     */
    public boolean appartient(double x, double y) {
        if (x > x1 && x < x2) {
            if (y > y1 && y < y2) {
                return true;
            }
        }
        return false;
    }

    public void dessine(Graphics g) {
        this.graphic = g;

        if(destination)
            g.setColor(Color.GREEN);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);

        g.setColor(Color.BLACK);

        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y2, x2, y1);
    }

    public void colorify(Color color) {
        graphic.setColor(color);
        graphic.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public String toString() {
        return "StaticObject\nCovered Zone inside (" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")";
    }

    public double getTopY() {
        return y1;
    }

    public boolean isDestination() {
        return destination;
    }

    public void setDestination(boolean destination) {
        this.destination = destination;
    }
}
