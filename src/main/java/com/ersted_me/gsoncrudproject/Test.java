package com.ersted_me.gsoncrudproject;

import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.repository.gson.GsonSkillRepositoryImpl;
import com.ersted_me.gsoncrudproject.util.GsonIOUtil;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        GsonSkillRepositoryImpl g = new GsonSkillRepositoryImpl();

        Skill skill = new Skill("name");
        Skill skill2 = new Skill("name2");
        Skill skill3 = new Skill("name3");
        Skill skill4 = new Skill(2L,"new");

        g.create(skill);
        g.create(skill2);
        g.create(skill3);

        g.getAll().forEach(System.out::println);
        System.out.println();

        g.delete(g.getById(1L));

        g.update(skill4);
//
        g.getAll().forEach(System.out::println);

    }
}
