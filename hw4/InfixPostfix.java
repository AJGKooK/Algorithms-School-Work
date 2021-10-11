package edu.iastate.cs228.hw4;

/**
 * @author Aaron Goff

 * This class evaluates input infix and postfix expressions.
 */

/**
 *
 * This class evaluates input infix and postfix expressions. 
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class InfixPostfix {

    /**
     * Repeatedly evaluates input infix and postfix expressions.  See the project description
     * for the input description. It constructs a HashMap object for each expression and passes it
     * to the created InfixExpression or PostfixExpression object.
     *
     * @param args
     **/
    public static void main(String[] args) throws FileNotFoundException {
        Scanner user_input = new Scanner(System.in);
        Scanner user_input2 = new Scanner(System.in);

		int numOf = 1, varNum;
		String temp = "", isVar = "", userInExpression = "", userInFileName = "";

        System.out.println("Evaluation of Infix and Postfix Expressions" +
                "\nkeys: 1 (standard input) 2 (file input) 3 (exit)" +
				"\n(Enter \"I\" before an infix expression, \"P\" before a postfix expression)");

		System.out.print("Trial " + numOf + ": ");
		int userIn = user_input.nextInt();

        while (userIn == 1 || userIn == 2) {  // While loop in order to count number of final iterations


            if (userIn == 1) {
            	System.out.print("Expression: ");
            	userInExpression = user_input2.nextLine();

				/*
				Run InfixExpression under standard input
				Could not get InfixExpression.java to properly execute
				 */

                if (userInExpression.startsWith("I") || userInExpression.startsWith("i")) {  // Checking for 'I/i' for InfixExpression
                    for (int i = 2; i < userInExpression.length(); i++) {       // Removes first two characters of the string and stores variables
                        if (Expression.isVariable(userInExpression.charAt(i))) {
                            isVar += userInExpression.charAt(i);
                        }
                        temp += userInExpression.charAt(i);
                    }
                    HashMap<Character, Integer> varMap = new HashMap<>();       // Initialize HashMap
                    varMap = new HashMap<Character, Integer>();       // Initialize varMap
                    InfixExpression Infix = new InfixExpression(temp);        // Writing expression without varMap for toString() output
//					System.out.println("Infix form: " + Infix.toString());        // toString() output for Infix
                    System.out.println("Postfix form: " + Infix.toString());        // toString() output for Postfix
                    System.out.println("where\n");
                    for (int i = 0; i < isVar.length(); i++) {      // Populating varNum to put
                        System.out.print(isVar.charAt(i) + " = ");
                        varNum = user_input.nextInt();
                        varMap.put(isVar.charAt(i), varNum);
                    }
                    PostfixExpression Postfix2 = new PostfixExpression(temp, varMap);        // Writing expression with varMap for evaluate()
                    System.out.println("Expression value: " + Infix.evaluate() + "\n\n");

                }

				/*
				Run PostfixExpression under standard input
				 */

                else if (userInExpression.startsWith("P") || userInExpression.startsWith("p")) {  // Checking for 'P/p' for PostfixExpression
                    for (int i = 2; i < userInExpression.length(); i++) {       // Removes first two characters of the string and stores variables
                        if (Expression.isVariable(userInExpression.charAt(i))) {
                            isVar += userInExpression.charAt(i);
                        }
                        temp += userInExpression.charAt(i);
                    }
                    HashMap<Character, Integer> varMap = new HashMap<>();       // Initialize HashMap
                    varMap = new HashMap<Character, Integer>();       // Initialize varMap
                    PostfixExpression Postfix = new PostfixExpression(temp);        // Writing expression without varMap for toString() output
//					System.out.println("Infix form: " + InfixExpression.toString());        // toString() output for Infix
                    System.out.println("Postfix form: " + Postfix.toString());        // toString() output for Postfix
                    System.out.println("where");
                    for (int i = 0; i < isVar.length(); i++) {      // Populating varNum to put
                        System.out.print(isVar.charAt(i) + " = ");
                        varNum = user_input.nextInt();
                        varMap.put(isVar.charAt(i), varNum);
                    }
                    PostfixExpression Postfix2 = new PostfixExpression(temp, varMap);        // Writing expression with varMap for evaluate()
                    System.out.println("Expression value: " + Postfix2.evaluate() + "\n\n");

                }

				/*
				Incorrect input catch
				 */

                else {
                    System.out.println("Incorrect input, please try again");
                    numOf--;
                }

                numOf++;
				System.out.print("Trial " + numOf + ": ");
                userIn = user_input.nextInt();

            }

			/*
			Run from file
			 */

            else if (userIn == 2) {
                System.out.print("Input from a file\nEnter file name: ");

				try {
					userInFileName = user_input2.nextLine();

				/*
				Run InfixExpression under file input
				 */

                    if (userInFileName.startsWith("I") || userInFileName.startsWith("i")) {  // Checking for 'I/i' for InfixExpression
                        for (int i = 2; i < userInFileName.length(); i++) {       // Removes first two characters of the string and stores variables
                            if (Expression.isVariable(userInFileName.charAt(i))) {
                                isVar += userInFileName.charAt(i);
                            }
                            temp += userInFileName.charAt(i);
                        }
                        HashMap<Character, Integer> varMap = new HashMap<>();       // Initialize HashMap
                        varMap = new HashMap<Character, Integer>();       // Initialize varMap
                        InfixExpression Infix = new InfixExpression(temp);        // Writing expression without varMap for toString() output
//					System.out.println("Infix form: " + Infix.toString());        // toString() output for Infix
                        System.out.println("Postfix form: " + Infix.toString());        // toString() output for Postfix
                        System.out.println("where\n");
                        for (int i = 0; i < isVar.length(); i++) {      // Populating varNum to put
                            System.out.print(isVar.charAt(i) + " = ");
                            varNum = user_input.nextInt();
                            varMap.put(isVar.charAt(i), varNum);
                        }
                        PostfixExpression Postfix2 = new PostfixExpression(temp, varMap);        // Writing expression with varMap for evaluate()
                        System.out.println("Expression value: " + Infix.evaluate() + "\n\n");

                    }

				/*
				Run PostfixExpression under standard input
				 */

                    else if (userInFileName.startsWith("P") || userInFileName.startsWith("p")) {  // Checking for 'P/p' for PostfixExpression
                        for (int i = 2; i < userInFileName.length(); i++) {       // Removes first two characters of the string and stores variables
                            if (Expression.isVariable(userInFileName.charAt(i))) {
                                isVar += userInFileName.charAt(i);
                            }
                            temp += userInFileName.charAt(i);
                        }
                        HashMap<Character, Integer> varMap = new HashMap<>();       // Initialize HashMap
                        varMap = new HashMap<Character, Integer>();       // Initialize varMap
                        PostfixExpression Postfix = new PostfixExpression(temp);        // Writing expression without varMap for toString() output
//					System.out.println("Infix form: " + InfixExpression.toString());        // toString() output for Infix
                        System.out.println("Postfix form: " + Postfix.toString());        // toString() output for Postfix
                        System.out.println("where");
                        for (int i = 0; i < isVar.length(); i++) {      // Populating varNum to put
                            System.out.print(isVar.charAt(i) + " = ");
                            varNum = user_input.nextInt();
                            varMap.put(isVar.charAt(i), varNum);
                        }
                        PostfixExpression Postfix2 = new PostfixExpression(temp, varMap);        // Writing expression with varMap for evaluate()
                        System.out.println("Expression value: " + Postfix2.evaluate() + "\n\n");

                    }

				} catch (Exception e) {
					e.printStackTrace();
				}

			}


            numOf++;
			System.out.print("Trial " + numOf + ": ");
            userIn = user_input.nextInt();
//			}
        }

        /*
         * Exit program
         */

        if (userIn == 3) {
            user_input.close();
        } else {
            System.out.println("Incorrect user input");
        }
    }

    // helper methods if needed
}
