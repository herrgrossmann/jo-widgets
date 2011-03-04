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

package org.jowidgets.tools.model.item;

import org.jowidgets.api.model.item.IContainerContentCreator;
import org.jowidgets.api.model.item.IContainerItemModel;
import org.jowidgets.api.model.item.IContainerItemModelBuilder;
import org.jowidgets.api.toolkit.Toolkit;

public class ContainerItemModel extends AbstractItemModelWrapper implements IContainerItemModel {

	public ContainerItemModel(final IContainerContentCreator contentCreator) {
		this(builder(contentCreator));
	}

	public ContainerItemModel(final IContainerItemModelBuilder builder) {
		super(builder.build());
	}

	@Override
	protected IContainerItemModel getItemModel() {
		return (IContainerItemModel) super.getItemModel();
	}

	@Override
	public IContainerContentCreator getContentCreator() {
		return getItemModel().getContentCreator();
	}

	@Override
	public void setContentCreator(final IContainerContentCreator contentCreator) {
		getItemModel().setContentCreator(contentCreator);
	}

	@Override
	public IContainerItemModel createCopy() {
		return getItemModel().createCopy();
	}

	public static IContainerItemModelBuilder builder() {
		return Toolkit.getModelFactoryProvider().getItemModelFactory().containerItemBuilder();
	}

	public static IContainerItemModelBuilder builder(final IContainerContentCreator contentCreator) {
		return builder().setContentCreator(contentCreator);
	}

}
