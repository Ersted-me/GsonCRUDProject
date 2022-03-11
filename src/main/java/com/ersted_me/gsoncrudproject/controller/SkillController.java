package com.ersted_me.gsoncrudproject.controller;

import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.SkillRepository;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill create(String name) {
        return skillRepository.create(new Skill(name));
    }

    public Skill getById(Long id) {
        return skillRepository.getById(id);
    }

    public Skill update(Skill skill) {
        return skillRepository.update(skill);
    }

    public void delete(Skill skill) {
        skillRepository.delete(skill);
    }

    public List<Skill> getAll() {
        return skillRepository.getAll();
    }
}
