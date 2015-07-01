/*
 * Copyright (c) 2010, Michael Grossmann
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
package org.jowidgets.impl.widgets.composed;

import org.jowidgets.api.widgets.IInputComponent;
import org.jowidgets.api.widgets.IInputComponentValidationLabel;
import org.jowidgets.api.widgets.IValidationResultLabel;
import org.jowidgets.api.widgets.descriptor.setup.IInputComponentValidationLabelSetup;
import org.jowidgets.tools.widgets.invoker.ColorSettingsInvoker;
import org.jowidgets.tools.widgets.invoker.VisibiliySettingsInvoker;
import org.jowidgets.tools.widgets.wrapper.ControlWrapper;
import org.jowidgets.util.Assert;
import org.jowidgets.util.IDecorator;
import org.jowidgets.validation.IValidationConditionListener;
import org.jowidgets.validation.IValidationResult;

public class InputComponentValidationStateLabelImpl extends ControlWrapper implements IInputComponentValidationLabel {

    private final IValidationResultLabel resultLabel;
    private final IInputComponent<?> inputComponent;
    private final IDecorator<IValidationResult> initialDecorator;
    private final IDecorator<IValidationResult> unmodifiedDecorator;

    public InputComponentValidationStateLabelImpl(
        final IValidationResultLabel resultLabel,
        final IInputComponentValidationLabelSetup setup) {
        super(resultLabel);
        Assert.paramNotNull(setup.getInputComponent(), "setup.getInputComponent()");

        this.initialDecorator = setup.getInitialValidationDecorator();
        this.unmodifiedDecorator = setup.getUnmodifiedValidationDecorator();

        this.resultLabel = resultLabel;

        ColorSettingsInvoker.setColors(setup, this);
        VisibiliySettingsInvoker.setVisibility(setup, this);

        this.inputComponent = setup.getInputComponent();

        inputComponent.addValidationConditionListener(new IValidationConditionListener() {
            @Override
            public void validationConditionsChanged() {
                IValidationResult validationResult = inputComponent.validate();
                if (!inputComponent.hasModifications() && unmodifiedDecorator != null) {
                    validationResult = unmodifiedDecorator.decorate(validationResult);
                }
                if (validationResult != null) {
                    resultLabel.setResult(validationResult);
                }
                else {
                    resultLabel.setEmpty();
                }
            }
        });

        resetValidation();
    }

    @Override
    public void resetValidation() {
        initialValidation();
    }

    private void initialValidation() {
        IValidationResult validationResult = inputComponent.validate();
        if (initialDecorator != null) {
            validationResult = initialDecorator.decorate(validationResult);
        }
        if (validationResult != null) {
            resultLabel.setResult(validationResult);
        }
        else {
            resultLabel.setEmpty();
        }
    }

}
