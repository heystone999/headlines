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

    /**
     * 按照类型和优先级拉取任务
     *
     * @param type
     * @param priority
     * @return
     */
    public Task pull(int type, int priority);

}