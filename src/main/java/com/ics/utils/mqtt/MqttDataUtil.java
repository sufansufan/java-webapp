package com.ics.utils.mqtt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ics.dataDesources.model.MonitorFactorTemplate;

public class MqttDataUtil {

		//获取开关编码
		public static List<String> getSwitchCodeList(String factorCode,List<String> switchCodeList) {
			switchCodeList.add(factorCode+"_1");
			switchCodeList.add(factorCode+"_2");
			switchCodeList.add(factorCode+"_3");
			switchCodeList.add(factorCode+"_4");
			switchCodeList.add(factorCode+"_5");
			switchCodeList.add(factorCode+"_6");
			switchCodeList.add(factorCode+"_7");
			switchCodeList.add(factorCode+"_8");
			switchCodeList.add(factorCode+"_9");
			switchCodeList.add(factorCode+"_10");
			switchCodeList.add(factorCode+"_11");
			switchCodeList.add(factorCode+"_12");
			switchCodeList.add(factorCode+"_13");
			switchCodeList.add(factorCode+"_14");
			switchCodeList.add(factorCode+"_15");
			switchCodeList.add(factorCode+"_16");
	      	 return switchCodeList;
	   }
		//获取开关数值
	    public static List<String> getSwitchValueList(String factorValue,List<String> switchValueList) {
	    	 //DI/O 低 16bit
	    	 String factorCode_bit = Integer.toBinaryString(Integer.valueOf(factorValue));
	    	 if(factorCode_bit.length()<32) {
	    	   	factorCode_bit = String.format("%32s", factorCode_bit).replace(' ', '0');
	    	 }
	    	 String Q000Fh = factorCode_bit.substring(16, 17);
	       	 String Q000Eh = factorCode_bit.substring(17, 18);
	       	 String Q000Dh = factorCode_bit.substring(18, 19);
	       	 String Q000Ch = factorCode_bit.substring(19, 20);
	       	 String Q000Bh = factorCode_bit.substring(20, 21);
	       	 String Q000Ah = factorCode_bit.substring(21, 22);
	       	 String Q0009h = factorCode_bit.substring(22, 23);
	       	 String Q0008h = factorCode_bit.substring(23, 24);
	       	 String Q0007h = factorCode_bit.substring(24, 25);
	       	 String Q0006h = factorCode_bit.substring(25, 26);
	       	 String Q0005h = factorCode_bit.substring(26, 27);
	       	 String Q0004h = factorCode_bit.substring(27, 28);
	       	 String Q0003h = factorCode_bit.substring(28, 29);
	       	 String Q0002h = factorCode_bit.substring(29, 30);
	       	 String Q0001h = factorCode_bit.substring(30, 31);
	       	 String Q0000h = factorCode_bit.substring(31, 32);
	       	switchValueList.add(Q0000h);
	       	switchValueList.add(Q0001h);
	       	switchValueList.add(Q0002h);
	       	switchValueList.add(Q0003h);
	       	switchValueList.add(Q0004h);
	       	switchValueList.add(Q0005h);
	       	switchValueList.add(Q0006h);
	       	switchValueList.add(Q0007h);
	       	switchValueList.add(Q0008h);
	       	switchValueList.add(Q0009h);
	       	switchValueList.add(Q000Ah);
	       	switchValueList.add(Q000Bh);
	       	switchValueList.add(Q000Ch);
	       	switchValueList.add(Q000Dh);
	       	switchValueList.add(Q000Eh);
	       	switchValueList.add(Q000Fh);
	       	 
	       	 return switchValueList;
	    }
	    
