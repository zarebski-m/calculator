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
package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    private final static double EPSILON = 1e-10;

    private Calculator testedObject;

    @Before
    public void setUp() {
        testedObject = new Calculator();
    }

    @Test
    public void testExecute_simpleExpression() throws Exception {
        final String expression = "2 + 2";
        testedObject.execute(expression);
        assertEquals(4.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_operatorPrecedence() throws Exception {
        final String expression = "2 + 2 * 2";
        testedObject.execute(expression);
        assertEquals(6.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_brackets() throws Exception {
        final String expression = "2 * ( 2 + 2 )";
        testedObject.execute(expression);
        assertEquals(8.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_multiplicativeLeftToRight() throws Exception {
        final String expression = "2 / 2 * 2";
        testedObject.execute(expression);
        assertEquals(2.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_powerRightToLeft() throws Exception {
        final String expression = "2 ^ 2 ^ 3";
        testedObject.execute(expression);
        assertEquals(256.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_constant() throws Exception {
        final String expression = "PI";
        testedObject.execute(expression);
        assertEquals(Math.PI, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_function() throws Exception {
        final String expression = "sin ( 3.14159 )";
        testedObject.execute(expression);
        assertEquals(Math.sin(3.14159), testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_functionWithOperator() throws Exception {
        final String expression = "sin ( 3.14 + 3.14 )";
        testedObject.execute(expression);
        assertEquals(Math.sin(6.28), testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_operatorWithFunction() throws Exception {
        final String expression = "1 - sin ( 3.14159 )";
        testedObject.execute(expression);
        assertEquals(1.0 - Math.sin(3.14159), testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_functionChain() throws Exception {
        final String expression = "exp ( sin ( PI ) )";
        testedObject.execute(expression);
        assertEquals(1.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_complexExpression() throws Exception {
        final String expression = "1 + atan2 ( 2 + 2 * 2 , log ( exp ( 6 ) ) )";
        testedObject.execute(expression);
        assertEquals(1 + Math.atan2(6.0, 6.0), testedObject.getResult(), EPSILON);
    }

    @Test
    public void testGetResult() throws Exception {
        testedObject.execute("1");
        assertEquals(1.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testGetResult_noResult() {
        assertTrue(Double.isNaN(testedObject.getResult()));
    }
}
