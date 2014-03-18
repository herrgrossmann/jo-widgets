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

import java.util.Collection;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.jowidgets.common.dnd.DnD;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.spi.clipboard.TransferTypeSpi;
import org.jowidgets.spi.dnd.IDragSourceListenerSpi;
import org.jowidgets.spi.impl.swt.common.dnd.SwtDragSource;
import org.jowidgets.spi.impl.swt.common.util.DimensionConvert;
import org.jowidgets.spi.widgets.IControlSpi;

public class SwtControl extends SwtComponent implements IControlSpi {

	private static final Dimension MAX_SIZE = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);

	private final SwtDragSource dragSource;

	public SwtControl(final Control control) {
		super(control);
		this.dragSource = new SwtDragSource(control);
	}

	@Override
	public void setLayoutConstraints(final Object layoutConstraints) {
		getUiReference().setLayoutData(layoutConstraints);
	}

	@Override
	public Object getLayoutConstraints() {
		return getUiReference().getLayoutData();
	}

	@Override
	public Dimension getMinSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getPreferredSize() {
		return DimensionConvert.convert(getUiReference().computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public Dimension getMaxSize() {
		return MAX_SIZE;
	}

	@Override
	public void setTransferTypes(final Collection<TransferTypeSpi> supportedTypes) {
		dragSource.setTransferTypes(supportedTypes);
	}

	@Override
	public void setActions(final Set<DnD> actions) {
		dragSource.setActions(actions);
	}

	@Override
	public void addDragSourceListenerSpi(final IDragSourceListenerSpi listener) {
		dragSource.addDragSourceListenerSpi(listener);
	}

	@Override
	public void removeDragSourceListenerSpi(final IDragSourceListenerSpi listener) {
		dragSource.removeDragSourceListenerSpi(listener);
	}

}
