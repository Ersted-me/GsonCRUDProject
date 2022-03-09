package com.ersted_me.gsoncrudproject.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class GsonIOUtil {
    private static final String SQUARE_BRACKETS = "[]";

    public static void write(String filename, String data) {
        try(RandomAccessFile fout = new RandomAccessFile(filename,"rw")){

            if(fout.length() == 0)
                fout.write(SQUARE_BRACKETS.getBytes(StandardCharsets.UTF_8));

            fout.seek(fout.length() - 1);

            if(fout.length() == 2)
                fout.write((data + "]").getBytes(StandardCharsets.UTF_8));
            else
                fout.write(("," + data + "]").getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

    public static void writeList(String filename, List<String> data, boolean overwrite){
        try(RandomAccessFile fout = new RandomAccessFile(filename,"rw")){
            if(overwrite)
                fout.setLength(0);

            for(String item : data){
                if(fout.length() == 0)
                    fout.write(SQUARE_BRACKETS.getBytes(StandardCharsets.UTF_8));

                fout.seek(fout.length() - 1);

                if(fout.length() == 2)
                    fout.write((item + "]").getBytes(StandardCharsets.UTF_8));
                else
                    fout.write(("," + item + "]").getBytes(StandardCharsets.UTF_8));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }


    public static String read(String filename){
        try(BufferedReader fin = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename)))){
            return fin.lines().collect(Collectors.joining());

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return "";
    }
}
