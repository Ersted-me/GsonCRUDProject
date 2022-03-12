package com.ersted_me.gsoncrudproject.view;

import com.ersted_me.gsoncrudproject.controller.SpecialtyController;
import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.model.Specialty;

import java.util.List;
import java.util.Scanner;

public class SpecialtyView {
    private final SpecialtyController controller;
    private final Scanner scanner;

    public SpecialtyView(SpecialtyController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void create() {
        System.out.print("Введите специальность: ");
        String skillName = scanner.nextLine();

        Specialty specialty = controller.create(skillName);
        if (specialty == null) {
            System.out.println("Не удалось записать.");
            return;
        }

        System.out.println("Успешно записано. ID: " + specialty.getId());
    }

    public void delete() {
        Specialty specialty = findSkillById();
        if (specialty == null)
            return;

        controller.delete(specialty);
        System.out.println("Успешно удалено.");
    }

    public void update() {
        Specialty specialty = findSkillById();
        if (specialty == null)
            return;

        System.out.print("Введите новый специальность: ");
        String newSpecialtyName = scanner.nextLine();
        specialty.setName(newSpecialtyName);

        specialty = controller.update(specialty);
        if (specialty == null) {
            System.out.println("Не удалось обновить.");
            return;
        }
        System.out.println("Успешно обновлено.");
    }

    public void getAll() {
        List<Specialty> specialties = controller.getAll();
        if (specialties == null) {
            System.out.println("Не удалось получить список.");
            return;
        }

        System.out.println("|ID\t\t|Name");
        for (Specialty specialty : specialties) {
            System.out.println(specialty.getId() + "\t\t" + specialty.getName());
        }
    }

    public void getById() {
        Specialty specialty = findSkillById();
        if (specialty == null)
            return;

        System.out.println("\n" + specialty.getId() + "|\t" + specialty.getName());
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
                        "\ncreate\t\tдобавить\n" +
                                "delete\t\tудалить\n" +
                                "update\t\tобновить\n" +
                                "getAll\t\tотобразить все\n" +
                                "getById\t\tпосмотреть запись по ID\n" +
                                "exit\t\tвыйти из меню\n");
            }
        }
    }

    private Specialty findSkillById() {
        System.out.print("Введите ID: ");
        Long id = checkId();
        if (id == null)
            return null;

        Specialty specialty = controller.getById(id);
        if (specialty == null) {
            System.out.println("Не удалось найти запись с id: " + id);
            return null;
        }
        return specialty;
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
