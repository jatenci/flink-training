package com.tuempresa.flinkapp;

import java.util.Random;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;
import org.apache.flink.streaming.api.functions.source.SourceFunction;


public class SpeedRuleSimulator implements SourceFunction<SpeedRule> {
    private volatile boolean running = true;
    private Random rand = new Random();
    private String[] ids = {"TRUCK-1", "TRUCK-2"};

    public void run(SourceContext<SpeedRule> ctx) throws InterruptedException {
        while (running) {
            String id = ids[rand.nextInt(ids.length)];
            int threshold = 60 + rand.nextInt(20);
            ctx.collect(new SpeedRule(id, threshold));
            Thread.sleep(5000);
        }
    }

    public void cancel() { running = false; }

}
