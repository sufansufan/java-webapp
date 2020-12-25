package com.ics.utils;

import org.apache.log4j.Logger;

/**
 * 公共的属性常量
 *
 * @author yi
 * @date 2017年11月24日 下午2:33:56
 */
public class ConstantProperty {

	protected static Logger logger = Logger.getLogger(ConstantProperty.class);

	/**
	 * 用户加密盐
	 */
	public final static String userSalt="xm-kld";

	/**
	 * 上传文件夹根目录
	 */
	public final static String upload_url = "upload/";

	public static String share_upload_url = "shareUpload/";
	// public static String this_url = "/var/www/iot_factory/shared/public";
	public static String this_url = "C:/EnvironmentalProtection/";


	/**
	 * 用户照片存放目录
	 */
	public static String sysUser_url = share_upload_url + "sysUser";

	/**
	 * 企业照片存放目录
	 */
	public static String polluteEnterprise_url = share_upload_url + "polluteEnterprise";
	/**
	 * 企业照片存放目录
	 */
	public static String enterpriseInfo_url = share_upload_url + "enterpriseInfo";

	/**
	 * 终端照片存放目录
	 */
	public static String device_url = this_url + "device";

	/**
	 * 设备照片存放目录
	 */
	public static String condensingDevice_url = this_url + "condensingDevice";

	/**
	 * 山洪灾害照片存放目录
	 */
	public static String torrents_url = share_upload_url + "torrents";

	/**
	 * 异常照片存放路径
	 */
	public final static String exception_url = upload_url + "exception";

	/**
	 * 模板目录
	 */
	public final static String tpl_url = "importTpl/";

	/**
	 * 用户导入文件存放目录
	 */
	public final static String import_sysUser_url = upload_url + "import/sysUser";

	/**
	 * 用户导入模板文件路径
	 */
	public final static String tpl_sysUser_url = tpl_url + "sysUserTemplate.xls";

	/**
	 * 待处理状态
	 */
	public final static String TASK_STATUS_1 = "1";

	/**
	 * 待处理状态名称
	 */
	public final static String TASK_STATUS_NAME_1 = "待处理";


	/**
	 * 被驳回状态
	 */
	public final static String TASK_STATUS_2 = "2";

	/**
	 * 被驳回状态名称
	 */
	public final static String TASK_STATUS_NAME_2 = "被驳回";

	/**
	 * 待确认状态
	 */
	public final static String TASK_STATUS_3 = "3";

	/**
	 * 待确认状态名称
	 */
	public final static String TASK_STATUS_NAME_3 = "待确认";

	/**
	 * 待承认状态
	 */
	public final static String TASK_STATUS_4 = "4";

	/**
	 * 待承认状态
	 */
	public final static String TASK_STATUS_NAME_4 = "待承认";

	/**
	 * 已完成状态
	 */
	public final static String TASK_STATUS_5 = "5";

	/**
	 * 已完成状态名称
	 */
	public final static String TASK_STATUS_NAME_5 = "已完成";

	/**
	 * 任务类型-日常点检
	 */
	public final static String TASK_TYPE_DAILY = "D";

	/**
	 * 任务类型-日常点检名称
	 */
	public final static String TASK_TYPE_DAILY_name = "日常点检";

	/**
	 * 任务类型- 定期点检
	 */
	public final static String TASK_TYPE_REGULARLY = "R";

	/**
	 * 任务类型- 定期点检名称
	 */
	public final static String TASK_TYPE_REGULARLY_NAME = "定期点检";

	/**
	 * 任务类型 - 保养
	 */
	public final static String TASK_TYPE_MAINTAIN = "M";

	/**
	 * 任务类型 - 保养名称
	 */
	public final static String TASK_TYPE_MAINTAIN_NAME = "保养";

	/**
	 * 任务类型 - 突发异常
	 */
	public final static String TASK_TYPE_BREAK_OUT_EXCEPTION = "BE";

	/**
	 * 任务类型 - 突发异常名称
	 */
	public final static String TASK_TYPE_BREAK_OUT_EXCEPTION_NAME = "突发异常";

	/**
	 * 任务类型 - 日常点检异常
	 */
	public final static String TASK_TYPE_DAILY_INSPECTION_EXCEPTION = "DE";

	/**
	 * 任务类型 - 定期点检异常
	 */
	public final static String TASK_TYPE_REGULAR_INSPECTION_EXCEPTION = "RE";

	/**
	 * 任务类型 - 保养异常
	 */
	public final static String TASK_TYPE_MAINTAIN_EXCEPTION = "ME";

	/**
	 * 任务类型 - 异常总称
	 */
	public final static String TASK_TYPE_ALL_EXCEPTION = "E";

	/**
	 * 任务类型 - 异常总称
	 */
	public final static String TASK_TYPE_ALL_EXCEPTION_NAME = "异常";

	/**
	 * 可用
	 */
	public final static int IS_AVAILABLE_1 = 1;

	/**
	 * 不可用
	 */
	public final static int IS_AVAILABLE_0 = 0;

	/**
	 * 组长code
	 */
	public final static String TEAM_LEADER_ROLECODE = "998";

	/**
	 * 组员code
	 */
	public final static String TEAM_MAMBER_ROLECODE = "997";

	/**
	 * 部门领导code
	 */
	public final static String DEPARTMENT_LEADER_ROLECODE = "999";

	/**
	 * 今天
	 */
	public final static String TODAY = "today";

	/**
	 * 当月
	 */
	public final static String CURRENT_MONTH = "currentMonth";

	/**
	 * 当季
	 */
	public final static String CURRENT_SEASON = "currentSeason";

	/**
	 * 当年
	 */
	public final static String CURRENT_YEAR = "currentYear";

}
