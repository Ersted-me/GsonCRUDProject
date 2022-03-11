package com.ersted_me.gsoncrudproject.view;

import com.ersted_me.gsoncrudproject.controller.SkillController;
import com.ersted_me.gsoncrudproject.model.Skill;

import java.util.List;
import java.util.Scanner;

public class SkillView {
    SkillController controller;
    Scanner scanner;

    public SkillView(SkillController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void create() {
        System.out.print("Введите навык: ");
        String skillName = scanner.nextLine();

        Skill skill = controller.create(skillName);
        if (skill == null) {
            System.out.println("Не удалось записать навык.");
            return;
        }

        System.out.println("Навык успешно записан. ID: " + skill.getId());
    }

    public void delete() {
        Skill skill = findSkillById();
        if (skill == null)
            return;

        controller.delete(skill);
        System.out.println("Навык успешно удален.");
    }

    public void update() {
        Skill skill = findSkillById();
        if (skill == null)
            return;

        System.out.print("Введите новый навык: ");
        String newSkillName = scanner.nextLine();
        skill.setName(newSkillName);

        skill = controller.update(skill);
        if (skill == null) {
            System.out.println("Не удалось обновить навык.");
            return;
        }
        System.out.println("Навык успешно обновлен.");
    }

    public void getAll() {
        List<Skill> skills = controller.getAll();
        if (skills == null) {
            System.out.println("Не удалось получить список навыков.");
            return;
        }

        System.out.println("|ID\t\t|Name");
        for (Skill skill : skills) {
            System.out.println(skill.getId() + "\t\t" + skill.getName());
        }
    }

    public void getById() {
        Skill skill = findSkillById();
        if (skill == null)
            return;

        System.out.println("\n" + skill.getId() + "|\t" + skill.getName());
    }

    public void showMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.print("\nВведите команду (help - справочник команд): ");
            String command = scanner.nextLine();

            switch (command) {
                case "create" -> create();
                case "delete" -> delete();
                case "update" -> update();
                case "getAll" -> getAll();
                case "getById" -> getById();
                case "exit" -> isExit = true;
                case "help" -> System.out.print(
                        "\ncreate\t\tдобавить навык\n" +
                                "delete\t\tудалить навык\n" +
                                "update\t\tобновить навык\n" +
                                "getAll\t\tотобразить все навыки\n" +
                                "getById\t\tпосмотреть навык по ID\n" +
                                "exit\t\tвыйти из меню\n");
            }
        }
    }

    private Skill findSkillById() {
        System.out.print("Введите ID: ");
        Long id = checkId();
        if (id == null)
            return null;

        Skill skill = controller.getById(id);
        if (skill == null) {
            System.out.println("Не удалось найти навык с id: " + id);
            return null;
        }
        return skill;
    }

    private Long checkId() {
        try {
            long id = Long.parseLong(scanner.nextLine());
            if (id < 0)
                throw new IllegalArgumentException("ID не может быть меньше 0.");

            return id;
        } catch (Exception e) {
            System.out.println("ID может состоять только из целого положительного числа.");
            return null;
        }
    }
}
