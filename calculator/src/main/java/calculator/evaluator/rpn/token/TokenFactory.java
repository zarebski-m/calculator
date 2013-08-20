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
package calculator.evaluator.rpn.token;

import calculator.exception.execute.FunctionNotDefinedException;
import calculator.function.FunctionRepository;

public class TokenFactory {
    private final FunctionRepository functionRepository;

    public TokenFactory(final FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    public final Token<?> getToken(final String tokenString) throws FunctionNotDefinedException {
        switch (deduceTokenType(tokenString)) {
            case OpenBracket:
                return new BracketToken.Open(tokenString);
            case ClosedBracket:
                return new BracketToken.Closed(tokenString);
            case Comma:
                return new BracketToken.Comma(tokenString);
            case Number:
                return new NumberToken(tokenString);
            case Function:
                return new FunctionToken(tokenString, functionRepository.get(tokenString));
            default:
                throw new UnsupportedOperationException("Unsupported token type for token string: " + tokenString);
        }
    }

    private Token.TokenType deduceTokenType(final String tokenString) {
        if ("(".equals(tokenString)) {
            return Token.TokenType.OpenBracket;
        }
        if (")".equals(tokenString)) {
            return Token.TokenType.ClosedBracket;
        }
        if (",".equals(tokenString)) {
            return Token.TokenType.Comma;
        }

        try {
            Double.parseDouble(tokenString);
            return Token.TokenType.Number;
        } catch (NumberFormatException e) {
            // intentionally left blank
        }
        return Token.TokenType.Function;
    }
}
