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
package calculator.function;

import calculator.exception.FunctionAlreadyExistsException;
import calculator.exception.FunctionNotDefinedException;
import calculator.exception.WrongFunctionNameException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FunctionFactory {
    private final Map<String, Function> functions = new HashMap<>();

    private final Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");

    public FunctionFactory() {
        // operators
        functions.put("+", new OperatorFunction.Add());
        functions.put("-", new OperatorFunction.Substract());
        functions.put("*", new OperatorFunction.Multiply());
        functions.put("/", new OperatorFunction.Divide());
        functions.put("%", new OperatorFunction.Modulo());
        functions.put("^", new OperatorFunction.Power());

        // builtin functions
        functions.put("sin", new BuiltinFunction.Sinus());
        functions.put("cos", new BuiltinFunction.Cosinus());
        functions.put("tan", new BuiltinFunction.Tangens());
        functions.put("cot", new BuiltinFunction.Cotangens());
        functions.put("sec", new BuiltinFunction.Secans());
        functions.put("csc", new BuiltinFunction.Cosecans());

        functions.put("abs", new BuiltinFunction.AbsoluteValue());
        functions.put("log", new BuiltinFunction.Log());
        functions.put("exp", new BuiltinFunction.Exp());
        functions.put("sgn", new BuiltinFunction.Signum());
        functions.put("sqrt", new BuiltinFunction.SquareRoot());
    }

    public Function getFunction(final String name) throws FunctionNotDefinedException {
        final Function function = functions.get(name);
        if (function == null) {
            throw new FunctionNotDefinedException(name);
        }
        return function;
    }

    public void registerFunction(final String name, final Function function) throws FunctionAlreadyExistsException,
                                                                                    WrongFunctionNameException {
        if (!namePattern.matcher(name).matches()) {
            throw new WrongFunctionNameException(name);
        }
        if (functions.containsKey(name)) {
            throw new FunctionAlreadyExistsException(name);
        }
        functions.put(name, function);
    }
}
