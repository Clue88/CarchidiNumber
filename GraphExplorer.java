import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;

public class GraphExplorer {
    private final PCRParser parser;
    private final HashMap<String, String[]> parents;

    public GraphExplorer() {
        parser = new PCRParser();
        parents = new HashMap<>();
    }

    public void exploreGraph(String startInstructorCode) {
        exploreGraph(startInstructorCode, Integer.MAX_VALUE, false);
    }

    public void exploreGraph(String startInstructorCode, boolean save) {
        exploreGraph(startInstructorCode, Integer.MAX_VALUE, save);
    }

    public void exploreGraph(String startInstructorCode, int maxNodes) {
        exploreGraph(startInstructorCode, maxNodes, false);
    }

    public void exploreGraph(String startInstructorCode, int maxNodes, boolean save) {
        parents.put(startInstructorCode, new String[] {null, null});
        LinkedList<String> queue = new LinkedList<>();
        queue.add(startInstructorCode);

        while (!queue.isEmpty()) {
            System.out.println(parents.size() + " instructors found so far");
            if (parents.size() > maxNodes) {
                if (save) {
                    writeToCSV("parentPointers.csv");
                }
                return;
            }
            String curr = queue.poll();
            Set<String> currCourses = new HashSet<>();
            try {
                currCourses = parser.getInstructorCourses(curr);
            } catch (IllegalArgumentException ignored) {

            }
            for (String course : currCourses) {
                Set<String> courseInstructors = new HashSet<>();
                try {
                    courseInstructors = parser.getCourseInstructors(course);
                } catch (IllegalArgumentException ignored) {

                }
                for (String instructor : courseInstructors) {
                    // TODO: Write to Edge List
                    if (parents.containsKey(instructor)) {
                        continue;
                    }
                    queue.add(instructor);
                    parents.put(instructor, new String[] {curr, course});
                }
            }
        }
        if (save) {
            writeToCSV("parentPointers.csv");
        }
    }

    public List<String> findShortestPath(String instructorName) {
        List<String> path = new ArrayList<>();
        String professorName;
        String curr = parser.getInstructorCode(instructorName);

        if (!parents.containsKey(curr)) {
            throw new IllegalArgumentException("instructor not found or not connected");
        }

        while (curr != null) {
            String[] parentArray = parents.get(curr);
            if (parentArray[0] != null) {
                professorName = parser.getInstructorName(parentArray[0]);
                path.add("(" + professorName + ", " + parentArray[1] + ")");
            }
            curr = parentArray[0];
        }
        return path;
    }

    private void writeToCSV(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Map.Entry<String, String[]> entry : parents.entrySet()) {
                String[] value = entry.getValue();
                writer.write(entry.getKey() + "," + value[0] + "," + value[1] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFromCSV(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] elements = scanner.nextLine().split(",");
                if (elements[1].equals("null")) {
                    elements[1] = null;
                }
                if (elements[2].equals("null")) {
                    elements[2] = null;
                }
                parents.put(elements[0], new String[] {elements[1], elements[2]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
