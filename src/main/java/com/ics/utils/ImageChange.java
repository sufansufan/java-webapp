package com.ics.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageChange {
	/**
	 * @Description: 将base64编码字符串转换为图片,保存设备安装的图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr
	 *            base64编码字符串
	 * @param path
	 *            图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path,String deviceCode,String headPath) {
		if (imgStr == null) {
			return false;
		};
		BASE64Decoder decoder = new BASE64Decoder();
		// 解密
		try {
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			File file = new File(headPath+deviceCode);
			if(!file.exists()) {
				file.mkdirs();  //mkdirs()创建多级文件夹，mkdir()只能创建一级的文件夹
			}
			OutputStream out = new FileOutputStream(headPath+deviceCode+"/"+path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
	
	public static void main(String[] args) {
	    String strImg = getImageStr("D:/test3.png");
	    generateImage(strImg, "test4.jpg","2323","D:/deviceImages/");
	}
}
