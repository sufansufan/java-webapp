package com.ics.system.service.impl;

import com.ics.system.model.SysLog;
import com.ics.system.mapper.SysLogMapper;
import com.ics.system.service.SysLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author kld
 * @since 2019-08-09
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
	
}
