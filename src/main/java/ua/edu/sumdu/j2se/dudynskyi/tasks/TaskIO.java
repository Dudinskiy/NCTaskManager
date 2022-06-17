package ua.edu.sumdu.j2se.dudynskyi.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            dos.writeInt(tasks.taskAmount);
            for (Task task : tasks) {
                char[] title = task.getTitle().toCharArray();
                dos.writeInt(title.length);
                dos.writeChars(task.getTitle());
                dos.writeBoolean(task.isActive());
                dos.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    long start = dateToLong(task.getStartTime());
                    dos.writeLong(start);
                    long end = dateToLong(task.getEndTime());
                    dos.writeLong(end);
                } else {
                    long time = dateToLong(task.getTime());
                    dos.writeLong(time);
                }
            }
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long dateToLong(LocalDateTime time) {
        ZonedDateTime timeZ = ZonedDateTime.of(time
                , ZoneId.systemDefault());
        return timeZ.toInstant().toEpochMilli();
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream dis = new DataInputStream(in)) {
            int taskAmount = dis.readInt();
            for (int i = 0; i < taskAmount; i++) {
                Task task;
                int length = dis.readInt();
                char[] chars = new char[length];
                for (int j = 0; j < length; j++) {
                    chars[j] = dis.readChar();
                }
                String title = String.valueOf(chars);
                boolean active = dis.readBoolean();
                int repeatInterval = dis.readInt();
                if (repeatInterval > 0) {
                    long startMilli = dis.readLong();
                    LocalDateTime start = longToDate(startMilli);
                    long endMilli = dis.readLong();
                    LocalDateTime end = longToDate(endMilli);
                    task = new Task(title, start, end, repeatInterval);
                } else {
                    long timeMilli = dis.readLong();
                    LocalDateTime time = longToDate(timeMilli);
                    task = new Task(title, time);
                }
                task.setActive(active);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LocalDateTime longToDate(long milli) {
        return LocalDateTime
                .ofInstant(Instant.ofEpochMilli(milli)
                        , TimeZone.getDefault().toZoneId());
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))
        ) {
            write(tasks, bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        try (BufferedWriter bw = new BufferedWriter(out)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Task[] arr = tasks.toArray();

            String json = gson.toJson(arr);

            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        try (BufferedReader br = new BufferedReader(in)) {
            String str = "";
            StringBuilder builder = new StringBuilder(str);
            while (br.ready()) {
                builder.append(br.readLine());
            }
            String json = builder.toString();
            Gson gson = new GsonBuilder().create();
            Task[] arr = gson.fromJson(json, Task[].class);

            for (Task task : arr) {
                tasks.add(task);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            write(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try (FileReader reader = new FileReader(file)) {
            read(tasks, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
