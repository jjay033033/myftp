package top.lmoon.myftp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FileUtil {

//	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	public static String readFileAndReplaceValues(String pathname,Map<String,String> valueMap) throws Exception{
		if(valueMap==null||valueMap.isEmpty()){
			return null;
		}
		Map<String,String> vMap = new HashMap<String,String>();
		vMap.putAll(valueMap);
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			file = new File(pathname);
			if (file == null || !file.exists()) {
				throw new Exception("没有这个文件："+pathname);
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			String buf = null;
			boolean hasFind = false;
			int index = -1;
			while ((buf = br.readLine()) != null) {
				if(!vMap.isEmpty()){
					hasFind = false;
					for(Iterator<String> it = vMap.keySet().iterator();it.hasNext();){
						String key = it.next();
						String value = vMap.get(key);
						if((index = buf.indexOf(key))>-1){
							sb.append(buf.substring(0, index+key.length()));
							sb.append(value);
							sb.append("\n"); 
							vMap.remove(key);
							hasFind = true;
							break;
						}
					}
					if(!hasFind){
						sb.append(buf); 
						sb.append("\n"); 
					}
				}else{
					sb.append(buf); 
					sb.append("\n"); 
				}
				
            } 
		} catch (Exception e) {
//			logger.error("readFile:", e);
//			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return sb.toString();
	}

	public static String readFileAndReplaceValue(String pathname,String key,String value) throws Exception{
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			file = new File(pathname);
			if (file == null || !file.exists()) {
				throw new Exception("没有这个文件："+pathname);
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			String buf = null;
			boolean hasFind = false;
			int index = -1;
			while ((buf = br.readLine()) != null) { 					
				if(!hasFind && (index = buf.indexOf(key))>-1){
					hasFind = true;
					sb.append(buf.substring(0, index+key.length()));
					sb.append(value);
					sb.append("\n"); 
				}else{
					sb.append(buf); 
					sb.append("\n"); 
				}
            } 
		} catch (Exception e) {
//			logger.error("readFile:", e);
//			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return sb.toString();
	}

	public static String readFile(String pathname) throws Exception {
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			file = new File(pathname);
			if (file == null || !file.exists()) {
				return null;
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			String buf = null;
			while ((buf = br.readLine()) != null) {
				sb.append(buf);
			}
		} catch (Exception e) {
//			logger.error("readFile:", e);
//			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return sb.toString();
	}

	public static void writeFile(String content, String pathname) throws Exception {
		File file = null;
		FileOutputStream fos = null;
		content.getBytes();
		try {
			// ./gui-config.json
			file = new File(pathname);
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);
			fos.write(content.getBytes("utf-8"));
			fos.flush();
		} catch (Exception e) {
//			logger.error("writeFile:", e);
//			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
	}

}
