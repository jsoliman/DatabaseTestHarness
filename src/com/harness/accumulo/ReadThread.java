package com.harness.accumulo;

import java.lang.Thread;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.net.URI;

import com.harness.common.Client;

import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.AccumuloException;
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

        /*Client client = null;

        try {
            client = new Client("instance", "localhost", "s22", "p");
        } catch (AccumuloException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            map.put(sw.toString(),(long)0.0);
        }
        catch (AccumuloSecurityException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            map.put(sw.toString(),(long)0.0);
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
        }*/
/*System.out.println("Sleeping");
try {
Thread.sleep(10000);
} catch (Exception ex) {
ex.printStackTrace();
}
System.out.println("Awake");
countDownLatch.countDown();*/
Client client = null;
        try {
            client = new Client("instance", "localhost", "s22", "p");
        } catch (AccumuloException ex) {
            ex.printStackTrace();
        }
        catch (AccumuloSecurityException ex) {
            ex.printStackTrace();
        }
System.out.println("HERE");
        try {
            startTime = System.currentTimeMillis();
            Iterator<Entry<Key, Value>> iterator = client.read("mathzor", new Authorizations("s22"), new Range(new Text("1"), new Text("1459")));
System.out.println("Is there something? " + iterator.hasNext());
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
