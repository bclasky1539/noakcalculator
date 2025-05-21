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
import noakcalc.Utilities.CalcConstants;
import noakcalc.Utilities.MathConstants;
import noakcalc.Utilities.MiscConstants;
import noakcalc.math.BaseConversion;
import noakutils.UtilsException;
import noakutils.UtilsMisc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Calculator Class
 */
public class Calculator {
    private static final Logger LOGGER =
            LogManager.getLogger(Calculator.class.getName());
    /**
     * Returns a grouped text.
     *
     * @return
     */
    private static String groupText(String str, int cnt, String sep) {
        String ret = "";
        int i = 0, a = 0, z = str.lastIndexOf('.');
        
        if (z <= 0) {
            z = str.length();
        }
        else {
            ret = str.substring(z);
        }
        
        if (str.length() > 0 && str.charAt(0) == '-') {
            a++;
        }
        
        for (i = z - cnt; i > a; i -= cnt) {
            ret = sep + str.substring(i, i + cnt) + ret;
        }
        
        return str.substring(0, i + cnt) + ret;
    }

    // Class instance data fields
    private boolean clrPrmDisplayText;
    private boolean groupDigits;
    private boolean outputDigits;
    private boolean txtareaDisplayText;
    private boolean hasError;
    private boolean hasConstantPower;
    private String prmDisplayText;
    private String numMode;
    private String lastKey;
    private String memValue;
    private final ParseExpression[] expr;

    /**
     * Constructor
     */
    public Calculator() {
        this.clrPrmDisplayText = false;
        this.groupDigits = false;
        this.outputDigits = false;
        this.txtareaDisplayText = false;
        this.hasError = false;
        this.hasConstantPower = false;
        this.prmDisplayText = null;
        this.numMode = null;
        this.lastKey = null;
        this.memValue = null;
        //this.expr = null;
        this.expr = new ParseExpression[2];
        for (int i = 0; i < expr.length; i++) {
            expr[i] = new ParseExpression();
        }
        numMode = CalcConstants.DEC;
        memValue = MathConstants.ZERO;
        initCalc();
    }

    /**
     * Re-initializes various fields
     */
    private void initCalc() {
        clear();
        clrPrmDisplayText = true;
        prmDisplayText = MathConstants.ZERO;
        hasError = false;
        hasConstantPower = false;
        lastKey = null;
    }


    /**
     * Returns a boolean value indicating whether this calculator has
     * a stored memory value
     *
     * @return memValue
     */
    public boolean hasMemValue() {
        return !memValue.equals(MathConstants.ZERO);
    }

    /**
     * Returns the text for the primary screen
     *
     * @return prmDisplayText
     */
    public String getPrmDisplayText() {
        if (!hasError && groupDigits) {
            if (null == numMode) {
                return groupText(prmDisplayText, 3, ",");
            }
            else {
                switch (numMode) {
                    case CalcConstants.BIN:
                    case CalcConstants.HEX:
                        return groupText(prmDisplayText, 4, " ");
                    case CalcConstants.OCT:
                        return groupText(prmDisplayText, 3, " ");
                    default:
                        return groupText(prmDisplayText, 3, ",");
                }
            }
        }

        LOGGER.debug("prmDisplayText #" + prmDisplayText + "#\n");
        return prmDisplayText;
    }

    /**
     * Returns the text for the secondary screen
     *
     * @return
     */
    public String getSecScreenText() {
        return expr[1] + (lastKey.equals(CalcConstants.EQU) ? " =" : "");
    }

    /**
     * Returns the boolean if Text Area should be populated
     *
     * @return
     */
    public boolean isTxtareaDisplayText() {
        return txtareaDisplayText;
    }

    /**
     * Prepares display with constants that have powers.
     */
    private void prepConstantPower(String str) {
        String [] constSubStrings =
        UtilsMisc.stringSplit(str, MiscConstants.SPACE_DELIMITER);
        for (int i = 0; i < constSubStrings.length; i++) {
            System.out.println("prepConstantPower: [" + i + "] "+ constSubStrings[i].trim());
        }
        push(constSubStrings[0].trim());
        push(ParseExpression.MUL);
        push(ParseExpression.PWT);
        push(constSubStrings[3].trim());
    }