	    //获取报警内容
	    public static List<List<String>> getAlarmContentList(String factorCode,String factorValue,List<String> alarmContentList, List<String> alarmContentCodeList,List<MonitorFactorTemplate> monitorFactorTemplateList){
	     	 String factorCode_bit = Integer.toBinaryString(Integer.valueOf(factorValue));
			 if(factorCode_bit.length()<32) {
			   	factorCode_bit = String.format("%32s", factorCode_bit).replace(' ', '0');
			 }
			 
    	   	 String value16 = factorCode_bit.substring(16, 17);
    	   	 String value15 = factorCode_bit.substring(17, 18);
    	   	 String value14 = factorCode_bit.substring(18, 19);
    	   	 String value13 = factorCode_bit.substring(19, 20);
    	   	 String value12 = factorCode_bit.substring(20, 21);
    	   	 String value11 = factorCode_bit.substring(21, 22);
    	   	 String value10 = factorCode_bit.substring(22, 23);
    	   	 String value9 = factorCode_bit.substring(23, 24);
    	   	 String value8 = factorCode_bit.substring(24, 25);
    	   	 String value7 = factorCode_bit.substring(25, 26);
    	   	 String value6 = factorCode_bit.substring(26, 27);
    	   	 String value5 = factorCode_bit.substring(27, 28);
    	   	 String value4 = factorCode_bit.substring(28, 29);
    	   	 String value3 = factorCode_bit.substring(29, 30);
    	   	 String value2 = factorCode_bit.substring(30, 31);
    	   	 String value1 = factorCode_bit.substring(31, 32);
    	   	 
    	   	 List<String> valueList = new ArrayList<String>();
    	   	 valueList.add(value1);
    	   	 valueList.add(value2);
    	   	 valueList.add(value3);
    	   	 valueList.add(value4);
    	   	 valueList.add(value5);
    	   	 valueList.add(value6);
    	   	 valueList.add(value7);
    	   	 valueList.add(value8);
    	   	 valueList.add(value9);
    	   	 valueList.add(value10);
    	   	 valueList.add(value11);
    	   	 valueList.add(value12);
    	   	 valueList.add(value13);
    	   	 valueList.add(value14);
    	   	 valueList.add(value15);
    	   	 valueList.add(value16);
    	   	 
    	   	 List<String> codeList = new ArrayList<String>();
    	   	 codeList = getSwitchCodeList(factorCode, codeList);
    	   	 
    	   	 List<List<String>> list = new ArrayList<List<String>>();
    	   	 
    	   	 for(int i=0;i<codeList.size();i++) {
    	   		 String tempCode1 = codeList.get(i);
    	   		 for(int j=0;j<monitorFactorTemplateList.size();j++) {
    	   			String tempCode2 = monitorFactorTemplateList.get(j).getFactorCode();
    	   			 if(tempCode1.equals(tempCode2)) {
    	   				 if(valueList.get(i).equals("1")) {
    	   					alarmContentCodeList.add(tempCode1);
    	   					alarmContentList.add(monitorFactorTemplateList.get(j).getFactorName());
    	   					list.add(alarmContentCodeList);
    	   					list.add(alarmContentList);
    	   				 }
    	   				
    	   			 }
    	   		 }
    	   	 }
    	   	    	 	
    	   	
    	   	return list;
	    }
	    
	    
	    //去掉list2中包含list1的所有元素
	    public static List<String> removeRepeatFactor(List<String> list1 , List<String> list2)throws Exception{
	           if(list1 != null && list2 != null) {
	               if (list1.size() != 0 && list2.size() != 0) {
	                   Collection A = new ArrayList(list1);
	                   Collection B = new ArrayList(list2);
	                   A.retainAll(B);
	                   if (A.size() != 0) {
	                       B.removeAll(A);
	                   }
	                   return (List<String>) B;
	               }
	           }
	           return list2;
	    }
	    
	    public static void main(String[] args) {
	    	String factorValue = "-32767";
	    	 String factorCode_bit = Integer.toBinaryString(Integer.valueOf(factorValue));
	    	 if(factorCode_bit.length()<32) {
				   	factorCode_bit = String.format("%32s", factorCode_bit).replace(' ', '0');
				 }
	    	 System.out.println(factorCode_bit);
	    	 
//	    	 String turn = turn(-32767);
//	 		System.out.println(turn);
//	 		System.out.println(Integer.toBinaryString(-32767));
	 		
	 	
		}
	    
	    public static String decimal2Scale(int number, int scale) {
	        if (2 > scale || scale > 32) {
	            throw new IllegalArgumentException("scale is not in range");
	        }
	        String result = "";
	        while (0 != number) {
	            result = number % scale + result;
	            number = number / scale;
	        }
	        
	        return result;
	    }
	
	    public static String turn(int number) {
			StringBuffer sb = new StringBuffer();
			if (number > 0) {
				while (number != 0) {
					if (number > 0) {
						sb.append(number % 2);
						number = number / 2;
					}
				}
			} else if (number < 0) {
				for (int i = 0; i < 32; i++) {
					// 0x80000000 是一个首位为1，其余位数为0的整数
					int t = (number & 0x80000000 >>> i) >>> (31 - i);
					sb.append(t);
				}
			}
			return sb.toString();
		}

}
