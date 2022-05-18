package ua.edu.sumdu.j2se.dudynskyi.tasks;

public class ArrayTaskList {
    private Task[] taskList;
    private int taskAmount;

    /**
     * Данный конструктор создает список задач с размером по умолчанию 10.
     */
    public ArrayTaskList() {
        taskList = new Task[10];
    }

    /**
     * Данный конструктор создает список задач требуемого размера.
     *
     * @param size размер списка.
     */
    public ArrayTaskList(int size) {
        taskList = new Task[size];
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

    /**
     * Метод возвращает список выполняемых задач в заданном диапазоне времени.
     *
     * @param from начальное время.
     * @param to   конечное время.
     * @return в случае отсутствия выполняемых задач возвращается пустой список.
     */
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList result = new ArrayTaskList();
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(to) < to) {
                result.add(getTask(i));
            }
        }
        return result;
    }
}
