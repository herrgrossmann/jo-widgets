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
package org.jowidgets.spi.impl.swing.common.widgets;

import java.awt.Component;

import org.jowidgets.common.types.Dimension;
import org.jowidgets.spi.dnd.IDragSourceSpi;
import org.jowidgets.spi.widgets.IControlSpi;

public abstract class AbstractActionControl extends AbstractActionComponent implements IControlSpi {

	private final SwingControl swingControlDelegate;

	public AbstractActionControl(final Component component) {
		super(component);
		this.swingControlDelegate = new SwingControl(component);
	}

	@Override
	public void setLayoutConstraints(final Object layoutConstraints) {
		swingControlDelegate.setLayoutConstraints(layoutConstraints);
	}

	@Override
	public Object getLayoutConstraints() {
		return swingControlDelegate.getLayoutConstraints();
	}

	@Override
	public Dimension getMinSize() {
		return swingControlDelegate.getMinSize();
	}

	@Override
	public Dimension getPreferredSize() {
		return swingControlDelegate.getPreferredSize();
	}

	@Override
	public Dimension getMaxSize() {
		return swingControlDelegate.getMaxSize();
	}

	@Override
	public void setToolTipText(final String toolTip) {
		swingControlDelegate.setToolTipText(toolTip);
	}

	@Override
	public IDragSourceSpi getDragSource() {
		return swingControlDelegate.getDragSource();
	}

}
