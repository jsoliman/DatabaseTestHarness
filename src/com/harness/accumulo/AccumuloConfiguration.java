package com.harness.accumulo;


import com.harness.common.Configuration;

public class AccumuloConfiguration extends Configuration {
	private String zookeeperInstance;
	private String zooServers;
	private String accumuloUser;
	private String accumuloPassword;

	public AccumuloConfiguration() {}

	public String getZooKeeperInstance() { return zookeeperInstance; }
	public String getZooServers() { return zooServers; }
	public String getAccumuloUser() { return accumuloUser; }
	public String getAccumuloPassword() { return accumuloPassword; }

	public void setZooKeeperInstance(String zookeeperInstance) { this.zookeeperInstance = zookeeperInstance; }
	public void setZooServers(String zooServers) { this.zooServers = zooServers; }
	public void setAccumuloUser(String accumuloUser) { this.accumuloUser = accumuloUser; }
	public void setAccumuloPassword(String accumuloPassword) { this.accumuloPassword = accumuloPassword; }

}