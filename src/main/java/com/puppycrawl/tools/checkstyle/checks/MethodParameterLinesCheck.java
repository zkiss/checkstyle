package com.puppycrawl.tools.checkstyle.checks;

import com.google.common.collect.Streams;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@StatelessCheck
public class MethodParameterLinesCheck extends AbstractCheck {

    public static final String MSG_PARAMS_LINES = "method.params.lines";
    private boolean allowSingleLine = true;

    private static DetailAST getFirstChild(DetailAST ast, int type) {
        DetailAST c = ast.getFirstChild();
        while (c != null && c.getType() != type) {
            c = c.getNextSibling();
        }
        return requireNonNull(c);
    }

    private static Stream<DetailAST> streamAll(DetailAST start, int type) {
        return Streams.stream(iterate(start))
                .filter(c -> c.getType() == type);
    }

    private static Iterator<DetailAST> iterate(DetailAST start) {
        return new Iterator<DetailAST>() {
            private DetailAST c = start;

            @Override
            public boolean hasNext() {
                return c != null;
            }

            @Override
            public DetailAST next() {
                DetailAST r = c;
                c = r.getNextSibling();
                return r;
            }
        };
    }

    private static boolean allDifferent(List<Integer> lines) {
        return new HashSet<>(lines).size() == lines.size();
    }

    private static boolean allSame(List<Integer> lines) {
        return new HashSet<>(lines).size() == 1;
    }

    public void setAllowSingleLine(boolean allowSingleLine) {
        this.allowSingleLine = allowSingleLine;
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
        List<Integer> lines = streamAll(parameters.getFirstChild(), TokenTypes.PARAMETER_DEF)
                .map(it -> it.getLineNo())
                .collect(Collectors.toList());

        if (lines.size() < 2) {
            return;
        }

        if (allDifferent(lines)) {
            return;
        }

        if (allowSingleLine && allSame(lines)) {
            return;
        }

        log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_LINES);
    }
}
