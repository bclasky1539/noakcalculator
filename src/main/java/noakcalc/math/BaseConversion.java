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
import noakcalc.Utilities.CalcConstants;
import noakcalc.Utilities.MathConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bdeveloper
 */
public class BaseConversion {

    private static final Logger LOGGER =
        LogManager.getLogger(BaseConversion.class.getName());


    /**
     * Converts BigDecimal to a radix
     *
     * * @return
     */
    private static String dec2rad(String str, int rad) {
        LOGGER.debug("str #" + str + "#");
        LOGGER.debug("rad #" + rad + "#");
        BigDecimal bd = new BigDecimal(str);
        String ret = "";
        bd = bd.setScale(0, RoundingMode.DOWN);
        LOGGER.debug("bd #" + bd + "#");
        // Radix value of bd
        ret = bd.toBigInteger().toString(rad);
        LOGGER.debug("at end ret #" + ret + "#");

        return "".equals(ret) ? MathConstants.ZERO : ret.toUpperCase();
    }

    /**
     * Converts a radical value to BigDecimal
     *
     * @return bd.toPlainString()
     */
    private static String rad2dec(String str, BigDecimal rad) {
        LOGGER.debug("str #" + str + "#");
        LOGGER.debug("rad #" + rad + "#");
        char sign = str.charAt(0);
        LOGGER.debug("sign #" + sign + "#");
        BigDecimal bd = new BigDecimal(0);
        int chr = 0;

        for (int i = str.length() - 1, p = 0; i >= 0; i--, p++) {
            chr = Character.toUpperCase(str.charAt(i));
            if (chr >= '0' && chr <= '9') {
                bd = bd.add(rad.pow(p).multiply(new BigDecimal(chr - '0')));
            }
            else if (chr >= 'A' && chr <= 'Z') {
                bd = bd.add(rad.pow(p).multiply(new BigDecimal(chr - 'A' + 10)));
            }
            else {
                p--; // Ignore other characters as if they weren't there ;)
            }
        }

        LOGGER.debug("before negate bd #" + bd + "#");
        if (sign == '-') {
            bd = bd.negate();
        }
        LOGGER.debug("after negate bd #" + bd + "#");

        return bd.toPlainString();
    }

    /**
     * Convert a decimal value to radical value.
     *
     * @param str
     * @param mode
     * @return str
     */
    public static String dec2rad(String str, String mode) {
        LOGGER.debug("str #" + str + "#");
        LOGGER.debug("mode #" + mode + "#");
        if (null != mode) {
            switch (mode) {
                case CalcConstants.BIN:
                    //return dec2rad(str, new BigDecimal(2));
                    return dec2rad(str, 2);
                case CalcConstants.OCT:
                    //return dec2rad(str, new BigDecimal(8));
                    return dec2rad(str, 8);
                case CalcConstants.HEX:
                    //return dec2rad(str, new BigDecimal(16));
                    return dec2rad(str, 16);
                default:
                    break;
            }
        }

        return str;
    }

    /**
     * Convert a radical value to decimal
     *
     * @param str
     * @param mode
     * @return str
     */
    public static String rad2dec(String str, String mode) {
        LOGGER.debug("str #" + str + "#");
        LOGGER.debug("mode #" + mode + "#");
        if (null != mode) {
            switch (mode) {
                case CalcConstants.BIN:
                    return rad2dec(str, new BigDecimal(2));
                case CalcConstants.OCT:
                    return rad2dec(str, new BigDecimal(8));
                case CalcConstants.HEX:
                    return rad2dec(str, new BigDecimal(16));
                default:
                    break;
            }
        }

        return str;
    }
    /**
     * Constructor
     */
    public BaseConversion() {
    }
}
