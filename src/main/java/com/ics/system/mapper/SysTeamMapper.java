package com.ics.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ics.system.model.SysTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suxiangrong
 * @date 2020/10/16
 */
public interface SysTeamMapper extends BaseMapper<SysTeam> {
    /**
     * 查询班组
     * @author suxiangrong
     * @date 2020/10/19
     * @param wrapper
     * @return java.util.List<com.ics.system.model.SysTeam>
     */
    List<SysTeam> selectTeamList(@Param("ew")Wrapper<SysTeam> wrapper);

}
