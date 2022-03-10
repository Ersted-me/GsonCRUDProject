package com.ersted_me.gsoncrudproject.repository.gson;

import com.ersted_me.gsoncrudproject.model.BaseEntity;
import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.SkillRepository;
import com.ersted_me.gsoncrudproject.util.GsonIOUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GsonSkillRepositoryImpl implements SkillRepository {
    private final static String FILE_NAME = "skill.json";
    private final Gson gson = new Gson();

    @Override
    public Skill create(Skill skill) {
        skill.setId(getLastId() + 1);
        GsonIOUtil.write(FILE_NAME, skill);
        return skill;
    }

    private Long getLastId() {
        Long id = 0L;
        List<Skill> skills = getSkills();

        if (skills != null) {
            skills.sort(Comparator.comparing(Skill::getId));
            id = skills.get(skills.size() - 1).getId();
        }

        return id;
    }

    @Override
    public Skill getById(Long id) {
        return getSkillById(id);
    }

    // возвращать старую или новую версию объекта?
    @Override
    public Skill update(Skill skill) {
        Skill oldSkill = getSkillById(skill.getId());
        if(oldSkill == null)
            return null;

        delete(oldSkill);
        GsonIOUtil.write(FILE_NAME, skill);

        return skill;
    }

    @Override
    public void delete(Skill skill) {
        if(skill == null)
            return;
        List<Skill> skills = getSkills();

        skills.removeIf(item -> item.getId().equals(skill.getId()));

        GsonIOUtil.writeList(FILE_NAME, skills, true);
    }

    @Override
    public List<Skill> getAll() {
        return getSkills();
    }

    private List<Skill> getSkills() {
        return jsonToObj(GsonIOUtil.read(FILE_NAME));
    }

    private Skill getSkillById(Long id){
        return getSkills().stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private List<Skill> jsonToObj(String jsonStr) {
        Type targetClassType = new TypeToken<List<Skill>>() {
        }.getType();
        return gson.fromJson(jsonStr, targetClassType);
    }
}
