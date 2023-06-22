package org.group1.back_end.Camera;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Mat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualizer implements Runnable {

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
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        // Create a CanvasFrame for displaying video.
        CanvasFrame canvasFrame = new CanvasFrame("Web Cam", 1);
        canvasFrame.setCanvasSize(WIDTH/2, HEIGHT/2);
        canvasFrame.setVisible(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel.add(canvasFrame.getCanvas(), c);

        // Creating a button and text field
        JButton button = new JButton("Add User");
        JTextField textField = new JTextField(20);
        Dimension textDimension = textField.getPreferredSize();
        button.setPreferredSize(new Dimension(textDimension.width, button.getPreferredSize().height));
        c.gridy = 1;
        panel.add(textField, c);
        c.gridy = 2;
        panel.add(button, c);

        frame.setResizable(false);
        frame.setUndecorated(true);

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

        frame.pack();
        Color general = new Color(42, 97, 117);
        panel.setBackground(general);
        frame.setBackground(general);
        canvasFrame.setBackground(general);
        button.setForeground(Color.WHITE);
        button.setBackground(general);
        textField.setBackground(general);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        // set the border of this component
        textField.setBorder(border);
        frame.setLocation(250, 200);
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

    @Override
    public void run() {
        try {
            this.display();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
