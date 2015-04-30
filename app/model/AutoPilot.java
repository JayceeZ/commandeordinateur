package app.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
            g.drawString(String.valueOf(i), c.getXf()-2, c.getYf()-2);
            g.drawLine(c.getXf()-3,c.getYf()-3,c.getXf()+3,c.getYf()+3);
            g.drawLine(c.getXf()-3,c.getYf()+3,c.getXf()+3,c.getYf()-3);
            i++;
        }
    }

    public void switchNext() {
        if(state < commands.size())
            state++;
        else
            state = 0;
    }
}
