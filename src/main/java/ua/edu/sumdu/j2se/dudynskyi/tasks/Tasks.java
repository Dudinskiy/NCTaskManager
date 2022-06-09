package ua.edu.sumdu.j2se.dudynskyi.tasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Tasks {

    public static Iterable<Task> incoming(
            Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
//        Stream<Task> stream;
//        Iterable<Task> result;
//        if (tasks instanceof AbstractTaskList) {
//            AbstractTaskList tasks1 = (AbstractTaskList) tasks;
//            result = TaskListFactory.createTaskList(tasks1.type);
//            stream = tasks1.getStream();
//
//        } else if (tasks instanceof List<?>) {
//            List<Task> tasks1 = (List<Task>) tasks;
//            stream = tasks1.stream();
//            result = new ArrayList<>();
//        }
////
//        stream.filter(task -> start.isBefore(task.nextTimeAfter(start))/*task.nextTimeAfter(from) > from*/)
//                .filter(task -> end.isAfter(task.nextTimeAfter(end))/*task.nextTimeAfter(to) < to*/)
//                .forEach(result::add);
//        return result;
        Stream<Task> stream = StreamSupport.stream(tasks.spliterator(), false);
        List<Task> list =  stream.filter(task -> start.isBefore(task.nextTimeAfter(start))/*task.nextTimeAfter(from) > from*/)
                .filter(task -> end.isAfter(task.nextTimeAfter(end))/*task.nextTimeAfter(to) < to*/)
                .collect(Collectors.toList());
        return list;
    }


    public static SortedMap<LocalDateTime, Set<Task>> calendar(
            Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> result = new TreeMap<>();

        Iterable<Task> tasks1 = incoming(tasks, start, end);
        for (Task task : tasks1) {
            if (result.isEmpty()) {
                if (task.isRepeated()) {

                }
                Set<Task> set = new HashSet<>();
                set.add(task);
                result.put(task.getTime(), set);
            }
            if (task.isRepeated()) {

            }
            for (Map.Entry<LocalDateTime, Set<Task>> entry : result.entrySet()) {
                if (entry.getKey().equals(task.getTime())) {
                    entry.getValue().add(task);
                }
            }
            Set<Task> set = new HashSet<>();
            set.add(task);
            result.put(task.getTime(), set);
        }

        return result;
    }
}
