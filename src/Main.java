import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
    /**
     * This class is responsible for passing values
     * into GradeBook, and starting the decision process
     * for the user on weather they want to read data, or input it
     */

    public static void main(String[] args) throws IOException {

        // The types of assignments and the weights for each type of assignment
        // Out of a 700 point scale
        final HashMap<String, Integer> ASSIGNMENTSANDWEIGHTS = new HashMap<>();
        ASSIGNMENTSANDWEIGHTS.put("Quiz",225);
        ASSIGNMENTSANDWEIGHTS.put("Exam",100);
        ASSIGNMENTSANDWEIGHTS.put("Homework",250);
        ASSIGNMENTSANDWEIGHTS.put("Project",125);

        // the List of Courses that are available for a person to input or see grades for.
        final List<String> COURSES = new ArrayList<>();
        COURSES.add("COP 2006");
        COURSES.add("COP 1500");
        COURSES.add("MAC 2312");
        COURSES.add("PHY 2048");
        COURSES.add("PHY 2048L");
        COURSES.add("ENC 1102");
        COURSES.add("GLY 1010C");

        GradeBook gradeBook = new GradeBook(COURSES,ASSIGNMENTSANDWEIGHTS);
        // this is the method in GradeBook that starts the whole process 
        gradeBook.makeDecision();

    }
}
