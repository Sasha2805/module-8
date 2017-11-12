package library.lib;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class LibraryFlow {
    private static final String STATUSES_JSON = "src/main/java/library.filesJSON/personStatuses.json";
    private static final String DOOR_STATUSES_JSON = "src/main/java/library.filesJSON/doorStatuses.json";

    private Library library;
    private ArrayList<PersonAtLibrary> personsAtLibrary;
    private Semaphore semaphore;
    private Semaphore door = new Semaphore(1);

    public LibraryFlow(Library library, int peopleCount) {
        this.library = library;
        this.personsAtLibrary = new ArrayList<>(peopleCount);
        this.semaphore = new Semaphore(library.getMaxAmount());
    }

    public void startThreads(){
        for (int i = 0; i < personsAtLibrary.size(); i++){
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
        ArrayList<String> statuses = loadStatuses(STATUSES_JSON);
        if (flowIndex < library.getMaxAmount()){
            statuses.remove("waited");
        }
        for (int i = 0; i < statuses.size(); i++){
            if ((flowIndex > library.getMaxAmount()) && (statuses.get(i).equals("waited"))){
                showPersonStatus(personsAtLibrary.get(flowIndex), statuses.get(i));
                i++;
                semaphore.acquire();
            }
            showPersonStatus(personsAtLibrary.get(flowIndex), statuses.get(i));
            if (statuses.get(i).equals("read")){
                try {
                    Thread.sleep(randomSleepTime(1,5)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        semaphore.release();
    }

    public void enterAndExitToLib() throws InterruptedException {
        for (int i = 0; i < personsAtLibrary.size(); i++){
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
        ArrayList<String> statuses = loadStatuses(DOOR_STATUSES_JSON);
        for (int i = 0; i < statuses.size(); i++){
            showPersonStatus(personsAtLibrary.get(flowIndex), statuses.get(i));
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

    public ArrayList<String> loadStatuses(String path) throws FileNotFoundException {
        String json = new Scanner(new File(path)).useDelimiter("\\Z").next();
        ArrayList<String> statuses = new ArrayList<>();
        statuses.addAll(JSON.parseArray(json, String.class));
        return statuses;
    }

    private void showPersonStatus(PersonAtLibrary person, String status){
        person.setPersonStatus(status);
        System.out.println(person);
    }

    public int randomSleepTime(int minTime, int maxTime){
        Random random = new Random();
        return random.nextInt(maxTime - minTime) + minTime;
    }

    @Override
    public String toString() {
        return "LibraryFlow{" +
                "library.lib = " + library +
                ", peopleCount = " + personsAtLibrary +
                '}';
    }

    public Library getLibrary() {
        return library;
    }

    public ArrayList<PersonAtLibrary> getPersonsAtLibrary() {
        return personsAtLibrary;
    }

}
