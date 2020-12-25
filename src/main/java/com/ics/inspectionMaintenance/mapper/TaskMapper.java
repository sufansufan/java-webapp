package com.ics.inspectionMaintenance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.inspectionMaintenance.model.ExceptionTaskCalculateModel;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.model.TaskForApp;
import com.ics.inspectionMaintenance.model.WorkOrderOverview;
import org.apache.ibatis.annotations.Param;

import javax.resource.spi.work.Work;
import java.util.List;
import java.util.Map;

public interface TaskMapper extends BaseMapper<Task> {
    List<Task> selectIMTaskByMachineId(Page<Task> page, @Param("ew") Wrapper<Task> wrapper);

    List<Task> selectExceptionTaskByMachineId(Page<Task> page, @Param("ew") Wrapper<Task> wrapper);

    List<Task> selectTaskAllByPage(Page<Task> page, @Param("ew") Wrapper<Task> wrapper);

    List<Task> selectTeamTaskByUserId( @Param("ew") Wrapper<Task> wrapper);

    List<Task> selectTaskIncludeTeamId( @Param("ew") Wrapper<Task> wrapper);

    void updateTaskStatusBySchedule(Map<String, Object> param);

    List<TaskForApp> selectTaskForApp(@Param("ew") Wrapper<TaskForApp> wrapper);

    List<WorkOrderOverview> taskOverviewList(Page<WorkOrderOverview> page, @Param("ew") Wrapper<WorkOrderOverview> wrapper);

    List<ExceptionTaskCalculateModel> getExceptionTaskByImTaskIds(@Param("ew") Wrapper<ExceptionTaskCalculateModel> wrapper);
}
