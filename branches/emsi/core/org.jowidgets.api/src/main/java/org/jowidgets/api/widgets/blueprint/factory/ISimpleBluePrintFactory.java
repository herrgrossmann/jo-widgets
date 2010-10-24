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
package org.jowidgets.api.widgets.blueprint.factory;

import org.jowidgets.api.convert.IConverter;
import org.jowidgets.api.widgets.blueprint.IInputCompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.IInputDialogBluePrint;
import org.jowidgets.api.widgets.blueprint.IInputFieldBluePrint;
import org.jowidgets.api.widgets.blueprint.ILabelBluePrint;
import org.jowidgets.api.widgets.blueprint.ITextSeparatorBluePrint;
import org.jowidgets.api.widgets.blueprint.IValidationLabelBluePrint;
import org.jowidgets.api.widgets.content.IInputContentCreator;

public interface ISimpleBluePrintFactory extends IBasicBluePrintFactory {

	ILabelBluePrint label();

	ITextSeparatorBluePrint textSeparator();

	IValidationLabelBluePrint validationLabel();

	<INPUT_TYPE> IInputFieldBluePrint<INPUT_TYPE> inputField(final IConverter<INPUT_TYPE> converter);

	<INPUT_TYPE> IInputDialogBluePrint<INPUT_TYPE> inputDialog(final IInputContentCreator<INPUT_TYPE> contentCreator);

	<INPUT_TYPE> IInputCompositeBluePrint<INPUT_TYPE> inputComposite(final IInputContentCreator<INPUT_TYPE> contentCreator);

}
