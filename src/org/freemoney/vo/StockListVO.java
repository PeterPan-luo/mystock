package org.freemoney.vo;

import java.io.Serializable;

public class StockListVO implements Serializable{
	private static final long serialVersionUID = 4588454560179314693L;
	private String stockid;	//股票代码
	private String stockname;//股票名称 
	private long total;		//持仓总数
	private float circleratio;//占流通盘比例
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
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public float getCircleratio() {
		return circleratio;
	}
	public void setCircleratio(float circleratio) {
		this.circleratio = circleratio;
	}
	
	
}
