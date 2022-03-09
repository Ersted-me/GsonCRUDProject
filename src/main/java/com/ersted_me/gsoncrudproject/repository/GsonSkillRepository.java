package com.ersted_me.gsoncrudproject.repository;

import com.ersted_me.gsoncrudproject.model.Skill;

import java.util.List;

public interface GsonSkillRepository extends SkillRepository{
    String objToJson(Skill skill);

    List<Skill> jsonToObj(String jsonStr);
}
