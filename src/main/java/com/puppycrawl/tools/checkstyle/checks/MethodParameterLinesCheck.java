package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;
import java.util.OptionalInt;

import static java.util.Objects.requireNonNull;

public class MethodParameterLinesCheck extends AbstractCheck {

    public static final String MESSGE = "method.params.lines";

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

        OptionalInt firstParamLine = OptionalInt.empty();
        OptionalInt secondParamLine = OptionalInt.empty();
        OptionalInt lastLine = OptionalInt.empty();
        for(DetailAST c = parameters.getFirstChild(); c != null; c = c.getNextSibling()) {
            if(c.getType() != TokenTypes.PARAMETER_DEF) {
                continue;
            }

            if(!firstParamLine.isPresent()) {
                firstParamLine = OptionalInt.of(c.getLineNo());
                continue;
            }

            if(!secondParamLine.isPresent()) {
                secondParamLine = OptionalInt.of(c.getLineNo());
                lastLine = secondParamLine;
                continue;
            }

            if(firstParamLine.getAsInt() == secondParamLine.getAsInt() &&
                c.getLineNo() != secondParamLine.getAsInt()) {
                log(c.getLineNo(), c.getColumnNo(), MESSGE);
                break;
            } else if (firstParamLine.getAsInt() != secondParamLine.getAsInt() &&
                lastLine.getAsInt() == c.getLineNo()) {
                log(c.getLineNo(), c.getColumnNo(), MESSGE);
                break;
            }
        }
    }

    private static DetailAST getFirstChild(DetailAST ast, int type) {
        DetailAST c = ast.getFirstChild();
        while(c!=null && c.getType()!=type) {
            c = c.getNextSibling();
        }
        return requireNonNull(c);
    }
}
