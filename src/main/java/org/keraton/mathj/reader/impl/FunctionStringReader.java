package org.keraton.mathj.reader.impl;

import org.keraton.mathj.func.Function;
import org.keraton.mathj.func.impl.Constant;
import org.keraton.mathj.func.impl.Variable;
import org.keraton.mathj.reader.FunctionReader;
import org.keraton.mathj.reader.SyntaxException;
import org.keraton.mathj.reader.ValidationReaderException;

import static org.keraton.mathj.func.impl.Functions.asConst;
import static org.keraton.mathj.func.impl.Functions.asEq;
import static org.keraton.mathj.func.impl.Functions.asVar;

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

public class FunctionStringReader implements FunctionReader {

    private final Function EMPTY_FUNCTION = null;

    private Lexer lexer;
    private int deep = 0;

    @Override
    public Function read(String text) throws ValidationReaderException, SyntaxException {
        String toParse = text.trim();
        if (toParse.isEmpty()) {
            return asConst(0);
        }
        lexer = new Lexer(toParse);
        return parseExpression(EMPTY_FUNCTION);
    }

    private Function parseExpression(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CONST:
                function = parseConst(lexeme);
                break;
            case VAR:
                function = parseVar(lexeme);
                break;
            case OPEN:
                function = parseOpen(function);
                break;
            case MINUS:
                function = parseMinusConst(function);
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseConst(Lexeme lexeme) throws ValidationReaderException, SyntaxException {
        return parseAfterConst(lexemeToConst(lexeme));
    }

    private Function parseAfterConst(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case VAR:
                function = parseConstVar(lexeme, function);
                break;
            case PLUS:
                function = parsePlus(function);
                break;
            case MINUS:
                function = parseMinus(function);
                break;
            case MULTIPLY:
                function = parseMultiply(function);
                break;
            case DIVIDE:
                function = parseDivide(function);
                break;
            case CLOSE:
                close();
                break;
            case EOD:
                break;
            default:
                return throwSyntaxException();
        }
        return function;
    }

    private Function parseConstVar(Lexeme lexeme, Function function) throws ValidationReaderException, SyntaxException {
        return parseAfterConstVar(asEq(function).multiply(lexemeToVar(lexeme)));
    }

    private Function parseAfterConstVar(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case PLUS:
                function = parsePlus(function);
                break;
            case MINUS:
                function = parseMinus(function);
                break;
            case MULTIPLY:
                function = parseMultiply(function);
                break;
            case DIVIDE:
                function = parseDivide(function);
                break;
            case CLOSE:
                close();
                break;
            case EOD:
                break;
            default:
                return throwSyntaxException();
        }
        return function;
    }

    private Function throwSyntaxException() throws SyntaxException {
        throw new SyntaxException("Error Syntax at : " + lexer.printPosition());
    }

    private Function parsePlus(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CONST:
                function = asEq(function).add(parseConst(lexeme));
                break;
            case VAR:
                function = asEq(function).add(parseVar(lexeme));
                break;
            case OPEN:
                function = asEq(function).add(parseOpen(function));
                break;
            case MINUS:
                function = asEq(function).add(parseMinusConst(function));
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseOpen(Function function) throws ValidationReaderException, SyntaxException {
        int before = open();
        function = parseExpression(function);
        if (isNotClosed(before))
            throw new SyntaxException("");

        function = parseAfterClose(function);

        return function;
    }

    private boolean isNotClosed(int before) {
        return deep != before;
    }

    private Function parseMinusConst(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CONST:
                function = asEq(asConst(-1)).multiply(parseConst(lexeme));
                break;
            case VAR:
                function = asEq(asConst(-1)).multiply(parseVar(lexeme));
                break;
            case EOD:
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseAfterClose(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CLOSE:
                close();
                break;
            case PLUS:
                function = parsePlus(function);
                break;
            case MINUS:
                function = parseMinus(function);
                break;
            case MULTIPLY:
                function = parseMultiply(function);
                break;
            case DIVIDE:
                function = parseDivide(function);
                break;
            case EOD:
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseVar(Lexeme lexeme) throws ValidationReaderException, SyntaxException {
        return parseAfterVar(lexemeToVar(lexeme));
    }

    private Function parseAfterVar(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case PLUS:
                function = parsePlus(function);
                break;
            case CLOSE:
                close();
                break;
            case MINUS:
                function = parseMinus(function);
                break;
            case MULTIPLY:
                function = parseMultiply(function);
                break;
            case DIVIDE:
                function = parseDivide(function);
                break;
            case EOD:
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseDivide(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CONST:
                function = asEq(function).divided(lexemeToConst(lexeme));
                function = parseAfterConst(function);
                break;
            case VAR:
                function = asEq(function).divided(lexemeToVar(lexeme));
                function = parseAfterVar(function);
                break;
            case OPEN:
                open();
                function = asEq(function).divided(parseExpression(function));
                function = parseAfterClose(function);
                break;
            case MINUS:
                function = asEq(function).divided(parseMinusConst(function));
                break;
            case EOD:
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseMultiply(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CONST:
                function = asEq(function).multiply(lexemeToConst(lexeme));
                function = parseAfterConst(function);
                break;
            case VAR:
                function = asEq(function).multiply(lexemeToVar(lexeme));
                function = parseAfterVar(function);
                break;
            case OPEN:
                open();
                function = asEq(function).multiply(parseExpression(function));
                function = parseAfterClose(function);
                break;
            case MINUS:
                function = asEq(function).multiply(parseMinusConst(function));
                break;
            case EOD:
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private Function parseMinus(Function function) throws ValidationReaderException, SyntaxException {
        Lexeme lexeme = lexer.nextLexeme();
        switch (lexeme.getType()) {
            case CONST:
                function = asEq(function).minus(parseConst(lexeme));
                break;
            case VAR:
                function = asEq(function).minus(parseVar(lexeme));
                break;
            case OPEN:
                function = asEq(function).minus(parseOpen(function));
                break;
            default:
                throwSyntaxException();
        }
        return function;
    }

    private int open() throws SyntaxException {
        int before = deep;
        deep++;
        return before;
    }

    private void close() throws ValidationReaderException, SyntaxException {
        if (deep <= 0) {
            throwSyntaxException();
        }
        deep--;
    }

    private Variable lexemeToVar(Lexeme lexeme) {
        return asVar(lexeme.getToken());
    }

    private Constant lexemeToConst(Lexeme lexeme) {
        return asConst(Integer.parseInt(lexeme.getToken()));
    }

}
