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

import calculator.command.Command;
import calculator.command.CommandResult;
import calculator.command.EmptyResult;
import calculator.command.FunctionListResult;
import calculator.evaluator.Evaluator;
import calculator.evaluator.rpn.RPNEvaluator;
import calculator.exception.command.UnknownCommandException;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.exception.parse.FunctionParseException;
import calculator.function.FunctionRepository;
import calculator.function.rpn.RPNFunctionRepository;
import calculator.function.rpn.builtin.DoubleConstant;
import calculator.function.rpn.custom.CustomConstant;
import calculator.function.rpn.custom.CustomFunction;
import calculator.function.rpn.custom.FunctionExecutor;
import calculator.parser.FunctionParser;
import calculator.parser.SimpleFunctionParser;
import com.google.common.annotations.VisibleForTesting;

public class Calculator {
    @VisibleForTesting
    static final String ANS = "ans";

    private Evaluator evaluator;

    private Evaluator helperEvaluator;

    private FunctionRepository functionRepository;

    private FunctionParser functionParser;

    public Calculator() {
        functionRepository = new RPNFunctionRepository();
        evaluator = new RPNEvaluator(functionRepository);
        helperEvaluator = new RPNEvaluator(functionRepository);
        functionParser = new SimpleFunctionParser(helperEvaluator);
    }

    private double actualResult = 0.0;

    public void evaluate(final String expression) throws ExpressionExecuteException {
        try {
            actualResult = evaluator.evaluate(expression);
            functionRepository.update(ANS, new DoubleConstant(actualResult));
        } catch (ExpressionExecuteException ex) {
            actualResult = Double.NaN;
            throw ex;
        } catch (FunctionParseException ex) {
            actualResult = Double.NaN;
            throw new ExpressionExecuteException(expression, ex);
        }
    }

    public double getResult() {
        return actualResult;
    }

    public void putFunction(final String name, final String functionBody) throws FunctionParseException {
        final FunctionExecutor executor = functionParser.parse(functionBody);
        functionRepository.update(name, new CustomFunction(executor));
    }

    public void putConstant(final String name, final String expression) throws FunctionParseException {
        final FunctionExecutor executor = functionParser.parse(expression);
        functionRepository.update(name, new CustomConstant(executor));
    }

    public void putConstant(final String name, final Double value) throws FunctionParseException {
        functionRepository.update(name, new DoubleConstant(value));
    }

    public void deleteFunctionOrConstant(final String name) {
        functionRepository.delete(name);
    }

    public void clear() throws FunctionParseException {
        actualResult = 0.0;
        functionRepository.update("ans", new DoubleConstant(actualResult));
    }

    public void clearAll() throws FunctionParseException {
        functionRepository.clear();
        clear();
    }

    public CommandResult executeCommand(final Command command) throws FunctionParseException,
            UnknownCommandException, ExpressionExecuteException {
        switch (command.getType()) {
            case DefineFunction:
                putFunction(command.getParam(), command.getContent());
                return new EmptyResult();
            case DefineConstant:
                putConstant(command.getParam(), command.getContent());
                return new EmptyResult();
            case Delete:
                deleteFunctionOrConstant(command.getParam());
                return new EmptyResult();
            case Clear:
                clear();
                return new EmptyResult();
            case ClearAll:
                clearAll();
                return new EmptyResult();
            case Save:
                final String name = command.getParam();
                putConstant(name, getResult());
                return new EmptyResult();
            case Print:
                return new FunctionListResult(functionRepository.getFunctions());
            case PrintBuiltin:
                return new FunctionListResult(functionRepository.getBuiltinFunctions());
            default:
                throw new UnknownCommandException();
        }
    }

    @VisibleForTesting
    void setEvaluator(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @VisibleForTesting
    void setFunctionRepository(final FunctionRepository repository) {
        this.functionRepository = repository;
    }

    @VisibleForTesting
    void setFunctionParser(final FunctionParser parser) {
        this.functionParser = parser;
    }
}
