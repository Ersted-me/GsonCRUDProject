package com.ersted_me.gsoncrudproject.controller;

import com.ersted_me.gsoncrudproject.model.Specialty;
import com.ersted_me.gsoncrudproject.repository.SpecialtyRepository;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyController(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty create(String name) {
        return specialtyRepository.create(new Specialty(name));
    }

    public Specialty getById(Long id) {
        return specialtyRepository.getById(id);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public void delete(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }
}
