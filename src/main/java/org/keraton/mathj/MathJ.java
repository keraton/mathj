package org.keraton.mathj;

import org.keraton.mathj.context.Context;
import org.keraton.mathj.func.Function;

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

public class MathJ implements Context {

    private int value;
    private Context context;

    public MathJ(Context context) {
        this.context = context;
    }

    public MathJ apply(Function func) {
        value = func.run(context);
        return this;
    }

    public int value() {
        return value;
    }

    public MathJ setContext(String key, Function value) {
        this.context.setContext(key, value);
        return this;
    }

    @Override
    public Function getContext(String key) {
        return this.context.getContext(key);
    }

    @Override
    public MathJ addContext(String key) {
        this.context.addContext(key);
        return this;
    }

    @Override
    public MathJ removeContext(String key) {
        this.context.removeContext(key);
        return this;
    }

    @Override
    public MathJ removeAll() {
        this.context.removeAll();
        return this;
    }


}
