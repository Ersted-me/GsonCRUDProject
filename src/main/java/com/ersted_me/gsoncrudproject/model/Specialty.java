package com.ersted_me.gsoncrudproject.model;

public class Specialty extends NamedEntity{

    public Specialty(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
