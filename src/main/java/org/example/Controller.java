package org.example;

public class Controller {

    private final EnvironmentalSystem envSystem;

    
    public static final float NORTH = 23.0f;
    public static final float CENTRAL = 22.0f;
    public static final float SOUTH = 20.0f;

    public Controller(EnvironmentalSystem envSystem) {
        this.envSystem = envSystem;
    }

    public EnvironmentalSystem getEnvSystem() {
        return envSystem;
    }
    
    public void heatingNow() {
        TemperatureFeed tempFeed = envSystem.getTempFeed();

        controlHeatingForWard(0, tempFeed.getTemperature(0), NORTH);
        controlHeatingForWard(1, tempFeed.getTemperature(1), CENTRAL);
        controlHeatingForWard(2, tempFeed.getTemperature(2), SOUTH);
    }

    private void controlHeatingForWard(int wardId, float tempNow, float aim) {
        if (tempNow < aim - 0.5f) {
            envSystem.turnHeatingOn(wardId, true);
        } else if (tempNow > aim + 0.5f) {
            envSystem.turnHeatingOn(wardId, false);
        }
    }


    public void dehumidifierNow() {
        HumidityFeed humidityFeed = envSystem.getHumidityFeed();
        int inside = humidityFeed.getInsideHumidity();
        int outside = humidityFeed.getOutsideHumidity();

        boolean states = inside > outside + 10;
        envSystem.turnDehumudifierOn(states);
    }


    public void airPurifierNow() {
        PollutionFeed pollutionFeed = envSystem.getPollutionFeed();
        float pollution = pollutionFeed.getPollution();

        boolean shouldOn = pollution > 5.0f;
        envSystem.turnAirPurifierOn(shouldOn);
    }
}
