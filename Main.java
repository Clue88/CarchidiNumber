import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Professor Name: ");
        String profName = scanner.nextLine();

        GraphExplorer explorer = new GraphExplorer();

        // import from pre-processed parent pointer file
        explorer.importFromCSV("parentPointers.csv");

        // generate BFS tree
//        String startCode = new PCRParser().getInstructorCode("Michael A. Carchidi");
//        explorer.exploreGraph(startCode, true);

        try {
            List<String> shortestPath = explorer.findShortestPath(profName);
            System.out.println(shortestPath);
            System.out.println("Carchidi Number: " + shortestPath.size());
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Instructor not connected or doesn't exist. Check spelling (middle initials?)");
        }
        System.out.println("Average Path Length: " + explorer.getAveragePathLength());
    }
}
