/*
 * noakcalculator(TM) is a Java program that provides a high-precision scientific calculator
 * Copyright (C) 2019-25 quark95cos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package noakcalc.components;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import noakcalc.Utilities.MathConstants;
import noakcalc.math.Angles;
import noakcalc.math.Combinatorics;
import noakcalc.math.Divisibility;
import noakcalc.math.Factorial;
import noakcalc.math.Taylor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * ParseExpression Class
 */
public class ParseExpression {

    // Brackets, roots, exponents and factorial
    public static final byte BRO = 0x00, BRACKET_OPEN = BRO;
    public static final byte BRC = 0x01, BRACKET_CLOSE = BRC;
    public static final byte SRT = 0x02, SQUARE_ROOT = SRT;
    public static final byte CRT = 0x03, CUBE_ROOT = CRT;
    public static final byte REC = 0x04, RECIPROCAL = REC;
    public static final byte SQR = 0x05, SQUARED = SQR;
    public static final byte CUB = 0x06, CUBED = CUB;
    public static final byte POW = 0x07, POWER = POW;
    public static final byte FCT = 0x08, FACTORIAL = FCT;
    public static final byte PWT = 0x09, POWER_10 = PWT;
    public static final byte PW2 = 0x10, POWER_2 = PW2;
    public static final byte NRT = 0x11, NTH_ROOT = NRT;
    public static final byte DMS = 0x12, DEG_MIN_SEC = DMS;
    public static final byte DEG = 0x13, DEGREE = DEG;

    // Common mathematical functions
    public static final byte SIN = 0x20, SINE = SIN;
    public static final byte COS = 0x21, COSINE = COS;
    public static final byte TAN = 0x22, TANGENT = TAN;
    public static final byte CSC = 0x23, COSECANT = CSC;
    public static final byte SEC = 0x24, SECANT = SEC;
    public static final byte COT = 0x25, COTANGENT = COT;
    public static final byte SNH = 0x26, HYPERBOLIC_SINE = SNH;
    public static final byte CSH = 0x27, HYPERBOLIC_COSINE = CSH;
    public static final byte TNH = 0x28, HYPERBOLIC_TANGENT = TNH;
    public static final byte CCH = 0x29, HYPERBOLIC_COSECANT = CCH;
    public static final byte SCH = 0x30, HYPERBOLIC_SECANT = SCH;
    public static final byte CTH = 0x31, HYPERBOLIC_COTANGENT = CTH;
    public static final byte LOG = 0x32, LOGARITHM = LOG;
    public static final byte LG2 = 0x33, LOGARITHM2 = LG2;
    public static final byte LGX = 0x34, LOGARITHMX = LGX;
    public static final byte NLG = 0x35, NATURAL_LOG = NLG;
    public static final byte INT = 0x36, INTEGER = INT;
    public static final byte FRA = 0x37, FRACTION = FRA;
    public static final byte ABS = 0x38, ABSOLUTE_VALUE = ABS;
    public static final byte NEG = 0x39, NEGATE = NEG;
    public static final byte FLR = 0x40, FLOOR = FLR;
    public static final byte ELX = 0x41, NATURAL_EXP_LOG = ELX;
    public static final byte CLG = 0x42, CEILING = CLG;
    public static final byte NCR = 0x43, COMBINATION = NCR;
    public static final byte NPR = 0x44, PERMUTATION = NPR;
    public static final byte NC2 = 0x45, COMBINATION_REPEAT = NC2;
    public static final byte NP2 = 0x46, PERMUTATION_REPEAT = NP2;
    public static final byte LCM = 0x47, LOWEST_COMMON_MULTIPLE = LCM;
    public static final byte GCD = 0x48, GREATEST_COMMON_DENOMINATOR = GCD;
    public static final byte MIN = 0x49, MINIMUM = MIN;
    public static final byte MAX = 0x50, MAXIMUM = MAX;

