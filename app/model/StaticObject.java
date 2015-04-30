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

    public StaticObject(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Test si le point (x, y) appartient à l'objet statique
     * @param x
     * @param y
     * @return
     */
    public boolean appartient(int x, int y) {
        if(x > x1 && x < x2) {
            if(y > y1 && y < y2) {
                return true;
            }
        }
        return false;
    }

    public void dessine(Graphics g) {
        this.graphic = g;
        g.drawRect(x1, y1, x2-x1, y2-y1);
    }

    public void colorify(Color color) {
        graphic.setColor(color);
        graphic.drawRect(x1, y1, x2-x1, y2-y1);
    }

    @Override
    public String toString() {
        return "StaticObject\nCovered Zone inside ("+x1+","+y1+") ("+x2+","+y2+")";
    }

    public static StaticObject[] buildLevel() {
        StaticObject[] staticObjects = new StaticObject[8];

        staticObjects[0] = new StaticObject(0, 0, 40, 200);
        staticObjects[1] = new StaticObject(100, 100, 200, 200);
        staticObjects[2] = new StaticObject(240, 100, 400, 140);
        staticObjects[3] = new StaticObject(0, 0, 40, 200);
        staticObjects[4] = new StaticObject(0, 0, 40, 200);
        staticObjects[5] = new StaticObject(0, 0, 40, 200);
        staticObjects[6] = new StaticObject(0, 0, 40, 200);
        staticObjects[7] = new StaticObject(0, 0, 40, 200);

        return staticObjects;
    }
}
