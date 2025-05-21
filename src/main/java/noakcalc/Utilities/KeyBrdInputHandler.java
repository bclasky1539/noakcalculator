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

import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import noakcalc.gui.SubCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * KeyBrdInputHandler Class Extends KeyAdapter
 */
public class KeyBrdInputHandler extends KeyAdapter {
    private static final Logger LOGGER =
            LogManager.getLogger(KeyBrdInputHandler.class.getName());

    protected boolean keyPressed;
    protected int lastKey;
    protected String keyString;
    private final SubCalculator subCalculator;


    /**
     * Constructor
     * @param subCalculator
     */
    public KeyBrdInputHandler(SubCalculator subCalculator) {
        this.keyPressed = false;
        this.lastKey = -999;
        this.keyString = CalcConstants.XXX;
        this.subCalculator = subCalculator;
    }

    /**
     * Acknowledges the key that is typed. The key is consumed so that it does
     * not flash in the text field
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    /**
     * Acknowledges the key that is pressed. They key is captured and processed
     * accordingly
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        LOGGER.debug("getKeyChar #" + e.getKeyChar() + "#");
        LOGGER.debug("getKeyCode #" + e.getKeyCode() + "#");
        LOGGER.debug("getNumMode #" + subCalculator.getNumMode() + "#\n");

        if (e.getID() == KeyEvent.KEY_PRESSED) {
            this.keyPressed = true;
            // Break this up into different methods so that the Scientific and Programmer calculators can share
            // the same methods and the Programmer calculator can have additional methods so well.
            // This is true for all other calculators added later.
            // Add Keyboard shortcuts in jbutton tooltips.

            switch (e.getKeyCode()) {
                case KeyEvent.VK_NUMPAD0: case KeyEvent.VK_NUMPAD1:
                    this.setNumeric(e.getKeyChar());
                    break;
                case KeyEvent.VK_4:
                case KeyEvent.VK_6: case KeyEvent.VK_7:
                case KeyEvent.VK_NUMPAD2: case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_NUMPAD4: case KeyEvent.VK_NUMPAD5:
                case KeyEvent.VK_NUMPAD6: case KeyEvent.VK_NUMPAD7:
                    if (subCalculator.getNumMode().equals(CalcConstants.BIN)) {
                        this.setNotValid();
                    }
                    else {
                        this.setNumeric(e.getKeyChar());
                    }
                    break;
                case KeyEvent.VK_NUMPAD8: case KeyEvent.VK_NUMPAD9:
                    if (subCalculator.getNumMode().equals(CalcConstants.BIN) ||
                        subCalculator.getNumMode().equals(CalcConstants.OCT)) {
                        this.setNotValid();
                    }
                    else {
                        this.setNumeric(e.getKeyChar());
                    }
                    break;
                case KeyEvent.VK_B: case KeyEvent.VK_F:
                    if (subCalculator.getNumMode().equals(CalcConstants.BIN) ||
                        subCalculator.getNumMode().equals(CalcConstants.OCT) ||
                        subCalculator.getNumMode().equals(CalcConstants.DEC)) {
                        this.setNotValid();
                    }
                    else {
                        this.setNumeric(e.getKeyChar());
                    }
                    break;
                case KeyEvent.VK_1: case KeyEvent.VK_3: case KeyEvent.VK_5:
                case KeyEvent.VK_8: case KeyEvent.VK_9: case KeyEvent.VK_0:
                case KeyEvent.VK_EQUALS:
                    this.setNumericOperSpecial(e.getKeyChar(), e.getKeyCode());
                    break;
                case KeyEvent.VK_2:
                    this.setNumericOperSpecialKey2(e.getKeyChar(), e.getKeyCode());
                    break;
                case KeyEvent.VK_MINUS: case KeyEvent.VK_MULTIPLY:
                case KeyEvent.VK_ADD: case KeyEvent.VK_SUBTRACT:
                    this.setBasicOper(e.getKeyChar());
                    break;
                case KeyEvent.VK_SLASH: case KeyEvent.VK_DIVIDE:
                    this.setDivideOper();
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    this.setBackspace();
                    break;
                case KeyEvent.VK_ENTER:
                    this.setEqual();
                    break;
                case KeyEvent.VK_PERIOD:
                    if (subCalculator.getNumMode().equals(CalcConstants.DEC)) {
                        this.setPeriod();
                    }
                    else {
                        this.setNotValid();
                    }
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_C:
                case KeyEvent.VK_D:
                case KeyEvent.VK_E:
                    this.setMiscSpecial(e.getKeyChar(), e.getKeyCode());
                    break;
                //case KeyEvent.VK_M:
                //    this.setModulus();
                //    break;
                case KeyEvent.VK_N:
                    this.setNegate();
                    break;
                case KeyEvent.VK_P:
                    this.setPI();
                    break;
                case KeyEvent.VK_SHIFT:
                    LOGGER.debug("SHIFT #" + e.getKeyChar() + "#");
                    LOGGER.debug("SHIFT #" + e.getKeyCode() + "#");
                    this.lastKey = KeyEvent.VK_SHIFT;
                    this.setNULLXXX();
                    break;
                case KeyEvent.VK_CONTROL:
                    LOGGER.debug("CONTROL #" + e.getKeyChar() + "#");
                    LOGGER.debug("CONTROL #" + e.getKeyCode() + "#");
                    this.lastKey = KeyEvent.VK_CONTROL;
                    this.setNULLXXX();
                    break;
                case KeyEvent.VK_ALT:
                    LOGGER.debug("ALT #" + e.getKeyChar() + "#");
                    LOGGER.debug("ALT #" + e.getKeyCode() + "#");
                    this.lastKey = KeyEvent.VK_ALT;
                    this.setNULLXXX();
                    break;
                //This needs to be tested on a Windows and Linux box as well
                case KeyEvent.VK_META:
                    LOGGER.debug("META #" + e.getKeyChar() + "#");
                    LOGGER.debug("META #" + e.getKeyCode() + "#");
                    this.lastKey = KeyEvent.VK_META;
                    this.setNULLXXX();
                    break;
                default:
                    this.setNotValid();
                    break;
            }
            if (this.keyString != null && this.keyString.equals(CalcConstants.SND)) {
                subCalculator.set2ndKeys();
            }
            else if (this.keyString != null && !this.keyString.equals(CalcConstants.XXX)) {
                subCalculator.setCalcInfo(this.keyString);
            }
        }
    }

    /**
     * Acknowledges the key that is released. This processes the key string to
     * the primary and secondary text fields
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        LOGGER.debug("getKeyChar #" + e.getKeyChar() + "#");
        LOGGER.debug("getKeyCode #" + e.getKeyCode() + "#\n\n");

        if (this.keyString != null) { //&& !keyString.equals(CalcConstants.DZZ)
            LOGGER.debug("if block\n");

            subCalculator.getJtxtPrimary().setText(subCalculator.getCalc().getPrmDisplayText());
            if ((this.keyString.equals(CalcConstants.DZZ) || this.keyString.equals(CalcConstants.BSP)) ||
                this.keyString.equals(CalcConstants.SND)) {
                // Ignore
            } else {
                subCalculator.getJtxtSecondary().setText(subCalculator.getCalc().getSecScreenText());
            }
        }
        this.keyPressed = false;
    }

    /**
     * Returns if key is pressed
     *
     * @return keyPressed
     */
    public boolean isKeyPressed() {
        return this.keyPressed;
    }

