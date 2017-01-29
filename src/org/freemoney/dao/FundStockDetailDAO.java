package org.freemoney.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.freemoney.db.DataBaseManager;
import org.freemoney.model.FundStockDetail;
import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class FundStockDetailDAO extends AbstractDAO{

	public FundStockDetailDAO() {
		super();
	}

	/**
	 * ���ӻ����Ʊ�ֲ���ϸ��¼
	 * @param record
	 * @throws SQLException
	 */
	public void insert(FundStockDetail record) throws SQLException{
		PreparedStatement st=null;
		try{
			String insertSql="insert into fundstockdetail values(?,?,?,?,?,?,?,?)";
			st=con.prepareStatement(insertSql);
			st.setString(1, record.getFundid());	//�����
			st.setInt(2,record.getQuarter());		//����
			st.setInt(3,record.getSerial());		//���
			st.setString(4,record.getStockid());	//��Ʊ����
			st.setString(5,record.getStockname());	//��Ʊ����
			st.setLong(6,record.getStocknumber());	//�ֹ�����
			st.setDouble(7,record.getStockvalue());	//�ֹɼ�ֵ
			st.setFloat(8,record.getRatio());		//ռ��ֵ����
			st.execute();
			log.info(insertSql);
		}catch(SQLException e){
			log.error("insert error:",e);
			e.printStackTrace();
			throw e;
		}finally{
			DataBaseManager.closeStatement(st);
		}
	}
	
	/**
	 * ��������������
	 * @param quarter  ���ȣ����£�
	 * @throws SQLException
	 */
	public void delete(int quarter) throws SQLException{
		PreparedStatement st=null;
		try{
			String sql="delte from fundstockdetail where quarter=?";
			st=con.prepareStatement(sql);
			st.setInt(1, quarter);
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
	 * ���������ɹ�Ʊ������ֲ�����ܼ�
	 * @param quarter  ����
	 * @return
	 */
	public List<StockListVO> getStockList(int quarter) {
		List<StockListVO> stockList=new ArrayList<StockListVO>();
		Statement st=null;
		try{
			String sql="select f.stockid,f.stockname,sum(f.stocknumber) total," +
					"round(sum(f.stocknumber)/10/s.circlecapital)/10 circleratio " +
					"from fundstockdetail f,stock s where f.quarter="+quarter+
					" and f.stockid=s.stockid group by f.stockid order by circleratio desc";
			log.info(sql);
			st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				StockListVO record=new StockListVO();
				record.setStockid(rs.getString("stockid"));
				record.setStockname(rs.getString("stockname"));
				record.setTotal(rs.getLong("total"));
				record.setCircleratio(rs.getFloat("circleratio"));
				stockList.add(record);
			}
		}catch(SQLException e){
			log.error("getStockList error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
		return stockList;
	}
	/**
	 * �����Ȼ��ܻ���˾�ֲ�����
	 * @param quarter
	 * @return
	 */
	public List<FundCompanyStockListVO> getFundCompanyStockList(int quarter) {
		List<FundCompanyStockListVO> stockList=new ArrayList<FundCompanyStockListVO>();
		Statement st=null;
		try{
			String sql="select fc.id,f.company,fs.stockid,fs.stockname,sum(fs.stocknumber) number," +
					"round(sum(fs.stocknumber)/10/s.circlecapital)/10 circleratio " +
					"from fundstockdetail fs,fund f,stock s,fundcompany fc " +
					"where fs.fundid=f.fundid and fs.stockid=s.stockid and f.company=fc.name and quarter="+quarter
					+ " group by f.company,fs.stockid "+
					"order by f.company,circleratio desc";
			log.info(sql);
			st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				FundCompanyStockListVO record=new FundCompanyStockListVO();
				record.setCompanyid(rs.getInt("id"));
				record.setCompanyname(rs.getString("company"));
				record.setStockid(rs.getString("stockid"));
				record.setStockname(rs.getString("stockname"));
				record.setStocknumber(rs.getLong("number"));
				record.setCircleratio(rs.getFloat("circleratio"));
				stockList.add(record);
			}
		}catch(SQLException e){
			log.error("getFundCompanyStockList error:",e);
			e.printStackTrace();
		}finally{
			DataBaseManager.closeStatement(st);
		}
		return stockList;
	}
	/**
	 * ���ݹ�Ʊid��ĳ���ȸù�Ʊ������ֲ����
	 * @param quarter
	 * @param stockID
	 * @return
	 */
	public List<FundStockListVO> getFundListByStock(int quarter,String stockID) {
		List<FundStockListVO> stockList = new ArrayList<FundStockListVO>();
		Statement st = null;
		try {
			String sql ="select f.name,fs.* from fundstockdetail fs,fund f "
					+"where fs.fundid=f.fundid and stockid="+stockID+ " and quarter="+quarter
					+" order by fs.stocknumber desc";
			log.info(sql);
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				FundStockListVO record = new FundStockListVO();
				
				record.setFundid(rs.getString("fundid"));
				record.setFundname(rs.getString("name"));
				record.setStockid(rs.getString("stockid"));
				record.setStockname(rs.getString("stockname"));
				record.setStocknumber(rs.getLong("stocknumber"));
				record.setCircleratio(rs.getFloat("ratio"));
				stockList.add(record);
			}
		} catch (SQLException e) {
			log.error("getFundListByStock error:" ,e);
			e.printStackTrace();
		} finally {
			DataBaseManager.closeStatement(st);
		}
		return stockList;
	}
}
