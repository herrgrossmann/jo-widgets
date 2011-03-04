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

package org.jowidgets.tools.toolkit;

import org.jowidgets.api.convert.IConverterProvider;
import org.jowidgets.api.threads.IUiThreadAccess;
import org.jowidgets.api.toolkit.IMessagePane;
import org.jowidgets.api.toolkit.IQuestionPane;
import org.jowidgets.api.toolkit.IToolkit;
import org.jowidgets.api.toolkit.IWidgetWrapperFactory;
import org.jowidgets.api.utils.IWidgetUtils;
import org.jowidgets.api.widgets.IFrame;
import org.jowidgets.api.widgets.IWindow;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.api.widgets.descriptor.IFrameDescriptor;
import org.jowidgets.common.application.IApplicationLifecycle;
import org.jowidgets.common.application.IApplicationRunner;
import org.jowidgets.common.image.IImageRegistry;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.util.Assert;

public class ToolkitWrapper implements IToolkit {

	private final IToolkit toolkit;

	public ToolkitWrapper(final IToolkit toolkit) {
		Assert.paramNotNull(toolkit, "toolkit");
		this.toolkit = toolkit;
	}

	protected IToolkit getToolkit() {
		return toolkit;
	}

	@Override
	public IImageRegistry getImageRegistry() {
		return toolkit.getImageRegistry();
	}

	@Override
	public IMessagePane getMessagePane() {
		return toolkit.getMessagePane();
	}

	@Override
	public IQuestionPane getQuestionPane() {
		return toolkit.getQuestionPane();
	}

	@Override
	public IGenericWidgetFactory getWidgetFactory() {
		return toolkit.getWidgetFactory();
	}

	@Override
	public IWidgetWrapperFactory getWidgetWrapperFactory() {
		return toolkit.getWidgetWrapperFactory();
	}

	@Override
	public IBluePrintFactory getBluePrintFactory() {
		return toolkit.getBluePrintFactory();
	}

	@Override
	public IConverterProvider getConverterProvider() {
		return toolkit.getConverterProvider();
	}

	@Override
	public IApplicationRunner getApplicationRunner() {
		return toolkit.getApplicationRunner();
	}

	@Override
	public IUiThreadAccess getUiThreadAccess() {
		return toolkit.getUiThreadAccess();
	}

	@Override
	public IWidgetUtils getWidgetUtils() {
		return toolkit.getWidgetUtils();
	}

	@Override
	public IWindow getActiveWindow() {
		return toolkit.getActiveWindow();
	}

	@Override
	public IFrame createRootFrame(final IFrameDescriptor descriptor) {
		return toolkit.createRootFrame(descriptor);
	}

	@Override
	public IFrame createRootFrame(final IFrameDescriptor descriptor, final IApplicationLifecycle lifecycle) {
		return toolkit.createRootFrame(descriptor, lifecycle);
	}

}
