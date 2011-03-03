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
package org.jowidgets.impl.swing.widgets.internal;

import java.awt.Component;

import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.IInputWidgetCommon;
import org.jowidgets.common.widgets.controler.impl.InputObservable;
import org.jowidgets.impl.swing.widgets.SwingWidget;

public abstract class AbstractSwingInputWidget extends InputObservable implements IInputWidgetCommon {

	private final Component component;
	private final SwingWidget swingWidgetDelegate;

	public AbstractSwingInputWidget(final Component component) {
		super();
		this.component = component;
		this.swingWidgetDelegate = new SwingWidget(component);
	}

	@Override
	public Component getUiReference() {
		return component;
	}

	@Override
	public void redraw() {
		swingWidgetDelegate.redraw();
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		swingWidgetDelegate.setForegroundColor(colorValue);
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		swingWidgetDelegate.setBackgroundColor(colorValue);
	}

	@Override
	public IColorConstant getForegroundColor() {
		return swingWidgetDelegate.getForegroundColor();
	}

	@Override
	public IColorConstant getBackgroundColor() {
		return swingWidgetDelegate.getBackgroundColor();
	}

	@Override
	public void setCursor(final Cursor cursor) {
		swingWidgetDelegate.setCursor(cursor);
	}

	@Override
	public void setVisible(final boolean visible) {
		swingWidgetDelegate.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return swingWidgetDelegate.isVisible();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		swingWidgetDelegate.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled() {
		return swingWidgetDelegate.isEnabled();
	}

	@Override
	public Dimension getSize() {
		return swingWidgetDelegate.getSize();
	}

}
