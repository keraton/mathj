package org.keraton.mathj.reader.impl;

import org.junit.Before;
import org.junit.Test;
import org.keraton.mathj.reader.ValidationReaderException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.keraton.mathj.reader.impl.Lexeme.Type.*;

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

public class LexerTest {

    private LexemeBuilder lexemeBuilder;

    @Before public void
    setup() {
        lexemeBuilder = new LexemeBuilder();
    }

    private Lexeme getLexeme(Lexer lex) throws ValidationReaderException {
        Lexeme lexeme = null;
        if (lex.hasLexeme()) {
            lexeme = lex.nextLexeme();
        }
        return lexeme;
    }

    private List<Lexeme> getLexemes(Lexer lex) throws ValidationReaderException {
        List<Lexeme> lexemes = new ArrayList<Lexeme>();

        while (lex.hasLexeme()) {
            lexemes.add(lex.nextLexeme());
        }
        return lexemes;
    }

    private void assertThat(Lexeme.Type type, String token, Lexeme lexeme) {
        assertEquals(lexemeBuilder.setType(type).setToken(token).createLexeme(), lexeme);
    }

    @Test(expected = ValidationReaderException.class) public void
    should_throw_exception_when_using_unrecognized_char() throws ValidationReaderException {
        Lexer lex = new Lexer(" @");
        getLexeme(lex);
    }

    @Test(expected = ValidationReaderException.class) public void
    should_throw_exception_when_using_unrecognized_char_within_var() throws ValidationReaderException {
        Lexer lex = new Lexer(" x@");
        getLexeme(lex);
    }

    @Test(expected = ValidationReaderException.class) public void
    should_throw_exception_when_using_unrecognized_char_within_const() throws ValidationReaderException {
        Lexer lex = new Lexer(" 1@");
        getLexeme(lex);
    }

    @Test public void
    should_return_const_2 () throws ValidationReaderException {
        Lexer lex = new Lexer("2");

        assertThat(CONST,"2", getLexeme(lex));
    }

    @Test public void
    should_return_const_12 () throws ValidationReaderException {
        Lexer lex = new Lexer("12");

        assertThat(CONST,"12", getLexeme(lex));
    }

    @Test public void
    should_return_var_x () throws ValidationReaderException {
        Lexer lex = new Lexer("x");

        assertThat(VAR,"x",getLexeme(lex));
    }

    @Test public void
    should_return_var_x1 () throws ValidationReaderException {
        Lexer lex = new Lexer("x1 ");

        assertThat(VAR,"x1", getLexeme(lex));
    }

    @Test public void
    should_return_plus () throws ValidationReaderException {
        Lexer lex = new Lexer("+");

        assertThat(PLUS,null, getLexeme(lex));
    }

    @Test public void
    should_return_minus () throws  ValidationReaderException {
        Lexer lex = new Lexer("-");

        assertThat(MINUS, null, getLexeme(lex));
    }

    @Test public void
    should_return_multply () throws  ValidationReaderException {
        Lexer lex = new Lexer("*");

        assertThat(MULTIPLY, null, getLexeme(lex));
    }

    @Test public void
    should_return_divide () throws  ValidationReaderException {
        Lexer lex = new Lexer("/");

        assertThat(DIVIDE, null, getLexeme(lex));
    }

    @Test public void
    should_return_open () throws  ValidationReaderException {
        Lexer lex = new Lexer("(");

        assertThat(OPEN, null, getLexeme(lex));
    }

    @Test public void
    should_return_close () throws  ValidationReaderException {
        Lexer lex = new Lexer(")");

        assertThat(CLOSE, null, getLexeme(lex));
    }


    @Test public void
    should_return_var_x1_const_23 () throws ValidationReaderException {
        Lexer lex = new Lexer("x1 23");
        List<Lexeme> lexemes = getLexemes(lex);

        assertThat(VAR,   "x1", lexemes.get(0));
        assertThat(CONST, "23", lexemes.get(1));
    }


    @Test public void
    should_return_var_x1_plus_x2 () throws  ValidationReaderException {
        Lexer lex = new Lexer("x1+x2");
        List<Lexeme> lexemes = getLexemes(lex);

        assertThat(VAR,  "x1", lexemes.get(0));
        assertThat(PLUS, null, lexemes.get(1));
        assertThat(VAR,  "x2", lexemes.get(2));
    }

