package com.puppycrawl.tools.checkstyle.checks.methodparameteralignment;

public class NoMethodIssues {

    public static void f1(int p1, int p2, int p3,
                          int p4, int p5,
                          int p6) {}

    static void f2(int p1,
                   int p2,
                   int p3,
                   int p4) {}

    private static void f3(
            int p1,
            int p2,
            int p3,
            int p4
    ) {}

}
