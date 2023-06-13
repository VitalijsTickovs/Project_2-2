package org.group1.back_end.Camera;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualizer {

    CameraEndPoint endpoint;

    public static final int FPS = 45;
    public Visualizer(CameraEndPoint endpoint) throws Exception {
        this.endpoint = endpoint;
    }

    public void display() throws Exception {
        // Creating a frame to display the webcam output
        JFrame frame = new JFrame("Web Cam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Creating a JPanel to hold all components
        JPanel panel = new JPanel();
        frame.add(panel);

        // Create a CanvasFrame for displaying video.
        CanvasFrame canvasFrame = new CanvasFrame("Web Cam", 1);
        canvasFrame.setCanvasSize(640, 480);
        canvasFrame.setVisible(false);
        panel.add(canvasFrame.getCanvas());

        // Creating a button and text field
        JButton button = new JButton("Add User");
        JTextField textField = new JTextField(20);
        panel.add(textField);
        panel.add(button);

        // Adding an ActionListener to the button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Storing the string from the text field
                String text = textField.getText();

                // Add user to face recognition system
                try {
                    endpoint.addUser(text);
                    JOptionPane.showMessageDialog(frame, "User added successfully.");
                    textField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }

            }
        });

        // Adjusting the layout
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.pack();
        frame.setVisible(true);

        while (true) {
            Mat mat = endpoint.paint();
            canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(mat));
            Thread.sleep(1000 / FPS);
        }
    }
}
