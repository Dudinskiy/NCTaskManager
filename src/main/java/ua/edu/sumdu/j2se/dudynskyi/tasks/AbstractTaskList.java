package ua.edu.sumdu.j2se.dudynskyi.tasks;


import java.io.Serializable;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Serializable {
    protected int taskAmount;
    protected ListTypes.types type;
    protected transient int modCount;


    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract Stream<Task> getStream();

    public Task[] toArray() {
        Task[] result = new Task[taskAmount];
        for (int i = 0; i < taskAmount; i++) {
            result[i] = getTask(i);
        }
        return result;
    }
}
