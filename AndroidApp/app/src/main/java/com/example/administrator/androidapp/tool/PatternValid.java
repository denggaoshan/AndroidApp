package com.example.administrator.androidapp.tool;

public class PatternValid {

    private static int username_maxLen = 15;
    private static int password_maxLen = 15;
    private static int username_minLen = 5;
    private static int password_minLen = 5;

    public static String validUsername(String name) {
        if (!name.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            return "NO";
        }
        if (name.length() > username_maxLen || name.length() < username_minLen) {
            return "NO";
        }
        return "OK";
    }

    public static String validPassword(String name) {
        if (!name.matches("[a-zA-Z0-9_]*")) {
            return "NO";
        }
        if (name.length() > username_maxLen || name.length() < username_minLen) {
            return "NO";
        }
        return "OK";
    }


    public static String validEmail(String name) {
        return "OK";
    }

    public static String validPhone(String name) {
        return "OK";
    }



}