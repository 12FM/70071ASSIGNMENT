package org.example;

public class WardInfo {
    private final int wardId;
    private final String name;
    private final float idealTemperature;

    public WardInfo(int wardId, String name, float idealTemperature) {
        this.wardId = wardId;
        this.name = name;
        this.idealTemperature = idealTemperature;
    }

    public int getWardId() {
        return wardId;
    }

    public String getName() {
        return name;
    }

    public float getIdealTemperature() {
        return idealTemperature;
    }
}