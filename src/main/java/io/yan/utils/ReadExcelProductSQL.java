package io.yan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.yan.entity.DataStructureBean;
import io.yan.entity.ImportDataRequestBean;

/**
 * 读取 excel表格，兼容2003和2007
 * @author ChuanJing
 */
public class ReadExcelProductSQL {

	/** 总行数 */
	private int totalRows = 0;

	/** 总列数 */
	private int totalCells = 0;

	/** 错误信息 */
	private String errorInfo;

	/**模板信息*/
	private List<DataStructureBean> modelMsg; 
	
	/**表名*/
	private String tableName;
	
	/** 构造方法 */
	public ReadExcelProductSQL() {}

	/**
	 * 得到总行数
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * 得到总列数
	 */
	public int getTotalCells() {
		return totalCells;
	}

	/**
	 * 得到错误信息
	 */
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * 
	 * 验证excel文件
	 * 
	 * @param：filePath 文件完整路径
	 * @return 返回 true 表示文件没有问题
	 */
	public boolean validateExcel(String filePath) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorInfo = "文件不是excel格式";
			return false;
		}

		/** 检查文件是否存在 */
		File file = new File(filePath);

		if (file == null || !file.exists()) {
			errorInfo = "文件不存在";
			return false;
		}

		return true;
	}

	
	/**
	 * 根据文件名读取excel文件      List<DataStructureBean> list
	 * 
	 * @param filePath 文件完整路径
	 * @param ignoreRows 读取数据忽略的行数，比喻第一行不需要读入，忽略的行数为1
	 * @throws Exception 
	 * @return：List  最后读取的结果，数据结构：List<List<String>>
	 */
	public String productInsertSQL(ImportDataRequestBean param) throws Exception {

		//解析XML
		InputStream inputStream = new FileInputStream(param.getModelFieldUrl());
		this.modelMsg = ParseXML.getDataStructureBean(inputStream);
		this.tableName = param.getTableName();		
		
		//生产SQL文件
		String sqlFieldUrl = param.getModelFieldUrl()
				.substring(0, param.getModelFieldUrl().lastIndexOf("\\"))+
				param.getModelFieldUrl().
				substring(param.getModelFieldUrl().lastIndexOf("\\"), param.getModelFieldUrl().lastIndexOf("."))
				+".sql";
		
		//读取Excel文件中的数据
		String read = this.read(param.getExcelFieldUrl(), 1);

		
		File file = new File(sqlFieldUrl);
		file.delete();
		file.createNewFile();
		
		
		 FileUtils.writerFile(read, sqlFieldUrl);
		/** 返回最后读取的结果 */
		return sqlFieldUrl;
	}
	
	
	
	
	/**
	 * 根据文件名读取excel文件      List<DataStructureBean> list
	 * 
	 * @param filePath 文件完整路径
	 * @param ignoreRows 读取数据忽略的行数，比喻第一行不需要读入，忽略的行数为1
	 * @return：List  最后读取的结果，数据结构：List<List<String>>
	 */
	private String read(String filePath, int ignoreRows) {

		String string ="";
		InputStream is = null;

		try {
			/** 验证文件是否合法 */
			if (!validateExcel(filePath)) {
				System.out.println(errorInfo);
				return null;
			}

			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (isExcel2007(filePath)) {
				isExcel2003 = false;
			}

			/** 调用本类提供的根据流读取的方法 */
			File file = new File(filePath);
			is = new FileInputStream(file);
			string = read(is, isExcel2003, ignoreRows);
			is.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {

				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}

			}
		}

		/** 返回最后读取的结果 */
		return string;
	}

	/**
	 * 根据流读取Excel文件
	 * 
	 * @param inputStream
	 * @param isExcel2003  是否是2003的表格（xls格式）
	 * @param ignoreRows 读取数据忽略的行数，比喻第一行不需要读入，忽略的行数为1
	 * @return：List
	 */
	private String read(InputStream inputStream, boolean isExcel2003, int ignoreRows) {
		String string ="";
		try {

			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;

			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			string = read(wb, ignoreRows);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return string;
	}

	/**
	 * 读取数据
	 * 
	 * @param Workbook
	 * @param ignoreRows 读取数据忽略的行数，比喻第一行不需要读入，忽略的行数为1
	 * @reture：List<List<String>>
	 */
	private String read(Workbook wb, int ignoreRows) {

		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(0);

		/** 得到Excel的行数 */
		this.totalRows = sheet.getPhysicalNumberOfRows();

		/** 得到Excel的列数，不从表格的第一行得到列数，从忽略之后的，要读取的第一行 获取列数*/
		if (this.totalRows >= 1 && sheet.getRow(ignoreRows) != null) {
			this.totalCells = sheet.getRow(ignoreRows).getPhysicalNumberOfCells();
		}

		
		StringBuilder sqlSB = new StringBuilder();
		
		//设置字段
		sqlSB.append("INSERT INTO \n").append(tableName).append(" \n(");
		for (int i = 0; i < modelMsg.size(); i++) {
			sqlSB.append(modelMsg.get(i).getFieldName());
			if (i<modelMsg.size()-1) {
				sqlSB.append(", ");
			}
		}
		sqlSB.append(" ) \n VALUES ");
		
		System.err.println("字段模型="+modelMsg.size());
		System.err.println("Excel执行行数="+this.totalRows);
		System.err.println("Excel执行列数="+this.getTotalCells());
		
		/** 循环Excel的行，不从表格的第一行循环，去掉忽略的行数*/
		for (int r = ignoreRows; r < this.totalRows; r++) {
			Row row = sheet.getRow(r);

			if (row == null) {
				continue;
			}

			sqlSB.append("\n ( ");
			/** 循环Excel的列 */
			for (int c = 0; c < this.getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";

				if (null != cell) {
					System.err.println(c);
					String falg = modelMsg.get(c).getFieldType();
					
					
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						
						// 如果数字是日期类型，就转换成日期
						if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
			                SimpleDateFormat sdf = null;  
			                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
			                    sdf = new SimpleDateFormat("HH:mm");  
			                } else {// 日期  
			                    sdf = new SimpleDateFormat("yyyy年MM月dd日");  
			                }  
			                Date date = cell.getDateCellValue();  
			                cellValue = sdf.format(date); 
			                sqlSB.append("'");
			                sqlSB.append(cellValue);
			                sqlSB.append("'");
			                if (c <= this.getTotalCells()-1) {
			                	sqlSB.append(", ");
							}
			                
			            } else if (cell.getCellStyle().getDataFormat() == 31) {  
			                // 处理自定义日期格式：yyyy年m月d日(通过判断单元格的格式id解决，id的值是31)  
			                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");  
			                double value = cell.getNumericCellValue();  
			                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
			                cellValue = sdf.format(date); 
			                sqlSB.append("'");
			                sqlSB.append(cellValue);
			                sqlSB.append("'");
			                if (c <= this.getTotalCells()-1) {
			                	sqlSB.append(", ");
							}
			            } else {  
			            	System.out.println("行数="+r);
			            	System.out.println("列数="+c);
			            	
			                double value = cell.getNumericCellValue();  
			                System.out.println("double value = "+value);
			                CellStyle style = cell.getCellStyle(); 
			                System.out.println(" CellStyle style =  "+style);
			                DecimalFormat format = new DecimalFormat();  
			                String temp = style.getDataFormatString();  
			                System.out.println(" String temp  = "+temp);
			                // 单元格设置成常规  
			                if (temp.equals("General")) {  
			                    format.applyPattern("#");  
			                }  
			                cellValue = format.format(value);  
			                
						DecimalFormat df = new DecimalFormat("0");//使用DecimalFormat类对科学计数法格式的数字进行格式化
						cellValue = df.format(cell.getNumericCellValue());
						
		                sqlSB.append(cellValue);
		                if (c <= this.getTotalCells()-1) {
		                	sqlSB.append(", ");
						}
						
			            }  
						break;

					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						sqlSB.append("'");
		                sqlSB.append(cellValue);
		                sqlSB.append("'");
		                if (c <= this.getTotalCells()-1) {
		                	sqlSB.append(", ");
						}
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}
			}
			sqlSB.append(" )");
			if (r < this.totalRows-1) {
				sqlSB.append(", ");
			}
			
		}
		System.err.println(sqlSB.toString());
		return sqlSB.toString();
	}

	/**
	 * 是否是2003的excel，返回true是2003
	 * 
	 * @param filePath 文件完整路径
	 * @return boolean true代表是2003
	 */
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 * 是否是2007的excel，返回true是2007
	 * 
	 * @param filePath 文件完整路径
	 * @return boolean true代表是2007
	 */
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}
}