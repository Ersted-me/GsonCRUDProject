package com.ersted_me.gsoncrudproject.view;

import com.ersted_me.gsoncrudproject.controller.DeveloperController;
import com.ersted_me.gsoncrudproject.controller.SkillController;
import com.ersted_me.gsoncrudproject.controller.SpecialtyController;
import com.ersted_me.gsoncrudproject.repository.DeveloperRepository;
import com.ersted_me.gsoncrudproject.repository.SkillRepository;
import com.ersted_me.gsoncrudproject.repository.SpecialtyRepository;
import com.ersted_me.gsoncrudproject.repository.gson.GsonDeveloperRepositoryImpl;
import com.ersted_me.gsoncrudproject.repository.gson.GsonSkillRepositoryImpl;
import com.ersted_me.gsoncrudproject.repository.gson.GsonSpecialtyRepositoryImpl;

import java.util.Scanner;

public class RunnerView {
    private DeveloperView developerView;
    private SkillView skillView;
    private SpecialtyView specialtyView;

    private Scanner scanner = new Scanner(System.in);

    public RunnerView() {
        DeveloperRepository developerRepository =
                new GsonDeveloperRepositoryImpl("developer.json");
        SkillRepository skillRepository =
                new GsonSkillRepositoryImpl("skill.json");
        SpecialtyRepository specialtyRepository =
                new GsonSpecialtyRepositoryImpl("specialty.json");

        DeveloperController developerController =
                new DeveloperController(developerRepository);
        SkillController skillController =
                new SkillController(skillRepository);
        SpecialtyController specialtyController =
                new SpecialtyController(specialtyRepository);

        developerView = new DeveloperView(
                developerController,
                skillController,
                specialtyController,
                scanner);

        skillView = new SkillView(
                skillController,
                scanner);

        specialtyView = new SpecialtyView(
                specialtyController,
                scanner);
    }

    public void showMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.print("\nВведите категорию (help - справочник): ");
            String command = scanner.nextLine();

            switch (command) {
                case "skill" -> skillView.showMenu();
                case "specialty" -> specialtyView.showMenu();
                case "developer" -> developerView.showMenu();
                case "exit" -> isExit = true;
                case "help" -> System.out.print(
                        "\nskill\t\tменю создания навыков\n" +
                                "specialty\t\tменю создания специальностей\n" +
                                "developer\t\tменю создания разработчиков\n" +
                                "exit\t\tвыйти\n");
            }
        }
    }
}
