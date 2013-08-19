package calculator;

/*
 * The MIT License
 *
 * Copyright 2013 Marcin Zarebski <zarebski.m[AT]gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import calculator.evaluator.Evaluator;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.function.FunctionRepository;
import calculator.parser.FunctionParser;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    private static double EPSILON = 1e-10;

    private EasyMockSupport support;

    private Evaluator evaluatorMock;

    private FunctionRepository functionRepositoryMock;

    private FunctionParser functionParserMock;

    private Calculator testedObject;

    @Before
    public void setUp() {
        support = new EasyMockSupport();
        evaluatorMock = support.createMock(Evaluator.class);
        functionRepositoryMock = support.createMock(FunctionRepository.class);
        functionParserMock = support.createMock(FunctionParser.class);

        testedObject = new Calculator();
        testedObject.setEvaluator(evaluatorMock);
        testedObject.setFunctionRepository(functionRepositoryMock);
        testedObject.setFunctionParser(functionParserMock);
    }

    @Test
    public void testExecute_noExecution() throws Exception {
        assertEquals(0.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_singleExecution() throws Exception {
        final String expr = "expression";
        final double expected = 12.3;

        expect(evaluatorMock.execute(expr)).andReturn(expected);

        support.replayAll();
        testedObject.execute(expr);
        support.verifyAll();

        assertEquals(expected, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testExecute_executionError() throws Exception {
        final String expr = "bad expression";

        expect(evaluatorMock.execute(expr)).andThrow(new ExpressionExecuteException(expr, null));

        try {
            support.replayAll();
            testedObject.execute(expr);
            support.verifyAll();
        } catch (ExpressionExecuteException ex) {
            return;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            assertTrue(Double.isNaN(testedObject.getResult()));
        }

        fail();
    }
}
