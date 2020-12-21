package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.jupiter.api.Test;

class MethodParameterAlignmentCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/methodparameteralignment";
    }

    @Test
    void testNoMethodIssues() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodParameterAlignmentCheck.class);

        final String[] expected = {};

        verify(checkConfig, getPath("NoMethodIssues.java"), expected);
    }

    @Test
    void testBadMethodParamAlignments() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodParameterAlignmentCheck.class);

        final String[] expected = {
                "4:5: " + getCheckMessage(MethodParameterAlignmentCheck.MSG_PARAM_ALIGNMENT),
                "8:5: " + getCheckMessage(MethodParameterAlignmentCheck.MSG_PARAM_ALIGNMENT)
        };

        verify(checkConfig, getPath("BadMethodParamAlignments.java"), expected);
    }
}