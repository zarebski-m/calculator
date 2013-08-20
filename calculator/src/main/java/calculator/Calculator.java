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
import calculator.command.NumberResult;
import calculator.command.VariableListResult;
import calculator.evaluator.Evaluator;
import calculator.evaluator.rpn.RPNEvaluator;
import calculator.exception.command.UnknownCommandException;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.exception.parse.FunctionParseException;
import calculator.function.FunctionRepository;
import calculator.function.rpn.RPNFunctionRepository;
import calculator.function.rpn.custom.CustomConstant;
import calculator.function.rpn.custom.CustomFunction;
import calculator.function.rpn.custom.FunctionExecutor;
import calculator.parser.FunctionParser;
import calculator.parser.SimpleFunctionParser;
import com.google.common.annotations.VisibleForTesting;
import java.util.HashSet;
import java.util.Set;

public class Calculator {
    private Evaluator evaluator;

    private Evaluator helperEvaluator;

    private FunctionRepository functionRepository;

    private FunctionParser functionParser;

    private final Set<String> variableNames = new HashSet<>();

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
        } catch (ExpressionExecuteException ex) {
            actualResult = Double.NaN;
            throw ex;
        }
    }

    public double getResult() {
        return actualResult;
    }

    public void addFunction(final String name, final String functionBody) throws FunctionParseException {
        final FunctionExecutor executor = functionParser.parse(functionBody);
        functionRepository.add(name, new CustomFunction(executor));
    }

    public void addConstant(final String name, final String expression) throws FunctionParseException {
        final FunctionExecutor executor = functionParser.parse(expression);
        functionRepository.add(name, new CustomConstant(executor));
    }

    public void deleteFunctionOrConstant(final String name) {
        functionRepository.delete(name);
    }

    public void clear() {
        actualResult = 0.0;
    }

    public void clearAll() {
        clear();
        for (final String var : variableNames) {
            deleteFunctionOrConstant(var);
        }
        variableNames.clear();
    }

    public CommandResult executeCommand(final Command command) throws FunctionParseException,
            UnknownCommandException,
            ExpressionExecuteException {
        switch (command.getType()) {
            case DefineFunction:
                addFunction(command.getParam(), command.getContent());
                return new EmptyResult();
            case DefineConstant:
                addConstant(command.getParam(), command.getContent());
                return new EmptyResult();
            case Clear:
                clear();
                return new EmptyResult();
            case ClearAll:
                clearAll();
                return new EmptyResult();
            case Recall:
                if (command.getParam() != null) {
                    evaluate(command.getParam());
                }
                return new NumberResult(getResult());
            case Save:
                final String name = command.getParam();
                deleteFunctionOrConstant(name);
                addConstant(name, String.valueOf(getResult()));
                variableNames.add(name);
                return new EmptyResult();
            case Print:
                final VariableListResult result = new VariableListResult();
                for (final String varName : variableNames) {
                    evaluate(varName);
                    result.addVariable(varName, getResult());
                }
                return result;
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
