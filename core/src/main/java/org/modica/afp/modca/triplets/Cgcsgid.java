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

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.common.StringUtils;

/**
 * Coded Graphical Character Set Global Identifier triplet is used to establish the values of the
 * code page and character set for interpretation of all structured field parameters having a
 * CHAR data type, such as name parameters, except where such parameters define a fixed encoding.
 */
public abstract class Cgcsgid extends Triplet {
    private static TripletIdentifiers tId = TripletIdentifiers.coded_graphic_character_set_global_identifier;
    private static final int LENGTH = 6;

    /** {@inheritDoc} */
    @Override
    public int getLength() {
        return LENGTH;
    }

    /** {@inheritDoc} */
    @Override
    public TripletIdentifiers getTid() {
        return tId;
    }

    /**
     * The concatenation of the GCSGID and CPGID is currently referred to as the Coded Graphic
     * Character Set Global Identifier (CGCSGID). In the past, it was also known as the Global
     * Character Set Identifier (GCID).
     */
    public static final class Cpgid extends Cgcsgid {
        private final int gcsgid;
        private final int cpgid;

        private Cpgid(int gcsgid, int cpgid) {
            this.gcsgid = gcsgid;
            this.cpgid = cpgid;
        }

        /**
         * Specifies the Graphic Character Set Global Identifier of the character set to be used in
         * conjunction with the Code Page Global Identifier to identify the graphic characters that
         * are represented by code points in any parameter with a data type of CHAR. The GCSGID may
         * identify a subset or the maximal set of all of the graphic characters supported for the
         * associated code page. Valid values for Graphic Character Set Global Identifiers are 1
         * through 65534. A value of 65535 (X'FFFF') indicates that a character set consisting of
         * all characters that have assigned code points in the associated code page is to be used.
         *
         * @return the Global Character Set Global Identifier
         */
        public int getGcsgid() {
            return gcsgid;
        }

        /**
         * Specifies the Code Page Global Identifier of the code page to be used in conjunction with
         * the character set to identify the graphic characters that are represented by code points
         * in any parameter with a data type of CHAR. Valid values for Code Page Global Identifiers
         * are 1 through 65534
         *
         * @return the Code Page Global Identifier
         */
        public int getCpgid() {
            return cpgid;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Cpgid)) {
                return false;
            }
            Cpgid obj = (Cpgid) o;
            return this.gcsgid == obj.gcsgid
                    && this.cpgid == obj.cpgid;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + gcsgid;
            result = 31 * result + cpgid;
            result = 31 * result + getTid().hashCode();
            return result;
        }

        @Override
        public List<ParameterAsString> getParameters() {
            List<ParameterAsString> params = new ArrayList<ParameterAsString>();
            params.add(new ParameterAsString("GCSGID", StringUtils.toHex(gcsgid, 4)));
            params.add(new ParameterAsString("CPGID", StringUtils.toHex(cpgid, 4)));
            return params;
        }
    }

    /**
     * Coded Character Set Identifier. Defined by the Character Data Representation Architecture.
     * Can be resolved to specify the code page and character set for interpretation of parameters
     * with CHAR data type. See the Character Data Representation Architecture Reference and
     * Registry, SC09-2190, for detailed information.
     */
    public static final class Ccsid extends Cgcsgid {
        private final int ccsid;

        private Ccsid(int ccsid) {
            this.ccsid = ccsid;
        }

        /**
         * Return the Coded Character Set Identifier.
         *
         * @return the ccsid
         */
        public int getCcsid() {
            return ccsid;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Ccsid)) {
                return false;
            }
            Ccsid obj = (Ccsid) o;
            return this.ccsid == obj.ccsid;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + ccsid;
            return result;
        }

        @Override
        public List<ParameterAsString> getParameters() {
            List<ParameterAsString> params = new ArrayList<ParameterAsString>();
            params.add(new ParameterAsString("CCSID", StringUtils.toHex(ccsid, 4)));
            return params;
        }
    }

    /**
     * Returns the Coded Graphic Character Set Global Identifier.
     *
     * @param context holds contextual information about the structured fields being parsed.This
     * object will be modified to use the appropriate code page once this triplet has been parsed
     * @param data the triplet data array
     * @param position the byte position in the data array
     *
     * @return the CGCSGID
     */
    public static Cgcsgid parse(Parameters params, Context context) {
        int gcsgid = (int) params.getUInt(2);
        int ccsidOrCpgid = (int) params.getUInt(2);
        if (gcsgid == 0x0000) {
            return new Ccsid(ccsidOrCpgid);
        } else {
            Cpgid cpgid = new Cpgid(gcsgid, ccsidOrCpgid);
            context.put(ContextType.MODCA_GCSGID, cpgid.cpgid);
            return new Cpgid(gcsgid, ccsidOrCpgid);
        }
    }
}
