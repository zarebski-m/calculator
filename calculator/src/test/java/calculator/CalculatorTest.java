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
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import calculator.command.Command;
import calculator.command.CommandResult;
import calculator.command.FunctionListResult;
import calculator.evaluator.Evaluator;
import calculator.exception.command.UnknownCommandException;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.exception.parse.ConstantWithParametersException;
import calculator.function.Function;
import calculator.function.FunctionRepository;
import calculator.function.rpn.builtin.BuiltinFunction;
import calculator.function.rpn.builtin.DoubleConstant;
import calculator.function.rpn.custom.CustomConstant;
import calculator.function.rpn.custom.CustomFunction;
import calculator.function.rpn.custom.FunctionExecutor;
import calculator.parser.FunctionParser;
import java.util.HashMap;
import java.util.Map;
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
    public void testEvaluateExpression_noExecution() throws Exception {
        assertEquals(0.0, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testEvaluateExpression_singleExecution() throws Exception {
        final String expr = "expression";
        final double expected = 12.3;

        expect(evaluatorMock.evaluate(expr)).andReturn(expected);
        functionRepositoryMock.update(eq(Calculator.ANS), anyObject(Function.class));

        support.replayAll();
        testedObject.evaluate(expr);
        support.verifyAll();

        assertEquals(expected, testedObject.getResult(), EPSILON);
    }

    @Test
    public void testEvaluateExpression_executionError() throws Exception {
        final String expr = "bad expression";

        expect(evaluatorMock.evaluate(expr)).andThrow(new ExpressionExecuteException(expr, null));

        try {
            support.replayAll();
            testedObject.evaluate(expr);
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

    @Test
    public void testAddFunction() throws Exception {
        final String name = "functionName";
        final String body = "body";
        final FunctionExecutor executorMock = support.createMock(FunctionExecutor.class);

        expect(functionParserMock.parse(body)).andReturn(executorMock);
        functionRepositoryMock.update(eq(name), anyObject(CustomFunction.class));

        support.replayAll();
        testedObject.putFunction(name, body);
        support.verifyAll();
    }

    @Test
    public void testAddConstant_success() throws Exception {
        final String name = "constantName";
        final String body = "body";
        final FunctionExecutor executorMock = support.createMock(FunctionExecutor.class);

        expect(functionParserMock.parse(body)).andReturn(executorMock);
        functionRepositoryMock.update(eq(name), anyObject(CustomConstant.class));
        expect(executorMock.getNumberOfParams()).andReturn(0);

        support.replayAll();
        testedObject.putConstant(name, body);
        support.verifyAll();
    }

    @Test(expected = ConstantWithParametersException.class)
    public void testAddConstant_useParams() throws Exception {
        final String name = "constantName";
        final String body = "body";
        final FunctionExecutor executorMock = support.createMock(FunctionExecutor.class);

        expect(functionParserMock.parse(body)).andReturn(executorMock);
        functionRepositoryMock.update(eq(name), anyObject(CustomConstant.class));
        expect(executorMock.getNumberOfParams()).andReturn(1);

        support.replayAll();
        testedObject.putConstant(name, body);
        support.verifyAll();
    }

    @Test
    public void testAddConstantValue() throws Exception {
        final String name = "constantName";
        final double value = 1.0;

        functionRepositoryMock.update(eq(name), anyObject(DoubleConstant.class));

        support.replayAll();
        testedObject.putConstant(name, value);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_putFunction() throws Exception {
        testedObject = support.createMockBuilder(Calculator.class).addMockedMethod("putFunction").createMock();
        final Command cmd = new Command.Builder().withName("func").withParam("name").withContent("body").build();

        testedObject.putFunction(cmd.getParam(), cmd.getContent());

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_putConstantExpression() throws Exception {
        testedObject = support.createMockBuilder(Calculator.class).addMockedMethod("putConstant", String.class,
                String.class).createMock();
        final Command cmd = new Command.Builder().withName("const").withParam("name").withContent("body").build();

        testedObject.putConstant(cmd.getParam(), cmd.getContent());

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_putConstantValue() throws Exception {
        testedObject = support.createMockBuilder(Calculator.class).addMockedMethod("putConstant", String.class,
                Double.class).createMock();
        final Command cmd = new Command.Builder().withName("s").withParam("name").build();

        testedObject.putConstant(cmd.getParam(), 0.0);

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_delete() throws Exception {
        final String name = "name";
        final Command cmd = new Command.Builder().withName("del").withParam(name).build();

        functionRepositoryMock.delete(name);

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_clear() throws Exception {
        final Command cmd = new Command.Builder().withName("c").build();

        functionRepositoryMock.update(eq(Calculator.ANS), anyObject(Function.class));

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_clearAll() throws Exception {
        final Command cmd = new Command.Builder().withName("ce").build();

        functionRepositoryMock.clear();
        functionRepositoryMock.update(eq(Calculator.ANS), anyObject(Function.class));

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }

    @Test
    public void testExecuteCommand_print() throws Exception {
        final Command cmd = new Command.Builder().withName("p").build();
        final String name1 = "name1";
        final String name2 = "name2";
        final Map<String, Function> map = new HashMap<>();
        map.put(name1, new DoubleConstant(1.0));
        map.put(name2, new BuiltinFunction.Sinus());

        expect(functionRepositoryMock.getFunctions()).andReturn(map);

        support.replayAll();
        CommandResult result = testedObject.executeCommand(cmd);
        support.verifyAll();

        assertTrue(result instanceof FunctionListResult);

        final FunctionListResult funcList = (FunctionListResult)result;
        assertEquals(2, funcList.getFunctions().size());
        assertTrue(funcList.getFunctions().containsKey(name1));
        assertTrue(funcList.getFunctions().containsKey(name2));

        final String str = result.getStringRepresentation();
        assertTrue(str.contains(name1));
        assertTrue(str.contains(name2));
        assertTrue(str.contains("<function>"));
        assertTrue(str.contains("<constant>"));
    }

    @Test
    public void testExecuteCommand_printBuiltin() throws Exception {
        final Command cmd = new Command.Builder().withName("pd").build();
        final String name = "name2";
        final Map<String, Function> map = new HashMap<>();
        map.put(name, new DoubleConstant(1.0));

        expect(functionRepositoryMock.getBuiltinFunctions()).andReturn(map);

        support.replayAll();
        CommandResult result = testedObject.executeCommand(cmd);
        support.verifyAll();

        assertTrue(result instanceof FunctionListResult);
        FunctionListResult funcList = (FunctionListResult)result;
        assertEquals(1, funcList.getFunctions().size());
        assertTrue(funcList.getFunctions().containsKey(name));
    }

    @Test(expected = UnknownCommandException.class)
    public void testExecuteCommand_unknown() throws Exception {
        Command cmd = new Command.Builder().withName("unknown").withParam("name").withContent("body").build();

        support.replayAll();
        testedObject.executeCommand(cmd);
        support.verifyAll();
    }
}
