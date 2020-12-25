package com.ics.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 获取properties配置文件属性值
 * 
 * @author yi
 * 
 */
public class PropertiesUtils {

	public static String PATH = "/config.properties";
	public static String VERSIONPATH = "/version.properties";
	
	private static Properties properties;
	static {
		try {
			InputStream is = PropertiesUtils.class.getResourceAsStream(PATH);
			InputStream version = PropertiesUtils.class.getResourceAsStream(VERSIONPATH);
			properties = new Properties();
			properties.load(new InputStreamReader(is, "utf-8"));
			properties.load(new InputStreamReader(version, "utf-8"));
			if (is != null) {
				is.close();
			}
			if (version != null) {
				version.close();
			}
		} catch (Exception e) {
			System.out.println(e + "file  not found");
		}
	}

	/**
	 * getPara 获取properties文件里值
	 * 
	 * @param propertiesPath
	 * @param key
	 * @return
	 */
	public static String getPara(String propertiesPath, String key) {
		return properties.getProperty(key);
	}

	public static String getPara(String key) {
		return properties.getProperty(key);
	}

	
	/**
	 * 属性读取
	 * @param key
	 * @param flag 是否实时
	 * @return
	 */
	public static String getPara(String key, boolean flag) {
		
		if(flag) {
			try {
				InputStream is = PropertiesUtils.class.getResourceAsStream(PATH);
				InputStream version = PropertiesUtils.class.getResourceAsStream(VERSIONPATH);
				properties = new Properties();
				properties.load(new InputStreamReader(is, "utf-8"));
				properties.load(new InputStreamReader(version, "utf-8"));
				if (is != null) {
					is.close();
				}
				if (version != null) {
					version.close();
				}
			} catch (Exception e) {
				System.out.println(e + "file  not found");
			}
		}
		return properties.getProperty(key);
	}
	
	public static void setPara(String key, String value) {
		
		properties.setProperty(key, value);
		String path = PropertiesUtils.class.getResource(PATH).getFile();
        try {
        	FileOutputStream outputFile = new FileOutputStream(path);
            properties.store(outputFile, null);
            outputFile.flush();
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	public static void main(String[] args) {

	}
}
