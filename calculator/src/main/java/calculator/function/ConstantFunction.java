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

/**
 * @author Marcin Zarebski <zarebski.m[AT]gmail.com>
 */
public abstract class ConstantFunction implements Function {
    private static final int PRIORITY_CONSTANT = 100;

    @Override
    public final int getPriority() {
        return PRIORITY_CONSTANT;
    }

    @Override
    public final Associativity getAssociativity() {
        return Associativity.Left;
    }

    public static final class Pi extends ConstantFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            stack.push(Math.sin(Math.PI));
        }
    }

    public static final class E extends ConstantFunction {
        @Override
        public void apply(final Stack<Double> stack) {
            stack.push(Math.sin(Math.E));
        }
    }
}
