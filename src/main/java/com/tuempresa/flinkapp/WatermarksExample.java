/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package com.tuempresa.flinkapp;

 import org.apache.flink.api.common.eventtime.WatermarkStrategy;
 import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
 import org.apache.flink.streaming.api.datastream.DataStream;
 import org.apache.flink.streaming.api.functions.ProcessFunction;
 import org.apache.flink.util.Collector;
 
 import java.time.Duration;
 
 public class WatermarksExample {
 
	 public static void main(String[] args) throws Exception {
 
		 final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
 
		 // Watermark Strategy
		 WatermarkStrategy<GpsSignal> strategy = WatermarkStrategy
				 .<GpsSignal>forBoundedOutOfOrderness(Duration.ofSeconds(5))
				 .withTimestampAssigner((event, ts) -> event.timestamp);
 
		 // Fuente de señales GPS con asignación de timestamp + watermark
		 DataStream<GpsSignal> gpsSignals = env
				 .addSource(new SignalSimulatorOutOfOrder())
				 .assignTimestampsAndWatermarks(strategy);
 
		 // Procesar el stream con ProcessFunction (sin keyBy, sin ventanas)
		 gpsSignals
				 .process(new ProcessFunction<GpsSignal, String>() {
					 @Override
					 public void processElement(GpsSignal value, Context ctx, Collector<String> out) {

						long currentWatermark = ctx.timerService().currentWatermark();

						if (value.timestamp < currentWatermark ) {
							return;
						};
						 out.collect("Asset: " + value.assetId +
								 ", Speed: " + value.speed +
								 ", Timestamp: " + value.timestamp +
								 ", Watermark: " + ctx.timerService().currentWatermark());
					 }
				 })
				 .print();
 
		 env.execute("ProcessFunction + Watermark Example");
	 }
 }
 