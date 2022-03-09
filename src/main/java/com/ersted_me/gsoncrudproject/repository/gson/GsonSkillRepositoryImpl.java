package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.GsonSkillRepository;
import com.ersted_me.gsoncrudproject.util.GsonIOUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GsonSkillRepositoryImpl implements GsonSkillRepository {
    private final static String FILE_NAME = "skill.json";

    @Override
    public Skill create(Skill skill) {
        skill.setId(getLastId() + 1);
        GsonIOUtil.write(FILE_NAME, objToJson(skill));
        return skill;
    }

    public Long getLastId() {
        Long id = 0L;
        List<Skill> skills = getAll();

        if (skills != null) {
            skills.sort(Comparator.comparing(Skill::getId));
            id = skills.get(skills.size() - 1).getId();
        }

        return id;
    }

    @Override
    public Skill getById(Long id) {
        Skill skill = getAll().stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (skill == null)
            throw new IllegalArgumentException("Объекта с id = " + id + " не существует.");

        return skill;
    }

    // старый объект удаляется и создается новый, с новым id
    @Override
    public void update(Skill skill) {
        delete(getById(skill.getId()));
        create(skill);
    }

    @Override
    public void delete(Skill skill) {
        List<Skill> skills = getAll();
        Skill removeSkill = null;
        for (Skill item: skills) {
            if(item.getId().equals(skill.getId())){
                removeSkill = item;
            }
        }
        skills.remove(removeSkill);

        GsonIOUtil.writeList(FILE_NAME, objToJson(skills),true);
    }

    @Override
    public List<Skill> getAll() {
        return jsonToObj(GsonIOUtil.read(FILE_NAME));
    }

    @Override
    public String objToJson(Skill skill) {
        return new Gson().toJson(skill);
    }


    public List<String> objToJson(List<Skill> skill) {
        return skill.stream().map((a) -> new Gson().toJson(a)).collect(Collectors.toList());
    }

    public List<Skill> jsonToObj(String jsonStr) {
        Type targetClassType = new TypeToken<List<Skill>>() {
        }.getType();
        return new Gson().fromJson(jsonStr, targetClassType);
    }
}
