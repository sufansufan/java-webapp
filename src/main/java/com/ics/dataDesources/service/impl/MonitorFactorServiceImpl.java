package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.mapper.MonitorFactorTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.MonitorFactorMapper;
import com.ics.dataDesources.model.MonitorFactor;
import com.ics.dataDesources.service.MonitorFactorService;

import java.util.List;

/**
 * 监测因子表 服务实现类
 * @author jjz
 *
 * 2019年7月22日
 */
@Service("monitorFactorService")
public class MonitorFactorServiceImpl extends ServiceImpl<MonitorFactorMapper, MonitorFactor> implements MonitorFactorService {

    @Autowired
    private MonitorFactorMapper monitorFactorMapper;
    @Override
    public Page<MonitorFactor> selectRelationPageList(Page<MonitorFactor> page, Wrapper<MonitorFactor> wrapper) {
        page.setRecords(monitorFactorMapper.selectRelationPageList(page, wrapper));
        return page;
    }

    @Override
    public List<MonitorFactor> selectRelationList(Wrapper<MonitorFactor> wrapper) {
        return monitorFactorMapper.selectRelationList(wrapper);
    }
}
