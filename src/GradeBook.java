import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GradeBook {
    /**
     * GradeBook is responsible for calculating a weighted grade on a 700 point scale
     * , given the classes and the weights assigned to each type of assignment.
     * It Also is responsible for either reading to a CSV file for grades, or
     * it searches that CSV for a recorded grade under that name.
     *
     * TODO: Add the bonus feature (See how many points need for next letter grade)
     * TODO: ALL DUE BY TOMORROW @jjburnham0705@eagle.fgcu.edu
     */

    /*
     * courses = a list of courses that can be taken by a student
     * assignmentsAndWeights = a Hashmap of assignments with
     *                         there weight, i.e. "Quizzes" : 120
     *gradeBook = where the current courses and grades for those courses is stored
     * name = name of the student
     */

    private List<String> courses;
    private HashMap<String, Integer> assignmentsAndWeights;
    private HashMap<String, Double> gradeBook = new HashMap<>();
    private String name;


    public GradeBook(List<String> courses, HashMap<String, Integer> assignmentsAndWeights) {
        /**Sets starting values for the class
         * and asks for name of user
         *
         * @param courses All the courses the student could have taken
         * @param assignmentsAndWeights The possible assignments for each course and their weights
         */
        this.courses = courses;
        this.assignmentsAndWeights = assignmentsAndWeights;
        // makes a splash screen
        System.out.println("Welcome to The FGCU Grade book! \n");
        System.out.print("Here you can choose to either get grades you have already inputted.");
        System.out.println(" or, calculate new courses all together.");
        System.out.println("Just follow the prompts below and you will be all set to use this program.\n\n");
        // no matter what the User does this program always needs to askName()
        askName();
    }
    public void makeDecision() throws IOException {
        /**
         * This is responsible for handling the decision that the
         * User has to make (Whether to Read or Write to the CSV File)
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("If you want to input grades type INPUT, if you want to read grades type READ");
        String input = scanner.nextLine();
        if(input.toUpperCase().equals("INPUT")){
            buildGradeBook();
        }else if (input.toUpperCase().equals("READ")){
            readGradeBookCSV();
        }else{
            System.out.println("That is not valid Input.");
        }
    }

    private void readGradeBookCSV() {
        /**
         * This function is responsible for searching through the
         * csv file for a person's name and grades
         */

        List<List<String>> studentGrades = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/GradeBook.csv");

            try (BufferedReader resultReader = new BufferedReader(reader)) {
                String line;
                // read through the CSV file and split each field to put it to a List to Search and later Print
                while ((line = resultReader.readLine()) != null) {
                    List<String> currentLine = new ArrayList<>();
                    for (String field : line.split(",")) {
                        currentLine.add(field);
                    }
                    studentGrades.add(currentLine);
                }
            } catch (IOException ex) {
                System.out.println("File Error: " + ex.getStackTrace());
            }
            printGrades(searchCSV(studentGrades));
        }catch (FileNotFoundException e){
            System.out.println("You have to input the grades of at least one person in order to read grades");
        }
    }

    private void buildGradeBook() throws IOException {
        /**
         * Builds the GradeBook CSV
         * Really just a switchboard for most of the class
         */
        List<String> coursesTaken = inputCoursesTaken(courses);
        HashMap<String, HashMap<String, Double>> gradesToCalculate = InputGrades(coursesTaken);
        calculateGrade(gradesToCalculate);
        writeGradeBookCSV();
    }

    private HashMap<String, HashMap<String, Double>> InputGrades(List<String> coursesTaken) {
        /**
         * Allows for user to input a grade for each assignment category and average each
         * and inputs them into a HashMap that contains the course name
         * and the average for each assignment type that was inputted
         *
         * @param coursesTaken a List of type String returned by inputCoursesTaken()
         * @return averageCourseAssignmentGrades a hashmap that contains the course name,
         *         each type of assignment, and the averages for those assignments.
         */

        HashMap<String, HashMap<String, Double>> averageCourseAssignmentGrades = new HashMap<>();
        // Loop through each course the student has taken
        for (String course : coursesTaken) {
            /*assignmentAndAverage = Map that will contain an assignment
                                     and the calculated average for that assignment
             */
            HashMap<String, Double> assignmentAndAverage = new HashMap<>();
            for (String assignment : assignmentsAndWeights.keySet()) {
                /*
                    inputFlag = Flag to end infinite loop
                    ctr = counter for assignment number and average calculation
                    sum = the sum of all the inputted grades for the average calculation
                    average = average grade over all the inputted grades for that assignment
                 */
                boolean inputFlag = false;
                int ctr = 1;
                int sum = 0;
                double average = 0;
                // to get user input for each grade
                while (!inputFlag) {
                    inputFlag = true;
                    System.out.println("When you are done entering grades for a category" +
                            " please input -1");
                    System.out.printf("for %s what did you get for %s %d\n", course, assignment, ctr);
                    Scanner scanner = new Scanner(System.in);
                    // the try is to catch any input that is not a double
                    try {
                        // converting the String input to an double
                        double input = Double.parseDouble(scanner.nextLine());
                        // the exit condition for this loop is an input of -1
                        if (input != -1) {
                            inputFlag = false;
                            // making sure input is a grade between 100% and 0%
                            if (input <= 100.0 && input >= 0.0) {
                                sum += input;
                                average = sum / (double) ctr;
                                System.out.printf("Average: %.2f\n", average);
                                ctr++;
                                // input what not a normal grade percentage
                            } else if (input > 100.0 || input < 0.0) {
                                System.out.println("That is not valid input, please enter an unweighted grade.");
                            }
                        } else {
                            // assigning the average grade to each type of assignment
                            assignmentAndAverage.put(assignment, average);
                        }
                    } catch (NumberFormatException  e) {
                        System.out.println("Please only input a Number between 100 and 0!\n\n");
                    }
                }
            }
            // adding each type of assignment and its weighted grade to each course
            averageCourseAssignmentGrades.put(course, assignmentAndAverage);
        }
        return averageCourseAssignmentGrades;
    }

    private List<String> inputCoursesTaken(List<String> courses) {
        /**
         * The student decides what courses that they have taken
         *
         * @param courses The list of all the courses available to the student
         * @return coursesTaken a list of Strings of only the courses
         *                      being taken by the student
         */

        List<String> coursesTaken = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        System.out.println("What Classes are you taking?");
        System.out.println("The Courses we offer are:");
        //to display all the available courses to the student
        for (String course : courses) {
            System.out.print(course + ", ");
        }
        System.out.println("When you are done entering classes please input QUIT");
        boolean inputFlag = false;
        // the only way to set the inputFlag = true is if the user enters QUIT
        while (!inputFlag) {
            if(coursesTaken.size()<=courses.size()) {
                String input = scan.nextLine();
                // checks for exit condition
                if (input.toUpperCase().equals("QUIT")) {
                    inputFlag = true;
                } else {
                    // checks to see if the user input is in the List of courses
                    if (courses.contains(input.toUpperCase())) {
                        coursesTaken.add(input.toUpperCase());
                        inputFlag = false;
                    } else {
                        // if input not in courses tell them it was not valid
                        System.out.println("That input was not valid");
                        inputFlag = false;
                    }
                }
            }else{
                System.out.println("you have reached the max number of classes you can have\n");
                inputFlag = true;
            }
        }
        return coursesTaken;
    }

    private void  calculateGrade(HashMap<String, HashMap<String, Double>> coursesToCalculate) {
        /**
         * Calculates the wighted grade out of a 700 point scale
         * and then adds the courses final grade to the gradeBook Hashmap
         *
         * @param coursesToCalculate the courses that the student has taken along with their
         *                           average score for each assignment type
         */

        for (String key : coursesToCalculate.keySet()) {
           // if the user accidentally presses enter to many times it will throw a NullPointerException
            try {
                // weightedSum = the sum of all the avaerage grades multiplied by their individual weight
                double weightedSum = 0.0;
                for (String assignment : assignmentsAndWeights.keySet()) {
                    // calculating the weighted grade of each assignment type
                    double grade = ((coursesToCalculate.get(key).get(assignment) / 100)
                            * assignmentsAndWeights.get(assignment));
                    weightedSum += grade;
                }
                // calculating the final weighted grade
                double finalGrade = ((weightedSum / 700) * 100);
                gradeBook.put(key, finalGrade);
            }catch (NullPointerException e){
                gradeBook.put(key, 0.0);
            }
        }

    }

    private void writeGradeBookCSV() throws IOException {
        /**
         * Reads User Data from the HashMap gradeBook
         * and then writes it to a csv in a specific order
         *
         * @throws IOException this is just a basic file handling step
         */

        File csvFile = new File("src/GradeBook.csv");
        if (!csvFile.exists()) {
            csvFile.createNewFile();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true));
            writer.write(name);
            for(String course:gradeBook.keySet()) {
                String decimalGrade = String.format("%.2f", gradeBook.get(course));
                writer.write("," + course + "," + decimalGrade + "," + determineLetterGrade(gradeBook.get(course)));
            }
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot Write to the File: Permission Issue:" + e.getStackTrace());
        }
    }

    private List<String> searchCSV(List<List<String>> csvInfo){
        /**
         * Search the CSV for a specific name and return a List
         * for response
         *
         * @param csvInfo is a the List of Lists that contains all
         *                of the Users in the CSV file that contains grades
         * @return coursesAndGrades A String List that Contains either the Students Grades
         *          with out the name of the student, or a error message that they do not have
         *          a recorded grade
         */
        List<String> coursesAndGrades = new ArrayList<>();
        for(List<String> list : csvInfo){
            if (list.contains(name)){
                for (String index: list){
                    // The Name of the Student should not be in the String List that gets returned
                    if(!index.equals(name)){
                        coursesAndGrades.add(index);
                    }
                }
                // if they found a record return that
                return coursesAndGrades;
            }
        }
        // if they did not find a record return error message
        return List.of("We do not have you on our records.");
    }


    private char determineLetterGrade(double grade){
        /**
         * This program Determines what Letter Grade A student got in their course
         *
         * @param grade the decimal grade that was calculated
         * @return letterGrade the letter grade that the student got
         */
        char letterGrade = 'F';

        if(grade >= 90){
            letterGrade = 'A';
        }else if( grade >= 80){
            letterGrade = 'B';
        }else if( grade >= 70){
            letterGrade = 'C';
        }else if( grade >= 60){
            letterGrade = 'D';
        }
        return letterGrade;
    }

    private void askName(){
        /**
         * asks the user their name and formats it
         * to be stored in the CSV to be able to more easily
         * find that name later, and set it to the name field
         */
        Scanner scan = new Scanner(System.in);
        System.out.println("What is your name?");
        String input = scan.nextLine();
        name = input.toUpperCase().replaceAll(" ","").strip();
    }
   private void printGrades(List<String> grades){
       /**
        * This prints the grades that were found in the CSV
        * under the User's name
        *
        * @param grades the List of grades per course for a specific student
        */
       if(grades.size() > 1){
            System.out.println("You Achieved a: ");
            // you have to increment by three because of the way a csv is graded
            for(int i=0;i< (grades.size());i+=3){
                    String output = String.format("%s %s in %s,", grades.get(i+1), grades.get(i+2),
                            grades.get(i));
                    System.out.print(output);
            }
        }else{
           // print error message for no file found
           String noFileFound = String.format("%s", grades).replace("[","").replace("]","");
           System.out.println(noFileFound);
       }
   }

}
