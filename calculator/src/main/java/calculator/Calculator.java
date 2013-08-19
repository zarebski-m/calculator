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
package calculator;

import static calculator.parser.token.Token.TokenType.Number;

import calculator.exception.ExpressionExecuteException;
import calculator.exception.FunctionNotDefinedException;
import calculator.exception.NotEnoughParametersException;
import calculator.function.Function;
import calculator.function.FunctionFactory;
import calculator.function.builtin.TerminalFunction;
import calculator.function.custom.FunctionParser;
import calculator.function.custom.FunctionParserImpl;
import calculator.parser.ExpressionTokenizer;
import calculator.parser.ExpressionTokenizerImpl;
import calculator.parser.token.FunctionToken;
import calculator.parser.token.NumberToken;
import calculator.parser.token.Token;
import calculator.parser.token.TokenFactory;
import java.util.Stack;

public class Calculator {
    private final Stack<Double> values = new Stack<>();

    private final Stack<Function> functions = new Stack<>();

    private final TokenFactory tokenFactory;

    private final FunctionFactory functionFactory;

    private final FunctionParser functionParser;

    public Calculator() {
        this.functionParser = new FunctionParserImpl();
        this.functionFactory = new FunctionFactory(functionParser);
        this.tokenFactory = new TokenFactory(functionFactory);
    }

    public void execute(final String expression) throws ExpressionExecuteException {
        ExpressionTokenizer tokenizer = new ExpressionTokenizerImpl(expression);
        try {
            while (tokenizer.hasNextToken()) {
                String tokenString = tokenizer.getNextToken();
                final Token<?> token = tokenFactory.getToken(tokenString);
                handleToken(token);
            }

            while (!functions.isEmpty()) {
                final Function function = functions.pop();
                function.apply(values);
            }
        } catch (FunctionNotDefinedException | NotEnoughParametersException ex) {
            throw new ExpressionExecuteException(expression, ex);
        }
    }

    private void handleToken(final Token<?> token) throws ExpressionExecuteException, NotEnoughParametersException {
        switch (token.getTokenType()) {
            case Number:
                handleNumber(token);
                break;
            case Function:
                handleFunction(token);
                break;
            case OpenBracket:
                handleOpenBracket();
                break;
            case ClosedBracket:
                handleClosedBracket();
                break;
            case Comma:
                handleComma();
                break;
            default:
                throw new UnsupportedOperationException(token.getRawValue());
        }
    }

    private void handleNumber(final Token<?> token) {
        final NumberToken numberToken = (NumberToken)token;
        values.push(numberToken.getValue());
    }

    private void handleFunction(final Token<?> token) throws ExpressionExecuteException,
            NotEnoughParametersException {
        final FunctionToken functionToken = (FunctionToken)token;
        while (!functions.isEmpty() && shouldExecute(functions.peek(), functionToken.getValue())) {
            final Function function = functions.pop();
            function.apply(values);
        }
        functions.push(functionToken.getValue());
    }

    private boolean shouldExecute(final Function existingFunction, final Function newFunction) {
        if (existingFunction.getPriority() > newFunction.getPriority()) {
            return true;
        }
        if (existingFunction.getPriority() == newFunction.getPriority() &&
                existingFunction.getAssociativity() == Function.Associativity.Left) {
            return true;
        }
        return false;
    }

    private void handleOpenBracket() {
        functions.push(new TerminalFunction());
    }

    private void handleClosedBracket() throws ExpressionExecuteException, NotEnoughParametersException {
        handleComma();
        if (!functions.isEmpty()) {
            functions.pop();
        }
    }

    private void handleComma() throws ExpressionExecuteException, NotEnoughParametersException {
        while (!functions.isEmpty() && functions.peek().getPriority() > TerminalFunction.PRIORITY_TERMINAL) {
            final Function function = functions.pop();
            function.apply(values);
        }
    }

    public void clear() {
        values.clear();
        functions.clear();
    }

    public double getResult() {
        if (values.isEmpty()) {
            return Double.NaN;
        }
        return values.peek();
    }
}
