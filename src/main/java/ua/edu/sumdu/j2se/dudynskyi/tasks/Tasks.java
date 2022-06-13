package ua.edu.sumdu.j2se.dudynskyi.tasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class Tasks {
    public static Iterable<Task> incoming(
            Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        if (tasks instanceof AbstractTaskList) {
            AbstractTaskList tasks1 = (AbstractTaskList) tasks;
            AbstractTaskList result = TaskListFactory.createTaskList(tasks1.type);
            Stream<Task> stream = tasks1.getStream();

            stream.filter(task -> task.nextTimeAfter(start) != null)
                    .filter(task -> task.nextTimeAfter(start).isAfter(start))
                    .filter(task -> task.nextTimeAfter(start).isBefore(end)
                            || task.nextTimeAfter(start).equals(end)).forEach(result::add);
            return result;
        } else {
            List<Task> tasks1 = (List<Task>) tasks;
            Stream<Task> stream = tasks1.stream();
            List<Task> result = new ArrayList<>();

            stream.filter(task -> task.nextTimeAfter(start) != null)
                    .filter(task -> task.nextTimeAfter(start).isAfter(start))
                    .filter(task -> task.nextTimeAfter(start).isBefore(end)
                            || task.nextTimeAfter(start).equals(end)).forEach(result::add);
            return result;
        }
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(
            Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        SortedMap<LocalDateTime, Set<Task>> result = new TreeMap<>();
        Iterable<Task> tasks1 = incoming(tasks, start, end);
        AbstractTaskList singleTasks = new ArrayTaskList();
        AbstractTaskList repeatTasks = new ArrayTaskList();
        Set<LocalDateTime> allDate = new HashSet<>();

        for (Task task : tasks1) {
            if (task.isRepeated()) {
                repeatTasks.add(task);
            } else {
                singleTasks.add(task);
            }
        }

        for (Task task : singleTasks) {
            allDate.add(task.getTime());
        }

        for (Task task : repeatTasks) {
            LocalDateTime time = task.nextTimeAfter(start);
            int interval = task.getRepeatInterval();
            while (time.isBefore(end) || time.equals(end)) {
                if (time.isBefore(task.getEndTime()) || time.equals(task.getEndTime())) {
                    allDate.add(time);
                    time = time.plusSeconds(interval);
                } else {
                    break;
                }
            }
        }

        for (LocalDateTime date : allDate) {
            result.put(date, new HashSet<>());
        }
        addSingleTaskToCalendar(result, singleTasks);
        addRepeatTaskToCalendar(start, end, result, repeatTasks);

        return result;
    }

    private static void addSingleTaskToCalendar(SortedMap<LocalDateTime
            , Set<Task>> result
            , AbstractTaskList singleTasks) {

        for (Task task : singleTasks) {
            for (Map.Entry<LocalDateTime, Set<Task>> entry : result.entrySet()) {
                if (entry.getKey().equals(task.getTime())) {
                    entry.getValue().add(task);
                }
            }
        }
    }

    private static void addRepeatTaskToCalendar(LocalDateTime start
            , LocalDateTime end
            , SortedMap<LocalDateTime
            , Set<Task>> result
            , AbstractTaskList repeatTasks) {

        for (Task task : repeatTasks) {
            LocalDateTime time = task.nextTimeAfter(start);
            int interval = task.getRepeatInterval();
            while (time.isBefore(end) || time.equals(end)) {
                if (time.isBefore(task.getEndTime()) || time.equals(task.getEndTime())) {
                    for (Map.Entry<LocalDateTime, Set<Task>> entry : result.entrySet()) {
                        if (entry.getKey().equals(time)) {
                            entry.getValue().add(task);
                        }
                    }
                    time = time.plusSeconds(interval);
                } else {
                    break;
                }
            }
        }
    }

}