    // The binary operators
    public static final byte MUL = 0x60, MULTIPLY = MUL;
    public static final byte DIV = 0x61, DIVIDE = DIV;
    public static final byte MOD = 0x62, MODULO = MOD;
    public static final byte ADD = 0x63, ADDITION = ADD;
    public static final byte SUB = 0x64, SUBTRACTION = SUB;
    private static final Logger LOGGER =
            LogManager.getLogger(ParseExpression.class.getName());
    /**
     * Returns a boolean value indicating whether the passed
     * parameter is an operator
     *
     * @return operator selected
     */
    private static boolean isOperator(Object obj) {
        byte opr = obj instanceof Byte ? (byte) obj : -1;
        
        return (opr >= BRO && opr <= DEG) || (opr >= SIN && opr <= MAX) ||
                (opr >= MUL && opr <= SUB);
    }
    /**
     * Returns a boolean value indicating whether the passed
     * parameter is an operand (just BigDecimal for now)
     *
     * @return
     */
    private static boolean isOperand(Object obj) {
        return obj instanceof BigDecimal;
    }
    /**
     * Returns a boolean value indicating whether the passed
     * parameter is an expression
     *
     * @return
     */
    private static boolean isExpression(Object obj) {
        return obj instanceof ParseExpression;
    }

    // Class instance data fields
    private ArrayList<Object> list = null;
    private ParseExpression parent = null;


    /**
     * Constructor
     */
    public ParseExpression() {
        this(null);
    }

    /**
     * Private constructor that makes objects with parents
     */
    private ParseExpression(ParseExpression parent) {
        this.list = new ArrayList<>();
        this.parent = parent;
    }

    /**
     * Returns a boolean value indicating whether this expression is
     * embedded within another expression
     *
     * @return
     */
    private boolean hasParent() {
        return this.parent != null;
    }

    /**
     * Returns the parent expression of this expression
     *
     * @return this.parent
     */
    private ParseExpression getParent() {
        return this.parent;
    }


    /**
     * Returns a boolean value indicating whether there are items on
     * the internal stack
     *
     * @return
     */
    public boolean hasItems() {
        return !list.isEmpty();
    }

    /**
     * Adds a new item onto the internal list. Think of it like a stack
     *
     * @param args
     * @return
     */
    public ParseExpression push(Object ... args) {
        boolean addAll = this.list.addAll(Arrays.asList(args));

        return this;
    }

    /**
     * Removes the last item (if any) from the internal stack
     *
     * @return
     */
    public Object pop() {
        int index = list.size() - 1;

        return (index >= 0) ? list.remove(index) : null;
    }

    /**
     * Evaluates and returns the result of this expression
     *
     * @return
     * @throws noakcalc.components.SyntaxErrorException
     * @throws noakcalc.components.InvalidInputException
     * @throws noakcalc.components.UnknownOperatorException
     */
    public BigDecimal eval()
        throws SyntaxErrorException, InvalidInputException, UnknownOperatorException {
        Object obj = null;
        ParseExpression curr = this;
        BigDecimal lhs = null, rhs = null;


        // STEP 0: Evaluate brackets to determine sub-expressions
        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            if (obj.equals(BRO)) {
                if (this.equals(curr)) {
                    curr = new ParseExpression(curr);
                    list.set(i, curr);
                    continue;
                }
                else {
                    curr = new ParseExpression(curr);
                    curr.getParent().push(curr);
                }
            }
            else if (obj.equals(BRC)) {
                curr = curr.getParent();
                if (curr == null) {
                    break;
                }
            }
            else if (this.equals(curr)) {
                if (!(isOperator(obj) || isExpression(obj))) {
                    list.set(i, new BigDecimal(obj.toString()));
                }
                continue;
            } else {
                curr.push(obj);
            }
            list.remove(i--);
        }

        if (!this.equals(curr)) {
            throw new SyntaxErrorException("Unmatched brackets");
        }

        // STEP 1: Translate SQR, CUB, PWT & PW2 into POWs
        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            if (obj.equals(SQR) || obj.equals(CUB)) {
                list.set(i, new BigDecimal(obj.equals(SQR) ? 2 : 3));
                list.add(i, POW);
                i++;
            }

            if (obj.equals(PWT)) {
                list.set(i, POW);
                list.add(i, new BigDecimal(10));
                i++;
            }

