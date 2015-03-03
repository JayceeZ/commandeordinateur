package projetcommandeordinateur.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isoard on 03/03/15.
 */
public class Window extends JFrame {
    Field field;

    public Window() {
        super();
        this.setLayout(new BorderLayout());
        this.setSize(800,600);
        this.setResizable(false);

        field = new Field();
        this.add(field, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
