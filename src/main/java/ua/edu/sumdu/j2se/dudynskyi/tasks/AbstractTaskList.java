package ua.edu.sumdu.j2se.dudynskyi.tasks;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {
    protected int taskAmount;
    protected ListTypes.types type;
    protected int modCount;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public  abstract Stream<Task> getStream();

//    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) {
//        AbstractTaskList result = TaskListFactory.createTaskList(type);
//        Stream<Task> stream = getStream();
//        stream.filter(task -> task.nextTimeAfter(from).isAfter(from)/*task.nextTimeAfter(from) > from*/)
//                .filter(task -> task.nextTimeAfter(to).isBefore(to)/*task.nextTimeAfter(to) < to*/)
//                .forEach(result::add);
//        return result;
//    }
}
