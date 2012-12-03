package com.harness.common;

import java.util.Map;

public abstract class TestHarness {

	public String outputContent;

    public void run() {        
        Map<String, Long> callTimes = dispatchEvents();
        
        printResults(callTimes);
    }

    protected abstract void init(String fileName);

    protected abstract Map<String, Long> dispatchEvents();

    protected abstract void printResults(Map<String, Long> timePerCall);

}