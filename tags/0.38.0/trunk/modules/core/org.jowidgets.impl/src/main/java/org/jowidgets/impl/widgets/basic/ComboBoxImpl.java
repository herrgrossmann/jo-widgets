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

import org.jowidgets.api.convert.IObjectStringConverter;
import org.jowidgets.api.convert.IStringObjectConverter;
import org.jowidgets.api.widgets.IComboBox;
import org.jowidgets.api.widgets.descriptor.setup.IComboBoxSetup;
import org.jowidgets.common.widgets.controller.IInputListener;
import org.jowidgets.spi.widgets.IComboBoxSpi;
import org.jowidgets.tools.validation.ValidationCache;
import org.jowidgets.tools.validation.ValidationCache.IValidationResultCreator;
import org.jowidgets.tools.widgets.invoker.ColorSettingsInvoker;
import org.jowidgets.tools.widgets.invoker.VisibiliySettingsInvoker;
import org.jowidgets.util.NullCompatibleEquivalence;
import org.jowidgets.validation.IValidationConditionListener;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.IValidator;
import org.jowidgets.validation.ValidationResult;
import org.jowidgets.validation.tools.CompoundValidator;

public class ComboBoxImpl<VALUE_TYPE> extends ComboBoxSelectionImpl<VALUE_TYPE> implements IComboBox<VALUE_TYPE> {

	private final IStringObjectConverter<VALUE_TYPE> stringObjectConverter;
	private final IObjectStringConverter<VALUE_TYPE> objectStringConverter;
	private final ValidationCache validationCache;
	private final CompoundValidator<VALUE_TYPE> compoundValidator;

	private String lastUnmodifiedValue;

	public ComboBoxImpl(final IComboBoxSpi comboBoxWidgetSpi, final IComboBoxSetup<VALUE_TYPE> setup) {
		super(comboBoxWidgetSpi, setup);

		VisibiliySettingsInvoker.setVisibility(setup, this);
		ColorSettingsInvoker.setColors(setup, this);

		setElements(setup.getElements());

		if (setup.getValue() != null) {
			setValue(setup.getValue());
		}

		this.stringObjectConverter = setup.getStringObjectConverter();
		this.objectStringConverter = setup.getObjectStringConverter();

		this.compoundValidator = new CompoundValidator<VALUE_TYPE>();

		final IValidator<VALUE_TYPE> validator = setup.getValidator();
		if (validator != null) {
			compoundValidator.addValidator(validator);
		}

		final IValidator<String> stringValidator = stringObjectConverter.getStringValidator();
		this.validationCache = new ValidationCache(new IValidationResultCreator() {
			@Override
			public IValidationResult createValidationResult() {
				final IValidationResult result;
				if (stringValidator != null) {
					result = stringValidator.validate(comboBoxWidgetSpi.getText());
				}
				else {
					result = ValidationResult.ok();
				}
				//if the converter could not parse the input, do not make more validation
				if (!result.isValid()) {
					return result;
				}
				else {
					return result.withResult(compoundValidator.validate(getValue()));
				}
			}
		});

		getWidget().addInputListener(new IInputListener() {
			@Override
			public void inputChanged() {
				getWidget().setToolTipText(objectStringConverter.getDescription(getValue()));
				fireInputChanged();
				validationCache.setDirty();
			}
		});

	}

	@Override
	public IComboBoxSpi getWidget() {
		return (IComboBoxSpi) super.getWidget();
	}

	@Override
	public boolean hasModifications() {
		return !NullCompatibleEquivalence.equals(lastUnmodifiedValue, getWidget().getText());
	}

	@Override
	public void resetModificationState() {
		this.lastUnmodifiedValue = getWidget().getText();
	}

	@Override
	public void addValidator(final IValidator<VALUE_TYPE> validator) {
		compoundValidator.addValidator(validator);
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
	public void setValue(final VALUE_TYPE value) {
		if (value == null) {
			getWidget().setText("");
			getWidget().setSelectedIndex(-1);
		}
		else {
			final int indexOfContent = getElements().indexOf(value);
			if (indexOfContent != -1) {
				getWidget().setSelectedIndex(indexOfContent);
			}
			else {
				getWidget().setText(convertToString(value));
			}
		}
	}

	private String convertToString(final VALUE_TYPE value) {
		final String result = objectStringConverter.convertToString(value);
		if (result != null) {
			return result;
		}
		else {
			return "";
		}
	}

	@Override
	public VALUE_TYPE getValue() {
		if (stringObjectConverter != null) {
			return stringObjectConverter.convertToObject(getWidget().getText());
		}
		return null;
	}

	@Override
	public void setEditable(final boolean editable) {
		getWidget().setEditable(editable);
	}

}
