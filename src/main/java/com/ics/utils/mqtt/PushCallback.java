package com.ics.utils.mqtt;

import com.ics.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.CharUtils;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.remoteMonitor.model.AlarmInfo;
import com.ics.remoteMonitor.model.RtdHistory;
import com.ics.remoteMonitor.service.AlarmInfoService;
import com.ics.remoteMonitor.service.RtdHistoryService;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;


public class PushCallback implements MqttCallback {
	protected static Logger logger = Logger.getLogger(PushCallback.class);
	protected static CacheManager cacheManager = CacheManager.create();

	public void connectionLost(Throwable cause) {
		// 连接丢失后，一般在这里面进行重连
		logger.info("连接断开，10秒之后尝试重连...");
		//先断开在重连
		 ClientMQTT client = new ClientMQTT();
		 client.stop();
		 try {
			Thread.sleep(10000L);
			client.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info("重连异常");
		}

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("deliveryComplete---------" + token.isComplete());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		try {
			RtdHistoryService rtdHistoryService = ApplicationUtil.getBean("rtdHistoryService");
			AlarmInfoService alarmInfoService = ApplicationUtil.getBean("alarmInfoService");
			DeviceInfoService deviceInfoService = ApplicationUtil.getBean("deviceInfoService");
			EnterpriseInfoService enterpriseInfoService = ApplicationUtil.getBean("enterpriseInfoService");
			MonitorFactorTemplateService monitorFactorTemplateService = ApplicationUtil.getBean("monitorFactorTemplateService");
			ControlMachineService controlMachineService = ApplicationUtil.getBean("controlMachineService");

			System.out.println("接口消息内容：" + new String(message.getPayload()));
			// subscribe后得到的消息会执行到这里面
			logger.info("接收消息主题 : " + topic);
			logger.info("接收消息Qos : " + message.getQos());
			logger.info("接收消息内容 : " + new String(message.getPayload()));
			String content = new String(message.getPayload());
//			   String content = "{\n" +
//			    		"\"ts\":1583229000000,\n" +
//			    		"\"id\":\"10000001\",\n" +
//			    		"\"params0\":{\n" +
//			    		"\"deviceNum\":0,\"W15B0\":64,\"W15B1\":128,\"W15B2\":0,\"W15B3\":0,\"W15B4\":0,\"W15B5\":0,\"W15B6\":0,\"W15B7\":0,\"W15B8\":0,\"W15B9\":0,\"W15BA\":0,\"W15BB\":0,\"W15BC\":0,\"W15BD\":0,\"W15BE\":0,\"W15BF\":0,\"W15C0\":0,\"W15C1\":0,\"W15C2\":0,\"W15C3\":0,\"W15C4\":0,\"W15C5\":0,\"W15C6\":0,\"W15C7\":0,\"W15C8\":0,\"W15C9\":0,\"W15CA\":0,\"W15CB\":0,\"W15CC\":0,\"W15CD\":0,\"W15CE\":0,\"W15CF\":0,\"W15D0\":0,\"W15D1\":0,\"W15D2\":0,\"W15D3\":0,\"W15D4\":0,\"W15D5\":0,\"W15D6\":0,\"W15D7\":0,\"W15D8\":0,\"W15D9\":-256,\"W15DA\":255,\"W15DB\":0,\"W15DC\":0,\"W15DD\":0,\"W15DE\":0,\"W15DF\":0,\"W15E0\":0,\"W15E1\":0,\"W15E2\":0,\"W15E3\":0,\"W15E4\":0,\"W15E5\":0,\"W15E6\":0,\"W15E7\":0,\"W15E8\":0,\"W15E9\":0,\"W15EA\":0,\"W15EB\":0,\"W15EC\":0,\"W15ED\":0,\"W15F6\":255,\"W15F7\":254,\"W15F8\":0,\"W15F9\":0,\"W15FA\":0,\"W15FB\":0,\"W15FC\":0,\"W15FD\":0,\"W15FE\":254,\"W15FF\":254,\"W1600\":0,\"W1601\":0,\"W1602\":0,\"W1603\":0,\"W1604\":0,\"W1605\":1,\"W1606\":255,\"W1607\":0,\"W1608\":0,\"W1609\":0,\"W160A\":0,\"W160B\":0,\"W165A\":0,\"W165B\":0,\"W165C\":0,\"W165D\":0,\"W165E\":0,\"W165F\":0,\"W1660\":0,\"W1661\":0,\"W1662\":0,\"W1663\":0,\"W1664\":0,\"W1665\":0,\"W1666\":0,\"W1667\":0,\"W1668\":0,\"W1669\":0,\"W166A\":0,\"W166B\":0,\"W166C\":0,\"W166D\":0,\"W166E\":0,\"W166F\":0,\"W1670\":0,\"W1671\":0,\"W1672\":0,\"W1673\":0,\"W1674\":0,\"W1675\":0,\"W1676\":0,\"W1677\":0,\"W1678\":0,\"W1679\":0,\"W167A\":0,\"W167B\":0,\"W167C\":0,\"W167D\":0,\"W167E\":0,\"W167F\":0,\"W1680\":0,\"W1681\":0,\"W1682\":0,\"W1683\":0,\"W1684\":0,\"W1685\":0,\"W1686\":0,\"W1687\":0,\"W1688\":0,\"W1689\":0,\"W168A\":0,\"W168B\":0,\"W168C\":0,\"W168D\":0,\"W168E\":0,\"W168F\":0,\"W1690\":0,\"W1691\":0,\"W1692\":0,\"W169B\":0,\"W169C\":0,\"W169D\":0,\"W169E\":0,\"W169F\":0,\"W16A0\":0,\"W16A1\":0,\"W16A2\":0,\"W16A3\":0,\"W16A4\":0},\"params1\":{\n" +
//			    		"\"deviceNum\":1,\"W1000\":25600,\"W1001\":100,\"W1002\":0,\"W1003\":0,\"W1004\":0,\"W1005\":0,\"W100A\":0,\"W100B\":0,\"W100C\":0,\"W100D\":0,\"W1010\":512,\"W1011\":2,\"W1012\":0,\"W1013\":0,\"W1014\":0,\"W1015\":0,\"W1016\":0,\"W1017\":0,\"W1018\":0,\"W1019\":0,\"W101A\":0,\"W101B\":0,\"W101C\":0,\"W101D\":0,\"W101E\":0,\"W101F\":0,\"W1020\":-256,\"W1021\":255,\"W1022\":0,\"W1023\":0,\"W1024\":0,\"W1025\":0,\"W1026\":0,\"W1027\":0,\"W1028\":0,\"W1029\":0,\"W102A\":0,\"W1030\":0,\"W1031\":0},\"params2\":{\n" +
//			    		"\"deviceNum\":2,\"W1040\":25600,\"W1041\":100,\"W1042\":0,\"W1043\":0,\"W1044\":0,\"W1045\":0,\"W104A\":0,\"W104B\":0,\"W104C\":0,\"W104D\":0,\"W1050\":-256,\"W1051\":255,\"W1052\":0,\"W1053\":0,\"W1054\":0,\"W1055\":0,\"W1056\":0,\"W1057\":0,\"W1058\":0,\"W1059\":0,\"W105A\":0,\"W105B\":0,\"W105C\":0,\"W105D\":0,\"W105E\":0,\"W105F\":0,\"W1060\":0,\"W1061\":0,\"W1062\":0,\"W1063\":512,\"W1064\":-254,\"W1065\":255,\"W1066\":768,\"W1067\":515,\"W1068\":2,\"W1069\":0,\"W106A\":0,\"W1070\":0,\"W1071\":0},\"params3\":{\n" +
//			    		"\"deviceNum\":3,\"W1080\":25600,\"W1081\":100,\"W1082\":0,\"W1083\":0,\"W1084\":0,\"W1085\":0,\"W108A\":0,\"W108B\":0,\"W108C\":0,\"W108D\":0,\"W1090\":0,\"W1091\":0,\"W1092\":0,\"W1093\":0,\"W1094\":-256,\"W1095\":767,\"W1096\":258,\"W1097\":1,\"W1098\":0,\"W1099\":-512,\"W109A\":254,\"W109B\":0,\"W109C\":0,\"W109D\":0,\"W109E\":0,\"W109F\":0,\"W10A0\":0,\"W10A1\":0,\"W10A2\":0,\"W10A3\":0,\"W10A4\":-768,\"W10A5\":509,\"W10A6\":1025,\"W10A7\":516,\"W10A8\":2,\"W10A9\":0,\"W10AA\":0,\"W10B0\":0,\"W10B1\":0},\"params4\":{\n" +
//			    		"\"deviceNum\":4,\"W10C0\":98,\"W10C1\":0,\"W10C2\":0,\"W10C3\":0,\"W10C4\":0,\"W10C5\":0,\"W10CA\":2,\"W10CB\":0,\"W10CC\":0,\"W10CD\":0,\"W10D0\":255,\"W10D1\":255,\"W10D2\":255,\"W10D3\":255,\"W10D4\":0,\"W10D5\":0,\"W10D6\":0,\"W10D7\":0,\"W10D8\":255,\"W10D9\":0,\"W10DA\":0,\"W10DB\":0,\"W10DC\":0,\"W10DD\":0,\"W10DE\":0,\"W10DF\":0,\"W10E0\":0,\"W10E1\":0,\"W10E2\":0,\"W10E3\":1,\"W10E4\":1,\"W10E5\":2,\"W10E6\":0,\"W10E7\":0,\"W10E8\":0,\"W10E9\":0,\"W10EA\":0,\"W10F0\":1,\"W10F1\":0},\"params5\":{\n" +
//			    		"\"deviceNum\":5,\"W1100\":100,\"W1101\":0,\"W1102\":0,\"W1103\":0,\"W1104\":0,\"W1105\":0,\"W110A\":0,\"W110B\":0,\"W110C\":0,\"W110D\":0,\"W1110\":2,\"W1111\":0,\"W1112\":0,\"W1113\":0,\"W1114\":0,\"W1115\":0,\"W1116\":0,\"W1117\":0,\"W1118\":0,\"W1119\":0,\"W111A\":0,\"W111B\":0,\"W111C\":0,\"W111D\":0,\"W111E\":0,\"W111F\":0,\"W1120\":0,\"W1121\":0,\"W1122\":0,\"W1123\":255,\"W1124\":0,\"W1125\":0,\"W1126\":0,\"W1127\":0,\"W1128\":0,\"W1129\":0,\"W112A\":0,\"W1130\":0,\"W1131\":0},\"params6\":{\n" +
//			    		"\"deviceNum\":6,\"W1140\":25600,\"W1141\":100,\"W1142\":0,\"W1143\":0,\"W1144\":0,\"W1145\":0,\"W114A\":0,\"W114B\":0,\"W114C\":0,\"W114D\":0,\"W1150\":3,\"W1151\":0,\"W1152\":0,\"W1153\":0,\"W1154\":0,\"W1155\":0,\"W1156\":0,\"W1157\":0,\"W1158\":0,\"W1159\":0,\"W115A\":0,\"W115B\":0,\"W115C\":0,\"W115D\":0,\"W115E\":0,\"W115F\":0,\"W1160\":0,\"W1161\":0,\"W1162\":0,\"W1163\":255,\"W1164\":0,\"W1165\":0,\"W1166\":0,\"W1167\":0,\"W1168\":0,\"W1169\":0,\"W116A\":0,\"W1170\":0,\"W1171\":0}\n" +
//			    		"}";
			if(content.equals("close")) {
				return;
			}
			JSONObject jsonObject = JSONObject.parseObject(content);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ts = jsonObject.getString("ts");
			if (StringUtils.isNotBlank(ts)) {
				long tsLong = Long.valueOf(ts);
				Date currentDate = new Date();
				currentDate.setTime(tsLong);
				String currentDateStr = sdf.format(currentDate);
				logger.info("接收消息内容时间 : " + currentDateStr);
				// String currentTime = sdf.format(date);
				String deviceCode = jsonObject.getString("id");
				String sn = jsonObject.getString("sn");// 终端序列ID
				// 添加设备上次活动时间的对象
				ClientLastTimeMgr.getInstance().addClientLastTime(deviceCode, new Date().getTime());
				logger.info("新增后终端活动时间对象数量：" + ClientLastTimeMgr.getInstance().getClientLastTimeSize());

				// 判断是否为路由器状态上报
				if (StringUtils.isNotBlank(sn)) {
					try {
						String ip_addr = jsonObject.getString("ip_addr");// 当前连接IP
						String signal = jsonObject.getString("signal");// 当前信号强度
						String online_time = jsonObject.getString("online_time");// 在线时长
						String start_time = jsonObject.getString("start_time");// 启动时长
						String operators = jsonObject.getString("operators");// 当前运营商
						String lbs_locating = jsonObject.getString("lbs_locating");// LBS定位信息
						String network_type = jsonObject.getString("network_type");// 网络制式
						String firmware_version = jsonObject.getString("firmware_version");// 固件版本号
						String cpu_load = jsonObject.getString("cpu_load");// CUP负荷
						String memory_surplus = jsonObject.getString("memory_surplus");// 内存剩余容量
						String memory_percent = jsonObject.getString("memory_percent");// 内存使用百分比
						String flash_surplus = jsonObject.getString("flash_surplus");// Flash剩余容量
						String flash_percent = jsonObject.getString("flash_percent");// Flash使用百分比

// 		        String[] lbs_locating_arr = lbs_locating.split(",");
// 		        String lar_str = lbs_locating_arr[0].split(":")[1];
// 		        String cellid_str = lbs_locating_arr[1].split(":")[1];
// 						long lac = Long.parseLong(lar_str,  16);
// 						long cellid = Long.parseLong(cellid_str,  16);
// 						//http://api.cellocation.com:81/cell?mcc=460&mnc=0&lac=32909&ci=217932672&output=json
// 						// 解析基站定位
// 						String LBSResult = HttpRequest.sendGet("http://api.cellocation.com:81/cell",
// 								"mcc=460&mnc=0&lac="+lac+"&ci="+cellid+"&output=json");
// 						JSONObject lbs_locating_json = JSONObject.parseObject(LBSResult);
// //						logger.info("解析位置数据 : " + lbs_locating_json);
// 						String lon = lbs_locating_json.getString("lon");
// 						String lat = lbs_locating_json.getString("lat");
						// String address = lbs_locating_json.getString("address");
						// BigDecimal longitude = new BigDecimal(lon);
						// BigDecimal latitude = new BigDecimal(lat);
						DeviceInfo model = new DeviceInfo();
						model.setDeviceCode(deviceCode);
						model.setNetState(1);
						model.setSn(sn);
						model.setIpAddr(ip_addr);
						model.setSignalStrength(signal);
						model.setOnlineTime(online_time);
						model.setStartTime(start_time);
						model.setOperators(operators);
						model.setLbsLocating(lbs_locating);
						model.setNetworkType(network_type);
						model.setFirmwareVersion(firmware_version);
						model.setCpuLoad(cpu_load);
						model.setMemorySurplus(memory_surplus);
						model.setMemoryPercent(memory_percent);
						model.setFlashSurplus(flash_surplus);
						model.setFlashPercent(flash_percent);
						model.setModifyTime(currentDate);
						// model.setLongitude(longitude);
						// model.setLatitude(latitude);
						// model.setAddress(address);
						EntityWrapper<DeviceInfo> ew0 = new EntityWrapper<>();
						ew0.setEntity(new DeviceInfo());
						ew0.andNew();
						ew0.eq("device_code", deviceCode);
						deviceInfoService.update(model, ew0);
						logger.info("终端状态更新成功");
						System.out.println("终端状态更新成功");
					} catch (Exception e) {
						System.out.println("终端状态异常：" + e.getMessage());
						logger.info("终端状态异常：" + e.getMessage());
					}

				} else {
					List<RtdHistory> rtdHistoryList = new ArrayList<RtdHistory>();
					List<AlarmInfo> alarmInfoList = new ArrayList<AlarmInfo>();

					// List<String[]> runtimeList = new ArrayList<String[]>();
					String dynamicId = CommonUtil.getRandomUUID();

					//存储machine_no和factor_code的映射关系，只存储报警和预警类型的监控因子，用于统计总数
					Map<String, Set<String>> map = new HashMap<>();

					Cache cache = cacheManager.getCache("switchCache");

					// 遍历获取1-22号机数据
					for (int i = 1; i < 23; i++) {
						List<String> switchCodeList = new ArrayList<String>();
						List<String> switchValueList = new ArrayList<String>();
						List<String> deviceSwitchCodeList = new ArrayList<String>();
						List<String> deviceSwitchValueList = new ArrayList<String>();
						List<String> earlyAlarmContentList = new ArrayList<String>();
						List<String> alarmContentList = new ArrayList<String>();
						List<String> earlyAlarmContentCodeList = new ArrayList<String>();
						List<String> alarmContentCodeList = new ArrayList<String>();
						List<String> factorCodeList = new ArrayList<String>();
						List<String> factorValueList = new ArrayList<String>();

						String str = "params" + i;
						JSONObject deviceParams = (JSONObject) jsonObject.get(str);
						if (deviceParams != null) {
							String deviceNum = deviceParams.getString("deviceNum");
							Set<String> monitorCodeSet = new HashSet<>();
							//运转时间
							List<String> runtimeList = new ArrayList<String>();

							map.put(deviceNum, monitorCodeSet);
							//设备开关
							EntityWrapper<MonitorFactorTemplate> templateEw5 = new EntityWrapper<>();
							templateEw5.setEntity(new MonitorFactorTemplate());
							templateEw5.andNew();
							templateEw5.eq("a.device_Code", deviceCode);
							templateEw5.eq("b.machine_no", deviceNum);
							templateEw5.eq("a.start_switch", 1);
							List<MonitorFactorTemplate> deviceSwitchList = monitorFactorTemplateService
									.selectList(templateEw5);
							// 开关
							EntityWrapper<MonitorFactorTemplate> templateEw4 = new EntityWrapper<>();
							templateEw4.setEntity(new MonitorFactorTemplate());
							templateEw4.andNew();
							templateEw4.eq("a.device_Code", deviceCode);
							templateEw4.eq("b.machine_no", deviceNum);
							templateEw4.eq("type_id", 4);
							templateEw4.eq("a.start_switch", 0);
							List<MonitorFactorTemplate> switchList = monitorFactorTemplateService
									.selectList(templateEw4);
							// 预警
							EntityWrapper<MonitorFactorTemplate> templateEw = new EntityWrapper<>();
							templateEw.setEntity(new MonitorFactorTemplate());
							templateEw.andNew();
							templateEw.eq("a.device_Code", deviceCode);
							templateEw.eq("b.machine_no", deviceNum);
							templateEw.eq("type_id", 2);
							templateEw.eq("a.start_switch", 0);
							List<MonitorFactorTemplate> earlyAlarmList = monitorFactorTemplateService
									.selectList(templateEw);
							// 报警
							EntityWrapper<MonitorFactorTemplate> templateEw2 = new EntityWrapper<>();
							templateEw2.setEntity(new MonitorFactorTemplate());
							templateEw2.andNew();
							templateEw2.eq("a.device_Code", deviceCode);
							templateEw2.eq("b.machine_no", deviceNum);
							templateEw2.eq("type_id", 3);
							templateEw2.eq("a.start_switch", 0);
							List<MonitorFactorTemplate> alarmList = monitorFactorTemplateService
									.selectList(templateEw2);
							// 数值
							EntityWrapper<MonitorFactorTemplate> templateEw3 = new EntityWrapper<>();
							templateEw3.setEntity(new MonitorFactorTemplate());
							templateEw3.andNew();
							templateEw3.eq("a.device_Code", deviceCode);
							templateEw3.eq("b.machine_no", deviceNum);
							templateEw3.eq("type_id", 1);
							templateEw3.eq("a.start_switch", 0);
							List<MonitorFactorTemplate> valueList = monitorFactorTemplateService
									.selectList(templateEw3);

							for (MonitorFactorTemplate factor : valueList) {
								if (factor.getIsRuntime() == 1) {
									runtimeList.add(factor.getFactorCode());
								}
							}

							//设备开关数值
							handleFactorList(deviceSwitchCodeList, deviceSwitchValueList, deviceSwitchList, deviceParams);
							// DI/O开关数值
							handleFactorList(switchCodeList, switchValueList, switchList, deviceParams);
							// 预警内容
							handleFactorList(earlyAlarmContentCodeList, earlyAlarmContentList, earlyAlarmList, deviceParams);
							// 报警内容
							handleFactorList(alarmContentCodeList, alarmContentList, alarmList, deviceParams);

							// 数值内容
							StringBuilder sb = new StringBuilder();
							for (int j = 0; j < valueList.size(); j++) {
								String tempCode = valueList.get(j).getFactorCode();
								String factorValue = getFactorValue(tempCode, sb, deviceParams);
								if (factorValue != null) {
									factorCodeList.add(tempCode);
									factorValueList.add(factorValue);
									//判断是否需要报警
									if(valueList.get(j).getAlarmState()==1) {
										if(valueList.get(j).getTypeId().equals("1")) {
											double lowerLimit = Double.valueOf(valueList.get(j).getLowerLimit());
											double upperLimit = Double.valueOf(valueList.get(j).getUpperLimit());
											//触发阈值报警
											if(Double.valueOf(factorValue)<lowerLimit || Double.valueOf(factorValue)>upperLimit) {
												AlarmInfo model = new AlarmInfo();
												model.setId(CommonUtil.getRandomUUID());
												model.setDynamicId(dynamicId);
												model.setDeviceCode(deviceCode);
												model.setCondensingDeviceNum(deviceNum);
												model.setAlarmType("2");
												model.setFactorCode(tempCode);
												model.setFactorValue(factorValue);
												model.setAlarmContent(
														Double.valueOf(factorValue)<lowerLimit
																? valueList.get(j).getLowerLimitText()
																: valueList.get(j).getUpperLimitText()
												);
												model.setCreateTime(currentDate);
												alarmInfoList.add(model);
											}
										}

									}
								}
							}

							int runState = 1;
							//遍历设备开关数值
							for (int j = 0; j < deviceSwitchCodeList.size(); j++) {
								RtdHistory model = new RtdHistory();
								model.setId(CommonUtil.getRandomUUID());
//								model.setDynamicId(dynamicId);
								model.setDeviceCode(deviceCode);
								model.setCondensingDeviceNum(deviceNum);
								model.setFactorCode(deviceSwitchCodeList.get(j));
								model.setFactorValue(deviceSwitchValueList.get(j));
								model.setCollectTime(currentDate);
								rtdHistoryList.add(model);
								runState = Integer.valueOf(deviceSwitchValueList.get(j));
							}

							// 遍历开关数值
							for (int j = 0; j < switchCodeList.size(); j++) {
								RtdHistory model = new RtdHistory();
								model.setId(CommonUtil.getRandomUUID());
//								model.setDynamicId(dynamicId);
								model.setDeviceCode(deviceCode);
								model.setCondensingDeviceNum(deviceNum);
								model.setFactorCode(switchCodeList.get(j));
								model.setFactorValue(switchValueList.get(j));
								model.setCollectTime(currentDate);
								rtdHistoryList.add(model);

								if (cache != null) {
									String key = "switch:" + deviceCode + ":" + deviceNum + ":" + switchCodeList.get(j);
									Element ele = new Element(key, switchValueList.get(j));
									cache.put(ele);
								}
							}


							String runtime = "0";
							// String runtime_low = "";
							// String runtime_high = "";
							// 遍历监测因子数值
							for (int j = 0; j < factorCodeList.size(); j++) {
								RtdHistory model = new RtdHistory();
								model.setId(CommonUtil.getRandomUUID());
//								model.setDynamicId(dynamicId);
								model.setDeviceCode(deviceCode);
								model.setCondensingDeviceNum(deviceNum);
								model.setFactorCode(factorCodeList.get(j));
								model.setFactorValue(factorValueList.get(j));
								model.setCollectTime(currentDate);
								rtdHistoryList.add(model);

								if (runtimeList.contains(factorCodeList.get(j))) {
									runtime = factorValueList.get(j);
								}
								//
								// String[] runtimeArr = runtimeList.get(Integer.valueOf(deviceNum)-1);
								// if(factorCodeList.get(j).equals(runtimeArr[0])) {
								// 	runtime_low = factorValueList.get(j);
								// }
								// if(factorCodeList.get(j).equals(runtimeArr[1])) {
								// 	runtime_high = factorValueList.get(j);
								// }
							}

							// 遍历预警内容
							for (int j = 0; j < earlyAlarmContentCodeList.size(); j++) {
								monitorCodeSet.add(earlyAlarmContentCodeList.get(j));
								AlarmInfo model = new AlarmInfo();
								model.setId(CommonUtil.getRandomUUID());
								model.setDynamicId(dynamicId);
								model.setDeviceCode(deviceCode);
								model.setCondensingDeviceNum(deviceNum);
								model.setFactorCode(earlyAlarmContentCodeList.get(j));
								model.setAlarmType("0");
								model.setAlarmContent(earlyAlarmContentList.get(j));
								model.setCreateTime(currentDate);
								alarmInfoList.add(model);
								RtdHistory model1 = new RtdHistory();
								model1.setId(CommonUtil.getRandomUUID());
//								model1.setDynamicId(dynamicId);
								model1.setDeviceCode(deviceCode);
								model1.setCondensingDeviceNum(deviceNum);
								model1.setFactorCode(earlyAlarmContentCodeList.get(j));
								model1.setFactorValue(earlyAlarmContentList.get(j));
								model1.setCollectTime(currentDate);
								rtdHistoryList.add(model1);
							}

							// 遍历报警内容
							for (int j = 0; j < alarmContentCodeList.size(); j++) {
								monitorCodeSet.add(alarmContentCodeList.get(j));
								AlarmInfo model = new AlarmInfo();
								model.setId(CommonUtil.getRandomUUID());
								model.setDynamicId(dynamicId);
								model.setDeviceCode(deviceCode);
								model.setCondensingDeviceNum(deviceNum);
								model.setFactorCode(alarmContentCodeList.get(j));
								model.setAlarmType("1");
								model.setAlarmContent(alarmContentList.get(j));
								model.setCreateTime(currentDate);
								alarmInfoList.add(model);
								RtdHistory model1 = new RtdHistory();
								model1.setId(CommonUtil.getRandomUUID());
//								model1.setDynamicId(dynamicId);
								model1.setDeviceCode(deviceCode);
								model1.setCondensingDeviceNum(deviceNum);
								model1.setFactorCode(alarmContentCodeList.get(j));
								model1.setFactorValue(alarmContentList.get(j));
								model1.setCollectTime(currentDate);
								rtdHistoryList.add(model1);
							}

// 							int runtime =0;
// 							if(!runtime_low.equals("") && !runtime_high.equals("")) {
// //								RUN TIME = 65536*X+Y ;X表示高位，Y表示低位，单位是小时
// //								runtime = 65536*Integer.valueOf(runtime_high)+Integer.valueOf(runtime_low);
// 								 String runtime_high_bit = Integer.toBinaryString(Integer.valueOf(runtime_high));
// 						    	 if(runtime_high_bit.length()<32) {
// 						    		 runtime_high_bit = String.format("%32s", runtime_high_bit).replace(' ', '0');
// 						    	 }
// 						    	 String runtime_low_bit = Integer.toBinaryString(Integer.valueOf(runtime_low));
// 						    	 if(runtime_low_bit.length()<32) {
// 						    		 runtime_low_bit = String.format("%32s", runtime_low_bit).replace(' ', '0');
// 						    	 }
// 						    	 String runtime_bit = runtime_high_bit + runtime_low_bit;
// 						    	 runtime = Integer.parseInt(runtime_bit,2);
// 							}
							int alarmState=0;
							if(earlyAlarmContentList.size()>0) {
								alarmState=1;
							}
							if(alarmContentList.size()>0) {
								alarmState=2;
							}
							//更新机组信息
							ControlMachine model = new ControlMachine();
							model.setRunningStatus(runState);
							model.setAlarmStatus(alarmState);
							model.setPowerStatus(runState);
							model.setModifyTime(currentDate);
							Integer runtimeI = Integer.parseInt(runtime);
							if (runtimeI > 0) {
								model.setRuntime(runtimeI);
							}
							EntityWrapper<ControlMachine> ew0 = new EntityWrapper<>();
							ew0.setEntity(new ControlMachine());
							ew0.andNew();
							ew0.eq("device_code", deviceCode);
							ew0.and();
							ew0.eq("machine_no", deviceNum);
							controlMachineService.update(model, ew0);
						}

					} // for循环end
					String oldDynamicId = "";
					if (rtdHistoryList.size() > 0) {
						rtdHistoryService.insertBatch(rtdHistoryList);
						logger.info("批量添加历史记录成功");
						DeviceInfo model = new DeviceInfo();
						model.setDynamicId(dynamicId);
						model.setNetState(1);
						model.setModifyTime(currentDate);
						EntityWrapper<DeviceInfo> ew0 = new EntityWrapper<>();
						ew0.setEntity(new DeviceInfo());
						ew0.andNew();
						ew0.eq("device_code", deviceCode);
						List<DeviceInfo> deviceInfoList = deviceInfoService.selectList(ew0);
						oldDynamicId = deviceInfoList.get(0).getDynamicId();
						deviceInfoService.update(model, ew0);
						logger.info("终端状态更新成功");
					}
					if (alarmInfoList.size() > 0) {
						EntityWrapper<AlarmInfo> alarmInfoEw = new EntityWrapper<>();
						alarmInfoEw.setEntity(new AlarmInfo());
						alarmInfoEw.andNew();
						alarmInfoEw.eq("device_code", deviceCode);
						alarmInfoEw.eq("dynamic_id", oldDynamicId);
						List<AlarmInfo> oldAlarmInfoList = alarmInfoService.selectList(alarmInfoEw);
						List<String> tempAlarmInfoList = new ArrayList<String>();
						List<String> tempOldAlarmInfoList = new ArrayList<String>();
						for(int m=0;m<alarmInfoList.size();m++) {
							tempAlarmInfoList.add(alarmInfoList.get(m).getFactorCode());
						}
						for(int n=0;n<oldAlarmInfoList.size();n++) {
							tempOldAlarmInfoList.add(oldAlarmInfoList.get(n).getFactorCode());
						}
						//去掉tempAlarmInfoList中跟tempOldAlarmInfoList相同的元素,新增的报警内容
						List<String> addList = MqttDataUtil.removeRepeatFactor(tempOldAlarmInfoList, tempAlarmInfoList);
						//去掉tempOldAlarmInfoList中跟tempAlarmInfoList相同的元素,报警恢复内容
						List<String> tempOldAlarmRecoveryInfoList = MqttDataUtil.removeRepeatFactor(tempAlarmInfoList, tempOldAlarmInfoList);

						for(int m=0;m<oldAlarmInfoList.size();m++) {
							if(tempOldAlarmRecoveryInfoList.contains(oldAlarmInfoList.get(m).getFactorCode())) {
								Date oldDate = oldAlarmInfoList.get(m).getCreateTime();
								if(currentDate.getTime()>oldDate.getTime()) {
									AlarmInfo model = new AlarmInfo();
									model.setRecoveryTime(currentDate);
									EntityWrapper<AlarmInfo> ew0 = new EntityWrapper<>();
									ew0.setEntity(new AlarmInfo());
									ew0.andNew();
									ew0.eq("id", oldAlarmInfoList.get(m).getId());
									alarmInfoService.update(model, ew0);
								}else {
									AlarmInfo model = new AlarmInfo();
									model.setDynamicId(dynamicId);
									EntityWrapper<AlarmInfo> ew0 = new EntityWrapper<>();
									ew0.setEntity(new AlarmInfo());
									ew0.andNew();
									ew0.eq("id", oldAlarmInfoList.get(m).getId());
									alarmInfoService.update(model, ew0);
								}

							}

						}


						for(Iterator<AlarmInfo> iterator=alarmInfoList.iterator();iterator.hasNext();){
							AlarmInfo alarmInfo = iterator.next();
							if(!addList.contains(alarmInfo.getFactorCode())) {
								iterator.remove();
							}
					   }

						for(Iterator<AlarmInfo> iterator=oldAlarmInfoList.iterator();iterator.hasNext();){
							AlarmInfo alarmInfo = iterator.next();
							if(tempOldAlarmRecoveryInfoList.contains(alarmInfo.getFactorCode())) {
								iterator.remove();
							}
					   }

					    //本就存在但本次未恢复的警告更新动态Id
						for(int m=0;m<oldAlarmInfoList.size();m++) {
							AlarmInfo model = new AlarmInfo();
							model.setDynamicId(dynamicId);
							EntityWrapper<AlarmInfo> ew0 = new EntityWrapper<>();
							ew0.setEntity(new AlarmInfo());
							ew0.andNew();
							ew0.eq("id", oldAlarmInfoList.get(m).getId());
							alarmInfoService.update(model, ew0);
						}


						if(alarmInfoList.size()>0) {
							// 批量添加报警内容
							alarmInfoService.insertBatch(alarmInfoList);
							logger.info("批量添加报警记录成功");
						}
						logger.info("新增报警数量 : " + alarmInfoList.size());

						//更新control_machine的报警状态
						List<AlarmInfo>  allAlarmInfo = new ArrayList(alarmInfoList);
						allAlarmInfo.addAll(oldAlarmInfoList);
						Set<String> earlyAll = allAlarmInfo.stream()
								.filter(a->a.getAlarmType().equals("0"))
								.map(a->a.getFactorCode())
								.collect(Collectors.toSet());
						Set<String> warnAll = allAlarmInfo.stream()
								.filter(a->a.getAlarmType().equals("1"))
								.map(a->a.getFactorCode())
								.collect(Collectors.toSet());
						map.entrySet().parallelStream().forEach(
								e->{
									String no = e.getKey();
									Set<String> codes = e.getValue();
									int earlyCount = 0;
									int warnCount = 0;
									if(CollectionUtils.isNotEmpty(earlyAll)){
										Set<String> temp = new HashSet<>(codes);
										temp.retainAll(earlyAll);
										earlyCount = temp.size();
									}
									if(CollectionUtils.isNotEmpty(warnAll)){
										Set<String> temp = new HashSet<>(codes);
										temp.retainAll(warnAll);
										if(temp.size() > 0) {
											warnCount = 1;
										}
									}
									//更新机组信息
									ControlMachine model = new ControlMachine();
									model.setFaultStatus(warnCount);
									model.setEarlyAlarmStatus(earlyCount);
									model.setModifyTime(currentDate);
									EntityWrapper<ControlMachine> ew0 = new EntityWrapper<>();
									ew0.setEntity(new ControlMachine());
									ew0.andNew();
									ew0.eq("device_code", deviceCode);
									ew0.eq("machine_no", no);
									controlMachineService.update(model, ew0);
								}
						);

						for (int q = 0; q < alarmInfoList.size(); q++) {
							EntityWrapper<EnterpriseInfo> ew0 = new EntityWrapper<>();
							ew0.setEntity(new EnterpriseInfo());
							ew0.andNew();
							ew0.eq("device_code", deviceCode);
							List<EnterpriseInfo> enterpriseList = enterpriseInfoService.selectList(ew0);
							if(enterpriseList.size()>0) {
								int mailPushState = enterpriseList.get(0).getMailPushState();
								String mailAddress = enterpriseList.get(0).getMailAddress();
								int wechatPushState = enterpriseList.get(0).getWechatPushState();
								String wechatAddress = enterpriseList.get(0).getWechatAddress();
								String agentId = enterpriseList.get(0).getAgentId();
//								logger.info("邮件推送状态 : " + mailPushState);
//								logger.info("邮件地址 : " + mailAddress);
//								logger.info("微信推送状态 : " + wechatPushState);
//								logger.info("企业微信地址 : " + wechatAddress);
//								logger.info("企业微信ID : " + agentId);
								String alarmContentStr = "报警内容";
								if (alarmInfoList.get(q).getAlarmType().equals("0")) {
									alarmContentStr = "预警内容";
								} else {
									alarmContentStr = "报警内容";
								}
								String sendMsg="";
								if(alarmInfoList.get(q).getAlarmType().equals("2")) {
									sendMsg = "企业名称：" + enterpriseList.get(0).getEnterpriseName() + "，机组编号："
											+ alarmInfoList.get(q).getCondensingDeviceNum() + "，监测因子："+alarmInfoList.get(q).getFactorCode()+"，数值："+alarmInfoList.get(q).getFactorValue()+"，" + alarmContentStr + "："
											+ alarmInfoList.get(q).getAlarmContent() + "，上报时间："+currentDateStr;
								}else {
									sendMsg = "企业名称：" + enterpriseList.get(0).getEnterpriseName() + "，机组编号："
											+ alarmInfoList.get(q).getCondensingDeviceNum() + "，" + alarmContentStr + "："
											+ alarmInfoList.get(q).getAlarmContent() + "，上报时间：" + currentDateStr;
								}

								String tempCode = alarmInfoList.get(q).getFactorCode();
								EntityWrapper<MonitorFactorTemplate> templateEw = new EntityWrapper<>();
								templateEw.setEntity(new MonitorFactorTemplate());
								templateEw.andNew();
								templateEw.eq("factor_code", tempCode);
								templateEw.eq("a.device_code", deviceCode);
								List<MonitorFactorTemplate> tempList = monitorFactorTemplateService.selectList(templateEw);
								logger.info("终端："+deviceCode+",该因子（"+tempCode+"）是否推送 : " + tempList.get(0).getAlarmState());
								if(tempList.get(0).getAlarmState()==1) {
									//微信推送
									if(!agentId.equals("")) {
										if (wechatPushState == 1) {
											if (!wechatAddress.equals("")) {
												try {
													wechatAddress = wechatAddress.replace(";", "|");
													 String sendContent = "touser=" + URLEncoder.encode(wechatAddress, "UTF-8" ) + "&appid="+agentId+"&content=" + URLEncoder.encode(sendMsg, "UTF-8" );
														String json = HttpRequest.sendGet("http://deve.docomo-iot.com.cn/wechat_send",sendContent);
//														logger.info("企业微信推送消息 : " + sendMsg);
//														logger.info("企业微信推送消息: " + sendContent);
//														logger.info("企业微信推送结果 : " + json);
												} catch (Exception e) {
													logger.error("微信推送失败：" + e.getMessage());
												}


											}

										}
									}else {
										logger.info("agentId为空");
									}

									//邮件推送
									if(mailPushState==1) {
										if (!mailAddress.equals("")) {
											try {
												if(alarmInfoList.get(q).getAlarmType().equals("2")) {
													sendMsg = "企业名称：" + enterpriseList.get(0).getEnterpriseName() + "，机组编号："
															+ alarmInfoList.get(q).getCondensingDeviceNum() + "，监测因子："+alarmInfoList.get(q).getFactorCode()+",数值："+alarmInfoList.get(q).getFactorValue()+"，" + alarmContentStr + "："
															+ alarmInfoList.get(q).getAlarmContent() + "，上报时间：" + currentDateStr;
												}else {
													sendMsg = "企业名称：" + enterpriseList.get(0).getEnterpriseName() + "，机组编号："
															+ alarmInfoList.get(q).getCondensingDeviceNum() + "，" + alarmContentStr + "："
															+ alarmInfoList.get(q).getAlarmContent() + "，上报时间：" + currentDateStr;
												}
												logger.info("邮件推送消息 : " + sendMsg);
												String mailName = "报警通知";
												if(alarmInfoList.get(q).getAlarmType().equals("0")) {
													mailName = "预警通知";
												}else if(alarmInfoList.get(q).getAlarmType().equals("1")){
													mailName = "报警通知";
												}else if(alarmInfoList.get(q).getAlarmType().equals("2")) {
													mailName = "阈值报警通知";
												}
												MailUtil.sendMail(mailAddress, sendMsg, mailName);
												logger.info("邮件推送成功 ");
											} catch (Exception e) {
												logger.error("邮箱推送失败：" + e.getMessage());
											}
										}
									}
								}
							}

						}

					}else {
						//当前无报警，则修改上一次上报的报警恢复时间
						EntityWrapper<AlarmInfo> alarmInfoEw = new EntityWrapper<>();
						alarmInfoEw.setEntity(new AlarmInfo());
						alarmInfoEw.andNew();
						alarmInfoEw.eq("device_code", deviceCode);
						alarmInfoEw.eq("dynamic_id", oldDynamicId);
						List<AlarmInfo> oldAlarmInfoList = alarmInfoService.selectList(alarmInfoEw);
						for(int n=0;n<oldAlarmInfoList.size();n++) {
							AlarmInfo model = new AlarmInfo();
							model.setRecoveryTime(currentDate);
							EntityWrapper<AlarmInfo> ew0 = new EntityWrapper<>();
							ew0.setEntity(new AlarmInfo());
							ew0.andNew();
							ew0.eq("id", oldAlarmInfoList.get(n).getId());
							alarmInfoService.update(model, ew0);
						}
						map.entrySet().parallelStream().forEach(
								e->{
									String no = e.getKey();
									//更新机组信息
									ControlMachine model = new ControlMachine();
									model.setFaultStatus(0);
									model.setEarlyAlarmStatus(0);
									model.setModifyTime(currentDate);
									EntityWrapper<ControlMachine> ew0 = new EntityWrapper<>();
									ew0.setEntity(new ControlMachine());
									ew0.andNew();
									ew0.eq("device_code", deviceCode);
									ew0.eq("machine_no", no);
									controlMachineService.update(model, ew0);
								}
						);
					}
				}
				System.out.println("mqtt数据解析结束 ，上报时间： "+currentDateStr+",设备编码："+deviceCode);

				logger.info("mqtt数据解析结束 ，上报时间： "+currentDateStr+",设备编码："+deviceCode);
			}

		} catch (Exception e) {
			System.out.println("MQTT异常：" + e.getMessage());
			logger.info("MQTT异常：" + e.getMessage());
		}

	}

