/*
 * The MIT License
 *
 * Copyright 2013 Marcin Zarebski <zarebski.m[AT]gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package calculator.parser;

import calculator.evaluator.Evaluator;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.exception.execute.NotEnoughParametersException;
import calculator.exception.parse.FunctionParseException;
import calculator.function.rpn.custom.FunctionExecutor;
import com.google.common.annotations.VisibleForTesting;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleFunctionParser implements FunctionParser {
    private Evaluator evaluator;

    private static final Pattern paramPattern = Pattern.compile("\\{(\\d)\\}");

    public SimpleFunctionParser(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    private class SimpleFunctionExecutor implements FunctionExecutor {
        private final int numberOfParams;

        private final String functionBody;

        public SimpleFunctionExecutor(int numberOfParams, String expression) {
            this.numberOfParams = numberOfParams;
            this.functionBody = expression;
        }

        public int getNumberOfParams() {
            return numberOfParams;
        }

        @Override
        public void execute(final Stack<Double> stack) throws ExpressionExecuteException {
            final double[] parameters = prepareParameters(stack);
            final String processedExpression = processExpression(functionBody, parameters);
            stack.push(evaluator.execute(processedExpression));
        }

        private double[] prepareParameters(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double[] parameters = new double[numberOfParams];

                int paramsCounter = numberOfParams;
                while (paramsCounter-- > 0) {
                    parameters[paramsCounter] = stack.pop();
                }
                return parameters;
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException(functionBody, e);
            }
        }

        private String processExpression(final String functionBody, final double[] parameters) {
            String processed = functionBody;
            for (int i = 0; i < parameters.length; ++i) {
                processed = processed.replaceAll("\\{" + i + "\\}", String.valueOf(parameters[i]));
            }
            return processed;
        }
    }

    @Override
    public FunctionExecutor parse(String functionBody) throws FunctionParseException {
        try {
            final int numberOfParams = countParameters(functionBody);
            return new SimpleFunctionExecutor(numberOfParams, functionBody);
        } catch (NumberFormatException ex) {
            throw new FunctionParseException(functionBody, ex);
        }

    }

    private int countParameters(final String functionBody) {
        int maxParam = -1;
        final Matcher matcher = paramPattern.matcher(functionBody);
        while (matcher.find()) {
            final int paramNumber;
            paramNumber = Integer.valueOf(matcher.group(1));
            if (paramNumber > maxParam) {
                maxParam = paramNumber;
            }
        }
        return maxParam + 1;
    }

    @VisibleForTesting
    void setEvaluator(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
}
