import library.Library;
import library.LibraryFlow;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter library name: ");
        String libraryName = in.next();
        System.out.print("Enter maxAmount: ");
        int maxAmount = in.nextInt();
        System.out.print("Enter peopleCount: ");
        int peopleCount = in.nextInt();
        LibraryFlow libraryFlow = new LibraryFlow(new Library(libraryName, maxAmount), peopleCount);
        in.close();
        System.out.println(libraryFlow);
        libraryFlow.execute(1);

    }

}
