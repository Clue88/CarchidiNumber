import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONObject;

public class PCRParser {
    private final HashMap<String, String> codesToNames;

    public PCRParser() {
        codesToNames = new HashMap<>();
    }

    private String getJSONContent(URL url) {
        StringBuilder output = new StringBuilder();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Cookie", "ga=GA1.2.709796897.1656641157; csrftoken=cQVsFOKKiTWIasPu5P3feclUb8SIWzJTwBvZKRBVgAlUFWGmDhncpu6hGsoVHDzl; sessionid=jia3lyerlrsj21c5a715b74k49332lqv");
            if (con.getResponseCode() != 200) {
                return null;
            }
            Scanner in = new Scanner(con.getInputStream());
            while (in.hasNextLine()) {
                output.append(in.nextLine());
            }
        } catch (IOException e) {
            return null;
        }
        return output.toString();
    }

    /**
     * Finds all of the instructors that have ever taught the given course.
     * @param courseCode The course code of the given course.
     * @return A set of instructor codes.
     * @throws IllegalArgumentException if the course is not found
     */
    public Set<String> getCourseInstructors(String courseCode) {
        try {
            URL url = new URL("https://penncoursereview.com/api/review/course/"
                    + courseCode + "?format=json");
            String jsonContent = getJSONContent(url);
            if (jsonContent == null) {
                throw new IllegalArgumentException("course not found");
            }
            JSONObject json = new JSONObject(jsonContent);
            JSONObject instructors = json.getJSONObject("instructors");
            return instructors.keySet();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
}