            if (obj.equals(PW2)) {
                list.set(i, POW);
                list.add(i, new BigDecimal(2));
                i++;
            }
        }

        // STEP 2: Roots, powers, reciprocal and factorial.
        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            if (isOperator(obj)) {
                switch ((byte) obj) {
                    case SQUARE_ROOT:
                    case CUBE_ROOT:
                        obj = i + 1 < list.size() ? list.get(i + 1) : -1;
                        if (obj.equals(SRT) || obj.equals(CRT)) {
                            continue;
                        }
                        else if (isOperand(obj)) {
                            rhs = (BigDecimal) obj;
                        }
                        else if (isExpression(obj)) {
                            rhs = ((ParseExpression) obj).eval();
                        }
                        else {
                            throw new SyntaxErrorException("Missing operand");
                        }

                        if (rhs.compareTo(BigDecimal.ZERO) < 0) {
                            throw new ArithmeticException("Root of negative no.");
                        }
                        rhs = new BigDecimal(list.get(i).equals(SRT) ?
                            Math.sqrt(rhs.doubleValue()) : Math.cbrt(rhs.doubleValue()));
                        list.set(i, rhs);
                        list.remove(i + 1);
                        i = Math.max(i - 2, -1);
                        break;

                    case POWER: case NTH_ROOT: case LGX: case NCR: case NPR:
                    case NC2: case NP2: case LCM: case GCD:
                        obj = i + 2 < list.size() ? list.get(i + 2) : -1;
                        if (obj.equals(POW) || obj.equals(NP2)
                            || obj.equals(NRT) || obj.equals(LGX)) {
                            continue;
                        }
                        obj = i > 0 ? list.get(i - 1) : -1;
                        if (isOperand(obj)) {
                            lhs = (BigDecimal) obj;
                        }
                        else if (isExpression(obj)) {
                            lhs = ((ParseExpression) obj).eval();
                        }
                        else {
                            throw new SyntaxErrorException("Missing operand");
                        }
                        obj = i + 1 < list.size() ? list.get(i + 1) : -1;
                        if (isOperand(obj)) {
                            rhs = (BigDecimal) obj;
                        }
                        else if (isExpression(obj)) {
                            rhs = ((ParseExpression) obj).eval();
                        }
                        else {
                            throw new SyntaxErrorException("Missing operand");
                        }
                        if (list.get(i).equals(POW) || list.get(i).equals(NP2)) {
                            if (rhs.compareTo(BigDecimal.ZERO) < 0) {
                                lhs = BigDecimal.ONE.divide(lhs.pow(rhs.abs().intValue()));
                            }
                            else {
                                lhs = lhs.pow(rhs.intValue());
                            }
                        }
                        else if (list.get(i).equals(NRT)) {
                            if (lhs.compareTo(BigDecimal.ZERO) < 0) {
                                throw new ArithmeticException("Root of negative no.");
                            }
                            else if (lhs.compareTo(BigDecimal.ZERO) == 0) {
                                throw new NumberFormatException("Infinite or NaN");
                            }
                            else {
                                lhs = new BigDecimal(Math.pow(rhs.intValue(), 1.0 / lhs.intValue()));
                            }
                        }
                        else if (list.get(i).equals(LGX)) {
                            if (lhs.compareTo(BigDecimal.ZERO) < 0 || rhs.compareTo(BigDecimal.ZERO) < 0) {
                                throw new ArithmeticException("Invalid input or calculation out of range");
                            }
                            else {
                                lhs = new BigDecimal(Math.log10(rhs.doubleValue())/Math.log10(lhs.doubleValue()));
                            }
                        }
                        else if (list.get(i).equals(NCR)) {
                            lhs = Combinatorics.calCombination(lhs, rhs);
                        }
                        else if (list.get(i).equals(NPR)) {
                            lhs = Combinatorics.calPermutation(lhs, rhs);
                        }
                        else if (list.get(i).equals(NC2)) {
                            lhs = Combinatorics.calCombinationRepeat(lhs, rhs);
                        }
                        else if (list.get(i).equals(LCM)) {
                            lhs = Divisibility.findLCM(lhs, rhs);
                        }
                        else if (list.get(i).equals(GCD)) {
                            lhs = Divisibility.findGCD(lhs, rhs);
                        }
                        list.set(i - 1, lhs);
                        list.remove(i);
                        list.remove(i);
                        i = Math.max(i - 3, -1);
                        break;

                    case RECIPROCAL:
                        obj = i > 0 ? list.get(i - 1) : -1;
                        if (isOperand(obj)) {
                            lhs = (BigDecimal) obj;
                        }
                        else if (isExpression(obj)) {
                            lhs = ((ParseExpression) obj).eval();
                        }
                        else {
                            throw new SyntaxErrorException("Missing operand");
                        }
                        list.set(i - 1, BigDecimal.ONE.divide(lhs, 30, BigDecimal.ROUND_DOWN));
                        list.remove(i);
                        i -= 1;
                        break;

                    case FACTORIAL:
                        obj = i > 0 ? list.get(i - 1) : -1;
                        if (isOperand(obj)) {
                            lhs = (BigDecimal) obj;
                        }
                        else if (isExpression(obj)) {
                            lhs = ((ParseExpression) obj).eval();
                        }
                        else {
                            throw new SyntaxErrorException("Missing operand");
                        }

                        // If the value to be determined is less then 0 then InvalidInputException.
                        // If the value to be determined is MathConstants.FACTORIAL_NUMBER or lower use the
                        // standard method of finding the factorial. Otherwise use the Stirling Approximation method
                        if (lhs.compareTo(BigDecimal.ZERO) < 0) {
                            throw new InvalidInputException("Factorial input must be greater than or equal to 0");
                        }
                        //else if (lhs.compareTo(new BigDecimal(5000)) > 0)
                        //throw new InvalidInputException("Factorial input too large (>5000)");
                        else if (lhs.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
                            list.set(i - 1, Factorial.factorial(lhs.setScale(0, BigDecimal.ROUND_DOWN)));
                        }
                        else {
                            list.set(i - 1, Factorial.factorialStrlng(lhs.setScale(0, BigDecimal.ROUND_DOWN)));
                        }
                        list.remove(i);
                        i -= 1;
                        break;
                }
            }
        }

        // STEP 3: Common mathematical functions.
        for (int i = list.size() - 1; i >= 0; i--) {
            obj = list.get(i);
            if (obj.equals(SIN) || obj.equals(COS) || obj.equals(TAN) ||
                obj.equals(CSC) || obj.equals(SEC) || obj.equals(COT) ||
                obj.equals(SNH) || obj.equals(CSH) || obj.equals(TNH) ||
                obj.equals(CCH) || obj.equals(SCH) || obj.equals(CTH) ||
                obj.equals(LOG) || obj.equals(LG2) || obj.equals(NLG) ||
                obj.equals(INT) || obj.equals(NEG) || obj.equals(FLR) ||
                obj.equals(CLG) || obj.equals(ELX) || obj.equals(ABS)) {
                obj = i + 1 < list.size() ? list.get(i + 1) : -1;
                    if (isOperand(obj)) {
                        rhs = (BigDecimal) obj;
                    }
                    else if (isExpression(obj)) {
                        rhs = ((ParseExpression) obj).eval();
                    }
                    else {
                        throw new SyntaxErrorException("Missing operand");
                    }
                    switch ((byte) list.get(i)) {
                        case SIN:
                            rhs = new BigDecimal(Math.sin(Math.toRadians(rhs.doubleValue())));
                            //rhs = new BigDecimal(Math.sin(rhs.doubleValue()));
                            break;
                        case COS:
                            rhs = new BigDecimal(Math.cos(Math.toRadians(rhs.doubleValue())));
                            //rhs = new BigDecimal(Math.cos(rhs.doubleValue()));
                            break;
                        case TAN:
                            if (rhs.compareTo(new BigDecimal(90)) == 0) {
                                throw new ArithmeticException("Tangent 90 - Undefined");
                            }

                            rhs = new BigDecimal(Math.tan(Math.toRadians(rhs.doubleValue())));
                            //rhs = new BigDecimal(Math.tan(rhs.doubleValue()));
                            break;
                        case CSC:
                            rhs = new BigDecimal(1/Math.sin(Math.toRadians(rhs.doubleValue())));
                            //rhs = new BigDecimal(1/Math.sin(rhs.doubleValue()));
                            break;
                        case SEC:
                            rhs = new BigDecimal(1/Math.cos(Math.toRadians(rhs.doubleValue())));
                            //rhs = new BigDecimal(1/Math.cos(rhs.doubleValue()));
                            break;
                        case COT:
                            if (rhs.compareTo(new BigDecimal(90)) == 0) {
                                throw new ArithmeticException("CoTangent 90 - 1/Infinity");
                            }
                            else if (rhs.compareTo(new BigDecimal(0)) == 0) {
                                throw new ArithmeticException("CoTangent 0 - Undefined");
                            }

                            rhs = new BigDecimal(1/Math.tan(Math.toRadians(rhs.doubleValue())));
                            //rhs = new BigDecimal(1/Math.tan(rhs.doubleValue()));
                            break;
                        case SNH:
                            //rhs = new BigDecimal(Math.sinh(Math.toRadians(rhs.doubleValue())));
                            rhs = new BigDecimal(Math.sinh(rhs.doubleValue()));
                            break;
                        case CSH:
                            //rhs = new BigDecimal(Math.cosh(Math.toRadians(rhs.doubleValue())));
                            rhs = new BigDecimal(Math.cosh(rhs.doubleValue()));
                            break;
                        case TNH:
                            //if (rhs.compareTo(new BigDecimal(90)) == 0) {
                            //    throw new ArithmeticException("Tangent 90 - Undefined");
                            //}

                            //rhs = new BigDecimal(Math.tanh(Math.toRadians(rhs.doubleValue())));
                            rhs = new BigDecimal(Math.tanh(rhs.doubleValue()));
                            break;
                        case CCH:
                            //rhs = new BigDecimal(1/Math.sinh(Math.toRadians(rhs.doubleValue())));
                            rhs = new BigDecimal(1/Math.sinh(rhs.doubleValue()));
                            break;
                        case SCH:
                            //rhs = new BigDecimal(1/Math.cosh(Math.toRadians(rhs.doubleValue())));
                            rhs = new BigDecimal(1/Math.cosh(rhs.doubleValue()));
                            break;
                        case CTH:
                            //if (rhs.compareTo(new BigDecimal(90)) == 0) {
                            //    throw new ArithmeticException("CoTangent 90 - 1/Infinity");
                            //}
                            //else if (rhs.compareTo(new BigDecimal(0)) == 0) {
                            //    throw new ArithmeticException("CoTangent 0 - Undefined");
                            //}

                            //rhs = new BigDecimal(1/Math.tanh(Math.toRadians(rhs.doubleValue())));
                            rhs = new BigDecimal(1/Math.tanh(rhs.doubleValue()));
                            break;
                        case LOG:
                            rhs = new BigDecimal(Math.log10(rhs.doubleValue()));
                            break;
                        case LG2:
                            rhs = new BigDecimal(Math.log10(rhs.doubleValue())/Math.log10(2.0));
                            break;
                        case NLG:
                            rhs = new BigDecimal(Math.log(rhs.doubleValue()));
                            break;
                        case INT:
                        case FLR:
                            rhs = rhs.setScale(0, BigDecimal.ROUND_DOWN);
                            break;
                        case CLG:
                            rhs = rhs.setScale(0, BigDecimal.ROUND_UP);
                            break;
                        case ELX:
                            rhs = new BigDecimal(Taylor.exponential(1000,rhs.doubleValue()));
                            break;
                        case NEG:
                            rhs = rhs.negate();
                            //LOGGER.debug("rhs #" + rhs + "#\n");
                            break;
                        case ABS:
                            rhs = rhs.abs();
                            break;
                        default:
                            continue;
                    }
                    if (rhs.scale() > 15) {
                        rhs = rhs.setScale(15, BigDecimal.ROUND_HALF_EVEN);
                    }
                    list.set(i, rhs);
                    list.remove(i + 1);
            }
        }

        // STEP 4: Special functions.
        for (int i = list.size() - 1; i >= 0; i--) {
            obj = list.get(i);
            if (obj.equals(DMS) || obj.equals(DEG)) {
                obj = i + 1 < list.size() ? list.get(i + 1) : -1;
                if (isOperand(obj)) {
                    rhs = (BigDecimal) obj;
                }
                else if (isExpression(obj)) {
                    rhs = ((ParseExpression) obj).eval();
                }
                else {
                    throw new SyntaxErrorException("Missing operand");
                }
                switch ((byte) list.get(i)) {
                    case DMS:
                        rhs = Angles.calDegreeToDMS(rhs);
                        break;
                    case DEG:
                        rhs = Angles.calDMSToDegree(rhs);
                        break;
                    default:
                        continue;
                }
                if (rhs.scale() > 15) {
                    rhs = rhs.setScale(15, BigDecimal.ROUND_HALF_EVEN);
                }
                list.set(i, rhs);
                list.remove(i + 1);
            }
        }

        // STEP 5: Special functions with no fractional limitation.
        for (int i = list.size() - 1; i >= 0; i--) {
            obj = list.get(i);
            if (obj.equals(FRA)) {
                obj = i + 1 < list.size() ? list.get(i + 1) : -1;
                if (isOperand(obj)) {
                    rhs = (BigDecimal) obj;
                }
                else if (isExpression(obj)) {
                    rhs = ((ParseExpression) obj).eval();
                }
                else {
                    throw new SyntaxErrorException("Missing operand");
                }
                switch ((byte) list.get(i)) {
                    case FRA:
                        rhs = rhs.remainder(BigDecimal.ONE);
                        break;
                    default:
                        continue;
                }
                //if (rhs.scale() > 15) {
                //    rhs = rhs.setScale(15, BigDecimal.ROUND_HALF_EVEN);
                //}
                list.set(i, rhs);
                list.remove(i + 1);
            }
        }

        // STEP 6: Multiplicative and additive operations.
        for (int s = 0; s < 2; s++) {
            for (int i = 0; i < list.size(); i++) {
                obj = list.get(i);
                if (s == 0 && (obj.equals(MUL) || obj.equals(DIV) || obj.equals(MOD)) ||
                    s == 1 && (obj.equals(ADD) || obj.equals(SUB))) {
                    obj = i > 0 ? list.get(i - 1) : -1;

                    if (isOperand(obj)) {
                        lhs = (BigDecimal) obj;
                    }
                    else if (isExpression(obj)) {
                        lhs = ((ParseExpression) obj).eval();
                    }
                    else {
                        throw new SyntaxErrorException("Missing operand");
                    }

                    obj = i + 1 < list.size() ? list.get(i + 1) : -1;

                    if (isOperand(obj)) {
                        rhs = (BigDecimal) obj;
                    }
                    else if (isExpression(obj)) {
                        rhs = ((ParseExpression) obj).eval();
                    }
                    else {
                        throw new SyntaxErrorException("Missing operand");
                    }

                    switch ((byte) list.get(i)) {
                        case MUL:
                            lhs = lhs.multiply(rhs);
                            break;
                        case DIV:
                            if (rhs.compareTo(BigDecimal.ZERO) == 0) {
                                throw new ArithmeticException("Division by zero");
                            }
                            lhs = lhs.divide(rhs, 30, BigDecimal.ROUND_DOWN);
                            break;
                        case MOD:
                            lhs = lhs.remainder(rhs);
                            break;
                        case ADD:
                            lhs = lhs.add(rhs);
                            break;
                        case SUB:
                            lhs = lhs.subtract(rhs);
                            break;
                    }
                    list.set(i - 1, lhs);
                    list.remove(i);
                    list.remove(i);
                    i -= 1;
                }
                else if (isExpression(obj)) {
                    list.set(i, rhs = ((ParseExpression) obj).eval());
                    obj = i > 0 ? list.get(i - 1) : -1;
                    if (isOperand(obj)) {
                        list.set(i - 1, rhs = rhs.multiply((BigDecimal) obj));
                        list.remove(i);
                        i -= 1;
                    }
                    obj = i + 1 < list.size() ? list.get(i + 1) : -1;
                    if (isOperand(obj)) {
                        list.set(i, rhs.multiply((BigDecimal) obj));
                        list.remove(i + 1);
                    }
                }
            }
        }

        // STEP 7: Multiply any remaining items. A cheap way to get my math right :)
        // For example, 2 sin 30 == 2 * sin 30
        while (list.size() > 1) {
            obj = list.get(0);
            if (isExpression(obj)) {
                lhs = ((ParseExpression) obj).eval();
            }
            else if (isOperand(obj)) {
                lhs = (BigDecimal) obj;
            }
            else {
                throw new UnknownOperatorException();
            }

            obj = list.get(1);

            if (isExpression(obj)) {
                rhs = ((ParseExpression) obj).eval();
            }
            else if (isOperand(obj)) {
                rhs = (BigDecimal) obj;
            }
            else {
                throw new UnknownOperatorException();
            }

            list.set(0, lhs.multiply(rhs));
            list.remove(1);
        }

        if (list.isEmpty()) {
            throw new SyntaxErrorException("Empty "
                + (this.hasParent() ? "brackets" : "expression"));
        }
        else if (isExpression(list.get(0))) {
            list.set(0, ((ParseExpression) list.get(0)).eval());
        }

        lhs = (BigDecimal) list.get(0);
        //if (lhs.scale() > 30) {
        //    lhs = lhs.setScale(30, BigDecimal.ROUND_HALF_EVEN);
        //}

        LOGGER.debug("lhs #" + lhs + "#\n");

        return lhs.stripTrailingZeros();
    }

    /**
     * Returns the string representation of this expression
     *
     * @return ret.trim()
     */
    @Override
    public String toString() {
        String ret = "";
        Object obj = null;

        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            if (obj.equals(BRO)) {
                ret += "(";
            }
            else if (obj.equals(BRC)) {
                ret += ")";
            }
            else if (isExpression(obj)) {
                ret += "[" + obj + "]";
            }
            else if (obj.equals(SRT)) {
                ret += "\u221A";
            }
            else if (obj.equals(CRT)) {
                ret += "\u221B";
            }
            else if (obj.equals(REC)) {
                ret += "\u02C9\u00B9";
            }
            else if (obj.equals(SQR)) {
                ret += "\u00B2";
            }
            else if (obj.equals(CUB)) {
                ret += "\u00B3";
            }
            else if (obj.equals(POW)) {
                ret += " ^ ";
            }
            else if (obj.equals(FCT)) {
                ret += "!";
            }
            else if (obj.equals(SIN)) {
                ret += " sin";
            }
            else if (obj.equals(COS)) {
                ret += " cos";
            }
            else if (obj.equals(TAN)) {
                ret += " tan";
            }
            else if (obj.equals(CSC)) {
                ret += " csc";
            }
            else if (obj.equals(SEC)) {
                ret += " sec";
            }
            else if (obj.equals(COT)) {
                ret += " cot";
            }
            else if (obj.equals(SNH)) {
                ret += " sinh";
            }
            else if (obj.equals(CSH)) {
                ret += " cosh";
            }
            else if (obj.equals(TNH)) {
                ret += " tanh";
            }
            else if (obj.equals(CCH)) {
                ret += " csch";
            }
            else if (obj.equals(SCH)) {
                ret += " sech";
            }
            else if (obj.equals(CTH)) {
                ret += " coth";
            }
            else if (obj.equals(LOG)) {
                ret += " log";
            }
            else if (obj.equals(LG2)) {
                ret += " log2";
            }
            else if (obj.equals(LGX)) {
                ret += " logx";
            }
            else if (obj.equals(NLG)) {
                ret += " ln";
            }
            else if (obj.equals(INT)) {
                ret += " int";
                //ret += "\u222B"; // This will be used for integrals
            }
             else if (obj.equals(FRA)) {
                ret += " frac";
                //ret += "\u222B"; // This will be used for integrals
            }
            else if (obj.equals(ABS)) {
                ret += " abs";
            }
            else if (obj.equals(NEG)) {
                ret += "-";
            }
            else if (obj.equals(FLR)) {
                ret += " flr";
            }
            else if (obj.equals(CLG)) {
                ret += " clg";
            }
            else if (obj.equals(MUL)) {
                ret += " \u00D7 ";
            }
            else if (obj.equals(DIV)) {
                ret += " \u00F7 ";
            }
            else if (obj.equals(MOD)) {
                ret += " mod ";
            }
            else if (obj.equals(ADD)) {
                ret += " \u002B ";
            }
            else if (obj.equals(SUB)) {
                ret += " - ";
            }
            else if (obj.equals(PWT)) {
                ret += " 10^ ";
            }
            else if (obj.equals(PW2)) {
                ret += " 2^ ";
            }
            else if (obj.equals(NRT)) {
                ret += " n√";
            }
            else if (obj.equals(DMS)) {
                ret += " dms";
            }
            else if (obj.equals(DEG)) {
                ret += " deg";
            }
            else if (obj.equals(ELX)) {
                ret += " e^ ";
            }
            else if (obj.equals(NCR)) {
                ret += " nCr ";
            }
            else if (obj.equals(NPR)) {
                ret += " nPr ";
            }
            else if (obj.equals(NC2)) {
                ret += " nCrr ";
            }
            else if (obj.equals(NP2)) {
                ret += " nPrr ";
            }
             else if (obj.equals(LCM)) {
                ret += " lcm ";
            }
            else if (obj.equals(GCD)) {
                ret += " gcd ";
            }
            else if (obj.equals(MIN)) {
                ret += " min(";
            }
            else if (obj.equals(MAX)) {
                ret += " max(";
            }
            else if (i > 0 && (list.get(i - 1).equals(SRT) ||
                list.get(i - 1).equals(CRT) || list.get(i - 1).equals(NEG))) {
                ret += obj;
            }
            else {
                ret += " " + obj;
            }
        }

        ret = ret.replaceAll("\\s\\s+", " ");
        ret = ret.replaceAll("\\(\\s+", "(");

        return ret.trim();
    }
}
