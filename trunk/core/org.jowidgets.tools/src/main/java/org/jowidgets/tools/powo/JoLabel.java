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

import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.ILabel;
import org.jowidgets.api.widgets.blueprint.ILabelBluePrint;
import org.jowidgets.api.widgets.descriptor.ILabelDescriptor;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Markup;

public class JoLabel extends Control<ILabel, ILabelBluePrint> implements ILabel {

	public JoLabel(final IImageConstant icon) {
		this(Toolkit.getBluePrintFactory().label().setIcon(icon));
	}

	public JoLabel(final IImageConstant icon, final String text) {
		this(Toolkit.getBluePrintFactory().label().setText(text).setIcon(icon));
	}

	public JoLabel(final IImageConstant icon, final String text, final String tooltipText) {
		this(Toolkit.getBluePrintFactory().label().setText(text).setIcon(icon).setToolTipText(tooltipText));
	}

	public JoLabel(final String text) {
		this(Toolkit.getBluePrintFactory().label().setText(text));
	}

	public JoLabel(final String text, final String toolTipText) {
		this(Toolkit.getBluePrintFactory().label().setText(text).setToolTipText(toolTipText));
	}

	public JoLabel(final ILabelDescriptor descriptor) {
		super(Toolkit.getBluePrintFactory().label().setSetup(descriptor));
	}

	@Override
	public void setMarkup(final Markup markup) {
		if (isInitialized()) {
			getWidget().setMarkup(markup);
		}
		else {
			getBluePrint().setMarkup(markup);
		}
	}

	@Override
	public void setText(final String text) {
		if (isInitialized()) {
			getWidget().setText(text);
		}
		else {
			getBluePrint().setText(text);
		}
	}

	@Override
	public String getText() {
		if (isInitialized()) {
			return getWidget().getText();
		}
		else {
			return getBluePrint().getText();
		}
	}

	@Override
	public void setToolTipText(final String text) {
		if (isInitialized()) {
			getWidget().setToolTipText(text);
		}
		else {
			getBluePrint().setToolTipText(text);
		}
	}

	@Override
	public String getToolTipText() {
		if (isInitialized()) {
			return getWidget().getToolTipText();
		}
		else {
			return getBluePrint().getToolTipText();
		}
	}

	@Override
	public void setIcon(final IImageConstant icon) {
		if (isInitialized()) {
			getWidget().setIcon(icon);
		}
		else {
			getBluePrint().setIcon(icon);
		}
	}

	public static ILabelBluePrint bluePrint() {
		return Toolkit.getBluePrintFactory().label();
	}

	public static ILabelBluePrint bluePrint(final String text) {
		return Toolkit.getBluePrintFactory().label().setText(text);
	}

	public static ILabelBluePrint bluePrint(final String text, final IImageConstant icon) {
		return Toolkit.getBluePrintFactory().label().setText(text).setIcon(icon);
	}

}
