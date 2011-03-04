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
package org.jowidgets.impl.swt.widgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.jowidgets.common.color.ColorValue;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.IWidgetCommon;
import org.jowidgets.impl.swt.color.IColorCache;
import org.jowidgets.impl.swt.cursor.CursorCache;
import org.jowidgets.impl.swt.util.DimensionConvert;

public class SwtWidget implements IWidgetCommon {

	private final IColorCache colorCache;
	private final Control control;

	public SwtWidget(final IColorCache colorCache, final Control control) {
		super();
		this.colorCache = colorCache;
		this.control = control;
	}

	@Override
	public Control getUiReference() {
		return control;
	}

	@Override
	public void redraw() {
		if (control.getParent() != null) {
			control.getParent().layout(new Control[] {control});
			control.getParent().redraw();
		}
		else {
			control.redraw();
		}
	}

	@Override
	public void setCursor(final Cursor cursor) {
		control.setCursor(CursorCache.getCursor(cursor));
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		control.setForeground(colorCache.getColor(colorValue));
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		control.setBackground(colorCache.getColor(colorValue));
	}

	@Override
	public IColorConstant getForegroundColor() {
		return toColorConstant(control.getForeground());
	}

	@Override
	public IColorConstant getBackgroundColor() {
		return toColorConstant(control.getBackground());
	}

	private IColorConstant toColorConstant(final Color color) {
		return new ColorValue(color.getRed(), color.getGreen(), color.getBlue());
	}

	@Override
	public void setEnabled(final boolean enabled) {
		control.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled() {
		return control.isEnabled();
	}

	@Override
	public void setVisible(final boolean visible) {
		control.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return control.isVisible();
	}

	@Override
	public Dimension getSize() {
		return DimensionConvert.convert(control.getSize());
	}

}
