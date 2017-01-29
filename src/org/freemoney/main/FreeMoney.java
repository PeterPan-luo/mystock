package org.freemoney.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.freemoney.service.DataPumpService;
import org.freemoney.service.ExportService;
import org.freemoney.utils.Constant;
import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;

public class FreeMoney {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printHeader();
		DataPumpService cdService = new DataPumpService();
		ExportService exportService=new ExportService();
		Properties props = new Properties(System.getProperties());
		String userDir = props.getProperty("user.dir");
		boolean run = true;
		while (run) {
			try {
				int quarter = 0;
				String squarter = null;
				String stockID = null;
				String input = printMenu();
				switch (Integer.parseInt(input)) {
					case 1:
						// �������л����Ʊ�ֲ���ϸ
						squarter = inputQuarter();
						if (!squarter.isEmpty())
							quarter = Integer.parseInt(squarter);
						else
							break;
						String path = userDir + File.separator + Constant.DOCPATH
								+ File.separator + squarter;
						cdService.createFundStockDetail(path, quarter);
						break;
					case 2:
						squarter=inputQuarter();
						if(!squarter.isEmpty())
							quarter=Integer.parseInt(squarter);
						else
							break;
						System.out.println("System is running,waiting......");
						//���������ɹ�Ʊ������ֲ�����ܼ�
						cdService.createStockListQuarter(quarter);
						break;
						
					case 3:
						squarter=inputQuarter();
						if(!squarter.isEmpty())
							quarter=Integer.parseInt(squarter);
						else
							break;
						System.out.println("System is running,waiting......");
						//���������ɻ���˾�ֲ��ܼƣ���ռ��ͨ�̱�����С����,�����Ƕ��ǻ���˾
						cdService.createFundCompanyStockListQuarter(quarter);
						break;
					case 4:
						squarter=inputBeginQuater();
						int preQuarter=0;	//�ȽϿ�ʼ����
						int currentQuater=0;//�ȽϽ�������
						if(!squarter.isEmpty())
							preQuarter=Integer.parseInt(squarter);
						else
							break;
						squarter=inputEndQuarter();
						if(!squarter.isEmpty())
							currentQuater=Integer.parseInt(squarter);
						else
							break;
						System.out.println("System is running,waiting......");
						//���ɹ�Ʊ������
						cdService.createStockAddReduceTable(preQuarter,currentQuater,userDir);
						break;
					case 5:
						preQuarter=0;	//�ȽϿ�ʼ����
						currentQuater=0;//�ȽϽ�������
						squarter=inputBeginQuater();
						if(!squarter.isEmpty())
							preQuarter=Integer.parseInt(squarter);
						else
							break;
						squarter=inputEndQuarter();
						if(!squarter.isEmpty())
							currentQuater=Integer.parseInt(squarter);
						else
							break;
						System.out.println("System is running,waiting......");
						//���ɻ���˾��Ʊ����������
						cdService.createFcStockAddReduceTable(preQuarter,currentQuater,userDir);
						break;
					case 0:
						run = false;
						break;
					case 6:
						squarter = inputQuarter();
						if (!squarter.isEmpty())
							quarter = Integer.parseInt(squarter);
						else
							break;
						stockID=inputStockID();
						if(stockID.isEmpty())
							break;
						// ������ѯĳֻ��Ʊ����������
						List<FundStockListVO> result=cdService.getFundListByStock(quarter,stockID);
						exportService.outFundListByStock(quarter, result);
						inputKeyboard();
						break;
					case 9:
						squarter = inputQuarter();
						if (!squarter.isEmpty())
							quarter = Integer.parseInt(squarter);
						else
							break;
						// ������ɾ����������
						cdService.deleteQuarterData(quarter);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String inputEndQuarter() throws IOException {
		System.out.print("������Ҫ�ȽϵĽ�ֹ����:");
		return inputKeyboard();
	}

	private static String inputBeginQuater() throws IOException {
		System.out.print("������Ҫ�Ƚϵ���ʼ����:");
		return inputKeyboard();
	}

	private static void printHeader() {
		StringBuffer sb=new StringBuffer();
		sb.append("FreeMoney - free your money.\n");
		sb.append("Copyright (c) 2009-2012 freemoney.org\n");
		System.out.println(sb.toString());
		
	}

	private static String printMenu() throws IOException {
		StringBuffer sb=new StringBuffer();
		sb.append("\n---------------------���˵�-----------------------\n");
		sb.append("1.���������ɻ����Ʊ�ֲ���ϸ\n");
		sb.append("2.���������ɹ�Ʊ�ֲ��ܼ�\n");
		sb.append("3.���������ɻ���˾��Ʊ�ֲ��ܼ�\n");
		sb.append("4.���ɹ�Ʊ�����ֱ��\n");
		sb.append("5.���ɻ���˾��Ʊ�����ֱ��\n");
		sb.append("6.ĳֻ��Ʊ������������ѯ\n");
		sb.append("8.����ִ��1��2��3�˵�����\n");
		sb.append("9.������ɾ����������\n");
		sb.append("0.�˳�ϵͳ\n");
		sb.append("-----------------------------------------------------\n");
		System.out.println(sb.toString());
		System.out.println("������ѡ��");
		return inputKeyboard();
	}

	private static String inputKeyboard() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in);//��������
		BufferedReader br=new  BufferedReader(isr);
		String input=new String();
		try{
			input=br.readLine();
		}catch(IOException e){
			System.out.print("I/O ERROR:"+e);
			throw e;
		}
		return input.trim();
	}

	private static String inputQuarter() throws IOException {
		System.out.println("�����뼾��:");
		return inputKeyboard();
	}
	private static String inputStockID() throws IOException {
		System.out.print("�������Ʊ����:");
		return inputKeyboard();
	}
}
