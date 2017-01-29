package org.freemoney.service;

import java.io.File;
import java.util.List;

import org.freemoney.utils.Constant;
import org.freemoney.utils.XLSExport;
import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class ExportService extends AbstractService{

	public ExportService() {
		super();
	}

	public void writeStockListQuarterXls(int quarter,
			List<StockListVO> stockList) {
		String filename=Constant.USER_HOMEDIR+File.separator+Constant.DOCPATH+File.separator+
		String.valueOf(quarter)+Constant.STOCKDETAILBYFUND + "." + Constant.XLS;
		log.info("开始导出xls文件......");
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"股票代码");
		exp.setCell(1,"股票名称");
		exp.setCell(2,"数量(股)");
		exp.setCell(3, "占流通盘比例%");
		for(int i=0;i<stockList.size();i++){
			StockListVO record=stockList.get(i);
			exp.createRow(i+1);
			exp.setCell(0,record.getStockid());
			exp.setCell(1,record.getStockname());
			exp.setCell(2,record.getTotal());
			exp.setCell(3,record.getCircleratio());
		}
		try{
			exp.exportXLS();
			log.info("导出xls文件成功："+filename);
		}catch (Exception e){
			log.error("导出xls文件失败:"+e);
			e.printStackTrace();
		}
	}
	/**
	 * 按季度生成基金公司持仓总计xls报表
	 * @param quarter
	 * @param stockList
	 */
	public void writeFundCompanyStockListQuarterXls(int quarter,
			List<FundCompanyStockListVO> stockList) {
		String filename=Constant.USER_HOMEDIR+File.separator+Constant.DOCPATH+File.separator+
		String.valueOf(quarter)+Constant.STOCKHEAVYBYFUNDCOMPANY + "." + Constant.XLS;
		log.info("开始导出xls文件......");
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"基金公司");
		exp.setCell(1,"股票代码");
		exp.setCell(2,"股票名称");
		exp.setCell(3,"数量(股)");
		exp.setCell(4, "占流通盘比例%");
		for(int i=0;i<stockList.size();i++){
			FundCompanyStockListVO record=stockList.get(i);
			exp.createRow(i+1);
			exp.setCell(0,record.getCompanyname());
			exp.setCell(1,record.getStockid());
			exp.setCell(2,record.getStockname());
			exp.setCell(3,record.getStocknumber());
			exp.setCell(4,record.getCircleratio());
		}
		try{
			exp.exportXLS();
			log.info("导出xls文件成功："+filename);
		}catch(Exception e){
			log.error("导出xls文件失败:"+filename+"|"+e);
			e.printStackTrace();
		}
	}
	/**
	 * 生成股票增减仓明细表格
	 * @param preQuarter
	 * @param currentQuarter
	 * @param docPath
	 * @param fileName
	 * @param resultList
	 */
	public void writeStockAddReduceTableXls(int preQuarter, int currentQuarter,
			String docPath, String fileName, List<StockListVO> resultList) {
		String filename=docPath+File.separator+Constant.DOCPATH+File.separator+String.valueOf(preQuarter)+"-"
			+String.valueOf(currentQuarter)+fileName+"."+Constant.XLS;
		log.info("开始导出xls文件..."+filename);
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"股票代码");
		exp.setCell(1,"股票名称");
		exp.setCell(2,"增减数量(股)");
		exp.setCell(3, "占流通盘比例%");
		for(int i=0;i<resultList.size();i++){
			StockListVO record=resultList.get(i);
			exp.createRow(i+1);
			exp.setCell(0,record.getStockid());
			exp.setCell(1,record.getStockname());
			exp.setCell(2,record.getTotal());
			exp.setCell(3,record.getCircleratio());
		}
		try{
			exp.exportXLS();
			log.info("导出xls文件成功："+filename);
		}catch(Exception e){
			log.error("导出xls文件失败:"+filename+"|"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 生成基金公司股票增减仓明细xls文件
	 * @param preQuarter
	 * @param currentQuater
	 * @param userDir
	 * @param fileName
	 * @param resultList
	 */
	public void writeFcStockAddReduceTableXls(int preQuarter,
			int currentQuater, String userDir,
			String fileName,
			List<FundCompanyStockListVO> resultList) {
		String filename=userDir+File.separator+Constant.DOCPATH+File.separator+
				String.valueOf(preQuarter)+"-"+String.valueOf(currentQuater)+fileName+"."+Constant.XLS;
		log.info("开始导出xls文件..."+filename);
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"基金公司");
		exp.setCell(1,"股票代码");
		exp.setCell(2,"股票名称");
		exp.setCell(3,"增减数量(股)");
		exp.setCell(4, "占流通盘比例%");
		for(int i=0;i<resultList.size();i++){
			FundCompanyStockListVO record=resultList.get(i);
			exp.createRow(i+1);
			exp.setCell(0, record.getCompanyname());
			exp.setCell(1, record.getStockid());
			exp.setCell(2,record.getStockname());
			exp.setCell(3,record.getStocknumber());
			exp.setCell(4,record.getCircleratio());
		}
		try{
			exp.exportXLS();
			log.info("导出excel文件成功："+filename);
		}catch(Exception e){
			log.info("导出excel文件失败："+filename,e);
			e.printStackTrace();
		}
	}
	public void outFundListByStock(int quarter,
			List<FundStockListVO> result) {
		System.out
				.println("--------------------------------------------------------------------------------");
		System.out.printf("%16s %8s %8s %12s %8s","基金名称", "股票代码",
				"股票名称", "持仓数量", "流通比例\n");
		System.out
				.println("--------------------------------------------------------------------------------");
		for (int i = 0; i < result.size(); i++) {
			FundStockListVO record = result.get(i);
			System.out.printf("\n%16s %8s %8s %12d %8.2f", record.getFundname().trim(), record
					.getStockid(), record.getStockname(), record
					.getStocknumber(), record.getCircleratio());
		}
	}
}
