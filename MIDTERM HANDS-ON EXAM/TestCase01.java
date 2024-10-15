import java.util.Scanner;

public class TestCase01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of students: ");
        int numberOfStudents = scanner.nextInt();
        scanner.nextLine();
        
        String[] studentNames = new String[numberOfStudents];
        double[] averageScores = new double[numberOfStudents];
        char[] letterGrades = new char[numberOfStudents];
        
        for (int j = 0; j < numberOfStudents; j++) {
            System.out.print("Enter the student's name: ");
            studentNames[j] = scanner.nextLine();
            
            System.out.print("Enter the number of assignments: ");
            int numberOfAssignments = scanner.nextInt();
            scanner.nextLine();
            
            double sum = 0;
            
            for (int i = 0; i < numberOfAssignments; i++) {
                System.out.print("Enter the type of assignment " + (i + 1) + " (e.g., Quiz, Homework, Exam): ");
                String assignmentType = scanner.nextLine();
                System.out.print("Enter the score for " + assignmentType + ": ");
                double score = scanner.nextDouble();
                sum += score;
                scanner.nextLine(); 
            }
            double average = sum / numberOfAssignments;
            char letterGrade;
            if (average >= 90) {
                letterGrade = 'A';
            } else if (average >= 80) {
                letterGrade = 'B';
            } else if (average >= 70) {
                letterGrade = 'C';
            } else if (average >= 60) {
                letterGrade = 'D';
            } else {
                letterGrade = 'F';
            }
            
            averageScores[j] = average;
            letterGrades[j] = letterGrade;
        }
        
        System.out.println("\nAll Students and Their Grades:");
        for (int j = 0; j < numberOfStudents; j++) {
            System.out.printf("Student Name: %s, Average Score: %.2f, Letter Grade: %c%n", 
                studentNames[j], averageScores[j], letterGrades[j]);
        }
        
        scanner.close();
    }
}
