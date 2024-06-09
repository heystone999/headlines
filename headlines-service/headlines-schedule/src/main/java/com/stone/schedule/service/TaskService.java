package com.stone.schedule.service;

import com.stone.model.schedule.dtos.Task;

public interface TaskService {
    /**
     * 添加延迟任务
     *
     * @param task
     * @return
     */
    public long addTask(Task task);

    /**
     * 取消任务
     *
     * @param taskId
     * @return
     */
    public boolean cancelTask(long taskId);
}
