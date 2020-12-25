package com.ics.utils.mqtt;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import com.ics.utils.PropertiesUtils;

/**
 * Created by StoneGeek on 2018/6/5.
 * 博客地址：http://www.cnblogs.com/sxkgeek
 */
public class ClientMQTT {
	protected static Logger logger = Logger.getLogger(PushCallback.class);

  private static final String clientid = UUID.randomUUID().toString().replace("-", "");
  private MqttClient client;
  private MqttConnectOptions options;

    private ScheduledExecutorService scheduler;

    public static final int[] Qos = {1};
		private String topicName;

    public ClientMQTT() {
    	 // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
        try {
					String host = PropertiesUtils.getPara("mqtt_host", true);
					String passWord = PropertiesUtils.getPara("mqtt_pass", true);
					String userName = PropertiesUtils.getPara("mqtt_user", true);
					this.topicName = PropertiesUtils.getPara("mqtt_topic", true);

					client = new MqttClient(host, clientid, new MemoryPersistence());
					// MQTT的连接设置
	        options = new MqttConnectOptions();
	        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
	        options.setCleanSession(true);
	        // 设置连接的用户名
	        options.setUserName(userName);
	        // 设置连接的密码
	        options.setPassword(passWord.toCharArray());
	        // 设置超时时间 单位为秒
	        options.setConnectionTimeout(10);
	        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
	        options.setKeepAliveInterval(20);
	        // 设置回调
	        client.setCallback(new PushCallback());
	        MqttTopic topic = client.getTopic(topicName);
	        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
	        options.setWill(topic, "close".getBytes(), 2, true);

//	        //订阅消息
//            int[] Qos  = {1};
//            String[] topic1 = {TOPIC};
//            client.connect(options);
//            client.subscribe(topic1, Qos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }


    public void start() {
        try {
            while (true) {
                try {
                    //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
                    if (!client.isConnected()) {
                    	logger.info("***** client to connect *****");
                        client.connect(options);
                    }
                    if (client.isConnected()) {//连接成功，跳出连接
                    	logger.info("***** connect success *****"+clientid);
                        break;
                    }
                } catch (MqttException e1) {
                    e1.printStackTrace();
                    logger.info("***** 连接异常1 *****"+e1.getMessage());
                }
                Thread.sleep(20000L);
            }
            logger.info("***** 订阅消息 *****");
            //订阅消息
						String[] topic1 = {topicName};

            client.subscribe(topic1, Qos);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("***** 连接异常2 *****"+e.getMessage());
        }
    }

	public void stop() {
        try {

            // 断开连接
            client.disconnect();
            // 关闭客户端
            client.close();
            logger.info("***** 断开连接 *****"+clientid);
        } catch (MqttException e) {
            e.printStackTrace();
            logger.info("***** 断开连接异常 *****"+e.getMessage());
        }
    }

    public static void main(String[] args) throws MqttException {
//        ClientMQTT client = new ClientMQTT();
//        client.start();
  	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
  	  System.out.println(sdf.format(1586437200000L));
    }
}
