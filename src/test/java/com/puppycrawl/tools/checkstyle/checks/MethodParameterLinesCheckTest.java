package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck;
import org.junit.jupiter.api.Test;

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_ASSIGN;
import static org.junit.jupiter.api.Assertions.*;

public class MethodParameterLinesCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/methodparameterlines";
    }

    @Test
    void testNoMethodIssues() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodParameterLinesCheck.class);

        final String[] expected = {};

        verify(checkConfig, getPath("NoMethodIssues.java"), expected);
    }

    @Test
    void testNoConstructorIssues() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodParameterLinesCheck.class);

        final String[] expected = {};

        verify(checkConfig, getPath("NoConstructorIssues.java"), expected);
    }

    @Test
    void testMethodParamsNotAllOneLineOrSeparateLines() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodParameterLinesCheck.class);

        final String[] expected = {
                "5:20: " + getCheckMessage(MethodParameterLinesCheck.MESSGE)
        };

        verify(checkConfig, getPath("MethodParamsNotAllOneLineOrSeparateLines.java"), expected);
    }

    @Test
    void testConstructorParamsNotAllOneLineOrSeparateLines() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodParameterLinesCheck.class);

        final String[] expected = {
                "5:51: " + getCheckMessage(MethodParameterLinesCheck.MESSGE)
        };

        verify(checkConfig, getPath("ConstructorParamsNotAllOneLineOrSeparateLines.java"), expected);
    }
}