package com.tuempresa.flinkapp;

import java.util.Random;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class SignalSimulator implements SourceFunction<GpsSignal> {
    private volatile boolean running = true;
    private Random rand = new Random();
    private String[] ids = {"TRUCK-1", "TRUCK-2"};

    public void run(SourceContext<GpsSignal> ctx) throws InterruptedException {
        while (running) {
            String id = ids[rand.nextInt(ids.length)];
            int speed = 40 + rand.nextInt(50);
            ctx.collect(new GpsSignal(id,speed,System.currentTimeMillis()));
            Thread.sleep(1000);
        }
    }

    public void cancel() { running = false; }

}
