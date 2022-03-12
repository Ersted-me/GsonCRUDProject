package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.BaseEntity;
import com.ersted_me.gsoncrudproject.repository.GenericRepository;
import com.ersted_me.gsoncrudproject.util.GsonIOUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenericRepositoryForNamedEntity<T extends BaseEntity> implements GenericRepository<T, Long> {
    protected final String FILE_NAME;
    protected final Type TYPE_TOKEN;

    public GenericRepositoryForNamedEntity(String file_name, Type TYPE_TOKEN) {
        FILE_NAME = file_name;
        this.TYPE_TOKEN = TYPE_TOKEN;
    }

    @Override
    public T create(T obj) {
        Long lastId = getLastId();
        if (lastId == null)
            return null;

        obj.setId(lastId + 1);

        try {
            GsonIOUtil.writeToJsonFile(FILE_NAME, obj);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
            return null;
        }

        return obj;
    }

    @Override
    public T getById(Long id) {
        return getNoteById(id);
    }

    @Override
    public T update(T t) {
        T oldElement = getNoteById(t.getId());
        if (oldElement == null)
            return null;

        delete(oldElement);
        try {
            GsonIOUtil.writeToJsonFile(FILE_NAME, t);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.toString());
            return null;
        }

        return t;
    }

    @Override
    public void delete(T t) {
        if (t == null)
            return;

        List<T> elements = getNotesFromJsonFile();
        if (elements == null)
            return;

        elements.removeIf(item -> item.getId()
                .equals(t.getId()));

        try {
            GsonIOUtil.writeList(FILE_NAME, elements, true);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.toString());
        }
    }

    @Override
    public List<T> getAll() {
        return getNotesFromJsonFile();
    }

    protected T getNoteById(Long id) {
        List<T> elements = getNotesFromJsonFile();
        if (elements == null)
            return null;

        return elements.stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    protected Long getLastId() {
        List<T> elements = getNotesFromJsonFile();

        if (elements == null)
            return null;

        if (elements.size() == 0)
            return -1L;

        elements.sort(Comparator.comparing(T::getId));
        return elements.get(elements.size() - 1).getId();
    }

    protected List<T> getNotesFromJsonFile() {
        String jsonObjFromFile;
        List<T> list;

        try {
            jsonObjFromFile = GsonIOUtil.read(FILE_NAME);
            list = jsonToObj(jsonObjFromFile, TYPE_TOKEN);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.toString());
            return null;
        }

        return list == null ? new ArrayList<>() : list;
    }

    protected List<T> jsonToObj(String jsonStr,Type targetClassType) {
        return GsonIOUtil.getGson().fromJson(jsonStr, targetClassType);
    }
}
