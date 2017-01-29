package org.freemoney.dao;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freemoney.db.DataBaseManager;

public class AbstractDAO {
	protected final Log log=LogFactory.getLog(getClass());
	protected Connection con;
	
	/**
	 * 从数据库连接池中获取数据库连接
	 */
	public void getConnection(){
		con=DataBaseManager.getConnection();
	}
	/**
	 * 释放数据库连接
	 */
	public void releaseConnection(){
		DataBaseManager.releaseConnection(con);
	}
}
