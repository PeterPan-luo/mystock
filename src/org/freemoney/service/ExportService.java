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
		log.info("��ʼ����xls�ļ�......");
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"��Ʊ����");
		exp.setCell(1,"��Ʊ����");
		exp.setCell(2,"����(��)");
		exp.setCell(3, "ռ��ͨ�̱���%");
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
			log.info("����xls�ļ��ɹ���"+filename);
		}catch (Exception e){
			log.error("����xls�ļ�ʧ��:"+e);
			e.printStackTrace();
		}
	}
	/**
	 * ���������ɻ���˾�ֲ��ܼ�xls����
	 * @param quarter
	 * @param stockList
	 */
	public void writeFundCompanyStockListQuarterXls(int quarter,
			List<FundCompanyStockListVO> stockList) {
		String filename=Constant.USER_HOMEDIR+File.separator+Constant.DOCPATH+File.separator+
		String.valueOf(quarter)+Constant.STOCKHEAVYBYFUNDCOMPANY + "." + Constant.XLS;
		log.info("��ʼ����xls�ļ�......");
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"����˾");
		exp.setCell(1,"��Ʊ����");
		exp.setCell(2,"��Ʊ����");
		exp.setCell(3,"����(��)");
		exp.setCell(4, "ռ��ͨ�̱���%");
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
			log.info("����xls�ļ��ɹ���"+filename);
		}catch(Exception e){
			log.error("����xls�ļ�ʧ��:"+filename+"|"+e);
			e.printStackTrace();
		}
	}
	/**
	 * ���ɹ�Ʊ��������ϸ���
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
		log.info("��ʼ����xls�ļ�..."+filename);
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"��Ʊ����");
		exp.setCell(1,"��Ʊ����");
		exp.setCell(2,"��������(��)");
		exp.setCell(3, "ռ��ͨ�̱���%");
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
			log.info("����xls�ļ��ɹ���"+filename);
		}catch(Exception e){
			log.error("����xls�ļ�ʧ��:"+filename+"|"+e);
			e.printStackTrace();
		}
	}

	/**
	 * ���ɻ���˾��Ʊ��������ϸxls�ļ�
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
		log.info("��ʼ����xls�ļ�..."+filename);
		XLSExport exp=new XLSExport(filename);
		exp.createRow(0);
		exp.setCell(0,"����˾");
		exp.setCell(1,"��Ʊ����");
		exp.setCell(2,"��Ʊ����");
		exp.setCell(3,"��������(��)");
		exp.setCell(4, "ռ��ͨ�̱���%");
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
			log.info("����excel�ļ��ɹ���"+filename);
		}catch(Exception e){
			log.info("����excel�ļ�ʧ�ܣ�"+filename,e);
			e.printStackTrace();
		}
	}
	public void outFundListByStock(int quarter,
			List<FundStockListVO> result) {
		System.out
				.println("--------------------------------------------------------------------------------");
		System.out.printf("%16s %8s %8s %12s %8s","��������", "��Ʊ����",
				"��Ʊ����", "�ֲ�����", "��ͨ����\n");
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
