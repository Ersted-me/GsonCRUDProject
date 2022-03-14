package com.ersted_me.gsoncrudproject.util;

import com.ersted_me.gsoncrudproject.model.BaseEntity;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GsonIOUtil {
    private static final String pathToFile = "/src/main/resources/";
    private static final String SQUARE_BRACKETS = "[]";
    private static final Gson gson = new Gson();


    public static Gson getGson() {
        return gson;
    }

    public static void writeToJsonFile(String filename, BaseEntity baseEntity) throws IOException {
        String absolutePathToFile = getPath(filename);

        if (notFile(absolutePathToFile))
            throw new FileNotFoundException();

        String jsonString = objToJson(baseEntity);

        try (RandomAccessFile fout = new RandomAccessFile(absolutePathToFile, "rw")) {
            writeCorrectFormOfString(fout, jsonString);
        }
    }

    public static void writeList(String filename,
                                 List<? extends BaseEntity> entities,
                                 boolean overwrite) throws IOException {
        String absolutePathToFile = getPath(filename);

        if (notFile(absolutePathToFile))
            throw new FileNotFoundException();

        List<String> jsonEntities = objToJson(entities);

        try (RandomAccessFile fout = new RandomAccessFile(absolutePathToFile, "rw")) {
            if (overwrite)
                fout.setLength(0);

            for (String jsonEntity : jsonEntities)
                writeCorrectFormOfString(fout, jsonEntity);
        }
    }

    public static String read(String filename) throws IOException {
        String absolutePathToFile = getPath(filename);

        if (notFile(absolutePathToFile))
            throw new FileNotFoundException();

        try (BufferedReader fin = new BufferedReader(
                new InputStreamReader(new FileInputStream(absolutePathToFile)))) {
            return fin.lines().collect(Collectors.joining());
        }
    }

    private static boolean notFile(String filename) {
        File file = new File(filename);
        return !file.exists() | !file.isFile();
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

    private static String getPath(String filename){
        Path currentAbsolutePath = Paths.get("").toAbsolutePath();

        return currentAbsolutePath + pathToFile + filename;
    }
}
