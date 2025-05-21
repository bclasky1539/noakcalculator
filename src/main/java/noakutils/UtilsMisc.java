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
package noakutils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bdeveloper
 */
public class UtilsMisc {

    /**
     *
     * @param text
     * @param delimiter
     * @return string splits of original string
     */
    public static String[] stringSplit(String text, String delimiter) {
        List<String> parts = new ArrayList<>();

        // If delimiter is null or empty split on blank
        if (delimiter == null || delimiter.length() == 0) {
            char[] varArr = text.trim().toCharArray();
            for (char c : varArr) {
                parts.add(String.valueOf(c));
            }
            return parts.toArray(String[]::new);
        }

        //Delimiter is not null or empty
        text += delimiter;

        for (int i = text.indexOf(delimiter), j=0; i != -1;) {
            String temp = text.substring(j,i);
            if(temp.trim().length() != 0) {
                parts.add(temp.trim());
            }
            j = i + delimiter.length();
            i = text.indexOf(delimiter,j);
        }

        return parts.toArray(String[]::new);
    }

    /**
     *
     * @param text
     * @param delimiter
     * @param startPos
     * @param endPos
     * @return string splits of original string
     */
    public static String[] stringSplit(String text, String delimiter,
        int startPos, int endPos) {
        int counter = 0;
        List<String> parts = new ArrayList<>();

        // If delimiter is null or empty split on blank
        if (delimiter == null || delimiter.length() == 0) {
            char[] varArr = text.trim().toCharArray();
            for (char c : varArr) {
                parts.add(String.valueOf(c));
            }
            return parts.toArray(String[]::new);
        }

        //Delimiter is not null or empty
        text += delimiter;

        for (int i = text.indexOf(delimiter), j=0; i != -1;) {
            counter += 1;
            String temp = text.substring(j,i);
            if(temp.trim().length() != 0 && counter >= startPos &&
                counter <= endPos) {
                parts.add(temp.trim());
            }
            j = i + delimiter.length();
            i = text.indexOf(delimiter,j);
        }

        return parts.toArray(String[]::new);
    }

