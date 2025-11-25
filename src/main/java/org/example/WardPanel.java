package org.example;

import javax.swing.*;
import java.awt.*;

public class WardPanel extends JPanel {

    private final WardInfo wardInfo;
    private final JLabel label;

    public WardPanel(WardInfo wardInfo) {
        this.wardInfo = wardInfo;
        this.label = new JLabel();
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder(wardInfo.getName() + " Ward"));
    }


    public void update(float currentTemp, boolean heatingOn, boolean outOfRange) {

        label.setText(String.format(
                "<html>"
                        + "Ward= %s<br>"
                        + "Current= %.1f °C<br>"
                        + "Ideal= %.1f °C<br>"
                        + "Heating= %s"
                        + "</html>",
                wardInfo.getName(),
                currentTemp,
                wardInfo.getIdealTemperature(),
                heatingOn ? "ON" : "OFF"
        ))
;
        if (outOfRange) {
            setBackground(Color.RED);
        } else {

            setBackground(Color.GREEN);
        }
    }

    public WardInfo getWardInfo() {
        return wardInfo;
    }
}