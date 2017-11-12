import library.lib.Library;
import library.lib.LibraryThread;
import library.lib.PersonAtLibrary;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter library.lib name: ");
        String libraryName = in.next();
        System.out.print("Enter maxAmount: ");
        int maxAmount = in.nextInt();
        System.out.print("Enter peopleCount: ");
        int peopleCount = in.nextInt();
        in.close();

        LibraryThread thread = new LibraryThread(new Library(libraryName, maxAmount), peopleCount);
        thread.getPersonsAtLibrary().add(new PersonAtLibrary("John", "Smith", 25, null));
        thread.getPersonsAtLibrary().add(new PersonAtLibrary("Sarah", "Jones", 18, null));
        thread.getPersonsAtLibrary().add(new PersonAtLibrary("Daniel", "Brown", 24, null));
        thread.getPersonsAtLibrary().add(new PersonAtLibrary("Olivia", "Evans", 18, null));
        thread.startThreads();
        thread.enterAndExitToLib();

    }

}
