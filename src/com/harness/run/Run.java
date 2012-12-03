package com.harness.run;

import com.harness.accumulo.AccumuloTestHarness;
import com.harness.mysql.MySQLTestHarness;

public class Run {
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            System.exit(2);
        }

        if (args[0].equals("MySQL")) {
            MySQLTestHarness msth = MySQLTestHarness.createMySQLTestHarness(args[1]);
            msth.run();
        } else if (args[0].equals("Accumulo")) {
            AccumuloTestHarness ath = AccumuloTestHarness.createAccumuloTestHarness(args[1]);
            ath.run();
        } else {
            printUsage();
        }
        System.exit(0);
    }

    public static void printUsage() {
        System.out.println("Usage: java -cp <classpath> JavaRunner type configFile");
        System.out.println("    type: Test type to run. Must be MySQL or CouchBase");
        System.out.println("    configFile: Configuration file for the test");      
    }
}