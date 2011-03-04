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
package org.jowidgets.impl.swt.widgets.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jowidgets.common.types.Markup;
import org.jowidgets.impl.swt.util.AlignmentConvert;
import org.jowidgets.impl.swt.util.FontProvider;
import org.jowidgets.impl.swt.widgets.SwtControl;
import org.jowidgets.spi.widgets.ITextLabelSpi;
import org.jowidgets.spi.widgets.setup.ITextLabelSetupSpi;

public class TextLabelImpl extends SwtControl implements ITextLabelSpi {

	public TextLabelImpl(final Object parentUiReference, final ITextLabelSetupSpi setup) {

		super(new Label((Composite) parentUiReference, SWT.BOLD));

		setText(setup.getText());
		setToolTipText(setup.getToolTipText());

		setMarkup(setup.getMarkup());

		getUiReference().setAlignment(AlignmentConvert.convert(setup.getAlignment()));
	}

	@Override
	public Label getUiReference() {
		return (Label) super.getUiReference();
	}

	@Override
	public void setText(String text) {
		final String oldText = getUiReference().getText();
		if (text == null) {
			text = "";
		}
		if (!text.equals(oldText)) {
			getUiReference().setText(text);
		}
	}

	@Override
	public void setToolTipText(final String text) {
		getUiReference().setToolTipText(text);
	}

	@Override
	public void setMarkup(final Markup markup) {
		final Label label = this.getUiReference();
		final Font newFont = FontProvider.deriveFont(label.getFont(), markup);
		label.setFont(newFont);
	}

}
