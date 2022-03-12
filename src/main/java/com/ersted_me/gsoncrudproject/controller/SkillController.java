package com.ersted_me.gsoncrudproject.controller;

import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.SkillRepository;

import java.util.List;

public class SkillController {
    private final SkillRepository repository;

    public SkillController(SkillRepository repository) {
        this.repository = repository;
    }

    public Skill create(String name) {
        return repository.create(new Skill(name));
    }

    public Skill getById(Long id) {
        return repository.getById(id);
    }

    public Skill update(Skill skill) {
        return repository.update(skill);
    }

    public void delete(Skill skill) {
        repository.delete(skill);
    }

    public List<Skill> getAll() {
        return repository.getAll();
    }
}
