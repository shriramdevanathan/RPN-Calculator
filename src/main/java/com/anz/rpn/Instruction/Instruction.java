package com.anz.rpn.Instruction;

import com.anz.rpn.Exception.CustomCalculatorException;
import com.anz.rpn.Operator.Operator;

public class Instruction {
    Operator operator;
    Double value;

    public Instruction(Operator operator, Double value) {
        this.operator = operator;
        this.value = value;
    }

    public String getReverseInstruction() throws CustomCalculatorException {
    	int numberOfOperands = operator.getOperandsNumber();
    	String ret = null;
    	switch(numberOfOperands){
    	case 0:
    		throw new CustomCalculatorException(String.format("invalid operation for operator %s", operator.getSymbol()));
    	case 1:
    		ret = String.format("%s", operator.getOpposite());
    		break;
    	default:
    		ret = String.format("%f %s %f", value, operator.getOpposite(), value);
    		break;
    		
    	}
    	return ret;
        
    }
}
