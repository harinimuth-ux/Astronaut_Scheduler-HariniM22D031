package com.astronaut.schedule;

public class TaskFactory {
    public static Task createTask(String description, String startTime, String endTime, String priority) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Task description cannot be empty.");
        }
        if (!isValidTime(startTime) || !isValidTime(endTime)) {
            throw new IllegalArgumentException("Error: Invalid time format. Use HH:MM (24-hour).");
        }
        if (startTime.compareTo(endTime) >= 0) {
            throw new IllegalArgumentException("Error: Start time must be earlier than end time.");
        }
        if (!priority.equalsIgnoreCase("High") &&
            !priority.equalsIgnoreCase("Medium") &&
            !priority.equalsIgnoreCase("Low")) {
            throw new IllegalArgumentException("Error: Priority must be High, Medium, or Low.");
        }
        return new Task(description.trim(), startTime, endTime, capitalize(priority));
    }

    private static boolean isValidTime(String time) {
        return time.matches("([01]\\d|2[0-3]):[0-5]\\d");
    }

    private static String capitalize(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
