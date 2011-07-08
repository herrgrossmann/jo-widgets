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

import org.jowidgets.api.widgets.ICheckBox;
import org.jowidgets.api.widgets.descriptor.setup.ICheckBoxSetup;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.widgets.controler.IInputListener;
import org.jowidgets.impl.widgets.basic.factory.internal.util.ColorSettingsInvoker;
import org.jowidgets.impl.widgets.basic.factory.internal.util.VisibiliySettingsInvoker;
import org.jowidgets.impl.widgets.common.wrapper.TextLabelSpiWrapper;
import org.jowidgets.spi.widgets.ICheckBoxSpi;
import org.jowidgets.tools.controler.InputObservable;

public class CheckBoxImpl extends AbstractBasicInputControl<Boolean> implements ICheckBox {

	private final ICheckBoxSpi checkBoxWidgetSpi;
	private final TextLabelSpiWrapper textLabelWidgetCommonWrapper;
	private final InputObservable inputObservable;

	private boolean isNull;

	public CheckBoxImpl(final ICheckBoxSpi checkBoxWidgetSpi, final ICheckBoxSetup setup) {
		super(checkBoxWidgetSpi, setup);
		this.checkBoxWidgetSpi = checkBoxWidgetSpi;
		this.textLabelWidgetCommonWrapper = new TextLabelSpiWrapper(checkBoxWidgetSpi);
		this.inputObservable = new InputObservable();

		VisibiliySettingsInvoker.setVisibility(setup, this);
		ColorSettingsInvoker.setColors(setup, this);
		if (setup.getValue() != null) {
			setValue(setup.getValue());
		}

		if (setup.getFontSize() != null) {
			setFontSize(Integer.valueOf(setup.getFontSize()));
		}
		if (setup.getFontName() != null) {
			setFontName(setup.getFontName());
		}

		getWidget().addInputListener(new IInputListener() {
			@Override
			public void inputChanged() {
				isNull = false;
			}
		});
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
	public void addInputListener(final IInputListener listener) {
		super.addInputListener(listener);
		inputObservable.addInputListener(listener);
	}

	@Override
	public void removeInputListener(final IInputListener listener) {
		super.removeInputListener(listener);
		inputObservable.removeInputListener(listener);
	}

	@Override
	public void setFontSize(final int size) {
		textLabelWidgetCommonWrapper.setFontSize(size);
	}

	@Override
	public void setFontName(final String fontName) {
		textLabelWidgetCommonWrapper.setFontName(fontName);
	}

	@Override
	public void setMarkup(final Markup markup) {
		textLabelWidgetCommonWrapper.setMarkup(markup);
	}

	@Override
	public void setText(final String text) {
		textLabelWidgetCommonWrapper.setText(text);
	}

	@Override
	public String getText() {
		return textLabelWidgetCommonWrapper.getText();
	}

	@Override
	public void setToolTipText(final String text) {
		textLabelWidgetCommonWrapper.setToolTipText(text);
	}

	@Override
	public String getToolTipText() {
		return textLabelWidgetCommonWrapper.getToolTipText();
	}

}
