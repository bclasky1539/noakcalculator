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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Divisibility Class
 */
public class Divisibility {

    //Simple Java program to find GCD (Greatest common Divisor) or GCF  (Greatest Common Factor) or HCF (Highest common factor).

    private static final Logger LOGGER =
        LogManager.getLogger(Divisibility.class.getName());


    /**
     * Computes Greatest Common Divisor
     * This is based on the Euclid's algorithm
     *
     * @param n
     * @param x
     * @return
     */
    public static BigDecimal findGCD(BigDecimal n, BigDecimal x) {
        LOGGER.debug("n = #" + n + "#");
        LOGGER.debug("x = #" + x + "#");

        if (x == BigDecimal.ZERO) {
            return n;
        }

        return findGCD(x, n.remainder(x));
    }

    /**
     * Computes Lowest Common Multiple
     * This is based on lcm(n,x) = (n*x)/gcd(n,x)
     *
     * @param n
     * @param x
     * @return
     */
    public static BigDecimal findLCM(BigDecimal n, BigDecimal x) {
        BigDecimal numerValue = n.multiply(x);
        BigDecimal gcdValue = findGCD(n, x);

        numerValue = numerValue.divide(gcdValue, 10, RoundingMode.HALF_UP);

        return numerValue;
    }
    /**
     * Constructor
     */
    public Divisibility() {
    }
}
