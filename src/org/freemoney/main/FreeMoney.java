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
						// 生成所有基金股票持仓明细
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
						//按季度生成股票被基金持仓情况总计
						cdService.createStockListQuarter(quarter);
						break;
						
					case 3:
						squarter=inputQuarter();
						if(!squarter.isEmpty())
							quarter=Integer.parseInt(squarter);
						else
							break;
						System.out.println("System is running,waiting......");
						//按季度生成基金公司持仓总计，按占流通盘比例大小计算,分析角度是基金公司
						cdService.createFundCompanyStockListQuarter(quarter);
						break;
					case 4:
						squarter=inputBeginQuater();
						int preQuarter=0;	//比较开始季度
						int currentQuater=0;//比较结束季度
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
						//生成股票增减仓
						cdService.createStockAddReduceTable(preQuarter,currentQuater,userDir);
						break;
					case 5:
						preQuarter=0;	//比较开始季度
						currentQuater=0;//比较结束季度
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
						//生成基金公司股票增减仓数据
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
						// 按季查询某只股票基金持有情况
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
						// 按季度删除所有数据
						cdService.deleteQuarterData(quarter);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String inputEndQuarter() throws IOException {
		System.out.print("请输入要比较的截止季度:");
		return inputKeyboard();
	}

	private static String inputBeginQuater() throws IOException {
		System.out.print("请输入要比较的起始季度:");
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
		sb.append("\n---------------------主菜单-----------------------\n");
		sb.append("1.按季度生成基金股票持仓明细\n");
		sb.append("2.按季度生成股票持仓总计\n");
		sb.append("3.按季度生成基金公司股票持仓总计\n");
		sb.append("4.生成股票增减仓表格\n");
		sb.append("5.生成基金公司股票增减仓表格\n");
		sb.append("6.某只股票基金持有情况查询\n");
		sb.append("8.连续执行1、2、3菜单功能\n");
		sb.append("9.按季度删除所有数据\n");
		sb.append("0.退出系统\n");
		sb.append("-----------------------------------------------------\n");
		System.out.println(sb.toString());
		System.out.println("请输入选择：");
		return inputKeyboard();
	}

	private static String inputKeyboard() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in);//键盘输入
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
		System.out.println("请输入季度:");
		return inputKeyboard();
	}
	private static String inputStockID() throws IOException {
		System.out.print("请输入股票代码:");
		return inputKeyboard();
	}
}
