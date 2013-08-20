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
package calculator.tokenizer;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpressionTokenizer implements ExpressionTokenizer {
    /**
     * Used to split without skipping delimiters. For example:
     * <code>"foo*bar".split(WITH_DELIMITER.format("[*]"))</code> becomes:
     * <code>"foo", "*", "bar"</code>
     */
    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    private final List<String> tokenStrings = new ArrayList<>();

    private int pos = 0;

    public SimpleExpressionTokenizer(final String input) {
        final String[] tokens = input.split(String.format(WITH_DELIMITER, "[*\\s+,/()^%-]"));
        for (final String token : tokens) {
            if (!token.isEmpty() && !token.matches("\\s")) {
                tokenStrings.add(token);
            }
        }
    }

    @Override
    public boolean hasNextToken() {
        return pos < tokenStrings.size();
    }

    @Override
    public String getNextToken() {
        String token = tokenStrings.get(pos);
        if (("-".equals(token) || "+".equals(token)) &&
                !isNumeric(getPrevious()) && isNumeric(getNext())) {
            token += tokenStrings.get(++pos);
        }
        ++pos;
        return token;
    }

    private String getPrevious() {
        return pos > 0 ? tokenStrings.get(pos - 1) : "";
    }

    private String getNext() {
        return pos < tokenStrings.size() - 1 ? tokenStrings.get(pos + 1) : "";
    }

    private static boolean isNumeric(final String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
