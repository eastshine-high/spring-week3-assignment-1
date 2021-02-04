package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskServiceTest {
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();

        //fixtures
        Task task = new Task();
        task.setTitle("original title");

        taskService.createTask(task);
    }

    @Test
    void createTask() {
        int originalSize = taskService.getTasks().size();

        Task task = new Task();
        task.setTitle("test");
        taskService.createTask(task);

        int afterCreationSize = taskService.getTasks().size();

        assertThat(afterCreationSize - originalSize).isEqualTo(1);
    }

    @Test
    void getTasks() {
        assertThat(taskService.getTasks()).hasSize(1);
    }

    @Test
    void getTaskWithValidId() {
        assertThat(taskService.getTask(1L).getTitle()).isEqualTo("original title");
    }

    @Test
    void getTaskWithInvalidId() {
        assertThatThrownBy(() -> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTask() {
        int originalSize = taskService.getTasks().size();

        taskService.deleteTask(1L);
        int afterDeletionSize = taskService.getTasks().size();

        assertThat(originalSize - afterDeletionSize).isEqualTo(1);
    }

    @Test
    void updateTask() {
        assertThat(taskService.getTask(1L).getTitle()).isEqualTo("original title");

        Task task = new Task();
        task.setTitle("updated title");
        taskService.updateTask(1L, task);

        assertThat(taskService.getTask(1L).getTitle()).isEqualTo("updated title");
    }
}
