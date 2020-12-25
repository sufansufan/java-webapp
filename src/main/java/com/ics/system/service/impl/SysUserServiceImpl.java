package com.ics.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.controller.SysUserController;
import com.ics.system.mapper.SysUserMapper;
import com.ics.system.model.SysUser;
import com.ics.system.model.SysUserIdentity;
import com.ics.system.service.SysRoleService;
import com.ics.system.service.SysUserService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.ExcelImportUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.ValidateRegular;
import com.ics.utils.security.SM3;

/**
 * <p>
 * 用户表，用于登录管理平台和终端操作 服务实现类
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysRoleService sysRoleService;
	
	
	@Override
	public List<SysUser> selectListByOrgId(String orgId) {

		EntityWrapper<SysUser> ew = new EntityWrapper<>();
		ew.setEntity(new SysUser());
		List<SysUser> userList = new ArrayList<>();
		if (orgId != null) {
			// 非超管
			ew.like("org_id_path", orgId);
		}
		ew.orderBy("identity_idx", true);
		ew.orderBy("user_code", true);
		userList = sysUserMapper.selectList(ew);

		return userList;
	}

	@Override
	public List<String> selectIdListByOrgId(String orgId) {

		EntityWrapper<SysUser> ew = new EntityWrapper<>();
		ew.setEntity(new SysUser());
		List<String> idList = new ArrayList<>();
		if (orgId != null) {
			// 超管
			ew.like("org_id_path", orgId);
		}
		List<SysUser> userList = sysUserMapper.selectList(ew);
		for (SysUser sysUser : userList) {
			idList.add(sysUser.getId());
		}

		return idList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonResult importList(String orgId, String path, String orgIdPath) {

		JsonResult result = new JsonResult();
		try {
			result = ExcelImportUtil.analy(path, 7);
		} catch (Exception e) {
			result.setFaildMsg("文件解析失败");
			e.printStackTrace();
		}

		if (result.isSuccess()) {
			List<String[]> list = (List<String[]>) result.getData();

			// 获取角色列表 name:id
			HashMap<String, String> roleMap = sysRoleService.getMapAll();

			
			for (int i = 1; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				if (obj != null && obj.length > 0) {

					String errorMsg = modelValid(obj);

					if (errorMsg == null) {

						// [zhangsan, 张三, 男, 15112345678, 普通, 领导,  xxx, 123456]
						SysUser model = new SysUser();

						model.setUserCode(String.valueOf(obj[0]));
						model.setUserName(String.valueOf(obj[1]));

						if (String.valueOf(obj[2]).equals("女")) {
							model.setSex(2);
						} else {
							model.setSex(1);
						}

						model.setOrgId(orgId);

						model.setCellphone(String.valueOf(obj[3]));

						if (roleMap.containsKey(String.valueOf(obj[4]))) {
							model.setRoleId(roleMap.get(String.valueOf(obj[4])));
						}else {
							model.setRoleId("00000000000000000000000000000001");
						}

						
						model.setIdCard(String.valueOf(obj[6]));

						model.setId(CommonUtil.getRandomUUID());
						model.setUserPassword(SM3.hash("123456" + ConstantProperty.userSalt));
						model.setTerminalUserPassword(SM3.hash("123456" + ConstantProperty.userSalt));
						model.setCreateTime(new Date());
						model.setStatus(1);
						
						if (StringUtils.isNotBlank(orgIdPath)) {
							model.setOrgIdPath(orgIdPath.replaceAll(",", ":"));
						}

						Integer flag = sysUserMapper.insert(model);
						if (flag <= 0) {
							throw new IllegalArgumentException((i + 1) + ",数据插入失败");
						}
					} else {
						throw new IllegalArgumentException((i + 1) + "," + errorMsg);
					}

				}
			}

		}

		return result;
	}

	public String modelValid(Object[] obj) {
		String result = null;

		String code = String.valueOf(obj[0]);
		String name = String.valueOf(obj[1]);
		String tel = String.valueOf(obj[3]);
		if (StringUtils.isBlank(code)) {
			result = "用户编号不可为空!";
		} else if (StringUtils.isBlank(name)) {
			result = "用户名称不可为空!";
		} else if (StringUtils.isBlank(String.valueOf(obj[2]))) {
			result = "用户性别不可为空!";
		} else {

			if (StringUtils.isNotBlank(tel)) {
				if (!ValidateRegular.isMobile(tel)) {
					result = "手机号输入错误!";
				}
			}
			if (result == null && StringUtils.isNotBlank(name)) {
				// 特殊字符验证
				if (ValidateRegular.isSpecialChar(name)) {
					result = "用户名称不能包含特殊字符!";
				} else {
					// 长度验证
					if (name.length() > 12) {
						result = "用户名称长度超过12位!";
					}
				}

			}

			if (result == null && StringUtils.isNotBlank(code)) {
				// 特殊字符验证
				if (ValidateRegular.isSpecialChar(code)) {
					result = "用户编号不能包含特殊字符!";
				} else {
					if (code.length() > 12) {
						result = "用户编号长度超过12位!";

					} else {
						// 验证编码唯一性
						boolean flag = SysUserController.isCodeNotExist(null, code);
						if (!flag) {
							result = "用户编号已存在!";
						}
					}
				}
			}

		}
		return result;
	}

	@Override
	public List<SysUser> selectIdentityList(Wrapper<SysUser> wrapper) {
		return sysUserMapper.selectIdentityList(wrapper);
	}

	@Override
	public Page<SysUser> selectRelationList(Page<SysUser> page, Wrapper<SysUser> wrapper) {
		page.setRecords(sysUserMapper.selectRelationList(page, wrapper));
		return page;
	}
	
	@Override
	public SysUser selectRelationById(String id) {
		return sysUserMapper.selectRelationById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteAllDataByBatchIds(List<String> idList) {
		boolean flag = false;
		sysUserMapper.deleteBatchIds(idList);
		try {
			//同步删除其他业务数据
			
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public SysUser selectOrgRelationById(Wrapper<SysUser> wrapper) {
		return sysUserMapper.selectOrgRelationById(wrapper);
	}

	
	@Override
	public SysUser getByUserCode(String userCode) {
		EntityWrapper<SysUser> ew=new EntityWrapper<>();
		ew.setEntity(new SysUser());
		
		SysUser u = new SysUser();
		u.setUserCode(userCode);
		SysUser sysUser = this.sysUserMapper.selectOne(u);
		
		if(sysUser != null && StringUtils.isNotBlank(sysUser.getRoleId())) {
			sysUser.setSysrole(sysRoleService.selectById(sysUser.getRoleId()));
		}
		
		return sysUser;
	}

	@Override
	public Page<SysUser> selectTeamUserByRoleName(Page<SysUser> page, Wrapper<SysUser> wrapper) {
		page.setRecords(sysUserMapper.selectTeamUserByRoleName(page,wrapper));
		return page;
	}

}
