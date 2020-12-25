package com.ics.system.service;

import com.ics.system.model.SysRole;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 角色表，描述用户具有的角色类型，如领导、管理员，每个角色赋予相应的权限 服务类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
public interface SysRoleService extends IService<SysRole> {
	/**
	 * 获取 名称：id map
	 * @return
	 */
	HashMap<String, String> getMapAll();
	
	Page<SysRole> selectRelationPageList(Page<SysRole> page, Wrapper<SysRole> wrapper);
	
	List<SysRole> selectRelationList(Wrapper<SysRole> wrapper);
	
	SysRole selectRelationById(String id);

	SysRole selectUserRole(Wrapper<SysRole> wrapper);
}
