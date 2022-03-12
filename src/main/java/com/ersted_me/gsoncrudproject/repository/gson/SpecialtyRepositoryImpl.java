package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.Specialty;
import com.ersted_me.gsoncrudproject.repository.SpecialtyRepository;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SpecialtyRepositoryImpl extends GenericRepositoryForNamedEntity<Specialty> implements SpecialtyRepository {

    public SpecialtyRepositoryImpl(String file_name) {
        super(file_name, new TypeToken<List<Specialty>>(){}.getType());
    }
}
