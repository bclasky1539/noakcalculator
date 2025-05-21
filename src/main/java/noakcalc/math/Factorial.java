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

/**
 *
 * Factorial Class
 */
public class Factorial {


    /**
     * Computes and returns the factorial of the argument
     * This should be used for smaller numbers
     *
     * @param n
     * @return r
     */
    public static BigDecimal factorial(BigDecimal n) {
        BigDecimal r = BigDecimal.ONE;

        while (n.compareTo(BigDecimal.ONE) > 0) {
            r = r.multiply(n);
            n = n.subtract(BigDecimal.ONE);
        }

        return r;
        //return n.compareTo(BigDecimal.ONE) > 0 ?
        //factorial(n.subtract(BigDecimal.ONE)).multiply(n) : n;
    }

    /**
     * Computes and returns the factorial of the argument
     * This is based on Stirling's Approximation for n!
     * n! ~ (n/e)^n * sqrt(2πn) * (1 + 1/(12*n))
     * This should be used for large numbers
     *
     * @param n
     * @return r
     */
    public static BigDecimal factorialStrlng(BigDecimal n) {
        BigDecimal r = BigDecimal.ONE;

        // sqrt(2πn)
        double fp = Math.sqrt(2*MathConstants.PID*n.doubleValue());

        // (1 + 1/(12*n))
        BigDecimal rp = BigDecimal.ONE.divide(new BigDecimal(12).multiply(n), 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);

        // (n/e)^n
        BigDecimal bp = n.divide(new BigDecimal(MathConstants.EULD), 10, RoundingMode.HALF_UP).pow(n.intValue());

        // Multiply them together and strip the decimal portion
        r = bp.multiply(rp).multiply(new BigDecimal(fp)).setScale(0,RoundingMode.DOWN);

        return r;
    }

    /**
     * Computes and returns the factorial of the argument
     * This is based on the Gosper's Approximation
     * n! ~ (n/e)^n * sqrt((2n+1/3)π) * (1 + 1/(12*n))
     * This should be used for large numbers
     *
     * @param n
     * @return r
     */
    public static BigDecimal factorialGspr(BigDecimal n) {
        BigDecimal r = BigDecimal.ONE;

        // sqrt(2πn)
        double fp = Math.sqrt((2*n.doubleValue() + (1/3))*MathConstants.PID);

        // (1 + 1/(12*n))
        BigDecimal rp = BigDecimal.ONE.divide(new BigDecimal(12).multiply(n), 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);

        // (n/e)^n
        BigDecimal bp = n.divide(new BigDecimal(MathConstants.EULD), 10, RoundingMode.HALF_UP).pow(n.intValue());

        // Multiply them together and strip the decimal portion
        r = bp.multiply(rp).multiply(new BigDecimal(fp)).setScale(0,RoundingMode.DOWN);

        return r;
    }
    /**
     * Constructor
     */
    public Factorial() {
    }
}
