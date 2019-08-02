package com.test.testmysql;

public class Parsing {
    /**
     * Returns Integer corresponding to s, or null if s is null.
     * @throws NumberFormatException if s is nonnull and
     * doesn't represent a valid integer
     */
    public static Integer parseInt(String s) {
        return (s == null) ? null : Integer.parseInt(s);
    }
    public static void main(String[] args) {
        System.out.println(parseInt("-1"));
        System.out.println(parseInt(null));
        System.out.println(parseInt("1"));
    }
}