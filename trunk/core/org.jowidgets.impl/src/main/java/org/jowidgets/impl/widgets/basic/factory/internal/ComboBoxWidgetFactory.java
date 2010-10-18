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

import org.jowidgets.api.veto.IInputVetoChecker;
import org.jowidgets.api.veto.NoVetoChecker;
import org.jowidgets.api.veto.VetoCheckResult;
import org.jowidgets.api.widgets.IComboBoxWidget;
import org.jowidgets.api.widgets.IWidget;
import org.jowidgets.api.widgets.descriptor.IComboBoxDescriptor;
import org.jowidgets.api.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.api.widgets.factory.IWidgetFactory;
import org.jowidgets.impl.spi.ISpiBluePrintFactory;
import org.jowidgets.impl.spi.blueprint.IComboBoxBluePrintSpi;
import org.jowidgets.impl.widgets.basic.ComboBoxWidget;
import org.jowidgets.impl.widgets.basic.factory.internal.util.ComboBoxBuilderConverter;
import org.jowidgets.spi.IWidgetFactorySpi;
import org.jowidgets.spi.verify.IInputVerifier;
import org.jowidgets.spi.widgets.IComboBoxWidgetSpi;

public class ComboBoxWidgetFactory extends AbstractWidgetFactory implements
		IWidgetFactory<IComboBoxWidget<?>, IComboBoxDescriptor<?>> {

	public ComboBoxWidgetFactory(
		final IGenericWidgetFactory genericWidgetFactory,
		final IWidgetFactorySpi spiWidgetFactory,
		final ISpiBluePrintFactory bpF) {

		super(genericWidgetFactory, spiWidgetFactory, bpF);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public IComboBoxWidget<?> create(final IWidget parent, final IComboBoxDescriptor<?> descriptor) {
		final IComboBoxBluePrintSpi bp = getSpiBluePrintFactory().comboBox().setSetup(descriptor);
		ComboBoxBuilderConverter.convert(bp, descriptor);

		final IInputVetoChecker<String> vetoChecker = getVetoChecker(descriptor);

		bp.setInputVerifier(new IInputVerifier() {

			@Override
			public boolean verify(final String currentValue, final String input, final int start, final int end) {
				//TODO must check veto on NEW current value
				final VetoCheckResult vetoCheckResult = vetoChecker.vetoCheck(input);
				return !vetoCheckResult.isVeto();
			}

		});

		final IComboBoxWidgetSpi widget = getSpiWidgetFactory().createComboBoxWidget(parent, bp);
		return new ComboBoxWidget(parent, widget, descriptor);
	}

	private IInputVetoChecker<String> getVetoChecker(final IComboBoxDescriptor<?> descriptor) {

		IInputVetoChecker<String> vetoChecker = descriptor.getInputVetoChecker();

		//TODO veto checker will be exchanged by textInputvalidator later
		if (vetoChecker == null) {
			vetoChecker = NoVetoChecker.getInstance();
		}

		return vetoChecker;
	}

}
