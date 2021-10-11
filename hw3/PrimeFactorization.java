package edu.iastate.cs228.hw3;

/**
 * @author Aaron Goff
 */

import java.util.ListIterator;

import static java.lang.Math.sqrt;

public class PrimeFactorization implements Iterable<PrimeFactor> {
    private static final long OVERFLOW = -1;
    private long value;    // the factored integer
    // it is set to OVERFLOW when the number is greater than 2^63-1, the
    // largest number representable by the type long.

    /**
     * Reference to dummy node at the head.
     */
    private Node head;

    /**
     * Reference to dummy node at the tail.
     */
    private Node tail;

    private int size;        // number of distinct prime factors


    // ------------
    // Constructors
    // ------------

    /**
     * Default constructor constructs an empty list to represent the number 1.
     * <p>
     * Combined with the add() method, it can be used to create a prime factorization.
     */
    public PrimeFactorization() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;

        size = 0;
        this.value = 1;
    }


    /**
     * Obtains the prime factorization of n and creates a doubly linked list to store the result.
     * Follows the direct search factorization algorithm in Section 1.2 of the project description.
     *
     * @param n
     * @throws IllegalArgumentException if n < 1
     */
    public PrimeFactorization(long n) throws IllegalArgumentException {
        this();
        if (n < 1) {
            throw new IllegalArgumentException("List size must be greater than 1");
        }
        value = n;
        int tempExp = 0;
        long tempNum = n;


    }


    /**
     * Copy constructor. It is unnecessary to verify the primality of the numbers in the list.
     *
     * @param pf
     */
    public PrimeFactorization(PrimeFactorization pf) {
        this.head = new Node();
        this.tail = new Node();

        PrimeFactorizationIterator pzi = pf.iterator();
        Node node = new Node();
        while (pzi.hasNext()){
            this.add(node.pFactor.prime, node.pFactor.multiplicity);
            pzi.next();
        }



        // TODO
    }

    /**
     * Constructs a factorization from an array of prime factors.  Useful when the number is
     * too large to be represented even as a long integer.
     *
     * @param // pflist
     */
    public PrimeFactorization(PrimeFactor[] pfList) {
        this.head = new Node();
        this.tail = new Node();

        PrimeFactor[] arr = new PrimeFactor[size];
        for(int i = 0; i < pfList.length; i++){
            arr[i] = pfList[i];

        }


        // TODO
    }


    // --------------
    // Primality Test
    // --------------

    /**
     * Test if a number is a prime or not.  Check iteratively from 2 to the largest
     * integer not exceeding the square root of n to see if it divides n.
     *
     * @param n
     * @return true if n is a prime
     * false otherwise
     */
    public static boolean isPrime(long n) {

        if (n == 2) {
            return true;
        } else if (n % 2 == 0 || n < 2) {
            return false;
        } else {
            for (long i = 3; i < sqrt(n); i = +2) {
                if (n % i == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }
    }


    // ---------------------------
    // Multiplication and Division
    // ---------------------------

    /**
     * Multiplies the integer v represented by this object with another number n.  Note that v may
     * be too large (in which case this.value == OVERFLOW). You can do this in one loop: Factor n and
     * traverse the doubly linked list simultaneously. For details refer to Section 3.1 in the
     * project description. Store the prime factorization of the product. Update value and size.
     *
     * @param n
     * @throws IllegalArgumentException if n < 1
     */
    public void multiply(long n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException("multiply(long n) n < 1");
        } else {
            this.value *= n;
        }

        // TODO
    }

    /**
     * Multiplies the represented integer v with another number in the factorization form.  Traverse both
     * linked lists and store the result in this list object.  See Section 3.1 in the project description
     * for details of algorithm.
     *
     * @param pf
     */
    public void multiply(PrimeFactorization pf) {
        PrimeFactorizationIterator pz = pf.iterator();
        Node node = new Node();

        while (pz.hasNext()) {
            if (node.pFactor.prime % 2 == 0) {
                node.pFactor.multiplicity++;
            }
            for (int i = 3; i < sqrt(value); i += 2) {
                while (i % value == 0) {
                    node.pFactor.multiplicity++;
                }
            }

        }

        // TODO
    }


    /**
     * Multiplies the integers represented by two PrimeFactorization objects.
     *
     * @param pf1
     * @param pf2
     * @return object of PrimeFactorization to represent the product
     */
    public static PrimeFactorization multiply(PrimeFactorization pf1, PrimeFactorization pf2) {


        // TODO
        return null;
    }


    /**
     * Divides the represented integer v by n.  Make updates to the list, value, size if divisible.
     * No update otherwise. Refer to Section 3.2 in the project description for details.
     *
     * @param n
     * @return true if divisible
     * false if not divisible
     * @throws IllegalArgumentException if n <= 0
     */
    public boolean dividedBy(long n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        return true;
        // TODO
    }


    /**
     * Division where the divisor is represented in the factorization form.  Update the linked
     * list of this object accordingly by removing those nodes housing prime factors that disappear
     * after the division.  No update if this number is not divisible by pf. Algorithm details are
     * given in Section 3.2.
     *
     * @param pf
     * @return true if divisible by pf
     * false otherwise
     */
    public boolean dividedBy(PrimeFactorization pf) {
        // TODO

        return true;
    }


    /**
     * Divide the integer represented by the object pf1 by that represented by the object pf2.
     * Return a new object representing the quotient if divisible. Do not make changes to pf1 and
     * pf2. No update if the first number is not divisible by the second one.
     *
     * @param pf1
     * @param pf2
     * @return quotient as a new PrimeFactorization object if divisible
     * null otherwise
     */
    public static PrimeFactorization dividedBy(PrimeFactorization pf1, PrimeFactorization pf2) {
        // TODO
        return null;
    }


    // -----------------------
    // Greatest Common Divisor
    // -----------------------

    /**
     * Computes the greatest common divisor (gcd) of the represented integer v and an input integer n.
     * Returns the result as a PrimeFactor object.  Calls the method Euclidean() if
     * this.value != OVERFLOW.
     * <p>
     * It is more efficient to factorize the gcd than n, which can be much greater.
     *
     * @param n
     * @return prime factorization of gcd
     * @throws IllegalArgumentException if n < 1
     */
    public PrimeFactorization gcd(long n) throws IllegalArgumentException {
        // TODO
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        return null;
    }


    /**
     * Implements the Euclidean algorithm to compute the gcd of two natural numbers m and n.
     * The algorithm is described in Section 4.1 of the project description.
     *
     * @param m
     * @param n
     * @return gcd of m and n.
     * @throws IllegalArgumentException if m < 1 or n < 1
     */
    public static long Euclidean(long m, long n) throws IllegalArgumentException {
        // TODO
        if (m < 1 || n < 1) {
            throw new IllegalArgumentException();
        }

        return 0;
    }


    /**
     * Computes the gcd of the values represented by this object and pf by traversing the two lists.  No
     * direct computation involving value and pf.value. Refer to Section 4.2 in the project description
     * on how to proceed.
     *
     * @param pf
     * @return prime factorization of the gcd
     */
    public PrimeFactorization gcd(PrimeFactorization pf) {
        // TODO

        return null;
    }


    /**
     * @param pf1
     * @param pf2
     * @return prime factorization of the gcd of two numbers represented by pf1 and pf2
     */
    public static PrimeFactorization gcd(PrimeFactorization pf1, PrimeFactorization pf2) {
        // TODO
        return null;
    }

    // ------------
    // List Methods
    // ------------

    /**
     * Traverses the list to determine if p is a prime factor.
     * <p>
     * Precondition: p is a prime.
     *
     * @param p
     * @return true  if p is a prime factor of the number v represented by this linked list
     * false otherwise
     * @throws IllegalArgumentException if p is not a prime
     */
    public boolean containsPrimeFactor(int p) throws IllegalArgumentException {
        // TODO
        if (!isPrime(p)) {
            throw new IllegalArgumentException();
        }
        else if (isPrime(p)){
            return true;
        }
        else
        return false;
    }

    // The next two methods ought to be private but are made public for testing purpose. Keep
    // them public

    /**
     * Adds a prime factor p of multiplicity m.  Search for p in the linked list.  If p is found at
     * a node N, add m to N.multiplicity.  Otherwise, create a new node to store p and m.
     * <p>
     * Precondition: p is a prime.
     *
     * @param p prime
     * @param m multiplicity
     * @return true  if m >= 1
     * false if m < 1
     */
    public boolean add(int p, int m) {
        // TODO
        PrimeFactorizationIterator pzi = iterator();
        Node node = new Node();

        while (pzi.hasNext()) {
            for (int i = 2; i < sqrt(value); i++) {
                if ((node.pFactor.prime == p) && (i == p)) {
                    node.pFactor.multiplicity += m;
                } else if ((node.pFactor.prime != p) && (i == p)) {
                    this.add(i, 1);
                    size++;
                }
            }
            pzi.next();
        }
        if (m >= 1) {
            return true;

        } else {
            return false;
        }
    }


    /**
     * Removes m from the multiplicity of a prime p on the linked list.  It starts by searching
     * for p.  Returns false if p is not found, and true if p is found. In the latter case, let
     * N be the node that stores p. If N.multiplicity > m, subtracts m from N.multiplicity.
     * If N.multiplicity <= m, removes the node N.
     * <p>
     * Precondition: p is a prime.
     *
     * @param p
     * @param m
     * @return true  when p is found.
     * false when p is not found.
     * @throws IllegalArgumentException if m < 1
     */
    public boolean remove(int p, int m) throws IllegalArgumentException {
        // TODO
        if (m < 1) {
            throw new IllegalArgumentException();
        }
        PrimeFactorizationIterator pzi = iterator();
        Node node = new Node();

        while (pzi.hasNext()) {
            for (int i = 2; i < sqrt(value); i++) {
                if ((node.pFactor.prime == p) && (i == p) && m > 1) {
                    node.pFactor.multiplicity -= m;
                    return true;
                } else if ((node.pFactor.prime == p) && (i == p) && m == 1) {
                    pzi.remove();
                    size--;
                    return true;
                } else {
                    return false;
                }
            }
            pzi.next();
        }


        return false;
    }


    /**
     * @return size of the list
     */
    public int size() {
        return size;
    }


    /**
     * Writes out the list as a factorization in the form of a product. Represents exponentiation
     * by a caret.  For example, if the number is 5814, the returned string would be printed out
     * as "2 * 3^2 * 17 * 19".
     */
    @Override
    public String toString() {
        String string = "";
        if (this.value == 1) {
            return "1";
        }
        PrimeFactorizationIterator pzi = iterator();
        while (pzi.hasNext()) {
            PrimeFactor f = pzi.next();
            string += f.toString();
            if(pzi.hasNext()){
                string += " * ";
            }
        }
        return string;

    }


    // The next three methods are for testing, but you may use them as you like.

    /**
     * @return true if this PrimeFactorization is representing a value that is too large to be within
     * long's range. e.g. 999^999. false otherwise.
     */
    public boolean valueOverflow() {
        return value == OVERFLOW;
    }

    /**
     * @return value represented by this PrimeFactorization, or -1 if valueOverflow()
     */
    public long value() {
        return value;
    }


    public PrimeFactor[] toArray() {
        PrimeFactor[] arr = new PrimeFactor[size];
        int i = 0;
        for (PrimeFactor pf : this)
            arr[i++] = pf;
        return arr;
    }


    @Override
    public PrimeFactorizationIterator iterator() {
        return new PrimeFactorizationIterator();
    }












    /**
     * Doubly-linked node type for this class.
     */
    private class Node {
        public PrimeFactor pFactor;            // prime factor
        public Node next;
        public Node previous;

        /**
         * Default constructor for creating a dummy node.
         */
        public Node() {
            // TODO
            this.pFactor = null;
            this.next = null;
            this.previous = null;
        }

        /**
         * Precondition: p is a prime
         *
         * @param p prime number
         * @param m multiplicity
         * @throws IllegalArgumentException if m < 1
         */
        public Node(int p, int m) throws IllegalArgumentException {
            // TODO
            if (m < 1) {
                throw new IllegalArgumentException();
            }
            this.pFactor = new PrimeFactor(p, m);
            this.next = null;
            this.previous = null;
        }


        /**
         * Constructs a node over a provided PrimeFactor object.
         *
         * @param pf
         * @throws IllegalArgumentException
         */
        public Node(PrimeFactor pf) {
            // TODO
            if (pf == null) {
                throw new IllegalArgumentException();
            }
            this.pFactor = pf;
            this.next = null;
            this.previous = null;
        }


        /**
         * Printed out in the form: prime + "^" + multiplicity.  For instance "2^3".
         * Also, deal with the case pFactor == null in which a string "dummy" is
         * returned instead.
         */
        @Override
        public String toString() {
            // TODO
            if (pFactor == null) {
                return "dummy";
            } else
                return pFactor.prime + "^" + pFactor.multiplicity;
        }
    }












    private class PrimeFactorizationIterator implements ListIterator<PrimeFactor> {
        // Class invariants:
        // 1) logical cursor position is always between cursor.previous and cursor
        // 2) after a call to next(), cursor.previous refers to the node just returned
        // 3) after a call to previous() cursor refers to the node just returned
        // 4) index is always the logical index of node pointed to by cursor

        private Node cursor = head.next;
        private Node pending = null;    // node pending for removal
        private int index = 0;

        // other instance variables ...


        /**
         * Default constructor positions the cursor before the smallest prime factor.
         */
        public PrimeFactorizationIterator() {
            cursor = head.next;
            // TODO
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }


        @Override
        public boolean hasPrevious() {
            return index > 0;
        }


        @Override
        public PrimeFactor next() {
            pending = cursor;
            cursor = cursor.next;
            ++index;
            return pending.pFactor;
        }


        @Override
        public PrimeFactor previous() {
            cursor = cursor.previous;
            --index;
            return pending.pFactor;
        }


        /**
         * Removes the prime factor returned by next() or previous()
         *
         * @throws IllegalStateException if pending == null
         */
        @Override
        public void remove() throws IllegalStateException {
            if (pending == null) throw new IllegalStateException();

            Node before = pending.previous;
            Node after = pending.next;

            before.next = after;
            after.previous = before;

            --size;
            pending = null;
        }


        /**
         * Adds a prime factor at the cursor position.  The cursor is at a wrong position
         * in either of the two situations below:
         * <p>
         * a) pf.prime < cursor.previous.pFactor.prime if cursor.previous != head.
         * b) pf.prime > cursor.pFactor.prime if cursor != tail.
         * <p>
         * Take into account the possibility that pf.prime == cursor.pFactor.prime.
         * <p>
         * Precondition: pf.prime is a prime.
         *
         * @param pf
         * @throws IllegalArgumentException if the cursor is at a wrong position.
         */
        @Override
        public void add(PrimeFactor pf) throws IllegalArgumentException {
            if ((pf.prime < cursor.previous.pFactor.prime) && (cursor.previous != head)) {
                throw new IllegalArgumentException();
            } else if ((pf.prime > cursor.pFactor.prime) && (cursor != tail)) {
                throw new IllegalArgumentException();
            } else if (pf.prime == cursor.pFactor.prime) {
// ?
            }
            Node insert = new Node(pf);
            Node before = cursor.previous;
            before.next = insert;
            insert.previous = before;
            cursor.previous = insert;
            insert.next = cursor;
            size++;

        }


        @Override
        public int nextIndex() {
            return index;
        }


        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Deprecated
        @Override
        public void set(PrimeFactor pf) {
            throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support set method");
        }

        // Other methods you may want to add or override that could possibly facilitate
        // other operations, for instance, addition, access to the previous element, etc.
        //
        // ...
        //
    }


    // --------------
    // Helper methods
    // --------------

    /**
     * Inserts toAdd into the list after current without updating size.
     * <p>
     * Precondition: current != null, toAdd != null
     */
    private void link(Node current, Node toAdd) {
        toAdd.previous = current;
        toAdd.next = current.next;
        current.next.previous = toAdd;
        current.next = toAdd;

    }


    /**
     * Removes toRemove from the list without updating size.
     */
    private void unlink(Node toRemove) {
        toRemove.previous.next = toRemove.next;
        toRemove.next.previous = toRemove.previous;

    }


    /**
     * Remove all the nodes in the linked list except the two dummy nodes.
     * <p>
     * Made public for testing purpose.  Ought to be private otherwise.
     */
    public void clearList() {
        size = 0;
        head.next = tail;
        tail.previous = head;
    }

    /**
     * Multiply the prime factors (with multiplicities) out to obtain the represented integer.
     * Use Math.multiply(). If an exception is throw, assign OVERFLOW to the instance variable value.
     * Otherwise, assign the multiplication result to the variable.
     */
    private void updateValue() {
        long prime, multiplicity;
        try {
            // TODO
            PrimeFactorizationIterator pzi = iterator();
            while (pzi.hasNext()) {
                multiplicity = pzi.next().multiplicity;
                prime = pzi.next().prime;
                double temp = Math.pow(prime, multiplicity);
                long ret = (long) temp;
                value = ret;
                pzi.next();
            }
        } catch (ArithmeticException e) {
            value = OVERFLOW;
        }

    }
}
