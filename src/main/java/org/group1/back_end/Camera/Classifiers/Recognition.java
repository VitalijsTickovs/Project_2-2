package org.group1.back_end.Camera.Classifiers;

import org.opencv.core.Mat;

public interface Recognition {

    /**
     * Paints the image with the color of the classifier and the bounds and/or name of the detected object/person
     * @param img, raw image
     * @return painted image
     */
    public Mat paint(Mat img);

    /**
     * Checks if a certain object or person is detected
     * @param img, raw image
     * @return true if detected, false otherwise
     */
    public boolean authenticate(Mat img);

    /**
     * Returns the name of the detected object or person
     * @param img, raw image
     * @return name of the detected object or person
     */
    public String detect(Mat img);
}
