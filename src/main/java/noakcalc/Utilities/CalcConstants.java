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
 * CalcConstants Interface
 */
public interface CalcConstants {

    // Static class data fields
    public static final String SCI = "Scientific";     // Scientific Mode
    public static final String STD = "Standard";       // Standard Mode

    public static final String GRP = "Digit Grouping"; // Digit Grouping (Extend this to be different digit groupings to select)
    public static final String OPT = "Output";         // Output TextArea

    public static final String HEX = "Hex";            // Hexadecimal
    public static final String DEC = "Dec";            // Decimal
    public static final String OCT = "Oct";            // Octal
    public static final String BIN = "Bin";            // Binary

    public static final String DMS = "dms";            // Degree Minute Second (Sexagesimal)
    public static final String DEG = "deg";            // Degrees
    public static final String RAD = "rad";            // Radians
    public static final String GRA = "grad";           // Gradians

    public static final String MCX = "MC";             // Memory Clear
    public static final String MRX = "MR";             // Memory Recall
    public static final String MSX = "MS";             // Memory Store
    public static final String MPX = "M+";             // Memory Add
    public static final String MMX = "M-";             // Memory Subtract

    public static final String XXX = null;             // Reserved
    public static final String BR1 = "(";              // Bracket Open
    public static final String BR2 = ")";              // Bracket Close
    public static final String BSP = "BSP";            // Backspace \u2190, ⇐
    public static final String BS2 = "";               // Backspace Alternate
    public static final String CEX = "CE";             // Clear Entry
    public static final String CXX = "CCC";            // Clear

    public static final String SIN = "sin";            // Sine
    public static final String COS = "cos";            // Cosine
    public static final String TAN = "tan";            // Tangent
    public static final String CSC = "csc";            // Cosecant
    public static final String SEC = "sec";            // Secant
    public static final String COT = "cot";            // CoTangent
    public static final String SNH = "sinh";           // Hyperbolic Sine
    public static final String CSH = "cosh";           // Hyperbolic Cosine
    public static final String TNH = "tanh";           // Hyperbolic Tangent
    public static final String CCH = "csch";           // Hyperbolic Cosecant
    public static final String SCH = "sech";           // Hyperbolic Secant
    public static final String CTH = "coth";           // Hyperbolic CoTangent
    public static final String NEG = "±";              // Negate \u00b1
    public static final String REC = "1/x";            // Reciprocal
    public static final String INT = "int";            // Integer
    public static final String FRA = "frac";           // Fraction
    public static final String ABS = "abs";            // Absolute Value
    public static final String NLG = "ln";             // Natural Logarithm
    public static final String LOG = "log";            // Logarithm base 10
    public static final String LG2 = "lg2";            // Logarithm base 2
    public static final String LGX = "logx";           // Logarithm base x
    public static final String PWT = "10^x";           // X Power of Ten 10\u02E3
    public static final String PW2 = "2^x";            // X Power of Two
    public static final String F2E = "F-E";            // Fine to Exponential
    public static final String EXP = "Exp";            // Exponential
    public static final String ELX = "e^x";            // Natural exponential function
    public static final String NCR = "nCr";            // Combination
    public static final String NPR = "nPr";            // Permutation
    public static final String NC2 = "nCrr";           // Combination with numbers repeating
    public static final String NP2 = "nPrr";           // Permutation with numbers repeating
    public static final String LCM = "lcm";            // Lowest Common Multiple
    public static final String GCD = "gcd";            // Greatest Common Denominator

    public static final String SRT = "√x";             // Square Root \u221a
    public static final String CRT = "3√x";            // Cube Root \u221bx
    public static final String SQR = "x^2";            // Squared x\u00b2
    public static final String CUB = "x^3";            // Cubed x\u00b3
    public static final String FCT = "n!";             // Factorial
    public static final String PER = "%";              // Percentage
    public static final String POW = "x^y";            // Power x\u02B8
    public static final String NRT = "n√x";            // N Root
    public static final String FLR = "flr";            // Floor ⌊x⌋
    public static final String CLG = "clg";            // Ceiling ⌈x⌉
    public static final String MIN = "min";            // Minimum of two numbers
    public static final String MAX = "max";            // Maximum of two numbers

    public static final String DZZ = "not valid";      // Not valid
    public static final String DG0 = "0";              // Digit 0
    public static final String DG1 = "1";              // Digit 1
    public static final String DG2 = "2";              // Digit 2
    public static final String DG3 = "3";              // Digit 3
    public static final String DG4 = "4";              // Digit 4
    public static final String DG5 = "5";              // Digit 5
    public static final String DG6 = "6";              // Digit 6
    public static final String DG7 = "7";              // Digit 7
    public static final String DG8 = "8";              // Digit 8
    public static final String DG9 = "9";              // Digit 9
    public static final String DOT = ".";              // Dot/Point

    public static final String DGA = "A";              // Hex digit A
    public static final String DGB = "B";              // Hex digit B
    public static final String DGC = "C";              // Hex digit C
    public static final String DGD = "D";              // Hex digit D
    public static final String DGE = "E";              // Hex digit E
    public static final String DGF = "F";              // Hex digit F

    public static final String MUL = "*";              // Multiply \u00D7
    public static final String DIV = "÷";              // Divide \u00F7
    public static final String MOD = "MDL";            // Modulus
    public static final String ADD = "+";              // Add \u002B
    public static final String SUB = "-";              // Subtract \u2212
    public static final String EQU = "=";              // Equal to \u003D
    public static final String SND = "2nd";            // 2nd Key

    public static final String CONST = "CONST";        // Constant
    public static final String EUL = "EUL";            // Euler's number
    public static final String PIX = "\u03C0";         // Constant π (Pi)
    public static final String GLR = "\u03C6";         // Golden Ratio φ (Phi)
    public static final String LSP = "LSP";            // Light Speed (c)
    public static final String COUL = "COUL";          // Elementary Charge (C)
    public static final String IMPD = "IMPD";          // Impedence of Vacuum (Ω)
    public static final String PBVC = "PBVC";          // Permeability of Vacuum (Newton/Ampere2)
    public static final String PTVC = "PTVC";          // Permitivity of Vacuum (Farad/m)
    public static final String PLCK = "PLCK";          // Plancks Constant (J Hz-1)
    public static final String BLTZ = "BLTZ";          // Boltzmann Constant (J K-1)
    public static final String SBTZ = "SBTZ";          // Stefan-Boltzmann Constant (watts m-2 kelvin-4)
    public static final String AVOG = "AVOG";          // Avogrado's Number (mol-1)
    public static final String GRAV = "GRAV";          // Gravitation Constant (m3 kg-1 s-2)
    public static final String ACCL = "ACCL";          // Earth Acceleration (m s-2)

    public static final String AVG = "\u03BC";         // Statistical Average μ (Mu)
    public static final String SUM = "\u2211";         // Statistical Sum ∑
    public static final String LST = "lst";            // Statistical Add
    public static final String CLS = "clr";            // Clear stat list
}
