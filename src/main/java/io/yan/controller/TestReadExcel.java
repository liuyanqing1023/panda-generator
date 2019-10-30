package io.yan.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import io.yan.utils.ReadExcel;

/**
 * 测试类
 * 
 * @author ChuanJing
 *
 */
public class TestReadExcel {

	public static void main(String[] args) throws Exception {

//	ReadExcel re = new ReadExcel();
//	List<List<String>> list = re.read("C:\\Users\\Iris004\\Desktop\\测试表格\\3.1 BMBS 经销商销售运营管理_活跃休眠线索呼出邀约登记表.xlsx", 7);
//	List<List<String>> list = re.read("C:\\Users\\Iris004\\Desktop\\经销商销售.xls", 7);

	// 遍历读取结果
//	if (list != null) {
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print("第" + (8+i) + "行");
//			
//			List<String> cellList = list.get(i);
//			for (int j = 0; j < cellList.size(); j++) {
//				System.out.print(" 第" + (j + 1) + "列值：");
//				System.out.print(" " + cellList.get(j));
//			}
//			System.out.println();
//		}
//	}

		// ----------------------测试多线程，4个线程同时读取4个表格，分别存在4个txt里----------------------
		Thread t1 = new Thread() {
			@Override
			public void run() {
				ReadExcel re = new ReadExcel();
				List<List<String>> list = re.read("C:\\Users\\Administrator\\Desktop\\北京畅的车辆信息模板8.28.xlsx", 7);
				
				if (list != null) {
					
					//-----------------------------写入文件----------------
					BufferedWriter bw=null;
					String str = null;
					try {
						bw=new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\aa.txt"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
					
					for (int i = 0; i < list.size(); i++) {
						
						//-----------------------------写入文件----------------
						str = "t1第" + (8+i) + "行";
						try {
							bw.write(str);
							bw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
						List<String> cellList = list.get(i);
						for (int j = 0; j < cellList.size(); j++) {
							
							//-----------------------------写入文件----------------
							str = " " + cellList.get(j);
							try {
								bw.write(str);
								bw.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
							//-----------------------------写入文件----------------
							
						}
						
						//-----------------------------写入文件----------------
						try {
							bw.newLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
					}
					
					//-----------------------------写入文件----------------
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
				}
			}
		};
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				ReadExcel re = new ReadExcel();
				List<List<String>> list = re.read("C:\\Users\\Iris004\\Desktop\\测试表格\\大表2.xlsx", 7);

				if (list != null) {
					
					//-----------------------------写入文件----------------
					BufferedWriter bw=null;
					String str = null;
					try {
						bw=new BufferedWriter(new FileWriter("C:\\Users\\Iris004\\Desktop\\测试表格\\测试表格2.txt"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
					
					for (int i = 0; i < list.size(); i++) {
						
						//-----------------------------写入文件----------------
						str = "t2第" + (8+i) + "行";
						try {
							bw.write(str);
							bw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
						List<String> cellList = list.get(i);
						for (int j = 0; j < cellList.size(); j++) {
							
							//-----------------------------写入文件----------------
							str = " " + cellList.get(j);
							try {
								bw.write(str);
								bw.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
							//-----------------------------写入文件----------------
							
						}
						
						//-----------------------------写入文件----------------
						try {
							bw.newLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
					}
					
					//-----------------------------写入文件----------------
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
				}
			}
		};

		Thread t3 = new Thread() {
			@Override
			public void run() {
				ReadExcel re = new ReadExcel();
				List<List<String>> list = re.read("C:\\Users\\Iris004\\Desktop\\测试表格\\大表3.xlsx", 7);

				if (list != null) {
					
					//-----------------------------写入文件----------------
					BufferedWriter bw=null;
					String str = null;
					try {
						bw=new BufferedWriter(new FileWriter("C:\\Users\\Iris004\\Desktop\\测试表格\\测试表格3.txt"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
					
					for (int i = 0; i < list.size(); i++) {
						
						//-----------------------------写入文件----------------
						str = "t3第" + (8+i) + "行";
						try {
							bw.write(str);
							bw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
						List<String> cellList = list.get(i);
						for (int j = 0; j < cellList.size(); j++) {
							
							//-----------------------------写入文件----------------
							str = " " + cellList.get(j);
							try {
								bw.write(str);
								bw.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
							//-----------------------------写入文件----------------
							
						}
						
						//-----------------------------写入文件----------------
						try {
							bw.newLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
					}
					
					//-----------------------------写入文件----------------
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
				}
			}
		};
		
		Thread t4 = new Thread() {
			@Override
			public void run() {
				ReadExcel re = new ReadExcel();
				List<List<String>> list = re.read("C:\\Users\\Iris004\\Desktop\\测试表格\\大表4.xlsx", 7);

				if (list != null) {
					
					//-----------------------------写入文件----------------
					BufferedWriter bw=null;
					String str = null;
					try {
						bw=new BufferedWriter(new FileWriter("C:\\Users\\Iris004\\Desktop\\测试表格\\测试表格4.txt"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
					
					for (int i = 0; i < list.size(); i++) {
						
						//-----------------------------写入文件----------------
						str = "t4第" + (8+i) + "行";
						try {
							bw.write(str);
							bw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
						List<String> cellList = list.get(i);
						for (int j = 0; j < cellList.size(); j++) {
							
							//-----------------------------写入文件----------------
							str = " " + cellList.get(j);
							try {
								bw.write(str);
								bw.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
							//-----------------------------写入文件----------------
							
						}
						
						//-----------------------------写入文件----------------
						try {
							bw.newLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//-----------------------------写入文件----------------
						
					}
					
					//-----------------------------写入文件----------------
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//-----------------------------写入文件----------------
				}
			}
		};
		
		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
		// ----------------------以上为测试多线程，看着不舒服 可以删掉----------------------
	}

	public static void writeDiskInfo(String path, String content) {
		
		BufferedWriter bw=null;
		try {
			bw=new BufferedWriter(new FileWriter(path));//字符输出流  写数据到文件
			String str = content;
			bw.write(str);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace(); 
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}