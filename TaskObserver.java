package com.astronaut.schedule;

public interface TaskObserver {
    void onConflict(Task newTask, Task existingTask);
}
