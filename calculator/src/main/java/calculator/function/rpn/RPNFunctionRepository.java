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
package calculator.function.rpn;

import calculator.exception.FunctionAlreadyExistsException;
import calculator.exception.FunctionNotDefinedException;
import calculator.exception.WrongFunctionNameException;
import calculator.function.Function;
import calculator.function.FunctionRepository;
import calculator.function.rpn.builtin.BinaryOperatorFunction;
import calculator.function.rpn.builtin.BuiltinConstant;
import calculator.function.rpn.builtin.BuiltinFunction;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RPNFunctionRepository implements FunctionRepository {
    private final Map<String, Function> functions = new HashMap<>();

    private final Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");

    public RPNFunctionRepository() {
        initOperators();
        initFunctions();
        initConstants();
    }

    private void initOperators() {
        functions.put("+", new BinaryOperatorFunction.Add());
        functions.put("-", new BinaryOperatorFunction.Substract());
        functions.put("*", new BinaryOperatorFunction.Multiply());
        functions.put("/", new BinaryOperatorFunction.Divide());
        functions.put("%", new BinaryOperatorFunction.Modulo());
        functions.put("^", new BinaryOperatorFunction.Power());
    }

    private void initFunctions() {
        functions.put("sin", new BuiltinFunction.Sinus());
        functions.put("cos", new BuiltinFunction.Cosinus());
        functions.put("tan", new BuiltinFunction.Tangent());
        functions.put("asin", new BuiltinFunction.ArcSinus());
        functions.put("acos", new BuiltinFunction.ArcCosinus());
        functions.put("atan", new BuiltinFunction.ArcTangent());
        functions.put("atan2", new BuiltinFunction.ArcTangent2());
        functions.put("sinh", new BuiltinFunction.SinusHyperbolic());
        functions.put("cosh", new BuiltinFunction.CosinusHyperbolic());
        functions.put("tanh", new BuiltinFunction.TangentHyperbolic());

        functions.put("abs", new BuiltinFunction.AbsoluteValue());
        functions.put("log", new BuiltinFunction.Log());
        functions.put("exp", new BuiltinFunction.Exp());
        functions.put("sgn", new BuiltinFunction.Signum());
        functions.put("sqrt", new BuiltinFunction.SquareRoot());
        functions.put("d2r", new BuiltinFunction.DegreesToRadians());
        functions.put("r2d", new BuiltinFunction.RadiansToDegrees());
    }

    private void initConstants() {
        functions.put("PI", new BuiltinConstant.Pi());
        functions.put("E", new BuiltinConstant.E());
    }

    @Override
    public Function get(final String name) throws FunctionNotDefinedException {
        final Function function = functions.get(name);
        if (function == null) {
            throw new FunctionNotDefinedException(name);
        }
        return function;
    }

    @Override
    public void add(final String name, final Function function) throws FunctionAlreadyExistsException,
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
