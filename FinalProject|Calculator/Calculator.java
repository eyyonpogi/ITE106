import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textDisplay;
    private JTextArea historyDisplay;
    private double input1, input2, resultingValue;
    private String operator;
    private boolean done;

    public Calculator() {
        setTitle("Kalkyuleytor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        textDisplay = new JTextField();
        textDisplay.setEditable(false);
        textDisplay.setFont(new Font("Arial", Font.BOLD, 32));
        textDisplay.setHorizontalAlignment(JTextField.RIGHT);
        textDisplay.setBackground(new Color(28, 28, 28));
        textDisplay.setForeground(Color.WHITE);
        textDisplay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(textDisplay, BorderLayout.NORTH);

        JPanel buttonGroup = new JPanel();
        buttonGroup.setBackground(new Color(40, 40, 40));
        buttonGroup.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String button : buttons) {
            JButton b = new JButton(button);
            b.setFont(new Font("Arial", Font.BOLD, 24));
            b.setBackground(new Color(60, 60, 60));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 3));
            b.addActionListener(this);
            buttonGroup.add(b);
        }

        add(buttonGroup, BorderLayout.CENTER);

        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(new Color(28, 28, 28));

        historyDisplay = new JTextArea(10, 30);
        historyDisplay.setEditable(false);
        historyDisplay.setFont(new Font("Courier New", Font.PLAIN, 14));
        historyDisplay.setBackground(new Color(28, 28, 28));
        historyDisplay.setForeground(Color.LIGHT_GRAY);
        historyDisplay.setText("History:\n");

        JScrollPane scrollPane = new JScrollPane(historyDisplay);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        add(historyPanel, BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String calcuInput = e.getActionCommand();

        if (textDisplay.getText().length() <= 0 && calcuInput.equals("0")) {
            return;
        }

        if (calcuInput.equals("C")) {
            input1 = 0;
            input2 = 0;
            textDisplay.setText("");
        }
        else if (Character.isDigit(calcuInput.charAt(0)) || calcuInput.equals(".")) {
            if (done) {
                textDisplay.setText("");
                done = false;
            }

            if (calcuInput.equals(".") && textDisplay.getText().contains(".")) {
                return;
            }

            textDisplay.setText(textDisplay.getText() + calcuInput);
        }
        else if (calcuInput.equals("=")) {
            input2 = Double.parseDouble(textDisplay.getText());
            calculate();
            textDisplay.setText(String.valueOf(resultingValue));
            done = true;

            try {
                String history = historyRecorder(
                        String.valueOf(input1) + " " +
                        operator + " " +
                        String.valueOf(input2) + " = " +
                        String.valueOf(resultingValue)
                );
                historyDisplay.setText("History:\n" + history);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else {
            operator = calcuInput;
            input1 = Double.parseDouble(textDisplay.getText());
            textDisplay.setText("");
            done = false;
        }
    }

    public void calculate() {
        switch (operator) {
            case "+":
                resultingValue = input1 + input2;
                break;
            case "-":
                resultingValue = input1 - input2;
                break;
            case "*":
                resultingValue = input1 * input2;
                break;
            case "/":
                if (input2 != 0) {
                    resultingValue = input1 / input2;
                } else {
                    resultingValue = Double.NaN;
                }
                break;
        }
    }

    public String historyRecorder(String record) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("calculator_history.txt"));

        String line;
        StringBuilder history = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            history.append(line).append(System.lineSeparator());
        }

        history.append(record);

        BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_history.txt"));
        writer.write(history.toString());

        reader.close();
        writer.close();

        return history.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_history.txt"));
        writer.write("");
        writer.close();

        new Calculator();
    }
}
