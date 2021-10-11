package edu.iastate.cs228.hw4;

/**
 * @author Aaron Goff
 */

/**
 *
 * This class evaluates a postfix expression using one stack.    
 *
 */

import java.util.HashMap;
import java.util.NoSuchElementException;

public class PostfixExpression extends Expression {
    private int leftOperand;            // left operand for the current evaluation step
    private int rightOperand;           // right operand (or the only operand in the case of
    // a unary minus) for the current evaluation step

    private PureStack<Integer> operandStack;  // stack of operands


    /**
     * Constructor stores the input postfix string and initializes the operand stack.
     *
     * @param st      input postfix string.
     * @param varTbl  hash map that stores variables from the postfix string and their values.
     */
    public PostfixExpression(String st, HashMap<Character, Integer> varTbl) {
        super(st, varTbl);
        operandStack = new ArrayBasedStack<Integer>();
    }


    /**
     * Constructor supplies a default hash map.
     *
     * @param s
     */
    public PostfixExpression(String s) {
        super(s);
        operandStack = new ArrayBasedStack<Integer>();
    }


    /**
     * Outputs the postfix expression according to the format in the project description.
     */
    @Override
    public String toString() {

        return removeExtraSpaces(postfixExpression);
    }


    /**
     * Resets the postfix expression.
     * @param st
     */
    public void resetPostfix(String st) {
        postfixExpression = st;
    }


    /**
     * Scan the postfixExpression and carry out the following:  
     *
     *    1. Whenever an integer is encountered, push it onto operandStack.
     *    2. Whenever a binary (unary) operator is encountered, invoke it on the two (one) elements popped from  
     *       operandStack,  and push the result back onto the stack.  
     *    3. On encountering a character that is not a digit, an operator, or a blank space, stop 
     *       the evaluation. 
     *
     * @return value of the postfix expression 
     * @throws ExpressionFormatException with one of the messages below: 
     *
     *           -- "Invalid character" if encountering a character that is not a digit, an operator
     *              or a whitespace (blank, tab); 
     *           --	"Too many operands" if operandStack is non-empty at the end of evaluation; 
     *           -- "Too many operators" if getOperands() throws NoSuchElementException; 
     *           -- "Divide by zero" if division or modulo is the current operation and rightOperand == 0;
     *           -- "0^0" if the current operation is "^" and leftOperand == 0 and rightOperand == 0;
     *           -- self-defined message if the error is not one of the above.
     *
     *         UnassignedVariableException if the operand as a variable does not have a value stored
     *            in the hash map.  In this case, the exception is thrown with the message
     *
     *           -- "Variable <name> was not assigned a value", where <name> is the name of the variable.  
     *
     */
    public int evaluate() {
        postfixExpression = postfixExpression.trim();                                                   // Trim edges
        for (int i = 0; i < postfixExpression.length(); i++) {                                          // Start for loop for length of postfixExpression
            if (postfixExpression.charAt(i) == ' ' || postfixExpression.charAt(i) == '\t') continue;    // Skip whitespaces
            if (isInt(Character.toString(postfixExpression.charAt(i)))) {                               // checking if postfixExpression.charAt(i) is an int
                int numHold = 0;
                while (isInt(Character.toString(postfixExpression.charAt(i)))) {                        // while counter to allow for multiple digit int numbers
                    i++;
                    numHold++;
                }
                operandStack.push(Integer.parseInt(postfixExpression.substring(i - numHold, i)));       // Push int to stack
            } else if (isVariable(postfixExpression.charAt(i))) {                                       // Check if postfixExpression.charAt(i) is a variable
                operandStack.push(varTable.getOrDefault(postfixExpression.charAt(i), 0));   // Push variable to stack
            } else {
                char op = postfixExpression.charAt(i);                                                  // Store operand from postfixExpression.charAt(i) to op
                getOperands(op);                                                                        // Identify operand
                operandStack.push(compute(op));                                                         // Compute and push to stack
            }
        }
        return operandStack.pop();                                                                      // Pop from stack
    }


    /**
     * For unary operator, pops the right operand from operandStack, and assign it to rightOperand. The stack must have at least
     * one entry. Otherwise, throws NoSuchElementException.
     * For binary operator, pops the right and left operands from operandStack, and assign them to rightOperand and leftOperand, respectively. The stack must have at least
     * two entries. Otherwise, throws NoSuchElementException.
     * @param op
     * 			char operator for checking if it is binary or unary operator.
     */
    private void getOperands(char op) throws NoSuchElementException {
        if (op == '~') {
            if (operandStack.isEmpty())             // when op is ~, if operandStack is empty, pop from operandStack and store in rightOperand
                throw new NoSuchElementException();
            rightOperand = operandStack.pop();
        } else {
            if (operandStack.size() < 2)            // if there are two operands, pop from stack and store in leftOperand and rightOperand
                throw new NoSuchElementException();
            leftOperand = operandStack.pop();
            rightOperand = operandStack.pop();
        }
    }


    /**
     * Computes "leftOperand op rightOprand" or "op rightOprand" if a unary operator.
     *
     * @param op operator that acts on leftOperand and rightOperand.
     * @return
     */
    private int compute(char op) {
        if (op == '+') {                            // Addition
            return rightOperand + leftOperand;
        } else if (op == '-') {                     // Subtraction
            return rightOperand - leftOperand;
        } else if (op == '*') {                     // Multiplication
            return rightOperand * leftOperand;
        } else if (op == '/') {                     // Division
            return rightOperand / leftOperand;
        } else if (op == '%') {                     // Modulo
            return rightOperand % leftOperand;
        } else if (op == '^') {                     // Power of
            return (int) Math.pow(rightOperand, leftOperand);
        } else if (op == '~') {                     // Negative number (urinary)
            return -1 * rightOperand;
        } else {
            // ????                                 // Else return 0
            return 0;
        }
    }

    protected static String removeExtraSpaces(String s) {
        StringBuilder sb = new StringBuilder(s.trim());

        for (int i = 1; i < sb.length(); i++) {
            if (sb.charAt(i) == '	') {                            // If there is a keyboard tab, convert to to a keyboard space
                sb.deleteCharAt(i);
                sb.insert(i, ' ');
            }
            if ((sb.charAt(i) == ' ' && sb.charAt(i - 1) == ' ') || (sb.charAt(i) == ' ' && sb.charAt(i - 1) == '(')) {
                sb.deleteCharAt(i);                                            // If there are two spaces next to each other, delete the second space
                i--;                                                           // Delete the space after ( to properly execute JUnit test
            }
            if (sb.charAt(i) == ')' && sb.charAt(i - 1) == ' ') {              // Delete the space before ) to properly execute JUnit test
                sb.deleteCharAt(i - 1);
                i--;
            }

        }
        return sb.toString().trim();                                           // Trim the edges and return
    }
}
