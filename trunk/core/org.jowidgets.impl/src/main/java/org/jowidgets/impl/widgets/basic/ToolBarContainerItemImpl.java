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

package org.jowidgets.impl.widgets.basic;

import org.jowidgets.api.model.item.IContainerItemModel;
import org.jowidgets.api.model.item.IToolBarItemModel;
import org.jowidgets.api.widgets.IToolBar;
import org.jowidgets.api.widgets.IToolBarContainerItem;
import org.jowidgets.api.widgets.descriptor.setup.IContainerSetup;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.impl.model.item.ContainerItemModelBuilder;
import org.jowidgets.spi.widgets.IToolBarContainerItemSpi;

public class ToolBarContainerItemImpl extends ContainerImpl implements IToolBarContainerItem {

	private final IToolBar parent;

	private String text;
	private String toolTipText;
	private IImageConstant icon;

	public ToolBarContainerItemImpl(
		final IToolBar parent,
		final IToolBarContainerItemSpi toolBarContainerItemSpi,
		final IContainerSetup setup) {
		super(toolBarContainerItemSpi, setup);

		this.parent = parent;
	}

	@Override
	public IToolBarContainerItemSpi getWidget() {
		return (IToolBarContainerItemSpi) super.getWidget();
	}

	@Override
	public IToolBar getParent() {
		return parent;
	}

	@Override
	public void setText(final String text) {
		this.text = text;
		getWidget().setText(text);
	}

	@Override
	public void setToolTipText(final String toolTipText) {
		this.toolTipText = toolTipText;
		getWidget().setToolTipText(toolTipText);
	}

	@Override
	public void setIcon(final IImageConstant icon) {
		this.icon = icon;
		getWidget().setIcon(icon);
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getToolTipText() {
		return toolTipText;
	}

	@Override
	public IImageConstant getIcon() {
		return icon;
	}

	@Override
	public void setModel(final IToolBarItemModel model) {
		// TODO MG implement model support
	}

	@Override
	public void setModel(final IContainerItemModel model) {
		// TODO MG implement model support
	}

	@Override
	public IContainerItemModel getModel() {
		// TODO MG implement model support
		return new ContainerItemModelBuilder().build();
	}

}
