package com.ics.dataDesources.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.MonitorFactorTemplateMapper;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import com.ics.dataDesources.service.MonitorFactorTemplateService;

/**
 * 监测因子模板表 服务实现类
 * @author jjz
 *
 * 2019年7月22日
 */
@Service("monitorFactorTemplateService")
public class MonitorFactorTemplateServiceImpl extends ServiceImpl<MonitorFactorTemplateMapper, MonitorFactorTemplate> implements MonitorFactorTemplateService {

	@Autowired
	private MonitorFactorTemplateMapper monitorFactorTemplateMapper;
	
	@Override
	public Page<MonitorFactorTemplate> selectRelationPageList(Page<MonitorFactorTemplate> page, Wrapper<MonitorFactorTemplate> wrapper) {
		page.setRecords(monitorFactorTemplateMapper.selectRelationPageList(page, wrapper));
		return page;
	}

	@Override
	public List<MonitorFactorTemplate> selectRelationList(Wrapper<MonitorFactorTemplate> wrapper) {
		return monitorFactorTemplateMapper.selectRelationList(wrapper);
	}

	@Override
	public List<MonitorFactorTemplate> selectList(Wrapper<MonitorFactorTemplate> wrapper){
		return monitorFactorTemplateMapper.selectList(wrapper);
	}
	
}
