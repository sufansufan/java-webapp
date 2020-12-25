package com.ics.utils.mqtt;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.utils.ApplicationUtil;

public class CheckTimeoutThread extends Thread{
	protected static Logger logger = Logger.getLogger(PushCallback.class);
	
	@Override
	public void run(){
		while(true) {
			try {
				logger.info("检查设备是否超时");
				Long currentTime = new Date().getTime();
				Map<String, Long>  LastTimeMap = ClientLastTimeMgr.getInstance().getClientLastTimeMap();
				Iterator<Entry<String, Long>> iter = LastTimeMap.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Long> entry = iter.next();
					Long intervalTime = currentTime - entry.getValue();
					if(intervalTime>1200*1000) {
						String deviceCode = entry.getKey();
						iter.remove();
						logger.info("移除后设备活动时间对象数量："+ClientLastTimeMgr.getInstance().getClientLastTimeSize());
						DeviceInfoService deviceInfoService = ApplicationUtil.getBean("deviceInfoService");
			        	//先把所有终端置为离线
			    		DeviceInfo model = new DeviceInfo();
						model.setNetState(0);
						EntityWrapper<DeviceInfo> ew0 = new EntityWrapper<>();
						ew0.setEntity(new DeviceInfo());
						ew0.andNew();
						ew0.eq("device_code", deviceCode);
						deviceInfoService.update(model, ew0);
					}
				}
							
				sleep(60 * 1000);
			} catch (Exception e) {
				logger.error("[CheckTimeoutThread.run]" + e.getMessage());
			}
		}
	
	}
}
