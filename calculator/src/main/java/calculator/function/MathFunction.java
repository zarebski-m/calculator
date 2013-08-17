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

import calculator.exception.NotEnoughParametersException;
import java.util.EmptyStackException;
import java.util.Stack;

public abstract class MathFunction implements Function {
    private static final int PRIORITY_FUNCTION = 10;

    @Override
    public final int getPriority() {
        return PRIORITY_FUNCTION;
    }

    @Override
    public final Associativity getAssociativity() {
        return Associativity.Right;
    }

    public static final class Sinus extends MathFunction {
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

    public static final class Cosinus extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.cos(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class Tangent extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.tan(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class ArcSinus extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.asin(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class ArcCosinus extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.acos(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class ArcTangent extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.atan(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class ArcTangent2 extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val2 = stack.pop();
                final double val1 = stack.pop();
                stack.push(Math.atan2(val1, val2));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class SinusHyperbolic extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.sinh(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class CosinusHyperbolic extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.cosh(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class TangentHyperbolic extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.tanh(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class AbsoluteValue extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.abs(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class Log extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.log(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class Exp extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.exp(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class Signum extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.signum(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class SquareRoot extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.sqrt(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class DegreesToRadians extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.toRadians(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }

    public static final class RadiansToDegrees extends MathFunction {
        @Override
        public void apply(final Stack<Double> stack) throws NotEnoughParametersException {
            try {
                final double val = stack.pop();
                stack.push(Math.toDegrees(val));
            } catch (EmptyStackException e) {
                throw new NotEnoughParametersException("sin", e);
            }
        }
    }
}
