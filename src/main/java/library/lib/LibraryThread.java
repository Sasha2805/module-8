package library.lib;

import JSON.LoadJSON;
import library.person.Person;
import library.person.PersonStatus;
import random.RandomGenerator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class LibraryThread {
    private static final String DOOR_STATUSES_JSON = "src/main/java/filesJSON/doorStatuses.json";
    private List<PersonStatus> statuses = Arrays.asList(PersonStatus.values());
    private Library library;
    private ArrayList<Person> people;
    private Semaphore maxAmount;
    private Semaphore door = new Semaphore(1);

    public LibraryThread(Library library, ArrayList<Person> people) {
        this.library = library;
        this.people = people;
        this.maxAmount = new Semaphore(library.getMaxAmount());
    }

    // Запускаем количество потоков равных people.size()
    public void startThreads(){
        for (int i = 0; i < people.size(); i++){
            int finalI = i;
            new Thread(() -> {
                try {
                    execute(finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Метод принимает индекс person и печатает все соответствующие ему статусы
    private void execute(int personIndex) throws IOException, InterruptedException {
        for (int i = 0; i < statuses.size(); i++){
            if (personIndex < library.getMaxAmount() && (statuses.get(i) == PersonStatus.waited)){
                continue;                                                   // Если индекс person меньше library.getMaxAmount(),
            }                                                               // пропускаем статус "waited"
            if ((personIndex > library.getMaxAmount()) && (statuses.get(i) == PersonStatus.waited)){
                showPersonStatus(people.get(personIndex), statuses.get(i)); // Если индекс person больше library.getMaxAmount(),
                i++;                                                        // печатаем статус "waited",
                maxAmount.acquire();                                        // и ждем пока место в библиотеке освободится
            }
            showPersonStatus(people.get(personIndex), statuses.get(i));
            if (statuses.get(i) == PersonStatus.read){
                try {
                    Thread.sleep(RandomGenerator.random(1,5)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        maxAmount.release();
    }

    // Запуск потоков, которые отвечают за прохождение Person через дверь библиотеки
    public void startPassingThroughDoor() throws InterruptedException {
        for (int i = 0; i < people.size(); i++){
            door.acquire();
            int finalI = i;
            new Thread(() -> {
                try {
                    passingThroughDoor(finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Печать статусов прохождения через дверь
    private void passingThroughDoor(int personIndex) throws FileNotFoundException {
        ArrayList<String> statuses = LoadJSON.load(DOOR_STATUSES_JSON);
        for (int i = 0; i < statuses.size(); i++){
           showPersonStatus(people.get(personIndex), statuses.get(i));
            if (statuses.get(i).contains("проходит")){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        door.release();
    }

    // Печать данных и статуса Person в консоль
    private void showPersonStatus(Person person, PersonStatus status){
        System.out.println(person + " " + status);
    }

    private void showPersonStatus(Person person, String doorStatus){
        System.out.println(person + " " + doorStatus);
    }

    @Override
    public String toString() {
        return "library.lib = " + library + ", peopleCount = " + people;
    }

    public Library getLibrary() {
        return library;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
}
