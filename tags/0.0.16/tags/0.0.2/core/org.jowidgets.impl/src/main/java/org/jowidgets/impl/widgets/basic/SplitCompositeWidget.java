/*
 * Copyright (c) 2010, grossmann
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

package org.jowidgets.impl.widgets.basic;

import org.jowidgets.api.widgets.ICompositeWidget;
import org.jowidgets.api.widgets.ISplitCompositeWidget;
import org.jowidgets.api.widgets.descriptor.setup.ISplitCompositeSetup;
import org.jowidgets.common.widgets.IWidget;
import org.jowidgets.impl.base.delegate.ChildWidgetDelegate;
import org.jowidgets.impl.widgets.basic.factory.internal.util.VisibiliySettingsInvoker;
import org.jowidgets.impl.widgets.common.wrapper.SplitContainerWidgetCommonWrapper;
import org.jowidgets.spi.widgets.ISplitContainerWidgetSpi;

public class SplitCompositeWidget extends SplitContainerWidgetCommonWrapper implements ISplitCompositeWidget {

	private final ChildWidgetDelegate childWidgetDelegate;
	private final ICompositeWidget first;
	private final ICompositeWidget second;

	public SplitCompositeWidget(
		final IWidget parent,
		final ISplitContainerWidgetSpi containerWidgetSpi,
		final ISplitCompositeSetup setup) {
		super(containerWidgetSpi);
		this.childWidgetDelegate = new ChildWidgetDelegate(parent);
		this.first = new CompositeWidget(this, getWidget().getFirst());
		this.second = new CompositeWidget(this, getWidget().getSecond());
		VisibiliySettingsInvoker.setVisibility(setup, this);
	}

	@Override
	public IWidget getParent() {
		return childWidgetDelegate.getParent();
	}

	@Override
	public ICompositeWidget getFirst() {
		return first;
	}

	@Override
	public ICompositeWidget getSecond() {
		return second;
	}

}
