import java.util.HashMap;
import java.util.List;

public class GradeBook {

    public List<String> courses;
    public HashMap <String, Integer> assignmentsAndWeights;
    public HashMap<String,String> gradeBook = new HashMap<>();
    public String name;

    // The Hashmap that contains all the courses
    // and all the weights for each of those assignments
    public HashMap<String,HashMap<String,Integer>>  coursesAndWeightedAssignments =
            new HashMap<>();

    public GradeBook(List<String> courses, HashMap<String, Integer> assignmentsAndWeights){

        this.courses = courses;
        this.assignmentsAndWeights = assignmentsAndWeights;

        // loops through the list of courses that were passed in
        // and adds the course and the weights for assignments that go with it
        for(String course : courses) {
            coursesAndWeightedAssignments.put(course, assignmentsAndWeights);
        }
    }

    @Override
    public String toString() {
        return coursesAndWeightedAssignments.toString();
    }
}
