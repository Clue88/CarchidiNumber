import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BFS {
    private final PCRParser parser;
    private final HashMap<String, String[]> parents;

    public BFS() {
        parser = new PCRParser();
        parents = new HashMap<>();
    }

    public HashMap<String, String[]> exploreGraph(String startInstructorCode) {
        return exploreGraph(startInstructorCode, Integer.MAX_VALUE, false);
    }

    public HashMap<String, String[]> exploreGraph(String startInstructorCode, int maxNodes) {
        return exploreGraph(startInstructorCode, maxNodes, false);
    }

    public HashMap<String, String[]> exploreGraph(
            String startInstructorCode, int maxNodes, boolean writeToEdgeList) {
        parents.put(startInstructorCode, new String[] {null, null});
        LinkedList<String> queue = new LinkedList<>();
        queue.add(startInstructorCode);

        while (!queue.isEmpty()) {
            System.out.println(parents.size() + " instructors found so far");
            if (parents.size() > maxNodes) {
                return parents;
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
        return parents;
    }

    // TODO: Find Shortest Path
}