	private void handleFactorList(List<String> codeList,
								  List<String> valueList,
								  List<MonitorFactorTemplate> templateList,
								  JSONObject deviceParams){
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < templateList.size(); j++) {
			String tempCode = templateList.get(j).getFactorCode();
			String factorValue = getFactorValue(tempCode, sb, deviceParams);
			if (factorValue != null) {
				codeList.add(tempCode);
				valueList.add(factorValue);
			}
		}
	}

	private String getFactorValue(String tempCode, StringBuilder sb, JSONObject params0) {
		String factorValue;
		if (tempCode.endsWith("L")) {
			int p = findFirstNumPosition(tempCode, 0);
			if(p != -1) {
				int l = tempCode.length();
				if(p > 0) {
					sb.append(tempCode.substring(0, p));
				}
				String numberStr = tempCode.substring(p, l - 1);
				long number = Long.parseLong(numberStr, 16) + 1;
				sb.append(paddingHexStringLeft(number));
				String value1 = params0.getString(tempCode.substring(0, l - 1));
				String value2 = params0.getString(sb.toString());
				if(value1 != null && value2 != null) {
					long num1 = Long.parseLong(value1) << 16;
					long num2 = Long.parseLong(value2);
					long num = num1 + num2;
					factorValue = Long.toString(num);
				}else {
					factorValue = null;
				}
				sb.delete(0, sb.length());
			}
			else {
				factorValue = null;
			}
		} else if (tempCode.contains("_") && !tempCode.endsWith("_")) {
			String[] codes = tempCode.split("_");
			String codeStr = codes[0];
			int pos = Integer.parseInt(codes[1], 16);
			String value = params0.getString(codeStr);
			if(value != null) {
				int number = Integer.parseInt(value);
				String binary = paddingBinaryStringLeft(number);
				factorValue = CharUtils.toString(binary.charAt(binary.length()-1-pos));
			}else {
				factorValue = null;
			}
		} else {
			factorValue = params0.getString(tempCode);
		}
		return factorValue;
	}

	private int findFirstNumPosition(String s, int p){
		if(s.length() <= p - 1){
			return -1;
		}
		char c = s.charAt(p);
		if(c>='0' && c<='9'){
			return p;
		}
		else {
			return findFirstNumPosition(s,++p);
		}
	}

	private String paddingHexStringLeft(long number){
		String pad = "0000";
		String m = Long.toHexString(number).toUpperCase();
		String r = m.length() == pad.length() ? m : pad.substring(0,pad.length()-m.length()) + m;
		return r;
	}

	private String paddingBinaryStringLeft(long number){
		String pad = "0000000000000000";
		String m = Long.toBinaryString(number).toUpperCase();
		String r = m.length() == pad.length() ? m : pad.substring(0,pad.length()-m.length()) + m;
		return r;
	}
}