    /**
     * Set keyString to numeric key pressed
     *
     * @param key
     */
    protected void setNumeric(char key) {
        this.keyString = String.valueOf(key).toUpperCase();
    }

    /**
     * Set keyString to basic operation key pressed
     *
     * @param key
     */
    protected void setBasicOper(char key) {
        this.keyString = String.valueOf(key);
    }

    /**
     *
     * Set keyString to special keys pressed where the key has multiple
     * results
     *
     * @param key
     * @param keyCode
     */
    protected void setNumericOperSpecial(char key, int keyCode) {
        if (this.lastKey == KeyEvent.VK_SHIFT) {
            switch (keyCode) {
                case KeyEvent.VK_1:
                    this.setFactorial();
                    break;
                case KeyEvent.VK_3:
                    this.setCubeRoot();
                    break;
                case KeyEvent.VK_5:
                    this.setModulus();
                    break;
                case KeyEvent.VK_8:
                    this.setMultiply();
                    break;
                case KeyEvent.VK_9:
                    this.setLeftBracket();
                    break;
                case KeyEvent.VK_0:
                    this.setRightBracket();
                    break;
                case KeyEvent.VK_EQUALS:
                    this.setAdd();
                    break;
                default:
                    // possibly put exception handling
                    break;
            }
            this.lastKey = -999;
        }
        else {
            switch (subCalculator.getNumMode()) {
                case CalcConstants.BIN:
                    if (keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_1 ||
                        keyCode == KeyEvent.VK_EQUALS) {
                        this.setNumeric(key);
                    }
                    else {
                        this.setNotValid();
                    }
                    break;
                case CalcConstants.OCT:
                    if (keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_1 ||
                        keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_5 ||
                        keyCode == KeyEvent.VK_EQUALS) {
                        this.setNumeric(key);
                    }
                    else {
                        this.setNotValid();
                    }
                    break;
                default:
                    this.setNumeric(key);
                    break;
            }
        }
    }

    /**
     * Set keyString to special keys pressed where the key has multiple
     * results
     *
     * @param key
     * @param keyCode
     */
    protected void setNumericOperSpecialKey2(char key, int keyCode) {
        switch (this.lastKey) {
            case KeyEvent.VK_SHIFT:
                switch (keyCode) {
                    case KeyEvent.VK_2:
                        this.setSquareRoot();
                        break;
                    default:
                        // possibly put exception handling
                        break;
                }   this.lastKey = -999;
                break;
            case KeyEvent.VK_ALT:
                switch (keyCode) {
                    case KeyEvent.VK_2:
                        this.set2nd();
                        break;
                    default:
                        // possibly put exception handling
                        break;
                }   this.lastKey = -999;
                break;
            default:
                if (subCalculator.getNumMode().equals(CalcConstants.BIN)) {
                    this.setNotValid();
                }
                else {
                    this.setNumeric(key);
                }   break;
        }
    }

