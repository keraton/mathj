package org.keraton.mathj.intj;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
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
public class BasicIntegerJTest {

	private IntegerJ figure;
	
	@Before
	public void setup() {
		figure = new IntegerJ();
	}
	
	private void initFigure(int i) {
		figure = new IntegerJ(i);
	}

	@Test public void 
	shouldStartWithZeroFigure() {
		assertEquals(0, figure.value());
	}
	
	@Test public void
	shouldInitWithX(){
		initFigure(1);
		assertEquals(1, figure.value());
	}
	
	@Test public void
	shouldSetWithX() {
		assertEquals(2, figure.set(2).value());
	}
	
	@Test public void
	shouldSetWithXmodeJ() {
		assertEquals(2, figure.set(new IntegerJ(2)).value());
	}
	
	@Test public void
	shouldXAddY(){
		initFigure(1);
		assertEquals(3, figure.add(2).value());
	}
	
	@Test public void
	shouldXAddYmodeJ(){
		initFigure(1);
		assertEquals(3, figure.add(new IntegerJ(2)).value());
	}
	
	@Test public void
	shouldXMinusY() {
		assertEquals(-1, figure.minus(1).value());
	}
	
	@Test public void
	shouldXMinusYmodeJ() {
		assertEquals(-1, figure.minus(new IntegerJ(1)).value());
	}
	
	@Test public void
	shouldXMultiplyY() {
		initFigure(1);
		assertEquals(6, figure.multiply(6).value());
	}

	@Test public void
	shouldXMultiplyYmodeJ() {
		initFigure(1);
		assertEquals(6, figure.multiply(new IntegerJ(6)).value());
	}
	
	@Test public void
	shouldXDividedY(){
		initFigure(6);
		assertEquals(1, figure.divided(6).value());
	}
	
	@Test public void
	shouldXDividedYmodeJ() {
		initFigure(6);
		assertEquals(1, figure.divided(new IntegerJ(6)).value());
	}
	
	@Test public void
	shouldXModuloY(){
		initFigure(6);
		assertEquals(0, figure.modulo(3).value());
	}
	
	@Test public void
	shouldXModuleYmodeJ() {
		initFigure(6);
		assertEquals(0, figure.modulo(new IntegerJ(3)).value());
	}
	
	@Test public void
	shouldXPowerY(){
		initFigure(2);
		assertEquals(8, figure.power(3).value());
	}
	
	@Test public void
	shouldXPowerYmodeJ(){
		initFigure(2);
		assertEquals(8, figure.power(new IntegerJ(3)).value());
	}
	
	@Test public void
	shouldXPower1(){
		initFigure(2);
		assertEquals(2, figure.power(1).value());
	}
	
	@Test public void
	shouldXPower0(){
		initFigure(2);
		assertEquals(1, figure.power(0).value());
	}
	
	@Test(expected=IllegalArgumentException.class) public void
	shouldFailWhenPowerNegative() {
		figure.power(-1);
	}
	
	@Test public void
	shouldAbsolute() {
		initFigure(-2);
		assertEquals(2, figure.abs().value());
	}
	
	@Test public void
	shouldIntegrate() {
		// 12 = (|2 - 4|) * 6 
		assertEquals(12, figure.add(2)
							   .minus(4)
							   .abs()
							   .multiply(6)
							   .value());
	}
	

}
