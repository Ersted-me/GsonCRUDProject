package com.ersted_me.gsoncrudproject.view;

import com.ersted_me.gsoncrudproject.controller.DeveloperController;
import com.ersted_me.gsoncrudproject.controller.SkillController;
import com.ersted_me.gsoncrudproject.controller.SpecialtyController;
import com.ersted_me.gsoncrudproject.model.Developer;
import com.ersted_me.gsoncrudproject.model.Skill;
import com.ersted_me.gsoncrudproject.model.Specialty;
import com.ersted_me.gsoncrudproject.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DeveloperView {
    private final DeveloperController developerController;
    private final SkillController skillController;
    private final SpecialtyController specialtyController;
    private final Scanner scanner;

    public DeveloperView(DeveloperController developerController,
                         SkillController skillController,
                         SpecialtyController specialtyController,
                         Scanner scanner) {
        this.developerController = developerController;
        this.skillController = skillController;
        this.specialtyController = specialtyController;
        this.scanner = scanner;
    }

    public void create() {
        Developer developer = makeOrUpdateDeveloper();
        developer = developerController.create(
                developer.getFirstName(), developer.getLastName(),
                developer.getSkills(), developer.getSpecialty());

        if (developer == null) {
            System.out.println("Не удалось записать навык.");
            return;
        }

        System.out.println("Навык успешно записан. ID: " + developer.getId());
    }
    private Developer makeOrUpdateDeveloper(){
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();

        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();

        System.out.print("Введите навыки (через enter, для завершения done):\n");
        List<Skill> skills = new ArrayList<>();
        while (true) {
            String nameSkill = scanner.nextLine();
            if (nameSkill.equals("done"))
                break;
            skills.add(skillController.create(nameSkill));
        }
        System.out.print("Введите специальность: ");
        Specialty specialty = specialtyController.create(scanner.nextLine());

        return new Developer(firstName,lastName,skills,specialty, Status.ACTIVE);
    }

    public void delete() {
        Developer developer = findDeveloperById();
        if (developer == null)
            return;

        developerController.delete(developer);
        System.out.println("Запись успешно удалена.");
    }

    public void update() {
        Developer developer = findDeveloperById();
        if (developer == null)
            return;

        Developer updatedDeveloper = makeOrUpdateDeveloper();
        updatedDeveloper.setId(developer.getId());

        updatedDeveloper = developerController.update(updatedDeveloper);
        if (updatedDeveloper == null) {
            System.out.println("Не удалось обновить запись.");
            return;
        }
        System.out.println("Запись успешно обновлена.");
    }

    public void getAll() {
        List<Developer> developers = developerController.getAll();
        if (developers == null) {
            System.out.println("Не удалось получить список.");
            return;
        }

        for (Developer developer : developers) {
            System.out.println("ID:\t" + developer.getId() + "\n" +
                    "Name:\t" + developer.getFirstName() + "\n" +
                    "Lastname:\t" + developer.getLastName() + "\n" +
                    "Skills:\t" + developer.getSkills().stream()
                    .map((e) -> e.getName() + "; ")
                    .collect(Collectors.joining()) + "\n" +
                    "Specialty:\t" + developer.getSpecialty().getName() + "\n");
        }
    }

    public void getById() {
        Developer developer = findDeveloperById();
        if (developer == null)
            return;

        System.out.println("ID:\t" + developer.getId() + "\n" +
                "Name:\t" + developer.getFirstName() + "\n" +
                "Lastname:\t" + developer.getLastName() + "\n" +
                "Skills:\t" + developer.getSkills().stream()
                .map((e) -> e.getName() + "; ")
                .collect(Collectors.joining()) + "\n" +
                "Specialty:\t" + developer.getSpecialty().getName() + "\n");
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

    private Developer findDeveloperById() {
        System.out.print("Введите ID: ");
        Long id = checkId();
        if (id == null)
            return null;

        Developer developer = developerController.getById(id);
        if (developer == null) {
            System.out.println("Не удалось найти запись с id: " + id);
            return null;
        }
        return developer;
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
