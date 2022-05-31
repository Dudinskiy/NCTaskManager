package ua.edu.sumdu.j2se.dudynskyi.tasks;

public class LinkedTaskList extends AbstractTaskList {

    private Cell first;
    private Cell last;


    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не должна быть равна null");
        }
        if (first == null) {
            first = new Cell(task, null, null);
            last = first;
        } else {
            Cell previous = last;
            last = new Cell(task, previous, null);
            previous.next = last;
        }
        taskAmount++;
    }

    public boolean remove(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не должна быть равна null");
        }
        Cell currentCell = first;
        Cell previousCell;
        Cell nextCell;
        Task taskFromList;
        for (int i = 0; i < taskAmount; i++) {
            taskFromList = currentCell.task;
            if (taskFromList.equals(task)) {
                if (currentCell.equals(first)) {
                    first = first.next;
                } else if (currentCell.equals(last)) {
                    last = last.previous;
                } else {
                    previousCell = currentCell.previous;
                    nextCell = currentCell.next;
                    previousCell.next = nextCell;
                    nextCell.previous = previousCell;
                }
                taskAmount--;
                return true;
            }
            currentCell = currentCell.next;
        }
        return false;
    }

    public int size() {
        return taskAmount;
    }

    public Task getTask(int index) {
        if (index > size()) {
            throw new IndexOutOfBoundsException();
        }
        Cell currentCell = first;
        Task taskFromList;
        for (int i = 0; i <= index; i++) {
            taskFromList = currentCell.task;
            if (i == index) {
                return taskFromList;
            }
            currentCell = currentCell.next;
        }
        return null;
    }

    private static class Cell {
        Task task;
        Cell previous;
        Cell next;

        private Cell(Task task, Cell previous, Cell next) {
            this.task = task;
            this.previous = previous;
            this.next = next;
        }
    }
}
