package com.harness.common;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.io.Text;

import org.apache.zookeeper.KeeperException;

import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.BatchWriterConfig;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.MultiTableBatchWriter;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.accumulo.core.security.ColumnVisibility;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.MutationsRejectedException;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;


public class Client {
	private String instanceName;
	private String zooServers;
	private ZooKeeperInstance instance;
	private Connector connector;
	private MultiTableBatchWriter mtbw;

	/**
	 * Generate a client to access Accumulo. Writer will use the default settings.
	 * @param instanceName Accumulo Instance name to use.
	 * @param zooServers   Comma separated list of zoo keeper servers
	 * @param username     Username to log in to Accumulo
	 * @param password     Password to log in to Accumulo
	 */
	public Client(String instanceName, String zooServers, String username, String password) 
	 throws AccumuloException, AccumuloSecurityException {
		this.instanceName = instanceName;
		this.zooServers = zooServers;
		this.instance = new ZooKeeperInstance(instanceName, zooServers);
		this.connector = this.instance.getConnector(username, password);
		this.mtbw =  connector.createMultiTableBatchWriter(new BatchWriterConfig());
	}

	/**
	 * Generate a client with the specified writer configuration
	 * @param instanceName      Name of the Accumulo instance to use.
	 * @param zooServers        Comma separated list of zoo keeper server names.
	 * @param username          Username to log in to Accumulo
	 * @param password          Password to log in to Accumulo
	 * @param batchWriterConfig Configuration to use for the writer.
	 */
	public Client(String instanceName, String zooServers, String username, String password, BatchWriterConfig batchWriterConfig) 
	 throws AccumuloException, AccumuloSecurityException {
		this.instanceName = instanceName;
		this.zooServers = zooServers;
		this.instance = new ZooKeeperInstance(instanceName, zooServers);
		this.connector = this.instance.getConnector(username, password);
		this.mtbw = connector.createMultiTableBatchWriter(batchWriterConfig);
	}

	public void addInsert(String tableName, String rowId, String colFam, String colQual, ColumnVisibility colVis, Value value, boolean withTimestamp) 
	 throws MutationsRejectedException, AccumuloException, AccumuloSecurityException {
		BatchWriter bw = createBatchWriter(tableName);
		Mutation mutation = new Mutation(new Text(rowId));
		if (withTimestamp) {
			mutation.put(new Text(colFam), new Text(colQual), colVis, value);
		} else {
			long timestamp = System.currentTimeMillis();	
			mutation.put(new Text(colFam), new Text(colQual), colVis, timestamp, value);
		}
		bw.addMutation(mutation);
	}

	public void addInsert(String tableName, String rowId, String colFam, String colQual, Value value, boolean withTimestamp) 
	 throws MutationsRejectedException, AccumuloException, AccumuloSecurityException {
		BatchWriter bw = createBatchWriter(tableName);
		Mutation mutation = new Mutation(new Text(rowId));
		if (withTimestamp) {
			mutation.put(new Text(colFam), new Text(colQual), value);
		} else {
			long timestamp = System.currentTimeMillis();	
			mutation.put(new Text(colFam), new Text(colQual), timestamp, value);
		}
		bw.addMutation(mutation);
	}


	public BatchWriter createBatchWriter(String tableName) throws AccumuloException, AccumuloSecurityException {
		createTable(tableName);
		BatchWriter bw = null;
		try {
			bw = mtbw.getBatchWriter(tableName);
		} catch (TableNotFoundException ex) {
			ex.printStackTrace();
		}
		return bw;
	}

	public void createTable(String tableName) throws AccumuloException, AccumuloSecurityException {
		if (!connector.tableOperations().exists(tableName)) {
			try {
				connector.tableOperations().create(tableName);
			} catch(TableExistsException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void flush() throws MutationsRejectedException {
		mtbw.flush();
	}

	public void close() throws MutationsRejectedException {
		mtbw.close();
	}

	public Iterator<Entry<Key, Value>> read(String tableName, Authorizations authorization, Range range) throws TableNotFoundException {
		Scanner scanner = connector.createScanner(tableName, authorization);

		scanner.setRange(range);
		return scanner.iterator();
	}
}