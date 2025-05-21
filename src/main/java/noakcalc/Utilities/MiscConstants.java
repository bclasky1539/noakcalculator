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
package noakcalc.Utilities;

/**
 *
 * MiscConstants Interface
 */
public interface MiscConstants {

    /**
     * About Information
     */
    public static final String ABOUT_INFO =
        "Noak Calculator 1.0.0  \u2015  A NOAK Java Project (Calculator)\n" +
        "Copyright \u00a9 2017-2025 Brian Clasky. All Rights Reserved.";

    /**
     * About Heading
     */
    public static final String ABOUT_HDNG = "About Noak Calculator...";

    /**
     * Misc constants
     */
    public final static String
        CLEAR_OUTPUT = "CLOPT",
        DIR_SYSTEM_PROPERTIES = "noakcalc/data/",
        //System.getProperty("user.dir") + 
        NUMBER_BUNDLE_NAME = DIR_SYSTEM_PROPERTIES + "text/number",
        OPERATOR_BUNDLE_NAME = DIR_SYSTEM_PROPERTIES + "text/operator",
        MISC_BUNDLE_NAME = DIR_SYSTEM_PROPERTIES + "text/misc",
        FUNCTION_BUNDLE_NAME = DIR_SYSTEM_PROPERTIES + "text/function",
        CONSTANT_BUNDLE_NAME = DIR_SYSTEM_PROPERTIES + "text/constant",
        PROP_EXT_NAME = ".properties",
        PIPE_DELIMITER = "|",
        SPACE_DELIMITER = " ";

    public final static String
        MME = "Mem",            // Memory Enabled
        WEN = "2en",            // 2nd Enabled
        CON = "CON";            // Constants

}
