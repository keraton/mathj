package org.keraton.mathj.func.impl;

import org.junit.Test;
import org.keraton.mathj.MathJ;
import org.keraton.mathj.context.Context;
import org.keraton.mathj.context.impl.MapContext;
import org.keraton.mathj.func.Function;

import static org.junit.Assert.assertEquals;

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

public class CustomEquationTest {
    
    protected class FunctionLinear implements Function {

        private final int a;
        private final int b;

        public FunctionLinear(int a, int b){
            this.a = a;
            this.b = b;
        }

        @Override
        public int run(Context context) {
            return (a + b);
        }

    }

    @Test public void
    linearFunctionExample() {
         // f(x) = ax + b;
        MathJ mathJ = new MathJ(new MapContext());
        FunctionLinear funcLin = new FunctionLinear(2, 3);
        FunctionLinear funcLin2 = new FunctionLinear(3, 3);

        // combination
        Equation eq = new Equation();
        eq.set(funcLin)
            .add(funcLin2);

        // Test equality
        for (int i=0; i<10; i++) {
            assertEquals(mathJ.apply(funcLin2).value() + mathJ.apply(funcLin).value(),
                    mathJ.apply(eq).value());
        }

    }


}
