package edu.iastate.cs228.hw4;

/**
 *
 * @author Aaron Goff
 *
 */

import java.util.HashMap;

public abstract class Expression {
    protected String postfixExpression;
    protected HashMap<Character, Integer> varTable; // hash map to store variables in the


    /**
     * Initialization with a provided hash map.
     *
     * @param varTbl
     */
    protected Expression(String st, HashMap<Character, Integer> varTbl) {
        postfixExpression = st;
        this.varTable = varTbl;
    }


    /**
     * Initialization with a default hash map.
     *
     * @param st
     */
    protected Expression(String st) {
        postfixExpression = st;
        this.varTable = new HashMap<>();
    }


    /**
     * Setter for instance variable varTable.
     *
     * @param varTbl
     */
    public void setVarTable(HashMap<Character, Integer> varTbl) {
        this.varTable = varTbl;
    }


    /**
     * Evaluates the infix or postfix expression.
     *
     * @return value of the expression
     * @throws ExpressionFormatException, UnassignedVariableException
     */
    public abstract int evaluate() throws ExpressionFormatException, UnassignedVariableException;


    // --------------------------------------------------------
    // Helper methods for InfixExpression and PostfixExpression
    // --------------------------------------------------------

    /**
     * Checks if a string represents an integer.  You may call the static method
     * Integer.parseInt().
     *
     * @param s
     * @return
     */
    protected static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    /**
     * Checks if a char represents an operator, i.e., one of '~', '+', '-', '*', '/', '%', '^', '(', ')'.
     *
     * @param c
     * @return
     */
    protected static boolean isOperator(char c) {
        if (c == '~' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '(' || c == ')') {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Checks if a char is a variable, i.e., a lower case English letter.
     *
     * @param c
     * @return
     */
    protected static boolean isVariable(char c) {
        if (c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' ||
                c == 'j' || c == 'k' || c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' ||
                c == 's' || c == 't' || c == 'u' || c == 'v' || c == 'w' || c == 'x' || c == 'y' || c == 'z') {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Removes extra blank spaces in a string.
     *
     * @param s
     * @return
     */
    protected static String removeExtraSpaces(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 1; i < sb.length(); i++) {
            if (sb.charAt(i) == '	') {                                 // If there is a keyboard tab, convert to to a keyboard space
                sb.deleteCharAt(i);
                sb.insert(i, ' ');
            }

            if (sb.charAt(i) == ' ' && sb.charAt(i - 1) == ' ') {       // If there are two spaces next to each other, delete the second space
                sb.deleteCharAt(i);
                i--;
            }
        }
        String returnString = sb.toString();
        return returnString.trim();                                     // Trim the edges and return
    }
}