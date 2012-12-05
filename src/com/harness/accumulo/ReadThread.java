package com.harness.accumulo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.net.URI;

import com.harness.common.Client;

import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.security.Authorizations;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.hadoop.io.Text;


public class ReadThread implements Runnable {
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
    private String rangeStart;
    private String rangeEnd;

    public ReadThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Long> map, 
                                int id, String instanceName, String zooServers, String userName,
                                String password, String table, String authorization,
                                String rangeStart, String rangeEnd) {

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
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;

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
            Iterator<Entry<Key, Value>> iterator = client.read(this.table, new Authorizations(this.authorization), new Range(this.rangeStart, this.rangeEnd));
            while (iterator.hasNext()) {
                Entry<Key, Value> entry = (Entry<Key, Value>) iterator.next(); 
                Text row = entry.getKey().getRow();
                Value value = entry.getValue();
            }

            endTime = System.currentTimeMillis();            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put("Read " + id, endTime - startTime);
        countDownLatch.countDown();
    }

}