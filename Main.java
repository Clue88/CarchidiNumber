import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        PCRParser parser = new PCRParser();
        Set<String> oneStepInstructors = new HashSet<>();

        String carchidiCode = parser.getInstructorCode("Michael A. Carchidi");
        Set<String> carchidiCourses = parser.getInstructorCourses(carchidiCode);
        if (carchidiCourses == null) {
            return;
        }
        for (String courseCode : carchidiCourses) {
            Set<String> instructors = parser.getCourseInstructors(courseCode);
            if (instructors == null) {
                continue;
            }
            oneStepInstructors.addAll(instructors);
        }

        System.out.println(oneStepInstructors);
        System.out.println(oneStepInstructors.size());

        for (String instructorCode : oneStepInstructors) {
            System.out.println(parser.getInstructorName(instructorCode));
        }

        BFS bfsCarchidi = new BFS();
        System.out.println(bfsCarchidi.BFS("Michael A. Carchidi", parser));
        System.out.println(bfsCarchidi.findShortestPath("Abraham Wyner", "Vijay Kumar", parser));
        bfsCarchidi.writeEdgeList("edgeList");
    }
}
