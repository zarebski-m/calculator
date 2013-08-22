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

import calculator.function.Function;
import calculator.function.rpn.AbstractConstant;
import calculator.function.rpn.AbstractFunction;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class FunctionListResult implements CommandResult {
    private final SortedMap<String, Function> functions = new TreeMap<>();

    public FunctionListResult(final Map<String, Function> functions) {
        this.functions.putAll(functions);
    }

    @Override
    public String getStringRepresentation() {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, Function> var : functions.entrySet()) {
            sb.append(var.getKey()).append("\t");
            if (var.getValue() instanceof AbstractFunction) {
                sb.append("<function>");
            } else if (var.getValue() instanceof AbstractConstant) {
                sb.append("<constant>");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Map<String, Function> getFunctions() {
        return Collections.unmodifiableMap(functions);
    }
}
