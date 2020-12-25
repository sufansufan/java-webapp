package com.ics.inspectionMaintenance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.inspectionMaintenance.mapper.ExceptionFixMapper;
import com.ics.inspectionMaintenance.mapper.ExceptionMapper;
import com.ics.inspectionMaintenance.model.Exception;
import com.ics.inspectionMaintenance.model.*;
import com.ics.inspectionMaintenance.service.*;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("exceptionFixService")
public class ExceptionFixServiceImpl extends ServiceImpl<ExceptionFixMapper, ExceptionFix> implements ExceptionFixService {
}
