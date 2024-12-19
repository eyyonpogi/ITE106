import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Contact {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + "," + phoneNumber;
    }
}

public class PhoneBookSystem {
    static ArrayList<Contact> contacts = new ArrayList<>();
    static final String FILE_NAME = "phonebook.txt";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Phonebook System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();

        JButton addButton = new JButton("Add Contact");
        JButton deleteButton = new JButton("Delete Contact");
        JButton searchButton = new JButton("Search Contact");
        JButton updateButton = new JButton("Update Contact");
        JButton saveButton = new JButton("Save Contacts");
        JButton loadButton = new JButton("Load Contacts");

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(updateButton);
        panel.add(saveButton);
        panel.add(loadButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String phoneNumber = phoneField.getText().trim();
                if (name.isEmpty() || phoneNumber.isEmpty()) {
                    outputArea.setText("Error: Name and phone number cannot be empty.");
                    return;
                }
                Contact contact = new Contact(name, phoneNumber);
                contacts.add(contact);
                outputArea.setText("Contact added:\n" + contact);
                nameField.setText("");
                phoneField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    outputArea.setText("Error: Name cannot be empty.");
                    return;
                }
                boolean removed = contacts.removeIf(contact -> contact.name.equalsIgnoreCase(name));
                if (removed) {
                    outputArea.setText("Contact deleted: " + name);
                } else {
                    outputArea.setText("Contact not found: " + name);
                }
                nameField.setText("");
                phoneField.setText("");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    outputArea.setText("Error: Name cannot be empty.");
                    return;
                }
                for (Contact contact : contacts) {
                    if (contact.name.equalsIgnoreCase(name)) {
                        outputArea.setText("Contact found:\n" + contact);
                        return;
                    }
                }
                outputArea.setText("Contact not found: " + name);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String phoneNumber = phoneField.getText().trim();
                if (name.isEmpty() || phoneNumber.isEmpty()) {
                    outputArea.setText("Error: Name and new phone number cannot be empty.");
                    return;
                }
                for (Contact contact : contacts) {
                    if (contact.name.equalsIgnoreCase(name)) {
                        contact.phoneNumber = phoneNumber;
                        outputArea.setText("Contact updated:\n" + contact);
                        return;
                    }
                }
                outputArea.setText("Contact not found: " + name);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
                    for (Contact contact : contacts) {
                        bw.write(contact.toString());
                        bw.newLine();
                    }
                    outputArea.setText("Contacts saved to " + FILE_NAME);
                } catch (IOException ex) {
                    outputArea.setText("Error saving contacts: " + ex.getMessage());
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    contacts.clear();
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 2) {
                            String name = parts[0];
                            String phoneNumber = parts[1];
                            contacts.add(new Contact(name, phoneNumber));
                        }
                    }
                    outputArea.setText("Contacts loaded from " + FILE_NAME);
                } catch (IOException ex) {
                    outputArea.setText("Error loading contacts: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
