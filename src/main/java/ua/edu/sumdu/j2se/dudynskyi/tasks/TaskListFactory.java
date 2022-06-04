package ua.edu.sumdu.j2se.dudynskyi.tasks;

public class TaskListFactory {

    public static AbstractTaskList createTaskList(ListTypes.types type) {
        if (type == null) {
            throw new NullPointerException();
        }
        if (ListTypes.types.ARRAY.equals(type)) {
            return new ArrayTaskList();
        } else if (ListTypes.types.LINKED.equals(type)) {
            return new LinkedTaskList();
        } else {
            throw new IllegalArgumentException(String.valueOf(type));
        }
    }
}
