import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        BFS bfs = new BFS();
        HashMap<String, String[]> parents = bfs.exploreGraph("592", 1000);
        String curr = "1";
        while (curr != null) {
            String[] parentArray = parents.get(curr);
            System.out.println(parentArray[0] + " " + parentArray[1]);
            curr = parentArray[0];
        }
    }
}
