package by.bsuir.graphicseditor.controller;

import org.jetbrains.annotations.Contract;

public class MathController {
    @Contract(pure = true)
    public static int sign(int number) {
        int result = 0;
        if (number < 0) {
            result = -1;
        } else if (number > 0) {
            result = 1;
        }
        return result;
    }

    @Contract(pure = true)
    public static double sign(double number) {
        double result = 0;
        if (number < 0) {
            result = 1;
        } else if (number > 0) {
            result = 1;
        }
        return result;
    }
}
