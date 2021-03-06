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
import calculator.function.rpn.AbstractFunction;
import java.util.EmptyStackException;
import java.util.Stack;

public final class BuiltinFunction {
    public static final class Sin extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.sin(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class Cos extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.cos(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("cos", e);
            }
        }
    }

    public static final class Tan extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.tan(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("tan", e);
            }
        }
    }

    public static final class ArcSin extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.asin(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("asin", e);
            }
        }
    }

    public static final class ArcCos extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.acos(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("acos", e);
            }
        }
    }

    public static final class ArcTan extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.atan(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("atan", e);
            }
        }
    }

    public static final class ArcTan2 extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val2 = stack.pop();
                final double val1 = stack.pop();
                stack.push(Math.atan2(val1, val2));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("atan2", e);
            }
        }
    }

    public static final class SinHyperbolic extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.sinh(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sinh", e);
            }
        }
    }

    public static final class CosHyperbolic extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.cosh(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("cosh", e);
            }
        }
    }

    public static final class TanHyperbolic extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.tanh(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("tanh", e);
            }
        }
    }

    public static final class AbsoluteValue extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.abs(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("abs", e);
            }
        }
    }

    public static final class Log extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.log(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("log", e);
            }
        }
    }

    public static final class Exp extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.exp(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("exp", e);
            }
        }
    }

    public static final class Signum extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.signum(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sgn", e);
            }
        }
    }

    public static final class SquareRoot extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.sqrt(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sqrt", e);
            }
        }
    }

    public static final class DegreesToRadians extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.toRadians(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("d2r", e);
            }
        }
    }

    public static final class RadiansToDegrees extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.toDegrees(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("r2d", e);
            }
        }
    }

    public static final class Min extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val2 = stack.pop();
                final double val1 = stack.pop();
                stack.push(Math.min(val1, val2));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("min", e);
            }
        }
    }

    public static final class Max extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val2 = stack.pop();
                final double val1 = stack.pop();
                stack.push(Math.max(val1, val2));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("max", e);
            }
        }
    }

    public static final class Negation extends AbstractFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(-val);
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("neg", e);
            }
        }
    }
}