    /**
     * Prepares and returns the negated value.
     */
    private String prepNegateValue(String str) {
        LOGGER.debug("str: #" + str + "#");

        String strPrepVal = "";

        if (UtilsMisc.findIndexOf(str, CalcConstants.PWT, 1) != -1) {
            String [] str1 = UtilsMisc.stringSplit(str, MiscConstants.SPACE_DELIMITER);
            strPrepVal = negateValue(str1[0]);
            strPrepVal += MiscConstants.SPACE_DELIMITER +
                    str1[1].trim() + MiscConstants.SPACE_DELIMITER + str1[2].trim();
        }
        else {
            strPrepVal = negateValue(str);
        }

        LOGGER.debug("After to negate #" + strPrepVal + "#\n");

        return strPrepVal;
    }

    /**
     * Returns the negated value to the prepNegateValue method.
     */
    private String negateValue(String strValue) {
        LOGGER.debug("prior to negate bd #" + strValue + "#");

        BigDecimal bdValue = new BigDecimal(strValue);

        if (bdValue.compareTo(BigDecimal.ZERO) > 0) {
            bdValue = bdValue.negate();
            prmDisplayText = MathConstants.MINUS + prmDisplayText; //"-";
        }
        else {
            bdValue = bdValue.abs();
            if (UtilsMisc.findIndexOf(prmDisplayText, MathConstants.MINUS, 1) != -1) {
                prmDisplayText = prmDisplayText.substring(UtilsMisc.findIndexOf(prmDisplayText, MathConstants.MINUS, 1)+1);
            }
        }

        return bdValue.toPlainString();
    }


    /**
     * Clears all items from the expression stacks
     */
    private void clear() {
        for (ParseExpression expr1 : expr) {
            while (expr1.hasItems()) {
                expr1.pop();
            }
        }
    }

    /**
     * Pushed a new item onto the expression stack
     */
    private void push(Object obj) {
        expr[1].push(obj);
        if (obj instanceof String) {
            expr[0].push(BaseConversion.rad2dec((String) obj, numMode));
        }
        else {
            expr[0].push(obj);
        }
    }

    /**
     * Pops an item off the expression stack
     */
    private void pop() {
        expr[0].pop();
        expr[1].pop();
    }

    /**
     * Throws an error message to the user
     */
    private void throwError(String msg) {
        LOGGER.error("Error Message #" + msg + "#");
        prmDisplayText = msg;
        hasError = true;
        UtilsMisc.beep();
    }

