package edu.iastate.cs228.hw4;

/**
 * @author Aaron Goff
 */

import Texting.Texting;

import java.util.HashMap;

/**
 * This class represents an infix expression. It implements infix to postfix conversion using
 * one stack, and evaluates the converted postfix expression.
 */

public class InfixExpression extends Expression {
    private String infixExpression;    // the infix expression to convert
    private boolean postfixReady = false;   // postfix already generated if true
    private int rankTotal = 0;        // Keeps track of the cumulative rank of the infix expression.

    private PureStack<Operator> operatorStack;      // stack of operators


    /**
     * Constructor stores the input infix string, and initializes the operand stack and
     * the hash map.
     *
     * @param st     input infix string.
     * @param varTbl hash map storing all variables in the infix expression and their values.
     */
    public InfixExpression(String st, HashMap<Character, Integer> varTbl) {
        super(null, varTbl);
        this.infixExpression = st;
        operatorStack = new ArrayBasedStack<Operator>();

    }


    /**
     * Constructor supplies a default hash map.
     *
     * @param s
     */
    public InfixExpression(String s) {
        super(null);
        this.infixExpression = s;
        operatorStack = new ArrayBasedStack<Operator>();
        // TODO
    }


    /**
     * Outputs the infix expression according to the format in the project description.
     */
    @Override
    public String toString() {

        return removeExtraSpaces(infixExpression);
    }


    /**
     * @return equivalent postfix expression, or
     * <p>
     * a null string if a call to postfix() inside the body (when postfixReady
     * == false) throws an exception.
     */
    public String postfixString() {

        if (postfixReady == true) {
            return infixExpression;
        } else
            try {
                postfix();
                return postfixExpression;
            } catch (Exception ex) {
                return null;
            }

    }


    /**
     * Resets the infix expression.
     *
     * @param st
     */
    public void resetInfix(String st) {

        infixExpression = st;
    }


    /**
     * Converts infix expression to an equivalent postfix string stored at postfixExpression.
     * If postfixReady == false, the method scans the infixExpression, and does the following
     * (for algorithm details refer to the relevant PowerPoint slides):
     * <p>
     * 1. Skips a whitespace character.
     * 2. Writes a scanned operand to postfixExpression.
     * 3. When an operator is scanned, generates an operator object.  In case the operator is
     * determined to be a unary minus, store the char '~' in the generated operator object.
     * 4. If the scanned operator has a higher input precedence than the stack precedence of
     * the top operator on the operatorStack, push it onto the stack.
     * 5. Otherwise, first calls outputHigherOrEqual() before pushing the scanned operator
     * onto the stack. No push if the scanned operator is ).
     * 6. Keeps track of the cumulative rank of the infix expression.
     * <p>
     * During the conversion, catches errors in the infixExpression by throwing
     * ExpressionFormatException with one of the following messages:
     * <p>
     * -- "Operator expected" if the cumulative rank goes above 1;
     * -- "Operand expected" if the rank goes below 0;
     * -- "Missing '('" if scanning a ‘)’ results in popping the stack empty with no '(';
     * -- "Missing ')'" if a '(' is left unmatched on the stack at the end of the scan;
     * -- "Invalid character" if a scanned char is neither a digit nor an operator;
     * <p>
     * If an error is not one of the above types, throw the exception with a message you define.
     * <p>
     * Sets postfixReady to true.
     */
    public void postfix() throws ExpressionFormatException {
        if (!postfixReady) {
            postfixExpression = "";
            int pars = 0;
            boolean trigger = false;
            for (int i = 0; i < infixExpression.length(); i++) {
                if (infixExpression.charAt(i) == ' ' || infixExpression.charAt(i) == '\t')
                    continue;
                /**
                 * This section scans for exception errors
                 */
                if (rankTotal > 1) {                                                // Cumulative rank above 1
                    throw new ExpressionFormatException("Operator expected");
                }
                if (rankTotal < 0) {                                                // Cumulative rank below 0
                    throw new ExpressionFormatException("Operand expected");
                }
                if (infixExpression.charAt(i) == '(') {                             // Trigger for checking ( )
                    pars++;
                }
                if (infixExpression.charAt(i) == ')') {                              // Trigger for checking ( )
                    pars--;
                }
                if (infixExpression.charAt(i) == ')' && pars < 0) {                  // Missing (
                    throw new ExpressionFormatException("Missing '('");
                }
                if (i == infixExpression.length() - 1 && pars != 0) {                  // Missing )
                    throw new ExpressionFormatException("Missing ')'");
                }
                if (!Expression.isOperator(infixExpression.charAt(i)) && !Expression.isInt(Character.toString(infixExpression.charAt(i)))) {
                    throw new ExpressionFormatException("Invalid character");         // Invalid Character
                }


                int numHold = 0;
                if (Expression.isInt(Character.toString(infixExpression.charAt(i)))) {
                    while (Expression.isInt(Character.toString(infixExpression.charAt(i)))) {
                        numHold++;
                        if (i != infixExpression.length() - 1) {                    // If program is not at the end of infixExpression, allow for infixExpression.substring(i-numHold,i) to run
                            i++;
                            trigger = true;                                         // Trigger switched to true to later run i-- and keep loop in balance
                            String intString = infixExpression.substring(i - numHold, i);
                            postfixExpression += intString;                         // Add intString to postfixExpression
                        } else {                                                    // If program is at the end of infixExpression, allow for infixExpression.substring(i) to run
                            String intString = infixExpression.substring(i);
                            postfixExpression += intString;                         // Add intString to postfixExpression
                            break;
                        }
                    }
                    if (trigger) {                                                  // If i++ was added earlier before loop ended (triggering this IF statement), i-- to keep loop in balance
                        i--;
                        trigger = false;
                    }

                } else {
                    Operator operator = new Operator(infixExpression.charAt(i));
                    if (operatorStack.isEmpty())                                    // If operator stack is empty, push first operator to stack
                        operatorStack.push(operator);
                    else if (operator.compareTo(operatorStack.peek()) <= 0)         // If compareTo() method returns 0 or -1, run outputHigherOrEqual() on operator
                        outputHigherOrEqual(operator);
                    if (infixExpression.charAt(i) != ')' && pars != 0)              // If charAt(i) is not ')' and there is a '(' bracket in the stack, push operator to operatorStack
                        operatorStack.push(operator);


                }

            }

            /**
             * This section scans for operators and generates an operator object
             */
            postfixReady = true;
        }
    }


