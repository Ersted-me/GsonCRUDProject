package com.ersted_me.gsoncrudproject.repository;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t);

    T getById(ID id);

    T update(T t);

    void delete(T t);

    List<T> getAll();
}
