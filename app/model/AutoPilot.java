package app.model;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by isoard on 30/04/15.
 */
public class AutoPilot {
    private List<Command> commands;
    private int state;

    public AutoPilot() {
        state = 0;
        commands = new ArrayList<Command>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public Command getCommandState() {
        if(commands.size() > state)
            return commands.get(state);
        return new Command();
    }

    public void drawPoints(Graphics g) {
        int i = 0;
        for(Command c:commands) {
            int x = (int) c.getXf();
            int y = (int) c.getYf();

            g.drawString(String.valueOf(i), x-2, y-2);
            g.drawLine(x-3,y-3,x+3,y+3);
            g.drawLine(x-3,y+3,x+3,y-3);
            i++;
        }
    }

    public void switchNext() {
        if(state < commands.size())
            state++;
        else
            state = 0;
    }

    public static AutoPilot loadFromFile(String path) {
        AutoPilot ap = new AutoPilot();
        File file = new File(path);

        if(file.canRead()) {
            System.out.println("\nFichier "+file+" chargé");
            try {
                Scanner reader = new Scanner(new FileInputStream(file)).useLocale(Locale.US);
                double[] values = new double[4];
                int i = 0;
                while(reader.hasNext()) {
                    if(!reader.hasNextDouble()) {
                        System.out.println("Caractère ignoré: "+reader.next());
                        continue;
                    }
                    values[i] = reader.nextDouble();
                    //System.out.println("valeur " + i + " vaut " + values[i]);
                    i++;
                    if (i == 4) {
                        ap.addCommand(new Command(values[0], values[1], values[2], values[3]));
                        values = new double[4];
                        i = 0;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Fichier non trouvé");
            }
        }
        return ap;
    }

    @Override
    public String toString() {
        return commands.toString();
    }
}
