package com.ics.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * 
 * 字符串工具类
 * @author yi
 */
public class StringUtil {
	
	private static char[] m_hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
     * byte数组转换成16进制字符串
     * @param _aby
     * @return
     */
    public static final String byteArray2StringHex(byte[] _aby) 
    {
    	if (_aby == null || _aby.length == 0) 
    	{
    		return null;   
        }
    	
    	int iLen = _aby.length;
    	char[] achHex = new char[iLen * 2];
        for ( int i = 0; i < iLen; i++ ) 
        {
            int iVal = _aby[i] & 0xFF;
            achHex[i * 2] = m_hexArray[iVal >>> 4];
            achHex[i * 2 + 1] = m_hexArray[iVal & 0x0F];
        }
        return new String(achHex);
    }
    
    /**
     * 字符串转换成byte数组
     * @param _str
     * @return
     */
    public static final byte[] string2ByteArray(String _str)
    {
    	if (_str == null)
    	{
    		return null;
    	}
    	
    	return _str.getBytes();
    }
	
	/**
	 * md5加密
	 * @param str
	 * @return
	 */
	public static String encryptMd56(String str){
		String re_md5 = new String();
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
 
            int i;
 
            StringBuilder build = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                	build.append("0");
                build.append(Integer.toHexString(i));
            }
 
            re_md5 = build.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
	}
	
	/**
	 * 生成随机码
	 * @param length 长度
	 * @param flag 1纯数字,2纯字母,3或other则生成a-z A-Z 0-9随机码
	 * @return
	 */
	public static String getRandomStr(int length, int flag){
		
		char mapChar[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char mapStr[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', '0' };
		char mapint[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		String str = "";
		
		switch (flag) {
		case 1:
			for (int i = 0; i < length; i++) {
				int count = (int) (mapint.length * Math.random());
				if (count >= mapint.length) {
					str += mapint[mapint.length - 1];
				} else {
					str += mapint[count];
				}
			}
			break;
		case 2:
			for (int i = 0; i < length; i++) {
				int count = (int) (mapChar.length * Math.random());
				if (count >= mapChar.length) {
					str += mapChar[mapChar.length - 1];
				} else {
					str += mapChar[count];
				}

			}
			break;

		default:
			
			for (int i = 0; i < length; i++) {
				int count = (int) (mapStr.length * Math.random());
				if (count >= mapStr.length) {
					str += mapStr[mapStr.length - 1];
				} else {
					str += mapStr[count];
				}

			}
			break;
		}
		return str;
	}
	
	
	/**
	 * list元素集用split分隔开
	 * @param split
	 * @param elementList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String join(String split, List elementList){
		StringBuffer result = new StringBuffer();
		if(elementList!=null && elementList.size()>0){
			for (int i = 0; i < elementList.size(); i++) {
				result.append(elementList.get(i));
				if(i!=elementList.size()-1){
					result.append(split);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * 判断container是否有包含数组中的元素
	 * @param container url
	 * @param regex 过滤的数组
	 * @return
	 */
	public static boolean contains(String container, String[] regex) {  
        for (String str : regex) {  
            if (container.contains(str)) {  
                return true;  
            }  
        }  
        return false;  
    }
	
	/**
	 * 判断数组中元素是否包含regex字符串
	 * @param container
	 * @param regex
	 * @return
	 */
    public static boolean contains(String[] container, String regex) {  
        for (String str : container) {  
            if (str.equals(regex)) {  
                return true;  
            }  
        }  
        return false;  
    }  
    
    /** 
     * 去除字符串首尾出现的某个字符. 
     * @param source 源字符串. 
     * @param element 需要去除的字符. 
     * @return String. 
     */  
    public static String trimFirstAndLastChar(String source,char element,char endelement){  
        boolean beginIndexFlag = true;  
        boolean endIndexFlag = true;  
        do{  
            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;  
            int endIndex = source.lastIndexOf(endelement) + 1 == source.length() ? source.lastIndexOf(endelement) : source.length();  
            source = source.substring(beginIndex, endIndex);  
            beginIndexFlag = (source.indexOf(element) == 0);  
            endIndexFlag = (source.lastIndexOf(endelement) + 1 == source.length());  
        } while (beginIndexFlag || endIndexFlag);  
        return source;  
    } 
	
}
