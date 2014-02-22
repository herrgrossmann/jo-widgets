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

package org.jowidgets.spi.impl.dummy;

import java.util.Collections;
import java.util.List;

import org.jowidgets.common.application.IApplicationRunner;
import org.jowidgets.common.image.IImageRegistry;
import org.jowidgets.common.threads.IUiThreadAccessCommon;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.IComponentCommon;
import org.jowidgets.spi.IOptionalWidgetsFactorySpi;
import org.jowidgets.spi.IWidgetFactorySpi;
import org.jowidgets.spi.IWidgetsServiceProvider;
import org.jowidgets.spi.clipboard.IClipboardSpi;
import org.jowidgets.spi.image.IImageHandleFactorySpi;
import org.jowidgets.spi.impl.dummy.application.DummyApplicationRunner;
import org.jowidgets.spi.impl.dummy.clipboard.DummyClipboard;
import org.jowidgets.spi.impl.dummy.image.DummyImageHandleFactory;
import org.jowidgets.spi.impl.dummy.image.DummyImageHandleFactorySpi;
import org.jowidgets.spi.impl.dummy.image.DummyImageRegistry;
import org.jowidgets.spi.impl.dummy.threads.DummyUiThreadAccess;

public class DummyWidgetsServiceProvider implements IWidgetsServiceProvider {

	private final DummyImageHandleFactorySpi imageHandleFactorySpi;
	private final DummyImageRegistry imageRegistry;
	private final DummyWidgetFactory widgetFactory;
	private final DummyOptionalWidgetsFactory optionalWidgetsFactory;
	private final DummyClipboard clipboard;

	public DummyWidgetsServiceProvider() {
		super();
		this.imageRegistry = new DummyImageRegistry(new DummyImageHandleFactory());
		this.imageHandleFactorySpi = new DummyImageHandleFactorySpi(imageRegistry);
		this.widgetFactory = new DummyWidgetFactory(imageRegistry);
		this.optionalWidgetsFactory = new DummyOptionalWidgetsFactory();
		this.clipboard = new DummyClipboard();
	}

	@Override
	public IImageRegistry getImageRegistry() {
		return imageRegistry;
	}

	@Override
	public IImageHandleFactorySpi getImageHandleFactory() {
		return imageHandleFactorySpi;
	}

	@Override
	public IWidgetFactorySpi getWidgetFactory() {
		return widgetFactory;
	}

	@Override
	public IOptionalWidgetsFactorySpi getOptionalWidgetFactory() {
		return optionalWidgetsFactory;
	}

	@Override
	public IUiThreadAccessCommon createUiThreadAccess() {
		return new DummyUiThreadAccess();
	}

	@Override
	public IApplicationRunner createApplicationRunner() {
		return new DummyApplicationRunner();
	}

	@Override
	public Object getActiveWindowUiReference() {
		//TODO LG active window must be simulated
		return null;
	}

	@Override
	public List<Object> getAllWindowsUiReference() {
		return Collections.emptyList();
	}

	@Override
	public Position toScreen(final Position localPosition, final IComponentCommon component) {
		// TODO LG implement
		return null;
	}

	@Override
	public Position toLocal(final Position screenPosition, final IComponentCommon component) {
		// TODO LG implement
		return null;
	}

	@Override
	public IClipboardSpi getClipboard() {
		return clipboard;
	}

}
