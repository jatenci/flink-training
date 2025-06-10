package com.tuempresa.flinkapp;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import java.time.Duration;

public class WindowsExample {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        WatermarkStrategy<GpsSignal> strategy = WatermarkStrategy
               .<GpsSignal>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                .withTimestampAssigner((event, timestamp) -> event.timestamp);

        DataStream<GpsSignal> signalStream = env
            .addSource(new SignalSimulatorOutOfOrder())
            .assignTimestampsAndWatermarks(strategy);

        signalStream
            .keyBy(signal -> signal.assetId)
            .window(TumblingEventTimeWindows.of(Time.seconds(10)))
            .maxBy("speed")
            .print();



        env.execute("Windows Example");
    }

}
