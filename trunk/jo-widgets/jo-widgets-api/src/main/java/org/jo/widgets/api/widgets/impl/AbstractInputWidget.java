/*
 * Copyright (c) 2010, Michael Grossmann
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the jo-widgets.org nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
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
package org.jo.widgets.api.widgets.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jo.widgets.api.validation.IValidator;
import org.jo.widgets.api.validation.OkValidator;
import org.jo.widgets.api.validation.ValidationResult;
import org.jo.widgets.api.widgets.IInputWidget;
import org.jo.widgets.api.widgets.controler.IInputListener;
import org.jo.widgets.util.Assert;

public abstract class AbstractInputWidget<VALUE_TYPE> implements
		IInputWidget<VALUE_TYPE> {

	private final Map<IInputWidget<?>, String> contextMap;
	private final List<IInputWidget<?>> subWidgets;
	private final Set<IInputListener> inputListeners;
	private final IInputListener subWidgetListener;

	private IValidator<VALUE_TYPE> validator;

	public AbstractInputWidget() {
		this(null);
	}

	public AbstractInputWidget(IValidator<VALUE_TYPE> validator) {
		super();
		if (validator == null) {
			validator = new OkValidator<VALUE_TYPE>();
		}
		this.validator = validator;
		this.contextMap = new HashMap<IInputWidget<?>, String>();
		this.inputListeners = new HashSet<IInputListener>();
		this.subWidgets = new LinkedList<IInputWidget<?>>();

		this.subWidgetListener = new IInputListener() {

			@Override
			public void inputChanged(final Object source) {
				fireContentChanged(source);
			}

		};
	}

	@Override
	public final void addInputListener(final IInputListener listener) {
		inputListeners.add(listener);
	}

	@Override
	public final void removeInputListener(final IInputListener listener) {
		inputListeners.remove(listener);
	}

	@Override
	public final ValidationResult validate() {
		final ValidationResult result = new ValidationResult();
		for (final IInputWidget<?> subWidget : getSubWidgets()) {
			ValidationResult subResult = subWidget.validate();
			final String contextLabel = contextMap.get(subWidget);
			if (contextLabel != null) {
				subResult = subResult.copyAndSetContext(contextLabel);
			}
			result.addValidationResult(subResult);
		}
		result.addValidationResult(validator.validate(getValue()));
		result.addValidationResult(additionalValidation());

		return result;
	}

	@Override
	public void setEditable(final boolean editable) {
		for (final IInputWidget<?> inputWidget : getSubWidgets()) {
			inputWidget.setEditable(editable);
		}
	}

	protected final List<IInputWidget<?>> getSubWidgets() {
		return new LinkedList<IInputWidget<?>>(subWidgets);
	}

	/**
	 * Could be overridden to make additional validations
	 * 
	 * @return the result of the additional validation
	 */
	protected ValidationResult additionalValidation() {
		return new ValidationResult();
	}

	protected final void registerSubInputWidget(final IInputWidget<?> subWidget) {
		registerSubInputWidget(null, subWidget);
	}

	protected final void registerSubInputWidget(final String contextLabel,
			final IInputWidget<?> subWidget) {
		Assert.paramNotNull(subWidget, "subWidget");
		subWidgets.add(subWidget);
		if (contextLabel != null) {
			contextMap.put(subWidget, contextLabel);
		}
		subWidget.addInputListener(subWidgetListener);
	}

	protected final void unRegisterSubInputWidget(
			final IInputWidget<?> subWidget) {
		Assert.paramNotNull(subWidget, "subWidget");
		subWidgets.remove(subWidget);
		contextMap.remove(subWidget);
		subWidget.removeInputListener(subWidgetListener);
	}

	protected final void fireContentChanged(final Object source) {
		for (final IInputListener listener : inputListeners) {
			listener.inputChanged(source);
		}
	}

}
