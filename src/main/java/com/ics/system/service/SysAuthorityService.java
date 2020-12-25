package com.ics.system.service;

import com.ics.system.model.SysAuthority;
import com.ics.system.model.SysUser;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
public interface SysAuthorityService extends IService<SysAuthority> {
	
	
	List<SysAuthority> getListByPidAndUser(String pid, SysUser loginUser);
}
