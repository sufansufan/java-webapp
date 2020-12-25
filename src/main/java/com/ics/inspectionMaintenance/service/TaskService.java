package com.ics.inspectionMaintenance.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.inspectionMaintenance.model.*;
import com.ics.system.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TaskService extends IService<Task> {
    Page<Task> selectIMTaskByMachineId(Page<Task> page, @Param("ew") Wrapper<Task> wrapper);

    Task buildIMTaskDetail(String scheduleId);

    void fillTaskEmptyFields(List<Task> availableTasks);

    Task getFullFieldTask(String taskId);

    Page<Task> selectPage(Page<Task> pager, EntityWrapper<Task> ew);

    TaskOverviewForWebKanban getKanbanOverview(EntityWrapper<Task> ew);

    List<TaskOverviewAboutMachineItem> getKanbanOverviewAboutMachine(EntityWrapper<Task> ew);

    List<KanbanTask> getKanbanTaskList(EntityWrapper<Task> ew, boolean isLeader, SysUser user) ;

    List<TaskOverview> selectToBeCompleted(EntityWrapper<Task> ew, boolean isLeader, SysUser user);

    List<TaskForApp> getTaskListForApp(EntityWrapper<TaskForApp> ew);

    boolean updateTaskStatus(String taskIds, String taskStatus, SysUser user, String rejectDesc);

    Page<WorkOrderOverview> taskOverviewList(Page<WorkOrderOverview> pager, EntityWrapper<WorkOrderOverview> ew);
}
