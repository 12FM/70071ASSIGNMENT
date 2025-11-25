package org.example;

import javax.swing.*;
import java.awt.*;

public class PollutionPanel extends JPanel {

    private final JLabel label;

    public PollutionPanel() {
        this.label = new JLabel();
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder("PM2.5"));
    }


    public void update(float pollution, boolean purifierOn, boolean outOfRange) {
        label.setText(String.format(
                "<html>"
                        + "Pollution= %.1f<br>"
                        + "Air Purifier= %s"
                        + "</html>",
                pollution,
                purifierOn ? "ON" : "OFF"
        ));

        if (outOfRange) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.GREEN);
        }
    }
}