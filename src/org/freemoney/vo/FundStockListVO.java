package org.freemoney.vo;

import java.io.Serializable;

/**
 * �����Ʊ��vo
 * @author Administrator
 *
 */
public class FundStockListVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String fundid;//����˾id
	private String fundname;//����˾����
	private String stockid;//��Ʊid
	private String stockname;//��Ʊ����
	private long stocknumber;//�ֹ�����
	private float circleratio;//ռ��ͨ�̱���
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
