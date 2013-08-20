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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import calculator.exception.execute.NotEnoughParametersException;
import calculator.function.Function;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class BuiltinFunctionTest {
    private static final double EPSILON = 1e-10;

    private Function function;

    private List<Double> params;

    private double expectedResult;

    public BuiltinFunctionTest(final Function function, final List<Double> params, double expectedResult) {
        this.function = function;
        this.params = params;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
            {new BuiltinFunction.Sinus(), asList(Math.PI), 0.0},
            {new BuiltinFunction.Cosinus(), asList(Math.PI), -1.0},
            {new BuiltinFunction.Tangent(), asList(0.0), 0.0},
            {new BuiltinFunction.ArcSinus(), asList(0.0), 0.0},
            {new BuiltinFunction.ArcCosinus(), asList(0.0), Math.PI / 2.0},
            {new BuiltinFunction.ArcTangent(), asList(Double.POSITIVE_INFINITY), Math.PI / 2.0},
            {new BuiltinFunction.ArcTangent2(), asList(1.0, 0.0), Math.PI / 2.0},
            {new BuiltinFunction.SinusHyperbolic(), asList(0.0), 0.0},
            {new BuiltinFunction.CosinusHyperbolic(), asList(0.0), 1.0},
            {new BuiltinFunction.TangentHyperbolic(), asList(0.0), 0.0},
            {new BuiltinFunction.AbsoluteValue(), asList(-1.0), 1.0},
            {new BuiltinFunction.Log(), asList(Math.E), 1.0},
            {new BuiltinFunction.Exp(), asList(1.0), Math.E},
            {new BuiltinFunction.Signum(), asList(0.1), 1.0},
            {new BuiltinFunction.Signum(), asList(0.0), 0.0},
            {new BuiltinFunction.Signum(), asList(-0.1), -1.0},
            {new BuiltinFunction.SquareRoot(), asList(9.0), 3.0},
            {new BuiltinFunction.DegreesToRadians(), asList(90.0), Math.PI / 2.0},
            {new BuiltinFunction.RadiansToDegrees(), asList(Math.PI), 180.0},
            {new BuiltinFunction.Min(), asList(1.0, 0.0), 0.0},
            {new BuiltinFunction.Max(), asList(1.0, 0.0), 1.0},
            {new BuiltinFunction.Negation(), asList(1.0), -1.0}
        };
        return asList(data);
    }

    @Test
    public void testExecute() throws Exception {
        Stack<Double> stack = new Stack<>();
        for (final double param : params) {
            stack.push(param);
        }
        function.apply(stack);
        assertEquals(expectedResult, stack.peek(), EPSILON);
    }

    @Test(expected = NotEnoughParametersException.class)
    public void testExecute_notEnoughParameters() throws Exception {
        function.apply(new Stack<Double>());
    }
}
