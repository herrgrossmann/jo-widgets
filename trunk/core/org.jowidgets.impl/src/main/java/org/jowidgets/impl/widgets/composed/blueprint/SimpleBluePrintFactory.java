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
package org.jowidgets.impl.widgets.composed.blueprint;

import org.jowidgets.api.convert.IConverter;
import org.jowidgets.api.widgets.blueprint.IInputCompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.IInputDialogBluePrint;
import org.jowidgets.api.widgets.blueprint.IInputFieldBluePrint;
import org.jowidgets.api.widgets.blueprint.ILabelBluePrint;
import org.jowidgets.api.widgets.blueprint.ITextSeparatorBluePrint;
import org.jowidgets.api.widgets.blueprint.IValidationLabelBluePrint;
import org.jowidgets.api.widgets.blueprint.convenience.ISetupBuilderConvenienceRegistry;
import org.jowidgets.api.widgets.blueprint.defaults.IDefaultsInitializerRegistry;
import org.jowidgets.api.widgets.blueprint.factory.ISimpleBluePrintFactory;
import org.jowidgets.api.widgets.content.IInputContentCreator;
import org.jowidgets.api.widgets.descriptor.IInputCompositeDescriptor;
import org.jowidgets.api.widgets.descriptor.IInputDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.IInputFieldDescriptor;
import org.jowidgets.api.widgets.descriptor.ILabelDescriptor;
import org.jowidgets.api.widgets.descriptor.ITextSeparatorDescriptor;
import org.jowidgets.api.widgets.descriptor.IValidationLabelDescriptor;
import org.jowidgets.impl.widgets.basic.blueprint.BasicBluePrintFactory;
import org.jowidgets.util.Assert;

public class SimpleBluePrintFactory extends BasicBluePrintFactory implements ISimpleBluePrintFactory {

	public SimpleBluePrintFactory(
		final ISetupBuilderConvenienceRegistry setupBuilderConvenienceRegistry,
		final IDefaultsInitializerRegistry defaultInitializerRegistry) {
		super(setupBuilderConvenienceRegistry, defaultInitializerRegistry);
	}

	@Override
	public final ILabelBluePrint label() {
		return createProxy(ILabelBluePrint.class, ILabelDescriptor.class);
	}

	@Override
	public ITextSeparatorBluePrint textSeparator() {
		return createProxy(ITextSeparatorBluePrint.class, ITextSeparatorDescriptor.class);
	}

	@Override
	public final IValidationLabelBluePrint validationLabel() {
		return createProxy(IValidationLabelBluePrint.class, IValidationLabelDescriptor.class);
	}

	@Override
	public final <INPUT_TYPE> IInputDialogBluePrint<INPUT_TYPE> inputDialog(final IInputContentCreator<INPUT_TYPE> contentCreator) {
		Assert.paramNotNull(contentCreator, "contentCreator");

		final IInputDialogBluePrint<INPUT_TYPE> inputDialogBluePrint;
		inputDialogBluePrint = createProxy(IInputDialogBluePrint.class, IInputDialogDescriptor.class);

		inputDialogBluePrint.setContentCreator(contentCreator);
		return inputDialogBluePrint;
	}

	@Override
	public final <INPUT_TYPE> IInputCompositeBluePrint<INPUT_TYPE> inputComposite(
		final IInputContentCreator<INPUT_TYPE> contentCreator) {
		Assert.paramNotNull(contentCreator, "contentCreator");

		final IInputCompositeBluePrint<INPUT_TYPE> inputCompositeBluePrint = createProxy(
				IInputCompositeBluePrint.class,
				IInputCompositeDescriptor.class);

		inputCompositeBluePrint.setContentCreator(contentCreator);
		return inputCompositeBluePrint;
	}

	@Override
	public final <INPUT_TYPE> IInputFieldBluePrint<INPUT_TYPE> inputField(final IConverter<INPUT_TYPE> converter) {
		Assert.paramNotNull(converter, "converter");

		final IInputFieldBluePrint<INPUT_TYPE> result = createProxy(IInputFieldBluePrint.class, IInputFieldDescriptor.class);
		return result.setConverter(converter);
	}

}
