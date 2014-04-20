package org.keraton.mathj.func.impl;

import org.junit.Before;
import org.junit.Test;
import org.keraton.mathj.MathJ;
import org.keraton.mathj.context.Context;
import org.keraton.mathj.context.VariableNotFoundException;
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

public class EquationWithVariableTest {


    private MathJ mathJ;

    @Before public void
    setup() {
        Context context = new MapContext();
        mathJ = new MathJ(context);
    }

    @Test(expected = VariableNotFoundException.class) public void
    givenThereIsNoMathVariable_InTheContext_thenThrowException_whenRun() {

        Variable var = new Variable("x");
        var.run(mathJ);
    }

    @Test public void
    givenInTheContextX_Set_to_3_then_FunctionVariableX_return_3() {
        mathJ.setContext("x", asConst(3));

        Variable var = new Variable("x");

        assertEquals(3, mathJ.apply(var).value());
    }

    @Test public void
    givenInTheContext_X_Y_Set_to_3_4_then_FunctionVariable_X_Y_return_3_4() {
        mathJ.setContext("x", asConst(3))
               .setContext("y", asConst(4));

        Variable var = new Variable("x");
        assertEquals(3, mathJ.apply(var).value());

        Variable var2 = new Variable("y");
        assertEquals(4, mathJ.apply(var2).value());
    }

}
