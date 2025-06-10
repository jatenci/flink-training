package com.tuempresa.flinkapp;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MinimalFlinkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements("a", "b", "c")
           .map(s -> s.toUpperCase())
           .print();

        env.execute("Test simple de Flink");
    }
}
