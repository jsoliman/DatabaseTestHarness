package com.cpe560.couchbase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.net.URI;

import com.harness.common.Client;

import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.security.Authorizations;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.ColumnVisibility;
import org.apache.hadoop.io.Text;

public class WriteThread implements Runnable {
    private Thread t;
    private int id;
    private ConcurrentHashMap<String, Long> map;
    private final CountDownLatch countDownLatch;
    private String instanceName;
    private String zooServers;
    private String userName;
    private String password;
    private String table;
    private String authorization;
    private String rowId;
    private String columnFamily;
    private String columnQualifier;
    private String value;


    public WriteThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Long> map, 
                                int id, String instanceName, String zooServers, String userName,
                                String password, String table, String authorization,
                                String rowId, String columnFamily, String columnQualifier,
                                String value) {

        this.countDownLatch = countDownLatch;
        t = new Thread(this, "ReadRequestThread");
        this.map = map;
        this.id = id;
        this.instanceName = instanceName;
        this.zooServers = zooServers;
        this.userName = userName;
        this.password = password;
        this.table = table;
        this.authorization = authorization;
        this.rowId = rowId;
        this.columnFamily = columnFamily;
        this.columnQualifier = columnQualifier;
        this.value = value;
        t.start();
    }

    public void run() {
        long startTime = 0;
        long endTime = 0;

        Client client = null;

        try {
            client = new Client(this.instanceName, this.zooServers, this.userName, this.password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Starting " + id + "\n");
        try {
            startTime = System.currentTimeMillis();

            client.addInsert(this.table, this.rowId, this.columnFamily, this.columnQualifier, new ColumnVisibility(this.authorization), new Value(value.getBytes()), true);

            endTime = System.currentTimeMillis();            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put("Read " + id, endTime - startTime);
        countDownLatch.countDown();
    }

}