package com.ics.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.ics.system.model.SysTeam;
import com.ics.system.model.SysTeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suxiangrong
 * @date 2020/10/18
 */
public interface SysTeamMemberService extends IService<SysTeamMember> {

    boolean addSysTeamMember(String userIds,String teamId);

    List<SysTeamMember> selectTeamMemberList(EntityWrapper<SysTeamMember> wrapper);
}
