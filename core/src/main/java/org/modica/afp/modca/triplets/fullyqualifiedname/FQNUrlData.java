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

package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;

public class FQNUrlData extends FullyQualifiedName {
    private final URL data;
    private final FQNType fqnType;

    FQNUrlData(int length, URL data, FQNType fqnType) {
        super(length);
        this.data = data;
        this.fqnType = fqnType;
    }

    /**
     * Returns the URL data.
     *
     * @return the URL
     */
    public URL getString() {
        return this.data;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.url;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNUrlData)) {
            return false;
        }
        FQNUrlData fqn = (FQNUrlData) o;
        return this.data.equals(fqn.data)
                && this.getFQNType() == fqn.getFQNType()
                && this.getLength() == fqn.getLength();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getLength();
        result = 31 * result + getFQNType().hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getTid().name() + ", " + getFQNType() + "=" + data;
    }

    @Override
    public FQNType getFQNType() {
        return fqnType;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("URL", data));
        return params;
    }
}
