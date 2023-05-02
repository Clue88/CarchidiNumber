import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BFS {
    private HashMap<String, Set<String>> graph;

    public HashMap<String, Set<String>> BFS(String profName, PCRParser pcr) {
        this.graph = new HashMap<>();

        String prof1code = pcr.getInstructorCode(profName);
        Set<String> prof1courses = pcr.getInstructorCourses(prof1code);
        graph.put(prof1code, new HashSet<>());

        // construct graph
        for (String p1course : prof1courses) {
            for (String p1 : pcr.getCourseInstructors(p1course)) {

                //add professor node if it doesn't already exist
                if (!graph.containsKey(p1)) {
                    graph.put(p1, new HashSet<>());
                }

                graph.get(prof1code).add(p1);
                graph.get(p1).add(prof1code);

            }
        }

        return graph;
    }


    public List<String> findShortestPath(String startProfName, String targetProfName, PCRParser pcr) {
        String startProf = pcr.getInstructorCode(startProfName);
        String targetProf = pcr.getInstructorCode(targetProfName);

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>(); //stores parent of a node as it is visited
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startProf);
        distances.put(startProf, 0);
        predecessors.put(startProf, null);

        while (!queue.isEmpty()) {
            String currProf = queue.poll();
            if (currProf.equals(targetProf)) {
                break;
            }
            if (visited.contains(currProf)) {
                continue;
            }
            visited.add(currProf);

            // update distances of neighbors
            for (String neighbor : graph.get(currProf)) {
                if (!visited.contains(neighbor)) {
                    int newDistance = distances.get(currProf) + 1;
                    if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        predecessors.put(neighbor, currProf);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        // construct path from start prof to target prof
        List<String> path = new ArrayList<>();
        if (!predecessors.containsKey(targetProf)) {
            return path;
        }
        String currProf = targetProf;
        while (currProf != null) {
            path.add(0, pcr.getInstructorName(currProf));
            currProf = predecessors.get(currProf);
        }
        return path;
    }






    //construct edge list file for zeno
    public void writeEdgeList(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String prof : graph.keySet()) {
            for (String neighbor : graph.get(prof)) {
                writer.write(prof + " " + neighbor + "\n");
            }
        }
        writer.close();
    }


}


