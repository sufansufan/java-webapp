package com.ics.system.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.system.model.SysUser;
import com.ics.utils.JsonResult;

/**
 * <p>
 * 用户表，用于登录管理平台和终端操作 服务类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
public interface SysUserService extends IService<SysUser> {
	
	/**
	 * 根据分组id获取该分组下的所有用户列表（分组id为null时获取所有用户），并根据级别、用户编码升序排序
	 * @param orgId
	 * @return
	 */
	List<SysUser> selectListByOrgId(String orgId);
	
	List<String> selectIdListByOrgId(String orgId);

	JsonResult importList(String orgId, String path, String orgIdPath);
	
	List<SysUser> selectIdentityList(Wrapper<SysUser> wrapper);
	
	Page<SysUser> selectRelationList(Page<SysUser> page, Wrapper<SysUser> wrapper);

	SysUser selectRelationById(String id);

	boolean deleteAllDataByBatchIds(List<String> idList);

	SysUser selectOrgRelationById(Wrapper<SysUser> wrapper);
	
	SysUser getByUserCode(String userCode);

	Page<SysUser> selectTeamUserByRoleName(Page<SysUser> page, Wrapper<SysUser> wrapper);

}
