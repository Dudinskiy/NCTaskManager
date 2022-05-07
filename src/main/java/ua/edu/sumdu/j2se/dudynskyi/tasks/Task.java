package ua.edu.sumdu.j2se.dudynskyi.tasks;

public class Task {
    private String title;
    private int time;
    private int startTime;
    private int endTime;
    private int repeatInterval;
    private boolean active;

    /**
     * Данный конструктор создает неактивную задачу, которая выполняется один раз.
     *
     * @param title название задачи.
     * @param time  время выполнения задачи.
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;
    }

    /**
     * Данный конструктор создает неактивную задачу, которая выполняется
     * в заданном промежутке времени с заданным интервалом.
     *
     * @param title    вазвание задачи.
     * @param start    время начала выполнения задачи.
     * @param end      время окончания выполнения задачи.
     * @param interval интервал времени, с которым выполняется задача.
     */
    public Task(String title, int start, int end, int interval) {
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
     * @return в случае если задача повторяемая, возвращается время начала выполнения задачи.
     */
    public int getTime() {
        if (isRepeated()) {
            return startTime;
        } else {
            return time;
        }
    }

    /**
     * Метод устанавливает время выполнения неповторяемой задачи. Если задача была повторяемой
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
     * Метод возвращает время начала выполнения повторяемой задачи.
     *
     * @return если задача не неповторяемая, возвращается время выполения задачи.
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
     * @return если задача неповторяемая, возвращается время выполения задачи.
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
}
