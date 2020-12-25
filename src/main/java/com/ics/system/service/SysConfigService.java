package com.ics.system.service;

import com.ics.system.model.SysConfig;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author yi
 * @since 2017-11-28
 */
public interface SysConfigService extends IService<SysConfig> {

	SysConfig selectByKey(String string);
	
	String selectValueByKey(String key);
	
}
