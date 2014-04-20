package org.keraton.mathj.reader.impl;

import org.keraton.mathj.reader.ValidationReaderException;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;
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

/**
 * This is a lexical analysis, no grammar
 */
public class Lexer {

    private final String text;
    private int position = 0;

    public Lexer(String text) {
        this.text = text.trim();
    }

    public boolean hasLexeme() {
        return textIsNotEnded();
    }

    private boolean textIsNotEnded() {
        return position < text.length();
    }

    public Lexeme nextLexeme() throws ValidationReaderException {
        if (!textIsNotEnded()) {
            return new Lexeme(EOD,"");
        }
        char charAt = eliminateWhiteSpace(nextChar());

        if (isDigit(charAt)) {
            return getConstLexeme("");
        }
        else if (isLetter(charAt)) {
             return getVarLexeme("");
        }
        else if (isSpecialChar(charAt)) {
            return getSpecialCharLexeme(charAt);
        }
        throw new ValidationReaderException("Error in validation : " + text);
    }

    private char nextChar() {
        return text.charAt(position);
    }

    private int next() {
        return position++;
    }

    private int back() {
        return position--;
    }

    private char eliminateWhiteSpace(char charAt) {
        while (isWhitespace(charAt) && textIsNotEnded()) {
            charAt = text.charAt(next());
            if (!isWhitespace(charAt)) {
                back();
            }
        }
        return charAt;
    }

    private boolean isSpecialChar(char charAt) {
        return  charAt == '+' ||
                charAt == '-' ||
                charAt == '*' ||
                charAt == '/' ||
                charAt == '(' ||
                charAt == ')';
    }

    private Lexeme getConstLexeme(String token) throws ValidationReaderException {
        if (textIsNotEnded()){
            char charAt = nextChar();
            if (isDigit(charAt)) {
                token += charAt;
                next();
                return getConstLexeme(token);
            }
            else if(isWhitespace(charAt) || isSpecialChar(charAt) || isLetter(charAt)) {
                return new Lexeme(CONST, token);
            }
            else {
                throw new ValidationReaderException("Error in the " + token);
            }
        }
        else {
            return new Lexeme(CONST, token);
        }
    }

    private Lexeme getVarLexeme(String token) throws ValidationReaderException {
        if (textIsNotEnded()){
            char charAt = nextChar();
            if (isDigit(charAt) || isLetter(charAt)) {
                token += charAt;
                next();
                return getVarLexeme(token);
            }
            else if(isWhitespace(charAt) || isSpecialChar(charAt)) {
                return new Lexeme(VAR, token);
            }
            else {
                throw new ValidationReaderException("Error in the " + token);
            }
        }
        else {
            return new Lexeme(VAR, token);
        }
    }

    private Lexeme getSpecialCharLexeme(char charAt) throws ValidationReaderException {
        next();
        switch (charAt) {
            case ('+') : return new Lexeme(PLUS,     null);
            case ('-') : return new Lexeme(MINUS,    null);
            case ('*') : return new Lexeme(MULTIPLY, null);
            case ('/') : return new Lexeme(DIVIDE,   null);
            case ('(') : return new Lexeme(OPEN,     null);
            case (')') : return new Lexeme(CLOSE,    null);
            default: throw new ValidationReaderException("Error in the operand");
        }
    }

    public String printPosition() {
        if (position == text.length()) {
            return text + "[]";
        }
        int calculatePosition = position;
        while(isWhitespace(text.charAt(calculatePosition)) && calculatePosition < text.length())
            calculatePosition++;

        return  ((calculatePosition == 0) ? "" : text.substring(0, calculatePosition))
                + "[" + text.charAt(calculatePosition)  + "]"
                +((calculatePosition + 1 == text.length()) ? "" : text.substring(calculatePosition + 1, text.length()));
    }
}
