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

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;



public class ConnectedStreamsExample {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		
		//fuente de señales de datos

		// Señales GPS
		DataStream<GpsSignal> gpsSignals = env.addSource(new SignalSimulator());
		// Reglas de velocidad
		DataStream<SpeedRule> speedRules = env.addSource(new SpeedRuleSimulator());
		
		// Conectar las dos fuentes
		ConnectedStreams<GpsSignal,SpeedRule> connectedStreams = gpsSignals.connect(speedRules);		

		//procesar las señales conectadas

		connectedStreams
			.keyBy(signal -> signal.assetId, rule -> rule.assetId)
			.process(new ConnectedProcessFunction())
			.print();	

		env.execute("Connected Streams Example");
	}
}
