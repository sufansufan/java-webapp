package com.ics.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class MySessionListener implements SessionListener {

	protected static Logger logger = Logger.getLogger(MySessionListener.class);

	@Override
	public void onStart(Session session) {
	}

	@Override
	public void onStop(Session session) {
	}

	@Override
	public void onExpiration(Session session) {
	}

}
