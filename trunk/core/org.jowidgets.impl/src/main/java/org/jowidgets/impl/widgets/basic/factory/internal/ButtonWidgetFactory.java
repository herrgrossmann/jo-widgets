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

package org.jowidgets.impl.widgets.basic.factory.internal;

import org.jowidgets.api.widgets.IButton;
import org.jowidgets.api.widgets.descriptor.IButtonDescriptor;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.common.widgets.factory.IWidgetFactory;
import org.jowidgets.impl.spi.ISpiBluePrintFactory;
import org.jowidgets.impl.spi.blueprint.IButtonBluePrintSpi;
import org.jowidgets.impl.widgets.basic.ButtonWidget;
import org.jowidgets.spi.IWidgetFactorySpi;
import org.jowidgets.spi.widgets.IButtonSpi;

public class ButtonWidgetFactory extends AbstractWidgetFactory implements IWidgetFactory<IButton, IButtonDescriptor> {

	public ButtonWidgetFactory(
		final IGenericWidgetFactory genericWidgetFactory,
		final IWidgetFactorySpi spiWidgetFactory,
		final ISpiBluePrintFactory bpF) {

		super(genericWidgetFactory, spiWidgetFactory, bpF);
	}

	@Override
	public IButton create(final Object parentUiReference, final IButtonDescriptor descriptor) {
		final IButtonBluePrintSpi bp = getSpiBluePrintFactory().button().setSetup(descriptor);
		final IButtonSpi buttonWidgetSpi = getSpiWidgetFactory().createButton(parentUiReference, bp);
		return new ButtonWidget(buttonWidgetSpi, descriptor);
	}

}
