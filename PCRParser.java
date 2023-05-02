import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class PCRParser {
    private final HashMap<String, String> codesToNames;
    private final HashMap<String, Set<String>> coursesToInstructors;

    public PCRParser() {
        codesToNames = new HashMap<>();
        coursesToInstructors = new HashMap<>();
    }

    private String getJSONContent(URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Cookie", "ga=GA1.2.709796897.1656641157; csrftoken=cQVsFOKKiTWIasPu5P3feclUb8SIWzJTwBvZKRBVgAlUFWGmDhncpu6hGsoVHDzl; sessionid=jia3lyerlrsj21c5a715b74k49332lqv");
            if (con.getResponseCode() != 200) {
                return null;
            }
            Scanner in = new Scanner(con.getInputStream());
            if (in.hasNextLine()) {
                return in.nextLine();
            }
        } catch (IOException e) {
            return null;
        }
        return "";
    }

    /**
     * Finds all of the instructors that have ever taught the given course.
     * @param courseCode The course code of the given course.
     * @return A set of instructor codes.
     * @throws IllegalArgumentException if the course is not found
     */
    public Set<String> getCourseInstructors(String courseCode) {
        if (coursesToInstructors.containsKey(courseCode)) {
            return coursesToInstructors.get(courseCode);
        }
        try {
            URL url = new URL("https://penncoursereview.com/api/review/course/"
                    + courseCode + "?format=json");
            String jsonContent = getJSONContent(url);
            if (jsonContent == null) {
                throw new IllegalArgumentException("course not found");
            }
            JSONObject json = new JSONObject(jsonContent);
            JSONObject instructors = json.getJSONObject("instructors");
            Set<String> keySet = instructors.keySet();
            if (!coursesToInstructors.containsKey(courseCode)) {
                coursesToInstructors.put(courseCode, keySet);
            }
            return keySet;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds all of the courses that the given instructor has ever taught.
     * @param instructorCode The instructor code of the given instructor.
     * @return A set of course codes.
     * @throws IllegalArgumentException if the instructor is not found
     */
    public Set<String> getInstructorCourses(String instructorCode) {
        try {
            URL url = new URL("https://penncoursereview.com/api/review/instructor/"
                    + instructorCode + "?format=json");
            String jsonContent = getJSONContent(url);
            if (jsonContent == null) {
                throw new IllegalArgumentException("instructor not found");
            }
            JSONObject json = new JSONObject(jsonContent);
            if (!codesToNames.containsKey(instructorCode)) {
                codesToNames.put(instructorCode, json.getString("name"));
            }
            JSONObject instructors = json.getJSONObject("courses");
            return instructors.keySet();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the name of the instructor associated with the given code.
     * @param instructorCode The code to search.
     * @return The name of the instructor.
     * @throws IllegalArgumentException if the instructor is not found
     */
    public String getInstructorName(String instructorCode) {
        if (codesToNames.containsKey(instructorCode)) {
            return codesToNames.get(instructorCode);
        }
        try {
            URL url = new URL("https://penncoursereview.com/api/review/instructor/"
                    + instructorCode + "?format=json");
            String jsonContent = getJSONContent(url);
            if (jsonContent == null) {
                throw new IllegalArgumentException("instructor not found");
            }
            JSONObject json = new JSONObject(jsonContent);
            return json.getString("name");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInstructorCode(String instructor) {
        try {
            File jsonFile = new File("data/autocomplete.json");
            Scanner scanner = new Scanner(jsonFile);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String jsonContent = scanner.nextLine();
            JSONObject json = new JSONObject(jsonContent);
            JSONArray instructors = json.getJSONArray("instructors");
            for (Object instructorObj : instructors) {
                JSONObject instructorDetail = (JSONObject) instructorObj;
                if (instructorDetail.getString("title").equals(instructor)) {
                    String[] urlParts = instructorDetail.getString("url").split("/");
                    return urlParts[urlParts.length - 1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
