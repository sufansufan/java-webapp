package com.ics.shiro;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class UuidSessionIdGenerator  implements SessionIdGenerator {

    public Serializable generateId(Session session) {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
