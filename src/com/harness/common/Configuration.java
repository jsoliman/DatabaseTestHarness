package com.harness.common;

public class Configuration {
    public Configuration() {};
    private int iterations;
    private int messagesPerIteration;
    private String outputFilename;
    private String workloadType;

    public int getIterations() { return iterations; }
    public int getMessagesPerIteration() { return messagesPerIteration; }
    public String getOutputFilename() { return outputFilename; }
    public String getWorkloadType() { return workloadType; }

    public void setIterations(int iterations) { this.iterations = iterations; }
    public void setMessagesPerIteration(int messagesPerIteration) { this.messagesPerIteration = messagesPerIteration; }
    public void setOutputFilename(String filename) { this.outputFilename = filename; }
    public void setWorkloadType(String workloadType) { this.workloadType = workloadType; }
}