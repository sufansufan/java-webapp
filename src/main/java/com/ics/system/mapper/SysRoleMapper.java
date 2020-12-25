package com.ics.system.mapper;

import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.system.model.SysRole;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
  * 角色表，描述用户具有的角色类型，如领导、管理员，每个角色赋予相应的权限 Mapper 接口
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

	List<SysRole> selectRelationPageList(Page<SysRole> page, @Param("ew") Wrapper<SysRole> wrapper);

	List<SysRole> selectRelationList(@Param("ew") Wrapper<SysRole> wrapper);
	
	SysRole selectRelationById(String id);

	SysRole selectUserRole(@Param("ew") Wrapper<SysRole> wrapper);
}