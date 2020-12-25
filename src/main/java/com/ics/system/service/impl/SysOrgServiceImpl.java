package com.ics.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.mapper.SysOrgMapper;
import com.ics.system.model.SysOrg;
import com.ics.system.service.SysOrgService;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Service("sysOrgService")
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

	@Autowired
	private SysOrgMapper sysOrgMapper;

	@Override
	public List<String> getAllChildIdList(String id, boolean includeItself) {
		return this.getChildIdList(id, includeItself);
	}

	public List<String> getChildIdList(String orgId, boolean includeItself) {
		List<String> orgIdList = new ArrayList<String>();
		// 获取子机构
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enabledstate", 1);
		params.put("pid", orgId);
		EntityWrapper<SysOrg> ew = new EntityWrapper<>();
		ew.setEntity(new SysOrg());
		ew.eq("parent_id", orgId);

		List<SysOrg> orgList = sysOrgMapper.selectList(ew);
		if (orgList != null && orgList.size() > 0) {
			if(includeItself) {
				orgIdList.add(orgId);
			}
			for (SysOrg org : orgList) {
				orgIdList.addAll(getChildIdList(org.getId(), includeItself));
			}
		} else {
			// 最子集
			orgIdList.add(orgId);
		}
		return orgIdList;
	}
}
