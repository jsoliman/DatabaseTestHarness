package com.cpe560.couchbase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Properties;
import java.net.URI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class ReadThread implements Runnable {
    private Thread t;
    private int id;
    private ConcurrentHashMap<String, Long> map;
    private final CountDownLatch countDownLatch;
    private String query;
    private String userName;
    private String password;
    private String port;
    private String database;
    private String serverName;

    public ReadThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Long> map, 
                                int id, String serverName, String port, String database,
                                String username, String password, String query) {
        this.countDownLatch = countDownLatch;
        t = new Thread(this, "ReadRequestThread");
        this.map = map;
        this.id = id;
        this.query = query;
        this.userName = username;
        this.password = password;
        this.serverName = serverName;
        this.port = port;
        this.database = database;

        t.start();
    }

    public Connection generateConnection(String serverName, String port, 
                                        String database, String userName, 
                                        String password) throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();

        try {
           Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
           e.printStackTrace();
        }
        conn = DriverManager.getConnection(
                   "jdbc:mysql://" +
                   serverName +
                   ":" + port + "/"
                   + database,
                   connectionProps);
        System.out.println("Connected to database");
        return conn;
    }

    public void run() {
        long startTime = 0;
        long endTime = 0;

        Connection connection = null;

        try {
            connection = generateConnection(serverName, port, this.database, userName, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Starting " + id + "\n"); 
        try {
            startTime = System.currentTimeMillis();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(this.query);
            endTime = System.currentTimeMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(id);
        map.put("Time to read " + id, endTime - startTime);
        countDownLatch.countDown();
    }

}