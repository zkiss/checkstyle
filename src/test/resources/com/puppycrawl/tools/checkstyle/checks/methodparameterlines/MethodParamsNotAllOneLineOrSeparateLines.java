package com.puppycrawl.tools.checkstyle.checks.methodparameterlines;

public class MethodParamsNotAllOneLineOrSeparateLines {
    public void f1(int p1, int p2, int p3,
                   int p4,
                   Object... p5) {}
}
