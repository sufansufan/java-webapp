package com.ics.dataDesources.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.dataDesources.model.EnterpriseInfo;

/**
 * 企业 服务类
 * @author jjz
 *
 * 2019年12月2日
 */
public interface EnterpriseInfoService extends IService<EnterpriseInfo> {
	
	Page<EnterpriseInfo> selectRelationPageList(Page<EnterpriseInfo> page, Wrapper<EnterpriseInfo> wrapper);
	
	List<EnterpriseInfo> selectRelationList(Wrapper<EnterpriseInfo> wrapper);
	
	EnterpriseInfo selectRelationById(String id);
}
