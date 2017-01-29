package org.freemoney.db;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataBaseManager {
	protected static final Log log=LogFactory.getLog(DataBaseManager.class.getName());
	private static DataBaseManager _instance=null;
	private static DataSource ds;
	
	public static DataBaseManager getInstance(){
		if(_instance==null){
			_instance=new DataBaseManager();
		}
		return _instance;
	}
	
	static{
		try{
			Properties prop=new Properties();
			InputStream is=DataBaseManager.class.getClassLoader().getResourceAsStream("dbcp.properties");
			URL url = DataBaseManager.class.getClassLoader().getResource("dbcp.properties");
			System.out.println("============" + url);
			prop.load(is);
			ds=BasicDataSourceFactory.createDataSource(prop);
			log.info("databasepool init success!idle="+((BasicDataSource)ds).getNumIdle());
		}catch(Exception e){
			log.info("databasepool init error:"+e);
			e.printStackTrace();
			throw new ExceptionInInitializerError("databasepool init error!");
		}
	}
	
	/**
	 * 从连接池中获取一个连接
	 * @return
	 */
	public static Connection getConnection(){
		try{
			Connection con=ds.getConnection();
			return con;
		}catch(Exception e){
			log.info("getConnection fail:",e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 释放连接
	 * @param con
	 */
	public static void releaseConnection(Connection con){
		try{
			if(con!=null && !con.isClosed())
				con.close();
		}catch(SQLException ex){
			log.info("releaseConnection fail:",ex);
			ex.printStackTrace();
		}
	}
	
	/**
	 * 关闭statement
	 * @param stateMent
	 * @param result
	 */
	public static void closeStatement(Statement stateMent,ResultSet result){
		try{
			if(result!=null){
				result.close();
			}
		}catch(SQLException e){
			log.error("closeStatement error:",e);
			e.printStackTrace();
		}
		
		try{
			if(stateMent!=null){
				stateMent.close();
			}
		}catch(SQLException e){
			log.error("closeStatement error:",e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭statement
	 * @param st
	 */
	public static void closeStatement(Statement st){
		if(st!=null){
			try{
				st.close();
			}catch(SQLException e){
				log.error("closeStatement error:",e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 通用查询方法
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql){
		ResultSet rset=null;
		Statement _stmt=null;
		Connection con=getConnection();
		try{
			_stmt=con.createStatement();
			rset=_stmt.executeQuery(sql);
		}catch(Exception e){
			log.error("executeQuery error:",e);
			e.printStackTrace();
		}finally{
			closeStatement(_stmt);
			releaseConnection(con);
		}
		return rset;
	}
	
	/**
	 * 通用增删改方法
	 * @param sql
	 */
	public void updateDB(String sql){
		Connection con=getConnection();
		Statement _stmt=null;
		try{
			_stmt=con.createStatement();
			_stmt.executeUpdate(sql);
		}catch(Exception e){
			log.error("updateDB error:",e);
			e.printStackTrace();
		}finally{
			closeStatement(_stmt);
			releaseConnection(con);
		}
	}
	
	public static void main(String[] args){
		Connection con=getConnection();
		releaseConnection(con);
		try{
			Thread.currentThread().sleep(60*1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
