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
package calculator.function.rpn.builtin;

import calculator.exception.execute.NotEnoughParametersException;
import calculator.function.Function;
import java.util.EmptyStackException;
import java.util.Stack;

public abstract class BinaryOperatorFunction implements Function {
    protected static final int PRIORITY_ADDITIVE = 1;

    protected static final int PRIORITY_MULTIPLICATIVE = 2;

    protected static final int PRIORITY_POWER = 3;

    private double lhs;

    private double rhs;

    protected final double getLhs() {
        return lhs;
    }

    protected final double getRhs() {
        return rhs;
    }

    protected final void prepareParams(final Stack<Double> stack) throws NotEnoughParametersException {
        try {
            rhs = stack.pop();
            lhs = stack.pop();
        } catch (final EmptyStackException e) {
            throw new NotEnoughParametersException("operator", e);
        }
    }

    public static final class Add extends BinaryOperatorFunction {
        @Override
        public int getPriority() {
            return PRIORITY_ADDITIVE;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.Left;
        }

        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            prepareParams(stack);
            stack.push(getLhs() + getRhs());
        }
    }

    public static final class Substract extends BinaryOperatorFunction {
        @Override
        public int getPriority() {
            return PRIORITY_ADDITIVE;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.Left;
        }

        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            prepareParams(stack);
            stack.push(getLhs() - getRhs());
        }
    }

    public static final class Multiply extends BinaryOperatorFunction {
        @Override
        public int getPriority() {
            return PRIORITY_MULTIPLICATIVE;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.Left;
        }

        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            prepareParams(stack);
            stack.push(getLhs() * getRhs());
        }
    }

    public static final class Divide extends BinaryOperatorFunction {
        @Override
        public int getPriority() {
            return PRIORITY_MULTIPLICATIVE;
        }

        @Override
        public Function.Associativity getAssociativity() {
            return Function.Associativity.Left;
        }

        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            prepareParams(stack);
            stack.push(getLhs() / getRhs());
        }
    }

    public static final class Modulo extends BinaryOperatorFunction {
        @Override
        public int getPriority() {
            return PRIORITY_MULTIPLICATIVE;
        }

        @Override
        public Function.Associativity getAssociativity() {
            return Function.Associativity.Left;
        }

        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            prepareParams(stack);
            stack.push(getLhs() % getRhs());
        }
    }

    public static final class Power extends BinaryOperatorFunction {
        @Override
        public int getPriority() {
            return PRIORITY_POWER;
        }

        @Override
        public Function.Associativity getAssociativity() {
            return Function.Associativity.Right;
        }

        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            prepareParams(stack);
            stack.push(Math.pow(getLhs(), getRhs()));
        }
    }
}
