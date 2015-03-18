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

package org.jowidgets.impl.widgets.basic;

import org.jowidgets.api.controller.IDisposeListener;
import org.jowidgets.api.controller.IParentListener;
import org.jowidgets.api.widgets.ICheckBox;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.descriptor.setup.ICheckBoxSetup;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.widgets.controller.IInputListener;
import org.jowidgets.impl.base.delegate.ControlDelegate;
import org.jowidgets.impl.widgets.common.wrapper.AbstractControlSpiWrapper;
import org.jowidgets.spi.widgets.ICheckBoxSpi;
import org.jowidgets.tools.controller.InputObservable;
import org.jowidgets.tools.validation.ValidationCache;
import org.jowidgets.tools.validation.ValidationCache.IValidationResultCreator;
import org.jowidgets.tools.value.InputComponentBind;
import org.jowidgets.tools.widgets.invoker.ColorSettingsInvoker;
import org.jowidgets.tools.widgets.invoker.VisibiliySettingsInvoker;
import org.jowidgets.util.IObservableValue;
import org.jowidgets.util.NullCompatibleEquivalence;
import org.jowidgets.util.ObservableValue;
import org.jowidgets.validation.IValidationConditionListener;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.IValidator;
import org.jowidgets.validation.tools.CompoundValidator;

public class CheckBoxImpl extends AbstractControlSpiWrapper implements ICheckBox {

    private final ICheckBoxSpi checkBoxWidgetSpi;
    private final InputObservable inputObservable;
    private final ValidationCache validationCache;
    private final ControlDelegate controlDelegate;
    private final CompoundValidator<Boolean> compoundValidator;
    private final IObservableValue<Boolean> observableValue;

    private String text;
    private boolean isNull;
    private Boolean lastUnmodifiedValue;
    private boolean editable;

    public CheckBoxImpl(final ICheckBoxSpi checkBoxWidgetSpi, final ICheckBoxSetup setup) {
        super(checkBoxWidgetSpi);
        this.checkBoxWidgetSpi = checkBoxWidgetSpi;
        this.inputObservable = new InputObservable();
        this.controlDelegate = new ControlDelegate(checkBoxWidgetSpi, this);
        this.compoundValidator = new CompoundValidator<Boolean>();

        final IValidator<Boolean> validator = setup.getValidator();
        if (validator != null) {
            compoundValidator.addValidator(validator);
        }

        VisibiliySettingsInvoker.setVisibility(setup, this);
        ColorSettingsInvoker.setColors(setup, this);

        this.observableValue = setup.getObservableValue() != null ? setup.getObservableValue() : new ObservableValue<Boolean>();

        if (setup.getValue() != null && observableValue.getValue() == null) {
            setValue(setup.getValue());
        }

        InputComponentBind.bind(observableValue, this);

        if (setup.getFontSize() != null) {
            setFontSize(Integer.valueOf(setup.getFontSize()));
        }
        if (setup.getFontName() != null) {
            setFontName(setup.getFontName());
        }

        this.validationCache = new ValidationCache(new IValidationResultCreator() {
            @Override
            public IValidationResult createValidationResult() {
                return compoundValidator.validate(getValue());
            }
        });

        getWidget().addInputListener(new IInputListener() {
            @Override
            public void inputChanged() {
                isNull = false;
                inputObservable.fireInputChanged();
                validationCache.setDirty();
            }
        });

        this.editable = setup.isEditable();
        if (!setup.isEditable()) {
            setEditable(false);
        }

        resetModificationState();
    }

    @Override
    public ICheckBoxSpi getWidget() {
        return (ICheckBoxSpi) super.getWidget();
    }

    @Override
    public IObservableValue<Boolean> getObservableValue() {
        return observableValue;
    }

    @Override
    public boolean hasModifications() {
        return !NullCompatibleEquivalence.equals(lastUnmodifiedValue, getValue());
    }

    @Override
    public void resetModificationState() {
        lastUnmodifiedValue = getValue();
    }

    @Override
    public boolean isSelected() {
        return checkBoxWidgetSpi.isSelected();
    }

    @Override
    public void setSelected(final boolean selected) {
        checkBoxWidgetSpi.setSelected(selected);
    }

    @Override
    public void setValue(final Boolean value) {
        if (value == null) {
            isNull = true;
            setSelected(false);
        }
        else if (!value.booleanValue() && isNull) {
            isNull = false;
            inputObservable.fireInputChanged();
        }
        else {
            isNull = false;
            setSelected(value.booleanValue());
        }
    }

    @Override
    public Boolean getValue() {
        if (isNull) {
            return null;
        }
        else {
            return Boolean.valueOf(isSelected());
        }
    }

    @Override
    public IValidationResult validate() {
        return validationCache.validate();
    }

    @Override
    public void addValidationConditionListener(final IValidationConditionListener listener) {
        validationCache.addValidationConditionListener(listener);
    }

    @Override
    public void removeValidationConditionListener(final IValidationConditionListener listener) {
        validationCache.removeValidationConditionListener(listener);
    }

    @Override
    public void setParent(final IContainer parent) {
        controlDelegate.setParent(parent);
    }

    @Override
    public IContainer getParent() {
        return controlDelegate.getParent();
    }

    @Override
    public void addParentListener(final IParentListener<IContainer> listener) {
        controlDelegate.addParentListener(listener);
    }

    @Override
    public void removeParentListener(final IParentListener<IContainer> listener) {
        controlDelegate.removeParentListener(listener);
    }

    @Override
    public boolean isReparentable() {
        return controlDelegate.isReparentable();
    }

    @Override
    public void addDisposeListener(final IDisposeListener listener) {
        controlDelegate.addDisposeListener(listener);
    }

    @Override
    public void removeDisposeListener(final IDisposeListener listener) {
        controlDelegate.removeDisposeListener(listener);
    }

    @Override
    public boolean isDisposed() {
        return controlDelegate.isDisposed();
    }

    @Override
    public void dispose() {
        controlDelegate.dispose();
    }

    @Override
    public IPopupMenu createPopupMenu() {
        return controlDelegate.createPopupMenu();
    }

    @Override
    public void setEditable(final boolean editable) {
        this.editable = editable;
        getWidget().setEditable(editable);
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void addValidator(final IValidator<Boolean> validator) {
        compoundValidator.addValidator(validator);
    }

    @Override
    public void addInputListener(final IInputListener listener) {
        inputObservable.addInputListener(listener);
    }

    @Override
    public void removeInputListener(final IInputListener listener) {
        inputObservable.removeInputListener(listener);
    }

    @Override
    public void setFontSize(final int size) {
        getWidget().setFontSize(size);
    }

    @Override
    public void setFontName(final String fontName) {
        getWidget().setFontName(fontName);
    }

    @Override
    public void setMarkup(final Markup markup) {
        getWidget().setMarkup(markup);
    }

    @Override
    public void setText(final String text) {
        this.text = text;
        getWidget().setText(text);
    }

    @Override
    public String getText() {
        return text;
    }

}
