package com.ics.system.mapper;

import com.ics.system.model.SysUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
  * 用户表，用于登录管理平台和终端操作 Mapper 接口
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	List<SysUser> selectIdentityList(@Param("ew") Wrapper<SysUser> wrapper);

	List<SysUser> selectRelationList(Page<SysUser> page, @Param("ew") Wrapper<SysUser> wrapper);

	SysUser selectRelationById(String id);
	
	SysUser selectOrgRelationById(@Param("ew") Wrapper<SysUser> wrapper);

	List<SysUser> selectTeamUserByRoleName(Page<SysUser> page,@Param("ew") Wrapper<SysUser> wrapper);

}