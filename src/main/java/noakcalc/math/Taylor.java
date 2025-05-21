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

/**
 *
 * Taylor Class
 */
public class Taylor {

    private static int n;


    /**
     * Returns approximate value of e^x
     * using sum of first n terms of Taylor Series
     *
     * @param x
     * @return sum
     */
    public static double exponential(double x) {
        double sum = 1.0f; // initialize sum of series

        for (int i = Taylor.n - 1; i > 0; --i ) {
            sum = 1 + x * sum / i;
        }

        return sum;
    }

    /**
     * Returns approximate value of e^x
     * using sum of first n terms of Taylor Series
     *
     * @param n
     * @param x
     * @return sum
     */
    public static double exponential(int n, double x) {
        double sum = 1.0f; // initialize sum of series

        for (int i = n - 1; i > 0; --i ) {
            sum = 1 + x * sum / i;
        }

        return sum;
    }
    /**
     * Constructor
     * @param n
     */
    public Taylor(int n) {
        Taylor.n = n;
    }
}
