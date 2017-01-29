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
	 * 增加一条记录
	 * @param record
	 */
	public void insert(StockList record){
		PreparedStatement st=null;
		try{
			String insertSql="insert into stocklist values(?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			st.setInt(1,record.getQuarter());//季度
			st.setString(2,record.getStockid());//股票代码
			st.setString(3,record.getStockname());//股票名称
			st.setLong(4,record.getTotal());//持仓总数
			st.setFloat(5,record.getCircleratio());//流通盘占比
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
	 * 按季度清理数据
	 * @param quarter
	 */
	public void delete(int quarter){
		PreparedStatement st=null;
		try{
			String sql="delete from stocklist where quarter=?";
			st=con.prepareStatement(sql);
			st.setInt(1,quarter);	//季度
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
	 * 获取某一季度股票持仓情况列表
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
