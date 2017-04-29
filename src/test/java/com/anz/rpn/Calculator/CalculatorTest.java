package com.anz.rpn.Calculator;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.Stack;

import org.junit.Test;

import com.anz.rpn.CalculatorMain.Calculator;
import com.anz.rpn.Exception.CustomCalculatorException;

public class CalculatorTest {
	
	/*
	 * 
	 * Below test cases are the ones validating the 8 examples mentioned in the 2nd page of the pdf.
	 */
	@Test
	public void testExamplesAnz() throws Exception {
        Calculator calculator = new Calculator();
        Double obtainedVal = 0D;
        
        
        //Example 1 test case
        calculator.evaluateExpression("5 2");
        Stack<Double> stack = calculator.getstackRpn();
        assertEquals(2, stack.pop(), 0);
        assertEquals(5, stack.pop(), 0);
         
        //Example 2 test case
        calculator = new Calculator();
        calculator.evaluateExpression("2 sqrt");
        DecimalFormat fmt = new DecimalFormat("0.##########");
        double d = Double.valueOf(fmt.format(Math.sqrt(new Double(2))));
        assertEquals(1, calculator.getstackRpn().size(),0);
        assertEquals(d,Double.valueOf(fmt.format(calculator.getstackRpn().pop())),0);
        
        //Example 3
        calculator = new Calculator();
        calculator.evaluateExpression("5 2 -");
        assertEquals(1, calculator.getstackRpn().size(),0);
        obtainedVal = calculator.getstackRpn().pop();
        assertEquals(3,obtainedVal,0);
        calculator.getstackRpn().push(obtainedVal);
        calculator.evaluateExpression("3 -");
        assertEquals(1, calculator.getstackRpn().size(),0);
        obtainedVal = calculator.getstackRpn().pop();
        assertEquals(0,obtainedVal,0);
        calculator.getstackRpn().push(obtainedVal);
        calculator.evaluateExpression("clear");
        assertEquals(0, calculator.getstackRpn().size(),0);
        
        
        //Example 4
        calculator = new Calculator();
        calculator.evaluateExpression("5 4 3 2");
        assertEquals(4, calculator.getstackRpn().size(),0);
        calculator.evaluateExpression("undo undo *");
        obtainedVal = calculator.getstackRpn().pop();
        assertEquals(20, obtainedVal,0);
        calculator.getstackRpn().push(obtainedVal);
        calculator.evaluateExpression("5 *");
        assertEquals(100, calculator.getstackRpn().get(0), 0);
        
        calculator.evaluateExpression("undo");
        obtainedVal = calculator.getstackRpn().pop();
        assertEquals(5, obtainedVal,0);
        obtainedVal = calculator.getstackRpn().pop();
        assertEquals(20, obtainedVal,0);
        
        //Example 5
        calculator = new Calculator();
        calculator.evaluateExpression("7 12 2 /");
        assertEquals(2, calculator.getstackRpn().size(),0);
        assertEquals(6, calculator.getstackRpn().get(1),0);
        calculator.evaluateExpression("*");
        assertEquals(42, calculator.getstackRpn().get(0),0);
        calculator.evaluateExpression("4 /");
        assertEquals(10.5, calculator.getstackRpn().get(0),0);
        
        //Example 6
        calculator = new Calculator();
        calculator.evaluateExpression("1 2 3 4 5");
        assertEquals(5, calculator.getstackRpn().size(),0);
        calculator.evaluateExpression("*");
        assertEquals(4, calculator.getstackRpn().size(),0);
        obtainedVal = calculator.getstackRpn().pop();
        assertEquals(20, obtainedVal, 0);
        calculator.getstackRpn().push(obtainedVal);
        calculator.evaluateExpression("clear 3 4 -");
        assertEquals(-1, calculator.getstackRpn().pop(), 0);
        
        //Example 7
        calculator = new Calculator();
        calculator.evaluateExpression("1 2 3 4 5");
        assertEquals(5, calculator.getstackRpn().size(),0);
        calculator.evaluateExpression("* * * *");
        assertEquals(120, calculator.getstackRpn().pop(),0);
        
        /*
         * 
         * Example 8(Ideally should be outside as another test case, but since this is part of the example, combining it here
         */
        calculator = new Calculator();
        try {
            calculator.evaluateExpression("1 2 3 * 5 + * * 6 5");
        } catch (CustomCalculatorException e) {
            assertEquals("operator * (position: 8): insufficient parameters", e.getMessage());
        }
        assertEquals(1, calculator.getstackRpn().size());
        assertEquals(11, calculator.getstackRpn().get(0), 0);
        
	}

	
	//	Other Necessary Tests
    @Test(expected = CustomCalculatorException.class)
    public void testNull() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluateExpression(null);
    }
    
	@Test(expected = CustomCalculatorException.class)
    public void testAlphaCharacters() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluateExpression("a b c 7 8 +");
    }
	
    @Test(expected = CustomCalculatorException.class)
    public void testOnlySymbolOperators() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluateExpression("+");
    }
    @Test(expected = CustomCalculatorException.class)
    public void testOnlyTextOperators() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluateExpression("sqrt");
    }

    @Test(expected = CustomCalculatorException.class)
    public void testNoWhiteSpace() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluateExpression("18+");
    }


}
