package org.freemoney.socket.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SocketMessage implements Serializable{


	private static final long serialVersionUID = 1L;
	private int cmdNo;//�����
	private Map<String, Object> cmdBody = new HashMap<String, Object>();//��Ϣ��
	
	public static final int STOCKADDREDUCE = 1001;////��Ʊ��������ϸ��ѯ����
	public static final int FCSTOCKADDREDUCE=1002;//����˾��Ʊ��������ϸ��ѯ����
	public static final int QUERYSTOCKQUARTER=1003;//������ѯĳֻ��Ʊ���������� 
	
	public static final String KEY_BEGINQUARTER="BEGINQUARTER";//��ʼ���ȼ�ֵ
	public static final String KEY_ENDQUARTER="ENDQUARTER";		//�������ȼ�ֵ
	public static final String KEY_QUERYQUARTER="QUERYQUARTER";	//��ѯ���ȼ�ֵ
	public static final String KEY_STOCKID="STOCKID";			//��Ʊ�����ֵ
	public static final String KEY_RESULT="RESULT";				//��ѯ�����ֵ
	
	public int getCmdNo() {
		return cmdNo;
	}
	public void setCmdNo(int cmdNo) {
		this.cmdNo = cmdNo;
	}
	public Map<String, Object> getCmdBody() {
		return cmdBody;
	}
	public void setCmdBody(Map<String, Object> cmdBody) {
		this.cmdBody = cmdBody;
	}
	
	
}