    /**
     * Set keyString to special keys pressed where the key has multiple
     * results
     *
     * @param key
     * @param keyCode
     */
    protected void setMiscSpecial(char key, int keyCode) {
        switch (this.lastKey) {
            case KeyEvent.VK_SHIFT:
                switch (keyCode) {
                    case KeyEvent.VK_A:
                        this.setNotValid();
                        break;
                    case KeyEvent.VK_C: //" C "
                        this.setClear(key);
                        break;
                    case KeyEvent.VK_E: //"CE"
                        this.setClearEntry();
                        break;
                    default:
                        // possibly put exception handling
                        break;
                }
                this.lastKey = -999;
                break;
            case KeyEvent.VK_CONTROL:
                switch (keyCode) {
                    case KeyEvent.VK_A: // Help/About
                        JOptionPane.showMessageDialog(null, MiscConstants.ABOUT_INFO,
                            MiscConstants.ABOUT_HDNG, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case KeyEvent.VK_C: // Copy Answer
                        getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(subCalculator.getJtxtPrimary().getText()), null);
                        break;
                    case KeyEvent.VK_E: // Copy Equation
                        getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(subCalculator.getJtxtSecondary().getText()), null);
                        break;
                    default:
                        // possibly put exception handling
                        break;
                }
                this.lastKey = -999;
                break;
            default:
                // VK_A, VK_C and VK_E are not valid keys in the Scientific Calculator
                //this.setNotValid();
                if (subCalculator.getNumMode().equals(CalcConstants.BIN) ||
                    subCalculator.getNumMode().equals(CalcConstants.OCT) ||
                    subCalculator.getNumMode().equals(CalcConstants.DEC)) {
                    this.setNotValid();
                }
                else {
                    this.setNumeric(key);
                }
                break;
        }
    }

    /**
     * Set keyString to divide operator
     *
     */
    protected void setDivideOper() {
        this.keyString = CalcConstants.DIV;
    }

    /**
     * Set keyString to clear operator
     *
     * @param key
     */
    protected void setClear(char key) {
        //this.keyString = " " + String.valueOf(key).toUpperCase() + " ";
        this.keyString = CalcConstants.CXX;
    }

    /**
     * Set keyString to clear entry operator
     *
     */
    protected void setClearEntry() {
        this.keyString = CalcConstants.CEX;
    }

    /**
     * Set keyString to backspace operator
     *
     */
    protected void setBackspace() {
        this.keyString = CalcConstants.BSP;
    }

    /**
     * Set keyString to equal operator
     *
     */
    protected void setEqual() {
        this.keyString = CalcConstants.EQU;
    }

    /**
     * Set keyString to period operator
     *
     */
    protected void setPeriod() {
        this.keyString = CalcConstants.DOT;
    }

    /**
     * Set keyString to modulus operator
     *
     */
    protected void setModulus() {
        this.keyString = CalcConstants.MOD;
    }

    /**
     * Set keyString to negate operator
     *
     */
    protected void setNegate() {
        this.keyString = CalcConstants.NEG;
    }

    /**
     * Set keyString to factorial operator
     *
     */
    protected void setFactorial() {
        this.keyString = CalcConstants.FCT;
    }

    /**
     * Set keyString to multiply operator
     *
     */
    protected void setMultiply() {
        this.keyString = CalcConstants.MUL;
    }

    /**
     * Set keyString to left bracket operator
     *
     */
    protected void setLeftBracket() {
        this.keyString = CalcConstants.BR1;
    }

    /**
     * Set keyString to right bracket operator
     *
     */
    protected void setRightBracket() {
        this.keyString = CalcConstants.BR2;
    }

    /**
     * Set keyString to add operator
     *
     */
    protected void setAdd() {
        this.keyString = CalcConstants.ADD;
    }

    /**
     * Set keyString to PI operator
     *
     */
    protected void setPI() {
        this.keyString = CalcConstants.PIX;
    }

    /**
     * Set keyString to square root operator
     *
     */
    protected void setSquareRoot() {
        this.keyString = CalcConstants.SQR;
    }

    /**
     * Set keyString to cube root operator
     *
     */
    protected void setCubeRoot() {
        this.keyString = CalcConstants.CUB;
    }

    /**
     * Set keyString to 2nd operator
     *
     */
    protected void set2nd() {
        this.keyString = CalcConstants.SND;
    }

    /**
     * Set keyString to NULL state
     *
     */
    protected void setNULLXXX() {
        this.keyString = CalcConstants.XXX;
    }

    /**
     * Set keyString to not valid state
     *
     */
    protected void setNotValid() {
        this.keyString = CalcConstants.DZZ;
    }
}
