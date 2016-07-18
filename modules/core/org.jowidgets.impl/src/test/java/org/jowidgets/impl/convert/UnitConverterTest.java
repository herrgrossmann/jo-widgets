/*
 * Copyright (c) 2016, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.impl.convert;

import org.jowidgets.unit.api.IUnitValue;
import org.jowidgets.unit.tools.UnitValue;
import org.jowidgets.unit.tools.converter.DoubleUnitConverter;
import org.jowidgets.unit.tools.converter.LongDoubleUnitConverter;
import org.jowidgets.unit.tools.units.HertzUnitSet;
import org.junit.Assert;
import org.junit.Test;

public class UnitConverterTest {

    @Test
    public void testLongDoubleUnitConverter() {
        final LongDoubleUnitConverter converter = new LongDoubleUnitConverter(HertzUnitSet.GH);
        final UnitValue<Double> unitValue = new UnitValue<Double>(1.005d, HertzUnitSet.GH);

        final Long baseValueOfUnitValue = converter.toBaseValue(unitValue);
        Assert.assertEquals(1005000000, baseValueOfUnitValue.longValue());

        final IUnitValue<Double> unitValueConvertedBack = converter.toUnitValue(baseValueOfUnitValue);
        Assert.assertEquals(unitValue, unitValueConvertedBack);
    }

    @Test
    public void testDoubleUnitConverter() {
        final DoubleUnitConverter converter = new DoubleUnitConverter(HertzUnitSet.GH);
        final UnitValue<Double> unitValue = new UnitValue<Double>(1.005d, HertzUnitSet.GH);
        final Double baseValueOfUnitValue = converter.toBaseValue(unitValue);
        final IUnitValue<Double> unitValueConvertedBack = converter.toUnitValue(baseValueOfUnitValue);
        Assert.assertEquals(unitValue, unitValueConvertedBack);
    }

}
