package ua.edu.sumdu.j2se.dudynskyi.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            AbstractTaskList list = (AbstractTaskList) objectInputStream.readObject();
            for (Task task : list) {
                tasks.add(task);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        BufferedWriter bw = new BufferedWriter(out);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Task[] arr = tasks.toArray();

        String json = gson.toJson(arr);

        bw.write(json);
        bw.flush();
        bw.close();
    }

    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String str = "";
        StringBuilder builder = new StringBuilder(str);
        while (bufferedReader.ready()) {
            builder.append(bufferedReader.readLine());
        }
        String json = builder.toString();
        Gson gson = new GsonBuilder().create();
        Task[] arr = gson.fromJson(json, Task[].class);
        in.close();

        for (Task task : arr) {
            tasks.add(task);
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        write(tasks, writer);
        writer.close();
    }

    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        FileReader reader = new FileReader(file);
        read(tasks, reader);
        reader.close();
    }
}
