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
import java.math.BigInteger;
import noakcalc.Utilities.MathConstants;
import noakutils.UtilsMisc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Angles Class
 */
public class Angles {

    private static final Logger LOGGER =
        LogManager.getLogger(Angles.class.getName());


    /**
     *
     * Convert Degrees to DMS
     *
     * @param value
     * @return
     */
    public static BigDecimal calDegreeToDMS(BigDecimal value) {
        LOGGER.debug("value #" + value + "#");

        String[] valSplitStrings = UtilsMisc.stringSplit(value.toString(), ".");
        BigDecimal minute_part;

        if (valSplitStrings.length > 1) {
            valSplitStrings[1] = "." + valSplitStrings[1];
            minute_part = new BigDecimal(Double.parseDouble(valSplitStrings[1]
                .replaceAll(",", ""))).multiply(BigDecimal.valueOf(60));
                //.divide(BigDecimal.valueOf(100), 30, BigDecimal.ROUND_UP);

            String[] minuteSplitStrings = UtilsMisc.stringSplit(minute_part.toString(), ".");
            BigDecimal seconds_part;

            if (minuteSplitStrings.length > 1) {
                minuteSplitStrings[1] = "." + minuteSplitStrings[1];
                seconds_part = new BigDecimal(Double.parseDouble(minuteSplitStrings[1]
                    .replaceAll(",", ""))).multiply(BigDecimal.valueOf(60)).setScale(0, BigDecimal.ROUND_DOWN);
                    //.divide(BigDecimal.valueOf(100), 30, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN);
            }
            else {
                seconds_part = new BigDecimal(BigInteger.ZERO);
            }
            if (minuteSplitStrings[0].startsWith(MathConstants.SIX_HNDRD_THSND)) {
                value = new BigDecimal(valSplitStrings[0]).add(BigDecimal.ONE);
            }
            else {
                value = new BigDecimal(valSplitStrings[0] + "." + minuteSplitStrings[0] + seconds_part);
            }
        }
        else {
            value = new BigDecimal(valSplitStrings[0]);
        }

        return value;
    }

    /**
     *
     * Convert DMS to Degrees
     *
     * @param value
     * @return
     */
    public static BigDecimal calDMSToDegree(BigDecimal value) {
        LOGGER.debug("value #" + value + "#");

        String[] valSplitStrings = UtilsMisc.stringSplit(value.toString(), ".");
        BigDecimal minute_part;

        if (valSplitStrings.length > 1) {
            minute_part = new BigDecimal(Double.parseDouble(valSplitStrings[1]
                .replaceAll(",", "")))
                .divide(BigDecimal.valueOf(60), 30, BigDecimal.ROUND_UP);

            String[] minuteSplitStrings = UtilsMisc.stringSplit(minute_part.toString(), ".");
            BigDecimal seconds_part;

            if (minuteSplitStrings.length > 1) {
                seconds_part = new BigDecimal(Double.parseDouble(minuteSplitStrings[1]
                    .replaceAll(",", ""))).divide(BigDecimal.valueOf(3600), 30,
                        BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN);
            }
            else {
                seconds_part = new BigDecimal(BigInteger.ZERO);
            }
            if (minuteSplitStrings[0].startsWith(MathConstants.SIX_HNDRD_THSND)) {
                value = new BigDecimal(valSplitStrings[0]).add(BigDecimal.ONE);
            }
            else {
                value = new BigDecimal(valSplitStrings[0] + "." + minuteSplitStrings[0] + seconds_part);
            }
        }
        else {
            value = new BigDecimal(valSplitStrings[0]);
        }

        return value;
    }

    /**
     *
     * Convert Degrees to Radians
     *
     * @param value
     * @return
     */
    public static BigDecimal calDegreeToRadians(BigDecimal value) {
        value = value.multiply(BigDecimal.valueOf(MathConstants.PID/180));
        value = new BigDecimal(Math.toRadians(value.doubleValue()));

        return value;
    }

    /**
     *
     * Convert Radians to Degrees
     *
     * @param value
     * @return
     */
    public static BigDecimal calRadiansToDegrees(BigDecimal value) {
        value = value.multiply(BigDecimal.valueOf(180/MathConstants.PID));
        value = new BigDecimal(Math.toDegrees(value.doubleValue()));

        return value;
    }

    /**
     *
     * Convert Degrees to Gradians
     *
     * @param value
     * @return
     */
    public static BigDecimal calDegreeToGradians(BigDecimal value) {
        return value.multiply(BigDecimal.valueOf(1.1111));
    }

    /**
     *
     * Convert Gradians to Degrees
     *
     * @param value
     * @return
     */
    public static BigDecimal calGradiansToDegrees(BigDecimal value) {
        return value.divide(BigDecimal.valueOf(1.1111), 10, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * Convert Radians to Gradians
     *
     * @param value
     * @return
     */
    public static BigDecimal calRadiansToGradians(BigDecimal value) {
        return value.multiply(BigDecimal.valueOf(63.66198));
    }

    /**
     *
     * Convert Gradians to Radians
     *
     * @param value
     * @return
     */
    public static BigDecimal calGradiansToRadians(BigDecimal value) {
        return value.divide(BigDecimal.valueOf(63.66198), 10, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * Constructor
     */
    public Angles() {
    }
}
