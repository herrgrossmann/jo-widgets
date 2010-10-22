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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jowidgets.api.validation.IValidator;
import org.jowidgets.api.validation.OkValidator;
import org.jowidgets.api.validation.ValidationResult;
import org.jowidgets.api.widgets.IInputWidget;
import org.jowidgets.common.widgets.controler.IInputListener;
import org.jowidgets.common.widgets.controler.impl.InputObservable;
import org.jowidgets.util.Assert;

public abstract class AbstractInputWidget<VALUE_TYPE> extends InputObservable implements IInputWidget<VALUE_TYPE> {

	private boolean mandatory;
	private final Map<IInputWidget<?>, String> contextMap;
	private final List<IInputWidget<?>> subWidgets;
	private final List<IValidator<VALUE_TYPE>> validators;
	private final IInputListener subWidgetListener;

	private final IValidator<VALUE_TYPE> validator;

	public AbstractInputWidget(final boolean mandatory) {
		this(mandatory, null);
	}

	public AbstractInputWidget(final boolean mandatory, IValidator<VALUE_TYPE> validator) {
		super();
		if (validator == null) {
			validator = new OkValidator<VALUE_TYPE>();
		}

		this.mandatory = mandatory;
		this.validator = validator;
		this.contextMap = new HashMap<IInputWidget<?>, String>();
		this.subWidgets = new LinkedList<IInputWidget<?>>();
		this.validators = new LinkedList<IValidator<VALUE_TYPE>>();

		this.subWidgetListener = new IInputListener() {

			@Override
			public void inputChanged(final Object source) {
				fireInputChanged(source);
			}

		};
	}

	@Override
	public final ValidationResult validate() {
		final VALUE_TYPE value = getValue();

		final ValidationResult result = new ValidationResult();
		for (final IInputWidget<?> subWidget : getSubWidgets()) {
			ValidationResult subResult = subWidget.validate();
			final String contextLabel = contextMap.get(subWidget);
			if (contextLabel != null) {
				subResult = subResult.copyAndSetContext(contextLabel);
			}
			result.addValidationResult(subResult);
		}
		result.addValidationResult(validator.validate(value));

		for (final IValidator<VALUE_TYPE> additionalValidator : validators) {
			result.addValidationResult(additionalValidator.validate(value));
		}

		result.addValidationResult(additionalValidation());

		return result;
	}

	@Override
	public void setEditable(final boolean editable) {
		for (final IInputWidget<?> inputWidget : getSubWidgets()) {
			inputWidget.setEditable(editable);
		}
	}

	/**
	 * Could be overridden to make additional validations
	 * 
	 * @return the result of the additional validation
	 */
	protected ValidationResult additionalValidation() {
		return new ValidationResult();
	}

	protected final List<IInputWidget<?>> getSubWidgets() {
		return new LinkedList<IInputWidget<?>>(subWidgets);
	}

	protected final void registerSubInputWidget(final IInputWidget<?> subWidget) {
		registerSubInputWidget(null, subWidget);
	}

	protected final void registerSubInputWidget(final String contextLabel, final IInputWidget<?> subWidget) {
		Assert.paramNotNull(subWidget, "subWidget");
		subWidgets.add(subWidget);
		if (contextLabel != null) {
			contextMap.put(subWidget, contextLabel);
		}
		subWidget.addInputListener(subWidgetListener);
	}

	protected final void unRegisterSubInputWidget(final IInputWidget<?> subWidget) {
		Assert.paramNotNull(subWidget, "subWidget");
		subWidgets.remove(subWidget);
		contextMap.remove(subWidget);
		subWidget.removeInputListener(subWidgetListener);
	}

	@Override
	public final boolean isMandatory() {
		return mandatory;
	}

	@Override
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public final void addValidator(final IValidator<VALUE_TYPE> validator) {
		Assert.paramNotNull(validator, "validator");
		this.validators.add(validator);
	}

}
