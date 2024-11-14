import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of students: ");
        int StudentNum = sc.nextInt();
        sc.nextLine();

        String[] studentNames = new String[StudentNum];
        int[] assignmentNums = new int[StudentNum];
        double[][] scores = new double[StudentNum][];

        for (int i = 0; i < StudentNum; i++) {
            System.out.print("Enter the name of student " + (i + 1) + ": ");
            studentNames[i] = sc.nextLine();

            System.out.print("Enter the number of assignments for " + studentNames[i] + ": ");
            assignmentNums[i] = sc.nextInt();
            scores[i] = new double[assignmentNums[i]]; 

            for (int j = 0; j < assignmentNums[i]; j++) {
                System.out.print("Enter score for assignment " + (j + 1) + ": ");
                scores[i][j] = sc.nextDouble();
            }
            sc.nextLine(); 
        }
      
        for (int i = 0; i < StudentNum; i++) {
            double totalScore = 0;

            for (int j = 0; j < assignmentNums[i]; j++) {
                totalScore += scores[i][j];
            }

            double average = totalScore / assignmentNums[i];
            char grade;

            if (average >= 90) {
                grade = 'A';
            } else if (average >= 80) {
                grade = 'B';
            } else if (average >= 70) {
                grade = 'C';
            } else if (average >= 60) {
                grade = 'D';
            } else {
                grade = 'F';
            }

            System.out.println("\n" + studentNames[i] + " - Average: " + String.format("%.2f", average) + " - Grade: " + grade);
        }

        sc.close();
    }
}
