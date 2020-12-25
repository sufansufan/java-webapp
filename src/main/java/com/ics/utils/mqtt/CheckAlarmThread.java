package com.ics.utils.mqtt;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.remoteMonitor.model.AlarmInfo;
import com.ics.remoteMonitor.service.AlarmInfoService;
import com.ics.utils.ApplicationUtil;

public class CheckAlarmThread extends Thread{
	protected static Logger logger = Logger.getLogger(CheckAlarmThread.class);

	@Override
	public void run(){
		AlarmInfoService alarmInfoService = ApplicationUtil.getBean("alarmInfoService");

		while(true) {
			try {
				EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
				ew.setEntity(new AlarmInfo());
				ew.andNew();
				ew.isNull("recovery_time");
				ew.and();
				ew.le("a.create_time", "DATE_SUB(NOW(), INTERVAL 30 MINUTE)");
				ew.and();
				ew.eq("alarm_type", 1);
				List<AlarmInfo> alarmsList = alarmInfoService.selectRelationList(ew);
				for (AlarmInfo alarm : alarmsList) {
					// 需要发送通知给上一级用户。
					System.out.println("++++++++");
				}
				// 两分钟检查一次
				sleep(60 * 2000);
			} catch (Exception e) {
				System.out.println("[CheckAlarmThread.run]" + e.getMessage());
				logger.error("[CheckAlarmThread.run]" + e.getMessage());
			}

		}

	}
}
