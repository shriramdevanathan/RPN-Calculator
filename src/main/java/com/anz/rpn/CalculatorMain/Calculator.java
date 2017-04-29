package com.anz.rpn.CalculatorMain;

import java.util.Stack;

import com.anz.rpn.Exception.CustomCalculatorException;
import com.anz.rpn.Instruction.Instruction;
import com.anz.rpn.Operator.Operator;

public class Calculator {

    private Stack<Double> stackRpn = new Stack<Double>();
    private Stack<Instruction> instructionsStack = new Stack<Instruction>();
    private int globalIndex = 0;
    private boolean undoOperation = false;

  
    private void evaluateToken(String token) throws CustomCalculatorException {
        Double value;
        try {
        	//Check for numeric
            value = Double.parseDouble(token);
            stackRpn.push(value);
            if (!undoOperation) {
                instructionsStack.push(null);
            }
        } catch (NumberFormatException nfe) {
        	//must be an operator
            processOperator(token);
        }
        
      
    }

  
    private void processOperator(String operatorString) throws CustomCalculatorException {

        if (stackRpn.isEmpty()) {
            throw new CustomCalculatorException("empty stack");
        }

        Operator operator = Operator.getEnum(operatorString);
        if (operator == null) {
            throw new CustomCalculatorException("invalid operator");
        }

        // clear value stack and instructions stack
        if (operator == Operator.CLEAR) {
        	 stackRpn.clear();
             instructionsStack.clear();
            return;
        }

        // undo evaluates the last instruction in stack
        if (operator == Operator.UNDO) {
        	undoOperation = true;
            undoLastInstruction();
            return;
        }

        if (operator.getOperandsNumber() > stackRpn.size()) {
            throwInvalidOperand(operatorString);
        }

        // getting operands
        Double firstOperand = stackRpn.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? stackRpn.pop() : null;
        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);

        if (result != null) {
            stackRpn.push(result);
            if (!undoOperation) {
                instructionsStack.push(new Instruction(Operator.getEnum(operatorString), firstOperand));
            }
        }

    }

    private void undoLastInstruction() throws CustomCalculatorException {
        if (instructionsStack.isEmpty()) {
            throw new CustomCalculatorException("no operations to undo");
        }

        Instruction lastInstruction = instructionsStack.pop();
        if (lastInstruction == null) {
            stackRpn.pop();
        } else {
            eval(lastInstruction.getReverseInstruction());
        }
    }


    private void throwInvalidOperand(String operator) throws CustomCalculatorException {
        throw new CustomCalculatorException(
                String.format("operator %s (position: %d): insufficient parameters", operator, globalIndex));
    }

    /**
     * Returns the values stackRpn
     */
    public Stack<Double> getstackRpn() {
        return stackRpn;
    }

 
    public void evaluateExpression(String input) throws CustomCalculatorException {
    	undoOperation = false;
    	eval(input);
    }

  
    private void eval(String input) throws CustomCalculatorException {
        if (input == null) {
            throw new CustomCalculatorException("Input is null. Please enter a valid number/operator.");
        }
        globalIndex = 0;
        String[] split = input.split(" ");
        for (String token : split) {
            globalIndex++;
            evaluateToken(token);
        }
    }
}
