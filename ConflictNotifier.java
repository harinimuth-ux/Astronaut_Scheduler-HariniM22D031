package com.astronaut.schedule;

public class ConflictNotifier implements TaskObserver {
    @Override
    public void onConflict(Task newTask, Task existingTask) {
        System.out.println("Error: Task \"" + newTask.getDescription() +
                           "\" conflicts with existing task \"" + existingTask.getDescription() + "\".");
    }
}
