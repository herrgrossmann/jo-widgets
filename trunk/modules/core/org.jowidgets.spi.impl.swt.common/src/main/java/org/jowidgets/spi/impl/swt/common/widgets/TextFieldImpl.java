/*
 * Copyright (c) 2010, Michael Grossmann, Nikolaus Moll
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
package org.jowidgets.spi.impl.swt.common.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.jowidgets.common.mask.TextMaskMode;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.verify.IInputVerifier;
import org.jowidgets.spi.impl.mask.TextMaskVerifierFactory;
import org.jowidgets.spi.impl.swt.common.options.SwtOptions;
import org.jowidgets.spi.impl.swt.common.util.FontProvider;
import org.jowidgets.spi.impl.verify.InputVerifierHelper;
import org.jowidgets.spi.widgets.setup.ITextFieldSetupSpi;
import org.jowidgets.util.EmptyCheck;

public class TextFieldImpl extends AbstractTextInputControl {

	private VerifyListener inputVerificationListener;
	private Listener showListener;

	public TextFieldImpl(final Object parentUiReference, final ITextFieldSetupSpi setup) {
		super(new Text((Composite) parentUiReference, getStyle(setup)));

		if (SwtOptions.hasInputVerification()) {
			final IInputVerifier maskVerifier = TextMaskVerifierFactory.create(this, setup.getMask());

			final IInputVerifier inputVerifier = InputVerifierHelper.getInputVerifier(maskVerifier, setup);
			if (inputVerifier != null) {
				inputVerificationListener = createVerifyListener(inputVerifier);
				getUiReference().addVerifyListener(inputVerificationListener);
			}
		}

		if (setup.getMaxLength() != null) {
			getUiReference().setTextLimit(setup.getMaxLength().intValue());
		}

		if (SwtOptions.hasInputVerification() && setup.getMask() != null && TextMaskMode.FULL_MASK == setup.getMask().getMode()) {
			setText(setup.getMask().getPlaceholder());
			getUiReference().addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(final FocusEvent e) {
					if (getUiReference().getSelectionCount() == 0) {
						getUiReference().setSelection(0, 0);
					}
				}
			});
		}

		registerTextControl(getUiReference(), setup.getInputChangeEventPolicy());
	}

	@Override
	public Text getUiReference() {
		return (Text) super.getUiReference();
	}

	@Override
	public String getText() {
		return getUiReference().getText();
	}

	@Override
	public void setText(final String text) {
		if (SwtOptions.hasTextFieldTruncateWorkaround()
			&& showListener == null
			&& !getUiReference().isVisible()
			&& !EmptyCheck.isEmpty(text)) {
			showListener = new ShowListener();
			getUiReference().addListener(SWT.Paint, showListener);
		}
		if (text != null) {
			getUiReference().setText(text);
		}
		else {
			getUiReference().setText("");
		}
		if (!getUiReference().isFocusControl()) {
			fireInputChanged(getText());
		}
	}

	@Override
	protected void registerTextControl(final Text textControl) {
		super.registerTextControl(textControl);
		if (inputVerificationListener != null) {
			getUiReference().addVerifyListener(inputVerificationListener);
		}
	}

	@Override
	protected void unregisterTextControl(final Text textControl) {
		super.unregisterTextControl(textControl);
		if (inputVerificationListener != null) {
			getUiReference().removeVerifyListener(inputVerificationListener);
		}
	}

	@Override
	public void setFontSize(final int size) {
		getUiReference().setFont(FontProvider.deriveFont(getUiReference().getFont(), size));
	}

	@Override
	public void setFontName(final String fontName) {
		getUiReference().setFont(FontProvider.deriveFont(getUiReference().getFont(), fontName));
	}

	@Override
	public void setMarkup(final Markup markup) {
		getUiReference().setFont(FontProvider.deriveFont(getUiReference().getFont(), markup));
	}

	@Override
	public void setSelection(final int start, final int end) {
		getUiReference().setSelection(start, end);
	}

	@Override
	public void select() {
		getUiReference().selectAll();
	}

	@Override
	public void setCaretPosition(final int pos) {
		getUiReference().setSelection(pos, pos);
	}

	@Override
	public int getCaretPosition() {
		return getUiReference().getCaretPosition();
	}

	@Override
	public void setEditable(final boolean editable) {
		getUiReference().setEditable(editable);
	}

	private VerifyListener createVerifyListener(final IInputVerifier verifier) {
		return new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent verifyEvent) {
				verifyEvent.doit = verifier.verify(
						getUiReference().getText(),
						verifyEvent.text,
						verifyEvent.start,
						verifyEvent.end);
			}
		};
	}

	private static int getStyle(final ITextFieldSetupSpi setup) {
		int result = 0;
		if (setup.hasBorder()) {
			result = result | SWT.BORDER;
		}
		if (setup.isPasswordPresentation()) {
			result = result | SWT.PASSWORD;
		}
		return result;
	}

	private final class ShowListener implements Listener {
		@Override
		public void handleEvent(final Event event) {
			if (getUiReference().isVisible() && !getUiReference().isFocusControl()) {
				getUiReference().setSelection(new Point(0, 0));
				if (showListener != null) {
					getUiReference().removeListener(SWT.Paint, showListener);
					showListener = null;
				}
			}
		}
	};

}
