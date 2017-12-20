import library.lib.Library;
import library.lib.LibraryThread;
import library.person.Person;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("John", "Smith", 25));
        people.add(new Person("Sarah", "Jones", 18));
        people.add(new Person("Daniel", "Brown", 24));
        people.add(new Person("Olivia", "Evans", 18));

        LibraryThread libraryThread = new LibraryThread(new Library("Library", 2), people);
        libraryThread.startThreads();
        libraryThread.startPassingThroughDoor();
    }
}
