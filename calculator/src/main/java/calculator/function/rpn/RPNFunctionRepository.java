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

import calculator.exception.execute.FunctionNotDefinedException;
import calculator.exception.parse.FunctionAlreadyExistsException;
import calculator.exception.parse.WrongFunctionNameException;
import calculator.function.Function;
import calculator.function.FunctionRepository;
import calculator.function.rpn.builtin.BinaryOperatorFunction;
import calculator.function.rpn.builtin.BuiltinConstant;
import calculator.function.rpn.builtin.BuiltinFunction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RPNFunctionRepository implements FunctionRepository {
    private final Map<String, Function> builtinFunctions = new HashMap<>();

    private final Map<String, Function> functions = new HashMap<>();

    private final Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");

    public RPNFunctionRepository() {
        initOperators();
        initFunctions();
        initConstants();
    }

    private void initOperators() {
        builtinFunctions.put("+", new BinaryOperatorFunction.Add());
        builtinFunctions.put("-", new BinaryOperatorFunction.Substract());
        builtinFunctions.put("*", new BinaryOperatorFunction.Multiply());
        builtinFunctions.put("/", new BinaryOperatorFunction.Divide());
        builtinFunctions.put("%", new BinaryOperatorFunction.Modulo());
        builtinFunctions.put("^", new BinaryOperatorFunction.Power());
    }

    private void initFunctions() {
        builtinFunctions.put("sin", new BuiltinFunction.Sinus());
        builtinFunctions.put("cos", new BuiltinFunction.Cosinus());
        builtinFunctions.put("tan", new BuiltinFunction.Tangent());
        builtinFunctions.put("asin", new BuiltinFunction.ArcSinus());
        builtinFunctions.put("acos", new BuiltinFunction.ArcCosinus());
        builtinFunctions.put("atan", new BuiltinFunction.ArcTangent());
        builtinFunctions.put("atan2", new BuiltinFunction.ArcTangent2());
        builtinFunctions.put("sinh", new BuiltinFunction.SinusHyperbolic());
        builtinFunctions.put("cosh", new BuiltinFunction.CosinusHyperbolic());
        builtinFunctions.put("tanh", new BuiltinFunction.TangentHyperbolic());

        builtinFunctions.put("abs", new BuiltinFunction.AbsoluteValue());
        builtinFunctions.put("log", new BuiltinFunction.Log());
        builtinFunctions.put("exp", new BuiltinFunction.Exp());
        builtinFunctions.put("sgn", new BuiltinFunction.Signum());
        builtinFunctions.put("sqrt", new BuiltinFunction.SquareRoot());
        builtinFunctions.put("d2r", new BuiltinFunction.DegreesToRadians());
        builtinFunctions.put("r2d", new BuiltinFunction.RadiansToDegrees());
        builtinFunctions.put("min", new BuiltinFunction.Min());
        builtinFunctions.put("max", new BuiltinFunction.Max());
        builtinFunctions.put("neg", new BuiltinFunction.Negation());
    }

    private void initConstants() {
        builtinFunctions.put("PI", new BuiltinConstant.Pi());
        builtinFunctions.put("E", new BuiltinConstant.E());
    }

    @Override
    public Function get(final String name) throws FunctionNotDefinedException {
        final Function tempFunction = functions.get(name);
        if (tempFunction != null) {
            return tempFunction;
        }
        final Function builtin = builtinFunctions.get(name);
        if (builtin != null) {
            return builtin;
        }

        throw new FunctionNotDefinedException(name);

    }

    @Override
    public void update(final String name, final Function function) throws FunctionAlreadyExistsException,
            WrongFunctionNameException {
        if (!namePattern.matcher(name).matches()) {
            throw new WrongFunctionNameException(name);
        }
        if (builtinFunctions.containsKey(name)) {
            throw new FunctionAlreadyExistsException(name);
        }
        functions.put(name, function);
    }

    @Override
    public void delete(final String name) {
        functions.remove(name);
    }

    @Override
    public void clear() {
        functions.clear();
    }

    @Override
    public Map<String, Function> getBuiltinFunctions() {
        return Collections.unmodifiableMap(builtinFunctions);
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Collections.unmodifiableMap(functions);
    }
}
