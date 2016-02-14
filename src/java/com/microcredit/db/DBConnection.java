package com.microcredit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBConnection {
    
        private static final Logger log = LoggerFactory.getLogger(DBConnection.class);
	
	private static DBConnection instance = new DBConnection();
	
	/*
	 * Singleton
	 */
	public static DBConnection getInstance(){
		return instance;
	}
	
	/**
	 * Get {@link DataSource} object using in DB connection pools.
	 * @param dataSourceName	Data source name configured in server(WAS).
	 * @return {@link DataSource}
	 */
	public DataSource getDataSource(String dataSourceName){
		DataSource dataSource = null;
		try{
			Context initialContext = new InitialContext();
			Context envContext = (Context)initialContext.lookup("java:comp/env");
			dataSource = (DataSource)envContext.lookup(dataSourceName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataSource;
	}
	
	/**
	 * Get a {@link Connection} object from DB connection pool.
	 * @param dataSourceName	DataSource Name
	 * @return	{@link Connection}
	 */
	public Connection getConnection(String dataSourceName){
		Connection conn = null;
		DataSource dataSource = null;
		try{
			dataSource = this.getDataSource(dataSourceName);
			conn = dataSource.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
		
	}
        
        public static Connection getConnection() {
        Connection conn = null;
        try {
            //step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            log.error("",ex);
        }
            try {
                //step2 create  the connection object
                conn = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "mc", "mc");
            } catch (SQLException ex) {
                log.error("",ex);
            }
        return conn;
    }

}
