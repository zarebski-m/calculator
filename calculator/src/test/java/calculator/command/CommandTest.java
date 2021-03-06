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
package calculator.command;

import static org.junit.Assert.assertEquals;

import calculator.exception.command.CommandParseException;
import org.junit.Test;

public class CommandTest {
    @Test
    public void testBuild_parseSuccess() throws Exception {
        final String line = ":func param body";

        final Command cmd = new Command.Builder().parse(line).build();

        assertEquals(Command.CommandType.DefineFunction, cmd.getType());
        assertEquals("param", cmd.getParam());
        assertEquals("body", cmd.getContent());
    }

    @Test(expected = CommandParseException.class)
    public void testBuild_parseFailure() throws Exception {
        final String line = "not_a_command";

        new Command.Builder().parse(line).build();
    }
}
