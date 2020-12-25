package com.ics.utils.mqtt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ClientLastTimeMgr {
	private static ClientLastTimeMgr m_instance = new ClientLastTimeMgr();

	public static ClientLastTimeMgr getInstance() {
		return m_instance;
	}

	/** 终端设备客户端上次活动时间 */
	private Map<String, Long> m_hmDeviceCode2ClientLastTime;

	private ReentrantLock m_lock;

	private ClientLastTimeMgr() {
		m_hmDeviceCode2ClientLastTime = new ConcurrentHashMap<String, Long>();
		m_lock = new ReentrantLock();
	}

	public void addClientLastTime(final String deviceCode, Long lastTime) {
		try {
			m_lock.lock();

			m_hmDeviceCode2ClientLastTime.put(deviceCode, lastTime);
		} finally {
			m_lock.unlock();
		}
	}

	public void removeClientLastTime(String deviceCode) {
		try {
			m_lock.lock();

			m_hmDeviceCode2ClientLastTime.remove(deviceCode);
		} finally {
			m_lock.unlock();
		}
	}

	public Long getClientLastTime(String deviceCode) {
		Long lastTime = null;

		try {
			m_lock.lock();

			lastTime = m_hmDeviceCode2ClientLastTime.get(deviceCode);
		} finally {
			m_lock.unlock();
		}

		return lastTime;
	}
	
	 public Map<String, Long> getClientLastTimeMap() {
	    	return m_hmDeviceCode2ClientLastTime;
	  }
	
	  public int getClientLastTimeSize() {
	    	return m_hmDeviceCode2ClientLastTime.size();
	  }

	/**
	 * 获取当前全部在线客户端的上次活动时间
	 * 
	 * @return
	 */
	public LinkedList<Long> getClientLastTimeList() {
		LinkedList<Long> lstClientLastTime = null;

		try {
			m_lock.lock();
			if (m_hmDeviceCode2ClientLastTime.isEmpty()) {
				return null;
			}

			lstClientLastTime = new LinkedList<Long>();
			Iterator<Map.Entry<String, Long>> iter = m_hmDeviceCode2ClientLastTime.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Long> entry = iter.next();
				Long lastTime = entry.getValue();
				lstClientLastTime.add(lastTime);
			}
		} finally {
			m_lock.unlock();
		}

		return lstClientLastTime;
	}
}
