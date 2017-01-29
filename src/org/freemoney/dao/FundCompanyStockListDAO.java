package org.freemoney.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.freemoney.db.DataBaseManager;
import org.freemoney.model.FundCompanyStockList;
import org.freemoney.model.FundCompanyStockListKey;

public class FundCompanyStockListDAO extends AbstractDAO{

	public FundCompanyStockListDAO() {
		super();
	}
	/**
	 * 增加单条记录
	 * @param record
	 */
	public void insert(FundCompanyStockList record){
		PreparedStatement st=null;
		try{
			String insertSql="insert into fcstocklist values(?,?,?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			st.setInt(1,record.getQuarter()); //季度 
			st.setInt(2,record.getCompanyid());	//基金公司ID
			st.setString(3,record.getCompanyname());//基金公司名称
			st.setString(4,record.getStockid());//股票代码
			st.setString(5,record.getStockname());//股票名称
			st.setLong(6,record.getStocknumber());//持股总数
			st.setFloat(7,record.getCircleratio());//流通盘占比
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
	 * 批量增加记录
	 * @param records
	 */
	public void insert(List<FundCompanyStockList> records){
		PreparedStatement st=null;
		try{
			String insertSql="insert into fcstocklist values(?,?,?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			int count=0;
			for(FundCompanyStockList record:records){
				st.setInt(1,record.getQuarter()); //季度 
				st.setInt(2,record.getCompanyid());	//基金公司ID
				st.setString(3,record.getCompanyname());//基金公司名称
				st.setString(4,record.getStockid());//股票代码
				st.setString(5,record.getStockname());//股票名称
				st.setLong(6,record.getStocknumber());//持股总数
				st.setFloat(7,record.getCircleratio());//流通盘占比
				st.addBatch();
				count++;
				if(count>=1000){
					st.executeBatch();
					st.clearBatch();
					count=0;
				}
			}
			log.info(insertSql);
			if(count!=0){
				st.executeBatch();
				st.clearBatch();
			}
		}catch(SQLException e){
			log.error("insert batch error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
	}
	/**
	 * 按季度清理数据
	 * @param quarter
	 * @throws SQLException 
	 */
	public void delete(int quarter) throws SQLException{
		PreparedStatement st=null;
		try{
			String sql="delete from fcstocklist where quarter=?";
			st=con.prepareStatement(sql);
			st.setInt(1, quarter);	//季度
			st.execute();
			log.info(sql);
		}catch(SQLException e){
			log.error("delete error:",e);
			e.printStackTrace();
			throw e;
		}finally{
			DataBaseManager.closeStatement(st);
		}
	}
	/**
	 * 获取某一季度基金公司股票持仓总计情况的列表
	 * @param quarter
	 * @return
	 */
	public Map<FundCompanyStockListKey,FundCompanyStockList> getFcStockList(int quarter){
		Map<FundCompanyStockListKey,FundCompanyStockList> fcStockList =new HashMap<FundCompanyStockListKey,FundCompanyStockList>();
		Statement st=null;
		try{
			String sql="select * from fcstocklist where quarter="+quarter;
			st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			log.info(sql);
			while(rs.next()){
				FundCompanyStockList record=new FundCompanyStockList();
				record.setQuarter(quarter);
				record.setCompanyid(rs.getInt("companyid"));
				record.setCompanyname(rs.getString("companyname"));
				record.setStockid(rs.getString("stockid"));
				record.setStockname(rs.getString("stockname"));
				record.setStocknumber(rs.getLong("stocknumber"));
				record.setCircleratio(rs.getFloat("circleratio"));
				FundCompanyStockListKey key=new FundCompanyStockListKey(quarter,record.getCompanyid(),record.getStockid());
				fcStockList.put(key, record);
			}
		}catch(SQLException e){
			log.error("getFcStockList error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
		return fcStockList;
	}
}
