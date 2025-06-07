package com.tuempresa.flinkapp;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

public class ConnectedProcessFunction extends CoProcessFunction<GpsSignal, SpeedRule, Alert> {

    private ValueState<Integer> thresholdState;

                @Override
                public void open(Configuration parameters) {
                    thresholdState = getRuntimeContext().getState(
                        new ValueStateDescriptor<>("threshold", Types.INT));
                }

                @Override
                public void processElement1(GpsSignal signal, Context ctx, Collector<Alert> out) throws Exception {
                    Integer threshold = thresholdState.value();
                    if (threshold != null && signal.speed > threshold) {
                        out.collect(new Alert(signal.assetId, signal.speed, threshold, signal.timestamp));
                    }
                }

                @Override
                public void processElement2(SpeedRule rule, Context ctx, Collector<Alert> out) throws Exception {
                    thresholdState.update(rule.threshold);
                    System.out.println("Actualizada regla para " + rule.assetId + ": " + rule.threshold);
                }
}