    /**
     *
     * @param text
     * @return if text is null or contains whitespace
     */
    public static boolean isStringNullOrWhiteSpace(String text) {
        if (text == null) {
            return true;
        }

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param text
     * @return if text contains whitespace
     */
    public static boolean containsWhiteSpaces(String text) {
        // It cannot contain whitespaces if it is null or empty
        if (text == null || text.length() == 0) {
            return false;
        }

        for(int i = 0; i < text.length(); i++){
            if(Character.isWhitespace(text.charAt(i))){
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param text
     * @return if text contains only numbers
     */
    public static boolean containsOnlyNumbers(String text) {
        // It cannot contain only numbers if it is null or empty
        if (text == null || text.length() == 0) {
            return false;
        }

        return text.matches("^[0-9]+$");
    }

    /**
     *
     * @param text
     * @return if text contains only numbers, decimal, negative sign
     */
    public static boolean containsOnlyNumbersSpecial(String text) {
        // It cannot contain only numbers, etc.if it is null or empty
        if (text == null || text.length() == 0) {
            return false;
        }

        return text.matches("^([0-9]|.|-)+$");
    }

    /**
     *
     * @param text
     * @return if text contains only alphanumeric
     */
    public static boolean containsOnlyAlphaNumeric(String text) {
        // It cannot contain only numbers if it is null or empty
        if (text == null || text.length() == 0) {
            return false;
        }

        return text.matches("^([A-Za-z]|[0-9])+$");
    }

    /**
     *
     * @param text
     * @return replace all whitespace
     * @throws UtilsException
     */
    public static String removeWhiteSpaces(String text)
        throws UtilsException {

        // If text is null then return UtilsException
        if (text == null) {
            throw new UtilsException("No search text has been provided");
        }

        return text.replaceAll("\\s+","");
    }

    /**
     *
     * @param text
     * @return replace all non numeric characters
     * @throws UtilsException
     */
    public static String removeNumeric(String text)
        throws UtilsException {

        // If text is null then return UtilsException
        if (text == null) {
            throw new UtilsException("No search text has been provided");
        }

        return text.replaceAll("[0-9.]", "");
    }

    /**
     *
     * @param text
     * @return replace all non numeric characters
     * @throws UtilsException
     */
    public static String removeNonNumeric(String text)
        throws UtilsException {

        // If text is null then return UtilsException
        if (text == null) {
            throw new UtilsException("No search text has been provided");
        }

        return text.replaceAll("[^0-9.]", "");
    }

    /**
     *
     * @param text
     * @param removeText
     * @return replace all strings removeText
     * @throws UtilsException
     */
    public static String removeString(String text, String removeText)
        throws UtilsException {

        // If text is null then return UtilsException
        if (text == null) {
            throw new UtilsException("No text has been provided");
        }

        // If removeText is null then return UtilsException
        if (removeText == null) {
            throw new UtilsException("No remove text has been provided");
        }

        return text.replaceAll(removeText, "");
    }

    /**
     *
     * @param text
     * @param c
     * @param n
     * @return index of character in string
     */
    public static int findIndexOf(String text, char c, int n) {
        if (text == null || n < 1) {
            return -1;
        }

        int pos = text.indexOf(c);

        while (--n > 0 && pos != -1) {
            pos = text.indexOf(c, pos + 1);
        }

        return pos;
    }

    /**
     *
     * @param text
     * @param str
     * @param n
     * @return index of string in string
     */
    public static int findIndexOf(String text, String str, int n) {
        if (text == null || n < 1) {
            return -1;
        }

        int pos = text.indexOf(str);

        while (--n > 0 && pos != -1) {
            pos = text.indexOf(str, pos + 1);
        }

        return pos;
    }

    /**
     *
     * @param text
     * @param c
     * @param n
     * @return last index of character in string
     */
    public static int findLastIndexOf(String text, char c, int n) {
        if (text == null || n < 1) {
            return -1;
        }

        int pos = text.length();

        while (n-- > 0 && pos != -1) {
            pos = text.lastIndexOf(c, pos - 1);
        }

        return pos;
    }

    /**
     *
     * @param text
     * @param searchText
     * @return count of string in string
     * @throws UtilsException
     */
    public static int countMatches(String text, String searchText)
        throws UtilsException {

        // If searchText is null then return UtilsException
        if (searchText == null || searchText.length() == 0) {
            throw new UtilsException("No search text has been provided");
        }

        Pattern pattern = Pattern.compile(searchText, Pattern.LITERAL);

        Matcher matcher = pattern.matcher(text);

        int count = 0;
        int pos = 0;

        while (matcher.find(pos)) {
            count++;
            pos = matcher.start() + 1;
        }

        return count;
    }

    /**
     *
     * @param text
     * @param searchText
     * @return all positions of string in string
     * @throws UtilsException
     */
    public static ArrayList<Integer> getMatchPositions(String text, String searchText)
        throws UtilsException {

        ArrayList<Integer> textArray = new ArrayList<>();

        // If searchText is null then return UtilsException
        if (searchText == null || searchText.length() == 0) {
            throw new UtilsException("No search text has been provided");
        }

        Pattern pattern = Pattern.compile(searchText, Pattern.LITERAL);

        Matcher matcher = pattern.matcher(text);

        int pos = 0;

        while (matcher.find(pos)) {
            pos = matcher.start() + 1;
            textArray.add(pos);
        }

        return textArray;
    }

    /**
     *
     * @param text
     * @return s
     * @throws UtilsException
     */
    public static String stripZeros(String text)
        throws UtilsException {

        // If text is null then return UtilsException
        if (text == null) {
            throw new UtilsException("No text has been provided");
        }

        if (text.contains(".")) {
            while (text.length() > 1 && (text.endsWith("0") ||
                text.endsWith("."))) {
                text = text.substring(0, text.length() - 1);
            }
        }

        return text;
    }

    /**
     *
     * Beep!
     *
     */
    public static void beep() {
        java.awt.Toolkit.getDefaultToolkit().beep();
    }
}
