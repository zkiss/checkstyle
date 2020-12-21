package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.OptionalInt;

import static java.util.Objects.requireNonNull;

public class MethodParameterAlignmentCheck extends AbstractCheck {

    public static final String MSG_PARAM_ALIGNMENT = "method.params.lines";

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

        OptionalInt firstParamColumn = OptionalInt.empty();
        for (DetailAST c = parameters.getFirstChild(); c != null; c = c.getNextSibling()) {
            if (c.getType() != TokenTypes.PARAMETER_DEF) {
                continue;
            }

            if (!firstParamColumn.isPresent()) {
                firstParamColumn = OptionalInt.of(c.getColumnNo());
                continue;
            }

            if (firstParamColumn.getAsInt() != c.getColumnNo()) {
                log(c.getLineNo(), c.getColumnNo(), MSG_PARAM_ALIGNMENT);
                break;
            }
        }
    }
}
