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
package calculator.evaluator.rpn;

import static org.junit.Assert.assertEquals;

import calculator.evaluator.Evaluator;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.function.rpn.RPNFunctionRepository;
import org.junit.Before;
import org.junit.Test;

public class RPNEvaluatorTest {
    private final static double EPSILON = 1e-10;

    private Evaluator testedObject;

    @Before
    public void setUp() {
        testedObject = new RPNEvaluator(new RPNFunctionRepository());
    }

    @Test
    public void testEvaluate_simpleExpression() throws Exception {
        final String expression = "2 + 2";
        final double actual = testedObject.evaluate(expression);
        assertEquals(4.0, actual, EPSILON);
    }

    @Test
    public void testEvaluate_operatorPrecedence() throws Exception {
        final String expression = "2 + 2 * 2";
        final double actual = testedObject.evaluate(expression);
        assertEquals(6.0, actual, EPSILON);
    }

    @Test
    public void testEvaluate_brackets() throws Exception {
        final String expression = "2 * ( 2 + 2 )";
        final double actual = testedObject.evaluate(expression);
        assertEquals(8.0, actual, EPSILON);
    }

    @Test
    public void testEvaluate_multiplicativeLeftToRight() throws Exception {
        final String expression = "2 / 2 * 2";
        final double actual = testedObject.evaluate(expression);
        assertEquals(2.0, actual, EPSILON);
    }

    @Test
    public void testEvaluate_powerRightToLeft() throws Exception {
        final String expression = "2 ^ 2 ^ 3";
        final double actual = testedObject.evaluate(expression);
        assertEquals(256.0, actual, EPSILON);
    }

    @Test
    public void testEvaluate_constant() throws Exception {
        final String expression = "PI";
        final double actual = testedObject.evaluate(expression);
        assertEquals(Math.PI, actual, EPSILON);
    }

    @Test
    public void testEvaluate_function() throws Exception {
        final String expression = "sin ( 3.14159 )";
        final double actual = testedObject.evaluate(expression);
        assertEquals(Math.sin(3.14159), actual, EPSILON);
    }

    @Test
    public void testEvaluate_functionWithOperator() throws Exception {
        final String expression = "sin ( 3.14 + 3.14 )";
        final double actual = testedObject.evaluate(expression);
        assertEquals(Math.sin(6.28), actual, EPSILON);
    }

    @Test
    public void testEvaluate_operatorWithFunction() throws Exception {
        final String expression = "1 - sin ( 3.14159 )";
        final double actual = testedObject.evaluate(expression);
        assertEquals(1.0 - Math.sin(3.14159), actual, EPSILON);
    }

    @Test
    public void testEvaluate_functionChain() throws Exception {
        final String expression = "exp ( sin ( PI ) )";
        final double actual = testedObject.evaluate(expression);
        assertEquals(1.0, actual, EPSILON);
    }

    @Test
    public void testEvaluate_complexExpression() throws Exception {
        final String expression = "1+atan2(2+2*2-2+2, log(exp(PI % E)))";
        final double actual = testedObject.evaluate(expression);
        assertEquals(1 + Math.atan2(2.0 + 2.0 * 2.0 - 2.0 + 2.0, Math.log(Math.exp(Math.PI % Math.E))), actual,
                EPSILON);
    }

    @Test(expected = ExpressionExecuteException.class)
    public void testEvaluate_badExpression() throws Exception {
        final String expression = "1 + ";
        testedObject.evaluate(expression);
    }
}
