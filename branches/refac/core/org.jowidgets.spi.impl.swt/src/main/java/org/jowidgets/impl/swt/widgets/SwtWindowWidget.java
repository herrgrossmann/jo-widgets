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
package org.jowidgets.impl.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jowidgets.api.image.IImageConstant;
import org.jowidgets.api.look.Dimension;
import org.jowidgets.api.look.Position;
import org.jowidgets.api.look.Rectangle;
import org.jowidgets.api.widgets.IWindowWidgetCommon;
import org.jowidgets.api.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.api.widgets.setup.IWidgetSetupCommon;
import org.jowidgets.impl.swt.internal.color.IColorCache;
import org.jowidgets.impl.swt.internal.image.SwtImageRegistry;
import org.jowidgets.impl.swt.util.DimensionConvert;
import org.jowidgets.impl.swt.util.PositionConvert;

public class SwtWindowWidget extends SwtContainerWidget implements IWindowWidgetCommon {

	public SwtWindowWidget(final IGenericWidgetFactory factory, final IColorCache colorCache, final Shell window) {
		super(factory, colorCache, window);
	}

	@Override
	public Shell getUiReference() {
		return (Shell) super.getUiReference();
	}

	@Override
	public void pack() {
		getUiReference().pack();
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			getUiReference().open();

			final Shell shell = getUiReference();
			final Display display = shell.getDisplay();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}
		else {
			getUiReference().close();
		}
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

	public void setIcon(final SwtImageRegistry imageRegistry, final IImageConstant icon) {
		getUiReference().setImage(imageRegistry.getImage(icon));
	}

	@Override
	public Rectangle getParentBounds() {
		final Shell shell = getUiReference();
		final Composite parentShell = shell.getParent();

		if (parentShell == null) {
			final org.eclipse.swt.graphics.Rectangle clientArea = Display.getCurrent().getPrimaryMonitor().getClientArea();
			return new Rectangle(new Position(clientArea.x, clientArea.y), new Dimension(clientArea.width, clientArea.height));
		}
		else {
			final Point location = parentShell.getLocation();
			final Point size = parentShell.getSize();
			return new Rectangle(new Position(location.x, location.y), new Dimension(size.x, size.y));
		}
	}

	@Override
	public <WIDGET_TYPE extends IWindowWidgetCommon, DESCRIPTOR_TYPE extends IWidgetSetupCommon<? extends WIDGET_TYPE>> WIDGET_TYPE createChildWindow(
		final DESCRIPTOR_TYPE descriptor) {
		return getGenericWidgetFactory().create(this, descriptor);
	}

}
