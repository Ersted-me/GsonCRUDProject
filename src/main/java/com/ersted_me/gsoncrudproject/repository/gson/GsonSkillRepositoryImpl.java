package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.SkillRepository;
import com.ersted_me.gsoncrudproject.util.GsonIOUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GsonSkillRepositoryImpl implements SkillRepository {
    private final static String FILE_NAME = "skill.json";
    private final Gson gson = new Gson();

    @Override
    public Skill create(Skill skill) {
        Long lastId = getLastId();
        if (lastId == null)
            return null;

        skill.setId(lastId + 1);

        try {
            GsonIOUtil.writeToJsonFile(FILE_NAME, skill);
        } catch (IOException e) {
            return null;
        }

        return skill;
    }

    @Override
    public Skill getById(Long id) {
        return getSkillById(id);
    }

    @Override
    public Skill update(Skill skill) {
        Skill oldSkill = getSkillById(skill.getId());
        if (oldSkill == null)
            return null;

        delete(oldSkill);
        try {
            GsonIOUtil.writeToJsonFile(FILE_NAME, skill);
        } catch (IOException e) {
            System.err.println("(GsonSkillRepositoryImpl -> update()) " +
                    "Ошибка: " + e.toString());
            return null;
        }

        return skill;
    }

    @Override
    public void delete(Skill skill) {
        if (skill == null)
            return;

        List<Skill> skills = getSkills();
        if (skills == null)
            return;

        skills.removeIf(item -> item.getId()
                .equals(skill.getId()));

        try {
            GsonIOUtil.writeList(FILE_NAME, skills, true);
        } catch (IOException e) {
            System.err.println("(GsonSkillRepositoryImpl -> delete()) " +
                    "Ошибка: " + e.toString());
        }
    }

    @Override
    public List<Skill> getAll() {
        return getSkills();
    }

    private Skill getSkillById(Long id) {
        List<Skill> skills = getSkills();
        if (skills == null)
            return null;

        return skills.stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private Long getLastId() {
        List<Skill> skills = getSkills();

        if (skills == null)
            return null;

        if (skills.size() == 0)
            return -1L;

        skills.sort(Comparator.comparing(Skill::getId));
        return skills.get(skills.size() - 1).getId();
    }

    private List<Skill> getSkills() {
        String jsonObjFromFile;
        List<Skill> skills;

        try {
            jsonObjFromFile = GsonIOUtil.read(FILE_NAME);
            skills = jsonToObj(jsonObjFromFile);
        } catch (IOException e) {
            System.err.println("(GsonSkillRepositoryImpl -> getSkills()) " +
                    "Ошибка: " + e.toString());
            return null;
        }

        return skills == null ? new ArrayList<>() : skills;
    }

    private List<Skill> jsonToObj(String jsonStr) {
        Type targetClassType = new TypeToken<List<Skill>>() {
        }.getType();
        return gson.fromJson(jsonStr, targetClassType);
    }
}
