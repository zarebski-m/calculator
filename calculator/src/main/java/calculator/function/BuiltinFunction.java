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

import java.util.Stack;

public final class BuiltinFunction {
    public static final class Sinus extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.sin(val));
        }
    }

    public static final class Cosinus extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.cos(val));
        }
    }

    public static final class Tangens extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.tan(val));
        }
    }

    public static final class Cotangens extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.cos(val) / Math.sin(val));
        }
    }

    public static final class Secans extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(1.0 / Math.cos(val));
        }
    }

    public static final class Cosecans extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(1.0 / Math.sin(val));
        }
    }

    public static final class AbsoluteValue extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.abs(val));
        }
    }

    public static final class Log extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.log(val));
        }
    }

    public static final class Exp extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.exp(val));
        }
    }

    public static final class Signum extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.signum(val));
        }
    }

    public static final class SquareRoot extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            final double val = stack.pop();
            stack.push(Math.sqrt(val));
        }
    }
}
