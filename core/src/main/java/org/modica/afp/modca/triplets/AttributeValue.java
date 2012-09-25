/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

/** The Attribute Value triplet is used to specify a value for a document attribute. */
public class AttributeValue extends Triplet {

    private final int length;
    private final String attributeValue;
    private final TripletIdentifiers tId;

    public AttributeValue(int tripletLength, TripletIdentifiers tId, Parameters params)
            throws UnsupportedEncodingException {
        this.length = tripletLength;
        this.tId = tId;
        // Two reserved bytes
        assert params.getByte() == (byte) 0x00;
        assert params.getByte() == (byte) 0x00;
        attributeValue = params.getString(length - 4);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public TripletIdentifiers getTid() {
        return tId;
    }

    /**
     * Is a character string which specifies the value of a document attribute. If this parameter is
     * omitted, the value of the document attribute is specified to be null, that is, no value is
     * assigned to theattribute.
     *
     * @return the attribute value
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("AttributeValue", attributeValue));
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AttributeValue)) {
            return false;
        }
        AttributeValue other = (AttributeValue) o;
        return this.length == other.length
                && this.attributeValue.equals(other.attributeValue);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + length;
        result = result * 31 + attributeValue.hashCode();
        result = result * 31 + attributeValue.hashCode();
        return result;
    }

}
