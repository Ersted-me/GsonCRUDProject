package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.Developer;
import com.ersted_me.gsoncrudproject.model.Specialty;
import com.ersted_me.gsoncrudproject.model.Status;
import com.ersted_me.gsoncrudproject.repository.DeveloperRepository;
import com.ersted_me.gsoncrudproject.util.GsonIOUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GsonDeveloperRepositoryImpl extends GenericRepositoryForNamedEntity<Developer> implements DeveloperRepository {

    public GsonDeveloperRepositoryImpl(String file_name) {
        super(file_name, new TypeToken<List<Developer>>(){}.getType());
    }

    @Override
    public void delete(Developer t) {
        if (t == null)
            return;

        List<Developer> elements = getNotesFromJsonFile();
        if (elements == null)
            return;

        elements.forEach(d -> {
            if(d.getId().equals(t.getId())){
                d.setStatus(Status.DELETED);
            }
        });

        try {
            GsonIOUtil.writeList(FILE_NAME, elements, true);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.toString());
        }
    }

    @Override
    public Developer getById(Long id) {

        return getAll().stream()
                .filter((e) -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Developer> getAll() {
        return super.getAll().stream()
                .filter((e) -> e.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }
}
