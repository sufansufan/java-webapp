package com.ics.system.service.impl;

import com.ics.system.model.SysRole;
import com.ics.system.mapper.SysRoleMapper;
import com.ics.system.service.SysRoleService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表，描述用户具有的角色类型，如领导、管理员，每个角色赋予相应的权限 服务实现类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public HashMap<String, String> getMapAll() {
		
		HashMap<String, String> map = new HashMap<>();
		
		List<SysRole> list = sysRoleMapper.selectList(null);
		if(list != null && list.size()>0) {
			for (SysRole sysRole : list) {
				map.put(sysRole.getRoleName(), sysRole.getId());
			}
		}
		
		return map;
	}

	@Override
	public Page<SysRole> selectRelationPageList(Page<SysRole> page, Wrapper<SysRole> wrapper) {
		page.setRecords(sysRoleMapper.selectRelationPageList(page, wrapper));
		return page;
	}

	@Override
	public List<SysRole> selectRelationList(Wrapper<SysRole> wrapper) {
		return sysRoleMapper.selectRelationList(wrapper);
	}

	@Override
	public SysRole selectRelationById(String id) {
		return sysRoleMapper.selectRelationById(id);
	}

	@Override
	public SysRole selectUserRole(Wrapper<SysRole> wrapper) {
		return sysRoleMapper.selectUserRole(wrapper);
	}

}
