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

package org.jowidgets.impl.model.item;

import org.jowidgets.api.command.IAction;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.model.item.IPopupActionItemModel;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Accelerator;

class PopupActionItemModelImpl extends AbstractActionItemModelImpl implements IPopupActionItemModel {

	private IMenuModel popupMenu;

	protected PopupActionItemModelImpl() {
		this(null, null, null, null, null, null, true, null, null);
	}

	protected PopupActionItemModelImpl(
		final String id,
		final String text,
		final String toolTipText,
		final IImageConstant icon,
		final Accelerator accelerator,
		final Character mnemonic,
		final boolean enabled,
		final IAction action,
		final IMenuModel popupMenu) {
		super(id, text, toolTipText, icon, accelerator, mnemonic, enabled, action);

		this.popupMenu = popupMenu;
	}

	@Override
	public IPopupActionItemModel createCopy() {
		final PopupActionItemModelImpl result = new PopupActionItemModelImpl();
		result.setContent(this);
		return result;
	}

	protected void setContent(final PopupActionItemModelImpl source) {
		super.setContent(source);
		this.popupMenu = source.getPopupMenu();
	}

	@Override
	public void setPopupMenu(final IMenuModel popupMenu) {
		this.popupMenu = popupMenu;
		fireItemChanged();
	}

	@Override
	public IMenuModel getPopupMenu() {
		return popupMenu;
	}

}