    /**
     * Accepts input key from the user
     *
     * @param inpKey
     */
    public void inputKey(String inpKey) {
        LOGGER.debug("lastKey #" + lastKey + "#");
        LOGGER.debug("key expr[0].toString() #" + expr[0].toString() + "#");
        LOGGER.debug("key expr[1].toString() #" + expr[1].toString() + "#");
        LOGGER.debug("key #" + inpKey + "#\n");

        txtareaDisplayText = false;

        if (hasError) {
            if (inpKey.equals(CalcConstants.CXX) || inpKey.equals(CalcConstants.GRP)) {}
            else {
                UtilsMisc.beep();
                return;
            }
        }
        //else if (lastKey == EQU && key != BSP) {
        else if (lastKey != null && lastKey.equals(CalcConstants.EQU) && !inpKey.equals(CalcConstants.BSP)) {
            clear();
        }

        if (inpKey != null && !inpKey.equals(CalcConstants.DZZ)) {
            LOGGER.debug("key if block\n");
            // This determines if a Constant or Function has been selected. If so, the value is display in prmDisplayText and the
            // switch (key) is bypassed as the key is set to CalcConstants.DZZ (Not valid)
            if (UtilsMisc.findIndexOf(inpKey, MiscConstants.PIPE_DELIMITER, 1) != -1 &&
                    inpKey.substring(0, UtilsMisc.findIndexOf(inpKey, MiscConstants.PIPE_DELIMITER, 1)).equals(CalcConstants.CONST)) {
                String [] constStrings = UtilsMisc.stringSplit(inpKey, MiscConstants.PIPE_DELIMITER);
                LOGGER.debug("\ninpKey PWT index: " + UtilsMisc.findIndexOf(inpKey, CalcConstants.PWT, 1));
                for (int i = 0; i < constStrings.length; i++) {
                    LOGGER.debug("[" + i + "] "+ constStrings[i].trim());
                }
                if (UtilsMisc.findIndexOf(inpKey, CalcConstants.PWT, 1) != -1) {
                    try {
                        String [] constSubStrings = UtilsMisc.stringSplit(constStrings[2], MiscConstants.SPACE_DELIMITER);
                        LOGGER.debug("\ninpKey: " + inpKey);
                        for (int i = 0; i < constSubStrings.length; i++) {
                            LOGGER.debug("[" + i + "] "+ constSubStrings[i].trim());
                        }
                        prmDisplayText = constSubStrings[0].trim() + MiscConstants.SPACE_DELIMITER +
                                CalcConstants.MUL + MiscConstants.SPACE_DELIMITER + UtilsMisc.removeString(constSubStrings[1].trim(), "x") +
                                MiscConstants.SPACE_DELIMITER + constSubStrings[2].trim();
                        hasConstantPower = true;
                    } catch (UtilsException ex) {
                        LOGGER.error(ex);
                    }
                }
                else {
                    prmDisplayText = constStrings[2];
                }
                lastKey = prmDisplayText;
                clrPrmDisplayText = false;
            }
            else {
                inputKeyDeterm (inpKey);
                lastKey = inpKey;
            }

            LOGGER.debug("Calculator: expr.length: #" + expr.length + "#");

            for (ParseExpression expr1 : expr) {
                LOGGER.debug("Calculator: expr[i]: #" + expr1 + "#");
            }
            LOGGER.debug("\n");
        }
    }
 
