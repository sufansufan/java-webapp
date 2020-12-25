package com.ics.system.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.ics.system.model.SysTeam;

import java.util.List;

/**
 * @author suxiangrong
 * @date 2020/10/16
 */
public interface SysTeamService extends IService<SysTeam> {

    /**
     * 查询班组
     * @author suxiangrong
     * @date 2020/10/19
     * @param wrapper
     * @return java.util.List<com.ics.system.model.SysTeam>
     */
    List<SysTeam> selectTeamList(Wrapper<SysTeam> wrapper);

    /**
     * 删除班组和组员
     * @author suxiangrong
     * @date 2020/10/19
     * @param id
     * @return boolean
     */
    void deleteTeamAndMember(String id);
}
