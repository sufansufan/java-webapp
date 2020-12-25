package com.ics.utils.mqtt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.utils.ApplicationUtil;
import com.ics.remoteMonitor.service.AlarmInfoService;

public class ServletContext implements ServletContextListener{
	protected static Logger logger = Logger.getLogger(ServletContext.class);
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("服务器正在启动,web应用被初始化");
		DeviceInfoService deviceInfoService = ApplicationUtil.getBean("deviceInfoService");
		//先把所有终端置为离线
		DeviceInfo model = new DeviceInfo();
		model.setNetState(0);
		EntityWrapper<DeviceInfo> ew0 = new EntityWrapper<>();
		ew0.setEntity(new DeviceInfo());
		deviceInfoService.update(model, ew0);
		//启动MQTT客户端
		ClientMQTT client = new ClientMQTT();
	  client.start();
	 	//检测终端超时线程
 		CheckTimeoutThread timeoutThread = new CheckTimeoutThread();
 		timeoutThread.start();
		//检测报警是否恢复线程
		CheckAlarmThread alarmThread = new CheckAlarmThread();
		alarmThread.start();
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		 ClientMQTT client = new ClientMQTT();
		 client.stop();
		 logger.info("服务器已经关闭,销毁所有的Servlet和Filter过滤器");
	}
}
