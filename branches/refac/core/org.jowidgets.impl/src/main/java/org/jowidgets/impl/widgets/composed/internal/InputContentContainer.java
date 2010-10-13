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
package org.jowidgets.impl.widgets.composed.internal;

import org.jowidgets.api.color.IColorConstant;
import org.jowidgets.api.look.Border;
import org.jowidgets.api.validation.ValidationResult;
import org.jowidgets.api.widgets.ICompositeWidget;
import org.jowidgets.api.widgets.IContainerWidgetCommon;
import org.jowidgets.api.widgets.IInputWidget;
import org.jowidgets.api.widgets.IWidget;
import org.jowidgets.api.widgets.blueprint.ICompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.IScrollCompositeBluePrint;
import org.jowidgets.api.widgets.content.IInputContentContainer;
import org.jowidgets.api.widgets.content.IInputContentCreator;
import org.jowidgets.api.widgets.content.IMandatoryInputContainer;
import org.jowidgets.api.widgets.factory.ICustomWidgetFactory;
import org.jowidgets.api.widgets.layout.ILayoutDescriptor;
import org.jowidgets.api.widgets.setup.IWidgetSetupCommon;
import org.jowidgets.impl.widgets.composed.AbstractInputWidget;
import org.jowidgets.impl.widgets.composed.blueprint.BluePrintFactory;

public class InputContentContainer<INPUT_TYPE> extends AbstractInputWidget<INPUT_TYPE> implements
		IInputContentContainer,
		IMandatoryInputContainer {

	private final ICompositeWidget compositeWidget;
	private final IInputContentCreator<INPUT_TYPE> content;

	public InputContentContainer(
		final IContainerWidgetCommon parent,
		final IInputContentCreator<INPUT_TYPE> content,
		final boolean scrollableContent,
		final Border border) {
		super(content.isMandatory());

		final BluePrintFactory bpF = new BluePrintFactory();

		if (scrollableContent) {
			final IScrollCompositeBluePrint scrollCompositeBluePrint = bpF.scrollComposite();
			scrollCompositeBluePrint.setBorder(border);
			compositeWidget = parent.add(scrollCompositeBluePrint, "growx, growy, h 0::,w 0::, wrap");
		}
		else {
			final ICompositeBluePrint compositeBluePrint = bpF.composite();
			compositeBluePrint.setBorder(border);
			compositeWidget = parent.add(compositeBluePrint, "growx, growy, wrap");
		}
		this.content = content;
		content.createContent(this);
		// do not add initialization after this, because this is given to the
		// create content method
	}

	@Override
	public Object getUiReference() {
		return compositeWidget.getUiReference();
	}

	@Override
	public IWidget getParent() {
		return compositeWidget.getParent();
	}

	@Override
	public void redraw() {
		compositeWidget.redraw();
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		compositeWidget.setForegroundColor(colorValue);
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		compositeWidget.setBackgroundColor(colorValue);
	}

	@Override
	public void setLayout(final ILayoutDescriptor layoutDescriptor) {
		compositeWidget.setLayout(layoutDescriptor);
	}

	@Override
	public void layoutBegin() {
		compositeWidget.layoutBegin();
	}

	@Override
	public void layoutEnd() {
		compositeWidget.layoutEnd();
	}

	@Override
	public void removeAll() {
		compositeWidget.removeAll();
	}

	@Override
	public void setValue(final INPUT_TYPE value) {
		content.setValue(value);
	}

	@Override
	public INPUT_TYPE getValue() {
		return content.getValue();
	}

	@Override
	public void registerInputWidget(final String label, final IInputWidget<?> inputWidget) {
		registerSubInputWidget(label, inputWidget);
	}

	@Override
	public void unRegisterInputWidget(final IInputWidget<?> inputWidget) {
		unRegisterSubInputWidget(inputWidget);
	}

	@Override
	protected ValidationResult additionalValidation() {
		return content.validate();
	}

	@Override
	public void setEditable(final boolean editable) {
		super.setEditable(editable);
	}

	@Override
	public boolean hasInput() {
		for (final IInputWidget<?> subWidget : getSubWidgets()) {
			if (subWidget.hasInput()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAllMandatoryInput() {
		for (final IInputWidget<?> subWidget : getSubWidgets()) {
			if (subWidget.isMandatory() && !subWidget.hasInput()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void addSubContent(final IInputContentCreator<?> subContentCreator, final Object layoutConstraints) {
		final ICompositeBluePrint compositeBp = new BluePrintFactory().composite();
		final ICompositeWidget innerComposite = add(compositeBp, layoutConstraints);
		final InnerCompositeContentContainer innerContainer = new InnerCompositeContentContainer(this, innerComposite);
		subContentCreator.createContent(innerContainer);
	}

	@Override
	public <WIDGET_TYPE extends IWidget> WIDGET_TYPE add(
		final IWidgetSetupCommon<? extends WIDGET_TYPE> descriptor,
		final Object layoutConstraints) {
		return compositeWidget.add(descriptor, layoutConstraints);
	}

	@Override
	public <WIDGET_TYPE extends IWidget> WIDGET_TYPE add(
		final ICustomWidgetFactory<WIDGET_TYPE> factory,
		final Object layoutConstraints) {
		return compositeWidget.add(factory, layoutConstraints);
	}

}
