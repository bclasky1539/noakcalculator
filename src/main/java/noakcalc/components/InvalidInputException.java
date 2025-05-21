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

import noakutils.UtilsException;

/**
 *
 * InvalidInputException Class Extends UtilsException
*/
public class InvalidInputException extends UtilsException {

    /**
     * Default constructor
     *
     */
    public InvalidInputException() {
	super();
    }

    /**
     * Create exception with error message
     *
     * @param message
     */
    public InvalidInputException(String message) {
    	super(message);
    }
}
