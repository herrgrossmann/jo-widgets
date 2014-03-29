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

import org.jowidgets.api.command.ITreeExpansionAction;
import org.jowidgets.api.model.item.IActionItemModel;
import org.jowidgets.api.model.item.IActionItemModelBuilder;
import org.jowidgets.api.model.item.ICheckedItemModel;
import org.jowidgets.api.model.item.ICheckedItemModelBuilder;
import org.jowidgets.api.model.item.IContainerItemModel;
import org.jowidgets.api.model.item.IContainerItemModelBuilder;
import org.jowidgets.api.model.item.IItemModelFactory;
import org.jowidgets.api.model.item.IMenuBarModel;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.model.item.IMenuModelBuilder;
import org.jowidgets.api.model.item.IPopupActionItemModel;
import org.jowidgets.api.model.item.IPopupActionItemModelBuilder;
import org.jowidgets.api.model.item.IRadioItemModel;
import org.jowidgets.api.model.item.IRadioItemModelBuilder;
import org.jowidgets.api.model.item.ISeparatorItemModel;
import org.jowidgets.api.model.item.ISeparatorItemModelBuilder;
import org.jowidgets.api.model.item.IToolBarModel;
import org.jowidgets.api.model.item.ITreeExpansionToolbarItemModelBuilder;

public class ItemModelFactory implements IItemModelFactory {

	@Override
	public IMenuModelBuilder menuBuilder() {
		return new MenuModelBuilder();
	}

	@Override
	public IActionItemModelBuilder actionItemBuilder() {
		return new ActionItemModelBuilder();
	}

	@Override
	public ICheckedItemModelBuilder checkedItemBuilder() {
		return new CheckedItemModelBuilder();
	}

	@Override
	public IRadioItemModelBuilder radioItemBuilder() {
		return new RadioItemModelBuilder();
	}

	@Override
	public ISeparatorItemModelBuilder separatorBuilder() {
		return new SeparatorItemModelBuilder();
	}

	@Override
	public IPopupActionItemModelBuilder popupActionItemBuilder() {
		return new PopupActionItemModelBuilder();
	}

	@Override
	public IContainerItemModelBuilder containerItemBuilder() {
		return new ContainerItemModelBuilder();
	}

	@Override
	public IMenuModel menu() {
		return menuBuilder().build();
	}

	@Override
	public IActionItemModel actionItem() {
		return actionItemBuilder().build();
	}

	@Override
	public ICheckedItemModel checkedItem() {
		return checkedItemBuilder().build();
	}

	@Override
	public IRadioItemModel radioItem() {
		return radioItemBuilder().build();
	}

	@Override
	public IPopupActionItemModel popupActionItem() {
		return popupActionItemBuilder().build();
	}

	@Override
	public IContainerItemModel containerItem() {
		return containerItemBuilder().build();
	}

	@Override
	public ISeparatorItemModel separator() {
		return separatorBuilder().build();
	}

	@Override
	public IMenuBarModel menuBar() {
		return new MenuBarModelImpl();
	}

	@Override
	public IToolBarModel toolBar() {
		return new ToolBarModelImpl();
	}

	@Override
	public ITreeExpansionToolbarItemModelBuilder treeExpansionToolbarItemBuilder(final ITreeExpansionAction action) {
		return new TreeExpansionToolbarActionModelBuilderImpl(action);
	}

}
