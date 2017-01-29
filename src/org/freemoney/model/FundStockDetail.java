package org.freemoney.model;

public class FundStockDetail {
	private String fundid;	//����ID
	private int quarter;	//����
	private int serial;		//���
	private String stockid;	//��Ʊ����
	private String stockname;//��Ʊ����
	private long stocknumber;//�ֹ�����
	private double stockvalue;//�ֹɼ�ֵ
	private float ratio;	//��ֵռ�� ����ָù�ռ�ù���ͨ�̵ı���
	
	public FundStockDetail() {
		super();
	}
	
	/**
	 * ȫ���Թ��췽��
	 * @param fundid
	 * @param quarter
	 * @param serial
	 * @param stockid
	 * @param stockname
	 * @param stocknumber
	 * @param ratio
	 */
	public FundStockDetail(String fundid, int quarter, int serial,
			String stockid, String stockname, long stocknumber,double stockvalue, float ratio) {
		super();
		this.fundid = fundid;
		this.quarter = quarter;
		this.serial = serial;
		this.stockid = stockid;
		this.stockname = stockname;
		this.stocknumber = stocknumber;
		this.stockvalue=stockvalue;
		this.ratio = ratio;
	}


	public String getFundid() {
		return fundid;
	}
	public void setFundid(String fundid) {
		this.fundid = fundid;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
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
	public float getRatio() {
		return ratio;
	}
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public double getStockvalue() {
		return stockvalue;
	}

	public void setStockvalue(double stockvalue) {
		this.stockvalue = stockvalue;
	}
	
	
}
