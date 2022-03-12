package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.SkillRepository;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonSkillRepositoryImpl extends GenericRepositoryForNamedEntity<Skill> implements SkillRepository {

    public GsonSkillRepositoryImpl(String file_name) {
        super(file_name, new TypeToken<List<Skill>>(){}.getType());
    }
}
