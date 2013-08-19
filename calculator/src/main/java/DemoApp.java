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

import calculator.Calculator;
import calculator.command.Command;
import calculator.exception.command.UnknownCommandException;
import calculator.exception.execute.ExpressionExecuteException;
import calculator.exception.parse.FunctionParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class DemoApp implements Runnable {
    public DemoApp() {
    }

    public static void main(final String[] args) throws IOException {
        DemoApp application = new DemoApp();
        application.run();
    }

    private String readExpression(final BufferedReader reader) {
        try {
            System.out.print("> ");
            return reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private void writeResult(double result) {
        System.out.println(result);
    }

    private boolean isCommand(final String line) {
        return line.startsWith(":");
    }

    @Override
    public void run() {
        final Calculator calc = new Calculator();

        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        String line = readExpression(reader);

        while (!"exit".equalsIgnoreCase(line)) {
            try {
                if (isCommand(line)) {
                    calc.executeCommand(new Command.Builder().parse(line).build());
                } else {
                    calc.evaluateExpression(line);
                    writeResult(calc.getResult());
                }
            } catch (ExpressionExecuteException | FunctionParseException | UnknownCommandException ex) {
                ex.printStackTrace();
            }
            line = readExpression(reader);
        }
    }
}
