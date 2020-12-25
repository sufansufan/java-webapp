package com.ics.dataDesources.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.EnterpriseInfoMapper;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.service.EnterpriseInfoService;

/**
 * 企业 服务实现类
 * @author jjz
 *
 * 2019年12月2日
 */
@Service("enterpriseInfoService")
public class EnterpriseInfoServiceImpl extends ServiceImpl<EnterpriseInfoMapper, EnterpriseInfo> implements EnterpriseInfoService {

	@Autowired
	private EnterpriseInfoMapper enterpriseInfoMapper;
	@Override
	public Page<EnterpriseInfo> selectRelationPageList(Page<EnterpriseInfo> page,
			Wrapper<EnterpriseInfo> wrapper) {
		page.setRecords(enterpriseInfoMapper.selectRelationPageList(page, wrapper));
		return page;
	}
	@Override
	public List<EnterpriseInfo> selectRelationList(Wrapper<EnterpriseInfo> wrapper) {
		return enterpriseInfoMapper.selectRelationList(wrapper);
	}
	@Override
	public EnterpriseInfo selectRelationById(String id) {
		return enterpriseInfoMapper.selectRelationById(id);
	}


	
	
}
