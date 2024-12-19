// Import necessary Java libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

// Employee class to represent individual employee details
class Employee {
    String id; // Employee ID
    String name; // Employee name
    double hourlyRate; // Hourly rate of the employee
    double hoursWorked; // Hours worked by the employee

    // Constructor to initialize Employee details
    public Employee(String id, String name, double hourlyRate, double hoursWorked) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    // Calculate gross pay (hourly rate * hours worked)
    public double getGrossPay() {
        return hourlyRate * hoursWorked;
    }

    // Calculate deductions (10% of gross pay)
    public double getDeductions() {
        return getGrossPay() * 0.1;
    }

    // Calculate net pay (gross pay - deductions)
    public double getNetPay() {
        return getGrossPay() - getDeductions();
    }

    // Format employee details as a string for display and saving to file
    @Override
    public String toString() {
        return id + "," + name + "," + hourlyRate + "," + hoursWorked + "," + getGrossPay() + "," + getNetPay();
    }
}

// Main PayrollSystem class to manage the GUI and operations
public class PayrollSystem {
    static ArrayList<Employee> employees = new ArrayList<>(); // List to store employees
    static final String FILE_NAME = "payroll.txt"; // File name for saving/loading records

    public static void main(String[] args) {
        // Create the main GUI frame
        JFrame frame = new JFrame("Payroll System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create a panel for user inputs and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5)); // Grid layout for input fields and buttons

        // Input fields for employee details
        JLabel idLabel = new JLabel("Employee ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel rateLabel = new JLabel("Hourly Rate:");
        JTextField rateField = new JTextField();
        JLabel hoursLabel = new JLabel("Hours Worked:");
        JTextField hoursField = new JTextField();

        // Buttons for various operations
        JButton addButton = new JButton("Add Employee");
        JButton calculateButton = new JButton("Calculate Pay");
        JButton saveButton = new JButton("Save Records");
        JButton loadButton = new JButton("Load Records");

        // Output area to display messages and results
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add input fields and buttons to the panel
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(rateLabel);
        panel.add(rateField);
        panel.add(hoursLabel);
        panel.add(hoursField);
        panel.add(addButton);
        panel.add(calculateButton);
        panel.add(saveButton);
        panel.add(loadButton);

        // Add panel and output area to the frame
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add employee to the list when the "Add Employee" button is clicked
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                try {
                    double hourlyRate = Double.parseDouble(rateField.getText().trim());
                    double hoursWorked = Double.parseDouble(hoursField.getText().trim());
                    Employee emp = new Employee(id, name, hourlyRate, hoursWorked);
                    employees.add(emp);
                    outputArea.setText("Employee added:\n" + emp);
                    idField.setText("");
                    nameField.setText("");
                    rateField.setText("");
                    hoursField.setText("");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Error: Please enter valid numbers for rate and hours.");
                }
            }
        });

        // Calculate payroll details and display when the "Calculate Pay" button is clicked
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (employees.isEmpty()) {
                    outputArea.setText("No employees to calculate.");
                    return;
                }
                StringBuilder sb = new StringBuilder("Payroll:\n");
                for (Employee emp : employees) {
                    sb.append(emp).append("\n");
                }
                outputArea.setText(sb.toString());
            }
        });

        // Save employee records to a file when the "Save Records" button is clicked
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
                    for (Employee emp : employees) {
                        bw.write(emp.toString());
                        bw.newLine();
                    }
                    outputArea.setText("Records saved to " + FILE_NAME);
                } catch (IOException ex) {
                    outputArea.setText("Error saving records: " + ex.getMessage());
                }
            }
        });

        // Load employee records from a file when the "Load Records" button is clicked
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    employees.clear(); // Clear current list of employees
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 4) {
                            String id = parts[0];
                            String name = parts[1];
                            double hourlyRate = Double.parseDouble(parts[2]);
                            double hoursWorked = Double.parseDouble(parts[3]);
                            employees.add(new Employee(id, name, hourlyRate, hoursWorked));
                        }
                    }
                    outputArea.setText("Records loaded from " + FILE_NAME);
                } catch (IOException ex) {
                    outputArea.setText("Error loading records: " + ex.getMessage());
                }
            }
        });

        // Set the frame visible to display the GUI
        frame.setVisible(true);
    }
}
