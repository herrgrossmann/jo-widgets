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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Markup;
import org.jowidgets.impl.swt.color.IColorCache;
import org.jowidgets.impl.swt.image.SwtImageRegistry;
import org.jowidgets.impl.swt.util.AlignmentConvert;
import org.jowidgets.impl.swt.util.FontProvider;
import org.jowidgets.spi.widgets.IButtonSpi;
import org.jowidgets.spi.widgets.setup.IButtonSetupSpi;

public class ButtonWidget extends AbstractSwtActionWidget implements IButtonSpi {

	private final SwtImageRegistry imageRegistry;

	public ButtonWidget(
		final IColorCache colorCache,
		final SwtImageRegistry imageRegistry,
		final Object parentUiReference,
		final IButtonSetupSpi setup) {
		super(colorCache, new Button((Composite) parentUiReference, SWT.NONE));
		this.imageRegistry = imageRegistry;

		setText(setup.getText());
		setToolTipText(setup.getToolTipText());
		setIcon(setup.getIcon());

		setMarkup(setup.getMarkup());

		getUiReference().setAlignment(AlignmentConvert.convert(setup.getAlignment()));

		getUiReference().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				fireActionPerformed();
			}
		});
	}

	@Override
	public Button getUiReference() {
		return (Button) super.getUiReference();
	}

	@Override
	public void setText(final String text) {
		if (text != null) {
			getUiReference().setText(text);
		}
		else {
			setText("");
		}
	}

	@Override
	public void setToolTipText(final String text) {
		getUiReference().setToolTipText(text);
	}

	@Override
	public void setIcon(final IImageConstant icon) {
		getUiReference().setImage(imageRegistry.getImage(icon));
	}

	@Override
	public void setMarkup(final Markup markup) {
		final Button button = this.getUiReference();
		final Font newFont = FontProvider.deriveFont(button.getFont(), markup);
		button.setFont(newFont);
	}

	@Override
	public void requestFocus() {
		getUiReference().setFocus();
	}

}
