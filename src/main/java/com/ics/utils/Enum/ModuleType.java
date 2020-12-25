package com.ics.utils.Enum;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志类型
 * @author admin
 *
 */
public enum ModuleType {

	Loginout(1,"登录登出"),
	SysOrg(2,"机构管理"),
	SysArea(3,"行政区域管理"),
	SysUser(4,"用户管理"),
	SysProcess(5,"流程管理"),
	SysConfig(6,"配置管理"),
	SysRole(7,"角色管理"),
	SysDictionary(8,"数据字典管理"),
	SysAuthority(9,"菜单管理"),
	
//	
	BaseHydrologySite(20,"水文站点管理"),
	
	BaseNotice(33,"通知公告管理")
	
	;

	private int type;
	
	private String desc;
	
	private ModuleType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	public int getType() {
		return type;
	}
	
	public static Map<Integer, String> getMap(){
		Map<Integer, String> map = new HashMap<>();
		
		String r = "";
		for(ModuleType m : ModuleType.values()) {
			map.put(m.getType(), m.getDesc());
			r += m.getType()+":"+m.getDesc()+";";
		}
		System.out.println(r);
		return map;
	}
	
	public static void main(String[] args) {
		ModuleType.getMap();
	}
}
