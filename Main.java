import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter Professor Name: ");
        String profName = scanner.nextLine();  // Read user input

        GraphExplorer explorer = new GraphExplorer();
        explorer.exploreGraph("592", true);
//        explorer.exploreGraph("8706", true);
        System.out.println(explorer.findShortestPath(profName));
    }
}
