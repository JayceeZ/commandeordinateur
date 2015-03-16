package app.view;

import javax.swing.*;

/**
 * Created by isoard on 10/03/15.
 */
public class InfoPanel extends JPanel {
    private final JLabel objectParams;

    public InfoPanel() {
        objectParams = new JLabel("waiting for data ...");
        this.add(objectParams);
    }

    public void display(String text) {
        objectParams.setText(text);
    }
}
