package library.lib;

import com.alibaba.fastjson.JSON;
import library.filesJSON.LoadJSON;
import random.RandomGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class LibraryThread {
    private static final String STATUSES_JSON = "src/main/java/library.filesJSON/personStatuses.json";
    private static final String DOOR_STATUSES_JSON = "src/main/java/library.filesJSON/doorStatuses.json";

    private Library library;
    private ArrayList<PersonAtLibrary> people;
    private Semaphore semaphore;
    private Semaphore door = new Semaphore(1);

    public LibraryThread(Library library, int peopleCount) {
        this.library = library;
        this.people = new ArrayList<>(peopleCount);
        this.semaphore = new Semaphore(library.getMaxAmount());
    }

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

    private void execute(int flowIndex) throws IOException, InterruptedException {
        ArrayList<String> statuses = LoadJSON.load(STATUSES_JSON);
        if (flowIndex < library.getMaxAmount()){
            statuses.remove("waited");
        }
        for (int i = 0; i < statuses.size(); i++){
            if ((flowIndex > library.getMaxAmount()) && (statuses.get(i).equals("waited"))){
                showPersonStatus(people.get(flowIndex), statuses.get(i));
                i++;
                semaphore.acquire();
            }
            showPersonStatus(people.get(flowIndex), statuses.get(i));
            if (statuses.get(i).equals("read")){
                try {
                    Thread.sleep(RandomGenerator.randomNumber(1,5)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        semaphore.release();
    }

    public void enterAndExitToLib() throws InterruptedException {
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

    private void passingThroughDoor(int flowIndex) throws FileNotFoundException {
        ArrayList<String> statuses = LoadJSON.load(DOOR_STATUSES_JSON);
        for (int i = 0; i < statuses.size(); i++){
            showPersonStatus(people.get(flowIndex), statuses.get(i));
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

    private void showPersonStatus(PersonAtLibrary person, String status){
        person.setPersonStatus(status);
        System.out.println(person);
    }

    @Override
    public String toString() {
        return "LibraryThread{" +
                "library.lib = " + library +
                ", peopleCount = " + people +
                '}';
    }

    public Library getLibrary() {
        return library;
    }

    public ArrayList<PersonAtLibrary> getPeople() {
        return people;
    }

}
