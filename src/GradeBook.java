import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GradeBook {
    /*
     * courses = a list of courses that can be taken by a student
     * assignmentsAndWeights = a Hashmap of assignments with
     *                         there weight, i.e. "Quizzes" : 120
     *gradeBook = where the current courses and grades for those courses is stored
     * name = name of the student
     */

    public List<String> courses;
    public HashMap <String, Integer> assignmentsAndWeights;
    public HashMap<String,Integer> gradeBook = new HashMap<>();
    public String name;

    /*
     * A Hashmap of a String and a Hashmap<String, Integer>.
     * This holds a course, the assignments for that course,
     * and the weights for those assignments
     */
    public HashMap<String,HashMap<String,Integer>>  coursesAndWeightedAssignments =
            new HashMap<>();

    public GradeBook(List<String> courses, HashMap<String, Integer> assignmentsAndWeights){
        /**
         * builds coursesAndWeightedAssignments
         *
         * @param courses All the courses the student could have taken
         * @param assignmentsAndWeights The possible assignments for each course and their weights
         */

        this.courses = courses;
        this.assignmentsAndWeights = assignmentsAndWeights;

        // loops through the list of courses that were passed in
        // and adds the course and the weights for assignments that go with it
        for(String course : courses) {
            coursesAndWeightedAssignments.put(course, assignmentsAndWeights);
        }
    }

    public void buildGradebook(){
        IOHelper ioHelper = new IOHelper();
        List<String> coursesTaken = ioHelper.inputCoursesTaken(courses);

        for (String course : coursesTaken){
            for (String assignment : assignmentsAndWeights.keySet()) {
                boolean inputFlag = false;
                int ctr = 1;
                int sum = 0;
                double average = 0;
                while(!inputFlag){
                    inputFlag = true;
                    System.out.println("When you are done entering grades for a category" +
                            " please input -1");
                    System.out.printf("for %s what did you get for %s %d\n",course,assignment,ctr);
                    Scanner scanner = new Scanner(System.in);
                    try {
                        int input = Integer.parseInt(scanner.nextLine());
                        if (input != -1) {
                            inputFlag = false;
                            if (input <= 100 && input >= 0) {
                                sum+=input;
                                average = sum/(double)ctr;
                                System.out.printf("Average: %.2f\n\n",average);
                                ctr++;

                            } else if (input > 100 || input < 0) {
                                System.out.println("That is not valid input, please enter an unweighted grade.");
                            }
                        }else{
                            double weightedGrade = (average/100) * assignmentsAndWeights.get(assignment);
                            System.out.println(weightedGrade);
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Please only input a Number between 100 and 0!\n\n");
                    }

                }
            }
        }
    }

    @Override
    public String toString() {
        return coursesAndWeightedAssignments.toString();
    }
}
