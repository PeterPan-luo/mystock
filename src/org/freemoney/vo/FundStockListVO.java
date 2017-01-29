package org.freemoney.vo;

import java.io.Serializable;

/**
 * 基金股票的vo
 * @author Administrator
 *
 */
public class FundStockListVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String fundid;//基金公司id
	private String fundname;//基金公司名称
	private String stockid;//股票id
	private String stockname;//股票名称
	private long stocknumber;//持股数量
	private float circleratio;//占流通盘比例
	public String getFundid() {
		return fundid;
	}
	public void setFundid(String fundid) {
		this.fundid = fundid;
	}
	public String getFundname() {
		return fundname;
	}
	public void setFundname(String fundname) {
		this.fundname = fundname;
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
