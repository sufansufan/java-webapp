package com.ics.system.service.impl;

import com.ics.system.model.SysConfig;
import com.ics.system.mapper.SysConfigMapper;
import com.ics.system.mapper.SysRoleMapper;
import com.ics.system.service.SysConfigService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author yi
 * @since 2017-11-28
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	@Override
	public SysConfig selectByKey(String key) {
		SysConfig config = new SysConfig();
		config.setConfigKey(key);
		config = sysConfigMapper.selectOne(config);
		return config;
	}

	@Override
	public String selectValueByKey(String key) {
		String result = "";
		SysConfig config = new SysConfig();
		config.setConfigKey(key);
		config = sysConfigMapper.selectOne(config);
		
		if(config != null) {
			result = config.getConfigValue();
		}
		
		return result;
	}
	
}
