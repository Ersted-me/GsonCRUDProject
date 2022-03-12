package com.ersted_me.gsoncrudproject.controller;

import com.ersted_me.gsoncrudproject.model.Specialty;
import com.ersted_me.gsoncrudproject.repository.SpecialtyRepository;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository repository;

    public SpecialtyController(SpecialtyRepository repository) {
        this.repository = repository;
    }

    public Specialty create(String name) {
        return repository.create(new Specialty(name));
    }

    public Specialty getById(Long id) {
        return repository.getById(id);
    }

    public Specialty update(Specialty specialty) {
        return repository.update(specialty);
    }

    public void delete(Specialty specialty) {
        repository.delete(specialty);
    }

    public List<Specialty> getAll() {
        return repository.getAll();
    }
}
