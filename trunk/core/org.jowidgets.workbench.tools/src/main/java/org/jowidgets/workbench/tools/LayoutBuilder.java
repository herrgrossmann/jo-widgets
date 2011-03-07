/*
 * Copyright (c) 2011, grossmann
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

package org.jowidgets.workbench.tools;

import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.workbench.api.ILayout;
import org.jowidgets.workbench.api.ILayoutContainer;
import org.jowidgets.workbench.api.LayoutScope;
import org.jowidgets.workbench.toolkit.api.ILayoutBuilder;
import org.jowidgets.workbench.toolkit.api.ILayoutContainerBuilder;
import org.jowidgets.workbench.toolkit.api.WorkbenchToolkit;

public class LayoutBuilder implements ILayoutBuilder {

	private final ILayoutBuilder builder;

	public LayoutBuilder() {
		this(builder());
	}

	public LayoutBuilder(final String id) {
		this(builder(id));
	}

	public LayoutBuilder(final ILayoutBuilder builder) {
		super();
		this.builder = builder;
	}

	@Override
	public ILayoutBuilder setLabel(final String label) {
		return builder.setLabel(label);
	}

	@Override
	public ILayoutBuilder setTooltip(final String toolTiptext) {
		return builder.setTooltip(toolTiptext);
	}

	@Override
	public ILayoutBuilder setIcon(final IImageConstant icon) {
		return builder.setIcon(icon);
	}

	@Override
	public ILayoutBuilder setId(final String id) {
		return builder.setId(id);
	}

	@Override
	public ILayoutBuilder setScope(final LayoutScope scope) {
		return builder.setScope(scope);
	}

	@Override
	public ILayoutBuilder setLayoutContainer(final ILayoutContainer layoutContainer) {
		return builder.setLayoutContainer(layoutContainer);
	}

	@Override
	public ILayoutBuilder setLayoutContainer(final ILayoutContainerBuilder layoutContainerBuilder) {
		return builder.setLayoutContainer(layoutContainerBuilder);
	}

	@Override
	public ILayout build() {
		return builder.build();
	}

	public static ILayoutBuilder builder() {
		return WorkbenchToolkit.getLayoutBuilderFactory().layout();
	}

	public static ILayoutBuilder builder(final String id) {
		return builder().setId(id);
	}

}
