package com.ics.dataDesources.service;

import com.ics.dataDesources.model.MonitorFactorTemplate;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 监测因子模板 服务类
 * @author jjz
 *
 * 2019年7月22日
 */
public interface MonitorFactorTemplateService extends IService<MonitorFactorTemplate> {

	Page<MonitorFactorTemplate> selectRelationPageList(Page<MonitorFactorTemplate> page, Wrapper<MonitorFactorTemplate> wrapper);
	
	List<MonitorFactorTemplate> selectRelationList(Wrapper<MonitorFactorTemplate> wrapper);
}