    /**
     * This function first calls postfix() to convert infixExpression into postfixExpression. Then
     * it creates a PostfixExpression object and calls its evaluate() method (which may throw
     * an exception).  It also passes any exception thrown by the evaluate() method of the
     * PostfixExpression object upward the chain.
     *
     * @return value of the infix expression
     * @throws ExpressionFormatException, UnassignedVariableException
     */
    public int evaluate() {
        try {
            postfix();
        } catch (ExpressionFormatException e) {
            e.printStackTrace();
        }
        PostfixExpression expression = new PostfixExpression(postfixExpression);
        return expression.evaluate();
    }


    /**
     * Pops the operator stack and output as long as the operator on the top of the stack has a
     * stack precedence greater than or equal to the input precedence of the current operator op.
     * Writes the popped operators to the string postfixExpression.
     * <p>
     * If op is a ')', and the top of the stack is a '(', also pops '(' from the stack but does
     * not write it to postfixExpression.
     *
     * @param op current operator
     */
    private void outputHigherOrEqual(Operator op) {
        while (!operatorStack.isEmpty() && operatorStack.peek().compareTo(op) >= 0) {  // Run while operatorStack is not empty & compareTo(op) returns >= 0
            if (op.operator == ')' && operatorStack.peek().operator == '(') {
                operatorStack.pop();
            } else {
                postfixExpression += operatorStack.pop().getOp();                   // Write operatorStack.pop().getOp() to postfixExpression
            }
        }
        if (!operatorStack.isEmpty() && operatorStack.peek().compareTo(op) < 0 && op.operator != '(') {  // Run while operatorStack is not empty, & compareTo(op) < 0 & op.operator is not (
            if (operatorStack.peek().operator != '(' || operatorStack.peek().operator != '(') {
                postfixExpression += operatorStack.pop().getOp();                   // Write operatorStack.pop().getOp() to postfixExpression
            } else {
                operatorStack.pop().getOp();
            }
        }
    }

    protected static String removeExtraSpaces(String s) {
        StringBuilder sb = new StringBuilder(s.trim());

        for (int i = 1; i < sb.length(); i++) {
            if (sb.charAt(i) == '	') {                                 // If there is a keyboard tab, convert to to a keyboard space
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
        return sb.toString().trim();                                     // Trim the edges and return
    }

    // other helper methods if needed
}
