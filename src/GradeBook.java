import java.util.HashMap;
import java.util.List;

public class GradeBook {

    public HashMap<String,HashMap<String,Integer>>  coursesAndWeightedAssignments =
            new HashMap<>();

    public GradeBook(List<String> courses, HashMap<String, Integer> assignmentsAndWeights){
        for(String course : courses){
            coursesAndWeightedAssignments.put(course,assignmentsAndWeights);
        }

        System.out.println(coursesAndWeightedAssignments);
    }
}
