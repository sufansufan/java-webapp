package com.ics.remoteMonitor.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 实时数据历史表
 * @author jjz
 *
 * 2019年7月23日
 */
@TableName("panel_template")
public class PanelTemplate extends Model<PanelTemplate> {

  private static final long serialVersionUID = 1L;

	private String id;

  @TableField("template_code")
	private String templateCode;

	@TableField("level")
	private Integer level;

	@TableField("layout")
	private String layout;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setDeviceCode(Integer level) {
		this.level = level;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

  @Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
