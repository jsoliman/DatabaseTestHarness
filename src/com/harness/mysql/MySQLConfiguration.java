package com.harness.mysql;

import com.harness.common.Configuration;
import java.util.List;

public class MySQLConfiguration extends Configuration {
	private String userName = null;
	private String password = null;
	private String databaseName = null;
    /* Server url*/
	private String serverName = null;
	private String port = null;
    /* Query to run during read tests. */
	private String readQuery = null;
    /* SQL command to create a table in a write test. */
    private String createTable = null;
    /* SQL command to drop a table in a write test. */
    private String dropTable = null;
    private String workloadType;

    public String getCreateTable() { return this.createTable; }
    public String getDropTable() { return this.dropTable; }
	public String getUsername() { return this.userName;}
	public String getPassword() { return this.password;}
	public String getDatabaseName() { return this.databaseName;}
	public String getServerName() { return this.serverName;}
	public String getPort() { return this.port;}
	public String getReadQuery() { return this.readQuery;}
    public String getWorkloadType() { return workloadType; }

    public void setCreateTable(String createTable) { this.createTable = createTable; }
    public void setdropTable(String dropTable) { this.dropTable = dropTable; }
	public void setUsername(String userName) { this.userName = userName;}
	public void setPassword(String password) { this.password = password;}
	public void setDatabaseName(String databaseName) { this.databaseName = databaseName;}
	public void setServerName(String serverName) { this.serverName = serverName;}
	public void setPort(String port) { this.port = port;}
	public void setReadQuery(String readQuery) { this.readQuery = readQuery;}
    public void setWorkloadType(String workload) { this.workloadType = workload; }
}