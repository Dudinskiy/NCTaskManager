package ua.edu.sumdu.j2se.dudynskyi.tasks;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Cloneable {

    private static final long serialVersionUID = 21L;
    private Task[] taskList;
    private static final int DEFAULT_CAPACITY = 10;


    /**
     * Данный конструктор создает список задач с размером по умолчанию 10.
     */
    public ArrayTaskList() {
        super();
        taskList = new Task[DEFAULT_CAPACITY];
        type = ListTypes.types.ARRAY;
    }

    /**
     * Данный конструктор создает список задач требуемого размера.
     *
     * @param size размер списка.
     */
    public ArrayTaskList(int size) {
        taskList = new Task[size];
        type = ListTypes.types.ARRAY;
    }

    /**
     * Метод добавляет задачу в список. В случае переполнения список автоматически расширяется.
     *
     * @param task задача равная null не будет добавлена в список.
     */
    public void add(Task task) {
        if (task != null) {
            if (taskAmount == taskList.length) {
                Task[] newTasksList = new Task[taskList.length + 10];
                System.arraycopy(taskList, 0, newTasksList, 0, size());
                taskList = newTasksList;
            }
            taskList[taskAmount] = task;
            taskAmount++;
            modCount++;
        }
    }

    /**
     * Метод удаляет задачу из списка.
     *
     * @param task задача не должна быть null.
     * @return если задача равна null или отсутствует в списке, возвращается false.
     */
    public boolean remove(Task task) {
        if (task != null) {
            for (int i = 0; i < size(); i++) {
                if (getTask(i).equals(task)) {
                    System.arraycopy(taskList, (i + 1), taskList, i, size() - (i + 1));
                    taskList[taskAmount - 1] = null;
                    taskAmount--;
                    modCount++;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод возвращает количество задач в списке.
     *
     * @return количество задач.
     */
    public int size() {
        return taskAmount;
    }

    /**
     * Метод возвращает задачу по заданному индексу.
     *
     * @param index значение должно быть больше или равно 0 и меньше количества задач.
     * @return если значение индекса равно или больше количества задач, возвращается null.
     */
    public Task getTask(int index) {
        if (index > taskList.length - 1) {
            throw new IndexOutOfBoundsException();
        }
        return taskList[index];
    }

    @Override
    public Stream<Task> getStream() {
        return Stream.of(toArray());
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iter();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(taskList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayTaskList)) {
            return false;
        }
        ArrayTaskList taskList1 = (ArrayTaskList) o;
        return Arrays.equals(taskList, taskList1.taskList);
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList) super.clone();
        clone.taskList = taskList.clone();
        for (int i = 0; i < size(); i++) {
            Task taskClone = taskList[i].clone();
            clone.taskList[i] = taskClone;
        }
        clone.modCount = 0;
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ArrayTaskList{");
        if (taskAmount != 0) {
            sb.append("taskAmount=").append(taskAmount).append(",\n");
            for (int i = 0; i < taskAmount; i++) {
                if (i != taskAmount - 1) {
                    sb.append(taskList[i].toString()).append(",").append("\n");
                } else {
                    sb.append(taskList[i].toString()).append("}");
                }
            }
        } else {
            sb.append("taskAmount=").append(taskAmount).append("}");
        }
        return sb.toString();
    }

    private class Iter implements Iterator<Task> {

        int nextForReturn;
        int lastReturned = -1;
        int expectModCount = modCount;

        @Override
        public boolean hasNext() {
            return nextForReturn < taskAmount;
        }

        @Override
        public Task next() {
            if (nextForReturn >= taskAmount) {
                throw new NoSuchElementException();
            }
            if (expectModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            lastReturned = nextForReturn;
            nextForReturn++;
            return taskList[lastReturned];
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }
            Task task = taskList[lastReturned];
            ArrayTaskList.this.remove(task);
            nextForReturn--;
            expectModCount = modCount;
        }
    }
}
