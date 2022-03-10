package com.ersted_me.gsoncrudproject.util;

import com.ersted_me.gsoncrudproject.model.BaseEntity;
import com.ersted_me.gsoncrudproject.model.Skill;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class GsonIOUtil {
    private static final String SQUARE_BRACKETS = "[]";
    private static final Gson gson = new Gson();

    public static void write(String filename, BaseEntity baseEntity) {
        String jsonString = objToJson(baseEntity);
        try (RandomAccessFile fout = new RandomAccessFile(filename, "rw")) {

            writeCorrectFormOfString(fout, jsonString);

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }


    public static void writeList(String filename, List<? extends BaseEntity> entities, boolean overwrite) {
        List<String> jsonEntities = objToJson(entities);

        try (RandomAccessFile fout = new RandomAccessFile(filename, "rw")) {
            if (overwrite)
                fout.setLength(0);

            for (String jsonEntity : jsonEntities)
                writeCorrectFormOfString(fout, jsonEntity);

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }


    public static String read(String filename) {
        try (BufferedReader fin = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename)))) {
            return fin.lines().collect(Collectors.joining());

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return null;
    }

    private static String objToJson(BaseEntity entity) {
        return gson.toJson(entity);
    }

    private static List<String> objToJson(List<? extends BaseEntity> entities) {
        return entities.stream().map(gson::toJson).collect(Collectors.toList());
    }

    private static void writeCorrectFormOfString(RandomAccessFile fout, String jsonEntity) throws IOException {
        if (fout.length() == 0)
            fout.write(SQUARE_BRACKETS.getBytes(StandardCharsets.UTF_8));

        fout.seek(fout.length() - 1);

        if (fout.length() == 2)
            fout.write((jsonEntity + "]").getBytes(StandardCharsets.UTF_8));
        else
            fout.write(("," + jsonEntity + "]").getBytes(StandardCharsets.UTF_8));
    }
}
