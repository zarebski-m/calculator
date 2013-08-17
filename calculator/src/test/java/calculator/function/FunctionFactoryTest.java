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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import calculator.exception.FunctionAlreadyExistsException;
import calculator.exception.FunctionNotDefinedException;
import calculator.exception.WrongFunctionNameException;
import org.junit.Before;
import org.junit.Test;

public class FunctionFactoryTest {
    private FunctionFactory factory;

    @Before
    public void setUp() {
        factory = new FunctionFactory();
    }

    @Test
    public void testGetFunction_checkBuiltins() throws Exception {
        final String[] names = {
            // trigonometric
            "sin", "cos", "tan",
            // cyclometric
            "asin", "acos", "atan", "atan2",
            // hiperbolic
            "sinh", "cosh", "tanh",
            // conversion
            "d2r", "r2d",
            // other
            "abs", "log", "exp", "sgn", "sqrt",
            // constants
            "PI", "E"
        };
        for (final String name : names) {
            Function f = factory.getFunction(name);
            assertNotNull(f);
        }
    }

    @Test(expected = FunctionNotDefinedException.class)
    public void testGetFunction_functionDoesNotExist() throws Exception {
        factory.getFunction("undefined");
    }

    @Test
    public void testRegisterFunction_success() throws Exception {
        final String name = "sin_new";
        factory.registerFunction(name, new MathFunction.Sinus());
        final Function function = factory.getFunction(name);
        assertTrue(function instanceof MathFunction.Sinus);
    }

    @Test(expected = FunctionAlreadyExistsException.class)
    public void testRegisterFunction_functionAlreadyExists() throws Exception {
        factory.registerFunction("sin", null);
    }

    @Test(expected = WrongFunctionNameException.class)
    public void testRegisterFunction_wrongName() throws Exception {
        factory.registerFunction("wrong-name", null);
    }
}
