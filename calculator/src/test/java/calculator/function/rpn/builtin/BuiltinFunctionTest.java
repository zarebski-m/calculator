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

import static org.junit.Assert.assertEquals;

import calculator.exception.execute.NotEnoughParametersException;
import calculator.function.Function;
import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class BuiltinFunctionTest {
    private static final double EPSILON = 1e-10;

    private Function function;

    private double value;

    private double expectedResult;

    public BuiltinFunctionTest(Function function, double value, double expectedResult) {
        this.function = function;
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
            {new BuiltinFunction.Sinus(), Math.PI, 0.0},
            {new BuiltinFunction.Cosinus(), Math.PI, -1.0},
            {new BuiltinFunction.Tangent(), 0.0, 0.0},
            {new BuiltinFunction.ArcSinus(), 0.0, 0.0},
            {new BuiltinFunction.ArcCosinus(), 0.0, Math.PI / 2.0},
            {new BuiltinFunction.ArcTangent(), Double.POSITIVE_INFINITY, Math.PI / 2.0},
            {new BuiltinFunction.SinusHyperbolic(), 0.0, 0.0},
            {new BuiltinFunction.CosinusHyperbolic(), 0.0, 1.0},
            {new BuiltinFunction.TangentHyperbolic(), 0.0, 0.0},
            {new BuiltinFunction.AbsoluteValue(), -1.0, 1.0},
            {new BuiltinFunction.Log(), Math.E, 1.0},
            {new BuiltinFunction.Exp(), 1.0, Math.E},
            {new BuiltinFunction.Signum(), 0.1, 1.0},
            {new BuiltinFunction.Signum(), 0.0, 0.0},
            {new BuiltinFunction.Signum(), -0.1, -1.0},
            {new BuiltinFunction.SquareRoot(), 9.0, 3.0},
            {new BuiltinFunction.DegreesToRadians(), 90.0, Math.PI / 2.0},
            {new BuiltinFunction.RadiansToDegrees(), Math.PI, 180.0},};
        return Arrays.asList(data);
    }

    @Test
    public void testExecute() throws Exception {
        Stack<Double> stack = new Stack<>();
        stack.push(value);
        function.apply(stack);
        assertEquals(expectedResult, stack.peek(), EPSILON);
    }

    @Test(expected = NotEnoughParametersException.class)
    public void testExecute_notEnoughParameters() throws Exception {
        function.apply(new Stack<Double>());
    }
}
