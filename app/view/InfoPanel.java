package app.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isoard on 10/03/15.
 */
public class InfoPanel extends JPanel {
    private final JTextArea objectParams;

    public InfoPanel(Dimension dimension) {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);

        this.setLayout(layout);

        objectParams = new JTextArea("Waiting for data ...");

        objectParams.setEditable(false);
        objectParams.setCursor(null);
        objectParams.setOpaque(false);
        objectParams.setFocusable(false);
        objectParams.setLineWrap(true);
        objectParams.setWrapStyleWord(false);

        objectParams.setMargin(new Insets(5,5,5,5));

        this.add(objectParams, BorderLayout.NORTH);

        this.setPreferredSize(dimension);
    }

    public void display(String text) {
        objectParams.setText(text);
    }
}
