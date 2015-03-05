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

package org.jowidgets.api.model.item;

import java.util.List;

import org.jowidgets.api.command.IAction;
import org.jowidgets.api.model.IListModelObservable;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.util.IDecorator;

public interface IMenuModel extends IItemModel, IMenuItemModel, IToolBarItemModel, IListModelObservable {

	//TODO MG add checked item model gets problem with ids
	void addItem(final IMenuItemModel item);

	void addItem(final int index, final IMenuItemModel item);

	<MODEL_TYPE extends IMenuItemModel, BUILDER_TYPE extends IItemModelBuilder<?, MODEL_TYPE>> MODEL_TYPE addItem(
		final BUILDER_TYPE itemBuilder);

	<MODEL_TYPE extends IMenuItemModel, BUILDER_TYPE extends IItemModelBuilder<?, MODEL_TYPE>> MODEL_TYPE addItem(
		int index,
		final BUILDER_TYPE itemBuilder);

	void addAfter(IMenuItemModel newItem, String... idPath);

	void addBefore(IMenuItemModel newItem, String... idPath);

	IActionItemModel addAction(IAction action);

	IActionItemModel addAction(final int index, IAction action);

	IActionItemModel addActionItem();

	IActionItemModel addActionItem(String text);

	IActionItemModel addActionItem(String text, String toolTipText);

	IActionItemModel addActionItem(String text, IImageConstant icon);

	IActionItemModel addActionItem(String text, String toolTipText, IImageConstant icon);

	ICheckedItemModel addCheckedItem();

	ICheckedItemModel addCheckedItem(String text);

	ICheckedItemModel addCheckedItem(String text, String toolTipText);

	ICheckedItemModel addCheckedItem(String text, IImageConstant icon);

	ICheckedItemModel addCheckedItem(String text, String toolTipText, IImageConstant icon);

	IRadioItemModel addRadioItem();

	IRadioItemModel addRadioItem(String text);

	IRadioItemModel addRadioItem(String text, String toolTipText);

	IRadioItemModel addRadioItem(String text, IImageConstant icon);

	IRadioItemModel addRadioItem(String text, String toolTipText, IImageConstant icon);

	ISeparatorItemModel addSeparator();

	ISeparatorItemModel addSeparator(String id);

	ISeparatorItemModel addSeparator(int index);

	IMenuModel addMenu();

	IMenuModel addMenu(String text);

	IMenuModel addMenu(String text, String toolTipText);

	IMenuModel addMenu(String text, IImageConstant icon);

	IMenuModel addMenu(String text, String toolTipText, IImageConstant icon);

	void addItemsOfModel(IMenuModel menuModel);

	/**
	 * Binds the given model to this model with the following manner:
	 * 
	 * 1. All items of the given model will be removed.
	 * 2. All items of this model will be added to the given model.
	 * 3. All changes on this model will be done on the given model.
	 * 
	 * @param model The model to bind this model to
	 * 
	 * @throws IllegalArgumentException if the given model is null
	 */
	void bind(IMenuModel model);

	/**
	 * Unbind the given model. Changes on this model will no longer be propagated
	 * to the given model.
	 * 
	 * @param model the model to unbind, may be not bound already but must not be null
	 * 
	 * @throws IllegalArgumentException if the given model is null
	 */
	void unbind(IMenuModel model);

	void removeItem(final IMenuItemModel item);

	void removeItem(int index);

	void removeAllItems();

	IMenuItemModel findItemByPath(String... idPath);

	void addDecorator(IDecorator<IAction> decorator);

	void removeDecorator(IDecorator<IAction> decorator);

	List<IMenuItemModel> getChildren();

	/**
	 * Makes a deep copy of the item and its children.
	 * Registered listeners on items won't be copied.
	 * 
	 * @return A new instance that is a clone of this instance
	 */
	@Override
	IMenuModel createCopy();

}
