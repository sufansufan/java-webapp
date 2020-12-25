package com.ics.inspectionMaintenance.service;

import com.baomidou.mybatisplus.service.IService;
import com.ics.inspectionMaintenance.model.Exception;
import com.ics.inspectionMaintenance.model.ExceptionDetail;

import java.util.List;


public interface ExceptionService extends IService<Exception> {

    List<Exception> getExceptionList();

    ExceptionDetail getExceptionDetail(String taskId);
}
