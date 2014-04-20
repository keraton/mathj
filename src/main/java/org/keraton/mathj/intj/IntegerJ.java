package org.keraton.mathj.intj;

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

@Deprecated
public class IntegerJ {
	
	private int value;

	public IntegerJ() {
	}

	public IntegerJ(int i) {
		this.value = i;
	}

	public int value() {
		return value;
	}
	
	public IntegerJ set(IntegerJ integerJ) {
		return set(integerJ.value);
	}
	
	public IntegerJ set(int i) {
		this.value = i;
		return this;
	}
	
	public IntegerJ add(IntegerJ integerJ) {
		return add(integerJ.value);
	}
	
	public IntegerJ add(int i) {
		this.value += i;
		return this;
	}
	
	public IntegerJ minus(IntegerJ integerJ) {
		return minus(integerJ.value);
	}

	public IntegerJ minus(int i) {
		this.value -= i;
		return this;
	}
	
	public IntegerJ multiply(IntegerJ integerJ) {
		return multiply(integerJ.value);
	}

	public IntegerJ multiply(int i) {
		this.value *= i;
		return this;
	}
	
	public IntegerJ divided(IntegerJ integerJ) {
		return divided(integerJ.value);
	}

	public IntegerJ divided(int i) {
		this.value /= i;
		return this;
	}
	
	public IntegerJ modulo(IntegerJ integerJ) {
		return modulo(integerJ.value);
	}

	public IntegerJ modulo(int i) {
		this.value %= i;
		return this;
	}

	public IntegerJ power(IntegerJ integerJ) {
		return power(integerJ.value);
	}
	
	public IntegerJ power(int i) {
		if (i>0) { 
			int temp = this.value;
			for (int j=1; j < i; j++) {
				this.value *= temp; 
			}
		}
		else if (i==0) {
			this.value = 1;
		}
		else {
			throw new IllegalArgumentException("Power Integer cannot negative");
		}
		return this;
	}

	public IntegerJ abs() {
		if (this.value < 0)
			this.value = this.value *-1;
		return this;
	}

}
