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
package calculator.parse.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import calculator.exception.FunctionNotDefinedException;
import calculator.function.FunctionFactory;
import org.junit.Before;
import org.junit.Test;

public class TokenFactoryTest {
    private static final double EPSILON = 1e-10;

    private TokenFactory testedObject;

    @Before
    public void setUp() {
        testedObject = new TokenFactory(new FunctionFactory());
    }

    @Test
    public void testGetToken_number() throws Exception {
        final double expected = 123.45e7;
        final String tokenString = String.valueOf(expected);

        final Token<?> token = testedObject.getToken(tokenString);

        assertEquals(Token.TokenType.Number, token.getTokenType());
        assertTrue(token instanceof NumberToken);
        final NumberToken numberToken = (NumberToken)token;
        assertEquals(expected, numberToken.getValue(), EPSILON);
    }

    @Test
    public void testGetToken_existingFunction() throws Exception {
        final String tokenString = "sin";

        final Token<?> token = testedObject.getToken(tokenString);

        assertEquals(Token.TokenType.Function, token.getTokenType());
        assertTrue(token instanceof OperatorToken);
    }

    @Test(expected = FunctionNotDefinedException.class)
    public void testGetToken_notExistingFunction() throws Exception {
        final String tokenString = "badFunction";

        testedObject.getToken(tokenString);
    }

    @Test
    public void testGetToken_openBracket() throws Exception {
        final String tokenString = "(";

        final Token<?> token = testedObject.getToken(tokenString);

        assertEquals(Token.TokenType.OpenBracket, token.getTokenType());
        assertTrue(token instanceof BracketToken.Open);
    }

    @Test
    public void testGetToken_closedBracket() throws Exception {
        final String tokenString = ")";

        final Token<?> token = testedObject.getToken(tokenString);

        assertEquals(Token.TokenType.ClosedBracket, token.getTokenType());
        assertTrue(token instanceof BracketToken.Closed);
    }

    @Test
    public void testGetToken_comma() throws Exception {
        final String tokenString = ",";

        final Token<?> token = testedObject.getToken(tokenString);

        assertEquals(Token.TokenType.Comma, token.getTokenType());
        assertTrue(token instanceof BracketToken.Comma);
    }
}
