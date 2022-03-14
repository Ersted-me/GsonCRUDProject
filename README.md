#GsonCRUDProject

##Описание
Необходимо реализовать консольное CRUD приложение, которое имеет следующие сущности:  
Developer (id, firstName, lastName, List<Skill> skills, Specialty specialty)  
Skill  
Specialty  
Status (enum ACTIVE, DELETED)  

В качестве хранилища данных необходимо использовать json файлы:
developers.json, skills.json, specialties.json.  
Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.

##Слои:  
model - POJO классы  
repository - классы, реализующие доступ к текстовым файлам  
controller - обработка запросов от пользователя  
view - все данные, необходимые для работы с консолью

##Инструкция по запуску
1. Скачать архив с проектом.  
2. Распаковать в удобную папку.
3. Запустить класс CrudConsole.