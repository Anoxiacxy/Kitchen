package utils;

import java.util.Scanner;

public class CmdTools {

    private static Scanner scanner = new Scanner(System.in);

    private static Scanner getScanner() {
        return scanner;
    }

    private static void splitLine() {
        System.out.println("-------------------------------------");
    }


    public static void err(String fmt, Object... arg) {
        System.out.printf(fmt, arg);
    }

    public static void errL(String msg) {
        System.out.println(msg);
    }

    public static void info(String fmt, Object... args) {
        System.out.printf(fmt, args);
    }

    public static String inputString(String msg) {
        System.out.print(msg);
        return inputString();
    }

    public static String inputString(String msg, Enum<?>... range) {
        String[] tmp = new String[range.length];
        for (int idx = 0; idx < range.length; idx++) {
            tmp[idx] = range[idx].name();
        }
        return inputString(msg, tmp);
    }

    public static String inputString(String msg, String... range) {
        info("input type in [%s]:", String.join(",", range));
        while (true) {
            String input = inputString();
            for (String ele : range) {
                if (ele.equals(input)) {
                    return ele;
                }
            }
            err("input [%s] not in range [%s], retry\n", input, String.join(",", range));
        }
    }


    public static String inputString() {
        return scanner.nextLine();
    }

    public static boolean yesNoChoice(String msg) {
        while (true) {
            info("%s [y/n]? :",msg );
            String input = getScanner().nextLine().trim();
            if (input.equalsIgnoreCase("y") ||
                    input.equalsIgnoreCase("yes")
            ) {
                return true;
            } else if (input.equalsIgnoreCase("n") ||
                    input.equalsIgnoreCase("no")) {
                return false;
            } else {
                err("unknown input %s , retry\n", input);
            }
        }
    }
    public static boolean yesNoChoice() {
        return yesNoChoice("yes or no ");
    }

    public static double inputDouble(String msg){
        info(msg);
        while (true){
            try {
                return Double.parseDouble(scanner.nextLine());
            }catch (Exception ex){
                err("input double error %s ,retry \n",ex.getMessage());
            }
        }
    }

    public static int rangeChoice(int upper) {
        return rangeChoice(1, upper);
    }

    public static int rangeChoice(String msg, int lower, int upper) {
        info("%s [%d-%d] : ", msg, lower, upper);
        while (true) {
            String input = getScanner().nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= lower && choice <= upper) {
                    return choice;
                } else {
                    err("range error %d not in [%d,%d]\n", choice, lower, upper);
                }
            } catch (Exception ex) {
                err("input err %s , retry\n", ex.getMessage());
            }
        }
    }

    public static int rangeChoice(int lower, int upper) {
       return rangeChoice("input choice",lower,upper);
    }

    public static void showMenu(String title, String... items) {
        System.out.println(title);
        splitLine();
        for (String item : items) {
            System.out.println(item);
        }
        splitLine();
    }


}