    @Test public void
    should_return_var_x1_plus_x2_with_spaces() throws ValidationReaderException {
        Lexer lex = new Lexer("x1 + x2");
        List<Lexeme> lexemes = getLexemes(lex);

        assertThat(VAR,  "x1", lexemes.get(0));
        assertThat(PLUS, null, lexemes.get(1));
        assertThat(VAR,  "x2", lexemes.get(2));
    }

    @Test public void
    should_return_EndOfDefinition() throws ValidationReaderException {
        Lexer lex = new Lexer("x1 23");
        lex.nextLexeme();
        lex.nextLexeme();

        assertThat(EOD, "", lex.nextLexeme());
    }

    @Test public void
    integration () throws ValidationReaderException {
        Lexer lex = new Lexer("(3x + y) * 3");
        List<Lexeme> lexemes = getLexemes(lex);

        assertThat(OPEN,     null, lexemes.get(0));
        assertThat(CONST,     "3", lexemes.get(1));
        assertThat(VAR,       "x", lexemes.get(2));
        assertThat(PLUS,     null, lexemes.get(3));
        assertThat(VAR,       "y", lexemes.get(4));
        assertThat(CLOSE,    null, lexemes.get(5));
        assertThat(MULTIPLY, null, lexemes.get(6));
        assertThat(CONST,    "3",  lexemes.get(7));
    }

    @Test public void
    should_print_at_start_on_single_letter() {
        Lexer lexer = new Lexer("3");

        assertEquals("[3]", lexer.printPosition());
    }

    @Test public void
    should_print_at_start() {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (

        assertEquals("[(]3x + y) * 3", lexer.printPosition());
    }

    @Test public void
    should_print_at_2nd_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (
        lexer.nextLexeme(); // 3

        assertEquals("([3]x + y) * 3", lexer.printPosition());
    }

    @Test public void
    should_print_at_3rd_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (
        lexer.nextLexeme(); // 3
        lexer.nextLexeme(); // x

        assertEquals("(3[x] + y) * 3", lexer.printPosition());
    }

    @Test public void
    should_print_at_4th_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (
        lexer.nextLexeme(); // 3
        lexer.nextLexeme(); // x
        lexer.nextLexeme(); // +

        assertEquals("(3x [+] y) * 3", lexer.printPosition());
    }

    @Test public void
    should_print_at_5th_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (
        lexer.nextLexeme(); // 3
        lexer.nextLexeme(); // x
        lexer.nextLexeme(); // +
        lexer.nextLexeme(); // y

        assertEquals("(3x + [y]) * 3", lexer.printPosition());
    }

    @Test public void
    should_print_at_6th_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (
        lexer.nextLexeme(); // 3
        lexer.nextLexeme(); // x
        lexer.nextLexeme(); // +
        lexer.nextLexeme(); // y
        lexer.nextLexeme(); // )

        assertEquals("(3x + y[)] * 3", lexer.printPosition());
    }

    @Test public void
    should_print_at_7th_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        // (
        lexer.nextLexeme(); // 3
        lexer.nextLexeme(); // x
        lexer.nextLexeme(); // +
        lexer.nextLexeme(); // y
        lexer.nextLexeme(); // )
        lexer.nextLexeme(); // *

        assertEquals("(3x + y) [*] 3", lexer.printPosition());
    }

     @Test public void
    should_print_at_before_last_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
         // (
         lexer.nextLexeme(); // 3
         lexer.nextLexeme(); // x
         lexer.nextLexeme(); // +
         lexer.nextLexeme(); // y
         lexer.nextLexeme(); // )
         lexer.nextLexeme(); // *
         lexer.nextLexeme(); // 3

        assertEquals("(3x + y) * [3]", lexer.printPosition());
    }

    @Test public void
    should_print_at_last_letter() throws ValidationReaderException {
        Lexer lexer = new Lexer("(3x + y) * 3");
        while (lexer.hasLexeme()) {
            lexer.nextLexeme();
        }
        assertEquals("(3x + y) * 3[]", lexer.printPosition());
    }


}
