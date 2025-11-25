package org.example;

import javax.swing.*;
import java.awt.*;

public class HumidityPanel extends JPanel {

    private final JLabel label;

    public HumidityPanel() {
        this.label = new JLabel();
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder("Humidity"));
    }

    public void update(int inside, int outside, boolean dehumidOn, boolean outOfRange) {
        int diff = inside - outside;
        label.setText(String.format(
                "<html>"
                        + "Inside= %d %%<br>"
                        + "Outside= %d %%<br>"
                        + "Diff= %d %%<br>"
                        + "Dehumidifier= %s"
                        + "</html>",
                inside,
                outside,
                diff,
                dehumidOn ? "ON" : "OFF"
        ));

        if (outOfRange) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.GREEN);
        }
    }
}