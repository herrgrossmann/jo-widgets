/*
 * Copyright (c) 2014, Michael Grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.api.widgets.blueprint.builder;

import java.util.Map;

import org.jowidgets.api.widgets.descriptor.IInputFieldDescriptor;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.types.VirtualKey;
import org.jowidgets.unit.api.IUnit;
import org.jowidgets.unit.api.IUnitConverter;
import org.jowidgets.unit.api.IUnitSet;

public interface IUnitValueFieldSetupBuilder<INSTANCE_TYPE extends IUnitValueFieldSetupBuilder<?, ?, ?>, BASE_VALUE_TYPE, UNIT_VALUE_TYPE> extends
        IInputComponentSetupBuilder<INSTANCE_TYPE, BASE_VALUE_TYPE> {

    INSTANCE_TYPE setUnitSet(IUnitSet unitSet);

    INSTANCE_TYPE setDefaultUnit(IUnit unit);

    INSTANCE_TYPE setUnitKeyMapping(Map<VirtualKey, IUnit> unitKeyMapping);

    INSTANCE_TYPE setUnitComboMinSize(int unitComboMinSize);

    INSTANCE_TYPE setUnitConverter(IUnitConverter<BASE_VALUE_TYPE, UNIT_VALUE_TYPE> converter);

    INSTANCE_TYPE setUnitValueInputField(IInputFieldDescriptor<UNIT_VALUE_TYPE> inputField);

    INSTANCE_TYPE setMarkup(Markup markup);

    INSTANCE_TYPE setFontSize(Integer size);

    INSTANCE_TYPE setFontName(String fontName);

}
