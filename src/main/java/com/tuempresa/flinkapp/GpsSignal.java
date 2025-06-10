package com.tuempresa.flinkapp;
public class GpsSignal {
    public String assetId;
    public int speed;
    public long timestamp;

    public GpsSignal(){};

    public GpsSignal(String assetId, int speed, long timestamp){
        this.assetId = assetId;
        this.speed = speed;
        this.timestamp = timestamp;

    }

    public String toString(){
        return "GpsSignal" + assetId + "speed" + speed + "timestamp" + timestamp;
    }

    public String getAssetId(){
        return assetId;
    }
    public int getSpeed(){
        return speed;
    }
    public long getTimestamp(){
        return timestamp;
    }

}
