package com.ersted_me.gsoncrudproject.model;

public class NamedEntity extends BaseEntity {
    protected String name;

    public NamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NamedEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
