/*
 * Copyright (c) 2010, grossmann
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

package org.jowidgets.tools.powo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.api.convert.IObjectStringConverter;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IComboBox;
import org.jowidgets.api.widgets.blueprint.IComboBoxSelectionBluePrint;
import org.jowidgets.api.widgets.descriptor.IComboBoxSelectionDescriptor;
import org.jowidgets.util.IObservableValue;

public class JoComboBoxSelection<VALUE_TYPE> extends
        InputControl<IComboBox<VALUE_TYPE>, IComboBoxSelectionBluePrint<VALUE_TYPE>, VALUE_TYPE> implements IComboBox<VALUE_TYPE> {

    @SuppressWarnings("unchecked")
    public JoComboBoxSelection(final List<VALUE_TYPE> elements) {
        this((IComboBoxSelectionBluePrint<VALUE_TYPE>) bluePrint().setElements(elements));
    }

    @SuppressWarnings("unchecked")
    public JoComboBoxSelection(final VALUE_TYPE... elements) {
        this((IComboBoxSelectionBluePrint<VALUE_TYPE>) bluePrint().setElements(elements));
    }

    public JoComboBoxSelection(final IObjectStringConverter<VALUE_TYPE> converter, final List<VALUE_TYPE> elements) {
        this(bluePrint(converter).setElements(elements));
    }

    public JoComboBoxSelection(final IObjectStringConverter<VALUE_TYPE> converter, final VALUE_TYPE... elements) {
        this(bluePrint(converter).setElements(elements));
    }

    public JoComboBoxSelection(final IObjectStringConverter<VALUE_TYPE> converter) {
        this(bluePrint(converter));
    }

    @SuppressWarnings("unchecked")
    public JoComboBoxSelection(final IComboBoxSelectionDescriptor<VALUE_TYPE> descriptor) {
        super((IComboBoxSelectionBluePrint<VALUE_TYPE>) bluePrint().setSetup(descriptor));
    }

    @Override
    public IObservableValue<VALUE_TYPE> getObservableValue() {
        if (isInitialized()) {
            return getWidget().getObservableValue();
        }
        else {
            return getBluePrint().getObservableValue();
        }
    }

    @Override
    public List<VALUE_TYPE> getElements() {
        if (isInitialized()) {
            return getWidget().getElements();
        }
        else {
            return new LinkedList<VALUE_TYPE>(getBluePrint().getElements());
        }
    }

    @Override
    public void setElements(final Collection<? extends VALUE_TYPE> elements) {
        if (isInitialized()) {
            getWidget().setElements(elements);
        }
        else {
            getBluePrint().setElements(elements);
        }
    }

    @Override
    public void setElements(final VALUE_TYPE... elements) {
        if (isInitialized()) {
            getWidget().setElements(elements);
        }
        else {
            getBluePrint().setElements(elements);
        }
    }

    @Override
    public int getSelectedIndex() {
        if (isInitialized()) {
            return getWidget().getSelectedIndex();
        }
        else {
            throw new IllegalStateException("This function is not supported for an uninitialized powo. Feel free to implent it");
        }
    }

    @Override
    public void setSelectedIndex(final int index) {
        if (isInitialized()) {
            getWidget().setSelectedIndex(index);
        }
        else {
            throw new IllegalStateException("This function is not supported for an uninitialized powo. Feel free to implent it");
        }
    }

    @Override
    public void select() {
        if (isInitialized()) {
            getWidget().select();
        }
        else {
            throw new IllegalStateException("This function is not supported for an uninitialized powo. Feel free to implent it");
        }
    }

    @Override
    public void setPopupVisible(final boolean visible) {
        if (isInitialized()) {
            getWidget().setPopupVisible(visible);
        }
        else {
            throw new IllegalStateException("This function is not supported for an uninitialized powo. Feel free to implent it");
        }
    }

    @Override
    public boolean isPopupVisible() {
        if (isInitialized()) {
            return getWidget().isPopupVisible();
        }
        else {
            throw new IllegalStateException("This function is not supported for an uninitialized powo. Feel free to implent it");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //some static blue print creation for convenience purpose from here
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static <VALUE_TYPE> IComboBoxSelectionBluePrint<VALUE_TYPE> bluePrint() {
        final IObjectStringConverter<VALUE_TYPE> converter = Toolkit.getConverterProvider().toStringConverter();
        return Toolkit.getBluePrintFactory().comboBoxSelection(converter);
    }

    public static <VALUE_TYPE> IComboBoxSelectionBluePrint<VALUE_TYPE> bluePrint(
        final IObjectStringConverter<VALUE_TYPE> converter) {
        final IComboBoxSelectionBluePrint<VALUE_TYPE> result = Toolkit.getBluePrintFactory().comboBoxSelection(converter);
        return result;
    }

    public static <ENUM_TYPE extends Enum<?>> IComboBoxSelectionBluePrint<ENUM_TYPE> bluePrint(final ENUM_TYPE... enumValues) {
        return Toolkit.getBluePrintFactory().comboBoxSelection(enumValues);
    }

    public static IComboBoxSelectionBluePrint<String> bluePrintString() {
        return Toolkit.getBluePrintFactory().comboBoxSelectionString();
    }

    public static IComboBoxSelectionBluePrint<Integer> bluePrintInteger() {
        return Toolkit.getBluePrintFactory().comboBoxSelectionIntegerNumber();
    }

    public static IComboBoxSelectionBluePrint<Long> bluePrintLong() {
        return Toolkit.getBluePrintFactory().comboBoxSelectionLongNumber();
    }

    public static IComboBoxSelectionBluePrint<Short> bluePrintShort() {
        return Toolkit.getBluePrintFactory().comboBoxSelectionShortNumber();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //some static comboBox creation for convenience purpose from here
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static JoComboBoxSelection<String> comboBoxString() {
        return new JoComboBoxSelection<String>(bluePrintString());
    }

    public static JoComboBoxSelection<String> comboBoxString(final String... elements) {
        return new JoComboBoxSelection<String>(bluePrintString().setElements(elements));
    }

    public static JoComboBoxSelection<String> comboBoxString(final List<String> elements) {
        return new JoComboBoxSelection<String>(bluePrintString().setElements(elements));
    }

    public static JoComboBoxSelection<Integer> comboBoxInteger() {
        return new JoComboBoxSelection<Integer>(bluePrintInteger());
    }

    public static JoComboBoxSelection<Integer> comboBoxInteger(final Integer... elements) {
        return new JoComboBoxSelection<Integer>(bluePrintInteger().setElements(elements));
    }

    public static JoComboBoxSelection<Integer> comboBoxInteger(final List<Integer> elements) {
        return new JoComboBoxSelection<Integer>(bluePrintInteger().setElements(elements));
    }

    public static JoComboBoxSelection<Long> comboBoxLong() {
        return new JoComboBoxSelection<Long>(bluePrintLong());
    }

    public static JoComboBoxSelection<Long> comboBoxLong(final Long... elements) {
        return new JoComboBoxSelection<Long>(bluePrintLong().setElements(elements));
    }

    public static JoComboBoxSelection<Long> comboBoxLong(final List<Long> elements) {
        return new JoComboBoxSelection<Long>(bluePrintLong().setElements(elements));
    }

    public static JoComboBoxSelection<Short> comboBoxShort() {
        return new JoComboBoxSelection<Short>(bluePrintShort());
    }

    public static JoComboBoxSelection<Short> comboBoxShort(final Short... elements) {
        return new JoComboBoxSelection<Short>(bluePrintShort().setElements(elements));
    }

    public static JoComboBoxSelection<Short> comboBoxShort(final List<Short> elements) {
        return new JoComboBoxSelection<Short>(bluePrintShort().setElements(elements));
    }

}
