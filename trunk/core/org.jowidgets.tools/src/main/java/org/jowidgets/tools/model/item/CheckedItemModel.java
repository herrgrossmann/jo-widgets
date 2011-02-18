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

import org.jowidgets.api.model.item.ICheckedItemModel;
import org.jowidgets.api.model.item.ICheckedItemModelBuilder;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.common.image.IImageConstant;

public class CheckedItemModel extends AbstractSelectableItemModelWrapper implements ICheckedItemModel {

	public CheckedItemModel() {
		this(builder());
	}

	public CheckedItemModel(final String text) {
		this(builder().setText(text));
	}

	public CheckedItemModel(final String text, final IImageConstant icon) {
		this(builder(text, icon));
	}

	public CheckedItemModel(final String text, final String toolTipText) {
		this(builder(text, toolTipText));
	}

	public CheckedItemModel(final String text, final String toolTipText, final IImageConstant icon) {
		this(builder(text, toolTipText, icon));
	}

	public CheckedItemModel(final String id, final String text, final String toolTipText, final IImageConstant icon) {
		this(builder(id, text, toolTipText, icon));
	}

	public CheckedItemModel(final ICheckedItemModelBuilder builder) {
		super(builder.build());
	}

	@Override
	protected ICheckedItemModel getItemModel() {
		return (ICheckedItemModel) super.getItemModel();
	}

	@Override
	public ICheckedItemModel createCopy() {
		return getItemModel().createCopy();
	}

	public static ICheckedItemModelBuilder builder() {
		return Toolkit.getModelFactoryProvider().getItemModelFactory().checkedItemBuilder();
	}

	public static ICheckedItemModelBuilder builder(final String text) {
		return builder().setText(text);
	}

	public static ICheckedItemModelBuilder builder(final String text, final String toolTipText) {
		return builder(text).setToolTipText(toolTipText);
	}

	public static ICheckedItemModelBuilder builder(final String text, final IImageConstant icon) {
		return builder().setText(text).setIcon(icon);
	}

	public static ICheckedItemModelBuilder builder(final String text, final String toolTipText, final IImageConstant icon) {
		return builder(text, toolTipText).setIcon(icon);
	}

	public static ICheckedItemModelBuilder builder(
		final String id,
		final String text,
		final String toolTipText,
		final IImageConstant icon) {
		return builder(text, toolTipText, icon).setId(id);
	}

}
