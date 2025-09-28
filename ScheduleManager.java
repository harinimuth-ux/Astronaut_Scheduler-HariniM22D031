package com.astronaut.schedule;

import java.util.*;

public class ScheduleManager {
    private static ScheduleManager instance;
    private final List<Task> tasks = new ArrayList<>();
    private final List<TaskObserver> observers = new ArrayList<>();

    private ScheduleManager() {}

    public static synchronized ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void addTask(Task task) {
        for (Task existing : tasks) {
            if (isConflict(task, existing)) {
                notifyObservers(task, existing);
                return;
            }
        }
        tasks.add(task);
        System.out.println("Task added successfully. No conflicts.");
    }

    public void removeTask(String description) {
        boolean removed = tasks.removeIf(t -> t.getDescription().equalsIgnoreCase(description));
        if (removed) {
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Error: Task not found.");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        tasks.sort(Comparator.comparing(Task::getStartTime));
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void markTaskCompleted(String description) {
        for (Task task : tasks) {
            if (task.getDescription().equalsIgnoreCase(description)) {
                task.markCompleted();
                System.out.println("Task marked as completed.");
                return;
            }
        }
        System.out.println("Error: Task not found.");
    }

    public void viewByPriority(String priority) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.getPriority().equalsIgnoreCase(priority)) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) System.out.println("No tasks with priority: " + priority);
    }

    public void editTask(String description, String newStart, String newEnd, String newPriority) {
        for (Task task : tasks) {
            if (task.getDescription().equalsIgnoreCase(description)) {
                tasks.remove(task);
                try {
                    Task updated = TaskFactory.createTask(description, newStart, newEnd, newPriority);
                    addTask(updated);
                    System.out.println("Task updated successfully.");
                } catch (Exception e) {
                    System.out.println("Error updating task: " + e.getMessage());
                    tasks.add(task); // rollback
                }
                return;
            }
        }
        System.out.println("Error: Task not found.");
    }

    public void searchTask(String keyword) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) System.out.println("No tasks found matching: " + keyword);
    }

    public void resetSchedule() {
        tasks.clear();
        System.out.println("All tasks cleared. Ready for a new day.");
    }

    public void viewTasksByPriority() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        tasks.sort(Comparator.comparing(Task::getPriority));
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private boolean isConflict(Task t1, Task t2) {
        return !(t1.getEndTime().compareTo(t2.getStartTime()) <= 0 ||
                 t1.getStartTime().compareTo(t2.getEndTime()) >= 0);
    }

    private void notifyObservers(Task newTask, Task existingTask) {
        for (TaskObserver observer : observers) {
            observer.onConflict(newTask, existingTask);
        }
    }
}
