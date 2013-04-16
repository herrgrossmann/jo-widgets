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

package org.jowidgets.impl.widgets.common.blueprint.convenience.registry;

import org.jowidgets.common.widgets.builder.ICompositeSetupBuilderCommon;
import org.jowidgets.common.widgets.builder.ISliderSetupBuilderCommon;
import org.jowidgets.common.widgets.builder.ITextLabelSetupBuilderCommon;
import org.jowidgets.common.widgets.builder.IToolBarSetupBuilderCommon;
import org.jowidgets.impl.widgets.common.blueprint.convenience.CompositeSetupConvenienceCommon;
import org.jowidgets.impl.widgets.common.blueprint.convenience.SliderSetupConvenienceCommon;
import org.jowidgets.impl.widgets.common.blueprint.convenience.TextLabelSetupConvenienceCommon;
import org.jowidgets.impl.widgets.common.blueprint.convenience.ToolBarSetupConvenienceCommon;
import org.jowidgets.tools.widgets.blueprint.convenience.SetupBuilderConvenienceRegistry;

public class CommonSetupConvenienceRegistry extends SetupBuilderConvenienceRegistry {

	public CommonSetupConvenienceRegistry() {
		super();
		register(ICompositeSetupBuilderCommon.class, new CompositeSetupConvenienceCommon());
		register(ITextLabelSetupBuilderCommon.class, new TextLabelSetupConvenienceCommon());
		register(IToolBarSetupBuilderCommon.class, new ToolBarSetupConvenienceCommon());
		register(ISliderSetupBuilderCommon.class, new SliderSetupConvenienceCommon());
	}
}
