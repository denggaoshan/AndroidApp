package com.example.administrator.androidapp;

public class PatternValid {

    private static int username_maxLen = 15;
    private static int password_maxLen = 15;
    private static int username_minLen = 5;
    private static int password_minLen = 5;

    public static String validUsername(String name) {
        if (!name.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            return "非法字符";
        }
        if (name.length() > username_maxLen || name.length() < username_minLen) {
            return "长度不合法";
        }
        return "OK";
    }

    public static String validPassword(String name) {
        if (!name.matches("[a-zA-Z0-9_]*")) {
            return "非法字符";
        }
        if (name.length() > username_maxLen || name.length() < username_minLen) {
            return "长度不合法";
        }
        return "OK";
    }
}