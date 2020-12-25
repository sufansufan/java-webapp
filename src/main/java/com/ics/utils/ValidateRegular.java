package com.ics.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 验证java的一些正则表达式
 * @author yi
 * @data 2017年12月10日 下午3:59:38
 */
public class ValidateRegular {
	
	/**
	 * 验证是否包含特殊字符
	 * @param str
	 * @return
	 */
	public static boolean isSpecialChar(String str)
    {
		String reg = "[` ~!@#$%^&*()+\\-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern pattern = Pattern.compile(reg);
        Matcher match = pattern.matcher(str);
        
        return match.find();  
    }
	/**
	 * 验证手机号
	 */
	public static boolean isMobile(String mobiles)
    {
		Pattern p = Pattern.compile("^1\\d{10}$");  
		Matcher match = p.matcher(mobiles);  
		return match.matches();  
    }
}
