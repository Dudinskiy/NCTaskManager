package ua.edu.sumdu.j2se.dudynskyi.tasks;

import java.util.Objects;

public class Task implements Cloneable {
    private String title;
    private int time;
    private int startTime;
    private int endTime;
    private int repeatInterval;
    private boolean active;

    /**
     * Данный конструктор создает неактивную
     * задачу, которая выполняется один раз.
     *
     * @param title название задачи.
     * @param time  время выполнения задачи.
     */
    public Task(String title, int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Время не может" +
                    " быть отрицательным");
        }
        this.title = title;
        this.time = time;
    }


    /**
     * Данный конструктор создает неактивную задачу,
     * которая выполняется в заданном промежутке времени
     * с заданным интервалом.
     *
     * @param title    вазвание задачи.
     * @param start    время начала выполнения задачи.
     * @param end      время окончания выполнения задачи.
     * @param interval интервал времени, с которым выполняется
     *                 задача.
     */
    public Task(String title, int start, int end, int interval) {
        if (start < 0 || end < 0) {
            throw new IllegalArgumentException("Время не должно" +
                    " быть отрицательным");
        }
        if (start >= end) {
            throw new IllegalArgumentException("Время начала" +
                    " задачи должно быть меньше времени конца");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("Интервал должен " +
                    "быть больше 0");
        }
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
    }

    /**
     * Метод возвращает название задачи.
     *
     * @return название задачи
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод устанавливает название задачи.
     *
     * @param title значение не должно быть пустым или null.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод определяет является ли задача активной.
     *
     * @return true - задача активна.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Метод изменяет статус активности задачи.
     *
     * @param active true - задача активна. false - задача неактивна.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Метод возвращает время выполнения задачи.
     *
     * @return в случае если задача повторяемая, возвращается
     * время начала выполнения задачи.
     */
    public int getTime() {
        if (isRepeated()) {
            return startTime;
        } else {
            return time;
        }
    }

    /**
     * Метод устанавливает время выполнения неповторяемой задачи.
     * Если задача была повторяемой
     * метод делает ее неповторяемой.
     *
     * @param time значение должно быть больше 0.
     */
    public void setTime(int time) {
        if (isRepeated()) {
            this.startTime = time;
            this.endTime = time;
            this.repeatInterval = 0;
        }
        this.time = time;
    }

    /**
     * Метод возвращает время начала выполнения
     * повторяемой задачи.
     *
     * @return если задача неповторяемая, возвращается
     * время выполения задачи.
     */
    public int getStartTime() {
        if (isRepeated()) {
            return startTime;
        } else {
            return time;
        }
    }

    /**
     * Метод возвращает время окончания выполения повторяемой задачи.
     *
     * @return если задача неповторяемая, возвращается
     * время выполения задачи.
     */
    public int getEndTime() {
        if (isRepeated()) {
            return endTime;
        } else {
            return time;
        }
    }

    /**
     * Метод возвращает интервал выполнения повторяемой задачи.
     *
     * @return если задача неповторяемая, возвращается 0.
     */
    public int getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * Метод устанавливает время и интервал выполнения повторяемой задачи.
     * Если задача была неповторяемой метод делает ее повторяемой.
     *
     * @param start    значение должно быть больше 0.
     * @param end      значение должно быть больше 0.
     * @param interval значение должно быть больше или равно 1.
     */
    public void setTime(int start, int end, int interval) {
        if (!isRepeated()) {
            this.time = start;
        }
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
    }

    /**
     * Метод определяет является ли задача повторяемой.
     *
     * @return true - задача повторяемая.
     */
    public boolean isRepeated() {
        return repeatInterval != 0;
    }

    /**
     * Метод возвращает время выполнения задачи
     * относительно текущего момента времени.
     *
     * @param current текущее время.
     * @return если задача неактивна или
     * завершена
     * возвращается -1.
     */
    public int nextTimeAfter(int current) {
        if (!isActive()) {
            return -1;
        }
        if (isRepeated()) {
            if (startTime > current) {
                return startTime;
            } else if ((endTime - repeatInterval) <= current) {
                return -1;
            } else {
                int nextTaskTime = startTime;
                while (true) {
                    nextTaskTime = nextTaskTime + repeatInterval;

                    if (nextTaskTime > current) {
                        return nextTaskTime;
                    }
                }
            }
        } else if (time > current) {
            return time;
        } else {
            return -1;
        }
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return getTime() == task.getTime()
                && getStartTime() == task.getStartTime()
                && getEndTime() == task.getEndTime()
                && getRepeatInterval() == task.getRepeatInterval()
                && isActive() == task.isActive()
                && getTitle().equals(task.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getTime()
                , getStartTime()
                , getEndTime()
                , getRepeatInterval()
                , isActive());
    }

    @Override
    public String toString() {
        return "Task{" + "title='" + title + '\''
                + ", time=" + time
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", repeatInterval=" + repeatInterval
                + ", active=" + active
                + '}';
    }
}
