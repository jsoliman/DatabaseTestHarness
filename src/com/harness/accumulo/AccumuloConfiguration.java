package com.harness.accumulo;


import com.harness.common.Configuration;

public class AccumuloConfiguration extends Configuration {
	private String zookeeperInstance;
	private String zooServers;
	private String accumuloUser;
	private String accumuloPassword;
	private String table;
	private String authorization;
	private String rangeStart;
	private String rangeEnd; 

	public AccumuloConfiguration() {}

	public String getZooKeeperInstance() { return zookeeperInstance; }
	public String getZooServers() { return zooServers; }
	public String getAccumuloUser() { return accumuloUser; }
	public String getAccumuloPassword() { return accumuloPassword; }
	public String getTable() { return table; }
	public String getAuthorization() { return authorization; }
	public String getRangeStart() { return rangeStart; }
	public String getRangeEnd() { return rangeEnd; }

	public void setZooKeeperInstance(String zookeeperInstance) { this.zookeeperInstance = zookeeperInstance; }
	public void setZooServers(String zooServers) { this.zooServers = zooServers; }
	public void setAccumuloUser(String accumuloUser) { this.accumuloUser = accumuloUser; }
	public void setAccumuloPassword(String accumuloPassword) { this.accumuloPassword = accumuloPassword; }
	public void setAuthorization(String authorization) { this.authorization = authorization; }
	public void setTable(String table) { this.table = table; }
	public void setRangeStart(String rangeStart) { this.rangeStart = rangeStart; }
	public void setRangeEnd(String rangeEnd) { this.rangeEnd = rangeEnd; }

}