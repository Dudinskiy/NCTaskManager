package ua.edu.sumdu.j2se.dudynskyi.tasks;


import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {

   protected int taskAmount;
    protected ListTypes.types type;
    protected int modCount;


    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract Stream<Task> getStream();
}
