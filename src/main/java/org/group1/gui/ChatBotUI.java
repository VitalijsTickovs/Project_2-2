package org.group1.gui;

import org.group1.response.Skill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotUI {

    static Skill sk;

    static {
        try {
            sk = new Skill();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChatBotUI() throws Exception {

    }

    public static void main(String[] args) {

        System.exit(0);
        main(args);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("ChatBot");
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        Container content = frame.getContentPane();
        content.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Ask a question:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        content.add(label, constraints);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 1;
        content.add(textField, constraints);

        JButton button = new JButton("Submit");
        constraints.gridx = 0;
        constraints.gridy = 2;
        content.add(button, constraints);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(textArea, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = textField.getText();
                String answer = chatBotResponse(question);
                textArea.setText(answer);
            }
        });

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static String chatBotResponse(String question) {

        return sk.getSkill(question);
    }
}
