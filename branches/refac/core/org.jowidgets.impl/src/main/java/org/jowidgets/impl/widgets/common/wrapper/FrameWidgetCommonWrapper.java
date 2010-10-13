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

package org.jowidgets.impl.widgets.common.wrapper;

import org.jowidgets.api.widgets.IFrameWidgetCommon;
import org.jowidgets.api.widgets.IWidget;
import org.jowidgets.api.widgets.factory.ICustomWidgetFactory;
import org.jowidgets.api.widgets.layout.ILayoutDescriptor;
import org.jowidgets.api.widgets.setup.IWidgetSetupCommon;

public class FrameWidgetCommonWrapper extends WindowWidgetCommonWrapper implements IFrameWidgetCommon {

	public FrameWidgetCommonWrapper(final IFrameWidgetCommon widget) {
		super(widget);
	}

	@Override
	protected IFrameWidgetCommon getWidget() {
		return (IFrameWidgetCommon) super.getWidget();
	}

	@Override
	public void setLayout(final ILayoutDescriptor layoutDescriptor) {
		getWidget().setLayout(layoutDescriptor);
	}

	@Override
	public void layoutBegin() {
		getWidget().layoutBegin();
	}

	@Override
	public void layoutEnd() {
		getWidget().layoutEnd();
	}

	@Override
	public void removeAll() {
		getWidget().removeAll();
	}

	@Override
	public <WIDGET_TYPE extends IWidget> WIDGET_TYPE add(
		final IWidgetSetupCommon<? extends WIDGET_TYPE> descriptor,
		final Object layoutConstraints) {
		return getWidget().add(descriptor, layoutConstraints);
	}

	@Override
	public <WIDGET_TYPE extends IWidget> WIDGET_TYPE add(
		final ICustomWidgetFactory<WIDGET_TYPE> factory,
		final Object layoutConstraints) {
		return getWidget().add(factory, layoutConstraints);
	}

}
