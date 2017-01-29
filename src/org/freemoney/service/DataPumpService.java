/*
 * Created on 2010-10-10 ����12:35:40
 * @author hejing
 */
package org.freemoney.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.freemoney.dao.FundCompanyStockListDAO;
import org.freemoney.dao.FundStockDetailDAO;
import org.freemoney.dao.StockListDAO;
import org.freemoney.model.FundCompanyStockList;
import org.freemoney.model.FundCompanyStockListKey;
import org.freemoney.model.FundStockDetail;
import org.freemoney.model.StockList;
import org.freemoney.utils.Constant;
import org.freemoney.utils.FileUtil;
import org.freemoney.utils.HandleFile;
import org.freemoney.utils.Utils;
import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class DataPumpService extends AbstractService {
	private FundStockDetailDAO fundStockDetailDAO;
	private StockListDAO stockListDAO;
	private FundCompanyStockListDAO fundCompanyStockListDAO;
	private ExportService exportService;

	public static void main(String[] args) {
		FileUtil.copyErrorFile("E:\\project\\mystock\\doc\\201006\\000001.doc");
	}

	public DataPumpService() {
		super();
		this.fundStockDetailDAO = new FundStockDetailDAO();
		this.stockListDAO = new StockListDAO();
		this.fundCompanyStockListDAO = new FundCompanyStockListDAO();
		this.exportService = new ExportService();
	}

	/**
	 * @param fundStockDetailDAO
	 */
	public DataPumpService(FundStockDetailDAO fundStockDetailDAO) {
		super();
		this.fundStockDetailDAO = fundStockDetailDAO;
	}

	/**
	 * @return the fundStockDetailDAO
	 */
	public FundStockDetailDAO getFundStockDetailDAO() {
		return fundStockDetailDAO;
	}

	/**
	 * @param fundStockDetailDAO
	 *            the fundStockDetailDAO to set
	 */
	public void setFundStockDetailDAO(FundStockDetailDAO fundStockDetailDAO) {
		this.fundStockDetailDAO = fundStockDetailDAO;
	}

	/**
	 * �Ӽ����ļ��н���������ֲ���ϸ�����
	 */
	public void createFundStockDetail(String path, int quarter) {
		log.info("receive createFundStockDetail.");
		File dir = new File(path);
		File[] childs = dir.listFiles();
		log.info("file number is:" + childs.length);
		int success = 0;
		int fail = 0;
		StringBuffer sbSuccess = new StringBuffer();
		StringBuffer sbfail = new StringBuffer();
		try {
			fundStockDetailDAO.getConnection();
			for (File file : childs) {
				if (file.isDirectory())
					continue;
				try {
					String filename = file.getAbsolutePath();
					log.info("now parse file:" + filename);
					String fundid = filename.substring(filename
							.lastIndexOf(File.separator) + 1, filename
							.lastIndexOf("."));
					String content = HandleFile.handleFile(filename);
					// ��Щ�ĵ������Ǵ�д��Сд�����Ҵ�д����Сд
					int index = content.indexOf("��� ��Ʊ���� ��Ʊ���� �������ɣ� ���ʼ�ֵ");
					if (index == -1)
						index = content.indexOf("��� ��Ʊ���� ��Ʊ���� ����(��) ���ʼ�ֵ");
					if (index == -1) {
						log.info("no stock list in " + filename);
						throw new Exception();
					}
					String result = content.substring(index, content.length());
					String[] rows = result.split("\n");
					int i = 0;
					for (String row : rows) {
						i++;
						if (i == 1) // ��һ�б�ͷ����
							continue;
						log.debug("insert row= " + row + "|filename="
								+ filename);
						// ��pdf�ļ�����
						if (row.indexOf("ծȯ") != -1 || row.indexOf("������") != -1
								|| row.indexOf("��ĩ") != -1) { // ����������
							log.info("break keyword");
							break;
						}
						String[] col = row.split(" ");
						log.debug(row + " collength=" + col.length);
						if (Utils.isPdf(filename)) {
							// pdf�ո������һ�е����
							if (col.length == 7
									&& !col[0].trim().equalsIgnoreCase("")
									&& Utils.isNumeric(col[0])
									&& Integer.parseInt(col[0]) < 500) {
								FundStockDetail record = new FundStockDetail();
								record.setFundid(fundid);
								record.setQuarter(quarter);
								record.setSerial(Integer.parseInt(col[0]));
								record.setStockid(col[1].trim());
								record.setStockname(col[2]);
								// ��Ʊ��λΪ��,�ֹ���
								String stocknum = Utils.formatNumber(col[3]);
								if (stocknum.indexOf(".") != -1)
									stocknum = stocknum.substring(0, stocknum
											.indexOf("."));
								record.setStocknumber(Long.parseLong(stocknum));
								// ��Ʊ��ֵ
								double stockValue = 0;
								try {
									stockValue = Double.parseDouble(Utils
											.formatNumber(col[4]));
								} catch (NumberFormatException ex) {
									log.error(ex);
									ex.printStackTrace();
								}
								record.setStockvalue(stockValue);
								// ����ֵռ��
								float ratio = 0;
								try {
									ratio = Float.parseFloat(Utils
											.formatNumber(col[5]));
								} catch (NumberFormatException ex) {
									log.error(ex);
									ex.printStackTrace();
								}
								record.setRatio(ratio);
								fundStockDetailDAO.insert(record);
							} else if (col.length == 7
									&& col[0].trim().equalsIgnoreCase("")
									&& Integer.parseInt(col[1]) < 500) {
								// pdf�ո��ڵ�һ�е����
								FundStockDetail record = new FundStockDetail();
								record.setFundid(fundid);
								record.setQuarter(quarter);
								record.setSerial(Integer.parseInt(col[1]));
								record.setStockid(col[2].trim());
								record.setStockname(col[3]);
								String stocknum = Utils.formatNumber(col[4]);
								if (stocknum.indexOf(".") != -1)
									stocknum = stocknum.substring(0, stocknum
											.indexOf("."));
								record.setStocknumber(Long.parseLong(stocknum));
								// ��Ʊ��ֵ
								double stockValue = 0;
								try {
									stockValue = Double.parseDouble(Utils
											.formatNumber(col[5]));
								} catch (NumberFormatException ex) {
									log.error(ex);
									ex.printStackTrace();
								}
								record.setStockvalue(stockValue);
								// ����ֵռ��
								float ratio = 0;
								try {
									ratio = Float.parseFloat(Utils
											.formatNumber(col[6]));
								} catch (NumberFormatException ex) {
									log.error(ex);
									ex.printStackTrace();
								}
								record.setRatio(ratio);
								fundStockDetailDAO.insert(record);
							}
						} else if (Utils.isDoc(filename)) {
							// ��һ�в������ֲ��Ҳ��ǿո��˳�ѭ����doc���ô˹���
							if (!Utils.isNumeric(col[0])) {
								log.info("break notnumeric");
								break;
							}
							if (col.length == 6
									&& Integer.parseInt(col[0]) < 500) {
								FundStockDetail record = new FundStockDetail();
								record.setFundid(fundid);
								record.setQuarter(quarter);
								record.setSerial(Integer.parseInt(col[0]));
								record.setStockid(col[1].trim());
								record.setStockname(col[2]);
								String stocknum = Utils.formatNumber(col[3]);
								if (stocknum.indexOf(".") != -1)
									stocknum = stocknum.substring(0, stocknum
											.indexOf("."));
								record.setStocknumber(Long.parseLong(stocknum));
								// ��Ʊ��ֵ
								double stockValue = 0;
								try {
									stockValue = Double.parseDouble(Utils
											.formatNumber(col[4]));
								} catch (NumberFormatException ex) {
									log.error(ex);
									ex.printStackTrace();
								}
								record.setStockvalue(stockValue);
								// ����ֵռ��
								float ratio = 0;
								try {
									ratio = Float.parseFloat(Utils
											.formatNumber(col[5]));
								} catch (NumberFormatException ex) {
									log.error(ex);
									ex.printStackTrace();
								}
								record.setRatio(ratio);
								fundStockDetailDAO.insert(record);
							}
						}
					}
					success++;
					sbSuccess.append(file.getAbsoluteFile() + "\n");
				} catch (Exception e) {
					log.error("createFundStockDetail error!filename="
							+ file.getAbsolutePath() + "::" ,e);
					e.printStackTrace();
					FileUtil.copyErrorFile(file.getAbsolutePath());
					log.info("copyerrorfile=" + file.getAbsolutePath());
					fail++;
					sbfail.append(file.getAbsoluteFile() + "\n");
				}
			}
		} finally {
			fundStockDetailDAO.releaseConnection();
			log.info("success parse file number:" + success + "\n"
					+ sbSuccess.toString() + "\n");
			log.info("fail parse file number:" + fail + "\n"
					+ sbfail.toString() + "\n");
		}
	}

	/**
	 * ���������ɹ�Ʊ�ֲ��ܼ�,������ɫ�ǹ�Ʊ
	 * 
	 * @param quarter
	 */
	public void createStockListQuarter(int quarter) {
		log.info("receive createStockListQuarter.");
		try {
			fundStockDetailDAO.getConnection();
			stockListDAO.getConnection();
			List<StockListVO> stockList = fundStockDetailDAO
					.getStockList(quarter);
			log.info("stockList size=" + stockList.size());
			for (StockListVO record : stockList) {
				StockList sl = new StockList();
				BeanUtils.copyProperties(sl, record);
				sl.setQuarter(quarter);
				stockListDAO.insert(sl);
			}
			// ����xls����
			exportService.writeStockListQuarterXls(quarter, stockList);
			log.info("createStockListQuarter success!");
		} catch (Exception e) {
			log.error("createStockListQuarter error::" ,e);
			e.printStackTrace();
		} finally {
			fundStockDetailDAO.releaseConnection();
			stockListDAO.releaseConnection();
		}
	}

	/**
	 * ���������ɻ���˾�ֲ��ܼƣ���ռ��ͨ�̱�����С����,�����Ƕ��ǻ���˾
	 * 
	 * @param quarter
	 */
	public void createFundCompanyStockListQuarter(int quarter) {
		log.info("receive createFundCompanyStockListQuarter.");
		try {
			fundStockDetailDAO.getConnection();
			fundCompanyStockListDAO.getConnection();
			List<FundCompanyStockListVO> stockList = fundStockDetailDAO
					.getFundCompanyStockList(quarter);
			List<FundCompanyStockList> recordList = new ArrayList<FundCompanyStockList>();
			log.info("FundCompanyStockList size=" + stockList.size());
			for (FundCompanyStockListVO record : stockList) {
				FundCompanyStockList fcStockList = new FundCompanyStockList();
				BeanUtils.copyProperties(fcStockList, record);
				fcStockList.setQuarter(quarter);
				recordList.add(fcStockList);
			}
			fundCompanyStockListDAO.insert(recordList);
			// ����xls����
			exportService.writeFundCompanyStockListQuarterXls(quarter,
					stockList);
			log.info("createFundCompanyStockListQuarter success!");
		} catch (Exception e) {
			log.error("createFundCompanyStockListQuarter error::" ,e);
			e.printStackTrace();
		} finally {
			fundStockDetailDAO.releaseConnection();
			fundCompanyStockListDAO.releaseConnection();
		}
	}

	/**
	 * �������������
	 * 
	 * @param quarter
	 */
	public void deleteQuarterData(int quarter) {
		try {
			log.info("receive deleteQuarterData command.");
			fundStockDetailDAO.getConnection();
			fundCompanyStockListDAO.getConnection();
			stockListDAO.getConnection();
			// һ��һ����ɾ������
			fundStockDetailDAO.delete(quarter);
			stockListDAO.delete(quarter);
			fundCompanyStockListDAO.delete(quarter);
		} catch (Exception e) {
			log.error("createFundCompanyStockListQuarter error::" ,e);
			e.printStackTrace();
		} finally {
			fundStockDetailDAO.releaseConnection();
			fundCompanyStockListDAO.releaseConnection();
			stockListDAO.releaseConnection();
		}
	}

	/**
	 * ���ɹ�Ʊ���֡���������б�����������ռ��ͨ�ɱ�������
	 * 
	 * @param preQuarter	  �Ƚϵ�ǰһ����
	 * @param currentQuarter  �Ƚϵĺ�һ����
	 * @param docPath 		  ��null��ʾ������xls�ļ�
	 */
	public List<StockListVO> createStockAddReduceTable(int preQuarter, int currentQuarter,
			String docPath) {
		log.info("receive createStockAddReduceTable.");
		List<StockListVO> resultList = new ArrayList<StockListVO>();
		try {
			log.info("receive createStockAddReduceTable command.");
			stockListDAO.getConnection();
			Map<String, StockList> preStockList = stockListDAO
					.getStockList(preQuarter);
			Map<String, StockList> currentStockList = stockListDAO
					.getStockList(currentQuarter);
			Iterator<String> it = currentStockList.keySet().iterator();
			while (it.hasNext()) {
				String stockid = it.next();
				StockList currentStock = currentStockList.get(stockid);
				StockList preStock = preStockList.get(stockid);
				if (preStock == null)
					continue;
				StockListVO vo = new StockListVO();
				BeanUtils.copyProperties(vo, currentStock);
				vo.setTotal(currentStock.getTotal() - preStock.getTotal());
				vo.setCircleratio(currentStock.getCircleratio()
						- preStock.getCircleratio());
				resultList.add(vo);
			}
			
			// ��ռ��ͨ�̱�����������
			Collections.sort(resultList, new Comparator<Object>() {
					public int compare(Object o1, Object o2) {
						//o1 > o2 true ΪĬ�ϵ����� o2 > o1 true Ϊ����
						StockListVO s1 = (StockListVO) o1;
						StockListVO s2 = (StockListVO) o2;
						float comp = s2.getCircleratio() - s1.getCircleratio();
						if (comp > 0)
							return 1;
						else if (comp < 0)
							return -1;
						else
							return 0;
					}
			});
			if(docPath!=null){
				// �������ֱ��
				exportService.writeStockAddReduceTableXls(preQuarter,
						currentQuarter, docPath, Constant.STOCKADDBYFUND,
						resultList);
			}
			return resultList;
		} catch (Exception e) {
			log.error("createStockAddReduceTable error::" ,e);
			e.printStackTrace();
			return resultList;
		} finally {
			stockListDAO.releaseConnection();
		}
	}

	/**
	 * ���ɻ���˾��Ʊ���֡���������б�����������ռ��ͨ�ɱ�������
	 * 
	 * @param preQuarter
	 * @param currentQuarter
	 * @param docPath 		  ��null��ʾ������xls�ļ�
	 */
	public List<FundCompanyStockListVO> createFcStockAddReduceTable(int preQuarter, int currentQuarter,
			String docPath) {
		log.info("receive createFcStockAddReduceTable.");
		List<FundCompanyStockListVO> resultList = new ArrayList<FundCompanyStockListVO>();
		try {
			log.info("receive createStockAddReduceTable command.");
			fundCompanyStockListDAO.getConnection();
			Map<FundCompanyStockListKey, FundCompanyStockList> preFcStockList = fundCompanyStockListDAO
					.getFcStockList(preQuarter);
			Map<FundCompanyStockListKey, FundCompanyStockList> currentFcStockList = fundCompanyStockListDAO
					.getFcStockList(currentQuarter);
			Iterator<FundCompanyStockListKey> it = currentFcStockList.keySet()
					.iterator();
			while (it.hasNext()) {
				FundCompanyStockListKey currentKey = it.next();
				FundCompanyStockList currentFcStock = currentFcStockList
						.get(currentKey);
				FundCompanyStockListKey preKey=null;
				boolean find=false;
				Iterator<FundCompanyStockListKey> itKey=preFcStockList.keySet().iterator();
				while(itKey.hasNext()){
					preKey=itKey.next();
					if(preKey.getCompanyid()==currentKey.getCompanyid()&&preKey.getStockid().equalsIgnoreCase(currentKey.getStockid())){
						find=true;
						break;
					}
				}
				if(!find)
					continue;
				FundCompanyStockList preFcStock = preFcStockList
						.get(preKey);
				if (preFcStock == null)
					continue;
				FundCompanyStockListVO vo = new FundCompanyStockListVO();
				BeanUtils.copyProperties(vo, currentFcStock);
				vo.setCircleratio(currentFcStock.getCircleratio()
						- preFcStock.getCircleratio());
				vo.setStocknumber(currentFcStock.getStocknumber()
						- preFcStock.getStocknumber());
				resultList.add(vo);
			}
			
			// ��ռ��ͨ�̱�����������
			Collections.sort(resultList, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					FundCompanyStockListVO s1 = (FundCompanyStockListVO) o1;
					FundCompanyStockListVO s2 = (FundCompanyStockListVO) o2;
					int compFcid = s2.getCompanyid() - s1.getCompanyid();
					float compCircleratio = s2.getCircleratio()
							- s1.getCircleratio();
					if (compFcid > 0)
						return 1;
					else if (compFcid == 0) {
						if (compCircleratio > 0)
							return 1;
						else if (compCircleratio == 0)
							return 0;
						else
							return -1;
					} else
						return -1;
				}
			});
			if(docPath!=null){
				// �������ֱ��
				exportService.writeFcStockAddReduceTableXls(preQuarter,
						currentQuarter, docPath,
						Constant.FUNDCOMPANYSTOCKADDBYFUND, resultList);
			}
			return resultList;
		} catch (Exception e) {
			log.error("createFcStockAddReduceTable error::" ,e);
			e.printStackTrace();
			return resultList;
		} finally {
			fundCompanyStockListDAO.releaseConnection();
		}
	}

	/**
	 * ���ɹ�Ʊ������������ 
	 * 
	 * @param preQuarter
	 * @param currentQuarter
	 * @return 
	 */
	public List<FundStockListVO> getFundListByStock(int quarter,String stockID) {
		List<FundStockListVO> resultList=null;
		try {
			log.info("receive getFundListByStock command.");
			fundStockDetailDAO.getConnection();
			resultList = fundStockDetailDAO.getFundListByStock(quarter, stockID);
		} catch (Exception e) {
			log.error("getFundListByStock error::" ,e);
			e.printStackTrace();
		} finally {
			fundStockDetailDAO.releaseConnection();
		}
		return resultList;
	}
}
