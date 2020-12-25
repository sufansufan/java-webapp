package com.ics.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ics.system.model.SysTeam;
import com.ics.system.model.SysTeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suxiangrong
 * @date 2020/10/17
 */
public interface SysTeamMemberMapper extends BaseMapper<SysTeamMember> {
    /**
     * 查询班组下组员
     * @author suxiangrong
     * @date 2020/10/19
     * @param wrapper
     * @return java.util.List<com.ics.system.model.SysTeamMember>
     */
    List<SysTeamMember> selectTeamMemberList(@Param("ew") Wrapper<SysTeamMember> wrapper);
}
