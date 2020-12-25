package com.ics.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.system.model.SysOrg;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * <p>
 * Title: CommonUtil
 * </p>
 * <p>
 * Description: 公共工具类
 * </p>
 * 
 * @author yi
 * @date 2017年10月25日 下午2:33:56
 */
public class CommonUtil {

	
	/**
	 * 登录用户Session名称
	 */
	public static String mSessionLoginUser = "LOGINUSERINFO";

	/**
	 * 生成32位id
	 * 
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取登录注册用户的信息
	 * 
	 * @return
	 */
	public static Object getLoginUserInfo(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		SysUser loginUser = null;
		if (subject != null && subject.getPrincipals() != null) {
			loginUser = (SysUser) subject.getPrincipals().getPrimaryPrincipal();
		}
		return loginUser;
	}

	/**
	 * 保存登录用户的信息
	 * 
	 * @param mResult
	 */
	public static void setLoginUserInfo(HttpServletRequest request, Object user) {
		HttpSession mHs = request.getSession();
		if (user != null) {
			mHs.setAttribute(mSessionLoginUser, user);
		} else {
			mHs.setAttribute(mSessionLoginUser, null);
		}
	}

	/**
	 * 文件流处理
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream inputStream) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, len);

		}
		return out.toByteArray();

	}

	public static String getLocalIpAddr() {

		String clientIP = null;
		Enumeration<NetworkInterface> networks = null;
		try {
			// 获取所有网卡设备
			networks = NetworkInterface.getNetworkInterfaces();
			if (networks == null) {
				return null;
			}
		} catch (SocketException e) {
			System.out.println(e.getMessage());
		}
		InetAddress ip;
		Enumeration<InetAddress> addrs;
		// 遍历网卡设备
		while (networks.hasMoreElements()) {
			NetworkInterface ni = networks.nextElement();
			try {
				// 过滤掉 loopback设备、虚拟网卡
				if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
					continue;
				}
			} catch (SocketException e) {
				e.getMessage();
			}
			addrs = ni.getInetAddresses();
			if (addrs == null) {
				continue;
			}
			// 遍历InetAddress信息
			while (addrs.hasMoreElements()) {
				ip = addrs.nextElement();
				if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && ip.getHostAddress().indexOf(":") == -1) {
					try {
						clientIP = ip.toString().split("/")[1];
					} catch (ArrayIndexOutOfBoundsException e) {
						clientIP = null;
					}
				}
			}
		}
		return clientIP;
	}
	
	public static String httpURLPOSTCase(String methodUrl,String body) {
		HttpURLConnection connection = null;
		OutputStream dataout = null;
		BufferedReader reader = null;
		String line = null;
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(methodUrl);
			connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
			connection.setDoOutput(true);// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false
			connection.setDoInput(true); // 设置是否从connection读入，默认情况下是true;
			connection.setRequestMethod("POST");// 设置请求方式为post,默认GET请求
			connection.setUseCaches(false);// post请求不能使用缓存设为false
			connection.setConnectTimeout(3000);// 连接主机的超时时间
			connection.setReadTimeout(3000);// 从主机读取数据的超时时间
			connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
			connection.setRequestProperty("charset", "utf-8");

			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer 66cb225f1c3ff0ddfdae31rae2b57488aadfb8b5e7");
			//connection.connect();// 建立TCP连接,getOutputStream会隐含的进行connect,所以此处可以不要

			dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数
			dataout.write(body.getBytes());
			dataout.flush();
			dataout.close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
				// 循环读取流
				while ((line = reader.readLine()) != null) {
					result.append(line).append(System.getProperty("line.separator"));//
				}
			}
			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			connection.disconnect();
		}
		return result.toString();
	}
	
	public static List<String> getorgIdList(SysUser loginUser,SysOrgService sysOrgService) {
		List<SysOrg> list = new ArrayList<>();
		if(loginUser.getIssupermanager()) {
			EntityWrapper<SysOrg> ew = new EntityWrapper<>();
			ew.setEntity(new SysOrg());
			ew.orderBy("sort_idx", true);
			list = sysOrgService.selectList(ew);
		}else {
			//根据用户所属机构，查询其可见机构
			String orgId = loginUser.getOrgId();
			
			EntityWrapper<SysOrg> ew = new EntityWrapper<>();
			ew.setEntity(new SysOrg());
			ew.like("org_id_path", orgId);
			ew.orderBy("sort_idx", true);
			
			list = sysOrgService.selectList(ew);
		}
		List<String> orgIdlist=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			orgIdlist.add(list.get(i).getId());
		}
		return orgIdlist;
	}
	
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
		    System.out.println("Local HostAddress: "+addr.getHostAddress());
		      String hostname = addr.getHostName();
		      System.out.println("Local host name: "+hostname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  

	}
}
