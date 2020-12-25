package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户职级表，描述用户职级信息
 * </p>
 *
 * @author yi
 * @since 2017-11-17
 */
@TableName("sys_user_rank")
public class SysUserRank extends Model<SysUserRank> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 职级名称，如局长、总队长、中队长，大队长
     */
	@TableField("rank_name")
	private String rankName;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("modify_time")
	private Date modifyTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysUserRank{" +
			", id=" + id +
			", rankName=" + rankName +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
