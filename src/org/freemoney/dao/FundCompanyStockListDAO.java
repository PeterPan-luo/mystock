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
	 * ���ӵ�����¼
	 * @param record
	 */
	public void insert(FundCompanyStockList record){
		PreparedStatement st=null;
		try{
			String insertSql="insert into fcstocklist values(?,?,?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			st.setInt(1,record.getQuarter()); //���� 
			st.setInt(2,record.getCompanyid());	//����˾ID
			st.setString(3,record.getCompanyname());//����˾����
			st.setString(4,record.getStockid());//��Ʊ����
			st.setString(5,record.getStockname());//��Ʊ����
			st.setLong(6,record.getStocknumber());//�ֹ�����
			st.setFloat(7,record.getCircleratio());//��ͨ��ռ��
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
	 * �������Ӽ�¼
	 * @param records
	 */
	public void insert(List<FundCompanyStockList> records){
		PreparedStatement st=null;
		try{
			String insertSql="insert into fcstocklist values(?,?,?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			int count=0;
			for(FundCompanyStockList record:records){
				st.setInt(1,record.getQuarter()); //���� 
				st.setInt(2,record.getCompanyid());	//����˾ID
				st.setString(3,record.getCompanyname());//����˾����
				st.setString(4,record.getStockid());//��Ʊ����
				st.setString(5,record.getStockname());//��Ʊ����
				st.setLong(6,record.getStocknumber());//�ֹ�����
				st.setFloat(7,record.getCircleratio());//��ͨ��ռ��
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
	 * ��������������
	 * @param quarter
	 * @throws SQLException 
	 */
	public void delete(int quarter) throws SQLException{
		PreparedStatement st=null;
		try{
			String sql="delete from fcstocklist where quarter=?";
			st=con.prepareStatement(sql);
			st.setInt(1, quarter);	//����
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
	 * ��ȡĳһ���Ȼ���˾��Ʊ�ֲ��ܼ�������б�
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
