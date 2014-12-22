/*
 * Copyright (c) 2013, Michael Grossmann
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
package org.jowidgets.impl.widgets.composed.factory;

import org.jowidgets.api.widgets.descriptor.ICalendarDescriptor;
import org.jowidgets.api.widgets.descriptor.ICollectionInputControlDescriptor;
import org.jowidgets.api.widgets.descriptor.ICollectionInputDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.ICollectionInputFieldDescriptor;
import org.jowidgets.api.widgets.descriptor.ICombinedCollectionInputFieldDescriptor;
import org.jowidgets.api.widgets.descriptor.IExpandCompositeDescriptor;
import org.jowidgets.api.widgets.descriptor.IInputComponentValidationLabelDescriptor;
import org.jowidgets.api.widgets.descriptor.IInputCompositeDescriptor;
import org.jowidgets.api.widgets.descriptor.IInputDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.IInputFieldDescriptor;
import org.jowidgets.api.widgets.descriptor.ILabelDescriptor;
import org.jowidgets.api.widgets.descriptor.ILevelMeterDescriptor;
import org.jowidgets.api.widgets.descriptor.ILoginDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.IMessageDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.IPasswordChangeDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.IProgressBarDescriptor;
import org.jowidgets.api.widgets.descriptor.IQuestionDialogDescriptor;
import org.jowidgets.api.widgets.descriptor.ISliderViewerDescriptor;
import org.jowidgets.api.widgets.descriptor.ITextSeparatorDescriptor;
import org.jowidgets.api.widgets.descriptor.ITreeViewerDescriptor;
import org.jowidgets.api.widgets.descriptor.IUnitValueFieldDescriptor;
import org.jowidgets.api.widgets.descriptor.IValidationResultLabelDescriptor;
import org.jowidgets.impl.widgets.basic.factory.BasicGenericWidgetFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.CollectionInputControlFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.CollectionInputDialogFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.CollectionInputFieldFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.CombinedCollectionInputFieldFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.CustomCalendarFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.ExpandCompositeFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.InputCompositeFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.InputDialogFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.InputFieldFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.LabelFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.LevelMeterFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.LoginDialogFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.MessageDialogFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.PasswordChangeDialogFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.ProgressBarFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.QuestionDialogFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.SliderViewerFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.TextSeparatorFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.TreeViewerFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.UnitValueFieldFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.ValidateableStateLabelFactory;
import org.jowidgets.impl.widgets.composed.factory.internal.ValidationResultLabelFactory;
import org.jowidgets.spi.IWidgetsServiceProvider;

public class GenericWidgetFactory extends BasicGenericWidgetFactory {

	public GenericWidgetFactory(final IWidgetsServiceProvider widgetsServiceProvider) {
		super(widgetsServiceProvider);
		registerCustomWidgetFactories();
		registerFallbackWidgets();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void registerCustomWidgetFactories() {
		register(IInputFieldDescriptor.class, new InputFieldFactory(this));
		register(IUnitValueFieldDescriptor.class, new UnitValueFieldFactory());
		register(ILabelDescriptor.class, new LabelFactory());
		register(ITextSeparatorDescriptor.class, new TextSeparatorFactory());
		register(IMessageDialogDescriptor.class, new MessageDialogFactory(this));
		register(IQuestionDialogDescriptor.class, new QuestionDialogFactory(this));
		register(IInputDialogDescriptor.class, new InputDialogFactory(this));
		register(ILoginDialogDescriptor.class, new LoginDialogFactory(this));
		register(IPasswordChangeDialogDescriptor.class, new PasswordChangeDialogFactory(this));
		register(IInputCompositeDescriptor.class, new InputCompositeFactory());
		register(IValidationResultLabelDescriptor.class, new ValidationResultLabelFactory(this));
		register(IInputComponentValidationLabelDescriptor.class, new ValidateableStateLabelFactory(this));
		register(IProgressBarDescriptor.class, new ProgressBarFactory(getSpiWidgetFactory()));
		register(ICollectionInputControlDescriptor.class, new CollectionInputControlFactory());
		register(ICollectionInputDialogDescriptor.class, new CollectionInputDialogFactory());
		register(ICollectionInputFieldDescriptor.class, new CollectionInputFieldFactory());
		register(ICombinedCollectionInputFieldDescriptor.class, new CombinedCollectionInputFieldFactory());
		register(IExpandCompositeDescriptor.class, new ExpandCompositeFactory());
		register(ILevelMeterDescriptor.class, new LevelMeterFactory());
		register(ITreeViewerDescriptor.class, new TreeViewerFactory<Object>());
		register(ISliderViewerDescriptor.class, new SliderViewerFactory<Object>(this));
	}

	private void registerFallbackWidgets() {
		if (getFactory(ICalendarDescriptor.class) == null) {
			register(ICalendarDescriptor.class, new CustomCalendarFactory());
		}
	}

}
