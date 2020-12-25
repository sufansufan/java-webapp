package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 流程节点表
 * </p>
 *
 * @author kld
 * @since 2019-08-07
 */
@TableName("sys_process_node")
public class SysProcessNode extends Model<SysProcessNode> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 流程id
     */
	@TableField("process_id")
	private String processId;
	@TableField("node_code")
	private String nodeCode;
	@TableField("node_name")
	private String nodeName;
    /**
     * 节点用户id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 节点用户编号
     */
	@TableField("user_code")
	private String userCode;
    /**
     * 节点用户名称
     */
	@TableField("user_name")
	private String userName;
    /**
     * 上一节点id，为0则表示起始节点
     */
	@TableField("prev_node_id")
	private String prevNodeId;
    /**
     * 下一节点id，为0则表示结束节点
     */
	@TableField("next_node_id")
	private String nextNodeId;
	private String remark;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrevNodeId() {
		return prevNodeId;
	}

	public void setPrevNodeId(String prevNodeId) {
		this.prevNodeId = prevNodeId;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return "SysProcessNode{" +
			", id=" + id +
			", processId=" + processId +
			", nodeCode=" + nodeCode +
			", nodeName=" + nodeName +
			", userId=" + userId +
			", userCode=" + userCode +
			", userName=" + userName +
			", prevNodeId=" + prevNodeId +
			", nextNodeId=" + nextNodeId +
			", remark=" + remark +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
