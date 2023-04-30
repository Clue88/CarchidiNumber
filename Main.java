import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> oneStepInstructors = new HashSet<>();

        Set<String> carchidiCourses = PCRParser.getInstructorCourses("592");
        if (carchidiCourses == null) {
            return;
        }
        for (String courseCode : carchidiCourses) {
            Set<String> instructors = PCRParser.getCourseInstructors(courseCode);
            if (instructors == null) {
                continue;
            }
            oneStepInstructors.addAll(instructors);
        }
        System.out.println(oneStepInstructors);
        System.out.println(oneStepInstructors.size());

        for (String instructorCode : oneStepInstructors) {
            System.out.println(PCRParser.getInstructorName(instructorCode));
        }
    }
}
