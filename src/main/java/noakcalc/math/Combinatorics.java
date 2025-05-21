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
package noakcalc.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import noakcalc.Utilities.MathConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Combinatorics Class
 */
public class Combinatorics {

    private static final Logger LOGGER =
        LogManager.getLogger(Combinatorics.class.getName());


    /**
     *
     * Calculate the Combination of two values
     *
     * @param nValue
     * @param rValue
     * @return
     */
    public static BigDecimal calCombination(BigDecimal nValue, BigDecimal rValue) {
        BigDecimal result = BigDecimal.ONE;

        LOGGER.debug("nValue #" + nValue + "#");
        LOGGER.debug("rValue #" + rValue + "#");

        // lhs.subtract(rhs);  nValue.subtract(rValue)
        // lhs.multiply(rhs);  nValue.multiply(rValue)
        // lhs = lhs.divide(rhs, 30, BigDecimal.ROUND_DOWN);
        // Factorial.factorial(lhs.setScale(0, BigDecimal.ROUND_DOWN)));

        BigDecimal subtractPart = nValue.subtract(rValue);

        if ((Factorial.factorial(subtractPart)
                .multiply(Factorial.factorial(rValue))).compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }

        if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
            result = Factorial.factorial(nValue)
            .divide((Factorial.factorial(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
            result = Factorial.factorialStrlng(nValue)
            .divide((Factorial.factorial(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1) {
            result = Factorial.factorial(nValue)
            .divide((Factorial.factorialStrlng(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1) {
            result = Factorial.factorialStrlng(nValue)
            .divide((Factorial.factorialStrlng(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }

        LOGGER.debug("Therefore Before: nCr #" + result + "#");
        result.setScale(0, RoundingMode.DOWN);
        LOGGER.debug("Therefore After: nCr #" + result + "#");

        return result;
    }

    /**
     *
     * Calculate the Combination of two values with repeatable numbers
     *
     * @param nValue
     * @param rValue
     * @return
     */
    public static BigDecimal calCombinationRepeat(BigDecimal nValue, BigDecimal rValue) {
        BigDecimal result = BigDecimal.ONE;

        LOGGER.debug("nValue #" + nValue + "#");
        LOGGER.debug("rValue #" + rValue + "#");

        BigDecimal subtractPart = nValue.subtract(BigDecimal.ONE);

        if ((Factorial.factorial(subtractPart)
                .multiply(Factorial.factorial(rValue))).compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }

        //result = Factorial.factorial(rValue.add(nValue).subtract(BigDecimal.ONE))
        //    .divide((Factorial.factorial(subtractPart)
        //        .multiply(Factorial.factorial(rValue))));

        if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
            result = Factorial.factorial(rValue.add(nValue).subtract(BigDecimal.ONE))
            .divide((Factorial.factorial(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
            result = Factorial.factorialStrlng(rValue.add(nValue).subtract(BigDecimal.ONE))
            .divide((Factorial.factorial(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1) {
            result = Factorial.factorial(rValue.add(nValue).subtract(BigDecimal.ONE))
            .divide((Factorial.factorialStrlng(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1) {
            result = Factorial.factorialStrlng(rValue.add(nValue).subtract(BigDecimal.ONE))
            .divide((Factorial.factorialStrlng(subtractPart)
                .multiply(Factorial.factorial(rValue))), 10, RoundingMode.HALF_UP);
        }

        LOGGER.debug("Therefore Before: nCr #" + result + "#");
        result.setScale(0, RoundingMode.DOWN);
        LOGGER.debug("Therefore After: nCr #" + result + "#");

        return result;
    }

    /**
     *
     * Calculate the Permutation of two values
     *
     * @param nValue
     * @param rValue
     * @return
     */
    public static BigDecimal calPermutation(BigDecimal nValue, BigDecimal rValue) {
        BigDecimal result = BigDecimal.ONE;

        LOGGER.debug("nValue #" + nValue + "#");
        LOGGER.debug("rValue #" + rValue + "#");

        BigDecimal subtractPart = nValue.subtract(rValue);

        if ((Factorial.factorial(subtractPart)).compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }

        if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
            result = Factorial.factorial(nValue).divide(Factorial.factorial(subtractPart), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1) {
            result = Factorial.factorialStrlng(nValue).divide(Factorial.factorial(subtractPart), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == -1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1) {
            result = Factorial.factorial(nValue).divide(Factorial.factorialStrlng(subtractPart), 10, RoundingMode.HALF_UP);
        }
        else if (nValue.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1 && subtractPart.compareTo(new BigDecimal(MathConstants.FACTORIAL_NUMBER)) == 1) {
            result = Factorial.factorialStrlng(nValue).divide(Factorial.factorialStrlng(subtractPart), 10, RoundingMode.HALF_UP);
        }

        LOGGER.debug("Therefore Before: nPr #" + result + "#");
        result.setScale(0, RoundingMode.DOWN);
        LOGGER.debug("Therefore After: nPr #" + result + "#");

        return result;
    }
    /**
     * Constructor
     */
    public Combinatorics() {
    }
}
