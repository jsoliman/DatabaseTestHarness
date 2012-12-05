package com.harness.accumulo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.Class;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.Map;

import com.google.gson.Gson;

import com.harness.accumulo.ReadThread;
import com.harness.common.TestHarness;

public class AccumuloTestHarness extends TestHarness {

	AccumuloConfiguration config;

	private AccumuloTestHarness() {
		this.config = new AccumuloConfiguration();
	}

	public static AccumuloTestHarness createAccumuloTestHarness(String filename) {
		AccumuloTestHarness ath = new AccumuloTestHarness();
		ath.init(filename);
		return ath;
	}

    protected void init(String filename) {
        try {
            FileReader f = new FileReader(filename);
            Gson gson = new Gson();
            config = gson.fromJson(f, AccumuloConfiguration.class);
            outputContent = "";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        int messagesPerIteration = config.getMessagesPerIteration();

        outputContent += "Messages Per Iteration: " + messagesPerIteration + "\n";
        outputContent += "Number of Iterations: " + config.getIterations() + "\n";

    }

	protected Map<String, Long> dispatchEvents() {
	    ConcurrentHashMap<String, Long> callTimes = new ConcurrentHashMap<String, Long>();	

	    int totalIterations = config.getIterations();
	    int messagesPerIteration = config.getMessagesPerIteration();

	    String workloadType = config.getWorkloadType();

		CountDownLatch countDownLatch = new CountDownLatch (totalIterations * messagesPerIteration);

	    try {
			//Instance instance = new ZooKeeperInstance(config.getZooKeeperInstance(), config.getZooServers());
			//Connection connection = instance.getConnector(config.getAccumuloUser(), config.getAccumuloPassword());

	        for (int iteration = 0; iteration < totalIterations; iteration++) {
	        	if (workloadType.equals("read")) {
	        		// Spawn read thread
	        		new ReadThread(countDownLatch, callTimes, iteration, config.getZooKeeperInstance(), config.getZooKeeperInstance(), config.getAccumuloUser(), config.getAccumuloPassword(), config.getTable(), config.getAuthorization(), config.getRangeStart(), config.getRangeEnd());
	        	}
	        	else if (workloadType.equals("write")) {
	        		// Spawn write thread
	        	}
	        	else if (workloadType.equals("mixed")) {
	        		// Spawn a mixed result thread
	        	}
	        	else if (workloadType.equals("map_reduce")) {
	        		// Spawn a mapreduce thread
	        	}
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return callTimes;
	}

    protected void printResults(Map<String, Long> timePerCall) {
        int time = 0;

        for (String i : timePerCall.keySet()) {
            time += timePerCall.get(i);
            outputContent += "Latency for " + i + ", " + timePerCall.get(i) + "\n";
        }

        outputContent += "Total Time, " + time;
        try{
            FileWriter fstream = new FileWriter(config.getOutputFilename());
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(outputContent);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}