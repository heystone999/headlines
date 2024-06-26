package com.stone.schedule.service.impl;

import com.stone.model.schedule.dtos.Task;
import com.stone.schedule.ScheduleApplication;
import com.stone.schedule.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
class TaskServiceImplTest {
    @Autowired
    private TaskService taskService;

    @Test
    public void testAddTask() {
        for (int i = 0; i < 5; i++) {
            Task task = new Task();
            task.setTaskType(100 + i);
            task.setPriority(50);
            task.setParameters("task test".getBytes());
            task.setExecuteTime(new Date().getTime() + 5000 * i);

            long taskId = taskService.addTask(task);
        }
    }

    @Test
    public void testCancelTask() {
        taskService.cancelTask(1799613349533634562L);
    }

    @Test
    public void testPoll() {
        Task task = taskService.poll(100, 50);
        System.out.println(task);
    }
}