package org.keraton.mathj.func.impl;

import org.junit.Before;
import org.junit.Test;
import org.keraton.mathj.MathJ;
import org.keraton.mathj.context.impl.MapContext;

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

public class EquationWithConstantTest {

    private MathJ mathJ;
    private Equation equation;


    @Before public void
    setUp() {
        mathJ = new MathJ(new MapContext());
        equation = new Equation();
    }

	@Test public void
	givenNothing_returnZero() {
		assertEquals(0, mathJ.apply(equation).value());
	}

    @Test public void
    givenAFunction_act_like_function() {
        Equation equation = new Equation(asConst(1));
        assertEquals(1, mathJ.apply(equation).value());
    }

    @Test public void
    shouldSetFunction() {
        equation.set(asConst(1));
        assertEquals(1, mathJ.apply(equation).value());
    }

    @Test public void
    shouldFunctionAddFunction() {
        equation.set(asConst(1)).add(asConst(2));
        assertEquals(3, mathJ.apply(equation).value());
    }

    @Test public void
    shouldFunctionMinusFunction() {
        equation.set(asConst(2)).minus(asConst(1));
        assertEquals(1, mathJ.apply(equation).value());
    }

    @Test public void
    shouldFunctionMultiplyFunction() {
        equation.set(asConst(2)).multiply(asConst(3));
        assertEquals(6, mathJ.apply(equation).value());
    }

    @Test public void
    shouldFunctionDividedFunction() {
        equation.set(asConst(6)).divided(asConst(2));
        assertEquals(3, mathJ.apply(equation).value());
    }

    @Test public void
    shouldFunctionPowerFunction() {
        // or and nor
    }

}