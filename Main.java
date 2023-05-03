import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter Professor Name: ");
        String profName = scanner.nextLine();  // Read user input

        GraphExplorer explorer = new GraphExplorer();
//        explorer.exploreGraph("592", 3000, true);
//        explorer.exploreGraph("8706", true);
        explorer.importFromCSV("parentPointers.csv");

        List<String> shortestPath = new ArrayList<>();
        try {
            shortestPath = explorer.findShortestPath(profName);
            System.out.println(shortestPath);
            System.out.println("Carchidi Number: " + shortestPath.size());
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Instructor not connected or doesn't exist. Check spelling (middle initials?)");
        }
    }
}
