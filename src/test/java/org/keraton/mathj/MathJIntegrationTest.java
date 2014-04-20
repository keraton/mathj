package org.keraton.mathj;

import org.junit.Test;
import org.keraton.mathj.context.Context;
import org.keraton.mathj.context.impl.MapContext;
import org.keraton.mathj.func.impl.Equation;

import static org.junit.Assert.assertEquals;
import static org.keraton.mathj.func.impl.Functions.*;

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

public class MathJIntegrationTest {

    @Test public void
    should_run_integration_test() {
        Context context = new MapContext();
        MathJ mathJ = new MathJ(context);
        mathJ.addContext("x")
             .addContext("y");

        Equation eq_2x_add_2y = asEq(asConst(2)).multiply(asVar("x"))
                                                                .add(
                                asEq(asConst(2)).multiply(asVar("y"))
                                );

        // 2(0) + 2(0) = 0
        assertEquals(0, mathJ.apply(eq_2x_add_2y).value());

        // 2(3) + 2(4) = 14
        context.setContext("x", asConst(3))
                .setContext("y", asConst(4));
        assertEquals(14, mathJ.apply(eq_2x_add_2y).value());

        // Test when we set the eq in the context
        context.setContext("eq", eq_2x_add_2y);
        assertEquals(14, mathJ.apply(asVar("eq")).value());

    }
}
