package com.tzx.sqlspatiatools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class CreateFileUtil {
	/**
	 * 生成.json格式文件
	 */
	public static boolean createJsonFile(String jsonString, String filePath, String fileName) {
		// 标记文件生成是否成功
		boolean flag = true;

		// 拼接文件完整路径
		String fullPath = filePath + File.separator + fileName + ".json";

		// 生成json格式文件
		try {
			// 保证创建一个新文件
			File file = new File(fullPath);
			if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
				file.getParentFile().mkdirs();
			}
			if (file.exists()) { // 如果已存在,删除旧文件
				file.delete();
			}
			file.createNewFile();

			// 格式化json字符串
			jsonString = JsonFormatTool.formatJson(jsonString);

			// 将格式化后的字符串写入文件
			Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			write.write(jsonString);
			write.flush();
			write.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		// 返回是否成功的标记
		return flag;
	}
	

	public static String getDatafromFile(String fileName) {
		String path = "./" + fileName + ".json";
		File f = new File(path);
		if(!f.exists()) {
			return "";
		}
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	public static void main(String[] args) {
		String jsonData = "[{'name':'John', 'grade':[{'course':'English','score':100},{'course':'Math','score':78}]},{'name':'Tom', 'grade':[{'course':'English','score':86},{'course':'Math','score':90}]}]";
		String filePath = "./";
		String fileName = "test";
		createJsonFile(jsonData, filePath, fileName);
		
		String js = getDatafromFile(fileName);
		System.out.println(js);
	}
}