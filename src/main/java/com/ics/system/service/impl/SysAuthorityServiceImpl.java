package com.ics.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.mapper.SysAuthorityMapper;
import com.ics.system.model.SysAuthority;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysAuthorityService;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Service
public class SysAuthorityServiceImpl extends ServiceImpl<SysAuthorityMapper, SysAuthority> implements SysAuthorityService {

	@Override
	public List<SysAuthority> getListByPidAndUser(String pid, SysUser loginUser) {
		
		List<SysAuthority> list = new ArrayList<>();
		EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
		ew.setEntity(new SysAuthority());
		
		if(StringUtils.isNotBlank(pid)) {
			ew.eq("authority_parent_id", pid);
		}
		ew.eq("authority_type", 1);
		
		ew.orderBy("authority_order", true);
		
		if (!loginUser.getIssupermanager()) {

			String authValue = loginUser.getSysrole().getAuthorityCode();
			if(StringUtils.isNotBlank(authValue)) {
				ew.in("authority_code", authValue.split(","));
			}
			
		}
		
		list = new SysAuthority().selectList(ew);
		
		
		return list;
	}
	
}
