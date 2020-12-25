package com.ics.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import com.alibaba.fastjson.JSON;
import java.util.*;
import com.ics.utils.HttpMapQuery;
import com.alibaba.fastjson.JSONObject;

public class WeixinUtil {
    private static final String API_BASE = "https://qyapi.weixin.qq.com/cgi-bin";

    public static String getAccesstoken(String corpId, String corpSecret) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("corpid", corpId);
        map.put("corpsecret", corpSecret);
        String respStr = sendGet("/gettoken", map);
        JSONObject jsonObject = JSONObject.parseObject(respStr);
        return jsonObject.getString("access_token");
    }

    public static boolean sendMessage(String corpId, String corpSecret, String agentId, String content) {

      return true;
    }

    public static String sendGet(String path, Map<?,?> param) {
        String query = HttpMapQuery.urlEncodeUTF8(param);
        return sendGet(path, query);
    }

    public static String sendGet(String path, String param) {
        System.out.println(param);
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = API_BASE + path + "?" + param;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String path, Map<?,?> param) {
      String body = JSON.toJSONString(param);
      return sendPost(path, body);
    }

    public static String sendPost(String path, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(API_BASE + path);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
      // mvn exec:java -Dexec.mainClass="com.ics.utils.WeixinUtil"
      String respStr = WeixinUtil.getAccesstoken("demo", "demo");

      Map<String,Object> map = new HashMap<String,Object>();
      map.put("touser", "LinJian");
      map.put("msgtype", "text");
      map.put("agentid", "1000002");

      Map<String,String> map1 = new HashMap<String,String>();
      map1.put("content", "你好，我是谁？");
      map.put("text", map1);

      respStr = WeixinUtil.sendPost("/message/send?access_token=" + respStr, map);
      System.out.println(respStr);
    }
}
