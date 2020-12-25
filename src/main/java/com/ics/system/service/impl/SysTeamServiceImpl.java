package com.ics.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.mapper.SysTeamMapper;
import com.ics.system.mapper.SysTeamMemberMapper;
import com.ics.system.model.SysTeam;
import com.ics.system.model.SysTeamMember;
import com.ics.system.service.SysTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author suxiangrong
 * @date 2020/10/16
 */
@Service("sysTeamService")
public class SysTeamServiceImpl extends ServiceImpl<SysTeamMapper, SysTeam> implements SysTeamService {

    @Autowired
    private SysTeamMapper sysTeamMapper;
    @Autowired
    private SysTeamMemberMapper sysTeamMemberMapper;

    @Override
    public List<SysTeam> selectTeamList(Wrapper<SysTeam> wrapper) {
        return sysTeamMapper.selectTeamList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeamAndMember(String id) {
        boolean flag = false;
        //删除班组
        this.sysTeamMapper.deleteById(id);
        //删除组员
        EntityWrapper<SysTeamMember> wr = new EntityWrapper<>();
        wr.setEntity(new SysTeamMember());
        wr.eq("team_id", id);
        int count = sysTeamMemberMapper.selectCount(wr);
        if (count != 0) {
            flag = false;
            throw new RuntimeException("班组下有成员，不能删除班组");
        }
    }
}
