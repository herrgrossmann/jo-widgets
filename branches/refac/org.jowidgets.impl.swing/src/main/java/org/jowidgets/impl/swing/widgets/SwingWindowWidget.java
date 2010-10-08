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
package org.jowidgets.impl.swing.widgets;

import java.awt.Window;

import org.jowidgets.api.image.IImageConstant;
import org.jowidgets.api.look.AutoPackPolicy;
import org.jowidgets.api.look.Dimension;
import org.jowidgets.api.look.Position;
import org.jowidgets.api.widgets.IWindowWidget;
import org.jowidgets.api.widgets.descriptor.base.IBaseWidgetDescriptor;
import org.jowidgets.api.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.impl.swing.internal.image.SwingImageRegistry;
import org.jowidgets.impl.swing.util.DimensionConvert;
import org.jowidgets.impl.swing.util.PositionConvert;

public class SwingWindowWidget extends SwingContainerWidget implements IWindowWidget {

	private final AutoPackPolicy autoPackPolicy;
	private boolean wasVisible;

	public SwingWindowWidget(final IGenericWidgetFactory factory, final Window window) {
		this(factory, window, AutoPackPolicy.ONCE);
	}

	public SwingWindowWidget(final IGenericWidgetFactory factory, final Window window, final AutoPackPolicy autoPackPolicy) {
		super(factory, window);
		this.wasVisible = false;
		this.autoPackPolicy = autoPackPolicy;
	}

	@Override
	public Window getUiReference() {
		return (Window) super.getUiReference();
	}

	@Override
	public void pack() {
		getUiReference().pack();
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			if (AutoPackPolicy.ALLWAYS.equals(autoPackPolicy)) {
				pack();
			}
			else if (!wasVisible && AutoPackPolicy.ONCE.equals(autoPackPolicy)) {
				pack();
			}
			wasVisible = true;
		}
		getUiReference().setVisible(visible);
	}

	@Override
	public final void setPosition(final Position position) {
		getUiReference().setLocation(PositionConvert.convert(position));
	}

	@Override
	public final Position getPosition() {
		return PositionConvert.convert(getUiReference().getLocation());
	}

	@Override
	public final void setSize(final Dimension size) {
		getUiReference().setSize(DimensionConvert.convert(size));
	}

	@Override
	public final Dimension getSize() {
		return DimensionConvert.convert(getUiReference().getSize());
	}

	@Override
	public <WIDGET_TYPE extends IWindowWidget, DESCRIPTOR_TYPE extends IBaseWidgetDescriptor<? extends WIDGET_TYPE>> WIDGET_TYPE createChildWindow(
		final DESCRIPTOR_TYPE descriptor) {
		return getGenericWidgetFactory().createChildWindow(this, descriptor);
	}

	protected void setIcon(final IImageConstant icon, final SwingImageRegistry imageRegistry) {
		getUiReference().setIconImage(imageRegistry.getImage(icon));
	}

}
