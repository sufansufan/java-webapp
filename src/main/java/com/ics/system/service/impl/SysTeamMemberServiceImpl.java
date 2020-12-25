package com.ics.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.mapper.SysTeamMapper;
import com.ics.system.mapper.SysTeamMemberMapper;
import com.ics.system.model.SysTeam;
import com.ics.system.model.SysTeamMember;
import com.ics.system.service.SysTeamMemberService;
import com.ics.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author suxiangrong
 * @date 2020/10/18
 */
@Service("sysTeamMemberService")
public class SysTeamMemberServiceImpl extends ServiceImpl<SysTeamMemberMapper, SysTeamMember> implements SysTeamMemberService {
    @Autowired
    private SysTeamMemberMapper sysTeamMemberMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addSysTeamMember(String userIds,String teamId) {
        boolean flag = false;
        List<String> userIdList = new ArrayList<>(Arrays.asList(userIds.split(",")));
        try {
            for (int i = 0; i < userIdList.size(); i++) {
                SysTeamMember sysTeamMember = new SysTeamMember();
                sysTeamMember.setId(CommonUtil.getRandomUUID());
                sysTeamMember.setUserId(userIdList.get(i));
                sysTeamMember.setTeamId(teamId);
                sysTeamMemberMapper.insert(sysTeamMember);
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<SysTeamMember> selectTeamMemberList(EntityWrapper<SysTeamMember> wrapper) {
        return sysTeamMemberMapper.selectTeamMemberList(wrapper);
    }
}
