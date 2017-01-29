package org.freemoney.vo;

import java.io.Serializable;

public class FundCompanyStockListVO implements Serializable{
	private static final long serialVersionUID = -871012757971590635L;
	private int companyid;	//基金公司ID
	private String companyname;//基金公司名称
	private String stockid;	//股票代码
	private String stockname;//股票名称
	private long stocknumber;//持股数量
	private float circleratio;//占流通盘比例
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getStockid() {
		return stockid;
	}
	public void setStockid(String stockid) {
		this.stockid = stockid;
	}
	public String getStockname() {
		return stockname;
	}
	public void setStockname(String stockname) {
		this.stockname = stockname;
	}
	public long getStocknumber() {
		return stocknumber;
	}
	public void setStocknumber(long stocknumber) {
		this.stocknumber = stocknumber;
	}
	public float getCircleratio() {
		return circleratio;
	}
	public void setCircleratio(float circleratio) {
		this.circleratio = circleratio;
	}
	
	
}
