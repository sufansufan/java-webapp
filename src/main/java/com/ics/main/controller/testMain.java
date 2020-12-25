package com.ics.main.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.JsonResult;
import com.ics.utils.StringUtil;
import com.ics.utils.security.SM3;

public class testMain {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String methodUrl="https://open.ys7.com/api/lapp/token/get?"
//				+ "appKey=f3499d7289564900a0adcfe3378161ff"
//				+ "&appSecret=067e4f346b769bd1a46fa0db26ac99e0";
//		String body="";
//		String httpURLPOSTCase = CommonUtil.httpURLPOSTCase(methodUrl, body);
//		Map<String, Object> map=JSON.parseObject(httpURLPOSTCase);
//		Map<String, Object> mapData=JSON.parseObject(map.get("data")+"");;
//		System.out.println(mapData.get("accessToken"));
		
		System.out.println(SM3.hash("zhihuihuanbao2019" + ConstantProperty.userSalt));
	}

}
