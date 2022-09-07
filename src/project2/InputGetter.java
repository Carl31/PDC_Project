/*
 * Static helper class for getting valid user input
 */
package project2;

import java.util.Scanner;

/**
 *
 * @author carls
 */
public final class InputGetter {

    private static Scanner scan = new Scanner(System.in);

    public static String getAlphanumeric(String prompt) {
        System.out.println(prompt);
        boolean isValid = false;
        String username = "";

        while (!isValid) {

            String line = scan.nextLine();
            if (line.trim().equalsIgnoreCase("quit")) {
                System.out.println("Game ended.");
                System.exit(0);
            }
            if (isAlphanumeric(line)) {
                isValid = true;
                username = line.toLowerCase();
            } else {
                System.out.println("Invalid input. Please input again.");
            }
        }
        return username;
    }

    public static int getPosInt(String prompt, int limit) {
        boolean isValid = false;
        int input = 0;

        while (!isValid) {
            System.out.println(prompt);
            String line = scan.nextLine();
            if (line.trim().equalsIgnoreCase("quit")) {
                System.out.println("Game ended.");
                System.exit(0);
            }
            try {
                input = Integer.parseInt(line);
                
                if (input > 0 && input <= limit) {
                    isValid = true;
                }
                else {
                    System.out.println("Invalid input. Please input a positive integer within limit.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please input again.");
            }
        }
        return input;
    }

    public static String getYesOrNo(String prompt) {
        boolean isValid = false;
        String input = "";

        while (!isValid) {
            System.out.println(prompt);
            String line = scan.nextLine();
            if (line.trim().equalsIgnoreCase("quit")) {
                System.out.println("Game ended.");
                System.exit(0);
            }

            if (line.equals("yes") || line.equals("no")) {
                isValid = true;
                input = line;
            } else {
                System.out.println("Invalid input. Please input again.");
            }
        }
        return input;
    }
    
    public static String getLanguage(String prompt) {
        boolean isValid = false;
        String input = "";

        while (!isValid) {
            System.out.println(prompt);
            String line = scan.nextLine();
            if (line.trim().equalsIgnoreCase("quit")) {
                System.out.println("Game ended.");
                System.exit(0);
            }

            if (line.equals("english") || line.equals("spanish") || line.equals("random")) {
                isValid = true;
                input = line;
            } else {
                System.out.println("Invalid input. Please input again.");
            }
        }
        return input;
    }

    private static boolean isAlphanumeric(String word) {
        return (word.matches("[a-zA-Z\u00f1 ]*"));
    }
}
