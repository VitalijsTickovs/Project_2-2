package org.group1.back_end.Camera;


import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualizer {

    CameraEndPoint endpoint;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;


    public static final int FPS = 30;
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
        canvasFrame.setCanvasSize(WIDTH, HEIGHT);
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
        frame.setLocation(0,0);
        frame.setVisible(true);

        while (true) {
            Mat mat = endpoint.paint();
            canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(mat));
        }
    }

    public static void main(String[] args) {
        try {
            CameraEndPoint endpoint = new CameraEndPoint();
            Visualizer visualizer = new Visualizer(endpoint);
            visualizer.display();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
