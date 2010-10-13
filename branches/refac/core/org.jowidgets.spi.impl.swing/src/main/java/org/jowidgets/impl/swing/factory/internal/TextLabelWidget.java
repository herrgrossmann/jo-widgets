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
package org.jowidgets.impl.swing.factory.internal;

import javax.swing.JLabel;

import org.jowidgets.api.look.Markup;
import org.jowidgets.api.util.ColorSettingsInvoker;
import org.jowidgets.api.widgets.IWidget;
import org.jowidgets.impl.swing.util.AlignmentConvert;
import org.jowidgets.impl.swing.util.FontProvider;
import org.jowidgets.impl.swing.widgets.SwingWidget;
import org.jowidgets.spi.widgets.ITextLabelWidgetSpi;
import org.jowidgets.spi.widgets.descriptor.setup.ITextLabelSetupSpi;

public class TextLabelWidget extends SwingWidget implements ITextLabelWidgetSpi {

	public TextLabelWidget(final IWidget parent, final ITextLabelSetupSpi<?> descriptor) {

		super(new JLabel());

		setText(descriptor.getText());
		setToolTipText(descriptor.getToolTipText());

		setMarkup(descriptor.getMarkup());

		getUiReference().setHorizontalAlignment(AlignmentConvert.convert(descriptor.getAlignment()));
		ColorSettingsInvoker.setColors(descriptor, this);
	}

	@Override
	public JLabel getUiReference() {
		return (JLabel) super.getUiReference();
	}

	@Override
	public void setText(final String text) {
		getUiReference().setText(text);
	}

	@Override
	public void setToolTipText(final String text) {
		getUiReference().setToolTipText(text);
	}

	@Override
	public void setMarkup(final Markup markup) {
		final JLabel label = getUiReference();
		label.setFont(FontProvider.deriveFont(label.getFont(), markup));
	}

}
