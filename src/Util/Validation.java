package Util;

public class Validation {
    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input");
            return false;
        }
    }

    public static boolean isDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input");
            return false;
        }
    }
}
