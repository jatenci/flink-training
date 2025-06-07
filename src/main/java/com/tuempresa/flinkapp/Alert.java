package com.tuempresa.flinkapp;

public class Alert {
    public String assetId;
    public int speed;
    public int threshold;
    public long timestamp;

    public Alert() {}

    public Alert(String assetId, int speed, int threshold, long timestamp) {
        this.assetId = assetId;
        this.speed = speed;
        this.threshold = threshold;
        this.timestamp = timestamp;
    }

    public String toString() {
        return "[ALERT] " + assetId + " velocidad=" + speed + " (límite=" + threshold + ")";
    }
}