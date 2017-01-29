package org.freemoney.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ���ɵ���Excel�ļ�����
 * 
 * @author hejing
 * 
 */
public class XLSExport {
	// �������ڸ�ʽ
	private static String DATE_FORMAT = " m/d/yy "; // "m/d/yy h:mm"

	// ���Ƹ�������ʽ
	private static String NUMBER_FORMAT = " #,##0.00 ";

	private String xlsFileName;

	private HSSFWorkbook workbook;

	private HSSFSheet sheet;

	private HSSFRow row;

	/** */
	/**
	 * ��ʼ��Excel
	 * 
	 * @param fileName
	 *            �����ļ���
	 */
	public XLSExport(String fileName) {
		this.xlsFileName = fileName;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	/**
	 * ����Excel�ļ�
	 * 
	 * @throws XLSException
	 */
	public void exportXLS() throws Exception {
		FileOutputStream fOut=null;
		try {
			fOut = new FileOutputStream(xlsFileName);
			workbook.write(fOut);
			fOut.flush();
		} catch (FileNotFoundException e) {
			throw new Exception(" ���ɵ���Excel�ļ�����! ", e);
		} catch (IOException e) {
			throw new Exception(" д��Excel�ļ�����! ", e);
		}finally{
			if(fOut!=null)
				fOut.close();
		}
	}

	/** */
	/**
	 * ����һ��
	 * 
	 * @param index
	 *            �к�
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	/** */
	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // �����µ�cell��ʽ
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // ����cell��ʽΪ���Ƶ����ڸ�ʽ
		cell.setCellStyle(cellStyle); // ���ø�cell���ڵ���ʾ��ʽ
	}

	/** */
	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, long value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}
	
	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // �����µ�cell��ʽ
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // ����cell��ʽΪ���Ƶĸ�������ʽ
		cell.setCellStyle(cellStyle); // ���ø�cell����������ʾ��ʽ
	}

	public static void main(String[] args) {
		System.out.println(" ��ʼ����Excel�ļ� ");
		XLSExport e = new XLSExport(" d:/test.xls ");
		e.createRow(0);
		e.setCell(0, " ��� ");
		e.setCell(1, " ���� ");
		e.setCell(2, " ���� ");
		e.setCell(3, " ��� ");
		e.createRow(1);
		e.setCell(0, 1);
		e.setCell(1, " �������� ");
		e.setCell(2, Calendar.getInstance());
		e.setCell(3, 111123.99);
		e.createRow(2);
		e.setCell(0, 2);
		e.setCell(1, " �������� ");
		e.setCell(2, Calendar.getInstance());
		e.setCell(3, 222456.88);
		try {
			e.exportXLS();
			System.out.println(" ����Excel�ļ�[�ɹ�] ");
		} catch (Exception e1) {
			System.out.println(" ����Excel�ļ�[ʧ��] ");
			e1.printStackTrace();
		}
	}
}