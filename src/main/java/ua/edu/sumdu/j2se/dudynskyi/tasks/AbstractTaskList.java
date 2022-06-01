package ua.edu.sumdu.j2se.dudynskyi.tasks;

public abstract class AbstractTaskList implements Iterable<Task> {
    protected int taskAmount;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public AbstractTaskList incoming(int from, int to, ListTypes.types type) {
        AbstractTaskList result = TaskListFactory.createTaskList(type);
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) > from
                  && getTask(i).nextTimeAfter(to) < to) {
                result.add(getTask(i));
            }
        }
        return result;
    }
}
