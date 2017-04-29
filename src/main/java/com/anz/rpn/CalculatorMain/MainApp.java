package com.anz.rpn.CalculatorMain;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.anz.rpn.Exception.CustomCalculatorException;

public class MainApp {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Calculator calculator = new Calculator();
		
		while (true) {
			System.out.println("Input Expression: ");
			System.out.println();
			String expression = scanner.nextLine();
			if (expression.equals("quit")) {
				scanner.close();
				break;
			} else {
				try {
					calculator.evaluateExpression(expression);
				} catch (CustomCalculatorException e) {
					System.out.println(e.getMessage());
				}
				//10 decimal places display
				DecimalFormat fmt = new DecimalFormat("0.##########");
				Stack<Double> stack = calculator.getstackRpn();
				//as per question
				System.out.print("Stack: ");
				for (Double value : stack) {
					System.out.print(fmt.format(value) + " ");
				}
				System.out.printf("%n");
			}
		}
	}
}
