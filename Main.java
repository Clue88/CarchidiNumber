import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PCRParser parser = new PCRParser();
        Set<String> oneStepInstructors = new HashSet<>();

        Set<String> carchidiCourses = parser.getInstructorCourses("592");
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
    }
}
