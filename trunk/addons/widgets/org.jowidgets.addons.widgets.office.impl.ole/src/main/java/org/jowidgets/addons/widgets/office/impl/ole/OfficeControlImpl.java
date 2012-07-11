/*
 * Copyright (c) 2012, grossmann, waheckma
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

package org.jowidgets.addons.widgets.office.impl.ole;

import org.jowidgets.addons.widgets.office.api.IOfficeControl;
import org.jowidgets.addons.widgets.office.api.IOfficeControlSetupBuilder;
import org.jowidgets.addons.widgets.ole.document.api.IOleDocument;
import org.jowidgets.addons.widgets.ole.document.tools.OleDocumentWrapper;
import org.jowidgets.util.event.ChangeObservable;
import org.jowidgets.util.event.IChangeListener;

class OfficeControlImpl extends OleDocumentWrapper implements IOfficeControl {

	@SuppressWarnings("unused")
	private final IOleDocument oleDocument;

	private final ChangeObservable changeObservable;

	public OfficeControlImpl(final IOleDocument oleDocument, final IOfficeControlSetupBuilder<?> setup) {
		super(oleDocument);
		this.oleDocument = oleDocument;
		this.changeObservable = new ChangeObservable();
	}

	@Override
	public void setToolbarVisible(final boolean visible) {

	}

	@Override
	public void addDocumentChangeListener(final IChangeListener changeListener) {
		changeObservable.addChangeListener(changeListener);
	}

	@Override
	public void removeDocumentChangeListener(final IChangeListener changeListener) {
		changeObservable.removeChangeListener(changeListener);
	}

}
