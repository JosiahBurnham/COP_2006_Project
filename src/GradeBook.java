import java.util.HashMap;
import java.util.List;

public class GradeBook {
    /**
     * courses = a list of courses that can be taken by a student
     * assignmentsAndWeights = a Hashmap of assignments with
     *                         there weight, i.e. "Quizzes" : 120
     *gradeBook = where the current courses and grades for those courses is stored
     * name = name of the student
     */

    public List<String> courses;
    public HashMap <String, Integer> assignmentsAndWeights;
    public HashMap<String,String> gradeBook = new HashMap<>();
    public String name;

    /**
     * A Hashmap of a String and a Hashmap<String, Integer>.
     * This holds a course, the assignments for that course,
     * and the weights for those assignments
     */
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
