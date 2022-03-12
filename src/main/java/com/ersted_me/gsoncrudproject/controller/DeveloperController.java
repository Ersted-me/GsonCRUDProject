package com.ersted_me.gsoncrudproject.controller;

import com.ersted_me.gsoncrudproject.model.Developer;
import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.model.Specialty;
import com.ersted_me.gsoncrudproject.model.Status;
import com.ersted_me.gsoncrudproject.repository.DeveloperRepository;

import java.util.List;

public class DeveloperController {
    private final DeveloperRepository repository;

    public DeveloperController(DeveloperRepository repository) {
        this.repository = repository;
    }

    public Developer create(String firstName, String lastName,
                        List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer(firstName, lastName, skills, specialty, Status.ACTIVE);
        return repository.create(developer);
    }

    public Developer getById(Long id) {
        return repository.getById(id);
    }

    public Developer update(Developer developer) {
        return repository.update(developer);
    }

    public void delete(Developer developer) {
        repository.delete(developer);
    }

    public List<Developer> getAll() {
        return repository.getAll();
    }
}
