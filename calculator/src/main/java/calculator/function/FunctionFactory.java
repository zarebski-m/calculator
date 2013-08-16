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

import java.util.HashMap;
import java.util.Map;

public class FunctionFactory {
    private final Map<String, Function> functions = new HashMap<>();

    public FunctionFactory() {
        // operators
        functions.put("+", new OperatorFunction.Add());
        functions.put("-", new OperatorFunction.Substract());
        functions.put("*", new OperatorFunction.Multiply());
        functions.put("/", new OperatorFunction.Divide());
        functions.put("%", new OperatorFunction.Modulo());
        functions.put("^", new OperatorFunction.Power());

        // builtin functions
        functions.put("sin", new BuiltinFunction.Sin());
        functions.put("cos", new BuiltinFunction.Cos());
        functions.put("tan", new BuiltinFunction.Tan());
        functions.put("cotan", new BuiltinFunction.Cotan());
    }

    public Function getFunction(final String name) {
        final Function function = functions.get(name);
        if (function == null) {
            throw new IllegalArgumentException("Unknown function or operator: " + name);
        }
        return function;
    }

    public void registerFunction(final String name, final Function function) {
        functions.put(name, function);
    }
}
