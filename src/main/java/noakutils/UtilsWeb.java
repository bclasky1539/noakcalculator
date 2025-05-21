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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author bdeveloper
 */
public class UtilsWeb {

    /**
     * Parses the provided string to a valid web address, that can be used to fetch data.
     *
     * @param urlString specification for the URL
     * @return a new URL instance
     * @throws UtilsException
     */
    public static URL getUrl(String urlString) throws UtilsException {
        URL url = null;

        try {
            URI uri = new URI(urlString);
            url = uri.toURL(); // Convert URI to URL when needed
        } catch (URISyntaxException uRIExc) {
            // Handle invalid URI
            throw new UtilsException("URI not provided", uRIExc);
        } catch (MalformedURLException uRLExc) {
            // If urlString is null or length is 0 then catch MalformedURLException
            throw new UtilsException("URL not provided", uRLExc);
        }

        return url;
    }
}
