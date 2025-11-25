package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class WardSensorsApp {

    private JFrame frame;
    private WardPanel northPanel;
    private WardPanel centralPanel;
    private WardPanel southPanel;
    private HumidityPanel humidityPanel;
    private PollutionPanel pollutionPanel;

    private final Controller controller;

    public WardSensorsApp() {
        EnvironmentalSystem envSystem = new EnvironmentalSystem();
        this.controller = new Controller(envSystem);
        createAndShowGUI();
        setupTimers();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Andressey Hospital Environmental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Grid: 5 行（3 个病房 + 湿度 + 污染）
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        northPanel = new WardPanel(new WardInfo(0, "North", Controller.NORTH));
        centralPanel = new WardPanel(new WardInfo(1, "Central", Controller.CENTRAL));
        southPanel = new WardPanel(new WardInfo(2, "South", Controller.SOUTH));

        humidityPanel = new HumidityPanel();
        pollutionPanel = new PollutionPanel();
        mainPanel.setBackground(Color.BLUE);
        mainPanel.add(northPanel);
        mainPanel.add(centralPanel);
        mainPanel.add(southPanel);
        mainPanel.add(humidityPanel);
        mainPanel.add(pollutionPanel);

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(2000, 2000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void setupTimers() {
        // 每秒更新传感器和界面
        Timer timer1 = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiNow();
                frame.repaint();
            }
        });
        timer1.start();


        Timer timer2 = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logPrinter();
            }
        });
        timer2.start();
    }



    private void uiNow() {
        EnvironmentalSystem env = controller.getEnvSystem();

        controller.heatingNow();
        controller.dehumidifierNow();
        controller.airPurifierNow();

        TemperatureFeed tempFeed = env.getTempFeed();
        HumidityFeed humidityFeed = env.getHumidityFeed();
        PollutionFeed pollutionFeed = env.getPollutionFeed();


        float north = tempFeed.getTemperature(0);
        float central = tempFeed.getTemperature(1);
        float south = tempFeed.getTemperature(2);

        boolean northH = env.isHeatingOn(0);
        boolean centralH = env.isHeatingOn(1);
        boolean southH = env.isHeatingOn(2);

        boolean alarmNH = Math.abs(north - Controller.NORTH) > 0.5f;
        boolean alarmCH = Math.abs(central - Controller.CENTRAL) > 0.5f;
        boolean alarmSH = Math.abs(south - Controller.SOUTH) > 0.5f;

        northPanel.update(north, northH, alarmNH);
        centralPanel.update(central, centralH, alarmCH);
        southPanel.update(south, southH, alarmSH);


        int inside = humidityFeed.getInsideHumidity();
        int outside = humidityFeed.getOutsideHumidity();
        boolean dehumidState = env.isDehumudifierOn();
        boolean alarmH = inside > outside + 10;

        humidityPanel.update(inside, outside, dehumidState, alarmH);


        float pollution = pollutionFeed.getPollution();
        boolean pollutionState = env.isAirPurifierOn();
        boolean alarmP = pollution > 5.0f;

        pollutionPanel.update(pollution, pollutionState, alarmP);
    }


    private void logPrinter() {
        EnvironmentalSystem env = controller.getEnvSystem();
        TemperatureFeed tempFeed = env.getTempFeed();
        HumidityFeed humidityFeed = env.getHumidityFeed();
        PollutionFeed pollutionFeed = env.getPollutionFeed();

        float north = tempFeed.getTemperature(0);
        float central = tempFeed.getTemperature(1);
        float south = tempFeed.getTemperature(2);

        int inside = humidityFeed.getInsideHumidity();
        int outside = humidityFeed.getOutsideHumidity();
        float pollution = pollutionFeed.getPollution();

        boolean northH = env.isHeatingOn(0);
        boolean centralH = env.isHeatingOn(1);
        boolean southH = env.isHeatingOn(2);

        boolean dehumidState = env.isDehumudifierOn();
        boolean pollutionState = env.isAirPurifierOn();

        String ultrasoundStatus = env.getUltrasoundScannerStatus();
        String ctStatus = env.getCTScannerStatus();

        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);

        System.out.println(time);
        System.out.printf( northH ? "ON" : "OFF",north, Controller.NORTH);
        System.out.printf(centralH ? "ON" : "OFF",central, Controller.CENTRAL);
        System.out.printf(southH ? "ON" : "OFF",south, Controller.SOUTH);

        int diff = inside - outside;
        System.out.printf(dehumidState ? "ON" : "OFF",inside, outside, diff);

        System.out.printf(pollutionState ? "ON" : "OFF", pollution);

        System.out.println("Ultrasound: " + ultrasoundStatus);
        System.out.println("CT:" + ctStatus);
    }}