    /**
     * Determines input key from the user
     *
     * @param key
     */
    private void inputKeyDeterm (String key) {
        LOGGER.debug("inputKeyDeterm key #" + key + "#\n");

        switch (key) {
            case CalcConstants.GRP:
                groupDigits = !groupDigits;
                if (hasError) {
                    return;
                }
                break;
            case CalcConstants.OPT:
                outputDigits = !outputDigits;
                if (hasError) {
                    return;
                }
                break;
            case CalcConstants.BIN: case CalcConstants.OCT:
            case CalcConstants.DEC: case CalcConstants.HEX:
                prmDisplayText = BaseConversion.dec2rad(BaseConversion.rad2dec(prmDisplayText, numMode), key);
                numMode = key;
                clrPrmDisplayText = true;
                break;
            case CalcConstants.MCX:
                memValue = MathConstants.ZERO;
                clrPrmDisplayText = true;
                break;
            case CalcConstants.MRX:
                if (!MathConstants.ZERO.equals(memValue)) {
                    prmDisplayText = BaseConversion.dec2rad(memValue, numMode);
                }
                else {
                    prmDisplayText = MathConstants.ZERO;
                    lastKey = CalcConstants.DG0;
                    return;
                }
                clrPrmDisplayText = true;
                break;
            case CalcConstants.MSX:
                memValue = BaseConversion.rad2dec(prmDisplayText, numMode);
                clrPrmDisplayText = true;
                break;
            case CalcConstants.MPX:
                memValue = new BigDecimal(BaseConversion.rad2dec(prmDisplayText, numMode)).add(
                        new BigDecimal(memValue)).toPlainString();
                clrPrmDisplayText = true;
                break;
            case CalcConstants.MMX:
                memValue = new BigDecimal(BaseConversion.rad2dec(memValue, numMode)).subtract(
                        new BigDecimal(prmDisplayText)).toPlainString();
                clrPrmDisplayText = true;
                break;
            case CalcConstants.BSP:
                if (clrPrmDisplayText) {
                    UtilsMisc.beep();
                    return;
                }
                else if (prmDisplayText.length() > 1) {
                    prmDisplayText = prmDisplayText.substring(0,
                            prmDisplayText.length() - 1);
                }
                else if (!MathConstants.ZERO.equals(prmDisplayText)) { //"0"
                    prmDisplayText = MathConstants.ZERO; //"0";
                }
                else if (expr[0].hasItems()) {
                    pop();
                }
                else {
                    UtilsMisc.beep();
                }
                break;
            case CalcConstants.CEX:
                prmDisplayText = MathConstants.ZERO;
                break;
            case CalcConstants.CXX:
                this.initCalc();
                break;
            case CalcConstants.NEG:
                prepNegateValue(prmDisplayText);
                break;
            case CalcConstants.DG0:
                if (clrPrmDisplayText) {
                    prmDisplayText = MathConstants.ZERO;
                    clrPrmDisplayText = false;
                }
                else if (!MathConstants.ZERO.equals(prmDisplayText)) {
                    prmDisplayText += key;
                }
                else if (lastKey.equals(CalcConstants.DG0)) {
                    UtilsMisc.beep();
                }
                break;
            case CalcConstants.DG1: case CalcConstants.DG2: case CalcConstants.DG3:
            case CalcConstants.DG4: case CalcConstants.DG5: case CalcConstants.DG6:
            case CalcConstants.DG7: case CalcConstants.DG8: case CalcConstants.DG9:
            case CalcConstants.DGA: case CalcConstants.DGB: case CalcConstants.DGC:
            case CalcConstants.DGD: case CalcConstants.DGE: case CalcConstants.DGF:
                if (clrPrmDisplayText || prmDisplayText.equals(CalcConstants.DG0)) {
                    prmDisplayText = key;
                    clrPrmDisplayText = false;
                }
                else {
                    prmDisplayText += key;
                }
                break;
            case CalcConstants.DOT:
                if (clrPrmDisplayText || prmDisplayText.equals(CalcConstants.DG0)) {
                    prmDisplayText = CalcConstants.DG0 + CalcConstants.DOT;
                    clrPrmDisplayText = false;
                }
                else if (!prmDisplayText.contains(CalcConstants.DOT)) {
                    prmDisplayText += key;
                }
                else {
                    UtilsMisc.beep();
                }
                break;
            case CalcConstants.BR1: case CalcConstants.BR2: case CalcConstants.REC:
            case CalcConstants.FCT: case CalcConstants.FLR: case CalcConstants.CLG:
            case CalcConstants.SIN: case CalcConstants.COS: case CalcConstants.TAN:
            case CalcConstants.CSC: case CalcConstants.SEC: case CalcConstants.COT:
            case CalcConstants.SNH: case CalcConstants.CSH: case CalcConstants.TNH:
            case CalcConstants.CCH: case CalcConstants.SCH: case CalcConstants.CTH:
            case CalcConstants.MOD: case CalcConstants.ADD: case CalcConstants.SUB:
            case CalcConstants.SRT: case CalcConstants.CRT: case CalcConstants.SQR:
            case CalcConstants.CUB: case CalcConstants.DMS: case CalcConstants.DEG:
            case CalcConstants.NLG: case CalcConstants.LOG: case CalcConstants.LG2:
            case CalcConstants.LGX: case CalcConstants.POW: case CalcConstants.NRT:
            case CalcConstants.PWT: case CalcConstants.PW2: case CalcConstants.ELX:
            case CalcConstants.NCR: case CalcConstants.NPR: case CalcConstants.NC2:
            case CalcConstants.NP2: case CalcConstants.MIN: case CalcConstants.MAX:
            case CalcConstants.INT: case CalcConstants.FRA: case CalcConstants.ABS:
            case CalcConstants.LCM: case CalcConstants.GCD: case CalcConstants.MUL:
            case CalcConstants.DIV:
                System.out.println("Calculator: inputKeyDeterm lastKey: " + lastKey + " hasConstantPower: " + hasConstantPower);
                if (clrPrmDisplayText) { // || prmDisplayText.equals(CalcConstants.DG0)) {
                    //prmDisplayText = CalcConstants.DG0;
                    switch (key) {
                        case CalcConstants.FCT: case CalcConstants.MUL:
                        case CalcConstants.DIV: case CalcConstants.MOD:
                        case CalcConstants.ADD: case CalcConstants.SUB:
                        //case CalcConstants.SRT: case CalcConstants.CRT:
                        case CalcConstants.SQR: case CalcConstants.CUB:
                        case CalcConstants.REC: case CalcConstants.POW:
                        case CalcConstants.NRT: case CalcConstants.LCM:
                        case CalcConstants.GCD:
                            if (hasConstantPower) {
                                prepConstantPower(lastKey);
                                hasConstantPower = false;
                            }
                            else {
                                push(prmDisplayText);
                            }
                            break;
                        default:
                            break;
                    }
                    clrPrmDisplayText = false;
                }
                else if (!MathConstants.ZERO.equals(prmDisplayText) ||
                //if (Utilities.containsOnlyNumbersSpecial(prmDisplayText) ||
                    UtilsMisc.containsOnlyNumbers(lastKey)) {
                    if (hasConstantPower) {
                        prepConstantPower(lastKey);
                        hasConstantPower = false;
                    }
                    else {
                        push(prmDisplayText);
                    }
                }

                switch (key) {
                    case CalcConstants.BR1:
                        push(ParseExpression.BRO);
                        break;
                    case CalcConstants.BR2:
                        push(ParseExpression.BRC);
                        break;
                    case CalcConstants.SRT:
                        push(ParseExpression.SRT);
                        break;
                    case CalcConstants.CRT:
                        push(ParseExpression.CRT);
                        break;
                    case CalcConstants.REC:
                        push(ParseExpression.REC);
                        break;
                    case CalcConstants.SQR:
                        push(ParseExpression.SQR);
                        break;
                    case CalcConstants.CUB:
                        push(ParseExpression.CUB);
                        break;
                    case CalcConstants.FCT:
                        push(ParseExpression.FCT);
                        break;
                    case CalcConstants.FLR:
                        push(ParseExpression.FLR);
                        break;
                    case CalcConstants.CLG:
                        push(ParseExpression.CLG);
                        break;
                    case CalcConstants.SIN:
                        push(ParseExpression.SIN);
                        break;
                    case CalcConstants.COS:
                        push(ParseExpression.COS);
                        break;
                    case CalcConstants.TAN:
                        push(ParseExpression.TAN);
                        break;
                    case CalcConstants.CSC:
                        push(ParseExpression.CSC);
                        break;
                    case CalcConstants.SEC:
                        push(ParseExpression.SEC);
                        break;
                    case CalcConstants.COT:
                        push(ParseExpression.COT);
                        break;
                    case CalcConstants.SNH:
                        push(ParseExpression.SNH);
                        break;
                    case CalcConstants.CSH:
                        push(ParseExpression.CSH);
                        break;
                    case CalcConstants.TNH:
                        push(ParseExpression.TNH);
                        break;
                    case CalcConstants.CCH:
                        push(ParseExpression.CCH);
                        break;
                    case CalcConstants.SCH:
                        push(ParseExpression.SCH);
                        break;
                    case CalcConstants.CTH:
                        push(ParseExpression.CTH);
                        break;
                    case CalcConstants.LOG:
                        push(ParseExpression.LOG);
                        break;
                    case CalcConstants.LG2:
                        push(ParseExpression.LG2);
                        break;
                    case CalcConstants.LGX:
                        push(ParseExpression.LGX);
                        break;
                    case CalcConstants.NLG:
                        push(ParseExpression.NLG);
                        break;
                    case CalcConstants.INT:
                        push(ParseExpression.INT);
                        break;
                    case CalcConstants.FRA:
                        push(ParseExpression.FRA);
                        break;
                    case CalcConstants.ABS:
                        push(ParseExpression.ABS);
                        break;
                    case CalcConstants.POW:
                        push(ParseExpression.POW);
                        break;
                    case CalcConstants.MUL:
                        push(ParseExpression.MUL);
                        break;
                    case CalcConstants.DIV:
                        push(ParseExpression.DIV);
                        break;
                    case CalcConstants.MOD:
                        push(ParseExpression.MOD);
                        break;
                    case CalcConstants.ADD:
                        push(ParseExpression.ADD);
                        break;
                    case CalcConstants.SUB:
                        push(ParseExpression.SUB);
                        break;
                    case CalcConstants.PWT:
                        push(ParseExpression.PWT);
                        break;
                    case CalcConstants.PW2:
                        push(ParseExpression.PW2);
                        break;
                    case CalcConstants.NRT:
                        push(ParseExpression.NRT);
                        break;
                    case CalcConstants.DMS:
                        push(ParseExpression.DMS);
                        break;
                    case CalcConstants.DEG:
                        push(ParseExpression.DEG);
                        break;
                    case CalcConstants.ELX:
                        push(ParseExpression.ELX);
                        break;
                    case CalcConstants.NCR:
                        push(ParseExpression.NCR);
                        break;
                    case CalcConstants.NPR:
                        push(ParseExpression.NPR);
                        break;
                    case CalcConstants.NC2:
                        push(ParseExpression.NC2);
                        break;
                    case CalcConstants.NP2:
                        push(ParseExpression.NP2);
                        break;
                    case CalcConstants.LCM:
                        push(ParseExpression.LCM);
                        break;
                    case CalcConstants.GCD:
                        push(ParseExpression.GCD);
                        break;
                    case CalcConstants.MIN:
                        push(ParseExpression.MIN);
                        break;
                    case CalcConstants.MAX:
                        push(ParseExpression.MAX);
                        break;
                    default:
                        break;
                }

                prmDisplayText = MathConstants.ZERO;
                clrPrmDisplayText = false;
                break;
            case CalcConstants.EQU:
                if (!MathConstants.ZERO.equals(prmDisplayText) ||
                    UtilsMisc.containsOnlyNumbers(lastKey) ||
                        !expr[0].hasItems()) {
                    if (hasConstantPower) {
                        prepConstantPower(lastKey);
                        hasConstantPower = false;
                    }
                    else {
                        push(prmDisplayText);
                    }
                }
                try {
                    prmDisplayText = UtilsMisc.stripZeros(expr[0].eval()
                            .toPlainString());
                    switch (numMode) {
                        case CalcConstants.BIN:
                            prmDisplayText = BaseConversion.dec2rad(prmDisplayText, CalcConstants.BIN);
                            break;
                        case CalcConstants.OCT:
                            prmDisplayText = BaseConversion.dec2rad(prmDisplayText, CalcConstants.OCT);
                            break;
                        case CalcConstants.HEX:
                            prmDisplayText = BaseConversion.dec2rad(prmDisplayText, CalcConstants.HEX);
                            break;
                        default:
                            break;
                    }
                    clrPrmDisplayText = true;
                    if (outputDigits) {
                        LOGGER.debug("OutputDigits would happen " + outputDigits + "#");
                        txtareaDisplayText = true;
                    }
                } catch (SyntaxErrorException e) {
                    throwError("Syntax Error: " + e.getMessage());
                } catch (InvalidInputException e) {
                    throwError("Input Error: " + e.getMessage());
                } catch (UnknownOperatorException e) {
                    throwError("Unknown Operator: " + e.getMessage());
                } catch (ArithmeticException | NumberFormatException e) {
                    throwError("Math Error: " + e.getMessage());
                } catch (UtilsException e) {
                    throwError("Application Error: " + e.getMessage());
                }
                break;
        }

    }

}
