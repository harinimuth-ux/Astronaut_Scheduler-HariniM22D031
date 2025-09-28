package com.astronaut.schedule;

import java.util.Scanner;

public class AstronautScheduleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduleManager manager = ScheduleManager.getInstance();
        manager.addObserver(new ConflictNotifier());

        while (true) {
            try {
                System.out.println("\n=== Astronaut Daily Schedule ===");
                System.out.println("1. Add Task");
                System.out.println("2. Remove Task");
                System.out.println("3. View All Tasks");
                System.out.println("4. Mark Task Completed");
                System.out.println("5. View Tasks by Priority");
                System.out.println("6. Edit Task");
                System.out.println("7. Search Task by Keyword");
                System.out.println("8. View Tasks Sorted by Priority");
                System.out.println("9. Reset Schedule (New Day)");
                System.out.println("10. Exit");
                System.out.print("Choose an option: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine();
                    continue;
                }

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter start time (HH:MM): ");
                        String start = sc.nextLine();
                        System.out.print("Enter end time (HH:MM): ");
                        String end = sc.nextLine();
                        System.out.print("Enter priority (High/Medium/Low): ");
                        String priority = sc.nextLine();
                        Task task = TaskFactory.createTask(desc, start, end, priority);
                        manager.addTask(task);
                        break;
                    case 2:
                        System.out.print("Enter task description to remove: ");
                        String removeDesc = sc.nextLine();
                        manager.removeTask(removeDesc);
                        break;
                    case 3:
                        manager.viewTasks();
                        break;
                    case 4:
                        System.out.print("Enter task description to mark completed: ");
                        String compDesc = sc.nextLine();
                        manager.markTaskCompleted(compDesc);
                        break;
                    case 5:
                        System.out.print("Enter priority to filter (High/Medium/Low): ");
                        String pr = sc.nextLine();
                        manager.viewByPriority(pr);
                        break;
                    case 6:
                        System.out.print("Enter description of task to edit: ");
                        String editDesc = sc.nextLine();
                        System.out.print("Enter new start time (HH:MM): ");
                        String newStart = sc.nextLine();
                        System.out.print("Enter new end time (HH:MM): ");
                        String newEnd = sc.nextLine();
                        System.out.print("Enter new priority (High/Medium/Low): ");
                        String newPr = sc.nextLine();
                        manager.editTask(editDesc, newStart, newEnd, newPr);
                        break;
                    case 7:
                        System.out.print("Enter keyword to search: ");
                        String keyword = sc.nextLine();
                        manager.searchTask(keyword);
                        break;
                    case 8:
                        manager.viewTasksByPriority();
                        break;
                    case 9:
                        manager.resetSchedule();
                        break;
                    case 10:
                        System.out.println("Exiting... Goodbye!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
