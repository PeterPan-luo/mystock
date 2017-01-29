package org.freemoney.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.freemoney.db.DataBaseManager;
import org.freemoney.model.StockList;

public class StockListDAO extends AbstractDAO{

	public StockListDAO() {
		super();
	}
	/**
	 * ����һ����¼
	 * @param record
	 */
	public void insert(StockList record){
		PreparedStatement st=null;
		try{
			String insertSql="insert into stocklist values(?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			st.setInt(1,record.getQuarter());//����
			st.setString(2,record.getStockid());//��Ʊ����
			st.setString(3,record.getStockname());//��Ʊ����
			st.setLong(4,record.getTotal());//�ֲ�����
			st.setFloat(5,record.getCircleratio());//��ͨ��ռ��
			st.execute();
			log.info(insertSql);
		}catch(SQLException e){
			log.error("insert error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
	}
	/**
	 * ��������������
	 * @param quarter
	 */
	public void delete(int quarter){
		PreparedStatement st=null;
		try{
			String sql="delete from stocklist where quarter=?";
			st=con.prepareStatement(sql);
			st.setInt(1,quarter);	//����
			st.execute(sql);
			log.info(sql);
		}catch(SQLException e){
			log.error("insert error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
	}
	
	/**
	 * ��ȡĳһ���ȹ�Ʊ�ֲ�����б�
	 * @param quarter
	 * @return
	 */
	public Map<String, StockList> getStockList(int quarter) {
		Map<String, StockList> stockMap=new HashMap<String,StockList>();
		Statement st=null;
		try{
			String sql="select * from stocklist where quarter="+quarter;
			log.info(sql);
			st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				StockList record=new StockList();
				record.setQuarter(quarter);
				record.setStockid(rs.getString("stockid"));
				record.setStockname(rs.getString("stockname"));
				record.setTotal(rs.getLong("total"));
				record.setCircleratio(rs.getFloat("circleratio"));
				stockMap.put(record.getStockid(), record);
			}
		}catch(SQLException e){
			log.error("getStockList error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
		return stockMap;
	}
}
