package com.ersted_me.gsoncrudproject.repository;

import com.ersted_me.gsoncrudproject.model.Skill;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t);

    T getById(ID id);

    void update(T t);

    void delete(T t);

    List<T> getAll();
}
