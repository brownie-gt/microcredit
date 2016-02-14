package com.microcredit.db;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDbUtil extends DBConnection{
	private static Logger logger = LoggerFactory.getLogger(CommonDbUtil.class); //Log4j
	private final static String CLASSNAME = "com.emfrontier.db.CommonDbUtil"; //this class
	private QueryRunner qr = null; //org.apache.commons.dbutils.QueryRunner
	@SuppressWarnings("rawtypes")
	private ResultSetHandler rsh = null; //org.apache.commons.dbutils.<interface>ResultSetHandler
	
	/**
	 * Execute a SELECT SQL and get a scalar integer type value without any replacement parameters.
     * @param conn
	 * @param sql				SELECTE SQL to retrieve one Integer data.
	 * @return	{@link Integer}
	 */
	@SuppressWarnings("unchecked")
	public Integer getIntScalar(Connection conn, String sql){
		Integer intVal = 0;
		try{
			qr = new QueryRunner();
			rsh = new ScalarHandler(1);
			intVal = (Integer)qr.query(conn, sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getIntScalar(dataSourceName, sql)", e);
		}
		return intVal;
	}
        
        
        @SuppressWarnings("unchecked")
	public BigDecimal getBigDecimalScalar(Connection conn, String sql){
		BigDecimal number = new BigDecimal(BigInteger.ZERO);
		try{
			qr = new QueryRunner();
			rsh = new ScalarHandler(1);
			number = (BigDecimal)qr.query(conn, sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getIntScalar(dataSourceName, sql)", e);
		}
		return number;
	}
	
	/**
	 * Execute a SELECT SQL and get a scalar integer type value with any replacement parameters.
	 * @param dataSourceName	The name of DataSource.
	 * @param sql				SELECT SQL to get the count number.
	 * @param params			parameters for SQL.
	 * @return	{@link Integer}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer getIntScalar(String dataSourceName, String sql, ArrayList params){
		Integer intVal = 0;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ScalarHandler(1);
			intVal = (Integer)qr.query(sql, rsh, params.toArray());
		}catch(Exception e){
			logger.error("", e);
		}
		return intVal;
	}
	
	/**
	 * Get count number from SELECT SQL.
	 * @param dataSourceName	The Name of DataSource.
	 * @param sql				Select SQL to get the count number.
	 * @return	{@link Integer}
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountValue(String dataSourceName, String sql){
		Integer intVal = 0;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ScalarHandler(1);
			BigDecimal longVal = (BigDecimal)qr.query(sql, rsh);
			intVal = longVal.intValue();
		}catch(Exception e){
			logger.error(CLASSNAME+".getCountScalar(dataSourceName, sql)", e);
		}
		return intVal;
	}
	
	/**
	 * Get count number from SELECT SQL using parameters.
	 * @param dataSourceName	DataSource Name
	 * @param sql				SELECT SQL
	 * @param params			parameters
	 * @return {@link Integer}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer getCountValue(String dataSourceName, String sql, ArrayList params){
		Integer intVal = 0;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ScalarHandler(1);
			Long longVal = (Long)qr.query(sql, rsh, params.toArray());
			intVal = longVal.intValue();
		}catch(Exception e){
			logger.error(CLASSNAME+".getCountValue(dataSourceName, sql, params)", e);
		}
		return intVal;
	}
	
	/**
	 * Get a String from SELECT SQL.
	 * @param dataSourceName	DataSource Name
	 * @param sql				Select SQL to get a String.
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getStringScalar(String dataSourceName, String sql){
		String str = "";
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ScalarHandler(1);
			str = (String)qr.query(sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getStringScalar(dataSourceName, sql)", e);
		}
		return str;
	}
	
	/**
	 * Get a String from SELECT SQL with parameters.
	 * @param dataSourceName	DataSource Name.
	 * @param sql				SELECT SQL.
	 * @param params			SQL parameters.
	 * @return	String
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getStringScalar(String dataSourceName, String sql, ArrayList params){
		String str = "";
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ScalarHandler(1);
			str = (String)qr.query(sql, rsh, params.toArray());
		}catch(Exception e){
			logger.error(CLASSNAME+".getStringScalar(dataSourceName, sql, parmas)", e);
		}
		return str;
	}
	
	/**
	 * <p>Converts the first ResultSet row into a Map.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new MapHandler();</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>Map map = (Map)qr.query("SELECT count(*) as count FROM emp", rsh);</li>
	 * <li>System.out.println(map.get("count"));</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name
	 * @param sql				SELECT SQL
	 * @return {@link Map}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMapData(String dataSourceName, String sql){
		Map map = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new MapHandler();
			map = (Map)qr.query(sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getMapData(dataSourceName, sql)", e);
		}
		return map;
	}
	
	/**
	 * <p>Converts the first ResultSet row into a Map using parameters.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new MapHandler();</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>Map map = (Map)qr.query("SELECT count(*) as count FROM emp where dept=?", rsh, params);</li>
	 * <li>System.out.println(map.get("count"));</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name.
	 * @param sql				SELECT SQL.
	 * @param params			parameters.
	 * @return {@link Map}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMapData(String dataSourceName, String sql, ArrayList params){
		Map map = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new MapHandler();
			map = (Map)qr.query(sql, rsh, params.toArray());
		}catch(Exception e){
			logger.error(CLASSNAME+".getMapData(dataSourceName, sql, params)", e);
		}
		return map;
	}
	
	/**
	 * <p>Converts a ResultSet into a List of Maps.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new MapListHandler();</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>List list = (List)qr.query("SELECT * FROM emp", rsh);</li>
	 * <li>for(Iterator itr = list.iterator(); itr.hasNext();){</li>
	 * <li>Map map = (Map)i.next();</li>
	 * <li>}</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name.
	 * @param sql				SELECT SQL.
	 * @return {@link List}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getMapListData(String dataSourceName, String sql){
		List list = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new MapListHandler();
			list = (List)qr.query(sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getMapListData(dataSourceName, sql)", e);
		}
		return list;
	}
	
	/**
	 * <p>Converts a ResultSet into a List of Maps using parameters.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new MapListHandler();</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>List list = (List)qr.query("SELECT * FROM emp where dept=?", rsh, params);</li>
	 * <li>for(Iterator itr = list.iterator(); itr.hasNext();){</li>
	 * <li>Map map = (Map)i.next();</li>
	 * <li>}</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name.
	 * @param sql				SELECT SQL.
	 * @param params			parameters.
	 * @return {@link List}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getMapListData(String dataSourceName, String sql, ArrayList params){
		List list = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new MapListHandler();
			list = (List)qr.query(sql, rsh, params.toArray());
		}catch(Exception e){
			logger.error(CLASSNAME+".getMapListData(dataSourceName, sql, params)", e);
		}
		return list;
	}
	
	/**
	 * <p>converts the ResultSet into a List of Object[]s.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new ArrayListHandler();</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>List list = (List)qr.query("SELECT * FROM emp", rsh);</li>
	 * <li>for(Iterator itr = list.iterator(); itr.hasNext();){</li>
	 * <li>Map map = (Map)i.next();</li>
	 * <li>}</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name
	 * @param sql				SELECT SQL
	 * @return {@link List}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getArrayListData(String dataSourceName, String sql){
		List list = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ArrayListHandler();
			list = (List)qr.query(sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getArrayListData(dataSourceName, sql)", e);
		}
		return list;
	}
	
	/**
	 * <p>converts the ResultSet into a List of Object[]s using parameters.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new ArrayListHandler();</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>List list = (List)qr.query("SELECT * FROM emp WHERE department=? and gender=?", rsh, params);</li>
	 * <li>for(Iterator itr = list.iterator(); itr.hasNext();){</li>
	 * <li>Map map = (Map)i.next();</li>
	 * <li>}</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name
	 * @param sql				SELECT SQL
	 * @param params			parameters
	 * @return {@link List}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getArrayListData(String dataSourceName, String sql, ArrayList params){
		List list = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new ArrayListHandler();
			list = (List)qr.query(sql, rsh, params.toArray());
		}catch(Exception e){
			logger.error(CLASSNAME+".getArrayListData(dataSourceName, sql, params)", e);
		}
		return list;
	}
	
	/**
	 * <p>Converts a ResultSet into a List of beans.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new BeanListHandler(UserVO.Class);</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>List beanList = (List)qr.query("SELECT empid, dept FROM emp", rsh, UserVO.class);</li>
	 * <li>UserVO uv = new UserVO();</li>
	 * <li>for(Iterator itr = list.iterator(); itr.hasNext();){</li>
	 * <li>uv = (UserVO)itr.next();</li>
	 * <li>System.out.println("ID = " + uv.getUserName());</li>
	 * <li>System.out.println("Department = " + uv.getDeptName());</li>
	 * <li>}</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name
	 * @param sql				SELECT SQL
	 * @param bean				{@link Class}(Java Bean)
	 * @return {@link List}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getBeanListData(String dataSourceName, String sql, Class bean){
		List beanList = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new BeanListHandler(bean);
			beanList = (List)qr.query(sql, rsh);
		}catch(Exception e){
			logger.error(CLASSNAME+".getBeanListData(dataSourceName, sql, bean)", e);
		}
		return beanList;
	}
	
	/**
	 * <p>Converts a ResultSet into a List of beans using parameters.</p>
	 * <fieldset>
	 * <legend>example</legend>
	 * <ol>
	 * <li>ResultSetHandler rsh = new BeanListHandler(UserVO.Class);</li>
	 * <li>QueryRunner qr = new QueryRunner(ds);</li>
	 * <li>List beanList = (List)qr.query("SELECT name, empid FROM emp where dept=?", rsh, UserVO.class, params);</li>
	 * <li>UserVO uv = new UserVO();</li>
	 * <li>for(Iterator itr = list.iterator(); itr.hasNext();){</li>
	 * <li>uv = (UserVO)itr.next();</li>
	 * <li>System.out.println("Name = " + uv.getUserName());</li>
	 * <li>System.out.println("ID = " + uv.getEmpId());</li>
	 * <li>}</li>
	 * </ol>
	 * </fieldset>
	 * @param dataSourceName	DataSource Name
	 * @param sql				SELECT SQL
	 * @param bean				{@link Class}(Java Bean)
	 * @param params			parameters
	 * @return {@link List}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getBeanListData(String dataSourceName, String sql, Class bean, ArrayList params){
		List beanList = null;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			rsh = new BeanListHandler(bean);
			beanList = (List)qr.query(sql, rsh, params.toArray());
		}catch(Exception e){
			logger.error(CLASSNAME+".getBeanListData(dataSourceName, sql, bean, params)", e);
		}
		return beanList;
	}
	
	/**
	 * Execute an SQL INSERT, UPDATE, or DELETE query without replacement parameters.
	 * @param dataSourceName	Datasource Name.
	 * @param sql				The SQL to execute.
	 * @return					The number of rows updated.
	 */
	public int execUpdate(String dataSourceName, String sql){
		int result = 0;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			result = qr.update(sql);
		}catch(Exception e){
			logger.error(CLASSNAME+".execUpdate(dataSourceName, sql)", e);
		}
		return result;
	}
	
	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement with a single replacement parameter. 
	 * The Connection is retrieved from the DataSource set in the constructor. 
	 * This Connection must be in auto-commit mode or the update will not be saved. 
	 * @param dataSourceName	Datasource name.
	 * @param sql				The SQL statement to execute.
	 * @param params			The replacement parameter. 
	 * @return The number of rows updated. 
	 */
	@SuppressWarnings("rawtypes")
	public int execUpdate(String dataSourceName, String sql, ArrayList params){
		int result = 0;
		try{
			qr = new QueryRunner(this.getDataSource(dataSourceName));
			result = qr.update(sql, params.toArray());
		}catch(Exception e){
			logger.error(CLASSNAME+".execUpdate(dataSourceName, sql, params)", e);
		}
		return result;
	}
	
	/**
	 * Loads a Map of query names to SQL values. 
	 * The Maps are cached so a subsequent request to load queries from the same path will return the cached Map. 
	 * @param path	The path that the ClassLoader will use to find the file. 
	 * 				This is not a file system path. 
	 * 				If you had a jarred Queries.properties file in the com.yourcorp.app.jdbc package 
	 * 				you would pass "/com/yourcorp/app/jdbc/Queries.properties" to this method. 
	 * @return	Map of query names to SQL values 
	 */
	public Map<String, String> getSqlMap(String path){
		Map<String, String> sqlMap = null;
		try{
			QueryLoader queryLoader = QueryLoader.instance();
			sqlMap = queryLoader.load(path);
			queryLoader.unload(path);
		}catch(IOException ioe){
			logger.error(CLASSNAME+".getSqlMap(path)", ioe);
		}catch(Exception e){
			logger.error(CLASSNAME+".getSqlMap(path)", e);
		}
		return sqlMap;
	}
}
