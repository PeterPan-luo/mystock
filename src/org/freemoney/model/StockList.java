package org.freemoney.model;

public class StockList {
	private int quarter;	//����
	private String stockid;	//��Ʊ����
	private String stockname;//��Ʊ����
	private long total;		//�ֲ�����
	private float circleratio;//ռ��ͨ�̱���
	
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
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
