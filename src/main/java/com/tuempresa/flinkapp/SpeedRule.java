package com.tuempresa.flinkapp;

public class SpeedRule {
    public String assetId;
    public int threshold;

    public SpeedRule() {}

    public SpeedRule(String assetId, int threshold) {
        this.assetId = assetId;
        this.threshold = threshold;
    }

    public String toString() {
        return "[Rule] " + assetId + " -> " + threshold + " km/h";
    }
}