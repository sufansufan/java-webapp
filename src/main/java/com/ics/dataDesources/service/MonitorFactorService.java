package com.ics.dataDesources.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.MonitorFactor;

import com.baomidou.mybatisplus.service.IService;
import com.ics.dataDesources.model.MonitorFactorTemplate;

import java.util.List;

/**
 * 监测因子 服务类
 * @author jjz
 *
 * 2019年7月22日
 */
public interface MonitorFactorService extends IService<MonitorFactor> {

    Page<MonitorFactor> selectRelationPageList(Page<MonitorFactor> page, Wrapper<MonitorFactor> wrapper);

    List<MonitorFactor> selectRelationList(Wrapper<MonitorFactor> wrapper);
}
