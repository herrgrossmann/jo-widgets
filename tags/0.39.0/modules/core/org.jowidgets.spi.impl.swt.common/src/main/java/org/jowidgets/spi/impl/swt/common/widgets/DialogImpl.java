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
package org.jowidgets.spi.impl.swt.common.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.spi.widgets.IFrameSpi;
import org.jowidgets.spi.widgets.setup.IDialogSetupSpi;

public class DialogImpl extends WindowImpl implements IFrameSpi {

	private final boolean isModal;

	public DialogImpl(final IGenericWidgetFactory factory, final Object parentUiReference, final IDialogSetupSpi setup) {

		super(factory, new Shell((Shell) parentUiReference, getStyle(setup)), setup.isCloseable());

		this.isModal = setup.isModal();

		if (setup.getTitle() != null) {
			getUiReference().setText(setup.getTitle());
		}
		setIcon(setup.getIcon());

		final boolean closeOnEscape = setup.isCloseable() && setup.isCloseOnEscape();

		getUiReference().addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE) {
					if (closeOnEscape) {
						setVisible(false);
					}
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
				}
			}
		});

	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			getUiReference().open();
			if (isModal) {
				final Shell shell = getUiReference();
				final Display display = shell.getDisplay();

				while (!shell.isDisposed() && shell.isVisible()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		}
		else {
			super.setVisible(false);
		}
	}

	private static int getStyle(final IDialogSetupSpi setup) {
		int result;
		if (setup.isDecorated()) {
			result = SWT.TITLE | SWT.BORDER;
		}
		else {
			result = SWT.NONE;
		}
		if (setup.isCloseable() && setup.isDecorated()) {
			result = result | SWT.CLOSE;
		}
		if (setup.isResizable() && setup.isDecorated()) {
			result = result | SWT.RESIZE;
		}
		if (setup.isModal()) {
			result = result | SWT.APPLICATION_MODAL;
		}
		return result;
	}

}
