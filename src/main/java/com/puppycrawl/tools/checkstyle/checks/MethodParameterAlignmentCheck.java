package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class MethodParameterAlignmentCheck extends AbstractCheck {

    public static final String MSG_PARAM_ALIGNMENT = "method.params.alignment";

    private static DetailAST getFirstChild(DetailAST ast, int type) {
        DetailAST c = ast.getFirstChild();
        while (c != null && c.getType() != type) {
            c = c.getNextSibling();
        }
        return requireNonNull(c);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parameters = getFirstChild(ast, TokenTypes.PARAMETERS);

        List<DetailAST> paramsAndAnnotations = MethodParameterLinesCheck.stream(parameters.getFirstChild())
                .filter(it -> it.getType() == TokenTypes.ANNOTATION ||
                        it.getType() == TokenTypes.PARAMETER_DEF)
                .collect(Collectors.toList());

    }
}
