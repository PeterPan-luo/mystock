package org.freemoney.dao;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freemoney.db.DataBaseManager;

public class AbstractDAO {
	protected final Log log=LogFactory.getLog(getClass());
	protected Connection con;
	
	/**
	 * �����ݿ����ӳ��л�ȡ���ݿ�����
	 */
	public void getConnection(){
		con=DataBaseManager.getConnection();
	}
	/**
	 * �ͷ����ݿ�����
	 */
	public void releaseConnection(){
		DataBaseManager.releaseConnection(con);
	}
}
