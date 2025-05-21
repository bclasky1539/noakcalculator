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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author quark95cos Since: Copyright(c) 2019
 */
public class Configs {

    private static Properties prop = new Properties();
    private int propCount;
    private final String filename;

    public Configs() {
        propCount = 0;
        filename = "";
        System.out.println("Configs: default constructor ... ");
    }

    public Configs(String ifilename) {
        propCount = 0;
        filename = ifilename;
        System.out.println("Configs: In Configs constructor filename = " + filename);
    }

    //public void loadProp(String filename) {
    public void loadProp() {
        try {
            System.out.println("Configs: In loadProp: " + filename);
            prop.load(new FileInputStream(filename));
            //prop.loadFromXML(new FileInputStream(filename + ".xml"));
            
            Enumeration<?> enumeration =prop.propertyNames();
            
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = prop.getProperty(key);
                propCount++;

                System.out.println(key + " = " + value);
            }
            
        }
        catch (IOException e) {
            System.err.println("In catch " + e);
        }

    }

    public int getPropCount() {
        return propCount;
    }

    public void saveProp(String title, String value, String comment) {
        try {
            prop.setProperty(title, value);
            prop.store(new FileOutputStream("config.tut"), comment);
            //prop.storeToXML(new FileOutputStream("config.xml"), comment);
        }
        catch (IOException e) {
            
        }
    }

    public String getProp(String title) {
        String value = prop.getProperty(title);

        return value;
    }

    public void setProp(String title, String value) {
        prop.setProperty(title, value);
    }

    /**
     *
     * @return
     */
    public Enumeration getPropertyNames() {
        return prop.propertyNames();
    }
}
