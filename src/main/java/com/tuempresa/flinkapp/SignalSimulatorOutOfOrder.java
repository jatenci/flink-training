package com.tuempresa.flinkapp;

import java.util.Random;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;
import org.apache.flink.streaming.api.functions.source.SourceFunction;



public class SignalSimulatorOutOfOrder implements SourceFunction<GpsSignal> {

    private final Random rand = new Random();
    private volatile boolean running = true;

    public void run(SourceContext<GpsSignal> ctx) throws InterruptedException {
            ctx.collect(new GpsSignal("TRUCK-1",100,10_000L));
            Thread.sleep(1000);
            ctx.collect(new GpsSignal("TRUCK-1",60,4_000L));
            Thread.sleep(1000);
            ctx.collect(new GpsSignal("TRUCK-1",90,16_000L));
            Thread.sleep(1000);
            ctx.collect(new GpsSignal("TRUCK-1",150,5_000L));
            Thread.sleep(1000);
            ctx.collect(new GpsSignal("TRUCK-1",90,21_000L));
        }

    public void cancel() { running = false; }

    }


