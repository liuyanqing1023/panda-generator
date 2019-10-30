package io.yan.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**  
* @ClassName: FileUtils  
* @Description: TODO 
* @author 刘彦青  
* @date 2018年7月6日  
*  
*/
public class FileUtils {
	
	/**
	 * 读文件
	 * @param url
	 * @return
	 */
	public static String getFile(String  url) {
		//创建字符流
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			FileReader fr = new FileReader(url);
			reader = new BufferedReader(fr);
			String line = null;
			while((line=reader.readLine())!=null){  //readLine方法返回的时候是不带换行符的。
				System.out.println(line);
				sb.append(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return sb.toString();
	}
	
	public static void writerFile(String content, String  url) throws IOException {
		//创建file
		File file = new File(url);
		if (!file.isFile()) return;
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bufw = new BufferedWriter(fw);//让缓冲区和指定流相关联。
		bufw.write(content);
//		bufw.newLine(); //写入一个换行符，这个换行符可以依据平台的不同写入不同的换行符。
		bufw.flush();//对缓冲区进行刷新，可以让数据到目的地中。
		bufw.close();//关闭缓冲区，其实就是在关闭具体的流。
	}
	
    public static List<File> getAllFile(File dirctory ){
    	List<File> list = new ArrayList<>();
    	
    	File[] files = dirctory.listFiles();
    	for (File file : files) {
			if (file.isDirectory()) {
				list.addAll(getAllFile(file));
			}else {
				list.add(file);
			}
		}
    	return list;
    }
	
    
    public static String getFileTree(String url) {
    	
    	
    	
    	
    	return "";
    }
}


