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

package org.modica.afp.ioca;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

/**
 * Set IOCA Function Set Identification self-defining field.
 */
public class IocaFunctionSetId implements SelfDefiningField {

    private final FunctionSet fs;
    private static int LENGTH = 3;

    public IocaFunctionSetId(Parameters params) {
        byte length = params.getByte();
        assert length == 0x02;
        byte category = params.getByte();
        assert category == 0x01;
        fs = FunctionSet.getValue(params.getByte());
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    /**
     * Returns the IOCA function set.
     *
     * @return the function set
     */
    public FunctionSet getFunctionSet() {
        return fs;
    }

    @Override
    public byte getId() {
        return (byte) 0xF7;
    }

    @Override
    public String toString() {
        return "IOCAFunctionSetId functionset=" + fs;
    }

    @Override
    public String getName() {
        return "IOCAFunctionsetId";
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("FunctionSet", fs.name()));
        return params;
    }
}
