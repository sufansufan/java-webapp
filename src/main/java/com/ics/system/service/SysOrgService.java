package com.ics.system.service;

import com.ics.system.model.SysOrg;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
public interface SysOrgService extends IService<SysOrg> {

	/**
	 * 获取所有的子集节点
	 * @param id
	 * @param includeItself 是否包含自身
	 * @return
	 */
	List<String> getAllChildIdList(String id, boolean includeItself);
}
