package com.ersted_me.gsoncrudproject.model;

public class Skill extends NamedEntity {

    public Skill(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
