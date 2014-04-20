package org.keraton.mathj.reader.impl;

import org.junit.Before;
import org.junit.Test;
import org.keraton.mathj.MathJ;
import org.keraton.mathj.context.impl.MapContext;
import org.keraton.mathj.func.Function;
import org.keraton.mathj.reader.FunctionReader;
import org.keraton.mathj.reader.SyntaxException;
import org.keraton.mathj.reader.ValidationReaderException;

import static org.junit.Assert.assertEquals;
import static org.keraton.mathj.func.impl.Functions.asConst;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) [2014] [Mathj]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class FunctionStringReaderTest {

    private FunctionReader reader;
    private MathJ mathJ;

    private void setContext_X_With_2() {
        mathJ.setContext("x", asConst(2));
    }

    @Before public void
    setup() {
        reader = new FunctionStringReader();
        mathJ = new MathJ(new MapContext());
    }

    private int value(Function func) {
        return mathJ.apply(func).value();
    }

    @Test public void
    should_return_const_zero_when_using_empty_string () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("");

        assertEquals(0, value(func));
    }

    @Test public void
    should_return_const_zero_when_using_empty_with_spaces_string () throws ValidationReaderException, SyntaxException {
        Function func = reader.read(" ");

        assertEquals(0, value(func));
    }

    @Test public void // 20
    should_return_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("20");

        assertEquals(20, value(func));
    }

    @Test public void  // x
    should_return_x () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("x");
        setContext_X_With_2();

        assertEquals(2, value(func));
    }

    @Test public void  // 2x
    should_return_2x () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2x");
        setContext_X_With_2();

        assertEquals(4, value(func));
    }

    @Test public void  // 2x + 2
    should_return_2x_plus_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2x + 2");
        setContext_X_With_2();

        assertEquals(6, value(func));
    }

    @Test public void  // 2x * 2
    should_return_2x_multiply_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2x * 2");
        setContext_X_With_2();

        assertEquals(8, value(func));
    }

    @Test public void  // 2x - 2
    should_return_2x_minus_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2x - 2");
        setContext_X_With_2();

        assertEquals(2, value(func));
    }

    @Test public void  // 2x / 2
    should_return_2x_divided_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2x / 2");
        setContext_X_With_2();

        assertEquals(2, value(func));
    }

    @Test public void  // 2 + 2
    should_return_2_plus_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2 + 2");

        assertEquals(4, value(func));
    }

    @Test public void // 2 + 2 + 2
    should_return_2_plus_2_plus_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2 + 2 + 2");

        assertEquals(6, value(func));
    }

    @Test public void // x + x
    should_return_x_plus_x () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("x + x");
        setContext_X_With_2();

        assertEquals(4, value(func));
    }

    @Test public void // 2 + x
    should_return_2_plus_x () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2 + x");
        setContext_X_With_2();

        assertEquals(4, value(func));
    }

    @Test public void // 2x + 2x
    should_return_2x_plus_2x () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("2x + 2x");
        setContext_X_With_2();

        assertEquals(8, value(func));
    }

    @Test public void  // (2)
    should_return_$2£ () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("(2)");

        assertEquals(2, value(func));
    }

    @Test(expected = SyntaxException.class) public void
    should_return_$2 () throws ValidationReaderException, SyntaxException {
        reader.read("(2");
    }

    @Test(expected = SyntaxException.class) public void
    should_return_£2 () throws ValidationReaderException, SyntaxException {
        reader.read(")2");
    }

    @Test public void
    should_return_$$2££ () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("((2))");

        assertEquals(2, value(func));
    }

    @Test public void  // (2) + (2)
    should_return_$2£_plus_$2£ () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("(2) + (2)");

        assertEquals(4, value(func));
    }

    @Test public void // 2 + 2) + 2
    should_return_$2_plus_2£_plus_2 () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("(2 + 2) + 2");

        assertEquals(6, value(func));
    }

    @Test(expected = SyntaxException.class) public void // (2 + 2) + )2
    should_return_$2_plus_2£_plus_£2 () throws ValidationReaderException, SyntaxException {
        reader.read("(2 + 2) + )2");
    }

    @Test(expected = SyntaxException.class) public void // (2) 2
    should_return_$2£_2 () throws ValidationReaderException, SyntaxException {
        reader.read("(2) 2");
    }

    @Test public void  // (x)
    should_return_$x£ () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("(x)");
        setContext_X_With_2();

        assertEquals(2, value(func));
    }

    @Test public void // ((x))
    should_return_$$x££ () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("((x))");
        setContext_X_With_2();

        assertEquals(2, value(func));
    }

    @Test public void  // (x) + (x)
    should_return_$x£_plus_$x£ () throws ValidationReaderException, SyntaxException {
        Function func = reader.read("(x) + (x)");
        setContext_X_With_2();

        assertEquals(4, value(func));
    }

    @Test(expected = SyntaxException.class) public void
    should_return_£x () throws ValidationReaderException, SyntaxException {
        reader.read(")x");
    }

    @Test(expected = SyntaxException.class) public void
    should_return_x$ () throws ValidationReaderException, SyntaxException {
        reader.read("x(");
    }

    @Test(expected = SyntaxException.class) public void
    should_return_$_plus () throws ValidationReaderException, SyntaxException {
        reader.read("(+)");
    }

    @Test public void // (x + x) + (2 + 2)
    plus_integration () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("(x + x) + (2 + 2)");
        setContext_X_With_2();

        assertEquals(8, value(func));
    }

    @Test public void // 2 - 2
    should_2_minus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 - 2");

        assertEquals(0, value(func));
    }

    @Test public void  // 2 - x
    should_2_minus_x () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 - x");
        setContext_X_With_2();

        assertEquals(0, value(func));
    }

    @Test public void // x - x
    should_x_minus_x () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("x - x");
        setContext_X_With_2();

        assertEquals(0, value(func));
    }

    @Test public void  // (2) - (2)
    should_$2£_minus_$2£ () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("(2) - (2)");

        assertEquals(0, value(func));
    }

    @Test public void // -2
    should_minus_2() throws SyntaxException, ValidationReaderException {
        Function func = reader.read("-2");

        assertEquals(-2, value(func));
    }

    @Test public void // -x
    should_minus_x() throws SyntaxException, ValidationReaderException {
        Function func = reader.read("-x");
        setContext_X_With_2();

        assertEquals(-2, value(func));
    }

    @Test public void  // -2
    should_minus_$_minus_2£ () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("(-2)");

        assertEquals(-2, value(func));
    }

    @Test public void // 2 + -2
    should_minus_2_plus_minus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 + -2");

        assertEquals(0, value(func));
    }

    @Test public void // 2 * 2
    should_2_multiply_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 * 2");

        assertEquals(4, value(func));
    }

    @Test public void // 2 * x
    should_2_multiply_x () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 * x");
        setContext_X_With_2();

        assertEquals(4, value(func));
    }

    @Test public void // x * x
    should_x_multiply_x () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("x * x");
        setContext_X_With_2();

        assertEquals(4, value(func));
    }

    @Test public void // (2) * 2
    should_$2£_multiply_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("(2) * 2 ");

        assertEquals(4, value(func));
    }

    @Test public void // 2 * (2)
    should_2_multiply_$2£ () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 *  (2)");

        assertEquals(4, value(func));
    }

    @Test public void  // 2 * -2
    should_2_multiply_minus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 * -2");

        assertEquals(-4, value(func));
    }

    @Test public void  // -2 * 2
    should_minus2_multiply_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" -2 * 2 ");

        assertEquals(-4, value(func));
    }

    @Test public void  // 2 + 2 * 2
    should_2_plus_2_multiply_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 + 2 * 3 ");

        assertEquals(8, value(func));
    }

    @Test public void  // 2 * (2 + 2) + 2
    should_2_multiply_$2_plus_2£_plus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 * (2 + 2) + 2");

        assertEquals(10, value(func));
    }

    @Test public void  // 2 * ((2 + 2)) + 2
    should_2_multiply_$$2_plus_2££_plus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 * ((2 + 2)) + 2");

        assertEquals(10, value(func));
    }

    @Test public void  // 2 * 3 + 2
    should_2_multiply_3_plus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 * 3 + 2");

        assertEquals(8, value(func));
    }

    @Test public void  // 3 * x + 2
    should_3_multiply_x_plus_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 3 * x + 2");
        setContext_X_With_2();

        assertEquals(8, value(func));
    }

    @Test public void  // 2 / 2
    should_2_divide_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 / 2");

        assertEquals(1, value(func));
    }

    @Test public void  // 2 / x
    should_2_divide_x () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 / x");
        setContext_X_With_2();

        assertEquals(1, value(func));
    }

    @Test public void  // x / x
    should_x_divide_x () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("x/x");
        setContext_X_With_2();

        assertEquals(1, value(func));
    }

    @Test public void  // (2) / 2
    should_$2£_divide_2 () throws SyntaxException, ValidationReaderException {
        Function func = reader.read("(2) / 2");

        assertEquals(1, value(func));
    }

    @Test public void  // 2 / (2)
    should_2_divide_$2£ () throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 /(2)");

        assertEquals(1, value(func));
    }

    @Test public void  // 2 / -2
    should_2_divide_minus2() throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 / -2");

        assertEquals(-1, value(func));
    }

    @Test public void  // 2 + 2 / 2
    should_2_plus_2_divide_3() throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 + 2 / 2");

        assertEquals(3, value(func));
    }

    @Test public void  // 2 / 2 + 2
    should_2_divide_2_plus_2() throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 / 2 + 2");

        assertEquals(3, value(func));
    }

    @Test public void // 2 / 2 + 2 + 2
    should_2_divide_2_plus_2_plus_2() throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 / 2 + 2 + 2");

        assertEquals(5, value(func));
    }

    @Test public void // 2 / x + 2
    should_2_divide_x_plus_2() throws SyntaxException, ValidationReaderException {
        Function func = reader.read(" 2 / x + 2 ");
        setContext_X_With_2();

        assertEquals(3, value(func));
    }

    @Test public void  // 2 / (1+1) + 2
    should_2_divide_$1_plus_1£_plus_2() throws SyntaxException, ValidationReaderException {
        Function func =reader.read("2 / (1 + 1) + 2");

        assertEquals(3, value(func));
    }

    @Test public void // 2 / 2 - 1
    should_2_divide_2_minus_1() throws SyntaxException, ValidationReaderException {
        Function func = reader.read("2 / 2 - 1");

        assertEquals(0, value(func));
    }

    @Test public void // 2 / ((1 + 1) + 2
    should_2_divide_$$1_plus_1££_plus_2() throws SyntaxException, ValidationReaderException {
        Function func =reader.read("2 / ((1 + 1)) + 2");

        assertEquals(3, value(func));
    }

    @Test public void // (((2 +2)) + (2 + 2))
    should_$$2_plus_2£_plus_$2_plus_2££() throws SyntaxException, ValidationReaderException {
        Function func = reader.read("(((2 +2)) + (2 + 2))");

        assertEquals(8, value(func));
    }

    @Test(expected = SyntaxException.class) public void
    should_failed_open_open_close () throws SyntaxException, ValidationReaderException {
        reader.read("((2 + 3 + 3)");
    }

    @Test(expected = SyntaxException.class) public void
    should_failed_open_close_close () throws SyntaxException, ValidationReaderException {
        reader.read("(2 + 3 + 3))");
    }

    @Test(expected = SyntaxException.class) public void
    should_failed_open_open_close_close_close () throws SyntaxException, ValidationReaderException {
        reader.read("((2 + 3) + 3)))");
    }

}
