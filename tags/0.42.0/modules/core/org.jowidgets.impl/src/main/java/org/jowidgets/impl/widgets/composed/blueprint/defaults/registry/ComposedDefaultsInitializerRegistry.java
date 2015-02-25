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

package org.jowidgets.impl.widgets.composed.blueprint.defaults.registry;

import org.jowidgets.api.widgets.blueprint.IExpandCompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.ILevelMeterBluePrint;
import org.jowidgets.api.widgets.blueprint.builder.ICollectionInputControlSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.ICollectionInputDialogSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.ICollectionInputFieldSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IInputComponentValidationLabelSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IInputCompositeSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IInputDialogSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IInputFieldSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.ILoginDialogSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IMessageDialogSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IPasswordChangeDialogSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IProgressBarSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IQuestionDialogSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.ISliderViewerSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.ITreeSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.ITreeViewerSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IUnitValueFieldSetupBuilder;
import org.jowidgets.api.widgets.blueprint.builder.IValidationLabelSetupBuilder;
import org.jowidgets.impl.widgets.basic.blueprint.defaults.registry.BasicDefaultsInitializerRegistry;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.CollectionInputControlDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.CollectionInputDialogDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.CollectionInputFieldDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.ExpandCompositeDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.InputComponentValidationLabelDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.InputCompositeDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.InputDialogDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.InputFieldDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.LevelMeterDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.LoginDialogDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.MessageDialogDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.PasswordChangeDialogDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.ProgressBarDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.QuestionDialogDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.SliderViewerDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.TreeDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.TreeViewerDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.UnitValueFieldDefaults;
import org.jowidgets.impl.widgets.composed.blueprint.defaults.ValidationLabelDefaults;

public class ComposedDefaultsInitializerRegistry extends BasicDefaultsInitializerRegistry {

	public ComposedDefaultsInitializerRegistry() {
		super();
		register(IInputCompositeSetupBuilder.class, new InputCompositeDefaults());
		register(ICollectionInputControlSetupBuilder.class, new CollectionInputControlDefaults());
		register(IMessageDialogSetupBuilder.class, new MessageDialogDefaults());
		register(IQuestionDialogSetupBuilder.class, new QuestionDialogDefaults());
		register(IProgressBarSetupBuilder.class, new ProgressBarDefaults());
		register(IInputDialogSetupBuilder.class, new InputDialogDefaults());
		register(ILoginDialogSetupBuilder.class, new LoginDialogDefaults());
		register(IPasswordChangeDialogSetupBuilder.class, new PasswordChangeDialogDefaults());
		register(IInputFieldSetupBuilder.class, new InputFieldDefaults());
		register(IUnitValueFieldSetupBuilder.class, new UnitValueFieldDefaults());
		register(IValidationLabelSetupBuilder.class, new ValidationLabelDefaults());
		register(IInputComponentValidationLabelSetupBuilder.class, new InputComponentValidationLabelDefaults());
		register(ICollectionInputDialogSetupBuilder.class, new CollectionInputDialogDefaults());
		register(ICollectionInputFieldSetupBuilder.class, new CollectionInputFieldDefaults());
		register(IExpandCompositeBluePrint.class, new ExpandCompositeDefaults());
		register(ILevelMeterBluePrint.class, new LevelMeterDefaults());
		register(ITreeSetupBuilder.class, new TreeDefaults());
		register(ISliderViewerSetupBuilder.class, new SliderViewerDefaults());
		register(ITreeViewerSetupBuilder.class, new TreeViewerDefaults());
	}
}
