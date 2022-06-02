package ua.edu.sumdu.j2se.dudynskyi.tasks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList implements Cloneable {

    private Node first;
    private Node last;


    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не должна быть равна null");
        }
        if (first == null) {
            first = new Node(task, null, null);
            last = first;
        } else {
            Node previous = last;
            last = new Node(task, previous, null);
            previous.next = last;
        }
        taskAmount++;
    }

    public boolean remove(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не должна быть равна null");
        }
        Node currentNode = first;
        Node previousNode;
        Node nextNode;
        Task taskFromList;
        for (int i = 0; i < taskAmount; i++) {
            taskFromList = currentNode.task;
            if (taskFromList.equals(task)) {
                if (currentNode.equals(first)) {
                    first = first.next;
                } else if (currentNode.equals(last)) {
                    last = last.previous;
                } else {
                    previousNode = currentNode.previous;
                    nextNode = currentNode.next;
                    previousNode.next = nextNode;
                    nextNode.previous = previousNode;
                }
                taskAmount--;
                return true;
            }
            currentNode = currentNode.next;
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
        Node currentNode;
        Task taskFromList;
        if (index <= size() / 2) {
            currentNode = first;
            for (int i = 0; i <= index; i++) {
                taskFromList = currentNode.task;
                if (i == index) {
                    return taskFromList;
                }
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = 1; i <= size() - index; i++) {
                taskFromList = currentNode.task;
                if (i == (size() - index)) {
                    return taskFromList;
                }
                currentNode = currentNode.previous;
            }
        }
        return null;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iter();
    }

    public Task[] listToArray() {
        Task[] arr = new Task[taskAmount];
        for (int i = 0; i < taskAmount; i++) {
            arr[i] = getTask(i);
        }
        return arr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkedTaskList)) {
            return false;
        }
        LinkedTaskList list = (LinkedTaskList) o;

        Task[] arr1 = listToArray();
        Task[] arr2 = list.listToArray();

        return Arrays.equals(arr1, arr2);
    }

    @Override
    public int hashCode() {
        Task[] arr = listToArray();
        return Arrays.hashCode(arr);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LinkedTaskList{\n");
        Task[] arr = listToArray();
        for (Task task : arr) {
            sb.append(task.toString()).append(",").append("\n");
        }
        sb.append(", taskAmount=").append(taskAmount).append('}');

        return sb.toString();
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList clone = (LinkedTaskList) super.clone();
        Task[] arr = listToArray();
        clone.first = null;
        clone.last = null;
        clone.taskAmount = 0;
        for (Task task : arr) {
            Task taskClone = task.clone();
            clone.add(taskClone);
        }
        return clone;
    }

    private static class Node {
        Task task;
        Node previous;
        Node next;

        private Node(Task task, Node previous, Node next) {
            this.task = task;
            this.previous = previous;
            this.next = next;
        }
    }

    private class Iter implements Iterator<Task> {

        int nextForReturn;
        int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return nextForReturn < taskAmount;
        }

        @Override
        public Task next() {
            lastReturned = nextForReturn;
            Task[] arr = listToArray();
            Task task = arr[lastReturned];
            nextForReturn++;
            return task;
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }
            Task task = getTask(lastReturned);
            LinkedTaskList.this.remove(task);
            nextForReturn--;
        }
    }
}
