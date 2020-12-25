package com.ics.remoteMonitor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.PanelTemplateMapper;
import com.ics.remoteMonitor.model.PanelTemplate;
import com.ics.remoteMonitor.service.PanelTemplateService;
import org.springframework.stereotype.Service;

/**
 * 监测因子标签表 服务实现类
 * @author jjz
 *
 * 2019年7月22日
 */
@Service("panelTemplateService")
public class PanelTemplateServiceImpl extends ServiceImpl<PanelTemplateMapper, PanelTemplate> implements PanelTemplateService {


}
